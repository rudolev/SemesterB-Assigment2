/**
 * @param <T> The type of the satellite data of the elements in the dynamic-set.
 */
public class MyDynamicSet<T> {
	/*
     * You may add any fields that you wish to add.
     * Remember that the use of built-in Java classes is not allowed,
     * the only variables types you can use are: 
     * 	-	the given classes in the assignment
     * 	-	basic arrays
     * 	-	primitive variables
     */

	// TODO - need to remove this function before the submission
	public ListLink<T> getLinkForKey(int key) {
		// TODO - chane to this line ListLink<T> current = this.minimumLink;
		ListLink<T> current = null;
		while (current != null) {
			if (current.key() == key)
				return current;
			current = current.getNext();
		}
		return null;
	}


	/**
	 * The constructor should initiate an empty dynamic-set.
	 * @param N The maximum number of elements in the dynamic set at each time.
	 */
	public MyDynamicSet(int N) {
		throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
	}
	
	public Element<T> search(int k) {
		throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
	}
	
	public void insert(Element<T> x) {
		throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
	}
	
	public void delete(Element<T> x) {
		throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
	}
	
	public Element<T> minimum() {
		throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
	}
	
	public Element<T> maximum() {
		throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
	}
	
	public Element<T> successor(Element<T> x) {
		throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
	}
	
	public Element<T> predecessor(Element<T> x) {
		throw new UnsupportedOperationException("Delete this line and replace it with your implementation");
	}
}
