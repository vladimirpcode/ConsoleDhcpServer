package internal.dhcpserver.dhcp.option;


/*
   NetBIOS over TCP/IP Node Type Option

   The NetBIOS node type option allows NetBIOS over TCP/IP clients which
   are configurable to be configured as described in RFC 1001/1002.  The
   value is specified as a single octet which identifies the client type
   as follows:

      Value         Node Type
      -----         ---------
      0x1           B-node
      0x2           P-node
      0x4           M-node
      0x8           H-node

   In the above chart, the notation '0x' indicates a number in base-16
   (hexadecimal).

   The code for this option is 46.  The length of this option is always
   1.

    Code   Len  Node Type
   +-----+-----+-----------+
   |  46 |  1  | see above |
   +-----+-----+-----------+
 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

import java.util.ArrayList;

public class DhcpOption46 extends DhcpOption{
    int value;

    private DhcpOption46(){
        super();
        code = 46;
        variableLength = false;
        payloadLength = 1;
        name = "NetBIOS over TCP/IP Node Type Option";
    }

    public void setValue(int value){
        this.value = value;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption46 option = new DhcpOption46();
        option.minPayloadLength = option.payloadLength;
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
        DhcpOption46 option = new DhcpOption46();
        option.value = Integer.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 46 - NetBIOS over TCP/IP Node Type Option\n\n"
            + "set option 46 <1bit_int flag>\n"
            + "set option 46 1\n";
    }
}
