package by.mmb.common;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * @author andrew.maksimovich
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractCommonTestRepository {

    protected JdbcTemplate jdbcTemplate = new JdbcTemplate();


    @BeforeAll
    public void initBD() throws Exception{
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .build();
        jdbcTemplate.setDataSource(dataSource);

        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(jdbcTemplate.getDataSource().getConnection()));
        database.setDefaultSchemaName("space");
        Liquibase liquibase = new Liquibase("db.changelog/changelog-master.xml", new ClassLoaderResourceAccessor(),database );
        liquibase.update("test");
    }
}