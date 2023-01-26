package internal.dhcpserver.dhcp.option;


/*
   Perform Mask Discovery Option

   This option specifies whether or not the client should perform subnet
   mask discovery using ICMP.  A value of 0 indicates that the client
   should not perform mask discovery.  A value of 1 means that the
   client should perform mask discovery.

   The code for this option is 29, and its length is 1.

    Code   Len  Value
   +-----+-----+-----+
   |  29 |  1  | 0/1 |
   +-----+-----+-----+
 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;

public class DhcpOption29 extends DhcpOption{
    int value;
    private DhcpOption29(){
        super();
        code = 29;
        variableLength = false;
        payloadLength = 1;
        name = "Perform Mask Discovery Option";
    }

    public void setValue(int value){
        this.value = value;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption29 option = new DhcpOption29();
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
        DhcpOption29 option = new DhcpOption29();
        option.value = Integer.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 29 - Perform Mask Discovery Option\n\n"
            + "set option 29 <1bit_int flag>\n"
            + "set option 29 1\n";
    }

}
