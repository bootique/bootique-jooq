package io.bootique.jooq;


import com.nhl.bootique.jdbc.DataSourceFactory;
import org.jooq.DSLContext;

import java.util.Collection;

public class DefaultJooqFactory implements JooqFactory {

    private DataSourceFactory dataSourceFactory;

    public DefaultJooqFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
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
                        "More then one DataSource is provided by 'bootique-jdbc");
        }
    }

    @Override
    public DSLContext newContext(String dataSource) {
        return null;
    }
}
