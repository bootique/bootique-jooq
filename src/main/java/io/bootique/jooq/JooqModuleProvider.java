package io.bootique.jooq;

import com.google.inject.Module;
import io.bootique.BQModuleProvider;

public class JooqModuleProvider implements BQModuleProvider {

    @Override
    public Module module() {
        return new JooqModule();
    }
}
