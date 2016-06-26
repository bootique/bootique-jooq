package io.bootique.jooq;

import com.nhl.bootique.BQRuntime;
import com.nhl.bootique.jdbc.JdbcModule;
import com.nhl.bootique.test.junit.BQDaemonTestFactory;
import org.junit.Rule;
import org.junit.Test;

public class JooqModuleIT {

    @Rule
    public BQDaemonTestFactory stack = new BQDaemonTestFactory();


    @Test
    public void testSelect() {

        BQRuntime runtime = stack.newRuntime().configurator(bootique ->
                bootique.modules(JdbcModule.class, JooqModule.class)
        ).start().getRuntime();


        runtime.getInstance()
    }
}
