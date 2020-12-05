package derivativerule;

/**
 * 
 * DivisionRule: Class that Implements the Quotient Rule
 * for 2 functions f(x) and g(x) that are both differentiable,
 * (f/g)' = f'g - fg' / g^2
 *
 */

public class DivisionRule implements DerivativeRule{

	@Override
	public String calculateRule(String st) {
		return null;
	}//calculateRule

	@Override
	/**
	 * @param String fx- the first function, String gx- the second function
	 * @return the result String of their derivative
	 */
	public String calculateRule(String fx, String gx) throws NumberFormatException, StringIndexOutOfBoundsException {
		
		String result = "";//store resulting derivative
		
		ProductRule product = new ProductRule();
		
		//check if the second function has any exp, and add ^-1 to bring it out of division
		if(gx.contains("^")) {
			
			String delimiter = "((?<=\\^)|(?=\\^))|";
			String[] arr = gx.split(delimiter);
			gx = (arr[0] + arr[1] + "-" + arr[2]);
		}//if
		else {	//otherwise, adding on ^-1 directly is okay
			
			gx += "^-1";
			
		}//else
		
		if(fx.equals("1") && gx.contains("x")) {
			
			//calculate the denominator since the numerator is a 1
			result = fx + "/";
			
			PowerRule pr = new PowerRule();
			String temp = pr.calculateRule(gx);
			String delimiter = "((?<=x)|(?=x))";
			String[] gxArr = temp.split(delimiter);
			
			if(gxArr[0].equals("x")) {
				result += temp;
			}//if
			else {
				result = gxArr[0] + "/" + gxArr[1] + gxArr[2];
			}//else
			
		}//if
		else {
			//calc using product rule now that is a product
			result = product.calculateRule(fx, gx);
		}//else
		
		//resulting derivative
		return result;
		
	}//calculateRule

}//DivisionRule
