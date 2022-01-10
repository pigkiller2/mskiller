import core.RPNCalculator;
import exception.RPNExpressionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.testng.Assert;
import utils.RPNCalculatorUtils;

import java.math.BigDecimal;
import java.util.Stack;

import static org.junit.Assert.*;

/**
 * @author ：Roy Wang
 * @description：Test Class for RPN Expression
 * @version: 1.0$
 */
public class RPNExpressionTest {

    Stack<BigDecimal> testStack = new Stack<>();
    RPNCalculator rpnc = new RPNCalculator();
    RPNCalculatorUtils rpncu = new RPNCalculatorUtils();

    @Before
    public void beforeTest(){
        System.out.println("Test Start...");
    }

    @After
    public void afterTest(){
        System.out.println("Test Completed...");
    }

    @Test
    public void testInvalidRPNExpression(){
        String testExample = "invalidinput";
        try {
            testStack = rpnc.calcRPN(testExample);
            assertEquals(0,testStack.size());
        }catch (RPNExpressionException e){

            fail();
        }
    }

    @Test
    public void testInvalidOperator(){
        String testExample = "2 5 ^";
        try {
            testStack = rpnc.calcRPN(testExample);
            assertEquals(2,testStack.size());
            assertEquals(new BigDecimal(5),testStack.pop());
            assertEquals(new BigDecimal(2),testStack.pop());
        }catch (RPNExpressionException e){

            fail();
        }
    }

    @Test
    public void testDivideByZero(){
        String testExample = "2 0 /";
        assertThrows(RPNExpressionException.class, () -> {
            testStack = rpnc.calcRPN(testExample);
        });


    }
}
