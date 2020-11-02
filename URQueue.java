
public class URQueue<AnyType> implements Queue <AnyType> {
	private URLinkedList<AnyType> queue;
	public URQueue () {
		queue = new URLinkedList<>();
	}	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	public void enqueue(AnyType x) {
		queue.addLast(x);
	}
	public AnyType dequeue() {
		if ( queue.isEmpty())
			return null;
		return queue.pollFirst();
		}
	public AnyType peek() {
		return queue.peekFirst();
	}
	public String toString() {
		return queue.toString();	        
	}
}
