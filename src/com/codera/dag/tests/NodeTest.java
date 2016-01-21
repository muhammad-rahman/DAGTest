package com.codera.dag.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.codera.dag.Node;

public class NodeTest {

	@Test
	public void testNodeName() {
		Node drink = new Node("Drink");

		assertEquals("Drink", drink.getName());
	}
	
	@Test
	public void testNodeAndChildLink() {
		Node drink = new Node("Drink");
		Node sparklingDrink = new Node("Sparkling Drink");

		assertFalse(drink.hasChild(sparklingDrink));

		drink.addChild(sparklingDrink);
		
		assertTrue(drink.hasChild(sparklingDrink));
	}
	
	
	@Test
	public void testAddSameChildTwice() {
		Node parent = new Node("Parent");
		Node child = new Node("Child");
		
		parent.addChild(child);
		parent.addChild(child);
		
		assertEquals(1, parent.getChildren().size() );
	}


	@Test
	public void testWrongChildNotAdded() {
		Node drink = new Node("Drink");
		Node sparklingDrink = new Node("Sparkling Drink");
		Node alcoholicDrink = new Node("Alcoholic Drink");
		
		drink.addChild(sparklingDrink);
		
		assertFalse(drink.hasChild(alcoholicDrink));
	}

	@Test
	public void testHasParent() {
		Node drink = new Node("Drink");
		Node sparklingDrink = new Node("Sparkling Drink");
		Node beer = new Node("Beer");
		
		assertFalse(beer.hasParent(sparklingDrink));

		drink.addChild(sparklingDrink);
		sparklingDrink.addChild(beer);
		
		assertTrue(beer.hasParent(sparklingDrink));
	}
	
	@Test
	public void testGetChildren() {
		Node drink = new Node("Drink");
		Node sparklingDrink = new Node("Sparkling Drink");
		Node beer = new Node("Beer");
		
		drink.addChild(sparklingDrink);
		drink.addChild(beer);
		
		Collection<Node> expected = new ArrayList<Node>(); 
		expected.add(sparklingDrink);
		expected.add(beer);
		
		Collection<Node> actual = drink.getChildren() ; 
		
		assertEquals(2, actual.size());
		assertTrue(actual.contains(beer));
		assertTrue(actual.contains(sparklingDrink));
	}

	@Test
	public void testGetChildrenReturnsImmutableCollection() {
		Node parent = new Node("Parent");
		Node child = new Node("Child");
		
		parent.addChild(child);
		assertEquals(1, parent.getChildren().size() );
		
		Collection<Node> children = parent.getChildren() ;
		
		try {
			children.add(new Node("Grand Child"));
			fail("should have thrown an error");
		} catch (UnsupportedOperationException e) {
			//Okay. Exception expected
		}
		
		assertEquals(1, parent.getChildren().size() );
	}

	@Test
	public void testIterationOverChildNodes() {
		Node drink = new Node("Drink");
		Node alchoholic = new Node("Alchoholic");
		Node wine = new Node("Wine");
		 
		drink.addChild(alchoholic);
		alchoholic.addChild(wine);
		
		assertEquals(2, drink.getDescendents().size());	
	}
	
	@Test
	public void testIterationOverDeeperTreeStructure() {
		Node drink = new Node("Drink");
		Node alchoholic = new Node("Alchoholic");
		Node wine = new Node("Wine");
		Node champagne = new Node("Champagne");
		Node beer = new Node("Beer");
		 
		drink.addChild(alchoholic);
		alchoholic.addChild(wine);
		alchoholic.addChild(beer);
		wine.addChild(champagne);

		assertEquals(4, drink.getDescendents().size());
	}
	
	@Test
	public void testGetDescendentsWithMultipleInheritance() {
		
		Node a = new Node("a");
		Node b = new Node("b");
		Node c = new Node("c");
		Node d = new Node("d");
		
		a.addChild(b);
		a.addChild(c);
		b.addChild(d);
		c.addChild(d);
		
		assertEquals(3, a.getDescendents().size()) ;
	}
	
	@Test
	public void testAcyclic(){
		Node parent = new Node("Parent");
		Node child = new Node("Child");
		
		parent.addChild(child);
		child.addChild(parent);
		
		assertEquals(1, parent.getDescendents().size());
	}	
}
