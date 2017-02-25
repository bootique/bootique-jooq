package io.bootique.jooq;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;
import io.bootique.jdbc.DataSourceFactory;

public class JooqModule extends ConfigModule {

    @Provides
    @Singleton
    JooqFactory provideJooqFactory(ConfigurationFactory configurationFactory, DataSourceFactory dataSourceFactory) {
        return configurationFactory
                .config(DefaultJooqFactoryFactory.class, configPrefix)
                .createFactory(dataSourceFactory);
    }
}
