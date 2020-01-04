/**************************************************************************
Copyright 2019 Vietnamese-German-University

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author: ngpbh
***************************************************************************/

package org.vgu.dm2schema.sql;

import net.sf.jsqlparser.schema.Table;

public class Trigger {
    private String name;
    private Table table;
    private TriggerAction action;
    private boolean isBefore;
    private boolean isAfter;
    private CompoundStatement statement;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public TriggerAction getAction() {
        return action;
    }

    public void setAction(TriggerAction action) {
        this.action = action;
    }

    public boolean isBefore() {
        return isBefore;
    }

    public void setBefore(boolean isBefore) {
        this.isBefore = isBefore;
    }

    public boolean isAfter() {
        return isAfter;
    }

    public void setAfter(boolean isAfter) {
        this.isAfter = isAfter;
    }

    public CompoundStatement getStatement() {
        return statement;
    }

    public void setStatement(CompoundStatement statement) {
        this.statement = statement;
    }

    public String getTriggerTime() {
        return isAfter ? "AFTER" : "BEFORE";
    }
    
    public String getTriggerEvent() {
        return getAction().toString().toUpperCase();
    }
    
    public Trigger(String name) {
        this.name = name;
    }
    
    public String getTriggerName() {
        return this.name.concat(getTriggerTime()).concat(getTriggerEvent());
    }

}
