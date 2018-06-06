/**
 *    Licensed to the ObjectStyle LLC under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ObjectStyle LLC licenses
 *  this file to you under the Apache License, Version 2.0 (the
 *  “License”); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package io.bootique.jooq;

import io.bootique.BQRuntime;
import io.bootique.jooq.unit.generated.Tables;
import io.bootique.test.junit.BQTestFactory;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class JooqMySQLIT {

    private static final String DB_NAME = "jooqdb";
    private static final String USER_NAME = "mysqluser";
    private static final String PASSWORD = "secret";

    @ClassRule
    public static MySQLContainer MYSQL = (MySQLContainer) new MySQLContainer("mysql:5.7")
            .withDatabaseName(DB_NAME)
            .withUsername(USER_NAME)
            .withPassword(PASSWORD)
            .withEnv("MYSQL_ROOT_HOST", "%");

    @Rule
    public BQTestFactory stack = new BQTestFactory();

    private static String dbUrl() {
        return String.format("jdbc:mysql://%s:%s/%s?TC_INITSCRIPT=%s",
                MYSQL.getContainerIpAddress(),
                MYSQL.getMappedPort(MySQLContainer.MYSQL_PORT),
                DB_NAME,
                "io/bootique/jooq/init_mysql.sql");
    }

    @Test
    public void testNewContext() {

        BQRuntime runtime = stack.app("--config=classpath:io/bootique/jooq/test.yml")
                .property("bq.jdbc.default.jdbcUrl", dbUrl())
                .property("bq.jdbc.default.username", USER_NAME)
                .property("bq.jdbc.default.password", PASSWORD)
                .autoLoadModules()
                .createRuntime();

        try (DSLContext c = runtime.getInstance(JooqFactory.class).newContext()) {

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
