package io.primeaspect.calculator

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

@Configuration
class TestConfiguration {
    @Bean
    fun dataSource(): DataSource = EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .build()

    @Bean
    fun template(): JdbcTemplate = JdbcTemplate(dataSource())
}