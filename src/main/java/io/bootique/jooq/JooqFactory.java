package io.bootique.jooq;

import org.jooq.DSLContext;

/**
 * Provides new instances of Jooq {@link DSLContext}.
 */
public interface JooqFactory {

    /**
     * Creates and returns a new context for the "default" DataSource. Only works if there one and only one
     * DataSource available in your Bootoque app. It is caller's responsibility to close the returned context.
     *
     * @return a new {@link DSLContext}.
     */
    DSLContext newContext();

    /**
     * Creates and returns a new context for the named DataSource. It is caller's responsibility to close the
     * returned context.
     *
     * @param dataSource the name of a DataSource mapped in Bootique.
     * @return a new {@link DSLContext}.
     */
    DSLContext newContext(String dataSource);
}
