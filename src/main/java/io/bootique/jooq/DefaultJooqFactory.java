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


import io.bootique.jdbc.DataSourceFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Objects;

public class DefaultJooqFactory implements JooqFactory {

    private DataSourceFactory dataSourceFactory;
    private SQLDialect dialect;
    private Settings defaultSettings;

    public DefaultJooqFactory(DataSourceFactory dataSourceFactory, SQLDialect dialect, Settings defaultSettings) {
        this.dataSourceFactory = dataSourceFactory;
        this.dialect = Objects.requireNonNull(dialect);
        this.defaultSettings = defaultSettings;
    }

    @Override
    public DSLContext newContext() {

        Collection<String> allNames = dataSourceFactory.allNames();

        switch (allNames.size()) {
            case 0:
                throw new IllegalStateException("DataSource is not configured. " +
                        "Configure it via 'bootique-jdbc'");
            case 1:
                return newContext(allNames.iterator().next());
            default:
                throw new IllegalStateException("Default DataSource ambiguity. " +
                        "More then one DataSource is provided by 'bootique-jdbc'");
        }
    }

    @Override
    public DSLContext newContext(String dataSource) {

        DataSource ds = dataSourceFactory.forName(dataSource);
        return DSL.using(ds, dialect, defaultSettings);
    }
}
