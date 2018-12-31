package tree.spring.data.neo4j.startup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tree.spring.data.neo4j.domain.Node;
import tree.spring.data.neo4j.repositories.NodeRepository;

@Component
public class PopulateDB {

	private final NodeRepository nodeRepository;

	public PopulateDB(NodeRepository nodeRepository) {
		this.nodeRepository = nodeRepository;
	}

	
	// public static int nodeCounter = 1;

	public static final int nodeMaxNum = 1000;

	@PostConstruct
	@Transactional
	public void generateRandomBinaryTree() {
		nodeRepository.deleteAll();
		Node root = new Node("Boss");
		List<Node> nodeList = Arrays.asList(nodeRepository.save(new Node("Child1")),
				nodeRepository.save(new Node("Child2")));
		root.setChildren(nodeList);
		nodeRepository.save(root);
		generateNodeAndChildren(nodeList, nodeMaxNum);

	}

	@Transactional
	public void generateNodeAndChildren(List<Node> nodeList, int nodeMaxNum) {
		int nodeCounter = 3;
		while (nodeCounter <= nodeMaxNum) {
			List<Node> childList = new ArrayList<>();
			for (Node node : nodeList) {
				List<Node> binaryChild = Arrays.asList(nodeRepository.save(new Node("Child" + nodeCounter++)),
						nodeRepository.save(new Node("Child" + nodeCounter++)));
				node.setChildren(binaryChild);
				nodeRepository.save(node);
				childList.addAll(binaryChild);
			}
			nodeList = childList;
		}

	}

}
