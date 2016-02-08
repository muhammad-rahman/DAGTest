package co.uk.codera.dag;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DAG<T> {
	private String graphName;

	private Map<T, Set<T>> map = new HashMap<T, Set<T>>();

	public DAG(String graphName) {
		this.graphName = graphName;
	}

	public String getGraphName() {
		return graphName;
	}

	public void add(T parent, Set<T> childSet) {
		for(T child: childSet) {
			if(this.getDescendants(child).contains(parent) || child.equals(parent)){
				throw new RuntimeException("Invalid relationship");
			}
		}
		map.put(parent, childSet);
	}

	public Set<T> getChildren(T parent) {
		if (!mapContainsKey(parent)) {
			throw new RuntimeException("Parent " + parent + " doesn't exsist");
		}
		return Collections.unmodifiableSet(map.get(parent));
	}

	public boolean hasParent(T parent, T child) {
		if (!mapContainsKey(parent)) {
			throw new RuntimeException("Parent doesn't exsist");
		}
		return map.get(parent).contains(child);
	}

	public boolean mapContainsKey(T key) {
		return map.containsKey(key);
	}
	
	public Set<T> getDescendants(T parent){
		Set<T> children = new HashSet<T>();
		Set<T> descendants = new HashSet<T>();

		if(mapContainsKey(parent)) {
			children = this.getChildren(parent);
		}
		
		for (T child: children) {
			descendants.add(child);
			descendants.addAll(this.getDescendants(child));
		}
		return descendants;
	}
}
