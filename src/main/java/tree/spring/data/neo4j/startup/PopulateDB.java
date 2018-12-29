package tree.spring.data.neo4j.startup;

import org.springframework.stereotype.Component;

import tree.spring.data.neo4j.domain.Node;
import tree.spring.data.neo4j.repositories.NodeRepository;

@Component
public class PopulateDB {

    private final NodeRepository nodeRepository;

    public PopulateDB(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }


    public void populate() {
    	nodeRepository.deleteAll();

		Node root = new Node("Boss");
		Node child1 = new Node("Child1");
		Node child2 = new Node("Child2");
		Node child3 = new Node("Child3");
		Node child4 = new Node("Child4");
		
		root.setHeight(0);
		child1.setHeight(root.getHeight() + 1);
		child2.setHeight(root.getHeight() + 1);
		child3.setHeight(root.getHeight() + 1);
		child4.setHeight(child1.getHeight() + 1);
		
		root.addChild(child1);
		root.addChild(child2);
		root.addChild(child3);
		child1.addChild(child4);
		
		nodeRepository.save(root);		
		nodeRepository.save(child1);
		nodeRepository.save(child2);
		nodeRepository.save(child3);
		nodeRepository.save(child4);

    }

    public static int nodeCounter = 3;
    public static int nodeMaxNum = 100;



    public void generateRandomBinaryTree() {
    }



}




