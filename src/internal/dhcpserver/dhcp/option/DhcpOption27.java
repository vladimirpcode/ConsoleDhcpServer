package internal.dhcpserver.dhcp.option;


/*
    All Subnets are Local Option

   This option specifies whether or not the client may assume that all
   subnets of the IP network to which the client is connected use the
   same MTU as the subnet of that network to which the client is
   directly connected.  A value of 1 indicates that all subnets share
   the same MTU.  A value of 0 means that the client should assume that
   some subnets of the directly connected network may have smaller MTUs.


   The code for this option is 27, and its length is 1.

    Code   Len  Value
   +-----+-----+-----+
   |  27 |  1  | 0/1 |
   +-----+-----+-----+
 */

import internal.dhcpserver.Utils;

public class DhcpOption27 extends DhcpOption{
    int value;
    private DhcpOption27(){
        super();
        code = 27;
        variableLength = false;
        payloadLength = 1;
        name = "All Subnets are Local Option";
    }

    public void setValue(int value){
        this.value = value;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption27 option = new DhcpOption27();
        option.minPayloadLength = payload.length;
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
        DhcpOption27 option = new DhcpOption27();
        option.value = Integer.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 27 - All Subnets are Local Option\n\n"
            + "set option 27 <1bit_flag>\n"
            + "set option 27 1\n";
    }

}
