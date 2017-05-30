import java.util.ArrayList;

/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author 161250151_温曜铭
 *
 */

public class wALU {

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation (String number, int length) {
		//用sb1存32位的二进制数，用sb2存length位的二进制数
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		int lengthOfInteger = 32;
		long mod = (long)Math.pow(2.0, lengthOfInteger);

		long value = Long.parseLong(number);
		//获取32位的二进制数
		if (value < 0) {
			//值小于0,在一个模系统中负数的表示和它加上模的值的表示一样
			value = value + mod;
		}
		for(int i = lengthOfInteger - 1;i >= 0;i--){
			//先统一转换到32位再根据length来决定最后的位数
			if (value >= Math.pow(2.0, i)) {
				sb1.append(1);
				value = value - (long)Math.pow(2.0, i);
			}else{
				sb1.append(0);
			}
		}
		//截取或者扩展
		if (length >= lengthOfInteger) {
			//获取符号位并扩展
			char SF = sb1.toString().charAt(0);
			if (SF == '0') {
				for(int i = 0;i<length - lengthOfInteger;i++){
					sb2.append(0);
				}
				sb2.append(sb1.toString());
			}else{
				for(int i = 0;i<length - lengthOfInteger;i++){
					sb2.append(1);
				}
				sb2.append(sb1.toString());
			}
		}else{
			//截断
			for(int j = lengthOfInteger - length;j < lengthOfInteger;j++){
				sb2.append(sb1.toString().charAt(j));
			}
		}
		String ret = sb2.toString();
		return ret;
	}

	/**
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		/* 先取得符号位
		 * 小数点前的默认为绝对值
		 * 整数部分用二进制补码来实现，从第一个不为0的地方开始append
		 * */
		StringBuffer sb1 = new StringBuffer();
		double value = Double.parseDouble(number);
		//计算最大值
		double pianyi = Math.pow(2.0, eLength-1)-1;
		double eMax = Math.pow(2.0, eLength)-1;
		double trueValueOfMaxE = eMax - pianyi;
		double max = Math.pow(2.0, trueValueOfMaxE) * (1 - Math.pow(2.0, -sLength-1));
		//计算最小值
		double min = Math.pow(2.0, 1-pianyi) * Math.pow(2.0, -sLength);
		//一些特殊情况
		if(value == 0){
			//等于0时
			for(int i = 0;i < eLength + sLength + 1;i++){
				sb1.append(0);
			}
		}else if(value > max){
			//正无穷，符号0，指数全1,尾数全0
			sb1.append(0);
			for(int i = 0;i < eLength;i++){
				sb1.append(1);
			}
			for(int i = 0;i < sLength;i++){
				sb1.append(0);
			}
		}else if(value < -max){
			//负无穷，符号1，指数全1,尾数全0
			sb1.append(1);
			for(int i = 0;i < eLength;i++){
				sb1.append(1);
			}
			for(int i = 0;i < sLength;i++){
				sb1.append(0);
			}
		}else if(value < min && value > 0){
			//非规格化正数
			sb1.append(0);
			for(int i = 0;i < eLength;i++){
				sb1.append(0);
			}
			for(int i = 0;i < sLength;i++){
				if (value >= Math.pow(2.0, -1-i)) {
					sb1.append(1);
					value = value - Math.pow(2.0, -1-i);
				}else{
					sb1.append(0);
				}
			}
		}else if(value < 0 && value > -min){
			//非规格化负数
			sb1.append(1);
			for(int i = 0;i < eLength;i++){
				sb1.append(0);
			}
			for(int i = 0;i < sLength;i++){
				if (value >= Math.pow(2.0, -1-i)) {
					sb1.append(1);
					value = value - Math.pow(2.0, -1-i);
				}else{
					sb1.append(0);
				}
			}
		}
		else {
			//规格化数
			if (value < 0) {
				//负数符号位置1
				sb1.append(1);
				value = Math.abs(value);
			}else{
				//正数符号位置0
				sb1.append(0);
			}
			long integerPart = (long)value;
			double decimalsPart = value - integerPart;
			//获取整数部分的二进制
			String integerPartBase2 = Long.toBinaryString(integerPart);
			if (value >= 1) {
				//大于等于1的时候左移
				int lengthOfIntegerPart = integerPartBase2.length();//首位必定为1，左移位数为整数二进制表示的长度-1
				int currentE = lengthOfIntegerPart - 1 + (int)pianyi;
				sb1.append(integerRepresentation(Integer.toString(currentE), eLength));//添加阶码
				sb1.append(integerPartBase2.substring(1));//把首位隐藏，已经添加了length-1位，小数表示的要减去这么多位
				for (int i = 0; i < sLength - lengthOfIntegerPart + 1; i++) {
					if (decimalsPart >= Math.pow(2.0, -1-i)) {
						sb1.append(1);
						decimalsPart = decimalsPart - Math.pow(2.0, -1-i);
					}else{
						sb1.append(0);
					}
				}
			}else{
				//小于1大于0的时候右移
				int count = 0;//count记录需要右移的位数
				loop:			for(int i = 0;i < sLength;i++){
					while(decimalsPart >= Math.pow(2.0, -1-i)){
						count = i + 1;
						break loop;
					}
				}
				StringBuffer decimalCollect = new StringBuffer();
				for(int i= 0;i < sLength + count + 1;i++){
					if (decimalsPart >= Math.pow(2.0, -1-i)) {
						decimalCollect.append(1);
						decimalsPart = decimalsPart - Math.pow(2.0, -1-i);
					}else{
						decimalCollect.append(0);
					}
				}
				int currentE = (int)pianyi - count;
				sb1.append(integerRepresentation(Integer.toString((int)currentE), eLength));//添加阶码,并且扩展成阶码长度位
				sb1.append(decimalCollect.toString().substring(count,sLength + count));//最后要补全尾数长度

			}

		}
		return sb1.toString();
	}

	/**
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		String output = null;
		if(length == 32){
			output = floatRepresentation(number, 8, 23);
		}else if(length == 64){
			output = floatRepresentation(number, 11, 52);
		}
		return output;
	}

	/**
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue (String operand) {
		//判断正负
		char SF = operand.charAt(0);
		int lengthOfOperand = operand.length();
		int sum = 0;
		if (SF == '0') {
			//正数直接计算
			for(int i = 0;i < lengthOfOperand;i++){
				if (operand.charAt(i) == '1') {
					sum = sum + (int)Math.pow(2.0, lengthOfOperand - 1 - i);
				}
			}
		}else{
			//负数先取反
			StringBuffer sb1 = new StringBuffer();
			for(int i = 0;i < lengthOfOperand;i++){
				if (operand.charAt(i) == '0') {
					sb1.append(1);
				}else{
					sb1.append(0);
				}
			}
			String afterChange = sb1.toString();
			for(int i = 0;i < afterChange.length();i++){
				if (afterChange.charAt(i) == '1') {
					sum = sum + (int)Math.pow(2.0, afterChange.length() - 1 - i);
				}
			}
			//取反之后再加上1
			sum = sum +1;
			sum = -sum;
		}
		return Integer.toString(sum);
	}

	/**
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		char SF = operand.charAt(0);//获取符号位
		String e = operand.substring(1, eLength + 1);//获取阶码
		String s = operand.substring(eLength + 1, operand.length());//获取尾数

		//先通过阶码的值判断是否是特殊情况
		int valueOfE = 0;//阶码的真值
		int pianyi = (int)Math.pow(2.0, eLength-1) - 1;//偏置常数为2的最高位次方减一
		for(int i = 0;i < eLength;i++){
			valueOfE = valueOfE + Integer.parseInt(String.valueOf(e.charAt(i))) * (int)Math.pow(2.0, eLength - 1 - i );
		}
		StringBuffer sb1 = new StringBuffer();
		for(int i = 0;i < sLength;i++){
			sb1.append(0);
		}
		String allzero = sb1.toString();
		if (valueOfE == 0) {
			//0或非规格化数
			if (s.equals(allzero)) {
				return "0";
			}else{
				double sum = 0.0;
				for(int i = 0;i < sLength;i++){
					sum = sum +  Integer.parseInt(String.valueOf(s.charAt(i))) * Math.pow(2.0, -1-i);
				}
				sum = sum * Math.pow(2.0, 1-pianyi);
				if (SF == '1') {
					sum = -sum;
				}
				return Double.toString(sum);
			}
		}else if(valueOfE-pianyi == (int)Math.pow(2.0, eLength)-1){
			//正负无穷或NaN
			if (!s.equals(allzero)) {
				//NaN阶码全1，尾数非0
				return "NaN";
			}else{
				//无穷阶码全1，尾数为0，正负看符号位
				if (SF == '0') {
					return "+Inf";
				}else{
					return "-Inf";
				}
			}
		}else {
			//规格化数
			int trueValueOfE = valueOfE - pianyi;
			double sum = 0.0;
			for(int i = 0;i < sLength;i++){
				sum = sum +  Integer.parseInt(String.valueOf(s.charAt(i))) * Math.pow(2.0, -1-i);
			}
			sum = sum + 1;//前面被隐藏的1要加上去
			sum = sum * Math.pow(2.0, trueValueOfE);
			if (SF == '1') {
				sum = -sum;
			}
			return Double.toString(sum);
		}
	}

	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation (String operand) {
		int lengthOfOperand = operand.length();
		StringBuffer sb1 = new StringBuffer();
		for(int i = 0;i<lengthOfOperand;i++){
			if (operand.charAt(i) == '0') {
				sb1.append(1);
			}else{
				sb1.append(0);
			}
		}
		String output = sb1.toString();
		return output;
	}

	/**
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		//左移n位相当于把左边n位的值都丢掉，后面补0
		int lengthOfOperand = operand.length();
		StringBuffer sb1 = new StringBuffer();
		if (n >= lengthOfOperand) {
			//全移没了
			for(int i = 0;i < lengthOfOperand;i++){
				sb1.append(0);
			}
		}else{
			for(int i = n;i < lengthOfOperand;i++){
				sb1.append(operand.charAt(i));
			}
			for(int i = 0;i < n;i++){
				sb1.append(0);
			}
		}

		String output = sb1.toString();
		return output;
	}

	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		//右移n位相当于把右边n位丢掉
		int lengthOfOperand = operand.length();
		StringBuffer sb1 = new StringBuffer();
		if (n >= lengthOfOperand) {
			//全移没了
			for(int i = 0;i < lengthOfOperand;i++){
				sb1.append(0);
			}
		}else{
			for(int i = 0;i < n;i++){
				sb1.append(0);
			}
			for(int i = 0;i < lengthOfOperand - n;i++){
				sb1.append(operand.charAt(i));
			}
		}
		String output = sb1.toString();
		return output;
	}

	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		//算数右移n位，丢弃低位的n位，左边添加符号位
		char SF = operand.charAt(0);
		int lengthOfOperand = operand.length();
		StringBuffer sb1 = new StringBuffer();
		if (n >= lengthOfOperand) {
			//全移没了
			for(int i = 0;i < lengthOfOperand;i++){
				sb1.append(SF);
			}
		}else{
			for(int i = 0;i < n;i++){
				sb1.append(SF);
			}
			for(int i = 0;i < lengthOfOperand - n;i++){
				sb1.append(operand.charAt(i));
			}
		}
		String output = sb1.toString();
		return output;
	}

	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder (char x, char y, char c) {
		StringBuffer sb1 = new StringBuffer();
		//先把三个字符转换成数字
		int valueOfX = Integer.parseInt(String.valueOf(x));
		int valueOfY = Integer.parseInt(String.valueOf(y));
		int valueOfC = Integer.parseInt(String.valueOf(c));
		int current = valueOfX ^ valueOfY ^ valueOfC;
		int ahead = (valueOfX&valueOfC) | (valueOfY&valueOfC) | (valueOfX&valueOfY);
		sb1.append(Integer.toString(ahead));
		sb1.append(Integer.toString(current));
		String result = sb1.toString();
		return result;
	}

	/**
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		//先取得两个操作数各位上的值，由低到高分别为1234
		int x1 = Integer.parseInt(String.valueOf(operand1.charAt(3)));
		int x2 = Integer.parseInt(String.valueOf(operand1.charAt(2)));
		int x3 = Integer.parseInt(String.valueOf(operand1.charAt(1)));
		int x4 = Integer.parseInt(String.valueOf(operand1.charAt(0)));
		int y1 = Integer.parseInt(String.valueOf(operand2.charAt(3)));
		int y2 = Integer.parseInt(String.valueOf(operand2.charAt(2)));
		int y3 = Integer.parseInt(String.valueOf(operand2.charAt(1)));
		int y4 = Integer.parseInt(String.valueOf(operand2.charAt(0)));

		//P的公式：P = X + Y，加法由或操作实现
		int p1 = x1|y1;
		int p2 = x2|y2;
		int p3 = x3|y3;
		int p4 = x4|y4;

		//G的公式：G = XY，乘法由与操作实现
		int g1 = x1&y1;
		int g2 = x2&y2;
		int g3 = x3&y3;
		int g4 = x4&y4;

		//根据公式得到c1 c2 c3 c4
		int c0 = Integer.parseInt(String.valueOf(c));
		//C1 = G1 + P1*C0
		int c1 = g1|(p1&c0);
		//C2 = G2 + P2*G1 + P2*P1*C0
		int c2 = g2|(p2&g1)|(p2&p1&c0);
		//C3 = G3 + P3*G2 + P3*P2*G1 + P3*P2*P1*C0
		int c3 = g3|(p3&g2)|(p3&p2&g1)|(p3&p2&p1&c0);
		//C4 = G4 + P4*G3 + P4*P3*G2 + P4*P3*P2*G1 + P4*P3*P2*P1*C0
		int c4 = g4|(p4&g3)|(p4&p3&g2)|(p4&p3&p2&g1)|(p4&p3&p2&p1&c0);

		//计算各位数的结果,result1234表示
		char r1 = fullAdder(operand1.charAt(3), operand2.charAt(3), c).charAt(1);
		char r2 = fullAdder(operand1.charAt(2), operand2.charAt(2), Integer.toString(c1).charAt(0)).charAt(1);
		char r3 = fullAdder(operand1.charAt(1), operand2.charAt(1), Integer.toString(c2).charAt(0)).charAt(1);
		char r4 = fullAdder(operand1.charAt(0), operand2.charAt(0), Integer.toString(c3).charAt(0)).charAt(1);
		StringBuffer sb1 = new StringBuffer();
		sb1.append(Integer.toString(c4).charAt(0));
		sb1.append(r4);
		sb1.append(r3);
		sb1.append(r2);
		sb1.append(r1);
		String output = sb1.toString();
		return output;
	}

	/**
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder (String operand) {
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		ArrayList<Integer> storec = new ArrayList<>();
		int value = 0;
		int c = 1;//c表示进位,c0即1的值
		storec.add(c);
		int f = 0;//f表示这一位加法运算的结果
		for(int i = operand.length()-1;i >= 0;i--){
			value = Integer.parseInt(String.valueOf(operand.charAt(i)));
			f = c^value;
			c = c&value;
			storec.add(c);
			sb1.append(f);
		}
		int overfolw = storec.get(storec.size()-1)^storec.get(storec.size()-2);
		sb2.append(overfolw);
		//现在结果是倒过来的，还需要将sb1的内容全部倒置
		String result = sb1.toString();
		for(int i = result.length() - 1;i >= 0;i--){
			sb2.append(result.charAt(i));
		}
		String output = sb2.toString();
		return output;
	}

	/**
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		//先计算四位加法计算需要的次数，将操作数符号扩展，再循环相加
		int times = length / 4;
		String add1 = null;
		String add2 = null;
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		//将被加数扩展
		for(int i = 0;i < length - operand1.length();i++){
			sb1.append(operand1.charAt(0));
		}
		sb1.append(operand1);
		add1 = sb1.toString();
		//将加数扩展
		for(int i = 0;i < length - operand2.length();i++){
			sb2.append(operand2.charAt(0));
		}
		sb2.append(operand2);
		add2 = sb2.toString();
		//调用四位加法，循环相加，求得的结果4位4位倒过来输出
		StringBuffer sb3 = new StringBuffer();//从左到右储存从低到高的运算结果
		StringBuffer sb4 = new StringBuffer();
		ArrayList<Character> storec = new ArrayList<>();//保存每一次四位加法每次的进位信息，用于判断溢出
		for(int i = 0;i < times ;i++){
			String partOfAdd1 = add1.substring(add1.length() - 4 - 4 * i,add1.length() - 4 * i);
			String partOfAdd2 = add2.substring(add2.length() - 4 - 4 * i,add2.length() - 4 * i);
			String sum = claAdder(partOfAdd1, partOfAdd2, c);
			sb3.append(sum.substring(1, 5));
			c = sum.charAt(0);
			storec.add(c);
		}
		ArrayList<Integer> storeCfinal = new ArrayList<>();
		int Cfinal = Integer.parseInt(String.valueOf(storec.get(storec.size()-1)));
		for(int i = 0;i < add1.substring(0,4).length();i++){
			Cfinal = Cfinal&Integer.parseInt(String.valueOf(add1.substring(0, 4).charAt(3-i))) |
					Cfinal&Integer.parseInt(String.valueOf(add2.substring(0, 4).charAt(3-i))) |
					Integer.parseInt(String.valueOf(add1.substring(0, 4).charAt(3-i)))&Integer.parseInt(String.valueOf(add2.substring(0, 4).charAt(3-i)));
			storeCfinal.add(Cfinal);
		}
		int overfolw = storeCfinal.get(storeCfinal.size()-1)^storeCfinal.get(storeCfinal.size()-2);
		sb4.append(overfolw);
		for(int i = 0;i < times ;i++){
			sb4.append(sb3.toString().substring(sb3.toString().length()- 4 - 4 * i, sb3.toString().length() - 4 * i));
		}
		return sb4.toString();
	}

	/**
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		return adder(operand1, operand2, '0', length);
	}

	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		//减去一个数等于加上这个数的补码，先取得减数的真值，再取负，再取得二进制数，再调用加法
		int trueValueOfOP2 = Integer.parseInt(integerTrueValue(operand2));
		String afterChange = integerRepresentation(Integer.toString(-trueValueOfOP2), length);
		String output = integerAddition(operand1, afterChange, length);
		return output;
	}

	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		String x = operand1;//x是被乘数
		String y = operand2;//y是乘数
		char helper = '0';//helper代表了辅助位
		String p = "";//p表示部分积
		for(int i = 0;i < length;i++){
			p = p + "0";
		}
		//扩展x与y
		int xToAdd = length - x.length();
		if (x.length() < length) {
			char SF = x.charAt(0);
			for(int i = 0; i < xToAdd; i++){
				x = SF + x;
			}
		}
		int yToAdd = length - y.length();
		if (y.length() < length) {
			char SF = y.charAt(0);
			for(int i = 0; i < yToAdd; i++){
				y = SF + y;
			}
		}
		//一共要进行length次的算数右移
		char overflow = '0';
		for(int i = 0;i < length;i++){
			//根据00 01 10 11四个不同情况判断要进行的操作
			if (y.charAt(y.length()-1) == '0') {
				if (helper == '1') {
					//01做加法
					p = integerAddition(p, x, length).substring(1);
					overflow = integerAddition(p, x, length).charAt(0);

				}
			}else{
				if (helper == '0') {
					//10做减法
					p = integerSubtraction(p, x, length).substring(1);
					overflow = integerAddition(p, x, length).charAt(0);
				}
			}

			//算数右移
			helper = y.charAt(length - 1);
			y = p.charAt(p.length()-1) + y.substring(0,y.length()-1);
			p = ariRightShift(p, 1);
		}
		String output = overflow + y;
		return output;
	}

	/**
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		char SFofX = operand1.charAt(0);//x表示被除数
		char SFofY = operand2.charAt(0);//y表示除数
		String x = operand1;
		String y = operand2;
		//长度扩展至length位
		if (operand1.length() < length) {
			for(int i = 0;i < length - operand1.length();i++){
				x = SFofX + x;
			}
		}
		if (operand2.length() < length) {
			for(int i = 0;i < length - operand2.length();i++){
				y = SFofY + y;
			}
		}
		//设置余数寄存器R
		String r = "";
		String q = x;
		char qn = '0';
		for(int i = 0;i < length;i++){
			r = SFofX + r;
		}
		//求第一位商，若XY同号R1 = X - Y，否则做加法
		if (SFofX != SFofY) {
			r = integerAddition(x, y, length).substring(1);
		}else{
			r = integerSubtraction(x, y, length).substring(1);
		}
		//若R1与Y同号，Q置1,否则置0
		if (r.charAt(0) == SFofY) {
			qn = '1';
		}else{
			qn = '0';
		}
		char overflow = '0';
		if ((SFofX==SFofY && qn=='1') || (SFofX!=SFofY && qn == '0')) {
			overflow = '1';
		}
		//左移
		r = r.substring(1) + q.substring(0, 1);
		q = q.substring(1) + qn;
		//进行length 次循环
		//R与Y同号，Q（n-1）置1，R（n+1）=2Rn-Y
		//R与Y异号，Q（n-1）置0，R（n+1）=2Rn+Y
		for(int i = 0;i < length ;i++){
			if (qn == '1') {
				r = integerSubtraction(r, y, length).substring(1);
			}else{
				r = integerAddition(r, y, length).substring(1);
			}
			if (r.charAt(0) == SFofY) {
				qn = '1';
			}else{
				qn = '0';
			}
			r = r.substring(1) + q.substring(0, 1);
			q = q.substring(1) + qn;
		}

		//商的修正
		if (SFofX == SFofY) {
			//符号相同，此时的商是真正的商
		}else{
			//符号不同，商加上1
			q = oneAdder(q).substring(1);
		}
		//余数修正
		if(r.charAt(0) == SFofX){
			//不需要修正
		}else{
			if (SFofX == SFofY) {
				r = integerAddition(r, y, length).substring(1);
			}else{
				r = integerSubtraction(r, y, length).substring(1);
			}
		}
		String output = overflow + q + r;
		return output;
	}

	/**
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		//取得符号
		char SFofX = operand1.charAt(0);
		char SFofY = operand2.charAt(0);
		//取得真值
		String x = operand1.substring(1);
		String y = operand2.substring(1);
		//扩展
		if (x.length() < length) {
			int xToAdd = length - x.length();
			for(int i = 0;i < xToAdd;i++){
				x = "0" + x;
			}
		}
		if (y.length() < length) {
			int yToAdd = length - y.length();
			for(int i = 0;i < yToAdd;i++){
				y = "0" + y;
			}
		}
		//通过异或判断是否为同号，同号求和，异号求差
		String output = null;
		if ((SFofX == '0' && SFofY == '0') || (SFofX == '1' && SFofY == '1')) {
			String sum = adder(x, y, '0', length);
			output = sum.substring(0, 1)+ SFofX + sum.substring(1);
		}else{
			String sum = adder(x, y, '0', length);
			if (sum.charAt(0) == '1') {
				String negation = negation(sum.substring(1));
				String adddone = oneAdder(negation);
				output = negation(String.valueOf(SFofX)) + adddone;
			}else{
				output = sum.substring(0, 1)+ SFofX + sum.substring(1);
			}
		}
		return output;
	}

	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		//在两个加数后面加0先扩展
		String output = null;
		String x = operand1.substring(1+eLength, 1+eLength+sLength);
		String y = operand2.substring(1+eLength, 1+eLength+sLength);
		for(int i = 0;i < gLength;i++){
			x = x + "0";
			y = y + "0";
		}
		//对阶
		String eOfX = integerTrueValue(operand1.substring(1, 1+eLength));
		String eOfY = integerTrueValue(operand2.substring(1, 1+eLength));
		String eOfBoth = null;
		int delta = Integer.parseInt(eOfX) - Integer.parseInt(eOfY);
		if (delta >= 0) {
			//x的阶码比y的阶码高,y逻辑右移
			y = logRightShift(y,delta);
			eOfBoth = eOfX;
		}else{
			//y的阶码比x的高
			x = logRightShift(x, Math.abs(delta));
			eOfBoth = eOfY;
		}
		String sum = signedAddition(operand1.charAt(0)+x, operand2.charAt(0)+y, sLength+gLength);
		//判断sum是否为0
		String allzero = null;
		for(int i = 0 ;i < sLength + gLength - 2;i++){
			allzero = "0" + allzero;
		}
		if (sum.substring(2).equals(allzero)) {
			//sum为0
			for (int i = 0; i < 2 + sLength + eLength; i++) {
				output = "0" + output;
			}
			return  output;
		}else{
			//sum不为0
			//判断是否溢出
			if (sum.charAt(1) == '1') {
				//溢出，右归
				eOfBoth = integerRepresentation(Integer.toString(Integer.parseInt(integerTrueValue(eOfBoth)) + 1), eLength);
				sum = logRightShift(sum, 1);
			}else{
				//不溢出，左归
				int count = 0;
				countloop:			for(int i = 2;i < 2+sLength+gLength;i++){
					if (sum.charAt(i) == '0') {
						count++;
					}else{
						break countloop;
					}
				}
				eOfBoth = integerRepresentation(Integer.toString(Integer.parseInt(integerTrueValue(eOfBoth)) - count), eLength);
				sum = leftShift(sum, count);
			}
			//直接暴力截断
			sum = sum.substring(2, 2+sLength);
		}
		//判断是否溢出
		String allone = "";
		for(int i = 0;i<eLength;i++){
			allone = "1" + allone;
		}
		String result = "";
		if (eOfBoth.equals(allone)) {
			//上溢
			result = "1" + result;
		}else{
			result = "0" + result;
		}
		result = result + (operand1.charAt(0)^operand2.charAt(0)) + eOfBoth + sum;
		return result;
	}

	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		String qufan = negation(operand2.substring(eLength+1));
		String addone = oneAdder(qufan).substring(1);
		String output = floatAddition(operand1, operand2.substring(0, eLength+1)+addone, eLength, sLength, gLength);
		return output;
	}

	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}

	/**
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
}