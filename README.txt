NAME: Jack Mandell
CONTACT INFO: jmandel5@u.rochester.edu
FILES: Stack.java, URLinkedList.java, URList.java, URNode.java, URQueue.java, Queue.java, URStack.java, PostFix.java, ExtraTestCases.txt, infix_expr_short.txt

HOW TO RUN PROGRAM:

The Postfix evaluation and conversion takes place all in a single java file called "PostFix.java". This program takes in a text file of expressions where each line is a single expression and outputs the results onto a file named "jmandel5_Project1_OUTPUT". On the command line, this can be written for example as: java PostFix.java infix_expr_short.txt. In the expressions, there must be a single space between every token (a negative number counts as one token). For example, here are some expressions:

( 6 - 7 ) + 4 * -3 - sin 0
( 45 % 6 ) - 6 ^ 0

I have attached a file called "ExtraTestCases.txt" to give examples of formatted expressions and to show the capabilities of my methods. I have also implemented all the extra operators included for the extra credit, and this file shows this. 

The results of this extra file should be:

4.0
0.9999999999999999
-1.0
1.0
0.25
0.8414709848078965
-0.8
0.0
0.0
1.0
0.0

ALGORITHM DESCRIPRTION:

PostFox.java contains three methods: 
public static URQueue<String> postFixConversion(String p);
public static double postFixEvaluation(URQueue(String q);
public static int operatorPrecedence(String a, String b);

postFixConversion() takes in a String expression and returns a URQueue of String tokens in the order that abides by the syntax of postfix evaluation. This method works by identifying tokens by the space between them. This part of the code was the hardest to figure out. I needed a way to locate the tokens within the String. The only solution I could think of with time I had was to have a space between all tokens. Once a token is found, it is tested to see if it is a subset of the static variable "operators" (a concatenation of all possible operators) or is a paranthesis. If not, it is added to the queue. The code then has a series of if-else if statements to test for the different scenarios that could happen. For examaple: if the top of the stack is a paranthesis, or if the stack is empty, or if the top of the stack is an operator of higher precendece than the token operator. This is repeated until the while loop has traversed the entire input String.

operatorPrecedence() takes in two operators (as Strings) and returns a positive number if the first operator is of higher precedence than the second, 0 if they are the same precedence, and a negative number if the first one is of lower precedence. In the method, each operator is assigned an integer value, a higher integer value implies a higher precendece. The code then returns the difference between these two numbers. 

postFixEvaluation() takes in a URQueue<String> and returns the executed expression value. For this method, I could not think of a more efficient way of implementing it except having an if statement for every possible operator type, then popping the top two tokens (top one for !, sin, cos, tan) and computing the operation. I converted the tokens into doubles to compute, then pushed the result back on as a String. 