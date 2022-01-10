package exception;

/**
 * @author ：Roy Wang
 * @description：Input Expression Exception class of the RPN calculator
 * @version: 1.0$
 */

public class RPNExpressionException extends Exception {

    private static final long serialVersionUID = 1L;

    public RPNExpressionException(String s) {
        super(s);
        //Add the exception information into log in later stage

    }
}
