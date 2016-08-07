package io.bootique.jooq;

import io.bootique.jdbc.DataSourceFactory;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;

import java.util.Objects;

public class DefaultJooqFactoryFactory {

    private SQLDialect dialect = SQLDialect.DEFAULT;
    private boolean executeLogging = false;

    // TODO: other Settings properties...

    /**
     * Sets an optional dialect for the Jooq DSL.
     *
     * @param dialect a Jooq SQL dialect object.
     */
    public void setDialect(SQLDialect dialect) {
        this.dialect = dialect;
    }

    /**
     * Sets a flag that enables or disables SQL execution logging by Jooq. "false" is the default. If you want to set
     * it to true, also make sure 'org.jooq' logger is allowed to log at DEBUG level.
     *
     * @param executeLogging SQL logging on/off flag. "false" is the default.
     */
    public void setExecuteLogging(boolean executeLogging) {
        this.executeLogging = executeLogging;
    }

    public DefaultJooqFactory createFactory(DataSourceFactory dataSourceFactory) {

        // pretty evil side effect on system properties. Wish Jooq had abstracted its properties bootstrap.
        // Still the logo has to go.
        System.setProperty("org.jooq.no-logo", "true");

        Settings defaultSettings = SettingsTools.defaultSettings();
        defaultSettings.setExecuteLogging(executeLogging);

        // TODO: guess the dialect based on the connection info
        Objects.requireNonNull(dialect);

        return new DefaultJooqFactory(dataSourceFactory, dialect, defaultSettings);
    }
}
