package io.bootique.jooq;

import com.google.inject.Module;
import io.bootique.BQModule;
import io.bootique.BQModuleProvider;
import io.bootique.jdbc.JdbcModuleProvider;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class JooqModuleProvider implements BQModuleProvider {

    @Override
    public Module module() {
        return new JooqModule();
    }

    @Override
    public Map<String, Type> configs() {
        return Collections.singletonMap("jooq", DefaultJooqFactoryFactory.class);
    }

    @Override
    public BQModule.Builder moduleBuilder() {
        return BQModuleProvider.super
                .moduleBuilder()
                .description("Provides integration for Jooq library.");
    }

    @Override
    public Collection<BQModuleProvider> dependencies() {
        return Collections.singletonList(
                new JdbcModuleProvider()
        );
    }
}
