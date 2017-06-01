import java.util.ArrayList;
import java.util.HashSet;

/**
 * 模拟ALU进行整数和浮点数的四则运算
 *
 * @author “161250168_徐强”
 */

public class qALU {

    /**
     * 生成十进制整数的二进制补码表示。<br/>
     * 例：integerRepresentation("9", 8)
     *
     * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
     * @param length 二进制补码表示的长度
     * @return number的二进制补码表示，长度为length
     */
    public String integerRepresentation (String number, int length) {

        // TODO YOUR CODE HERE.
        String result="";
        int num=Integer.parseInt(number);
        for(int i=0;i<length;i++){
            int x=num&(1<<i);
            if(x==0)
                result="0"+result;
            else
                result="1"+result;

        }





        return result;
    }
    /**
     * 生成十进制浮点数的二进制表示。
     * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
     * 舍入策略为向0舍入。<br/>
     * 例：floatRepresentation("11.375", 8, 11)
     *
     * @param number  十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
     * @param eLength 指数的长度，取值大于等于 4
     * @param sLength 尾数的长度，取值大于等于 4
     * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
     */
    public String floatRepresentation (String number, int eLength, int sLength) {
        // TODO YOUR CODE HERE.

        if(number.equals("+Inf")){    //正无穷
            String m="0";
            for(int i=0;i<eLength;i++)
                m=m+"1";
            for(int i=0;i<sLength;i++)
                m=m+"0";
            return m;
        }
        else{
            if(number.equals("-Inf")){   //负无穷
                String m="1";
                for(int i=0;i<eLength;i++)
                    m=m+"1";
                for(int i=0;i<sLength;i++)
                    m=m+"0";
                return m;
            }
            else{
                double num=Double.parseDouble(number);
                if(num==0){                   //   0
                    String m="0";
                    for(int i=0;i<eLength;i++)
                        m=m+"0";
                    for(int i=0;i<sLength;i++)
                        m=m+"0";
                    return m;
                }
                else{
                    if(Math.abs(num)>Math.pow(2, -Math.pow(2, eLength-1)+2)) {            //规格化数
                        int i=0;      //指数
                        String s="0";    //返回的结果
                        if(num<0){
                            num=-num;
                            s="1";
                        }
                        int x=(int)num;
                        double y=num-x;
                        String str=Integer.toBinaryString(x);//整数部分

                        String result="";                    //小数部分

                        while(y!=0){
                            y=y*2;
                            if(y>=1){
                                y=y-1;
                                result=result+"1";
                            }
                            else
                                result=result+"0";
                        }
                        if(x==0){
                            for(int j=0;j<result.length();j++){
                                if(result.charAt(j)=='1'){
                                    i=-(j+1)+(int)(Math.pow(2,eLength-1)-1);
                                    result=result.substring(j);
                                    break;
                                }
                            }
                        }
                        else{
                            i=(int)(Math.pow( 2,eLength-1)-1)+str.length()-1;
                            result=str.substring(1)+result;
                        }
                        String m=Integer.toBinaryString(i);   //指数部分
                        for(int j=0;j<eLength-m.length();j++)
                            m="0"+m;

                        int a=result.length();
                        if(a>sLength)          //尾数
                            result=result.substring(0,sLength);
                        else{
                            for(int j=0;j<sLength-a;j++)
                                result=result+"0";
                        }
                        s=s+m+result;
                        return s ;
                    }
                    else{                            //非规格化数
                        return null;
                    }
                }
            }
        }


    }

    /**
     * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
     * 例：ieee754("11.375", 32)
     *
     * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
     * @param length 二进制表示的长度，为32或64
     * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
     */
    public String ieee754(String number, int length) {
        // TODO YOUR CODE HERE.
        if (length == 32)
            return floatRepresentation(number, 8, 23);
        else
            return floatRepresentation(number, 11, 52);
    }

    /**
     * 计算二进制补码表示的整数的真值。<br/>
     * 例：integerTrueValue("00001001")
     *
     * @param operand 二进制补码表示的操作数
     * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
     */
    public String integerTrueValue(String operand) {
        // TODO YOUR CODE HERE.
        String result = null;
        char[] x = operand.toCharArray();
        int number = 0;
        if (x[0] == '1') {
            for (int i = 0; i < x.length; i++) {
                number = number + (int) (x[i] - '0') * (int) Math.pow(2, x.length - 1 - i);
            }
            number = (int) Math.pow(2, x.length) - number;
            result = "-" + String.valueOf(number);
        } else {
            for (int i = 0; i < x.length; i++) {
                number = number + (int) (x[i] - '0') * (int) Math.pow(2, x.length - 1 - i);
            }
            result = String.valueOf(number);
        }
        return result;
    }

    /**
     * 计算二进制原码表示的浮点数的真值。<br/>
     * 例：floatTrueValue("01000001001101100000", 8, 11)
     *
     * @param operand 二进制表示的操作数
     * @param eLength 指数的长度，取值大于等于 4
     * @param sLength 尾数的长度，取值大于等于 4
     * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
     */
    public String floatTrueValue(String operand, int eLength, int sLength) {
        String result = "";                     //返回结果

        if (operand.charAt(0) == '1')            //符号位
            result = "-";

        String s = operand.substring(1, 1 + eLength);           //指数
        String str = operand.substring(1 + eLength);            //指数

        HashSet<Character> x = new HashSet<Character>();
        for (int i = 0; i < s.length(); i++) {
            x.add(s.charAt(i));
        }

        if (x.contains('0') && x.size() == 1) {
            str = "0" + str;
            int m = (int) Math.pow(2, eLength - 1) - 2;    //右移位数
            double r = 0;
            for (int i = 0; i < str.length(); i++)
                r += Math.pow(2, -i + m) * Integer.parseInt(Character.toString(str.charAt(i)));

            result += String.valueOf(r);


        } else {
            if (x.contains('1') && x.size() == 1) {
                HashSet<Character> x1 = new HashSet<Character>();
                for (int i = 0; i < str.length(); i++) {
                    x1.add(str.charAt(i));
                }
                if (x1.contains('0') && x1.size() == 1)
                    result += "Inf";
                else
                    result = "NaN";


            } else {
                double r = 0;
                str = "1" + str;
                int m = Integer.parseInt(s, 2) - (int) Math.pow(2, eLength - 1) + 1;
                if (m >= 0) {
                    if (m > str.length() - 2) {
                        for (int i = 0; i < m - str.length()+1; i++) {
                            str += "0";
                        }
                        for (int i = 0; i < str.length(); i++)
                            r += Math.pow(2, str.length() - 1 - i) * Integer.parseInt(Character.toString(str.charAt(i)));
                        result+=String.valueOf(r);
                    } else {
                        for (int i = 0; i <= m; i++)
                            r += Math.pow(2, m - i) * Integer.parseInt(Character.toString(str.charAt(i)));
                        for (int i = m + 1; i < str.length(); i++)
                            r += Math.pow(2, -i + m) * Integer.parseInt(Character.toString(str.charAt(i)));
                        result += String.valueOf(r);
                    }


                } else {
                    for (int i = 0; i < str.length(); i++)
                        r += Math.pow(2, -i + m) * Integer.parseInt(Character.toString(str.charAt(i)));

                    result += String.valueOf(r);


                }


            }


        }


        return result;


    }

    /**
     * 按位取反操作。<br/>
     * 例：negation("00001001")
     *
     * @param operand 二进制表示的操作数
     * @return operand按位取反的结果
     */
    public String negation(String operand) {
        // TODO YOUR CODE HERE.
        char[] x = operand.toCharArray();
        for (int i = 0; i < x.length; i++) {
            if (x[i] == '0')
                x[i] = '1';
            else
                x[i] = '0';
        }
        return String.valueOf(x);
    }

    /**
     * 左移操作。<br/>
     * 例：leftShift("00001001", 2)
     *
     * @param operand 二进制表示的操作数
     * @param n       左移的位数
     * @return operand左移n位的结果
     */
    public String leftShift(String operand, int n) {
        // TODO YOUR CODE HERE.
        char[] x = operand.toCharArray();
        for (int j = 0; j < x.length - n; j++) {
            x[j] = x[j + n];
        }
        for (int j = x.length - n; j < x.length; j++) {
            x[j] = '0';
        }

        return String.valueOf(x);
    }

    /**
     * 逻辑右移操作。<br/>
     * 例：logRightShift("11110110", 2)
     *
     * @param operand 二进制表示的操作数
     * @param n       右移的位数
     * @return operand逻辑右移n位的结果
     */
    public String logRightShift(String operand, int n) {
        // TODO YOUR CODE HERE.
        char[] x = operand.toCharArray();
        char[] y = operand.toCharArray();
        for (int j = n; j < x.length; j++) {
            x[j] = y[j - n];
        }
        for (int i = 0; i < n; i++) {
            x[i] = '0';
        }
        return String.valueOf(x);
    }

    /**
     * 算术右移操作。<br/>
     * 例：logRightShift("11110110", 2)
     *
     * @param operand 二进制表示的操作数
     * @param n       右移的位数
     * @return operand算术右移n位的结果
     */
    public String ariRightShift(String operand, int n) {

        // TODO YOUR CODE HERE.
        char[] x = operand.toCharArray();
        char[] y = operand.toCharArray();
        if (x[0] == '0') {
            for (int j = n; j < x.length; j++) {
                x[j] = y[j - n];
            }
            for (int i = 0; i < n; i++) {
                x[i] = '0';
            }
        } else {
            for (int j = n; j < x.length; j++) {
                x[j] = y[j - n];
            }
            for (int i = 0; i < n; i++) {
                x[i] = '1';
            }
        }


        return String.valueOf(x);
    }

    /**
     * 全加器，对两位以及进位进行加法运算。<br/>
     * 例：fullAdder('1', '1', '0')
     *
     * @param x 被加数的某一位，取0或1
     * @param y 加数的某一位，取0或1
     * @param c 低位对当前位的进位，取0或1
     * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
     */
    public String fullAdder(char x, char y, char c) {
        // TODO YOUR CODE HERE.
        int x1 = Integer.parseInt(Character.toString(x));
        int y1 = Integer.parseInt(Character.toString(y));
        int c1 = Integer.parseInt(Character.toString(c));
        int i = x1 ^ y1 ^ c1;
        int j = (x1 & y1 | x1 & c1 | y1 & c1);

        return (String.valueOf(j) + String.valueOf(i));
    }

    /**
     * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
     * 例：claAdder("1001", "0001", '1')
     *
     * @param operand1 4位二进制表示的被加数
     * @param operand2 4位二进制表示的加数
     * @param c        低位对当前位的进位，取0或1
     * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环得
     */
    public String claAdder(String operand1, String operand2, char c) {
        // TODO YOUR CODE HERE.
        char[] op1 = operand1.toCharArray();                                    //将string转化为int数组
        char[] op2 = operand2.toCharArray();
        int[] intop1 = new int[op1.length];
        int[] intop2 = new int[op2.length];
        for (int i = 0; i < op1.length; i++) {
            intop1[i] = Integer.parseInt(Character.valueOf(op1[i]).toString());
        }
        for (int i = 0; i < op2.length; i++) {
            intop2[i] = Integer.parseInt(Character.valueOf(op2[i]).toString());
        }

        int[] p = new int[op1.length];
        int[] g = new int[op1.length];
        for (int i = 0; i < op1.length; i++) {
            p[i] = intop1[i] | intop2[i];
            g[i] = intop1[i] & intop2[i];
        }

        int[] C = new int[op1.length + 1];
        C[4] = Integer.parseInt(Character.valueOf(c).toString());

        C[3] = g[3] | (p[3] & C[4]);
        C[2] = g[2] | (p[2] & g[3]) | (p[2] & p[3] & C[4]);
        C[1] = g[1] | (p[1] & g[2]) | (p[1] & p[2] & g[3]) | (p[1] & p[2] & p[3] & C[4]);
        C[0] = g[0] | (p[0] & g[1]) | (p[0] & p[1] & g[2]) | (p[0] & p[1] & p[2] & g[3]) | (p[0] & p[1] & p[2] & p[3] & C[4]);

        char[] F = new char[4];
        for (int i = 0; i < 4; i++) {
            F[i] = fullAdder(String.valueOf(intop1[i]).charAt(0), String.valueOf(intop2[i]).charAt(0), String.valueOf(C[i + 1]).charAt(0)).charAt(1);
        }

        String str = "" + C[0];
        for (char i : F) {
            str = str + i;
        }

        return str;
    }

    /**
     * 加一器，实现操作数加1的运算。
     * 需要采用与门、或门、异或门等模拟，
     * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
     * {@link #claAdder(String, String, char) claAdder}、
     * {@link #adder(String, String, char, int) adder}、
     * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
     * 例：oneAdder("00001001")
     *
     * @param operand 二进制补码表示的操作数
     * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
     */
    public String oneAdder(String operand) {
        // TODO YOUR CODE HERE.
        String result = "";
        char[] str = operand.toCharArray();
        if ((Integer.parseInt(operand, 2) + 1) == Math.pow(2, str.length - 1)) {
            result = result + "11";
            for (int i = 1; i < str.length; i++)
                result = result + "0";
        } else {
            int[] x = new int[str.length];
            int[] y = new int[str.length];
            for (int i = 0; i < str.length; i++) {
                x[i] = Integer.parseInt(Character.toString(str[i]));
            }
            for (int i = 0; i < str.length - 1; i++) {
                y[i] = 0;
            }
            y[str.length - 1] = 1;

            int[] f = new int[str.length];
            int[] c = new int[str.length + 1];
            c[c.length - 1] = 0;
            for (int i = str.length - 1; i >= 0; i--) {
                f[i] = x[i] ^ y[i] ^ c[i + 1];
                c[i] = (x[i] & c[i + 1]) | (y[i] & c[i + 1]) | (x[i] & y[i]);
            }
            if (c[0] == 1)
                c[0] = 0;
            result = result + c[0];
            for (int i : f)
                result = result + i;

        }
        return result;
    }

    /**
     * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
     * 例：adder("0100", "0011", ‘0’, 8)
     *
     * @param operand1 二进制补码表示的被加数
     * @param operand2 二进制补码表示的加数
     * @param c        最低位进位
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
     * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
     */
    public String adder(String operand1, String operand2, char c, int length) {
        // TODO YOUR CODE HERE.
        if (length > 4) {
            ArrayList<String> data = new ArrayList<String>();
            int x = operand1.length();
            int y = operand2.length();
            if (operand1.charAt(0) == '0') {                               //符号拓展
                for (int i = 0; i < length - x; i++)
                    operand1 = "0" + operand1;
            } else {
                for (int i = 0; i < length - x; i++)
                    operand1 = "1" + operand1;
            }
            if (operand2.charAt(0) == '0') {
                for (int i = 0; i < length - y; i++)
                    operand2 = "0" + operand2;
            } else {
                for (int i = 0; i < length - y; i++)
                    operand2 = "1" + operand2;
            }
            for (int i = 0; i < length - 4; i += 4) {
                String result = claAdder(operand1.substring(operand1.length() - 4 - i, operand1.length() - i)
                        , operand2.substring(operand2.length() - 4 - i, operand1.length() - i)
                        , c);
                c = result.charAt(0);
                data.add(result);
            }

            operand1 = operand1.substring(0, 4);
            operand2 = operand2.substring(0, 4);
            char[] op1 = operand1.toCharArray();                                    //将string转化为int数组
            char[] op2 = operand2.toCharArray();
            int[] intop1 = new int[op1.length];
            int[] intop2 = new int[op2.length];
            for (int i = 0; i < op1.length; i++) {
                intop1[i] = Integer.parseInt(Character.valueOf(op1[i]).toString());
            }
            for (int i = 0; i < op2.length; i++) {
                intop2[i] = Integer.parseInt(Character.valueOf(op2[i]).toString());
            }

            int[] p = new int[op1.length];
            int[] g = new int[op1.length];
            for (int i = 0; i < op1.length; i++) {
                p[i] = intop1[i] | intop2[i];
                g[i] = intop1[i] & intop2[i];
            }

            int[] C = new int[op1.length + 1];
            C[4] = Integer.parseInt(Character.valueOf(c).toString());

            C[3] = g[3] | (p[3] & C[4]);
            C[2] = g[2] | (p[2] & g[3]) | (p[2] & p[3] & C[4]);
            C[1] = g[1] | (p[1] & g[2]) | (p[1] & p[2] & g[3]) | (p[1] & p[2] & p[3] & C[4]);
            C[0] = g[0] | (p[0] & g[1]) | (p[0] & p[1] & g[2]) | (p[0] & p[1] & p[2] & g[3]) | (p[0] & p[1] & p[2] & p[3] & C[4]);
            int i1 = C[0] ^ C[1];

            int[] F = new int[4];
            for (int i = 0; i < 4; i++) {
                F[i] = intop1[i] ^ intop2[i] ^ C[i + 1];
            }

            String str = "";
            for (int i = 0; i < data.size(); i++)
                str = data.get(i).substring(1) + str;

            for (int m : F)
                str = m + str;
            str = i1 + str;

            return str;
        } else {
            char[] op1 = operand1.toCharArray();                                    //将string转化为int数组
            char[] op2 = operand2.toCharArray();
            int[] intop1 = new int[op1.length];
            int[] intop2 = new int[op2.length];
            for (int i = 0; i < op1.length; i++) {
                intop1[i] = Integer.parseInt(Character.valueOf(op1[i]).toString());
            }
            for (int i = 0; i < op2.length; i++) {
                intop2[i] = Integer.parseInt(Character.valueOf(op2[i]).toString());
            }

            int[] p = new int[op1.length];
            int[] g = new int[op1.length];
            for (int i = 0; i < op1.length; i++) {
                p[i] = intop1[i] | intop2[i];
                g[i] = intop1[i] & intop2[i];
            }

            int[] C = new int[op1.length + 1];
            C[4] = Integer.parseInt(Character.valueOf(c).toString());

            C[3] = g[3] | (p[3] & C[4]);
            C[2] = g[2] | (p[2] & g[3]) | (p[2] & p[3] & C[4]);
            C[1] = g[1] | (p[1] & g[2]) | (p[1] & p[2] & g[3]) | (p[1] & p[2] & p[3] & C[4]);
            C[0] = g[0] | (p[0] & g[1]) | (p[0] & p[1] & g[2]) | (p[0] & p[1] & p[2] & g[3]) | (p[0] & p[1] & p[2] & p[3] & C[4]);

            int i2 = C[0] ^ C[1];
            char[] F = new char[4];
            for (int i = 0; i < 4; i++) {
                F[i] = fullAdder(String.valueOf(intop1[i]).charAt(0), String.valueOf(intop2[i]).charAt(0), String.valueOf(C[i + 1]).charAt(0)).charAt(1);
            }

            String str = "" + i2;
            for (char i : F) {
                str = str + i;
            }
            return str;

        }

    }

    /**
     * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
     * 例：integerAddition("0100", "0011", 8)
     *
     * @param operand1 二进制补码表示的被加数
     * @param operand2 二进制补码表示的加数
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
     * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
     */
    public String integerAddition(String operand1, String operand2, int length) {
        // TODO YOUR CODE HERE.
        String result = adder(operand1, operand2, '0', length);
        return result;
    }

    /**
     * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
     * 例：integerSubtraction("0100", "0011", 8)
     *
     * @param operand1 二进制补码表示的被减数
     * @param operand2 二进制补码表示的减数
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
     * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
     */
    public String integerSubtraction(String operand1, String operand2, int length) {
        // TODO YOUR CODE HERE.
        boolean bl = false;
        if (operand1.charAt(0) != operand2.charAt(0))
            bl = true;

        char[] x = operand2.toCharArray();
        for (int i = 0; i < x.length; i++) {
            if (x[i] == '1')
                x[i] = '0';
            else
                x[i] = '1';

        }
        operand2 = String.valueOf(x);
        String str = oneAdder(operand2);
        String result = str.substring(1, str.length());
        if (bl) {
            return adder(operand1, result, '0', length);
        } else {
            return "0" + adder(operand1, result, '0', length).substring(1);
        }
    }

    /**
     * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
     * 例：integerMultiplication("0100", "0011", 8)
     *
     * @param operand1 二进制补码表示的被乘数
     * @param operand2 二进制补码表示的乘数
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
     * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
     */
    public String integerMultiplication(String operand1, String operand2, int length) {
        // TODO YOUR CODE HERE.

        char[] x = new char[length + operand2.length() + 1];

        for (int i = 0; i < length; i++)
            x[i] = '0';
        for (int i = length; i < length + operand2.length(); i++)
            x[i] = operand2.charAt(i - length);
        x[x.length - 1] = '0';

        for (int j = 0; j < operand2.length(); j++) {
            String str = "" + x[x.length - 2] + x[x.length - 1];
            if (str.equals("11") || str.equals("00")) {
                char[] y = ariRightShift(String.valueOf(x), 1).toCharArray();
                for (int i = 0; i < y.length; i++)
                    x[i] = y[i];
            } else {
                if (str.equals("01")) {
                    String s = String.copyValueOf(x, 0, length);
                    String s1 = adder(s, operand1, '0', length).substring(1);
                    for (int i = 0; i < length; i++)
                        x[i] = s1.charAt(i);
                    char[] y = ariRightShift(String.valueOf(x), 1).toCharArray();
                    for (int i = 0; i < y.length; i++)
                        x[i] = y[i];
                } else {
                    String s = String.copyValueOf(x, 0, length);
                    String s1 = integerSubtraction(s, operand1, length).substring(1);
                    for (int i = 0; i < length; i++)
                        x[i] = s1.charAt(i);
                    char[] y = ariRightShift(String.valueOf(x), 1).toCharArray();
                    for (int i = 0; i < y.length; i++)
                        x[i] = y[i];
                }
            }

        }
        HashSet<Character> m = new HashSet<Character>();
        for (int i = 0; i <= operand2.length(); i++)
            m.add(x[i]);
        if (m.size() == 1)
            return "0" + String.copyValueOf(x, operand2.length(), length);
        else
            return "1" + String.copyValueOf(x, operand2.length(), length);
    }

    /**
     * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
     * 例：integerDivision("0100", "0011", 8)
     *
     * @param operand1 二进制补码表示的被除数
     * @param operand2 二进制补码表示的除数
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
     * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
     */
    public String integerDivision(String operand1, String operand2, int length) {
        // TODO YOUR CODE HERE.
        if (Integer.parseInt(operand2) == 0) {
            return "NaN";
        } else {
            HashSet<Character> m = new HashSet<Character>();
            for (int i = 0; i < operand2.length(); i++)
                m.add(operand2.charAt(i));
            if (Integer.parseInt(operand2) == 1) {
                String str = "0";
                for (int i = 0; i < length; i++)
                    str = str + "0";
                for (int i = 0; i < length - operand1.length(); i++)
                    str = str + operand1.charAt(0);
                str = str + operand1;
                return str;
            } else {
                if (m.size() == 1) {
                    operand1 = negation(operand1);
                    String result = oneAdder(operand1);
                    operand1 = result.substring(1);
                    String str = "" + result.charAt(0);
                    for (int i = 0; i < length; i++)
                        str = str + "0";
                    for (int i = 0; i < length - operand1.length(); i++)
                        str = str + operand1.charAt(0);
                    str = str + operand1;
                    return str;

                } else {
                    char[] x = new char[2 * length];
                    for (int i = x.length - operand1.length(); i < x.length; i++) {
                        x[i] = operand1.charAt(i - (x.length - operand1.length()));
                    }
                    for (int i = 0; i < x.length - operand1.length(); i++) {
                        x[i] = operand1.charAt(0);
                    }

                    for (int i = 0; i < length; i++) {
                        if (x[0] == operand2.charAt(0)) {
                            char c;
                            String str = leftShift(String.valueOf(x), 1);
                            x = str.toCharArray();
                            String s = integerSubtraction(String.copyValueOf(x, 0, length), operand2, length).substring(1);
                            for (int j = 0; j < length; j++)
                                x[j] = s.charAt(j);
                            if (x[0] == operand2.charAt(0)) {
                                c = '1';
                            } else
                                c = '0';

                            x[x.length - 1] = c;
                        } else {
                            char c;
                            String str = leftShift(String.valueOf(x), 1);
                            x = str.toCharArray();
                            String s = adder(String.copyValueOf(x, 0, length), operand2, '0', length).substring(1);
                            for (int j = 0; j < length; j++)
                                x[j] = s.charAt(j);
                            if (x[0] == operand2.charAt(0)) {
                                c = '1';
                            } else
                                c = '0';

                            x[x.length - 1] = c;
                        }
                    }
                    if (!(operand1.charAt(0) == operand2.charAt(0))) {
                        String str = oneAdder(String.copyValueOf(x, length, length)).substring(1);
                        for (int i = length; i < x.length; i++)
                            x[i] = str.charAt(i - length);
                    }
                    if (!(x[0] == operand1.charAt(0))) {
                        if (operand1.charAt(0) == operand2.charAt(0)) {
                            String str = adder(String.copyValueOf(x, 0, length), operand2, '0', length).substring(1);
                            for (int i = 0; i < length; i++) {
                                x[i] = str.charAt(i);
                            }
                        } else {
                            String str = integerSubtraction(String.copyValueOf(x, 0, length), operand2, length).substring(1);
                            for (int i = 0; i < length; i++) {
                                x[i] = str.charAt(i);
                            }
                        }
                    }
                    String str = "0";
                    for (int i = 0; i < x.length; i++)
                        str = str + x[i];
                    return str;
                }
            }

        }

    }

    /**
     * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
     * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
     * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
     * 例：signedAddition("1100", "1011", 8)
     *
     * @param operand1 二进制原码表示的被加数，其中第1位为符号位
     * @param operand2 二进制原码表示的加数，其中第1位为符号位
     * @param length   存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
     * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
     */
    public String signedAddition(String operand1, String operand2, int length) {
        // TODO YOUR CODE HERE.
        char x = operand1.charAt(0);
        char y = operand2.charAt(0);
        operand1 = "0" + operand1.substring(1);
        operand2 = "0" + operand2.substring(1);
        if (x == y) {
            String str = adder(operand1, operand2, '0', length);
            return ("" + str.charAt(0) + x + str.substring(1));
        } else {
            String str = integerSubtraction(operand1, operand2, length);
            if (str.charAt(1) == '0') {
                return ("" + str.charAt(0) + x + str.substring(1));
            } else {
                String result = str.substring(1);
                str = negation(result);
                str = oneAdder(str);
                return ("" + str.charAt(0) + y + str.substring(1));
            }
        }
    }

    public String add(String operand1, String operand2, int length) {     //原码加法
        char x = operand1.charAt(0);
        char y = operand2.charAt(0);
        String s1 = operand1.substring(1);
        String s2 = operand2.substring(1);

        if (x == y) {                                        //同号相加
            char[] m1 = s1.toCharArray();
            char[] m2 = s2.toCharArray();
            int[] op1 = new int[m1.length];
            int[] op2 = new int[m2.length];
            int[] c = new int[m1.length + 1];
            c[c.length - 1] = 0;
            int[] f = new int[m1.length];
            for (int j = 0; j < m1.length; j++) {
                op1[j] = Integer.parseInt(Character.toString(m1[j]));
                op2[j] = Integer.parseInt(Character.toString(m2[j]));
            }
            for (int j = m1.length - 1; j >= 0; j--) {
                f[j] = op1[j] ^ op2[j] ^ c[j + 1];
                c[j] = (op1[j] & op2[j]) | (op1[j] & c[j + 1]) | (op2[j] & c[j + 1]);
            }

            String result = "" + x + c[0];
            for (int i : f)
                result += i;
            return result;

        } else {
            //异号相减
            s2 = negation(s2);
            s2 = oneAdder(s2).substring(1);
            char[] m1 = s1.toCharArray();
            char[] m2 = s2.toCharArray();
            int[] op1 = new int[m1.length];
            int[] op2 = new int[m2.length];
            int[] c = new int[m1.length + 1];
            c[c.length - 1] = 0;
            int[] f = new int[m1.length];
            for (int j = 0; j < m1.length; j++) {
                op1[j] = Integer.parseInt(Character.toString(m1[j]));
                op2[j] = Integer.parseInt(Character.toString(m2[j]));
            }
            for (int j = m1.length - 1; j >= 0; j--) {
                f[j] = op1[j] ^ op2[j] ^ c[j + 1];
                c[j] = (op1[j] & op2[j]) | (op1[j] & c[j + 1]) | (op2[j] & c[j + 1]);
            }

            if (c[0] == 1) {
                String result = "" + x;
                for (int i : f)
                    result += i;
                return result;
            } else {
                String result = "" + y;
                String str = String.valueOf(f);
                str = negation(str);
                str = oneAdder(str).substring(1);
                return result;
            }


        }


    }

    /**
     * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
     * 例：floatAddition("00111111010100000", "0011111100000", 8, 8, 8)
     *
     * @param operand1 二进制表示的被加数
     * @param operand2 二进制表示的加数
     * @param eLength  指数的长度，取值大于等于 4
     * @param sLength  尾数的长度，取值大于等于 4
     * @param gLength  保护位的长度
     * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
     */
    public String floatAddition(String operand1, String operand2, int eLength, int sLength, int gLength) {
        // TODO YOUR CODE HERE.
        String s1 = operand1.substring(1, eLength + 1);
        String s2 = operand2.substring(1, eLength + 1);
        int a = Integer.parseInt(s1, 2); //operand1 指数
        int b = Integer.parseInt(s2, 2); //operand2指数

        if (b > a)
            s1 = s2;                 //s1为结果的指数

        String m1 = operand1.substring(eLength + 1);
        String m2 = operand2.substring(eLength + 1);   //尾数
        for (int j = 0; j < gLength; j++) {
            m1 += "0";
            m2 += "0";
        }
        if (a == 0) {
            m1 = "0" + m1;
        } else
            m1 = "1" + m1;

        if (b == 0) {
            m2 = "0" + m2;

        } else {
            m2 = "1" + m2;
        }

        if (b > a)
            m1 = logRightShift(m1, b - a);
        else
            m2 = logRightShift(m2, a - b);

        m1 = operand1.charAt(0) + m1;
        m2 = operand2.charAt(0) + m2;

        String result = add(m1, m2, m1.length());   //尾数运算
        if (m1.charAt(0) == m2.charAt(0)) {
            int o = 0;
            if (result.charAt(1) == '1') {  //右规

                if ((Integer.parseInt(s1, 2) == (Math.pow(2, eLength) - 1)) || (Integer.parseInt(s1, 2) == (Math.pow(2, eLength) - 2)))
                    o = 1;
                s1 = Integer.toBinaryString(Integer.parseInt(s1, 2) + 1);
                int l = s1.length();
                for (int i = 0; i < eLength - l; i++)
                    s1 = "0" + s1;
                return "" + o + m1.charAt(0) + s1 + result.substring(2, sLength + 2);
            } else
                return "" + o + m1.charAt(0) + s1 + result.substring(3, sLength + 3);
        } else {
            int o = 0;
            String k = result.substring(1);                               //左规
            for (int j = 0; j < k.length(); j++) {
                if (k.charAt(j) == '1') {
                    if (Integer.parseInt(s1, 2) == j) {
                        k = leftShift(k, j - 1);
                        s1 = "";
                        for (int i = 0; i < eLength; i++)
                            s1 += "0";

                    } else {
                        if (Integer.parseInt(s1, 2) < j) {
                            k = leftShift(k, j - Integer.parseInt(s1, 2));
                            s1 = "";
                            for (int i = 0; i < eLength; i++)
                                s1 += "0";


                        } else {
                            k = leftShift(k, j);
                            s1 = Integer.toBinaryString(Integer.parseInt(s1, 2) - j);
                            int l = s1.length();
                            for (int i = 0; i < eLength - l; i++)
                                s1 = "0" + s1;

                        }
                    }
                    break;
                }

            }
            return "" + o + result.charAt(0) + s1 + k.substring(1, sLength + 1);
        }


    }

    public String Add(String operand1, String operand2, int length) {             //指数加法
        char[] x = operand1.toCharArray();
        char[] y = operand2.toCharArray();
        int[] op1 = new int[x.length];
        int[] op2 = new int[y.length];
        int[] c = new int[x.length + 1];
        c[c.length - 1] = 0;
        int[] f = new int[x.length];
        for (int i = 0; i < x.length; i++) {
            op1[i] = Integer.parseInt(Character.toString(x[i]));
            op2[i] = Integer.parseInt(Character.toString(y[i]));
        }
        for (int i = x.length - 1; i >= 0; i--) {
            f[i] = op1[i] ^ op2[i] ^ c[i + 1];
            c[i] = (op1[i] & op2[i]) | (op1[i] & c[i + 1]) | (op2[i] & c[i + 1]);
        }
        String result = "" + c[0];
        for (int i : f)
            result += i;
        return result;
    }

    public String mult(String operand1, String operand2, int length) {           //原码乘法
        char[] x = new char[2 * length + 1];
        for (int i = 0; i < length + 1; i++)
            x[i] = '0';
        for (int i = length + 1; i < x.length; i++)
            x[i] = operand2.charAt(i - length - 1);

        for (int i = length - 1; i >= 0; i--) {
            if (operand2.charAt(i) == '1') {
                String result = Add(String.copyValueOf(x, 1, length), operand1, length);
                for (int j = 0; j < length + 1; j++)
                    x[j] = result.charAt(j);
                String str = logRightShift(String.valueOf(x), 1);
                for (int j = 0; j < str.length(); j++)
                    x[j] = str.charAt(j);
            } else {
                String str = logRightShift(String.valueOf(x), 1);
                for (int j = 0; j < str.length(); j++)
                    x[j] = str.charAt(j);
            }
        }

        return String.copyValueOf(x, 1, 2 * length);

    }


    /**
     * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
     * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
     *
     * @param operand1 二进制表示的被减数
     * @param operand2 二进制表示的减数
     * @param eLength  指数的长度，取值大于等于 4
     * @param sLength  尾数的长度，取值大于等于 4
     * @param gLength  保护位的长度
     * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
     */
    public String floatSubtraction(String operand1, String operand2, int eLength, int sLength, int gLength) {
        // TODO YOUR CODE HERE.
        if (operand2.charAt(0) == '1') {
            operand2 = "0" + operand2.substring(1);
        } else
            operand2 = "1" + operand2.substring(1);
        return floatAddition(operand1, operand2, eLength, sLength, gLength);
    }

    /**
     * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
     * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
     *
     * @param operand1 二进制表示的被乘数
     * @param operand2 二进制表示的乘数
     * @param eLength  指数的长度，取值大于等于 4
     * @param sLength  尾数的长度，取值大于等于 4
     * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
     */
    public String floatMultiplication(String operand1, String operand2, int eLength, int sLength) {
        // TODO YOUR CODE HERE.
        int o = 0;
        if (operand1.charAt(0) != operand2.charAt(0))
            o = 1;
        boolean n3 = true, n4 = true;
        for (int p = 0; p < operand1.length(); p++) {
            if (operand1.charAt(p) != '0') {
                n3 = false;
                break;
            }
        }
        for (int p = 0; p < operand2.length(); p++) {
            if (operand2.charAt(p) != '0') {
                n4 = false;
                break;
            }
        }
        if (n3 || n4) {
            String result = "";
            for (int i = 0; i < 2 + eLength + sLength; i++) {                              //判断是否有操作数为0
                result += "0";
            }
            return result;
        } else {
            char h = '0';
            String s1 = operand1.substring(1, eLength + 1);                     //指数
            String s2 = operand2.substring(1, eLength + 1);
            int x = Integer.parseInt(s1, 2) - (int) Math.pow(2, eLength - 1) + 1 + Integer.parseInt(s2, 2) - (int) Math.pow(2, eLength - 1) + 1;            //指数相加的结果
            String s = Integer.toBinaryString(x + (int) Math.pow(2, eLength - 1) - 1);
            if (x + (int) Math.pow(2, eLength - 1) - 1 > (int) Math.pow(2, eLength) - 2) {
                h = '1';
                s = s.substring(s.length() - eLength);
            } else {
                int l = s.length();
                for (int i = 0; i < eLength - l; i++)
                    s = "0" + s;
            }
            String m1 = operand1.substring(eLength + 1);
            String m2 = operand2.substring(eLength + 1);                       //尾数

            boolean n1 = true, n2 = true;                                                             //判断是否为规格化数
            for (int i = 0; i < s1.length(); i++) {
                if (s1.charAt(i) != '0') {
                    n1 = false;
                    break;
                }
            }
            for (int i = 0; i < s1.length(); i++) {
                if (s2.charAt(i) != '0') {
                    n2 = false;
                    break;
                }
            }
            if (n1)
                m1 = "0" + m1;                                                                    //加上隐藏位
            else
                m1 = "1" + m1;

            if (n2)
                m2 = "0" + m2;
            else
                m2 = "1" + m2;

            String str = mult(m1, m2, sLength + 1);

            if (str.charAt(0) == '1') {
                str = str.substring(1, sLength + 1);
                if (Integer.parseInt(s, 2) == Math.pow(2, s.length()) - 2) {
                    h = '1';
                }
                s = oneAdder(s).substring(1);

            } else {
                str = str.substring(2, 2 + sLength);
            }


            return "" + h + o + s + str;
        }


    }

    public String divide(String operand1, String operand2, int length) {         //原码除法
        String operand3 = oneAdder(negation(operand2)).substring(1);          //operand2相反数
        char[] result = new char[2 * length];
        for (int i = 0; i < result.length; i++)
            result[i] = '0';
        for (int i = 0; i < length; i++)
            result[i] = operand1.charAt(i);

        for (int i = 0; i < length; i++) {


        }


        return null;
    }

    /**
     * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
     * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
     *
     * @param operand1 二进制表示的被除数
     * @param operand2 二进制表示的除数
     * @param eLength  指数的长度，取值大于等于 4
     * @param sLength  尾数的长度，取值大于等于 4
     * @return 长度为2+eLength+sLength的字符串表示的相除结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
     */
    public String floatDivision(String operand1, String operand2, int eLength, int sLength) {
        // TODO YOUR CODE HERE.
        char x = operand1.charAt(0);                 //符号位
        char y = operand2.charAt(0);
        int o = 0;
        if (x != y)
            o = 1;                                  //符号位确定

        int p = 0;                                  //判断指数是否上溢出
        String s1 = operand1.substring(1, eLength + 1);      //指数
        String s2 = operand2.substring(1, eLength + 1);

        String m1 = operand1.substring(eLength + 1);          //尾数
        String m2 = operand2.substring(eLength + 1);

        int m = Integer.parseInt(s1, 2) - (int) Math.pow(2, eLength - 1) + 1 - Integer.parseInt(s2, 2) + (int) Math.pow(2, eLength - 1) - 1;  //指数相减

        if (m >= ((int) Math.pow(2, eLength) - 1))
            p = 1;

        String s = Integer.toBinaryString(m);
        int l = s.length();
        for (int i = 0; i < eLength - l; i++)
            s = "0" + s;                                //相减之后的指数

        String str = divide("1" + m1, "1" + m2, m1.length() + 1);


        return null;
    }
}
