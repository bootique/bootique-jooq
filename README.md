<!--
   Licensed to ObjectStyle LLC under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ObjectStyle LLC licenses
   this file to you under the Apache License, Version 2.0 (the
   “License”); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.
  -->

[![Build Status](https://travis-ci.org/bootique/bootique-jooq.svg)](https://travis-ci.org/bootique/bootique-jooq)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.bootique.jooq/bootique-jooq/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.bootique.jooq/bootique-jooq/)

# bootique-jooq

Integration of Jooq SQL builder with Bootique. See usage example [bootique-jooq-demo](https://github.com/bootique-examples/bootique-jooq-demo).

## Usage

Include ```bootique-jooq```:
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.bootique.bom</groupId>
            <artifactId>bootique-bom</artifactId>
            <version>0.19</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

...

<dependency>
	<groupId>io.bootique.jooq</groupId>
	<artifactId>bootique-jooq</artifactId>
</dependency>
```

Do whatever Jooq class generation is required (for now outside of bootique-jooq scope).

Create configuration with DB connection information and optional Jooq settings:
```yaml
jdbc:
  default:
      url: "jdbc:postgresql://192.168.99.100:5432/scheduling"
      initialSize: 1
      username: postgres
      password: postgres

jooq:
  dialect: POSTGRES
  executeLogging: true
```
Inject ```JooqFactory``` and start using Jooq:
```java
@Inject
private JooqFactory jooqFactory;

public void doSomething() {
	try (DSLContext c = jooqFactory.newContext()) {
	    Record r = c.select().from(Tables.MY_TABLE).fetchOne();
	}
}
```




