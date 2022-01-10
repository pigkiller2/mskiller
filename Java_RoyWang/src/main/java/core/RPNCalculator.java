package core; /**
 * @author ：Roy Wang
 * @description：RPN calculator class
 * @version: 1.0$
 */

import constant.RPNConstants;
import exception.RPNExpressionException;
import utils.RPNCalculatorUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Stack;

public class RPNCalculator {

    //The stack to store the operands, i.e. numbers
    //For Phase 3, use Stack<RPNOperand> to replace BigDecimal for color
    private Stack<BigDecimal> operand = new Stack<BigDecimal>();

    //The stack for audit trail of the operations for undo/redo
    private Stack<List<BigDecimal>> auditTrailList = new Stack<>();


    /**
     * Perform RPN calculation based on input
     * @param input
     * @throws RPNExpressionException
     */
    public Stack<BigDecimal> calcRPN (String input) throws RPNExpressionException {

        String[] inputArr = input.split(RPNConstants.INPUT_STRING_DELIMITER);
        int inputArrLength = inputArr.length;

        RPNCalculatorUtils rpncu = new RPNCalculatorUtils();

        for (int i=0;i < inputArrLength; i ++){
            String inputStr = inputArr[i];

            //inputStr is operand, i.e. numbers
            if (rpncu.isOperand(inputStr)){
                operand.push( new BigDecimal(inputStr));
                rpncu.addAuditTrail (operand, auditTrailList);
                continue;
            }

            else if (rpncu.isUnaryOperator(inputStr)){
                    if (operand.size()>0){
                        BigDecimal bd1 = operand.pop();
                        operand.push(rpncu.calculate (bd1, inputStr));
                        rpncu.addAuditTrail (operand, auditTrailList);
                    }
                    else {
                        //throw new exception.ExpressionFormatException("Input Error: operator" + inputStr + "@position " + (i*2+1) + " lacks sufficient operand. Please double check the input.");
                        System.out.println ("operator " + inputStr + " (position: " + (i*2+1) +"): insufficient parameters");
                        break;
                    }
            }

            else if (rpncu.isBinaryOperator(inputStr)){
                    if (operand.size()>1){
                        BigDecimal bd2 = operand.pop(); //dividend goes first in case division operation
                        BigDecimal bd1 = operand.pop();
                        operand.push(rpncu.calculate (bd2, bd1, inputStr));
                        rpncu.addAuditTrail (operand, auditTrailList);
                    }
                    else {
                        //throw new exception.ExpressionFormatException("Input Error: operator '" + inputStr + "' lacks sufficient operands. Please double check the input.");
                        System.out.println ("operator " + inputStr + " (position: " + (i*2+1) +"): insufficient parameters");
                        break;
                    }
            }

            else if (RPNConstants.SPECIAL_OPERATOR_UNDO.equals(inputStr)){
                rpncu.undo (operand,auditTrailList);
            }

            else if (RPNConstants.SPECIAL_OPERATOR_CLEAR.equals(inputStr)){
                rpncu.clear (operand,auditTrailList);
            }

            else if (RPNConstants.SPECIAL_OPERATOR_EXIT.equals(inputStr)){
                System.exit(0);
            }

            else {

                //throw new RPNExpressionException("Input Error: Invalid RPN Expression. Please double check the input.");
                System.out.println ("Invalid RPN Expression '"+inputStr+"' . Please double check the valid input list.");
            }
        }

        rpncu.printStack(operand);
        return operand;
    }


}
