package tree.spring.data.neo4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import tree.spring.data.neo4j.domain.Node;
import tree.spring.data.neo4j.repositories.NodeRepository;
import tree.spring.data.neo4j.services.NodeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class TreeTest {

	private static final String SAMPLE_ROOT_NAME = "Root";
	private static final String SAMPLE_CHILD1_NAME = "Child1";
	private static final String SAMPLE_CHILD2_NAME = "Child2";
	private static final String SAMPLE_CHILD3_NAME = "Child3";
	private static final String SAMPLE_CHILD4_NAME = "Child4";
	private static final int TREE_NODE_COUNT = 5;

	@Autowired
	private NodeRepository nodeRepository;

	@Autowired
	private NodeService nodeService;

	@Before
	public void setUp() {

		nodeRepository.deleteAll();

		Node root = new Node("Root");
		Node child1 = new Node("Child1");
		Node child2 = new Node("Child2");
		Node child3 = new Node("Child3");
		Node child4 = new Node("Child4");

		root.addChild(child1);
		root.addChild(child2);
		root.addChild(child3);
		child1.addChild(child4);

		nodeRepository.save(root);
		nodeRepository.save(child1);
		nodeRepository.save(child2);
		nodeRepository.save(child3);

	}

	/**
	 * Test of count of tree node method
	 */
	@Test
	public void testTreeCount() {

		long count = nodeRepository.count();
		assertEquals(count, TREE_NODE_COUNT);

	}

	/**
	 * Test of findByTitle method
	 */
	@Test
	public void testFindByTitle() {

		Node sample_root = nodeRepository.findByName(SAMPLE_ROOT_NAME);
		Node sample_child1 = nodeRepository.findByName(SAMPLE_CHILD1_NAME);
		Node sample_child2 = nodeRepository.findByName(SAMPLE_CHILD2_NAME);
		Node sample_child3 = nodeRepository.findByName(SAMPLE_CHILD3_NAME);
		Node sample_child4 = nodeRepository.findByName(SAMPLE_CHILD4_NAME);

		assertEquals(sample_root.getName(), SAMPLE_ROOT_NAME);
		assertEquals(sample_child1.getName(), SAMPLE_CHILD1_NAME);
		assertEquals(sample_child2.getName(), SAMPLE_CHILD2_NAME);
		assertEquals(sample_child3.getName(), SAMPLE_CHILD3_NAME);
		assertEquals(sample_child4.getName(), SAMPLE_CHILD4_NAME);

	}

	/**
	 * Test of getChildren method
	 */
	@Test
	public void testGetChildren() {

		Collection<Node> root_children = nodeRepository.getChildren(SAMPLE_ROOT_NAME);
		Collection<Node> child1_children = nodeRepository.getChildren(SAMPLE_CHILD1_NAME);
		Collection<Node> child2_children = nodeRepository.getChildren(SAMPLE_CHILD2_NAME);
		Collection<Node> child3_children = nodeRepository.getChildren(SAMPLE_CHILD3_NAME);
		Collection<Node> child4_children = nodeRepository.getChildren(SAMPLE_CHILD4_NAME);

		assertEquals(root_children.size(), 3);
		assertEquals(child1_children.size(), 1);
		assertEquals(child2_children.size(), 0);
		assertEquals(child3_children.size(), 0);
		assertEquals(child4_children.size(), 0);

		assertThat(getNodeName(root_children), containsInAnyOrder(SAMPLE_CHILD1_NAME, SAMPLE_CHILD2_NAME, SAMPLE_CHILD3_NAME));
		assertThat(getNodeName(child1_children), containsInAnyOrder(SAMPLE_CHILD4_NAME));
	}
	
	public List<String> getNodeName(Collection<Node> nodeColl)
	{
		List<String> nodeNames =new ArrayList<>();
		Iterator<Node> iterator = nodeColl.iterator();
		while(iterator.hasNext())
			nodeNames.add(iterator.next().getName());
		return nodeNames;
			
						
	}


	/**
	 * Test of get parent of node method
	 */
	@Test
	public void testGetParent() {

		Collection<Node> root_parent = nodeRepository.getParent(SAMPLE_ROOT_NAME);
		Collection<Node> child1_parent = nodeRepository.getParent(SAMPLE_CHILD1_NAME);
		Collection<Node> child2_parent = nodeRepository.getParent(SAMPLE_CHILD2_NAME);
		Collection<Node> child3_parent = nodeRepository.getParent(SAMPLE_CHILD3_NAME);
		Collection<Node> child4_parent = nodeRepository.getParent(SAMPLE_CHILD4_NAME);

		assertEquals(root_parent.size(), 0);
		assertThat(getNodeName(child1_parent), containsInAnyOrder(SAMPLE_ROOT_NAME));
		assertThat(getNodeName(child2_parent), containsInAnyOrder(SAMPLE_ROOT_NAME));
		assertThat(getNodeName(child3_parent), containsInAnyOrder(SAMPLE_ROOT_NAME));
		assertThat(getNodeName(child4_parent), containsInAnyOrder(SAMPLE_CHILD1_NAME));

	}
	
	/**
	 * Test of change parent method
	 */

	@Test
	public void testChangeParent() {

		nodeService.changeParentNode(SAMPLE_CHILD2_NAME, SAMPLE_CHILD1_NAME);

		Collection<Node> root_children = nodeRepository.getChildren(SAMPLE_ROOT_NAME);
		Collection<Node> child1_children = nodeRepository.getChildren(SAMPLE_CHILD1_NAME);
		Collection<Node> child2_children = nodeRepository.getChildren(SAMPLE_CHILD2_NAME);
		Collection<Node> child3_children = nodeRepository.getChildren(SAMPLE_CHILD3_NAME);
		Collection<Node> child4_children = nodeRepository.getChildren(SAMPLE_CHILD4_NAME);

		assertEquals(root_children.size(), 2);
		assertEquals(child1_children.size(), 2);
		assertEquals(child2_children.size(), 0);
		assertEquals(child3_children.size(), 0);
		assertEquals(child4_children.size(), 0);

		assertThat(getNodeName(root_children), containsInAnyOrder(SAMPLE_CHILD1_NAME, SAMPLE_CHILD3_NAME));
		assertThat(getNodeName(child1_children), containsInAnyOrder(SAMPLE_CHILD2_NAME, SAMPLE_CHILD4_NAME));

	}

	/**
	 * Test of node height method
	 */
	@Test
	public void testGetHeight() {

		int root_height = nodeService.calculateHeight(SAMPLE_ROOT_NAME);
		int child1_height = nodeService.calculateHeight(SAMPLE_CHILD1_NAME);
		int child2_height = nodeService.calculateHeight(SAMPLE_CHILD2_NAME);
		int child3_height = nodeService.calculateHeight(SAMPLE_CHILD3_NAME);
		int child4_height = nodeService.calculateHeight(SAMPLE_CHILD4_NAME);

		assertEquals(root_height, 0);
		assertEquals(child1_height, 1);
		assertEquals(child2_height, 1);
		assertEquals(child3_height, 1);
		assertEquals(child4_height, 2);

	}

}