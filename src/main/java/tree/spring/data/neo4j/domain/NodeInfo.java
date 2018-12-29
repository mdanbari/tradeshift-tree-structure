package tree.spring.data.neo4j.domain;

public class NodeInfo {

	private Long nodeId;
	private String rootName;
	private String parentName;
	private Integer height;

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "NodeInfo{" + "id = " + nodeId + ", rootNode = '" + rootName + '\'' + ", parentNode = '" + parentName
				+ '\'' + ", height = '" + height + '\'' + '}';
	}

}
