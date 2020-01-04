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

public class CreateFunction {

    protected Function function;
    protected String delimiter;

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    @Override
    public String toString() {
        function.getStatement().setDelimiter(delimiter);
        return String.format(SQLSchemaTemplate.CREATE_FUNCTION, delimiter,
            function.getName(), function.getStatement());
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
