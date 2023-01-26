package internal.dhcpserver.dhcp.option;


/*
    Path MTU Aging Timeout Option

   This option specifies the timeout (in seconds) to use when aging Path
   MTU values discovered by the mechanism defined in RFC 1191 [12].  The
   timeout is specified as a 32-bit unsigned integer.

   The code for this option is 24, and its length is 4.

    Code   Len           Timeout
   +-----+-----+-----+-----+-----+-----+
   |  24 |  4  |  t1 |  t2 |  t3 |  t4 |
   +-----+-----+-----+-----+-----+-----+
 */

import internal.dhcpserver.Utils;

public class DhcpOption24 extends DhcpOption{
    long timeout;

    private DhcpOption24(){
        super();
        code = 24;
        variableLength = false;
        payloadLength =4;
        name = "Path MTU Aging Timeout Option";
    }

    public void setTimeout(long timeout){
        this.timeout = timeout;
    }


    public static DhcpOption valueOf(int[] payload){
        DhcpOption24 option = new DhcpOption24();
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
        DhcpOption24 option = new DhcpOption24();
        option.timeout = Long.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 24 - Path MTU Aging Timeout Option\n\n"
            + "set option 24 <32bit_uint_timeout>\n"
            + "set option 24 1120\n";
    }
}
