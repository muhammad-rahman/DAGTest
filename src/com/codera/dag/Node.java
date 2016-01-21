package com.codera.dag;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Node<T> {
	private T name;
	private Collection<Node> children = new HashSet<Node>();

	public Node(T name) {
		this.name = name;
	}

	public String getName() {
		return name.toString();
	}

	public void addChild(Node child) {
		if (!this.hasParent(child)) {
			children.add(child);
		}
	}

	public boolean hasChild(Node child) {
		return children.contains(child);
	}

	public boolean hasParent(Node parent) {
		return parent.children.contains(this);
	}

	public Collection<Node> getChildren() {
		return Collections.unmodifiableCollection(children);
	}

	public Collection<Node> getDescendents() {
		Collection<Node> descendents = new HashSet<Node>();
		for (Node child : children) {
			descendents.add(child);
			Collection<Node> nodeDescendents = child.getDescendents();

			for (Node nodeDescendent : nodeDescendents) {
				descendents.add(nodeDescendent);
			}
		}

		return descendents;
	}
}
