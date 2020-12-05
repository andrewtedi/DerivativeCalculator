package derivativerule;

/**
 * 
 * ProductRule: Class that Implements the Product Rule
 * for 2 functions f(x) and g(x) that are both differentiable,
 * (fg)' = f'g + fg'
 *
 */

import application.FunctionHandler;

public class ProductRule implements DerivativeRule{

	@Override
	/**
	 * @param String fx- the first function, String gx- the second function
	 * @return the result String of their derivative
	 */
	public String calculateRule(String fx, String gx) throws NumberFormatException, StringIndexOutOfBoundsException {
		
		String result = "";	//final result
		String fxdx = "NONE";	//derivative of fx
		String gxdx = "NONE";	//derivative of gx
		
		FunctionHandler fh = new FunctionHandler();
		
		fx = fx.replaceAll("\\s", "");	//get rid of any spaces just in case
		gx = gx.replaceAll("\\s", "");	//get rid of any spaces just in case
		String delimiter = "((?<=cos)|(?=cos))|((?<=sin)|(?=sin))|((?<=tan)|(?=tan))|((?<=cxs)|(?=cxs))|((?<=sxn)|(?=sxn))|((?<=txn)|(?=txn))";
		String[] arrFX = fx.split(delimiter);	//split for easy parsing
		String[] arrGX = gx.split(delimiter);	//split for easy parsing
		int displace = 0;
		//check what function the fx is
		if(arrFX[0].equals("sin")) {
			
			fxdx = fh.sinDeriv();
			displace = fh.sinDisplace();
			
		}//if
		else if(arrFX[0].equals("cos")) {
			
			fxdx = fh.cosDeriv();
			displace = fh.cosDisplace();
			
		}//else if
		else if(arrFX[0].equals("tan")) {
			
			fxdx = fh.tanDeriv();
			displace = fh.tanDisplace();
			
		}//else if
		else if(arrFX[0].equals("sin^-1")) {
			
			fxdx = fh.invSinDeriv();
			displace = fh.invSinDisplace();
			
		}//else if
		else if(arrFX[0].equals("cos^-1")) {
			
			fxdx = fh.invCosDeriv();
			displace = fh.invCosDisplace();
			
		}//else if
		else if(arrFX[0].equals("tan^-1")) {
			
			fxdx = fh.invTanDeriv();
			displace = fh.invTanDisplace();
			
		}//else if
		else{}//else

		fxdx = "(" + fxdx.substring(0, displace) + arrFX[1].substring(1, arrFX[1].length()-1) + fxdx.substring(displace) + ")";
		
		//check what function the gx is
		if(arrGX[0].equals("sin")) {
			
			gxdx = fh.sinDeriv();
			displace = fh.sinDisplace();
			
		}//if
		else if(arrGX[0].equals("cos")) {
			
			gxdx = fh.cosDeriv();
			displace = fh.cosDisplace();
			
		}//else if
		else if(arrGX[0].equals("tan")) {
			
			gxdx = fh.tanDeriv();
			displace = fh.tanDisplace();
			
		}//else if
		else if(arrGX[0].equals("sin^-1")) {
			
			gxdx = fh.invSinDeriv();
			displace = fh.invSinDisplace();
			
		}//else if
		else if(arrGX[0].equals("cos^-1")) {
			
			gxdx = fh.invCosDeriv();
			displace = fh.invCosDisplace();
			
		}//else if
		else if(arrGX[0].equals("tan^-1")) {
			
			gxdx = fh.invTanDeriv();
			displace = fh.invTanDisplace();
			
		}//else if
		else {}//else
		
		gxdx = "("+ gxdx.substring(0, displace) + arrGX[1].substring(1, arrGX[1].length()-1) + gxdx.substring(displace) + ")";
		
		//calculate the inside fx () is there is something
		if(!(arrFX[1].equals("(x)"))) {
			arrFX[1] = arrFX[1].replaceAll("[()]", "");	//get rid of any ()
			PowerRule pf = new PowerRule();
			fxdx = "(" + pf.calculateRule(arrFX[1]) + ")" + fxdx;
		}//if
		else {}//else
		
		//calculate the inside gx () is there is something
		if(!(arrGX[1].equals("(x)"))){
			if(arrGX[1].contains(")")){
				arrGX[1] = arrGX[1].replaceAll("[()]", "");	//get rid of any ()
				PowerRule pf = new PowerRule();
				gxdx = "(" + pf.calculateRule(arrGX[1]) + ")" + gxdx;
			}//if
			else {}//else
		}//if
		else {}//else
		
		//final result
		result = ((fxdx + gx) + " + " + (fx + gxdx));
		
		return result;
		
	}//calculateRule

	@Override
	public String calculateRule(String st) {
		// TODO Auto-generated method stub
		return null;
	}//calculateRule no use
	
}//ProductRule
