package internal.dhcpserver.dhcp.option;


/*
   Non-Local Source Routing Enable/Disable Option

   This option specifies whether the client should configure its IP
   layer to allow forwarding of datagrams with non-local source routes
   (see Section 3.3.5 of [4] for a discussion of this topic).  A value
   of 0 means disallow forwarding of such datagrams, and a value of 1
   means allow forwarding.

   The code for this option is 20, and its length is 1.

    Code   Len  Value
   +-----+-----+-----+
   |  20 |  1  | 0/1 |
   +-----+-----+-----+
 */

import internal.dhcpserver.Utils;

public class DhcpOption20 extends DhcpOption{
    int value;

    private DhcpOption20(){
        super();
        code = 20;
        variableLength = false;
        payloadLength = 1;
        name = "Non-Local Source Routing Enable/Disable Option";
    }

    public void setValue(int value){
        if(value == 0){
            this.value = 0;
        }else{
            this.value = 1;
        }
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption20 option = new DhcpOption20();
        option.minPayloadLength = option.payloadLength;
        option.value = payload[0];
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = new byte[]{(byte) value};
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption20 option = new DhcpOption20();
        option.setValue(Integer.valueOf(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 20 - Non-Local Source Routing Enable/Disable Option\n\n"
            + "set option 20 <0 | 1>\n"
            + "set option 20 1\n";
    }

}
