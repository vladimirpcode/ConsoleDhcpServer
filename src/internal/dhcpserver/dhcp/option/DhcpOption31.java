package internal.dhcpserver.dhcp.option;


/*
   Perform Router Discovery Option

   This option specifies whether or not the client should solicit
   routers using the Router Discovery mechanism defined in RFC 1256
   [13].  A value of 0 indicates that the client should not perform
   router discovery.  A value of 1 means that the client should perform
   router discovery.

   The code for this option is 31, and its length is 1.

    Code   Len  Value
   +-----+-----+-----+
   |  31 |  1  | 0/1 |
   +-----+-----+-----+
 */

import internal.dhcpserver.Utils;

public class DhcpOption31 extends DhcpOption{
    int value;
    private DhcpOption31(){
        super();
        code = 31;
        variableLength = false;
        payloadLength = 1;
        name = "Perform Router Discovery Option";
    }

    public void setValue(int value){
        this.value = value;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption31 option = new DhcpOption31();
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
        DhcpOption31 option = new DhcpOption31();
        option.value = Integer.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 31 - Perform Router Discovery Option\n\n"
            + "set option 31 <1bit_int flag>\n"
            + "set option 31 1\n";
    }
}
