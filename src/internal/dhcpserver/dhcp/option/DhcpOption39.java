package internal.dhcpserver.dhcp.option;


/*
   TCP Keepalive Garbage Option

   This option specifies the whether or not the client should send TCP
   keepalive messages with a octet of garbage for compatibility with
   older implementations.  A value of 0 indicates that a garbage octet
   should not be sent. A value of 1 indicates that a garbage octet
   should be sent.

   The code for this option is 39, and its length is 1.

    Code   Len  Value
   +-----+-----+-----+
   |  39 |  1  | 0/1 |
   +-----+-----+-----+

 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

public class DhcpOption39 extends DhcpOption{
    int value;

    private DhcpOption39(){
        super();
        code = 39;
        variableLength = false;
        payloadLength = 1;
        name = "TCP Keepalive Garbage Option";
    }

    public void setValue(int value){
        this.value = value;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption39 option = new DhcpOption39();
        option.minPayloadLength = 1;
        option.value = payload[0];
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = new byte[1];
        bytes[0] = (byte)value;
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption39 option = new DhcpOption39();
        option.value = Integer.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 39 - TCP Keepalive Garbage Option\n\n"
            + "set option 39 <1bit_int flag>\n"
            + "set option 39 1\n";
    }
}
