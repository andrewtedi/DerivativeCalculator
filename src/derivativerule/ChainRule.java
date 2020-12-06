package derivativerule;

/**
 * 
 * ChainRule: Class that Implements the Chain Rule
 * for 2 functions f(x) and g(x) that are both differentiable,
 * the derivative of F(x) is 
 * F'(x) = f'(g(x))g'(x)
 *
 */

import application.FunctionHandler;

public class ChainRule implements DerivativeRule {

	@Override
	public String calculateRule(String st) {
		return null;
	}//calculateRule

	@Override
	/**
	 * @param String out is for the outside function, String in is for the inside function
	 * @return String is the derivative resulting from the chain function
	 */
	public String calculateRule(String out, String in) throws NumberFormatException {
		
		FunctionHandler fh = new FunctionHandler();

		//string out = fx, string in = gx
		//result from rule
		String result = "";
		
		//first term- gx'
		//delimiters: +, -, *, /
		String temp = in;	//use a temp to not disturb the string- we will need it later
		temp = temp.replaceAll("\\s", "");	//get rid of any spaces just in case
		String delimiter = "((?<=\\+)|(?=\\+))|((?<=\\-)|(?=\\-))|((?<=\\*)|(?=\\*))|((?<=\\/)|(?=\\/))|((?<=cos)|(?=cos))|((?<=sin)|(?=sin))|((?<=tan)|(?=tan))|((?<=\\()|(?=\\())|((?<=\\))|(?=\\)))";
		String[] arr = temp.split(delimiter);	//now the string is split between stuff for ease of use
		//if there is an array, go through it
		if(arr.length > 1) {
			result = "(";
			PowerRule pf = new PowerRule();
			for(int i=0; i<arr.length; i++) {
				if(i % 2 == 0 || i == 0) {
					result += pf.calculateRule(arr[i]);
				}//if
				else{
					result += arr[i];
				}//else
			}//for
			result += ")";
		}//if
		else {	//otherwise there is only 1 term, just use it as is
			PowerRule pf = new PowerRule();
			
			result = ("(" + pf.calculateRule(in) + ")");
			if(result.equals("(1)"))
				result = "";
			else if(result.equals("(0)"))
				return "0";
		}//else
		//now we have (gx')
		String outoutside = "";
		int i = 0;
		if(!out.matches("[-]?[0-9]+")) {
			while(Character.isDigit(out.charAt(i)) || out.charAt(i) == 'i' || out.charAt(i) == 'p'|| out.charAt(i) == '-') {
				if(out.charAt(i) == 'p') {
					if(!out.contains("pi"))
						throw new NumberFormatException();
				}
				if(out.charAt(i) == '-') {
					if(i != 0)
						throw new NumberFormatException();
				}
				outoutside += Character.toString(out.charAt(i));
				i++;
				if(i == out.length())
					break;
			}
			out = out.substring(i);
			System.out.println(outoutside);
		}
		int addedLength = result.length();
		//next term- fx'
		int displace = 0;
		if(out.equals("sin")) {	//function is sin
			
			if(!outoutside.equals("")) {
				result += '(';
				result += fh.sinDeriv();
				result += ')';
				displace = fh.sinDisplace() + 1;
			}
			else {
				result += fh.sinDeriv();
				displace = fh.sinDisplace();
			}
		
		}//if
		else if(out.equals("cos")) { //function is cos
			
			if(!outoutside.equals("")) {
				result += '(';
				result += fh.cosDeriv();
				result += ')';
				displace = fh.cosDisplace() + 1;
			}
			else {
				result += fh.cosDeriv();
				displace = fh.cosDisplace();
			}
			
		}//else if
		else if(out.equals("tan")) { //function is tan
			
			if(!outoutside.equals("")) {
				result += '(';
				result += fh.tanDeriv();
				result += ')';
				displace = fh.tanDisplace() + 1;
			}
			else {
				result += fh.tanDeriv();
				displace = fh.tanDisplace();
			}
			
		}//else if
		else if(out.equals("sin^-1")) { //function is sin^-1
			
			if(!outoutside.equals("")) {
				result += '(';
				result += fh.invSinDeriv();
				result += ')';
				displace = fh.invSinDisplace() + 1;
			}
			else {
				result += fh.invSinDeriv();
				displace = fh.invSinDisplace();
			};
			
		}//else if
		else if(out.equals("cos^-1")) { //function is cos^-1
			
			if(!outoutside.equals("")) {
				result += '(';
				result += fh.invCosDeriv();
				result += ')';
				displace = fh.invCosDisplace() + 1;
			}
			else {
				result += fh.invCosDeriv();
				displace = fh.invCosDisplace();
			}
			
		}//else if
		else if(out.equals("tan^-1")) { //function is tan^-1
			
			if(!outoutside.equals("")) {
				result += '(';
				result += fh.invTanDeriv();
				result += ')';
				displace = fh.invTanDisplace() + 1;
			}
			else {
				result += fh.invTanDeriv();
				displace = fh.invTanDisplace();
			}
			
		}//else if
		else if(out.contains("ln")) { //function is ln
			if(!outoutside.equals("")) {
				result += '(';
				result += fh.lnDeriv();
				result += ')';
				displace = fh.lnDisplace() + 1;
			}
			else {
				result += fh.lnDeriv();
				displace = fh.lnDisplace();
			}
		}//else if
		else if(out.contains("sqrt")) { //function is sqrt
			result += fh.sqrtDeriv();
			displace = fh.sqrtDisplace();
		}
		else if(out.contains("^")) {
			if(out.contains("e^")) {
				// make e function
				result += fh.eDeriv();
				displace = fh.eDisplace();
			}
			else {
				if(out.matches("[0-9]+(\\^)") || out.matches("[0-9]*[e]?(pi\\^)")) {
					String base = out.substring(0, out.length() - 1);
					// make base function
					result += fh.expDeriv(base);
					displace = fh.expDisplace(base);
				}
				else
					throw new NumberFormatException();
			}
		}
		else {
			
			PowerRule pf = new PowerRule();
			result += pf.calculateRule(in);
			
		}//else
		//now we have gx'fx'(missing gx)
		//add in gx into the last spot in parenthesis
		if(displace == 0)
			result += ("(" + in + ")");
		else
			result = result.substring(0, displace + addedLength) + in + result.substring(displace + addedLength);
		return outoutside + result;	//return gx' fx'(gx)
	}//calculateRule	

}//ChainRule
