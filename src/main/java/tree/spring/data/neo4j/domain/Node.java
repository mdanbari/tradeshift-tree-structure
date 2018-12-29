package tree.spring.data.neo4j.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Node {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	//private Integer height;

	@Relationship(type = "child_rel", direction = Relationship.OUTGOING)
	private List<Node> children;

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public Node(String name) {
		this.name = name;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addChild(Node child) {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		this.children.add(child);
	}

	public List<Node> getChildren() {
		return children;
	}

//	public Integer getHeight() {
//		return height;
//	}
//
//	public void setHeight(Integer height) {
//		this.height = height;
//	}

}
