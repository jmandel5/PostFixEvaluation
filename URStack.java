
public class URStack<AnyType> implements Stack<AnyType> {
	private URLinkedList<AnyType> stack;
    public URStack()
   {
       stack = new URLinkedList();
   }
   public boolean isEmpty() {
       return stack.isEmpty();
   }
   public void push(AnyType x) {
       stack.addFirst(x);
   }
   public AnyType pop() {
       if ( stack.isEmpty() ) {
           return null;   
       }
       return stack.pollFirst();
   }
   public AnyType peek() {
      return stack.peekFirst();  
   }
   public String toString() {
       return stack.toString();
   }
}
