package entity;

import java.awt.*;
import java.math.BigDecimal;

/**
 * @author ：Roy Wang
 * @description：RPN Operand Class used for Phase 3 requirement for color
 * @version: 1.0$
 */
public class RPNOperand {

    private BigDecimal operand;
    private int position;
    private Color color;

    public RPNOperand(){
        this.operand = BigDecimal.ZERO;
        this.position = 0;
        this.color = new Color(0,0,0);
    }
    public RPNOperand(BigDecimal number, int pos, Color col){
        this.operand = number;
        this.position = pos;
        this.color = col;
    }
}
