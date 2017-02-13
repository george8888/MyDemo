package com.mqtt;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

/**
 * byte,short,int,long型数据与byte数组互转工具类
 */
public class DataConvertUtil {

    /**
     * 将有符号short型数据转换成byte数组 byte数组:高字节在前，低字节在后
     * 
     * @param s
     *            需要转换的数据
     * @return 转换之后的数据
     */
    public static byte[] shortToByteArray(Short s) {

        if ((s > Short.MAX_VALUE) || (s < Short.MIN_VALUE)) {
            return null;
        }

        byte[] bs = new byte[2];
        Short temp = s;

        for (int i = 0; i < 2; i++) {
            int offset = (bs.length - 1 - i) * 8;

            bs[i] = (byte)((temp >>> offset) & 0xff);
        }

        return bs;
    }

    /**
     * 将有符号int型数据转换成byte数组 byte数组:高字节在前，低字节在后
     * 
     * @param n
     *            int型数据
     * @return int型数据的byte数组表示
     */
    public static byte[] intToByteArray(int n) {

        if (n > Integer.MAX_VALUE || n < Integer.MIN_VALUE) {
            return null;
        }

        byte[] bs = new byte[4];
        int temp = n;

        for (int i = 0; i < 4; i++) {
            int offset = (bs.length - 1 - i) * 8;

            bs[i] = (byte)((temp >>> offset) & 0xff);
        }

        return bs;
    }

    /**
     * 将long型数据转换成byte数组 byte数组:高字节在前，低字节在后
     * 
     * @param l
     *            long型数据
     * @return long型数据的byte数组表示
     */
    public static byte[] longToByteArray(long l) {

        if ((l > Long.MAX_VALUE) || (l < Long.MIN_VALUE)) {
            return null;
        }

        byte[] bs = new byte[8];
        long temp = l;

        for (int i = 0; i < 8; i++) {
            int offset = (bs.length - 1 - i) * 8;

            bs[i] = (byte)((temp >>> offset) & 0xff);
        }

        return bs;
    }

    /**
     * 字节数组转化成无符号整数
     * 
     * @param bs
     *            需要转换的数组
     * @return 转换之后的long类型数据
     */
    public static long byteToUnsignInt(byte[] bs) {

        int mask = 0xff;
        int temp = 0;
        long res = 0;
        int byteslen = bs.length;

        if (byteslen > 8) {
            return Long.valueOf(0L);
        }

        for (int i = 0; i < byteslen; i++) {
            res <<= 8;
            temp = bs[i] & mask;
            res |= temp;
        }

        return res;
    }

    public static long byteToSignInt(byte[] bs) {
        int mask = 0xff;
        int temp = 0;
        long res = 0L;
        int byteslen = bs.length;

        if (byteslen > 8) {
            return Long.valueOf(0L);
        }

        for (int i = 0; i < byteslen; i++) {
            res <<= 8;
            if (i == 0 && bs[i] < 0) {
                temp = bs[i];
            } else {
                temp = bs[i] & mask;
            }
            res |= temp;
        }

        return res;
    }

    /**
     * 将data字节型数据转换为0~255 (0xFF 即BYTE)。
     * 
     * @param data
     *            需要转换的数据
     * @return 转换之后的数据
     */
    public static int getUnsignedByte(byte data) {

        return data & 0x0FF;
    }

    /**
     * 将data short数据转换为0~65535 (0xFFFF 即 WORD)
     * 
     * @param data
     *            需要转换的数据
     * @return 转换之后的数据
     */
    public static int getUnsignedByte(Short data) {

        return data & 0x0FFFF;
    }

    /**
     * 将int型数据转换为0~4294967295 (0xFFFFFFFF即DWORD)
     * 
     * @param data
     *            需要转换的数据
     * @return 转换之后的数据
     */
    public static long getUnsignedInt(int data) {

        return data & 0x0FFFFFFFFL;
    }

    /**
     * 高字节数组到short的转换
     * 
     * @param b
     *            需要转换的数据
     * @return short 转换之后的数据
     */
    public static Short hBytesToShort(byte[] b) {

        int s = 0;

        if (b[0] >= 0) {
            s = s + b[0];
        } else {
            s = s + 256 + b[0];
        }

        s = s * 256;

        if (b[1] >= 0) {
            s = s + b[1];
        } else {
            s = s + 256 + b[1];
        }

        short result = (short)s;

        return result;
    }

    /**
     * 将高字节数组转换为int
     * 
     * @param b
     *            需要转换的数据
     * @return 转换之后的数据
     */
    public static int hBytesToInt(byte[] b) {

        int s = 0;

        for (int i = 0; i < 3; i++) {
            if (b[i] >= 0) {
                s = s + b[i];
            } else {
                s = s + 256 + b[i];
            }

            s = s * 256;
        }

        if (b[3] >= 0) {
            s = s + b[3];
        } else {
            s = s + 256 + b[3];
        }

        return s;
    }

    /**
     * 将byte数组转换为有符号bit位组合
     * 
     * @param b
     *            需要转换的数据
     * @return 转换之后的二进制形式字符串
     */
    public static String getBit(byte[] b) {

        StringBuffer sbf = new StringBuffer();
        int[] bit = new int[8];

        for (int j = 0; j < b.length; j++) {
            for (int i = 0; i < bit.length; i++) {
                bit[8 - i - 1] = (b[j] >> i) & 1;
            }
            for (int i : bit) {
                sbf.append(i);
            }
        }
        return sbf.toString();
    }

    /**
     * 将二进制串转换成日期格式 ：2013-01-22 09:25:35
     * 
     * @param binary
     *            二进制串（4位）
     * @return 转换之后的时间
     */
    public static String binaryToDate(byte[] b) {

        String binary = getBit(b);
        if (binary == null || "".equals(binary) || binary.length() < 32) {
            return null;
        }
        String date = "";
        String yearBin = binary.substring(0, 6);
        String monthBin = binary.substring(6, 10);
        String dayBin = binary.substring(10, 15);
        String hourBin = binary.substring(15, 20);
        String minBin = binary.substring(20, 26);
        String secBin = binary.substring(26, 32);

        String year = new DecimalFormat("00").format(Integer.valueOf(yearBin, 2));
        String month = new DecimalFormat("00").format(Integer.valueOf(monthBin, 2));
        String day = new DecimalFormat("00").format(Integer.valueOf(dayBin, 2));
        String hour = new DecimalFormat("00").format(Integer.valueOf(hourBin, 2));
        String min = new DecimalFormat("00").format(Integer.valueOf(minBin, 2));
        String second = new DecimalFormat("00").format(Integer.valueOf(secBin, 2));

        date = "20" + year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + second;

        return date;
    }

    /**
     * 
     * @param date
     * date的格式必须是 2014-08-25 11:37:41
     * @return
     */
    public static String dateToBinary(String date) {
    	if (date == null || "".equals(date)) {
            return null;
        }
    	String [] strArray = date.split(" ");
    	String str1 = strArray[0];
    	String str2 = strArray[1];
    	String [] StrArray2 = str1.split("-");
    	String [] StrArray3 = str1.split(":");
    	String year = StrArray2[0];
    	String month = StrArray2[1];
    	String day = StrArray2[2];
    	String hour =StrArray3[0] ;
    	String min = StrArray3[1];
    	String second = StrArray3[2];
    	
    	String year2 = new DecimalFormat("00").format(Integer.valueOf(year, 2));
    	System.out.println(year2);
    	StringBuffer sbf = new StringBuffer();
        int[] bit = new int[8];

         
    	//String binary = getBit(b);
        
      /*  String yearBin = binary.substring(0, 6);
        String monthBin = binary.substring(6, 10);
        String dayBin = binary.substring(10, 15);
        String hourBin = binary.substring(15, 20);
        String minBin = binary.substring(20, 26);
        String secBin = binary.substring(26, 32);*/

       /* String year = new DecimalFormat("00").format(Integer.valueOf(yearBin, 2));
        String month = new DecimalFormat("00").format(Integer.valueOf(monthBin, 2));
        String day = new DecimalFormat("00").format(Integer.valueOf(dayBin, 2));
        String hour = new DecimalFormat("00").format(Integer.valueOf(hourBin, 2));
        String min = new DecimalFormat("00").format(Integer.valueOf(minBin, 2));
        String second = new DecimalFormat("00").format(Integer.valueOf(secBin, 2));*/

        

        return null;
    }

    /**
     * <p>
     * 方法描述: [字符串左补0]
     * </p>
     * 
     * @param str
     *            需要补零的字符串
     * @param strLength
     *            转换后的长度
     * 
     * @return 处理后的字符串
     */
    public static String addZeroForString(String str, int strLength) {

        int strLen = str.length();

        StringBuffer sb = new StringBuffer();
        if (strLen < strLength) {
            while (strLen < strLength) {
                // 左补0
                sb.append("0");
                // 右补0
                // // sb.append(str).append("0");
                // str = sb.toString();
                // strLen = str.length();
                strLen++;
            }
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 十六进制转二进制
     * 
     * @param hexString
     * @return
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String tmp;
        StringBuffer bString = new StringBuffer("");
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString.append(tmp.substring(tmp.length() - 4));
        }
        return bString.toString();
    }

    /**
     * 十六进制字符串转Byte数组
     * 
     * @param hexStringArrary
     *            十六进制字符串数组
     * @return 返回转换后的byte数组
     */
    public static byte[] hexStringToBytes(String[] hexStringArrary) {
        StringBuffer strbf = new StringBuffer();
        for (String tmpStr : hexStringArrary) {
            strbf.append(tmpStr);
        }
        String hexString = strbf.toString();
        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }
    
    /**
     * 十六进制字符串转Byte数组
     * 
     * @param hexString
     *            十六进制字符串
     * @return 返回转换后的byte数组
     */
    public static byte[] hexStringToBytes(String hexString) {

        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 二进制转十六进制
     * 
     * @param bString
     *            二进制字符串
     * @return 转换后的16进制数
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }


    /**
     * byte数组转16进制字符串
     * @param b 需要转换的byte数组
     * @return 转换之后的16进制字符串
     */
    public static String byte2HexString(byte[] b) {
        return Encodes.encodeHex(b);
    }

    /**
     * 字符串填充（追加形式）
     * @param srcStr 需要填充的字符串
     * @param fillStr 填充字符串
     * @param tarLen 转换之后的长度
     * @return 填充之后的字符串
     */
    public static String strRightFiill(String srcStr, String fillStr, int tarLen) {
        // 判断填充的字符串和长度是否为空。为空直接返回srcStr
        if (StringUtils.isNotBlank(fillStr) && tarLen > 0) {
            StringBuffer strBf = new StringBuffer();
            // 判断需要填充的字符串是否为空
            if (StringUtils.isNotBlank(srcStr)) {
                int srcStrLen = srcStr.length();
                strBf.append(srcStr);
                while (srcStrLen < tarLen) {
                    strBf.append(fillStr);
                    srcStrLen = fillStr.length() + srcStrLen;
                }
            } else {
                int srcStrLen = 0;
                while (srcStrLen < tarLen) {
                    strBf.append(fillStr);
                    srcStrLen = fillStr.length() + srcStrLen;
                }
            }
            return strBf.toString();
        } else {
            return srcStr;
        }
    }

    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }
}
