/**
 * @param <T> The type of the satellite data of the elements in the data structure.
 */
public class MyFirstDataStructure<T> {
	/*
     * You may add any fields that you wish to add.
     * Remember that the use of built-in Java classes is not allowed,
     * the only variables types you can use are:
     * 	-	the given classes in the assignment
     * 	-	basic arrays
     * 	-	primitive variables
     */

	private MyAVLTree<T> byKeyTree = null;
	private MyAVLTree<T> byInsertOrderTree = null;
	private Element<T> maxElement = null;
	private Element<T> first = null;
	private Element<T> last = null;
	private int insertCounter = 0;

	/***
     * This function is the Init function.
	 * @param N The maximum number of elements in the data structure at each time.
     */
	public MyFirstDataStructure(int N) {
		byKeyTree = new MyAVLTree<>();
		byInsertOrderTree = new MyAVLTree<>();
	}
	
	public void insert(Element<T> x) {
		TreeNode<T> elem = new TreeNode<>(x);
		TreeNode<T> elemByOrder = new TreeNode<>(insertCounter, x.satelliteData());

		elemByOrder.setLinkedNode(elem);
		elem.setLinkedNode(elemByOrder);

		byKeyTree.insert(elem);
		byInsertOrderTree.insert(elemByOrder);

		if (maxElement == null)
			maxElement = elem;
		else {
			if (maxElement.key() < elem.key()) {
				maxElement = elem;
			}
		}

		if (first == null)
			first = elem;

		last = elem;
	}
	
	public void findAndRemove(int k) {
		TreeNode<T> toRemoveByKey = byKeyTree.search(k);
		byKeyTree.delete(toRemoveByKey);
		maxElement = byKeyTree.rightMostNode();

		byInsertOrderTree.delete(toRemoveByKey.getLinkedNode());

		first = byInsertOrderTree.leftMostNode().getLinkedNode();
		last = byInsertOrderTree.rightMostNode().getLinkedNode();
	}

	public Element<T> maximum() {
		return maxElement;
	}

	public Element<T> first() {
		return first;
	}

	public Element<T> last() {
		return last;
	}

}
