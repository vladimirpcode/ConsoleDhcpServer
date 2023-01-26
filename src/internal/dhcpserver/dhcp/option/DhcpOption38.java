package internal.dhcpserver.dhcp.option;


/*
   TCP Keepalive Interval Option

   This option specifies the interval (in seconds) that the client TCP
   should wait before sending a keepalive message on a TCP connection.
   The time is specified as a 32-bit unsigned integer.  A value of zero
   indicates that the client should not generate keepalive messages on
   connections unless specifically requested by an application.

   The code for this option is 38, and its length is 4.

    Code   Len           Time
   +-----+-----+-----+-----+-----+-----+
   |  38 |  4  |  t1 |  t2 |  t3 |  t4 |
   +-----+-----+-----+-----+-----+-----+
 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

public class DhcpOption38 extends DhcpOption{
    long keepaliveInterval;

    private DhcpOption38(){
        super();
        code = 38;
        variableLength = false;
        payloadLength = 4;
        name = "TCP Keepalive Interval Option";
    }

    public void setKeepaliveInterval(long keepaliveInterval){
        this.keepaliveInterval = keepaliveInterval;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption38 option = new DhcpOption38();
        option.minPayloadLength = option.payloadLength;
        option.keepaliveInterval = Utils.getUnsignedLongFrom4int(payload[0],payload[1],payload[2],payload[3]);
        return option;

    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.longTo4uBytes(keepaliveInterval);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption38 option = new DhcpOption38();
        option.keepaliveInterval = Long.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 38 - TCP Keepalive Interval Option\n\n"
            + "set option 38 <32bit_uint time interval>\n"
            + "set option 38 33123\n";
    }


}
