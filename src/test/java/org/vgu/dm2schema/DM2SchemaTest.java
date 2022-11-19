package org.vgu.dm2schema;

import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.vgu.dm2schema.dm.DataModel;

import java.io.File;
import java.io.FileReader;

public class DM2SchemaTest {

    // @BeforeEach
    // void setUp() throws Exception {}

    @Test
    void generateDatabaseSqlFile() throws Exception {

        File dataModelFile = new File("/home/pj/coding/SQLSI/sncs2021_evaluation/assoClas_dm.json");

        DataModel dataModel =
                new DataModel(
                        new JSONParser().parse(new FileReader(dataModelFile.getAbsolutePath())));

        String script = DM2Schema.generateDatabase(dataModel, "vgudb");

        System.out.println(script);
    }
}
