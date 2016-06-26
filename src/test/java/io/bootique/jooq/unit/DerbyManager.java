package io.bootique.jooq.unit;


import org.junit.Assert;
import org.junit.rules.ExternalResource;

import java.io.File;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: create bootique-jdbc-test and move it there?
public class DerbyManager extends ExternalResource {

    public static final OutputStream DEV_NULL = new OutputStream() {

        @Override
        public void write(int b) {
        }
    };

    public DerbyManager(String location) {

        if (System.getProperty("derby.stream.error.field") == null) {
            System.setProperty("derby.stream.error.field", DerbyManager.class.getName() + ".DEV_NULL");
        }

        deleteDir(new File(location));
    }

    protected void deleteDir(File dir) {
        if (dir.exists()) {

            for (File f : dir.listFiles()) {
                if (f.isFile()) {
                    Assert.assertTrue(f.delete());
                } else {
                    deleteDir(f);
                }
            }

            Assert.assertTrue(dir.delete());
        }
    }

    @Override
    protected void after() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            // the exception is actually expected on shutdown... go figure...
        }
    }
}
