import core.RPNCalculator;
import exception.RPNExpressionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.RPNCalculatorUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author ：Roy Wang
 * @description：Unit Test Class for RPN Calculator
 * @version: 1.0$
 */
public class RPNCalculatorTest {
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
    public void testRPNCalculation1(){
        String testExample1 = "5 2";
        try {
            testStack = rpnc.calcRPN(testExample1);
            assertEquals(new BigDecimal(2), testStack.pop());
            assertEquals(new BigDecimal(5), testStack.peek());
        }catch (RPNExpressionException e){
            fail();
        }
    }

    @Test
    public void testRPNCalculation2(){
        String testExample2a = "2 sqrt";
        String testExample2b = "clear 9 sqrt";
        try {
            testStack = rpnc.calcRPN(testExample2a);
            assertEquals(0, (new BigDecimal("1.4142135623")).compareTo(testStack.peek().setScale(10, RoundingMode.DOWN).stripTrailingZeros()));

            testStack = rpnc.calcRPN(testExample2b);
            assertEquals(new BigDecimal(3), testStack.peek().stripTrailingZeros());
        }catch (RPNExpressionException e){
            fail();
        }
    }

    @Test
    public void testRPNCalculation3(){
        String testExample3a = "5 2 -";
        String testExample3b = "3 -";
        String testExample3c = "clear";
        try {
            testStack = rpnc.calcRPN(testExample3a);
            assertEquals(new BigDecimal(3), (testStack.peek().setScale(10, RoundingMode.DOWN).stripTrailingZeros()));

            testStack = rpnc.calcRPN(testExample3b);
            assertEquals( BigDecimal.ZERO, testStack.peek().stripTrailingZeros());

            testStack = rpnc.calcRPN(testExample3c);
            assertEquals( true, testStack.isEmpty());
        }catch (RPNExpressionException e){
            fail();
        }
    }

    @Test
    public void testRPNCalculation4(){
        String testExample4a = "5 4 3 2";
        String testExample4b = "undo undo *";
        String testExample4c = "5 *";
        String testExample4d = "undo";
        try {
            testStack = rpnc.calcRPN(testExample4a);
            Stack<BigDecimal> tempStack = new Stack<>();
            tempStack.addAll(testStack);
            assertEquals(new BigDecimal(2), (tempStack.pop()));
            assertEquals(new BigDecimal(3), (tempStack.pop()));
            assertEquals(new BigDecimal(4), (tempStack.pop()));
            assertEquals(new BigDecimal(5), (tempStack.pop()));

            testStack = rpnc.calcRPN(testExample4b);
            assertEquals( new BigDecimal(20), testStack.peek());

            testStack = rpnc.calcRPN(testExample4c);
            assertEquals( new BigDecimal(100), testStack.peek());

            testStack = rpnc.calcRPN(testExample4d);
            tempStack.clear();
            tempStack.addAll(testStack);
            assertEquals( new BigDecimal(5), testStack.pop());
            assertEquals( new BigDecimal(20), testStack.pop());
        }catch (RPNExpressionException e){
            fail();
        }
    }

    @Test
    public void testRPNCalculation5(){
        String testExample5a = "7 12 2 /";
        String testExample5b = "*";
        String testExample5c = "4 /";
        try {
            testStack = rpnc.calcRPN(testExample5a);
            Stack<BigDecimal> tempStack = new Stack<>();
            tempStack.addAll(testStack);
            assertEquals(new BigDecimal(6), (tempStack.pop().stripTrailingZeros()));
            assertEquals(new BigDecimal(7), (tempStack.pop().stripTrailingZeros()));
            testStack = rpnc.calcRPN(testExample5b);
            assertEquals(new BigDecimal(42), testStack.peek().stripTrailingZeros());
            testStack = rpnc.calcRPN(testExample5c);
            assertEquals(new BigDecimal(10.5), testStack.peek().stripTrailingZeros());
        }catch (RPNExpressionException e){
            fail();
        }
    }

    @Test
    public void testRPNCalculation6(){
        String testExample6a = "1 2 3 4 5";
        String testExample6b = "*";
        String testExample6c = "clear 3 4 -";
        try {
            testStack = rpnc.calcRPN(testExample6a);
            Stack<BigDecimal> tempStack = new Stack<>();
            tempStack.addAll(testStack);
            assertEquals(new BigDecimal(5), (tempStack.pop().stripTrailingZeros()));
            assertEquals(new BigDecimal(4), (tempStack.pop().stripTrailingZeros()));
            assertEquals(new BigDecimal(3), (tempStack.pop().stripTrailingZeros()));
            assertEquals(new BigDecimal(2), (tempStack.pop().stripTrailingZeros()));
            assertEquals(new BigDecimal(1), (tempStack.pop().stripTrailingZeros()));
            testStack = rpnc.calcRPN(testExample6b);

            assertEquals(true, (new BigDecimal(20)).compareTo(testStack.peek())==0);
            testStack = rpnc.calcRPN(testExample6c);
            assertEquals(new BigDecimal(-1), testStack.peek().stripTrailingZeros());
        }catch (RPNExpressionException e){
            fail();
        }
    }

    @Test
    public void testRPNCalculation7(){
        String testExample7a = "1 2 3 4 5";
        String testExample7b = "* * * *";
        try {
            testStack = rpnc.calcRPN(testExample7a);
            Stack<BigDecimal> tempStack = new Stack<>();
            tempStack.addAll(testStack);
            assertEquals(new BigDecimal(5), (tempStack.pop().stripTrailingZeros()));
            assertEquals(new BigDecimal(4), (tempStack.pop().stripTrailingZeros()));
            assertEquals(new BigDecimal(3), (tempStack.pop().stripTrailingZeros()));
            assertEquals(new BigDecimal(2), (tempStack.pop().stripTrailingZeros()));
            assertEquals(new BigDecimal(1), (tempStack.pop().stripTrailingZeros()));
            testStack = rpnc.calcRPN(testExample7b);

            assertEquals(true, (new BigDecimal(120)).compareTo(testStack.peek())==0);

        }catch (RPNExpressionException e){
            fail();
        }
    }

    @Test
    public void testRPNCalculation8(){
        String testExample8a = "1 2 3 * 5 + * * 6 5";
        try {
            testStack = rpnc.calcRPN(testExample8a);
            assertEquals(new BigDecimal(11), (testStack.pop().stripTrailingZeros()));
        }catch (RPNExpressionException e){
            fail();
        }
    }
}
