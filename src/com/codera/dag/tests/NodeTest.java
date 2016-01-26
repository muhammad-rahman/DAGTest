package com.codera.dag.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import main.java.Node;

public class NodeTest {

	@Test
	public void testNodeName() {
		Node<String> drink = new Node<String>("Drink");

		assertEquals("Drink", drink.getName());
	}

	@Test
	public void testNodeAndChildLink() {
		Node<String> drink = new Node<String>("Drink");
		Node<String> sparklingDrink = new Node<String>("Sparkling Drink");

		assertFalse(drink.hasChild(sparklingDrink));

		drink.addChild(sparklingDrink);
		
		assertTrue(drink.hasChild(sparklingDrink));
	}
	
	
	@Test
	public void testAddSameChildTwice() {
		Node<String> parent = new Node<String>("Parent");
		Node<String> child = new Node<String>("Child");
		
		parent.addChild(child);
		parent.addChild(child);
		
		assertEquals(1, parent.getChildren().size() );
	}


	@Test
	public void testWrongChildNotAdded() {
		Node<String> drink = new Node<String>("Drink");
		Node<String> sparklingDrink = new Node<String>("Sparkling Drink");
		Node<String> alcoholicDrink = new Node<String>("Alcoholic Drink");
		
		drink.addChild(sparklingDrink);
		
		assertFalse(drink.hasChild(alcoholicDrink));
	}

	@Test
	public void testHasParent() {
		Node<String> drink = new Node<String>("Drink");
		Node<String> sparklingDrink = new Node<String>("Sparkling Drink");
		Node<String> beer = new Node<String>("Beer");
		
		assertFalse(beer.hasParent(sparklingDrink));

		drink.addChild(sparklingDrink);
		sparklingDrink.addChild(beer);
		
		assertTrue(beer.hasParent(sparklingDrink));
	}
	
	@Test
	public void testGetChildren() {
		Node<String> drink = new Node<String>("Drink");
		Node<String> sparklingDrink = new Node<String>("Sparkling Drink");
		Node<String> beer = new Node<String>("Beer");
		
		drink.addChild(sparklingDrink);
		drink.addChild(beer);
		
		Collection<Node<String>> expected = new ArrayList<Node<String>>(); 
		expected.add(sparklingDrink);
		expected.add(beer);
		
		Collection<Node<String>> actual = drink.getChildren() ; 
		
		assertEquals(2, actual.size());
		assertTrue(actual.contains(beer));
		assertTrue(actual.contains(sparklingDrink));
	}

	@Test
	public void testGetChildrenReturnsImmutableCollection() {
		Node<String> parent = new Node<String>("Parent");
		Node<String> child = new Node<String>("Child");
		
		parent.addChild(child);
		assertEquals(1, parent.getChildren().size() );
		
		Collection<Node<String>> children = parent.getChildren() ;
		
		try {
			children.add(new Node<String>("Grand Child"));
			fail("should have thrown an error");
		} catch (UnsupportedOperationException e) {
			//Okay. Exception expected
		}
		
		assertEquals(1, parent.getChildren().size() );
	}

	@Test
	public void testIterationOverChildNodes() {
		Node<String> drink = new Node<String>("Drink");
		Node<String> alchoholic = new Node<String>("Alchoholic");
		Node<String> wine = new Node<String>("Wine");
		 
		drink.addChild(alchoholic);
		alchoholic.addChild(wine);
		
		assertEquals(2, drink.getDescendents().size());	
	}
	
	@Test
	public void testIterationOverDeeperTreeStructure() {
		Node<String> drink = new Node<String>("Drink");
		Node<String> alchoholic = new Node<String>("Alchoholic");
		Node<String> wine = new Node<String>("Wine");
		Node<String> champagne = new Node<String>("Champagne");
		Node<String> beer = new Node<String>("Beer");
		 
		drink.addChild(alchoholic);
		alchoholic.addChild(wine);
		alchoholic.addChild(beer);
		wine.addChild(champagne);

		assertEquals(4, drink.getDescendents().size());
	}
	
	@Test
	public void testGetDescendentsWithMultipleInheritance() {
		
		Node<String> a = new Node<String>("a");
		Node<String> b = new Node<String>("b");
		Node<String> c = new Node<String>("c");
		Node<String> d = new Node<String>("d");
		
		a.addChild(b);
		a.addChild(c);
		b.addChild(d);
		c.addChild(d);
		
		assertEquals(3, a.getDescendents().size()) ;
	}
	
	@Test
	public void testAcyclic(){
		Node<String> parent = new Node<String>("Parent");
		Node<String> child = new Node<String>("Child");
		
		parent.addChild(child);
		child.addChild(parent);
		
		assertEquals(1, parent.getDescendents().size());
	}	
}
