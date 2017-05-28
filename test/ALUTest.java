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
        System.out.println(alu.floatTrueValue("01000001001101100000", 8, 11));
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
        String string;
        for(int i=0;i<10000;i++){
            string=String.valueOf(-250000+50*i);
            string=string+"."+"0";
            System.out.println(string);
            assertEquals(alu.ieee754(string,32),wALU.ieee754(string,32));
            assertEquals(alu.ieee754(string,64),wALU.ieee754(string,64));
        }
    }

    @org.junit.Test
    public void ieee7542() throws Exception {
        String string;
        for(int i=0;i<10000;i++){
            string=String.valueOf(-2500+0.5D*i);
            System.out.println(string);
            assertEquals(alu.ieee754(string,32),qALU.ieee754(string,32));
            assertEquals(alu.ieee754(string,64),qALU.ieee754(string,64));
        }
    }
    @org.junit.Test
    public void integerTrueValue() throws Exception {
        String[] strings4={
                 "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101","0000"
        };

        String[] strings8=new String[0x100];
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                strings8[(i<<4)+j]=strings4[i]+strings4[j];
            }
        }

        String[] strings16=new String[0x10000];
        for(int i=0;i<0x100;i++){
            for(int j=0;j<0x100;j++){
                strings16[(i<<8)+j]=strings8[i]+strings8[j];
            }
        }
        String temp=null;
//        String[] strings32=new String[0x7fffffff];
        for(int i=0;i<0x10000;i++){
            for(int j=0;j<0x10000;j++){
                temp=strings16[i]+strings16[j];
                assertEquals(alu.integerTrueValue(temp),wALU.integerTrueValue(temp));
            }
            System.out.println(temp);
        }

//        for(int i=0;i<0x7fffffff;i++){
//            System.out.println(strings32[i]);
//            assertEquals(alu.integerTrueValue(strings32[i]),wALU.integerTrueValue(strings32[i]));
//        }
    }

    @org.junit.Test
    public void integerTrueValue2() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=0;i<16;i++){
            System.out.println(strings[i]);
            assertEquals(alu.integerTrueValue(strings[i]),qALU.integerTrueValue(strings[i]));
        }
    }


    @org.junit.Test
    public void floatTrueValue() throws Exception {
        long x=Double.doubleToLongBits(3.2);
        StringBuffer buffer=new StringBuffer();
        for(int i=0;i<64;i++){
            if((x&(1L<<(63-i)))!=0){
                buffer.append('1');
            }else {
                buffer.append('0');
            }
        }
        String test=new String(buffer);
//        System.out.println(alu.floatTrueValue(test,11,52));
//        assertEquals(alu.floatTrueValue(test,11,52),qALU.floatTrueValue(test,11,52));
    }

    @org.junit.Test
    public void floatTrueValue2() throws Exception {

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