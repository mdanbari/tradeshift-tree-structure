package tree.spring.data.neo4j.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tree.spring.data.neo4j.domain.Node;
import tree.spring.data.neo4j.domain.NodeInfo;
import tree.spring.data.neo4j.repositories.NodeRepository;

@Service
public class NodeService {

	

	private final NodeRepository nodeRepository;

	public NodeService(NodeRepository nodeRepository) {
		this.nodeRepository = nodeRepository;
	}

	

	@Transactional
	@Async
	public void generateRandomBinaryTree(int nodeMaxNum) {
		nodeRepository.deleteAll();
		List<Node> nodeList = Arrays.asList(new Node("Boss"));
		generateNodeAndChildren(nodeList, nodeMaxNum);

	}

	@Transactional
	public void generateNodeAndChildren(List<Node> nodeList, int nodeMaxNum) {
		int nodeCounter = 2;
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


	
	@Transactional
	public void changeParentNode(String nodeName, String newParentName) {
		
		Node currentNode = nodeRepository.findByName(nodeName);
		Collection<Node> oldParentCol = nodeRepository.getParent(nodeName);
		Iterator<Node> oldParentItr = oldParentCol.iterator();
		Node oldParent = oldParentItr.next();
		Collection<Node> oldParentChildrenCol = nodeRepository.getChildren(oldParent.getName());
		
		List<Node> oldParentChildrenList = new ArrayList<>(oldParentChildrenCol);
		oldParentChildrenList.remove(currentNode);
		

		Collection<Node> currentNodeChildrenCol = nodeRepository.getChildren(nodeName);
		Iterator<Node> currentNodeChildrenItr = currentNodeChildrenCol.iterator();
		while (currentNodeChildrenItr.hasNext()) {
			Node currentNodeChild = currentNodeChildrenItr.next();
			//currentNodeChild.setHeight(oldParent.getHeight() + 1);
			nodeRepository.save(currentNodeChild);
			oldParentChildrenList.add(currentNodeChild);
		}
		oldParent.setChildren(oldParentChildrenList);
		nodeRepository.deleteRelation(nodeName);
		nodeRepository.save(oldParent);
		
		
		Node newParent = nodeRepository.findByName(newParentName);
		Collection<Node> newParentCol = nodeRepository.getChildren(newParentName);
		//currentNode.setHeight(newParent.getHeight() + 1);
		nodeRepository.save(currentNode);
		newParentCol.add(currentNode);
		newParent.setChildren(new ArrayList<>(newParentCol));
		nodeRepository.save(newParent);
		
		
		
	}

	@Transactional(readOnly = true)
	public int calculateHeight(String nodeName) {
		Collection<Node> oldParentCol = nodeRepository.getParent(nodeName);
		int height = 0;
		while(oldParentCol.iterator().hasNext())
		{
			Node nextParent = oldParentCol.iterator().next();
			oldParentCol = nodeRepository.getParent(nextParent.getName());
			height ++;
		}
		return height;		
	}
	
	@Transactional(readOnly = true)
	public String getRootNode(String nodeName) {
		Node currentNode = nodeRepository.findByName(nodeName);
		Collection<Node> oldParentCol = nodeRepository.getParent(nodeName);
		Node root  = currentNode;
		while(oldParentCol.iterator().hasNext())
		{
			Node nextParent = oldParentCol.iterator().next();
			oldParentCol = nodeRepository.getParent(nextParent.getName());
			root = nextParent;
		}
		return root.getName();		
	}
	
	@Transactional(readOnly = true)
	public Node getParentNode(String nodeName){
		Collection<Node> parent = nodeRepository.getParent(nodeName);
		Iterator<Node> iterator = parent.iterator();
		if(iterator.hasNext())
			return iterator.next();
		else
			return nodeRepository.findByName(nodeName);		
	}	

	
	@Transactional(readOnly = true)
	public String getNodeInfo(String nodeName) {
		Node currentNode = nodeRepository.findByName(nodeName);
		NodeInfo nodeInfo = new NodeInfo();
		nodeInfo.setNodeId(currentNode.getId());
		//nodeInfo.setHeight(currentNode.getHeight());
		nodeInfo.setParentName(getParentNode(nodeName).getName());
		nodeInfo.setRootName(getRootNode(nodeName));
		return nodeInfo.toString();
	}

	@Transactional(readOnly = true)
	public List<Node> getNodeChildren(String nodeName) {
		Collection<Node> children = nodeRepository.getChildren(nodeName);
		return new ArrayList<>(children);
	}


	
	
}
