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
//        System.out.println(alu.integerSubtraction("0000","1000",8));
//        System.out.println(alu.signedAddition("0001","1000",4));
//          System.out.println(alu.floatTrueValue("0111111111111000000000000000000000000000000000000000000000000000", 11, 52));
//        System.out.println(qALU.floatTrueValue("0110000100100110110000101101010000100101011011111111110011000011", 11, 52));
//        System.out.println(qALU.floatRepresentation("-1.5999999999999996", 4, 200));
        System.out.println(alu.floatRepresentation("-0.1999999999999993", 4, 47));
        System.out.println(alu.floatRepresentation("-0.1999999999999993", 4, 60));
        System.out.println(alu.floatRepresentation("0.0", 5, 4));

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
            string=String.valueOf(-50000+0.1D*i);
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
                    assertEquals(alu.floatRepresentation(string, elength, slength), alu.floatRepresentation(string, elength, slength));
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
        String temp=null;
        String[] strings16=new String[0x10000];
        for(int i=0;i<0x100;i++){
            for(int j=0;j<0x100;j++){
//                strings16[(i<<8)+j]=strings8[i]+strings8[j];
                temp=strings8[i]+strings8[j];
                assertEquals(alu.integerTrueValue(temp),wALU.integerTrueValue(temp));
            }
        }
//        String temp=null;
//        String[] strings32=new String[0x7fffffff];
//        for(int i=0;i<0x10000;i++){
//            for(int j=0;j<0x10000;j++){
//                temp=strings16[i]+strings16[j];
//
//            }
//            System.out.println(temp);
//        }

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
        for(int j=0;j<200000;j++) {
            long x = Double.doubleToLongBits(0.00000000000001D*j);
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < 64; i++) {
                if ((x & (1L << (63 - i))) != 0) {
                    buffer.append('1');
                } else {
                    buffer.append('0');
                }
            }
            String test = new String(buffer).substring(0,buffer.length()-1);
//        System.out.println(alu.floatTrueValue(test,11,52));
            System.out.println(test);
            assertEquals(alu.floatTrueValue(test, 11, 51), wALU.floatTrueValue(test, 11, 51));
        }
    }

    @org.junit.Test
    public void floatTrueValue2() throws Exception {
        double temp=10000000000000000000000000000000000000000D;
        temp=temp*temp;temp=temp*temp;
        for(int j=0;j<200000;j++) {

            long x = Double.doubleToLongBits(temp * j);
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < 64; i++) {
                if ((x & (1L << (63 - i))) != 0) {
                    buffer.append('1');
                } else {
                    buffer.append('0');
                }
            }
            System.out.printf("%f\n",Double.longBitsToDouble(x));
            String test = new String(buffer);
            System.out.println(test);
            assertEquals(alu.floatTrueValue(test, 11, 52), qALU.floatTrueValue(test, 11, 52));
        }
    }

    @org.junit.Test
    public void negation() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=0;i<16;i++) {
            assertEquals(alu.negation(strings[i]),wALU.negation(strings[i]));
            assertEquals(alu.negation(strings[i]),qALU.negation(strings[i]));
        }
    }

    @org.junit.Test
    public void leftShift() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101",
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=1;i<32;i++) {
            assertEquals(alu.leftShift(strings[i],i-1),wALU.leftShift(strings[i],i-1));
//            assertEquals(alu.leftShift(strings[i],2*i),qALU.leftShift(strings[i],2*i));
        }
    }

//w pass q fail
    @org.junit.Test
    public void logRightShift() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101",
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101",
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=1;i<16;i++) {
            for(int j=0;j<16;j++) {
                assertEquals(alu.logRightShift(strings[i]+strings[j], 3), wALU.logRightShift(strings[i]+strings[j], 3));
                assertEquals(alu.logRightShift(strings[i]+strings[j], 3), qALU.logRightShift(strings[i]+strings[j], 3));
            }
        }
    }
//w pass q fail
    @org.junit.Test
    public void ariRightShift() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101",
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101",
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=1;i<48;i++) {
            for (int j = 0; j < 16; j++) {
                assertEquals(alu.ariRightShift(strings[i] + strings[j], 3), wALU.ariRightShift(strings[i] + strings[j], 3));
                assertEquals(alu.ariRightShift(strings[i] + strings[j], 3), qALU.ariRightShift(strings[i] + strings[j], 3));
            }
        }
    }

    @org.junit.Test
    public void fullAdder() throws Exception {
        assertEquals(alu.fullAdder('1','1','1'),wALU.fullAdder('1','1','1'));
        assertEquals(alu.fullAdder('1','1','0'),wALU.fullAdder('1','1','0'));
        assertEquals(alu.fullAdder('1','0','0'),wALU.fullAdder('1','0','0'));
        assertEquals(alu.fullAdder('0','1','0'),wALU.fullAdder('0','1','0'));
        assertEquals(alu.fullAdder('0','1','1'),wALU.fullAdder('0','1','1'));
        assertEquals(alu.fullAdder('0','0','1'),wALU.fullAdder('0','0','1'));
        assertEquals(alu.fullAdder('0','0','0'),wALU.fullAdder('0','0','0'));
        assertEquals(alu.fullAdder('1','0','1'),wALU.fullAdder('1','0','1'));
    }

    @org.junit.Test
    public void fullAdder2() throws Exception {
        assertEquals(alu.fullAdder('1','1','1'),qALU.fullAdder('1','1','1'));
        assertEquals(alu.fullAdder('1','1','0'),qALU.fullAdder('1','1','0'));
        assertEquals(alu.fullAdder('1','0','0'),qALU.fullAdder('1','0','0'));
        assertEquals(alu.fullAdder('0','1','0'),qALU.fullAdder('0','1','0'));
        assertEquals(alu.fullAdder('0','1','1'),qALU.fullAdder('0','1','1'));
        assertEquals(alu.fullAdder('0','0','1'),qALU.fullAdder('0','0','1'));
        assertEquals(alu.fullAdder('0','0','0'),qALU.fullAdder('0','0','0'));
        assertEquals(alu.fullAdder('1','0','1'),qALU.fullAdder('1','0','1'));
    }

    @org.junit.Test
    public void claAdder() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                System.out.println(strings[i]+"##"+strings[j]);
                assertEquals(alu.claAdder(strings[i],strings[j],'1'),wALU.claAdder(strings[i],strings[j],'1'));
                System.out.println(strings[i]+"#*#"+strings[j]);
                assertEquals(alu.claAdder(strings[i],strings[j],'0'),wALU.claAdder(strings[i],strings[j],'0'));
            }
        }
    }

    @org.junit.Test
    public void claAdder2() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                System.out.println(strings[i]+"##"+strings[j]);
                assertEquals(alu.claAdder(strings[i],strings[j],'1'),qALU.claAdder(strings[i],strings[j],'1'));
                System.out.println(strings[i]+"#*#"+strings[j]);
                assertEquals(alu.claAdder(strings[i],strings[j],'0'),qALU.claAdder(strings[i],strings[j],'0'));
            }
        }
    }

    //q pass  w fail
    @org.junit.Test
    public void oneAdder() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=0;i<16;i++){
            System.out.println(strings[i]);
//            assertEquals(alu.oneAdder(strings[i]),wALU.oneAdder(strings[i]));
            assertEquals(alu.oneAdder(strings[i]),qALU.oneAdder(strings[i]));
        }
    }

    @org.junit.Test
    public void adder() throws Exception {
        String[] strings={
                "00", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","100","10","1","1101"
        };
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                System.out.println(strings[i]+"##"+strings[j]);
                assertEquals(alu.adder(strings[i],strings[j],'1',4),wALU.adder(strings[i],strings[j],'1',4));
                System.out.println(strings[i]+"#*#"+strings[j]);
                assertEquals(alu.adder(strings[i],strings[j],'0',4),wALU.adder(strings[i],strings[j],'0',4));
            }
        }
    }

    @org.junit.Test
    public void adder2() throws Exception {
        String[] strings={
                "000", "001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","110","11","1101"
        };
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                System.out.println(strings[i]+"##"+strings[j]);
                assertEquals(alu.adder(strings[i],strings[j],'1',4),qALU.adder(strings[i],strings[j],'1',4));
                System.out.println(strings[i]+"#*#"+strings[j]);
                assertEquals(alu.adder(strings[i],strings[j],'0',4),qALU.adder(strings[i],strings[j],'0',4));
            }
        }
    }

    @org.junit.Test
    public void integerAddition() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                System.out.println(strings[i]+"#*#"+strings[j]);
                assertEquals(alu.integerAddition(strings[i],strings[j],4),wALU.integerAddition(strings[i],strings[j],4));
            }
        }
    }
    @org.junit.Test
    public void integerAddition2() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                System.out.println(strings[i]+"##"+strings[j]);
                assertEquals(alu.integerAddition(strings[i],strings[j],4),qALU.integerAddition(strings[i],strings[j],4));
            }
        }
    }

    @org.junit.Test
    public void integerSubtraction() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
//                System.out.println(strings[i]+"##"+strings[j]);
//                assertEquals(alu.integerSubtraction(strings[i],strings[j],4),wALU.integerSubtraction(strings[i],strings[j],4));
                System.out.println(strings[i]+"#*#"+strings[j]);
                assertEquals(alu.integerSubtraction(strings[i],strings[j],8),qALU.integerSubtraction(strings[i],strings[j],8));
            }
        }
    }

    @org.junit.Test
    public void integerMultiplication() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                System.out.println(strings[i]+"##"+strings[j]);
                assertEquals(alu.integerMultiplication(strings[i],strings[j],4),qALU.integerMultiplication(strings[i],strings[j],4));
                System.out.println(strings[i]+"#*#"+strings[j]);
                assertEquals(alu.integerMultiplication(strings[i],strings[j],8),qALU.integerMultiplication(strings[i],strings[j],8));
            }
        }
    }

    @org.junit.Test
    public void integerDivision() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                if(strings[j].equals("0000")){
                    continue;
                }
                System.out.println(strings[i]+"##"+strings[j]);
                assertEquals(alu.integerDivision(strings[i],strings[j],4),wALU.integerDivision(strings[i],strings[j],4));
                System.out.println(strings[i]+"#*#"+strings[j]);
                assertEquals(alu.integerDivision(strings[i],strings[j],8),wALU.integerDivision(strings[i],strings[j],8));
            }
        }
    }

    @org.junit.Test
    public void signedAddition() throws Exception {
        String[] strings={
                "0000", "0001", "0010", "0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1110","1111","1101"
        };
        for(int i=1;i<16;i++){
            for(int j=1;j<16;j++){
                System.out.println(strings[i]+"##"+strings[j]);
                assertEquals(alu.signedAddition(strings[i],strings[j],4),qALU.signedAddition(strings[i],strings[j],4));
            }
        }
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