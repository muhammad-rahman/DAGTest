package co.uk.codera.dag;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Node<T> {
	private T name;
	private Collection<Node<T>> children = new HashSet<Node<T>>();

	public Node(T name) {
		this.name = name;
	}

	public T getName() {
		return name;
	}

	public void addChild(Node<T> child) {
		if (!this.hasParent(child)) {
			children.add(child);
		}
	}

	public boolean hasChild(Node<T> child) {
		return children.contains(child);
	}

	public boolean hasParent(Node<T> parent) {
		return parent.children.contains(this);
	}

	public Collection<Node<T>> getChildren() {
		return Collections.unmodifiableCollection(children);
	}

	public Collection<Node<T>> getDescendents() {
		Collection<Node<T>> descendents = new HashSet<Node<T>>();
		for (Node<T> child : children) {
			descendents.add(child);
			Collection<Node<T>> nodeDescendents = child.getDescendents();

			for (Node<T> nodeDescendent : nodeDescendents) {
				descendents.add(nodeDescendent);
			}
		}

		return descendents;
	}
}
