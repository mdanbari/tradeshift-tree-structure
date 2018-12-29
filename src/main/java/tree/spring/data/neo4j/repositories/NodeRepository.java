package tree.spring.data.neo4j.repositories;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import tree.spring.data.neo4j.domain.Node;

public interface NodeRepository extends Neo4jRepository<Node, Long> {

	@Query(" MATCH (n:Node)-[r]-(p:Node) where n.name ={name} RETURN n,p,r limit 3")
	Collection<Node> findByNameQuery(@Param("name") String name);

	Node findByName(@Param("name") String title);

	Collection<Node> findByNameLike(@Param("name") String name);

	@Query("MATCH (p)-[r:child_rel]->(n) WHERE n.name = {name} RETURN p")
	Collection<Node> getParent(@Param("name") String name);

	@Query("MATCH (p)-[r:child_rel]->(n) WHERE p.name = {name} RETURN n")
	Collection<Node> getChildren(@Param("name") String name);

	@Query("MATCH ()-[r:child_rel]-(n) WHERE n.name = {name} DELETE r")
	Collection<Node> deleteRelation(@Param("name") String name);

}
