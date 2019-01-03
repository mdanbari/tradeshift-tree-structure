package tree.spring.data.neo4j.repositories;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import tree.spring.data.neo4j.domain.Node;

public interface NodeRepository extends Neo4jRepository<Node, Long> {

	Node findByName(@Param("name") String title);

	@Query("MATCH (p)-[r:child_rel]->(n) WHERE n.name = {name} RETURN p")
	Collection<Node> getParent(@Param("name") String name);

	@Query("MATCH (p)-[r:child_rel]->(n) WHERE p.name = {name} RETURN n")
	Collection<Node> getChildren(@Param("name") String name);

	@Query("MATCH ()-[r:child_rel]-(n) WHERE n.name = {name} DELETE r")
	Collection<Node> deleteRelation(@Param("name") String name);
	
	@Query("MATCH (n) DETACH DELETE n")
	void deleteAllNode();


}
