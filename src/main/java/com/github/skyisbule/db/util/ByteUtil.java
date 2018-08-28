package com.github.skyisbule.db.util;

//byte操作的util
public class ByteUtil {

    public static byte[] arraycopy(byte[] target,int pos,byte[] newBytes){
        System.arraycopy(newBytes,0,target,pos,newBytes.length);
        return target;
    }

    public static byte[] intToByte4(int sum) {
        byte[] arr = new byte[4];
        arr[0] = (byte) (sum >> 24);
        arr[1] = (byte) (sum >> 16);
        arr[2] = (byte) (sum >> 8);
        arr[3] = (byte) (sum & 0xff);
        return arr;
    }

    public static int byteToInt4(byte[] bytes){
            return   bytes[3] & 0xFF |
                    (bytes[2] & 0xFF) << 8 |
                    (bytes[1] & 0xFF) << 16 |
                    (bytes[0] & 0xFF) << 24;
    }

    public static byte[] getTimeStampByte4(){
        Integer time = (int)System.currentTimeMillis();
        return intToByte4(time);
    }

}
