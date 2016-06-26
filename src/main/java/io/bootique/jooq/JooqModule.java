package io.bootique.jooq;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.nhl.bootique.ConfigModule;
import com.nhl.bootique.config.ConfigurationFactory;
import com.nhl.bootique.jdbc.DataSourceFactory;

public class JooqModule extends ConfigModule {

    @Provides
    @Singleton
    JooqFactory provideJooqueFactory(ConfigurationFactory configurationFactory, DataSourceFactory dataSourceFactory) {
        return configurationFactory.config(DefaultJooqFactoryFactory.class, configPrefix).createFactory(dataSourceFactory);
    }
}
