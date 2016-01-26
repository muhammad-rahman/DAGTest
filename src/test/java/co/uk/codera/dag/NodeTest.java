package co.uk.codera.dag;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import co.uk.codera.dag.Node;

public class NodeTest {

	@Test
	public void testNodeName() {
		Node<String> node = new Node<String>("node");

		assertEquals("node", node.getName());
	}

	@Test
	public void testNodeAndChildLink() {
		Node<String> parent = new Node<String>("parent");
		Node<String> child = new Node<String>("child");

		assertFalse(parent.hasChild(child));

		parent.addChild(child);

		assertTrue(parent.hasChild(child));
	}

	@Test
	public void testAddSameChildTwice() {
		Node<String> parent = new Node<String>("parent");
		Node<String> child = new Node<String>("child");

		parent.addChild(child);
		parent.addChild(child);

		assertEquals(1, parent.getChildren().size());
	}

	@Test
	public void testWrongChildNotAdded() {
		Node<String> parent = new Node<String>("parent");
		Node<String> childOne = new Node<String>("child one");
		Node<String> childTwo = new Node<String>("child two");

		parent.addChild(childOne);

		assertFalse(parent.hasChild(childTwo));
	}

	@Test
	public void testHasParent() {
		Node<String> parent = new Node<String>("parent");
		Node<String> child = new Node<String>("child");

		assertFalse(child.hasParent(parent));

		parent.addChild(child);

		assertTrue(child.hasParent(parent));
	}

	@Test
	public void testGetChildren() {
		Node<String> parent = new Node<String>("parent");
		Node<String> childOne = new Node<String>("first child");
		Node<String> childTwo = new Node<String>("second child");

		parent.addChild(childOne);
		parent.addChild(childTwo);

		Collection<Node<String>> expected = new ArrayList<Node<String>>();
		expected.add(childOne);
		expected.add(childTwo);

		Collection<Node<String>> actual = parent.getChildren();

		assertEquals(2, actual.size());
		assertTrue(actual.contains(childOne));
		assertTrue(actual.contains(childTwo));
	}

	@Test
	public void testGetChildrenReturnsImmutableCollection() {
		Node<String> parent = new Node<String>("parent");
		Node<String> child = new Node<String>("child");

		parent.addChild(child);
		assertEquals(1, parent.getChildren().size());

		Collection<Node<String>> children = parent.getChildren();

		try {
			children.add(new Node<String>("Grand Child"));
			fail("should have thrown an error");
		} catch (UnsupportedOperationException e) {
			// Okay. Exception expected
		}

		assertEquals(1, parent.getChildren().size());
	}

	@Test
	public void testIterationOverChildNodes() {
		Node<String> parent = new Node<String>("parent");
		Node<String> child = new Node<String>("child");
		Node<String> grandChild = new Node<String>("grandchild");

		parent.addChild(child);
		child.addChild(grandChild);

		assertEquals(2, parent.getDescendents().size());
	}

	@Test
	public void testIterationOverDeeperTreeStructure() {
		Node<String> drink = new Node<String>("drink");
		Node<String> alchoholic = new Node<String>("alchoholic");
		Node<String> wine = new Node<String>("wine");
		Node<String> champagne = new Node<String>("champagne");
		Node<String> beer = new Node<String>("ceer");

		drink.addChild(alchoholic);
		alchoholic.addChild(wine);
		alchoholic.addChild(beer);
		wine.addChild(champagne);

		assertEquals(4, drink.getDescendents().size());
	}

	@Test
	public void testGetDescendentsWithMultipleInheritance() {

		Node<String> nodeA = new Node<String>("node a");
		Node<String> nodeB = new Node<String>("node b");
		Node<String> nodeC = new Node<String>("node c");
		Node<String> nodeD = new Node<String>("node d");

		nodeA.addChild(nodeB);
		nodeA.addChild(nodeC);
		nodeB.addChild(nodeD);
		nodeC.addChild(nodeD);

		assertEquals(3, nodeA.getDescendents().size());
	}

	@Test
	public void testAcyclic() {
		Node<String> parent = new Node<String>("parent");
		Node<String> child = new Node<String>("child");

		parent.addChild(child);
		
		try {
			child.addChild(parent);
			fail("should have thrown an error");
		} catch (RuntimeException e) {
			// Okay. Exception expected
		}

		assertEquals(0, child.getDescendents().size());
	}
	
	@Test
	public void testDeeperAcyclic() {
		Node<String> parent = new Node<String>("parent");
		Node<String> child = new Node<String>("child");
		Node<String> grandChild = new Node<String>("grandChild");
		
		parent.addChild(child);
		child.addChild(grandChild);
		
		try {
			grandChild.addChild(parent);
			fail("should have thrown an error");
		} catch (RuntimeException e) {
			// Okay. Exception expected
		}

		assertEquals(0, grandChild.getDescendents().size());
	}
}
