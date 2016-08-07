package io.bootique.jooq;

import io.bootique.test.junit.BQModuleProviderChecker;
import org.junit.Test;

public class JooqModuleProviderTest {

    @Test
    public void testPresentInJar() {
        BQModuleProviderChecker.testPresentInJar(JooqModuleProvider.class);
    }
}
