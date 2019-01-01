package tree.spring.data.neo4j.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tree.spring.data.neo4j.domain.Node;
import tree.spring.data.neo4j.services.NodeService;


@RestController
@RequestMapping("/")
public class NodeController {

	private final NodeService nodeService;
	
	public NodeController(NodeService nodeService) {
		this.nodeService = nodeService;
	}
	
	@GetMapping("/changeParentNode")
	public void changeParentNode(@RequestParam(value = "nodeName") String nodeName,@RequestParam(value = "newParentName") String newParentName) {
		nodeService.changeParentNode(nodeName,newParentName);
	}
	
	@GetMapping("/calculateHeight")
	public int calculateDepth(@RequestParam(value = "nodeName") String nodeName) {
		return nodeService.calculateHeight(nodeName);
	}
	
	@GetMapping("/getRootNode")
	public String getRootNode(@RequestParam(value = "nodeName") String nodeName) {
		return nodeService.getRootNode(nodeName);
	}
	
	@GetMapping("/populateDB")
	public void populateDB(@RequestParam(value = "nodeNum") Integer nodeNum) {
		nodeService.generateRandomBinaryTree(nodeNum);
	}

	@GetMapping("/getNodeInfo")
	public String getNodeInfo(@RequestParam(value = "nodeName") String nodeName) {
		return nodeService.getNodeInfo(nodeName);
	}
	
	@GetMapping("/getNodeChildren")
	public List<Node> getNodeChildren(@RequestParam(value = "nodeName") String nodeName) {
		return nodeService.getNodeChildren(nodeName);
	}


}
