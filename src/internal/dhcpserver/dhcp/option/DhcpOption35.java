package internal.dhcpserver.dhcp.option;


/*
   ARP Cache Timeout Option

   This option specifies the timeout in seconds for ARP cache entries.
   The time is specified as a 32-bit unsigned integer.

   The code for this option is 35, and its length is 4.

    Code   Len           Time
   +-----+-----+-----+-----+-----+-----+
   |  35 |  4  |  t1 |  t2 |  t3 |  t4 |
   +-----+-----+-----+-----+-----+-----+
 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

public class DhcpOption35 extends DhcpOption{
    long timeout;

    private DhcpOption35(){
        super();
        code = 35;
        variableLength = false;
        payloadLength = 4;
        name = "ARP Cache Timeout Option";
    }

    public void setTimeout(long timeout){
        this.timeout = timeout;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption35 option = new DhcpOption35();
        option.minPayloadLength = option.payloadLength;
        option.timeout = Utils.getUnsignedLongFrom4int(payload[0],payload[1],payload[2],payload[3]);
        return option;

    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.longTo4uBytes(timeout);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption35 option = new DhcpOption35();
        option.timeout = Long.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 35 - ARP Cache Timeout Option\n\n"
            + "set option 35 <32bit_uint timeout>\n"
            + "set option 35 235\n";
    }
}
