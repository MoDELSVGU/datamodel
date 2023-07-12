# datamodel
A parser component that parses JSON representation to Java object hierarchy for our datamodel.

```
<dependency>
  <groupId>io.github.modelsvgu</groupId>
  <artifactId>datamodelj</artifactId>
  <version>[1.0.0,)</version>
</dependency>
```

Current support includes:
- Classes.
- Attributes with primitive types such as Integer and String.
- Association: binary associations.
- Association-classes.

To use it please call it via the following method:
```java
DataModel dm = DMParser.parse(<datamodel_filepath>);
```

Example:
The following representation is depricated but still supported.
```json
[
  {
    "class": "Lecturer",
    "attributes": [
      {
        "name": "name",
        "type": "String"
      },
      {
        "name": "age",
        "type": "Integer"
      }
    ],
    "ends": [
      {
        "association": "Enrollment",
        "name": "students",
        "target": "Student",
        "opp": "lecturers",
        "mult": "*"
      }
    ]
  },
  {
    "class": "Student",
    "attributes": [
      {
        "name": "name",
        "type": "String"
      },
      {
        "name": "age",
        "type": "Integer"
      }
    ],
    "ends": [
      {
        "association": "Enrollment",
        "name": "lecturers",
        "target": "Lecturer",
        "opp": "students",
        "mult": "*"
      }
    ]
  }
]
```

For newer representation, please add a version tag:
```json
{
	"version": "1.0.6-ASC",
	"dataModel": [
		{
			"class": "Lecturer",
			"attributes": [
				{
					"name": "name",
					"type": "String"
				},
				{
					"name": "email",
					"type": "String"
				},
				{
					"name": "age",
					"type": "Integer"
				}
			]
		},
		{
			"class": "Student",
			"attributes": [
				{
					"name": "name",
					"type": "String"
				},
				{
					"name": "email",
					"type": "String"
				},
				{
					"name": "age",
					"type": "Integer"
				}
			]
		},
		{
			"association": "Enrolment",
			"ends": [
				{
					"name": "students",
					"target": "Student",
					"mult": "*"
				},
				{
					"name": "lecturers",
					"target": "Lecturer",
					"mult": "*"
				}
			]
		}
	]
}
```
