/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
