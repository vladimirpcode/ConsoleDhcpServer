package internal.dhcpserver.dhcp.option;


/*
   Mask Supplier Option

   This option specifies whether or not the client should respond to
   subnet mask requests using ICMP.  A value of 0 indicates that the
   client should not respond.  A value of 1 means that the client should
   respond.

   The code for this option is 30, and its length is 1.

    Code   Len  Value
   +-----+-----+-----+
   |  30 |  1  | 0/1 |
   +-----+-----+-----+
 */

import internal.dhcpserver.Utils;

public class DhcpOption30 extends DhcpOption{
    int value;
    private DhcpOption30(){
        super();
        code = 30;
        variableLength = false;
        payloadLength = 1;
        name = "Mask Supplier Option";
    }

    public void setValue(int value){
        this.value = value;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption30 option = new DhcpOption30();
        option.minPayloadLength = 1;
        option.value = payload[0];
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = new byte[]{(byte)value};
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption30 option = new DhcpOption30();
        option.value = Integer.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 30 - Mask Supplier Option\n\n"
            + "set option 30 <1bit_int flag>\n"
            + "set option 30 1\n";
    }
}
