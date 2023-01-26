package internal.dhcpserver.dhcp.option;


/*
   Ethernet Encapsulation Option

   This option specifies whether or not the client should use Ethernet
   Version 2 (RFC 894 [15]) or IEEE 802.3 (RFC 1042 [16]) encapsulation
   if the interface is an Ethernet.  A value of 0 indicates that the
   client should use RFC 894 encapsulation.  A value of 1 means that the
   client should use RFC 1042 encapsulation.

   The code for this option is 36, and its length is 1.

    Code   Len  Value
   +-----+-----+-----+
   |  36 |  1  | 0/1 |
   +-----+-----+-----+
 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

public class DhcpOption36 extends DhcpOption{
    int value;

    private DhcpOption36(){
        super();
        code = 36;
        variableLength = false;
        payloadLength = 1;
        name = "Ethernet Encapsulation Option";
    }

    public void setValue(int value){
        this.value = value;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption36 option = new DhcpOption36();
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
        DhcpOption36 option = new DhcpOption36();
        option.value = Integer.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 36 - Ethernet Encapsulation Option\n\n"
            + "set option 36 <1bit_int flag>\n"
            + "set option 36 1\n";
    }
}
