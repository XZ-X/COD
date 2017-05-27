import static org.junit.Assert.*;

/**
 * Created by xuxiangzhe on 2017/5/27.
 */
public class ALUTest {
    static ALU alu=new ALU();
    static wALU wALU=new wALU();

    public static void main(String[] args) {
        System.out.println(alu.integerRepresentation("-128",8));
    }

    //expect  actual
    @org.junit.Test
    public void integerRepresentation() throws Exception {
        for(int number=-128;number<=128;number++){
            for(int length=8;length<=64;length++){
                System.out.println(number+"###"+length);
                assertEquals(alu.integerRepresentation(String.valueOf(number),length),wALU.integerRepresentation(String.valueOf(number),length));
            }
        }

    }

    @org.junit.Test
    public void floatRepresentation() throws Exception {
    }

    @org.junit.Test
    public void ieee754() throws Exception {
    }

    @org.junit.Test
    public void integerTrueValue() throws Exception {
    }

    @org.junit.Test
    public void floatTrueValue() throws Exception {
    }

    @org.junit.Test
    public void negation() throws Exception {
    }

    @org.junit.Test
    public void leftShift() throws Exception {
    }

    @org.junit.Test
    public void logRightShift() throws Exception {
    }

    @org.junit.Test
    public void ariRightShift() throws Exception {
    }

    @org.junit.Test
    public void fullAdder() throws Exception {
    }

    @org.junit.Test
    public void claAdder() throws Exception {
    }

    @org.junit.Test
    public void oneAdder() throws Exception {
    }

    @org.junit.Test
    public void adder() throws Exception {
    }

    @org.junit.Test
    public void integerAddition() throws Exception {
    }

    @org.junit.Test
    public void integerSubtraction() throws Exception {
    }

    @org.junit.Test
    public void integerMultiplication() throws Exception {
    }

    @org.junit.Test
    public void integerDivision() throws Exception {
    }

    @org.junit.Test
    public void signedAddition() throws Exception {
    }

    @org.junit.Test
    public void floatAddition() throws Exception {
    }

    @org.junit.Test
    public void floatSubtraction() throws Exception {
    }

    @org.junit.Test
    public void floatMultiplication() throws Exception {
    }

    @org.junit.Test
    public void floatDivision() throws Exception {
    }

}