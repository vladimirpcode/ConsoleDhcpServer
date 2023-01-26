package internal.dhcpserver.dhcp.option;


/*
   TCP Default TTL Option

   This option specifies the default TTL that the client should use when
   sending TCP segments.  The value is represented as an 8-bit unsigned
   integer.  The minimum value is 1.

   The code for this option is 37, and its length is 1.

    Code   Len   TTL
   +-----+-----+-----+
   |  37 |  1  |  n  |
   +-----+-----+-----+
 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

public class DhcpOption37 extends DhcpOption{
    int tcpTTL;

    private DhcpOption37(){
        super();
        code = 37;
        variableLength = false;
        payloadLength = 1;
        name = "TCP Default TTL Option";
    }

    public void setTcpTTL(int tcpTTL){
        this.tcpTTL = tcpTTL;
    }

    public static DhcpOption valueOf(int[] payload){
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = new byte[1];
        bytes[0] = (byte)tcpTTL;
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption37 option = new DhcpOption37();
        option.tcpTTL = Integer.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 37 - TCP Default TTL Option\n\n"
            + "set option 37 <8bit_uint TcpTtl\n"
            + "set option 37 140\n";
    }
}
