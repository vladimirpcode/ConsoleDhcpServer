package internal.dhcpserver.dhcp.option;


/*
   IP Forwarding Enable/Disable Option

   This option specifies whether the client should configure its IP
   layer for packet forwarding.  A value of 0 means disable IP
   forwarding, and a value of 1 means enable IP forwarding.

   The code for this option is 19, and its length is 1.

    Code   Len  Value
   +-----+-----+-----+
   |  19 |  1  | 0/1 |
   +-----+-----+-----+
 */

import internal.dhcpserver.Utils;

public class DhcpOption19 extends DhcpOption{
    int value;

    private DhcpOption19(){
        super();
        code = 19;
        variableLength = false;
        payloadLength = 1;
        name = "IP Forwarding Enable/Disable Option";
    }

    public void setValue(int value){
        if(value == 0){
            this.value = 0;
        }else{
            this.value = 1;
        }
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption19 option = new DhcpOption19();
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
        DhcpOption19 option = new DhcpOption19();
        option.setValue(Integer.valueOf(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 19 - IP Forwarding Enable/Disable Option\n\n"
            + "set option 19 <0 | 1>\n"
            + "set option 19 1\n";
    }
}
