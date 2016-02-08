package co.uk.codera.dag;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DAGTest {
	private DAG<String> graph;
	
	@Before
	public void setUp(){
		graph = new DAG<String>("Direct Acyclic Graph Test");
	}
	
	@Test
	public void testCreateGraphWithName() {
		assertEquals("Direct Acyclic Graph Test", graph.getGraphName());
	}

	@Test
	public void testCreateParentWithNoChild() {
		Set<String> emptySet = new HashSet<String>();
		graph.add("parent", emptySet);

		assertEquals(emptySet, graph.getChildren("parent"));
	}

	@Test
	public void testCreateParentWithChild() {
		Set<String> parentDescendantSet = new HashSet<String>();
		parentDescendantSet.add("child");
		graph.add("parent", parentDescendantSet);

		assertEquals(parentDescendantSet, graph.getChildren("parent"));
	}

	@Test
	public void testImmutableChildMapReturn() {
		Set<String> parentDescendantSet = new HashSet<String>();

		parentDescendantSet.add("child");
		graph.add("parent", parentDescendantSet);

		Set<String> immutableList = graph.getChildren("parent");

		try {
			immutableList.add("failedChild");
			fail("Expected to fail");
		} catch (UnsupportedOperationException e) {
			// Expected
		}
	}

	@Test
	public void testParentNodeDoesNotAddChildTwice() {
		Set<String> parentDescendantSet = new HashSet<String>();

		parentDescendantSet.add("child");
		parentDescendantSet.add("child");

		graph.add("parent", parentDescendantSet);

		assertEquals(1, graph.getChildren("parent").size());
	}

	@Test
	public void testGetChildrenOnParentThatDoesNotExsist() {
		try {
			graph.getChildren("parent");
			fail("Should fail as parent and child doesn't exsist");
		} catch (RuntimeException e) {
			// Expected
		}
	}

	@Test
	public void testHasParent() {
		Set<String> parentDescendantSet = new HashSet<String>();

		parentDescendantSet.add("child");

		graph.add("parent", parentDescendantSet);

		assertTrue(graph.hasParent("parent", "child"));
	}

	@Test
	public void testWithWrongParent() {
		Set<String> parentDescendantSet = new HashSet<String>();

		parentDescendantSet.add("child");
		graph.add("parent", parentDescendantSet);

		Set<String> emptySet = new HashSet<String>();
		graph.add("stranger", emptySet);

		assertFalse(graph.hasParent("stranger", "child"));
	}

	@Test
	public void testWithParentThatDoesNotExsist() {
		Set<String> parentDescendantSet = new HashSet<String>();

		parentDescendantSet.add("child");
		graph.add("parent", parentDescendantSet);

		try {
			graph.hasParent("stranger", "child");
			fail("Should fail as child has to relation to stranger");
		} catch (RuntimeException e) {
			// Expected
		}
	}

	@Test
	public void testDescendantsWithSimpleStructure() {
		Set<String> parentDescendantSet = new HashSet<String>();
		Set<String> childDescendantSet = new HashSet<String>();

		parentDescendantSet.add("child");
		childDescendantSet.add("grandChild");

		graph.add("parent", parentDescendantSet);
		graph.add("child", childDescendantSet);

		assertEquals(2, graph.getDescendants("parent").size());
	}

	@Test
	public void testDescendantsWithDeeperTreeStructure() {
		Set<String> parentDescendantSet = new HashSet<String>();
		Set<String> childDescendantSet = new HashSet<String>();
		Set<String> grandchildDescendantSet = new HashSet<String>();

		parentDescendantSet.add("child");
		graph.add("parent", parentDescendantSet);

		childDescendantSet.add("grandchildOne");
		childDescendantSet.add("grandchildTwo");
		graph.add("child", childDescendantSet);

		grandchildDescendantSet.add("greatGrandchild");
		graph.add("grandchildOne", grandchildDescendantSet);

		assertEquals(4, graph.getDescendants("parent").size());
	}

	@Test
	public void testMultipleInheritence() {
		Set<String> aDescendantSet = new HashSet<String>();
		Set<String> bDescendantSet = new HashSet<String>();
		Set<String> cDescendantSet = new HashSet<String>();

		aDescendantSet.add("Node B");
		aDescendantSet.add("Node C");
		graph.add("Node A", aDescendantSet);

		bDescendantSet.add("Node D");
		graph.add("Node B", bDescendantSet);

		cDescendantSet.add("Node D");
		graph.add("Node C", bDescendantSet);

		assertEquals(3, graph.getDescendants("Node A").size());
	}
	
	@Test
	public void testGetDescendentsWithNoDescendents(){		
		assertEquals(0, graph.getDescendants("parent").size());
	}

	@Test
	public void testBasicAcyclicLoop() {		
		Set<String> parentDescendantSet = new HashSet<String>();
		Set<String> childDescendantSet = new HashSet<String>();
		
		parentDescendantSet.add("child");
		graph.add("parent", parentDescendantSet);
		
		childDescendantSet.add("parent");
		
		try {
			graph.add("child", childDescendantSet);		
			fail("Should not allow to add parent as child");
		} catch (RuntimeException e) {
			//Expected
		}
			
		assertEquals(0, graph.getDescendants("child").size());
	}
	
	@Test 
	public void testDeeperAcyclicLoop(){		
		Set<String> parentDescendantSet = new HashSet<String>();
		Set<String> childDescendantSet = new HashSet<String>();
		Set<String> grandchildDescendantSet = new HashSet<String>();
		
		parentDescendantSet.add("child");
		graph.add("parent", parentDescendantSet);
		
		childDescendantSet.add("grandchild");
		graph.add("child", childDescendantSet);
		
		grandchildDescendantSet.add("parent");
		
		try {
			graph.add("grandchild", grandchildDescendantSet);		
			fail("Should not allow to add grandparent as child");
		} catch (RuntimeException e) {
			//Expected
		}
			
		assertEquals(0, graph.getDescendants("grandchild").size());
	}
	
	@Test
	public void testDoNotAllowToAddItSelf(){
		Set<String> parentDescendantSet = new HashSet<String>();
		parentDescendantSet.add("parent");
	
		try {
			graph.add("parent", parentDescendantSet);	
			fail("Should not allow to add parent to itself");
		} catch (RuntimeException e) {
			//Expected
		}
			
		assertEquals(0, graph.getDescendants("parent").size());
	}

	@Test
	public void testWithNumberNodes(){
		DAG<Integer> graph = new DAG<Integer>("DAG with numerical nodes");

		Set<Integer> parentDescendantSet = new HashSet<Integer>();
		parentDescendantSet.add(2);
		
		graph.add(1, parentDescendantSet);
		
		assertEquals(1, graph.getDescendants(1).size());
	}
}
