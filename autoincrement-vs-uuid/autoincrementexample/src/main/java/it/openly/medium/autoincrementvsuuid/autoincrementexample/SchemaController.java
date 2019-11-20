package it.openly.medium.autoincrementvsuuid.autoincrementexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@RestController
public class SchemaController {

    @Autowired
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @RequestMapping("/listtables")
    public List<Map<String, Object>> listTables() {
        return namedParameterJdbcTemplate.queryForList("select * from information_schema.tables where table_schema = 'PUBLIC'", new HashMap<>());
    }

    @RequestMapping("/createtables")
    public String createSchema() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("schema_ddl/create_schema_tables.sql"));
        return "Done.";
    }

    @RequestMapping("/droptables")
    public String dropSchema() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("schema_ddl/drop_schema_tables.sql"));
        return "Done.";
    }

}
