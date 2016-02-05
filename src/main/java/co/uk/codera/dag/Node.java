package co.uk.codera.dag;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Node<T> {
	private T data;
	
	private Collection<Node<T>> children = new HashSet<Node<T>>();

	public Node(T data) {
		this.data = data;
	}

	public T getName() {
		return data;
	}

	public void addChild(Node<T> child) {
		if (hasAncestor(child, this)) {
			throw new RuntimeException("Voliates acyclic rules");
		} 
		children.add(child);
	}

	public boolean hasChild(Node<T> child) {
		return children.contains(child);
	}

	public boolean hasAncestor(Node<T> potentialAncestor, Node<T> potentialDesendents) {
		return potentialAncestor.getDescendents().contains(potentialDesendents);
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
			descendents.addAll(child.getDescendents());
		}

		return descendents;
	}
}
