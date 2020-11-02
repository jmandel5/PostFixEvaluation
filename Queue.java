public interface Queue<AnyType> {
	public boolean isEmpty();
	public void enqueue(AnyType x);
	public AnyType dequeue();
	public AnyType peek();
}