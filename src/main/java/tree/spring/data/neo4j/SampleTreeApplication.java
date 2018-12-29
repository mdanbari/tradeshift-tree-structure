package tree.spring.data.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


@SpringBootApplication
@EnableNeo4jRepositories("tree.spring.data.neo4j.repositories")
public class SampleTreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleTreeApplication.class, args);
    }
}