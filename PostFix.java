import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
public class PostFix {
	public static final String operators = "*+-/%<>=|&!^sincostan";
	
	public static void main ( String args[] ) throws IOException {        
		File file = new File(args[0]); 
		Double result = null;
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		BufferedWriter writer = new BufferedWriter(new FileWriter("jmandel5_Project1_OUTPUT.txt"));
		String st; 
		while ((st = br.readLine()) != null) {
			result = postFixEvaluation(postFixConversion(st));
			writer.write(Double.toString(result));
			writer.write("\n");
		}
		writer.close();
	}
	public static URQueue<String> postFixConversion ( String p) {
		p = p + " ";
		URQueue<String> q = new URQueue<>();
		URStack<String> s = new URStack<>();
		int j = 0;
		int i = 0;
		String token;
		while( j != p.length() - 1 )  {		
			j = i + 1;
			while (  ! p.substring(j,j+1).equals(" ")) {
				j++;
			}
			token = p.substring(i,j);
			
			//Enqueues the number tokens
			if (operators.indexOf(token) < 0 && ! token.equals("(") && ! token.equals(")") ) {
				q.enqueue(token);
			}
			//Push any operator if the stack is empty 
			else if ( s.isEmpty()) {
				s.push(token);
			}
			//Push open parenthesis only if the top of stack is not a closed parenthesis 
			//or push token when the top of the stack is open parenthesis
			else if ( s.peek().equals("(") && ! token.equals(")") || token.equals("(")) {
				s.push(token);
			} 
			 //Pop all operators until an open parenthesis is hit
			else if (token.equals(")")) { 
				while ( ! s.peek().equals("(") ) { 		
					q.enqueue(s.pop());
				}
				s.pop(); 
				
			} 				
			//If the operator is of lower precedence, it enqueues the operators until it finds one
			// of lower precendence
			else if (operatorPrecedence(token,s.peek()) < 0) {
				while (! s.isEmpty() && operatorPrecedence(token,s.peek()) < 0)
				q.enqueue(s.pop());
				s.push(token);
			}
			//If the token is an operator of higher precedence originally, the token is just 
			//pushed onto the stack
			else {
				s.push(token);
			}
			i = j +1;
		}
		while ( ! s.isEmpty() )
			q.enqueue(s.pop());
		return  q;
	}
	//returns a positive number if operator a is of higher precedence than operator b
	public static int operatorPrecedence(String a, String b) {
		int aValue = 0, bValue = 0;
		if ( a.equals("!")) 
			aValue = 8;
		else if ( a.equals("^")) 
			aValue = 7;
		else if ( a.equals("sin") || a.equals("cos") || a.contentEquals("tan"))
			aValue = 6;
		else if ( a.equals("*") || a.equals("/") || a.equals("%")  )
			aValue = 5;
		else if ( a.equals("+") || a.equals("-")  )
			aValue = 4;
		else if ( a.equals("<") || a.equals(">") ) 	
			aValue = 3;
		else if ( a.equals("=") )
			aValue = 2;
		else if ( a.equals("&"))
			aValue = 1;
		
		if (b.equals("!"))
			bValue = 8;
		else if ( b.equals("^")) 
			bValue = 7;
		else if ( b.equals("sin") || b.equals("cos") || b.contentEquals("tan"))
			bValue = 6;
		else if ( b.equals("*") || b.equals("/") || b.equals("%")  )
			bValue = 5;
		else if ( b.equals("+") || b.equals("-")  )
			bValue = 4;
		else if ( b.equals("<") || b.equals(">") ) 	
			bValue = 3; 
		else if ( b.equals("=") || b.equals("!"))
			bValue = 2;
		else if ( b.equals("&"))
			bValue = 1;
		
		return aValue - bValue;
	}
	public static double postFixEvaluation (URQueue<String> q ) {
		String token;
		String first , second;
		URStack<String> s = new URStack<>();
		while ( ! q.isEmpty() ) {
			token = q.dequeue();
			if ( operators.indexOf(token) < 0) {
				s.push(token);
			}
			else if ( token.equals("^") ){
				first = s.pop();
				second = s.pop();
				s.push(Double.toString( Math.pow(Double.parseDouble(second), Double.parseDouble(first) ) )  );
			}
			else if ( token.equals("*") ){
				first = s.pop();
				second = s.pop();
				s.push(Double.toString( Double.parseDouble(second) * Double.parseDouble(first) )   );
			}
			else if ( token.equals("/") ){
				first = s.pop();
				second = s.pop();
				s.push(Double.toString( Double.parseDouble(second) / Double.parseDouble(first) )   );
			}
			else if ( token.equals("%") ){
				first = s.pop();
				second = s.pop();
				s.push(Double.toString( Double.parseDouble(second) % Double.parseDouble(first) )   );
			}
			else if ( token.equals("+") ){
				first = s.pop();
				second = s.pop();
				s.push(Double.toString( Double.parseDouble(second) + Double.parseDouble(first) )   );
			}
			else if ( token.equals("-") ){
				first = s.pop();
				second = s.pop();
				s.push(Double.toString( Double.parseDouble(second) - Double.parseDouble(first) )   );
			}	
			else if ( token.equals(">")) {
				first = s.pop();
				second = s.pop();
				if (  Double.parseDouble(second) > Double.parseDouble(first) ) 
					s.push(Double.toString(1));
				else 
					s.push(Double.toString(0));
			}
			else if ( token.equals("<")) {
				first = s.pop();
				second = s.pop();
				if (  Double.parseDouble(second) < Double.parseDouble(first) ) 
					s.push(Double.toString(1));
				else 
					s.push(Double.toString(0));
			}
			else if ( token.equals("=")) {
				first = s.pop();
				second = s.pop();
				if (  Double.parseDouble(second) == Double.parseDouble(first) ) 
					s.push(Double.toString(1));
				else 
					s.push(Double.toString(0));
			}
			else if ( token.equals("&")) {
				first = s.pop();
				second = s.pop();
				if (  Double.parseDouble(second) + Double.parseDouble(first) == 2 ) 
					s.push(Double.toString(1));
				else 
					s.push(Double.toString(0));
			}
			else if ( token.equals("!")) {
				first = s.pop();
				if (  Double.parseDouble(first) == 1 ) 
					s.push(Double.toString(0));
				else 
					s.push(Double.toString(1));
			}
			else if ( token.equals("|")) {
				first = s.pop();
				second = s.pop();
				if (  Double.parseDouble(second) + Double.parseDouble(first) > 0 ) 
					s.push(Double.toString(1));
				else 
					s.push(Double.toString(0));
			}
			else if ( token.equals("sin")) {
				first = s.pop();
				s.push(Double.toString( Math.sin(Double.parseDouble(first))  ) );
			}
			else if ( token.equals("cos")) {
				first = s.pop();
				s.push(Double.toString( Math.cos(Double.parseDouble(first))  ) );
			}
			else if ( token.equals("tan")) {
				first = s.pop();
				s.push(Double.toString( Math.tan(Double.parseDouble(first))  ) );
			}
		}
			
		return Double.parseDouble(s.pop());
	}
}

