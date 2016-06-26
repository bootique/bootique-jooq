package io.bootique.jooq;

import com.nhl.bootique.jdbc.DataSourceFactory;
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

    public void setExecuteLogging(boolean executeLogging) {
        this.executeLogging = executeLogging;
    }

    public DefaultJooqFactory createFactory(DataSourceFactory dataSourceFactory) {


        Settings defaultSettings = SettingsTools.defaultSettings();
        defaultSettings.setExecuteLogging(executeLogging);

        // TODO: guess the dialect based on the connection info
        Objects.requireNonNull(dialect);

        return new DefaultJooqFactory(dataSourceFactory, dialect, defaultSettings);
    }
}
