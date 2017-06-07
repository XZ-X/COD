import java.util.ArrayList;
import java.util.HashSet;

/**
 * ģ��ALU���������͸���������������
 *
 * @author ��161250168_��ǿ��
 */

public class qALU {

    /**
     * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
     * ����integerRepresentation("9", 8)
     *
     * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
     * @param length �����Ʋ����ʾ�ĳ���
     * @return number�Ķ����Ʋ����ʾ������Ϊlength
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
     * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
     * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
     * �������Ϊ��0���롣<br/>
     * ����floatRepresentation("11.375", 8, 11)
     *
     * @param number  ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
     * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
     */
    public String floatRepresentation (String number, int eLength, int sLength) {
        // TODO YOUR CODE HERE.

        if(number.equals("+Inf")){    //������
            String m="0";
            for(int i=0;i<eLength;i++)
                m=m+"1";
            for(int i=0;i<sLength;i++)
                m=m+"0";
            return m;
        }
        else{
            if(number.equals("-Inf")){   //������
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
                    if(Math.abs(num)>Math.pow(2, -Math.pow(2, eLength-1)+2)) {            //�����
                        int i=0;      //ָ��
                        String s="0";    //���صĽ��
                        if(num<0){
                            num=-num;
                            s="1";
                        }
                        int x=(int)num;
                        double y=num-x;
                        String str=Integer.toBinaryString(x);//��������

                        String result="";                    //С������

                        String yy=String.valueOf(y);
                        int[] y1=new int[yy.length()-1];
                        y1[0]=0;
                        for(int k=1;k<y1.length;k++){
                            y1[k]=Integer.parseInt(Character.toString(yy.charAt(k+1)));
                        }

                        HashSet<Integer> y2=new HashSet<Integer>();
                        for(int k:y1)
                            y2.add(k);
                        boolean xx=((y2.contains(0))&&(y2.size()==1))||(result.length()>sLength+100);
                        while(!xx){
                            for(int k=1;k<y1.length;k++)
                                y1[k]*=2;
                            for(int k=y1.length-1;k>=1;k--){
                                if(y1[k]>=10){
                                    y1[k]-=10;
                                    y1[k-1]+=1;
                                }

                            }
                            if(y1[0]==1){
                                y1[0]=0;
                                result+="1";

                            }
                            else{
                                result+="0";
                            }

                            y2=new HashSet<Integer>();
                            for(int k:y1)
                                y2.add(k);

                            xx=((y2.contains(0))&&(y2.size()==1))||(result.length()>sLength+100);
                        }

                        if(x==0){
                            for(int j=0;j<result.length();j++){
                                if(result.charAt(j)=='1'){
                                    i=-(j+1)+(int)(Math.pow(2,eLength-1)-1);
                                    result=result.substring(j+1);
                                    break;
                                }
                            }
                        }
                        else{
                            i=(int)(Math.pow( 2,eLength-1)-1)+str.length()-1;
                            result=str.substring(1)+result;
                        }
                        String m=Integer.toBinaryString(i);   //ָ������

                        int bb=m.length();
                        for(int j=0;j<eLength-bb;j++)
                            m="0"+m;

                        int a=result.length();
                        if(a>sLength)          //β��
                            result=result.substring(0,sLength);
                        else{
                            for(int j=0;j<sLength-a;j++)
                                result=result+"0";
                        }
                        s=s+m+result;
                        return s ;
                    }
                    else{                            //�ǹ����
                        return null;
                    }
                }
            }
        }


    }





    /**
     * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
     * ����ieee754("11.375", 32)
     *
     * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
     * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
     * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
     */
    public String ieee754(String number, int length) {
        // TODO YOUR CODE HERE.
        if (length == 32)
            return floatRepresentation(number, 8, 23);
        else
            return floatRepresentation(number, 11, 52);
    }

    /**
     * ��������Ʋ����ʾ����������ֵ��<br/>
     * ����integerTrueValue("00001001")
     *
     * @param operand �����Ʋ����ʾ�Ĳ�����
     * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
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
     * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
     * ����floatTrueValue("01000001001101100000", 8, 11)
     *
     * @param operand �����Ʊ�ʾ�Ĳ�����
     * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
     */
    public String floatTrueValue(String operand, int eLength, int sLength) {
        String result = "";                     //���ؽ��

        if (operand.charAt(0) == '1')            //����λ
            result = "-";

        String s = operand.substring(1, 1 + eLength);           //ָ��
        String str = operand.substring(1 + eLength);            //ָ��

        HashSet<Character> x = new HashSet<Character>();
        for (int i = 0; i < s.length(); i++) {
            x.add(s.charAt(i));
        }

        if (x.contains('0') && x.size() == 1) {
            str = "0" + str;
            int m = (int) Math.pow(2, eLength - 1) - 2;    //����λ��
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
     * ��λȡ��������<br/>
     * ����negation("00001001")
     *
     * @param operand �����Ʊ�ʾ�Ĳ�����
     * @return operand��λȡ���Ľ��
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
     * ���Ʋ�����<br/>
     * ����leftShift("00001001", 2)
     *
     * @param operand �����Ʊ�ʾ�Ĳ�����
     * @param n       ���Ƶ�λ��
     * @return operand����nλ�Ľ��
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
     * �߼����Ʋ�����<br/>
     * ����logRightShift("11110110", 2)
     *
     * @param operand �����Ʊ�ʾ�Ĳ�����
     * @param n       ���Ƶ�λ��
     * @return operand�߼�����nλ�Ľ��
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
     * �������Ʋ�����<br/>
     * ����logRightShift("11110110", 2)
     *
     * @param operand �����Ʊ�ʾ�Ĳ�����
     * @param n       ���Ƶ�λ��
     * @return operand��������nλ�Ľ��
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
     * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
     * ����fullAdder('1', '1', '0')
     *
     * @param x ��������ĳһλ��ȡ0��1
     * @param y ������ĳһλ��ȡ0��1
     * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
     * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
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
     * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
     * ����claAdder("1001", "0001", '1')
     *
     * @param operand1 4λ�����Ʊ�ʾ�ı�����
     * @param operand2 4λ�����Ʊ�ʾ�ļ���
     * @param c        ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
     * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ����
     */
    public String claAdder(String operand1, String operand2, char c) {
        // TODO YOUR CODE HERE.
        char[] op1 = operand1.toCharArray();                                    //��stringת��Ϊint����
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
     * ��һ����ʵ�ֲ�������1�����㡣
     * ��Ҫ�������š����š�����ŵ�ģ�⣬
     * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
     * {@link #claAdder(String, String, char) claAdder}��
     * {@link #adder(String, String, char, int) adder}��
     * {@link #integerAddition(String, String, int) integerAddition}������<br/>
     * ����oneAdder("00001001")
     *
     * @param operand �����Ʋ����ʾ�Ĳ�����
     * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
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
     * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
     * ����adder("0100", "0011", ��0��, 8)
     *
     * @param operand1 �����Ʋ����ʾ�ı�����
     * @param operand2 �����Ʋ����ʾ�ļ���
     * @param c        ���λ��λ
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
     * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
     */
    public String adder(String operand1, String operand2, char c, int length) {
        // TODO YOUR CODE HERE.
        if (length > 4) {
            ArrayList<String> data = new ArrayList<String>();
            int x = operand1.length();
            int y = operand2.length();
            if (operand1.charAt(0) == '0') {                               //������չ
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
            char[] op1 = operand1.toCharArray();                                    //��stringת��Ϊint����
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
            char[] op1 = operand1.toCharArray();                                    //��stringת��Ϊint����
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
     * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
     * ����integerAddition("0100", "0011", 8)
     *
     * @param operand1 �����Ʋ����ʾ�ı�����
     * @param operand2 �����Ʋ����ʾ�ļ���
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
     * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
     */
    public String integerAddition(String operand1, String operand2, int length) {
        // TODO YOUR CODE HERE.
        String result = adder(operand1, operand2, '0', length);
        return result;
    }

    /**
     * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
     * ����integerSubtraction("0100", "0011", 8)
     *
     * @param operand1 �����Ʋ����ʾ�ı�����
     * @param operand2 �����Ʋ����ʾ�ļ���
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
     * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
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
     * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
     * ����integerMultiplication("0100", "0011", 8)
     *
     * @param operand1 �����Ʋ����ʾ�ı�����
     * @param operand2 �����Ʋ����ʾ�ĳ���
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
     * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
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
     * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
     * ����integerDivision("0100", "0011", 8)
     *
     * @param operand1 �����Ʋ����ʾ�ı�����
     * @param operand2 �����Ʋ����ʾ�ĳ���
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
     * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
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
     * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
     * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
     * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
     * ����signedAddition("1100", "1011", 8)
     *
     * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
     * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
     * @param length   ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
     * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
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

    public String add(String operand1, String operand2, int length) {     //ԭ��ӷ�
        char x = operand1.charAt(0);
        char y = operand2.charAt(0);
        String s1 = operand1.substring(1);
        String s2 = operand2.substring(1);

        if (x == y) {                                        //ͬ�����
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
            //������
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
     * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
     * ����floatAddition("00111111010100000", "0011111100000", 8, 8, 8)
     *
     * @param operand1 �����Ʊ�ʾ�ı�����
     * @param operand2 �����Ʊ�ʾ�ļ���
     * @param eLength  ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength  β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param gLength  ����λ�ĳ���
     * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
     */
    public String floatAddition(String operand1, String operand2, int eLength, int sLength, int gLength) {
        // TODO YOUR CODE HERE.
        String s1 = operand1.substring(1, eLength + 1);
        String s2 = operand2.substring(1, eLength + 1);
        int a = Integer.parseInt(s1, 2); //operand1 ָ��
        int b = Integer.parseInt(s2, 2); //operand2ָ��

        if (b > a)
            s1 = s2;                 //s1Ϊ�����ָ��

        String m1 = operand1.substring(eLength + 1);
        String m2 = operand2.substring(eLength + 1);   //β��
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

        String result = add(m1, m2, m1.length());   //β������
        if (m1.charAt(0) == m2.charAt(0)) {
            int o = 0;
            if (result.charAt(1) == '1') {  //�ҹ�

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
            String k = result.substring(1);                               //���
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

    public String Add(String operand1, String operand2, int length) {             //ָ���ӷ�
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

    public String mult(String operand1, String operand2, int length) {           //ԭ��˷�
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
     * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
     * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
     *
     * @param operand1 �����Ʊ�ʾ�ı�����
     * @param operand2 �����Ʊ�ʾ�ļ���
     * @param eLength  ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength  β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param gLength  ����λ�ĳ���
     * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
     * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
     * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
     *
     * @param operand1 �����Ʊ�ʾ�ı�����
     * @param operand2 �����Ʊ�ʾ�ĳ���
     * @param eLength  ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength  β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
            for (int i = 0; i < 2 + eLength + sLength; i++) {                              //�ж��Ƿ��в�����Ϊ0
                result += "0";
            }
            return result;
        } else {
            char h = '0';
            String s1 = operand1.substring(1, eLength + 1);                     //ָ��
            String s2 = operand2.substring(1, eLength + 1);
            int x = Integer.parseInt(s1, 2) - (int) Math.pow(2, eLength - 1) + 1 + Integer.parseInt(s2, 2) - (int) Math.pow(2, eLength - 1) + 1;            //ָ����ӵĽ��
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
            String m2 = operand2.substring(eLength + 1);                       //β��

            boolean n1 = true, n2 = true;                                                             //�ж��Ƿ�Ϊ�����
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
                m1 = "0" + m1;                                                                    //��������λ
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

    public String divide(String operand1, String operand2, int length) {         //ԭ�����
        String operand3 = oneAdder(negation(operand2)).substring(1);          //operand2�෴��
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
     * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
     * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
     *
     * @param operand1 �����Ʊ�ʾ�ı�����
     * @param operand2 �����Ʊ�ʾ�ĳ���
     * @param eLength  ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @param sLength  β���ĳ��ȣ�ȡֵ���ڵ��� 4
     * @return ����Ϊ2+eLength+sLength���ַ�����ʾ��������,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
     */
    public String floatDivision(String operand1, String operand2, int eLength, int sLength) {
        // TODO YOUR CODE HERE.
        char x = operand1.charAt(0);                 //����λ
        char y = operand2.charAt(0);
        int o = 0;
        if (x != y)
            o = 1;                                  //����λȷ��

        int p = 0;                                  //�ж�ָ���Ƿ������
        String s1 = operand1.substring(1, eLength + 1);      //ָ��
        String s2 = operand2.substring(1, eLength + 1);

        String m1 = operand1.substring(eLength + 1);          //β��
        String m2 = operand2.substring(eLength + 1);

        int m = Integer.parseInt(s1, 2) - (int) Math.pow(2, eLength - 1) + 1 - Integer.parseInt(s2, 2) + (int) Math.pow(2, eLength - 1) - 1;  //ָ�����

        if (m >= ((int) Math.pow(2, eLength) - 1))
            p = 1;

        String s = Integer.toBinaryString(m);
        int l = s.length();
        for (int i = 0; i < eLength - l; i++)
            s = "0" + s;                                //���֮���ָ��

        String str = divide("1" + m1, "1" + m2, m1.length() + 1);


        return null;
    }
}
