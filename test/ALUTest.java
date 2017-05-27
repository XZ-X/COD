import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xuxiangzhe on 2017/5/27.
 */
public class ALUTest {
    static ALU alu=new ALU();
    static wALU wALU=new wALU();
    static qALU qALU=new qALU();

    public static void main(String[] args) {
        System.out.println(alu.floatRepresentation("-1.5999999999999996",4,49));
    }

    //expect  actual
    @org.junit.Test
    public void integerRepresentation() throws Exception {
        for(int number=-1024;number<=1024;number++){
            for(int length=64;length<=128;length++){
                System.out.println(number+"###"+length);
                assertEquals(alu.integerRepresentation(String.valueOf(number),length),wALU.integerRepresentation(String.valueOf(number),length));
            }
        }

    }
    @Test
    public void integerRepresentation2() throws Exception {
        for(int number=-128;number<=128;number++){
            for(int length=8;length<=32;length++){
                System.out.println(number+"###"+length);
                assertEquals(alu.integerRepresentation(String.valueOf(number),length),qALU.integerRepresentation(String.valueOf(number),length));
            }
        }

    }

    @org.junit.Test
    public void floatRepresentation() throws Exception {
        String string;
        for(int i=0;i<1000;i++) {
            string=String.valueOf(-50.0+0.1D*i);
            for(int elength=4;elength<20;elength++){
                for(int slength=4;slength<50;slength++){
                    System.out.println(string+"##"+elength+"##"+slength);
                    assertEquals(alu.floatRepresentation(string, elength, slength), wALU.floatRepresentation(string, elength, slength));
                }
            }

        }
    }

    @org.junit.Test
    public void floatRepresentation2() throws Exception {
        String string;
        for(int i=0;i<100;i++) {
            string=String.valueOf(-5.0+0.1D*i);
            for(int elength=4;elength<20;elength++){
                for(int slength=4;slength<50;slength++){
                    System.out.println(string+"##"+elength+"##"+slength);
                    assertEquals(alu.floatRepresentation(string, elength, slength), qALU.floatRepresentation(string, elength, slength));
                }
            }

        }
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