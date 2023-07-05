/**************************************************************************
Copyright 2019 Vietnamese-German-University
Copyright 2023 ETH Zurich

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author: thian
@author: hoangnguyen (hoang.nguyen@inf.ethz.ch)
***************************************************************************/

package modeling.data.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import modeling.data.config.Config;
import modeling.data.entities.Association;
import modeling.data.entities.AssociationType;
import modeling.data.entities.Attribute;
import modeling.data.entities.DataModel;
import modeling.data.entities.End;
import modeling.data.entities.Entity;
import modeling.data.entities.Multiplicity;

public class DmUtils {

	public static String getAssociationName(DataModel dm, String className, String endName) {

		Set<Association> assocs = dm.getAssociations();

		for (Association assoc : assocs) {
			if (className.equals(assoc.getLeftEnd().getCurrentClass())
					&& endName.equals(assoc.getRightEnd().getOpp())) {
				return assoc.getName();
			}

			if (className.equals(assoc.getRightEnd().getCurrentClass())
					&& endName.equals(assoc.getRightEnd().getOpp())) {
				return assoc.getName();
			}
		}

		return null;
	}

	public static String getAssociationEndTargetClassName(DataModel dm, String className, String endName) {
		End end = getEnd(dm, className, endName);
		return Optional.ofNullable(end).map(End::getTargetClass).orElse(null);
	}

	public static String getAssociationEndSourceClassName(DataModel dm, String className, String endName) {
		End end = getEnd(dm, className, endName);
		return Optional.ofNullable(end).map(End::getOpp).orElse(null);
	}

	public static boolean isAssociationEndOfClass(DataModel dm, String className, String endName) {
		End end = getEnd(dm, className, endName);
		return Optional.ofNullable(end).map(End::getName).map(v -> v.equals(endName)).orElse(false);
	}

	public static boolean isClass(DataModel dm, String className) {
		return dm.getEntities().containsKey(className);
	}

	public static boolean isPropertyOfClass(DataModel dm, String className, String attName) {
		Attribute att = getAttribute(dm, className, attName);
		return Optional.ofNullable(att).map(Attribute::getName).map(v -> v.equals(attName)).orElse(false);
	}

	public static String getAttributeType(DataModel dm, String className, String attName) {
		Attribute att = getAttribute(dm, className, attName);
		return Optional.ofNullable(att).map(Attribute::getType).orElse(null);
	}

	public static boolean isAssocM2M(DataModel dm, String className, String endName) {
		return getAssocRelationship(dm, className, endName) == AssociationType.MANY_TO_MANY;
	}

	public static boolean isAssocM2O(DataModel dm, String className, String endName) {
		return getAssocRelationship(dm, className, endName) == AssociationType.MANY_TO_ONE;
	}

	public static boolean isEndMultMany(DataModel dm, String className, String endName) {
		End end = getEnd(dm, className, endName);
		return Optional.ofNullable(end).map(End::getMult).map(v -> v == Multiplicity.MANY).orElse(false);
	}

	public static boolean isEndMultOne(DataModel dm, String className, String endName) {
		End end = getEnd(dm, className, endName);
		return Optional.ofNullable(end).map(End::getMult).map(v -> v == Multiplicity.ONE).orElse(false);
	}

	private static AssociationType getAssocRelationship(DataModel dm, String className, String endName) {

		try {
			End end = getEnd(dm, className, endName);
			Multiplicity rightMult = end.getMult();

			String targetClass = getAssociationEndTargetClassName(dm, className, endName);
			Entity entity = dm.getEntities().get(targetClass);
			Multiplicity leftMult = null;

			for (End oppositeEnd : entity.getEnds()) {
				if (oppositeEnd.getTargetClass().equals(className)) {
					leftMult = oppositeEnd.getMult();
					break;
				}
			}

			return getRelationshipType(leftMult, rightMult);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static AssociationType getRelationshipType(Multiplicity leftMult, Multiplicity rightMult) {
		boolean isLeftMultOne = leftMult == Multiplicity.ONE;
		boolean isRightMultOne = rightMult == Multiplicity.ONE;

		if (isLeftMultOne ^ isRightMultOne)
			return AssociationType.MANY_TO_ONE;
		if (isLeftMultOne && isRightMultOne)
			return AssociationType.ONE_TO_ONE;
		return AssociationType.MANY_TO_MANY;
	}

	private static End getEnd(DataModel dm, String className, String endName) {
		Entity entity = dm.getEntities().get(className);
		Set<End> ends = entity.getEnds();

		for (End end : ends) {
			if (end.getName().equals(endName)) {
				return end;
			}
		}

		return null;
	}

	private static Attribute getAttribute(DataModel dm, String className, String attName) {
		Entity entity = dm.getEntities().get(className);

		Set<Attribute> atts = entity.getAttributes();

		for (Attribute att : atts) {
			if (att.getName().equals(attName)) {
				return att;
			}
		}

		return null;
	}

	public static List<JSONObject> transform(List<JSONObject> jsonArray) {
		JSONArray target = new JSONArray();
		JSONArray clazzes = new JSONArray();
		JSONArray ascs = new JSONArray();
		if ("1.0.5-ASC".equals(Config.VERSION)) {
			// Step 1: Transform the classes
			for (JSONObject obj : jsonArray) {
				if (obj.containsKey("class")) {
					JSONObject clazz = new JSONObject();
					clazz.put("class", obj.get("class"));
					clazz.put("attributes", obj.get("attributes"));
					clazzes.add(clazz);
				}
			}
			// Step 2: Transform the association-classes
			List<String> explicitAssociationName = new ArrayList<String>();
			for (JSONObject obj : jsonArray) {
				if (obj.containsKey("association-class")) {
					JSONObject asc = new JSONObject();
					asc.put("class", obj.get("association-class"));
					asc.put("isAssociation", true);
					asc.put("attributes", obj.get("attributes"));
					String associationName = (String) obj.get("association");
					explicitAssociationName.add(associationName);

					List<JSONObject> asc_ends = (JSONArray) obj.get("implicit-associations");
					JSONArray asc_target_ends = new JSONArray();
					asc.put("ends", asc_target_ends);

					List<String> opps = new ArrayList<String>();
					List<String> oppTargets = new ArrayList<String>();
					List<String> mults = new ArrayList<String>();
					for (JSONObject assoc : jsonArray) {
						if (assoc.containsKey("association") && !assoc.containsKey("association-class")
								&& ((String) assoc.get("association")).equals(associationName)) {
							List<JSONObject> ends = (JSONArray) assoc.get("ends");
							for (JSONObject end : ends) {
								opps.add((String) end.get("name"));
								oppTargets.add((String) end.get("target"));
								mults.add((String) end.get("mult"));
							}
						}
					}
					for (JSONObject asc_end : asc_ends) {
						String oppClass = (String) asc_end.get("opp-class");
						for (int i = 0; i < opps.size(); i++) {
							String oppTarget = oppTargets.get(i);
							if (oppTarget.equals(oppClass)) {
								// Here I ignored the opp-class-end since it is not needed at the moment!
								JSONObject asc_target_end = new JSONObject();
								asc_target_end.put("association", asc_end.get("name"));
								asc_target_end.put("name", opps.get(i));
								asc_target_end.put("target", oppTarget);
								JSONArray remainingOpps = new JSONArray();
								for (String s : opps) {
									if (!s.equals(opps.get(i))) {
										remainingOpps.add(s);
									}
								}
								asc_target_end.put("opps", remainingOpps);
								JSONArray remainingOppTargets = new JSONArray();
								for (String s : oppTargets) {
									if (!s.equals(oppTarget)) {
										remainingOppTargets.add(s);
									}
								}
								asc_target_end.put("opptargets", remainingOppTargets);
								asc_target_end.put("mult", mults.get(i));
								asc_target_ends.add(asc_target_end);
								break;
							}
						}
					}
					ascs.add(asc);
				}
			}
			// Step 3: Transform association
			for (JSONObject obj : jsonArray) {
				if (obj.containsKey("association")) {
					String name = (String) obj.get("association");
					if (!explicitAssociationName.contains(name)) {
						// In this case we know that this is binary association
						List<JSONObject> ends = (JSONArray) obj.get("ends");
						JSONObject left = ends.get(0);
						JSONObject right = ends.get(1);

						JSONObject leftEnd = new JSONObject();
						clazzes.forEach(c -> {
							JSONObject clazz = (JSONObject) c;
							if (clazz.get("class").equals(left.get("target"))) {
								leftEnd.put("association", obj.get("association"));
								leftEnd.put("name", right.get("name"));
								leftEnd.put("target", right.get("target"));
								leftEnd.put("opp", left.get("name"));
								leftEnd.put("mult", right.get("mult"));
								JSONArray clazz_ends = (JSONArray) clazz.get("ends");
								if (clazz_ends == null) {
									clazz_ends = new JSONArray();
								}
								clazz_ends.add(leftEnd);
							}
						});

						JSONObject rightEnd = new JSONObject();
						clazzes.forEach(c -> {
							JSONObject clazz = (JSONObject) c;
							if (clazz.get("class").equals(left.get("target"))) {
								rightEnd.put("association", obj.get("association"));
								rightEnd.put("name", left.get("name"));
								rightEnd.put("target", left.get("target"));
								rightEnd.put("opp", right.get("name"));
								rightEnd.put("mult", left.get("mult"));
								JSONArray clazz_ends = (JSONArray) clazz.get("ends");
								if (clazz_ends == null) {
									clazz_ends = new JSONArray();
								}
								clazz_ends.add(rightEnd);
							}
						});
					}
				}
			}
		}
		target.addAll(clazzes);
		target.addAll(ascs);
		return target;
	}

}
