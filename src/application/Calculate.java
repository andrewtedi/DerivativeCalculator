package application;

import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

public class Calculate {
	
	private static Queue<String> queue;
	private static Queue<String> derivedQueue;
	
	private static final char[] acceptableCharacters = 
			new char[]{'a', 'c', 'e', 'i', 'l', 'n', 'o', 'p', 'q', 'r', 's', 't', 'x', '+', '-', '*', '/', '^', '(', ')', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private static Stack<Character> parenthesesStack;
	
	private static RuleHandler rh;
	
	public Calculate() {
		queue = new LinkedList<String>();
		derivedQueue = new LinkedList<String>();
		parenthesesStack = new Stack<Character>();
		rh = new RuleHandler();
	}
	
	//method to call from outside
	public String calculate(String input) throws Exception {
		queue.clear();
		derivedQueue.clear();
		String output = "";
		// try and catch block for returning errors
		try {
			addToQueue(input);
			deriveTerms();
			output = createOutput();
		}
		catch(NumberFormatException e) {
			if(e.getMessage().isEmpty())
				throw new Exception("Bad input: Formatting error");
			else
				throw new Exception(e.getMessage());
		}
		catch(StringIndexOutOfBoundsException e) {
			throw new Exception("Bad input: Formatting error");
		}
		catch(ArithmeticException e) {
			throw new Exception("Bad input: Cannot divide by 0");
		}
		catch(Exception e) {
			throw e;
		}
		return output;
	}
	
	// adds all terms in the input to a queue
	// examples include "tan(x)", "3x^2", "+", "-", 24
	private void addToQueue(String input) throws Exception {
		if(input.length() == 0)
			throw new Exception("Bad input: Empty input");
		if(input.length() > 20)
			throw new Exception("Bad input: Exceeds 20 characters");
		// remove spaces from function to ease adding terms
		input = input.replaceAll(" ", "");
		if(input.contains("/0"))
			throw new ArithmeticException();
		boolean addedOperator = false;
		int start = 0;
		// iterate through function to create terms
		for(int i = 0; i < input.length(); i++) {
			if(!isAcceptable(input.charAt(i)))
				throw new Exception("Bad input: contains invalid character");
			if(input.charAt(i) == '(')
				parenthesesStack.push('(');
			else if(input.charAt(i) == ')') {
                if(parenthesesStack.empty())
                	throw new Exception("Bad input: Doesn't have a beginning parentheses");
                if(input.charAt(i - 1) == '(')
                	throw new Exception("Bad input: Nothing inside parentheses");
                if(parenthesesStack.peek() == '(')
                	parenthesesStack.pop();
                else
                	throw new Exception("Bad input: Doesn't have an ending parentheses");
            }
			// use operands to split terms if the operand is outside parentheses
			if(input.charAt(i) == '+' || (input.charAt(i) == '-' && (i != 0 && input.charAt(i-1) != '^'))) {
				if(addedOperator)
					throw new Exception("Bad input: Continuous operators");
				if(parenthesesStack.empty()) {
					queue.add(input.substring(start, i));
					start = i;
					queue.add(input.substring(start, i+1));
					start++;
					addedOperator = true;
				}
				else
					addedOperator = false;
			}
			else if(input.charAt(i) == '*' || input.charAt(i) == '/') {
				if(addedOperator)
					throw new Exception("Bad input: Continuous operators");
				addedOperator = true;
			}
			else
				addedOperator = false;
		}
		queue.add(input.substring(start, input.length()));
		// check to see if equation has extra parentheses or operator
		if(!parenthesesStack.empty() || addedOperator)
			throw new Exception("Bad input: Doesn't have an ending parentheses or ends with an operator");
	}
	
	// determines if the character in the input is acceptable
	private boolean isAcceptable(char character) {
		for(int i = 0; i < acceptableCharacters.length; i++) {
			if(character == acceptableCharacters[i])
				return true;
		}
		return false;
	}
	
	// use RuleHandler to derive terms in the queue
	private void deriveTerms() throws Exception {
		while(!queue.isEmpty()) {
			String term = queue.remove();
			String newTerm = rh.derive(term);
			derivedQueue.add(newTerm);
		}
	}
	
	// make the derived function by putting all the terms back together
	private String createOutput() {
		String outputString = "";
		while(!derivedQueue.isEmpty()) {
			outputString += derivedQueue.poll();
			outputString += " ";
		}
		// fix any possible String errors
		outputString = outputString.replace("+ -", "-");
		outputString = outputString.replace("- +", "-");
		outputString = outputString.replace("+ +", "+");
		outputString = outputString.replace("- -", "+");
		outputString = outputString.replace("+-", "-");
		outputString = outputString.replace("-+", "-");
		outputString = outputString.replace("++", "+");
		outputString = outputString.replace("--", "+");
		outputString = outputString.replace("* +", "*");
		outputString = outputString.replace("/ +", "/");
		outputString = outputString.replace("*+", "*");
		outputString = outputString.replace("/+", "/");
		outputString = outputString.replace("+ 0", "");
		outputString = outputString.replace("- 0", "");
		outputString = outputString.replace("+0", "");
		outputString = outputString.replace("-0", "");
		outputString = outputString.trim();
		return outputString;
	}
}
