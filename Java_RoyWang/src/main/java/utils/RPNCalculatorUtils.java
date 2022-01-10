package utils; /**
 * @author ：Roy Wang
 * @description：Utility class used in RPN calculator
 * @version: 1.0$
 */

import constant.RPNConstants;
import exception.RPNExpressionException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class RPNCalculatorUtils {


    //List of binary and unary operator, support future additions of new operators
    public List<String> binaryOperatorList = new ArrayList<>(Arrays.asList(
            RPNConstants.BINARY_OPERATOR_ADD,
            RPNConstants.BINARY_OPERATOR_SUBTRACT,
            RPNConstants.BINARY_OPERATOR_MULTIPLY,
            RPNConstants.BINARY_OPERATOR_DIVIDE
    ));
    public List<String> unaryOperatorList = new ArrayList<>(Arrays.asList(
            RPNConstants.UNARY_OPERATOR_SQRT
    ));

    public List<String> specialOperatorList = new ArrayList<>(Arrays.asList(
            RPNConstants.SPECIAL_OPERATOR_CLEAR,
            RPNConstants.SPECIAL_OPERATOR_UNDO
    ));

    /**
     * check if the input string is a valid operand, i.e. BigDecimal number
     * @param inputStr
     * @return
     */
    public boolean isOperand(String inputStr) {
        try {
            BigDecimal bd = new BigDecimal(inputStr);
        }catch (Exception e) {
            //Not numeric input, not Operand but Operator
            return false;
        }
        return true;
    }

    public boolean isUnaryOperator(String inputStr) {
        if (unaryOperatorList.contains(inputStr)){
            return true;
        }
        return false;
    }

    public boolean isBinaryOperator(String inputStr) {
        if (binaryOperatorList.contains(inputStr)){
            return true;
        }
        return false;
    }

    /**
     * Unary Operator Calculation
     * @param operand
     * @param inputStr
     * @throws RPNExpressionException
     */
    public BigDecimal calculate(BigDecimal operand, String inputStr) throws RPNExpressionException {

        switch(inputStr) {
            case "sqrt":
                if(operand.compareTo(BigDecimal.ZERO) < 0) {
                    throw new RPNExpressionException("Input Error: Cannot perform Square Root Operation on input " + operand);
                }

                //Square Root for BigDecimal using sqrt on Double may lose precision but still meets the precision requirement
                BigDecimal result = (new BigDecimal(Math.sqrt(operand.doubleValue()))).setScale(15, RoundingMode.HALF_UP);
                return result;
            default:
                throw new RPNExpressionException("Input Error: Not supported operator " + inputStr);
        }
    }

    /**
     * Binary Operator Calculation
     * @param bd2
     * @param bd1
     * @param inputStr
     */
    public BigDecimal calculate(BigDecimal bd2, BigDecimal bd1, String inputStr) throws RPNExpressionException {
        switch(inputStr){
            case RPNConstants.BINARY_OPERATOR_ADD:
                return bd1.add(bd2);
            case RPNConstants.BINARY_OPERATOR_SUBTRACT:
                return bd1.subtract(bd2);
            case RPNConstants.BINARY_OPERATOR_MULTIPLY:
                return bd1.multiply(bd2);
            case RPNConstants.BINARY_OPERATOR_DIVIDE:
                if (bd2.compareTo(BigDecimal.ZERO)==0){
                    throw new RPNExpressionException("Input Error: The dividend cannot be zero.");
                }
                return bd1.divide(bd2).setScale(15, RoundingMode.HALF_UP);
            default:
                throw new RPNExpressionException("Input Error: operator '" + inputStr+"' not supported.");
        }

    }


    /**
     * Perform Undo function using historical snapshot in Audit Trail
     * @param operand
     * @param auditTrailList
     */
    public void undo(Stack<BigDecimal> operand, Stack<List<BigDecimal>> auditTrailList) {

        //Pop out all operand in the Stack
        while (!operand.isEmpty())
        {
            operand.pop();
        }

        //Load the last snapshot of Stack from AuditTrail
        if (!auditTrailList.isEmpty()){
            auditTrailList.pop();
            List<BigDecimal> numberAudit = auditTrailList.peek();
            for (BigDecimal bd : numberAudit){
                if (bd!=null)
                    operand.push(bd);
            }
        }
    }

    /**
     * Perform clear operation
     * @param operand
     * @param auditTrailList
     */
    public void clear(Stack<BigDecimal> operand, Stack<List<BigDecimal>> auditTrailList) {
        //Pop out all operand in the Stack
        while (!operand.isEmpty())
        {
            operand.pop();
        }

        List<BigDecimal> bdList = new ArrayList<>();
        bdList.add(null);
        //Log clean action in AuditTrail, in case undo action
        auditTrailList.push(bdList);
    }

    /**
     * Add the Stack snapshot into Audit Trail
     * @param operand
     * @param auditTrailList
     */
    public void addAuditTrail(Stack<BigDecimal> operand, Stack<List<BigDecimal>> auditTrailList){
        List<BigDecimal> operandList = new ArrayList<>();
        for (BigDecimal bd : operand){
            operandList.add(bd);
        }
        auditTrailList.push(operandList);
    }

    /**
     * Print the content of the Stack
     * BigDecimal print format as required
     * @param operand
     */
    public void printStack (Stack<BigDecimal> operand){
        System.out.print("stack: ");
        if (operand.isEmpty())
            System.out.println();
        else {
            DecimalFormat numberFormat = new DecimalFormat("##########.##########");
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            for (BigDecimal bd : operand){
                System.out.print (numberFormat.format(bd) + " ");
            }
            System.out.println(" ");
        }
    }
}
