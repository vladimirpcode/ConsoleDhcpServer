package internal.dhcpserver.dhcp.option;


/*
   Trailer Encapsulation Option

   This option specifies whether or not the client should negotiate the
   use of trailers (RFC 893 [14]) when using the ARP protocol.  A value
   of 0 indicates that the client should not attempt to use trailers.  A
   value of 1 means that the client should attempt to use trailers.

   The code for this option is 34, and its length is 1.

    Code   Len  Value
   +-----+-----+-----+
   |  34 |  1  | 0/1 |
   +-----+-----+-----+

 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

import java.util.ArrayList;

public class DhcpOption34 extends DhcpOption{
    int value;

    private DhcpOption34(){
        super();
        code = 34;
        variableLength = false;
        payloadLength = 1;
        name = "Trailer Encapsulation Option";
    }

    public void setValue(int value){
        this.value = value;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption34 option = new DhcpOption34();
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
        DhcpOption34 option = new DhcpOption34();
        option.value = Integer.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 34 - Trailer Encapsulation Option\n\n"
            + "set option 34 <1bit_int flag\n"
            + "set option 34 1\n";
    }
}
