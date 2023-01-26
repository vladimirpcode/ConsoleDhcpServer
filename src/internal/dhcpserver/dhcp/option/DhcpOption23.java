package internal.dhcpserver.dhcp.option;


/*
    Default IP Time-to-live

   This option specifies the default time-to-live that the client should
   use on outgoing datagrams.  The TTL is specified as an octet with a
   value between 1 and 255.

   The code for this option is 23, and its length is 1.

    Code   Len   TTL
   +-----+-----+-----+
   |  23 |  1  | ttl |
   +-----+-----+-----+
 */

import internal.dhcpserver.Utils;

public class DhcpOption23 extends DhcpOption{
    int ipTTL;

    private DhcpOption23(){
        super();
        code = 23;
        variableLength = false;
        payloadLength = 1;
        name = "Default IP Time-to-live";
    }

    public void setIpTTL(int ipTTL){
        this.ipTTL = ipTTL;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption23 option = new DhcpOption23();
        option.minPayloadLength = option.payloadLength;
        option.ipTTL = payload[0];
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = new byte[]{(byte)ipTTL};
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption23 option = new DhcpOption23();
        option.setIpTTL(Integer.valueOf(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 23 - Default IP Time-to-live\n\n"
            + "set option 23 <8bit_uint_IpTTL>\n"
            + "set option 23 110\n";
    }

}
