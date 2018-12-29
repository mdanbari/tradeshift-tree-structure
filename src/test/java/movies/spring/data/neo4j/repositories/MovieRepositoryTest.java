package movies.spring.data.neo4j.repositories;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import tree.spring.data.neo4j.domain.Node;
import tree.spring.data.neo4j.repositories.NodeRepository;

/**
 * @author pdtyreus
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MovieRepositoryTest {

	
	
	@Autowired
	private NodeRepository nodeRepository;
	


	

	@Before
	@Rollback(false)
	public void setUp() {
		
		nodeRepository.deleteAll();
		
		Node root = new Node("Boss");
		nodeRepository.save(root);
		Node child1 = new Node("Child1");
		root.addChild(child1);
		//child1.setParent(root);
		nodeRepository.save(child1);
		Node child2 = new Node("Child2");
		//child2.setParent(root);
		root.addChild(child2);
		nodeRepository.save(child2);
		Node child3 = new Node("Child3");
		//child3.setParent(root);
		nodeRepository.save(child3);
		root.addChild(child3);
		
		Node child4 = new Node("Child4");
		nodeRepository.save(child4);
		child1.addChild(child4);
		//child4.setParent(child1);
		nodeRepository.save(child1);
		
		nodeRepository.save(root);
		
		
		
		
		
//		Movie matrix = new Movie("The Matrix", 1999, "Welcome to the Real World");
//
//		movieRepository.save(matrix);
//
//		Person keanu = new Person("Keanu Reeves", 1964);
//
//		personRepository.save(keanu);
//
//		Role neo = new Role(matrix, keanu);
//		neo.addRoleName("Neo");
//
//		matrix.addRole(neo);
//
//		movieRepository.save(matrix);
	}

	/**
	 * Test of findByTitle method, of class MovieRepository.
	 */
	@Test
	@Rollback(false)
	public void testFindByTitle() {

		String currentNodeName = "Child1";
		String newParrentName = "Child3";
		Node currentNode = nodeRepository.findByName(currentNodeName);
		Optional<Node> findById = nodeRepository.findById(currentNode.getId(), 1);
		Optional<Node> findById2 = nodeRepository.findById(currentNode.getId(), 2);
//		Collection<Node> findByName = nodeRepository.findByName(currentNodeName);
//		Collection<Node> findByName2 = nodeRepository.findByName(newParrentName);
		
		//Node currentNode = nodeRepository.findByName(currentNodeName);
		//Node newParent = nodeRepository.findByName(newParrentName);
//		Node currentParent = currentNode.getParent();
//		List<Node> children = currentParent.getChildren();
//		children.remove(currentNode);
//		nodeRepository.save(currentParent);
//		currentNode.setParent(newParent);
//		nodeRepository.save(currentNode);
//		newParent.addChild(currentNode);
//		nodeRepository.save(newParent);
		
		
		//assertEquals("Boss", result.getParent().getName());
	}
//
//	/**
//	 * Test of findByTitleContaining method, of class MovieRepository.
//	 */
//	@Test
//	public void testFindByTitleContaining() {
//		String title = "*Matrix*";
//		Collection<Movie> result = movieRepository.findByTitleLike(title);
//		assertNotNull(result);
//		assertEquals(1, result.size());
//	}
//
//	/**
//	 * Test of graph method, of class MovieRepository.
//	 */
//	@Test
//	public void testGraph() {
//		Collection<Movie> graph = movieRepository.graph(5);
//
//		assertEquals(1, graph.size());
//
//		Movie movie = graph.iterator().next();
//
//		assertEquals(1, movie.getRoles().size());
//
//		assertEquals("The Matrix", movie.getTitle());
//		assertEquals("Keanu Reeves", movie.getRoles().iterator().next().getPerson().getName());
//	}
}