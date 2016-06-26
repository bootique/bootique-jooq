package io.bootique.jooq;

import org.jooq.DSLContext;

/**
 * Provides new instances of Jooq {@link DSLContext}.
 */
public interface JooqFactory {

    /**
     * Creates and returns a new context for the "default" DataSource. Only works if there one DataSource available
     * in your Bootoque app. It is the caller's responsibility to close the context.
     *
     * @return a new {@link DSLContext}.
     */
    DSLContext newContext();

    /**
     * Creates and returns a new context for the named DataSource. It is the caller's responsibility to
     * close the context.
     *
     * @param dataSource DataSource name mapped in Bootique.
     * @return a new {@link DSLContext}.
     */
    DSLContext newContext(String dataSource);
}
