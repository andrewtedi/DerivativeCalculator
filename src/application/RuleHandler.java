package application;

import derivativerule.ChainRule;
import derivativerule.DivisionRule;
import derivativerule.PowerRule;
import derivativerule.ProductRule;

public class RuleHandler {

	/**
	 * Function that takes the given input and applies a rule to it in order to get the derivative
	 * @param term - the String given from the parsed input
	 * @return result - the result of the derived term
	 */
	public String derive(String term) throws NumberFormatException {
		if(term.equals("+") || term.equals("-"))
			return term;
		
		//result of derivative to return
		String result = "";
		try {
			if(term.equals("")) {
			
				//empty input
				result = "";
			
			}//if
			else if(term.contains("/")) {
			
				//call quotient rule
				//first split input term
				String delimiter = "((?<=\\/)|(?=\\/))";	//split at the /
				String[] termSplit = term.split(delimiter);	//now, easy parsing
				
				//now, call quotient rule
				DivisionRule dr = new DivisionRule();
				result = dr.calculateRule(termSplit[0],termSplit[2]);
			
			}//else if
			else if(term.contains("sin")&&term.contains("cos") || term.contains("sin")&&term.contains("tan")
						|| term.contains("cos")&& term.contains("tan")){

				//first multi check
				//first split functions
				String delimiter = "((?<=\\))|(?=\\)))";	//split at the ) (where the next function starts
				String[] termSplit = term.split(delimiter);	//now, parsing
				termSplit[0] += termSplit[1];	//add the ) back
				termSplit[2] += termSplit[3];	//add the ) back

				//now, call product rule
				ProductRule pr = new ProductRule();
				result = pr.calculateRule(termSplit[0].replace("*", ""), termSplit[2].replace("*", ""));

			}//else if
			else if(term.contains("*")) {
			
				//first split input term
				String delimiter = "((?<=\\*)|(?=\\*))";	//split at the *
				String[] termSplit = term.split(delimiter);	//now, easy parsing
			
				//now, call product rule
				ProductRule pr = new ProductRule();
				result = pr.calculateRule(termSplit[0],termSplit[3]);
			
			}//else if
			else if(term.contains("sqrt") || term.contains("cos") || term.contains("sin") 
					|| term.contains("tan") || term.contains("ln") || term.contains("e^") || term.contains("sin^-1") 
					|| term.contains("cos^-1") || term.contains("tan^-1")
					|| term.matches(".*[0-9]+\\^.*")) {
				int startParenthesesIndex = term.indexOf('(');
				String out = term.substring(0, startParenthesesIndex);
				int endParenthesesIndex = term.indexOf(')');
				String in = term.substring(startParenthesesIndex + 1, endParenthesesIndex);
				//call chain rule
				ChainRule cr = new ChainRule();
				result = cr.calculateRule(out, in);
			
			}//else if
			else {
				//call power rule
				PowerRule pr = new PowerRule();
				result = pr.calculateRule(term);
			
			}//else
		}
		catch(NumberFormatException e) {
			throw e;
		}
		//result derived to return
		return result;
		
	}//derive
	
}//RuleHandler
