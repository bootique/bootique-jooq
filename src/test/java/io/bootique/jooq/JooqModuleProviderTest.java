package io.bootique.jooq;

import com.nhl.bootique.test.junit.BQModuleProviderChecker;
import org.junit.Test;

public class JooqModuleProviderTest {

    @Test
    public void testPresentInJar() {
        BQModuleProviderChecker.testPresentInJar(JooqModuleProvider.class);
    }
}
