package core;

import exception.RPNExpressionException;
import utils.RPNCalculatorUtils;

import java.util.Scanner;

/**
 * @author ：Roy Wang
 * @description：Main class of the RPN calculator
 * @version: 1.0$
 */

public class Main {

    public static void main (String args[]){
        RPNCalculator rpnc = new RPNCalculator();
        RPNCalculatorUtils rpncu = new RPNCalculatorUtils();

        System.out.print("Welcome to RPN Calculator!\n" + "Valid input include: \n"+"- Numerics\n- Unary Operators: ");

        for (String operator : rpncu.unaryOperatorList) {
            System.out.print(operator+" ");
        }
        System.out.print("\n- Binary Operators: ");
        for (String operator : rpncu.binaryOperatorList) {
            System.out.print(operator+" ");
        }
        System.out.print("\n- Special Operators: ");
        for (String operator : rpncu.specialOperatorList) {
            System.out.print(operator+" ");
        }
        System.out.println("\nType 'exit' to exit the program.");


        try {
            while (true) {

                System.out.println("Please key in an expression:");

                Scanner scan = new Scanner(System.in);
                String strInput = scan.nextLine();
                //System.out.println("Input Expression: " + strInput);
                rpnc.calcRPN(strInput);
            }
        }catch (RPNExpressionException e){
            e.printStackTrace();
            //Add the exception information into Log in later stage
        }
    }
}
