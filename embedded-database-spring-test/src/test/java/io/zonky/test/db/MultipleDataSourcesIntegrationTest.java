/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.zonky.test.db;

import io.zonky.test.db.flyway.BlockingDataSourceWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static io.zonky.test.assertj.MockitoAssertions.mockWithName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase(beanName = "dataSource2")
@ContextConfiguration
public class MultipleDataSourcesIntegrationTest {

    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource1() {
            return mock(DataSource.class, "mockDataSource1");
        }

        @Bean
        public DataSource dataSource2() {
            return mock(DataSource.class, "mockDataSource2");
        }

        @Bean
        public DataSource dataSource3() {
            return mock(DataSource.class, "mockDataSource3");
        }
    }

    @Autowired
    private DataSource dataSource1;
    @Autowired
    private DataSource dataSource2;
    @Autowired
    private DataSource dataSource3;

    @Test
    public void dataSource1ShouldBeMock() {
        assertThat(dataSource1).is(mockWithName("mockDataSource1"));
    }

    @Test
    public void dataSource2ShouldBePostgresDataSource() {
        assertThat(dataSource2).isExactlyInstanceOf(BlockingDataSourceWrapper.class);
    }

    @Test
    public void dataSource3ShouldBeMock() {
        assertThat(dataSource3).is(mockWithName("mockDataSource3"));
    }
}
