package internal.dhcpserver;

import java.lang.reflect.Array;
import java.util.stream.StreamSupport;

public class Utils {

    public static boolean isFirstBit(int num){
        //проверяет установлен ли первый бит в единицу.
        System.out.println("debug: " + (num) + " " + String.valueOf((num >>> 31) == 1) + " "+ String.valueOf((num >>> 31)));
        return (num >>> 31) == 1;
    }

    public static int getUnsignedNumFrom2int(int n1, int n2){
        int result = (n1 << 8) + n2;
        return result;
    }

    public static long getUnsignedLongFrom4int(int n1, int n2, int n3, int n4){

        long result = (n1 << 8) + n2;
        result <<= 8;
        result += n3;
        result <<= 8;
        result += n4;
        return result;
    }

    public static int[] byteArrToIntArr(byte[] bytes){
        int[] result = new int[bytes.length];
        for(int i = 0; i < bytes.length; i++){
            result[i] = Byte.toUnsignedInt(bytes[i]);
        }
        return result;
    }

    public static int[] getUnsignedNumsFromSignedByteArray(byte[] bytes){
        int[] result = new int[bytes.length];
        for(int i = 0; i < result.length; i++){
            result[i] = Byte.toUnsignedInt(bytes[i]);
        }
        return result;
    }

    //называется так, потому что беззнаковые байты, полученные по сети
    //нужно представлять в java как знаковые int числа
    public static int[] getIntBytes(int[] src, int start, int length){
        int[] result = new int[length];
        for(int i = 0; i < length; i++){
            result[i] = src[start + i];
        }
        return result;
    }

    public static byte[] getBytes(byte[] src, int start, int length){
        byte[] result = new byte[length];
        for(int i = 0; i < length; i++){
            result[i] = src[start + i];
        }
        return result;
    }

    public static byte[] IntArrToSignedByteArr(int[] arr){
        byte[] result = new byte[arr.length];
        for(int i = 0; i < arr.length; i++){
            result[i] = (byte) arr[i];
        }
        return result;
    }

    public static byte[] longTo4uBytes(long number){
        byte[] result = new byte[4];
        result[0] = (byte) (number >>> 24);
        result[1] = (byte) (number >>> 16);
        result[2] = (byte) (number >>> 8);
        result[3] = (byte) number;
        return result;
    }

    public static byte[] intTo2uBytes(int number){
        byte[] result = new byte[2];
        result[0] = (byte) (number >>> 8);
        result[1] = (byte) number;
        return result;
    }

    public static String getStringFromIntArr(int[] arr){
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (arr[i] != 0){
            builder.append((char) (arr[i]));
            i++;
        }
        return builder.toString();
    }

    public static byte[] nullTermStrToBytes(String str, int size){
        char[] chars = str.toCharArray();
        byte[] result = new byte[size];
        for (int i = 0; i < result.length; i++){
            result[i] = 0;
        }
        for(int i = 0; i < chars.length; i++){
            result[i] = (byte) (chars[i]);
        }

        return result;
    }
}
