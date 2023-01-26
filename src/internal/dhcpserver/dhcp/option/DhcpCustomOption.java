package internal.dhcpserver.dhcp.option;

import internal.dhcpserver.Utils;

public class DhcpCustomOption extends DhcpOption{
    int[] payload;
    private DhcpCustomOption(int code, int[] payload){
        super();
        this.code = code;
        this.payload = payload;
    }

    public static DhcpCustomOption getCustomOption(int code, int[] payload){
        return new DhcpCustomOption(code, payload);
    }
    public static DhcpCustomOption getCustomOption(int code){
        return new DhcpCustomOption(code, null);
    }

    @Override
    public byte[] toByteArray(){
        int payloadLen;
        if(payload != null){
            payloadLen = payload.length;
        }else {
            payloadLen = 0;
        }
        byte[] result = new byte[2 + payloadLen];
        result[0] = (byte) code;
        result[1] = (byte) payloadLen;
        if(payload != null){
            System.arraycopy(Utils.IntArrToSignedByteArr(payload), 0, result, 2, payloadLen);
        }
        return result;
    }
}
