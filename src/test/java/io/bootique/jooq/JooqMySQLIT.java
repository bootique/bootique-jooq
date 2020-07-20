/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.bootique.jooq;

import io.bootique.BQRuntime;
import io.bootique.Bootique;
import io.bootique.jdbc.junit5.tc.TcDbTester;
import io.bootique.jooq.unit.generated.Tables;
import io.bootique.junit5.BQApp;
import io.bootique.junit5.BQTest;
import io.bootique.junit5.BQTestTool;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Testcontainers
@BQTest
public class JooqMySQLIT {

    @Container
    static final MySQLContainer db = new MySQLContainer("mysql:8.0.20").withDatabaseName("jooqdb");

    @BQTestTool
    static final TcDbTester dbTester = TcDbTester.db(db);

    @BQApp(skipRun = true)
    static final BQRuntime app = Bootique
            .app("--config=classpath:io/bootique/jooq/test.yml")
            .autoLoadModules()
            .module(dbTester.moduleWithTestDataSource("db"))
            .createRuntime();

    @Test
    public void testNewContext() {

        try (DSLContext c = app.getInstance(JooqFactory.class).newContext()) {

            c.createTable(Tables.TEST_TABLE).columns(Tables.TEST_TABLE.fields()).execute();

            c.delete(Tables.TEST_TABLE).execute();
            c.insertInto(Tables.TEST_TABLE).set(Tables.TEST_TABLE.ID, 4).set(Tables.TEST_TABLE.NAME, "me").execute();
            Record r = c.select().from(Tables.TEST_TABLE).fetchOne();
            assertNotNull(r);
            assertEquals(Integer.valueOf(4), r.get(Tables.TEST_TABLE.ID));
            assertEquals("me", r.get(Tables.TEST_TABLE.NAME));
        }
    }
}
