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

import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;
import io.bootique.jdbc.DataSourceFactory;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;

import java.util.Objects;

@BQConfig
public class DefaultJooqFactoryFactory {

    private SQLDialect dialect = SQLDialect.DEFAULT;
    private boolean executeLogging = false;

    // TODO: other Settings properties...

    /**
     * Sets an optional dialect for the Jooq DSL.
     *
     * @param dialect a Jooq SQL dialect object.
     */
    @BQConfigProperty("An optional RDBMS-specific SQL dialect we'll be working with. E.g. 'MariaDB'.")
    public void setDialect(SQLDialect dialect) {
        this.dialect = dialect;
    }

    /**
     * Sets a flag that enables or disables SQL execution logging by Jooq. "false" is the default. If you want to set
     * it to true, also make sure 'org.jooq' logger is allowed to log at DEBUG level.
     *
     * @param executeLogging SQL logging on/off flag. "false" is the default.
     */
    @BQConfigProperty("Enables or disables SQL logging. False by default.")
    public void setExecuteLogging(boolean executeLogging) {
        this.executeLogging = executeLogging;
    }

    public DefaultJooqFactory createFactory(DataSourceFactory dataSourceFactory) {

        // pretty evil side effect on system properties. Wish Jooq had abstracted its properties bootstrap.
        // Still the logo has to go.
        System.setProperty("org.jooq.no-logo", "true");

        Settings defaultSettings = SettingsTools.defaultSettings();
        defaultSettings.setExecuteLogging(executeLogging);

        // TODO: guess the dialect based on the connection info - https://github.com/bootique/bootique-jooq/issues/3
        Objects.requireNonNull(dialect);

        return new DefaultJooqFactory(dataSourceFactory, dialect, defaultSettings);
    }
}
