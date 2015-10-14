package app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import domain.Customer;

@SpringBootApplication
//Implements CommandLineRunner, so that the run() method is executed when the app context loads.
public class Application implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    
    // Since we're using spring-jdbc, Spring automagically creates a JDBCTemplate.
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*
     * Spring Boot uses H2, an in-memory RDBMS, which is loaded by the autowired JDBCTemplate.
     */
    @Override
    public void run(String... strings) throws Exception {
        log.info("Creating tables...");
        
        // Create a data schema
        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers (" +
                    "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255)+");
        
        // split up the whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("Winnie Woo", "Jeff Dean", "Josh Bloch", "Winnie Pooh").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());
        
        // Use a stream to log each tuple
        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));
        
        // Use JDBCTemplate's batchUpdate operation to bulk load data. The '?' instructs JDBC to bind values
        // and avoid SQL injection attacks
        jdbcTemplate.batchUpdate("INSERT INTO customers (first_name, last_name) VALUES (?, ?)", splitUpNames);
        
        log.info("Querying customer table where first name = 'Winnie'");
        jdbcTemplate.query("SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] {"Winnie"},
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")))
                .forEach(customer -> log.info(customer.toString()));
    }
}
