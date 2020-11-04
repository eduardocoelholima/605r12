import java.util.* ;


public class SortedStorage<E extends Comparable<E>> implements StorageInterface<E>  {

	private int size = 0;

	private class Node<E>	{
		Node()	{
		}
		Node(E element)	{
			payLoad = element;
			counter = 1;
			left = right = null;
		}
		private Node<E> left;
		private Node<E> right;
		E payLoad= null;
		int counter = 0;
	}

	Node<E> root;
	int soManyNulls = 0;

	public SortedStorage( ) {
        	root = null;
	}
	synchronized public int howManyNulls()	{
		return soManyNulls;
	}
	public boolean includesNull()	{
		return soManyNulls > 0;
	}
	synchronized public boolean delete( E element)	{
		boolean rValue = false;
		if ( element == null )	{
			if ( soManyNulls > 0 )	{
				soManyNulls --;
				rValue = true;
			} else 
				rValue = false;
		} else {
			rValue = deleteElementInTree(element);
		}
		return rValue;
	}
	private boolean deleteElementInTree(E element)	{
		boolean rValue = false;
		Node<E> aNode = null;
		if ( root == null )
			rValue = false;
		else {	
			aNode = findThisElementInTree(root, element);
			if ( aNode == null )
				rValue = false;
			else {
				aNode.counter--;
				rValue = true;
				if ( aNode.counter == 0 )	{	// the last one
					root = deleteThisElementInTree(root, element);
				}
			}
		}
		return ( rValue );
	}
	private Node<E> minimumElement(Node<E> thisNode) {
		if (thisNode.left == null)
			return thisNode;
		else {
			return minimumElement(thisNode.left);
		}
	}

	private Node<E> deleteThisElementInTree(Node<E> root, E payLoad)	{
		if ( root == null )
			return null;
		if ( root.payLoad.compareTo(payLoad) > 0 ) {	// see insert
			root.left = deleteThisElementInTree(root.left, payLoad );
		} else if ( root.payLoad.compareTo(payLoad) < 0 ) {	// see insert
			root.right = deleteThisElementInTree(root.right, payLoad );
		} else {
			if ( (root.left != null) && (root.right != null) ) {
				Node<E> tmp = root;
				Node<E> minimumNodeOnRight = minimumElement(tmp.right);
				root.payLoad = minimumNodeOnRight.payLoad;
				root.counter = minimumNodeOnRight.counter;
				root.right = deleteThisElementInTree(root.right, minimumNodeOnRight.payLoad);
			} else if (root.left != null) {
				root = root.left;
			} else if (root.right != null) {
				root = root.right;
			} else {
				root = null;
			}
		}
		return root;
	}
	synchronized public boolean find(E element)	{
/*
		return ( ( ( element == null ) && ( howManyNulls() > 0 ) )	||
			findElementInTree(root, element) );
*/
		if ( ( element == null ) && ( howManyNulls() > 0 ) )
			return true;
		else 
			return findElementInTree(root, element);
	}
	private boolean findElementInTree(Node<E> node, E element)	{
		if ( element == null )	
			return false;
		Node<E> resultNode =  findThisElementInTree(node, element);
		return  ( resultNode != null );
		
	}
	private Node<E> findThisElementInTree(Node<E> node, E element)	{
		if ( node == null )	{
			return null;
		} else if ( node.payLoad.compareTo(element) == 0)	{
			return node;
		} else if ( node.payLoad.compareTo(element) > 0 ) {	// see insert
				return findThisElementInTree(node.left, element);
		} else {
				return findThisElementInTree(node.right, element);
		}
		
	}
	synchronized public boolean add(E element)	{
		try { Thread.sleep(1000); } catch (  InterruptedException e ) { }
		boolean rValue;
		if (  find(element) )	{
			rValue = false;
		} else {
			if ( element == null )
				soManyNulls++;
			else
				addElementToTree(element);
			rValue = true;
		}
		return rValue;
	}
	private void addElementToTree(E element)  {
		if ( root == null )	{
			root = new Node<E>(element);
		} else 	{
			addThisElementToTree(root, element);
		}
        }
	private void addThisElementToTree(Node<E> node, E element)  {
		if (  node.payLoad.compareTo(element) == 0 )
			node.counter ++;
		else if ( node.payLoad.compareTo(element) > 0 )	{  // see findThisElementInTree
			if ( node.left == null )	{
				node.left = new Node<E>(element);
			} else
				addThisElementToTree(node.left, element);
		} else {
			if ( node.right == null )	{
				node.right = new Node<E>(element);
			} else
				addThisElementToTree(node.right, element);
		}
	}
	synchronized public List<E>getList()  {
		List<E> theResultsAsList = new ArrayList<E>();
		
		fillList(root, theResultsAsList);
		return theResultsAsList;
     	}
	private void fillList(Node<E> node, List<E> theResultsAsList)  {
		if ( node != null ) {
			if ( node.left != null )
				fillList(node.left, theResultsAsList);

			theResultsAsList.add(node.payLoad);

			if ( node.right != null )
				fillList(node.right, theResultsAsList);
		}
	}
	synchronized private String parse(Node<E> node)  {
		String rValue = "";
		if ( node != null ) {
			rValue = " ( ";
			if ( node.left == null )
				rValue += "l: null ";
			else
				rValue += parse(node.left) + " ";

			rValue += node.payLoad + "/" + node.counter + " ";

			if ( node.right == null )
				rValue += "r:  null ";
			else
				rValue += "r: " + parse(node.right) + " ";
			rValue = rValue + " ) ";
		}
		return rValue;
// Values stored: +l: +l: null 3/1 r:  null  5/1 r: +l: null 6/1 r:  null
	}
	synchronized public String toString()  {
		String rValue = "\n	includes so many null values = " + howManyNulls() +
		"\n	Values stored: " +  parse(root);
		return rValue;
	}

}

