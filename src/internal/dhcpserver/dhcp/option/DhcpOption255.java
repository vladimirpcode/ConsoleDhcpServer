package internal.dhcpserver.dhcp.option;


/*
   End Option

   The end option marks the end of valid information in the vendor
   field.  Subsequent octets should be filled with pad options.

   The code for the end option is 255, and its length is 1 octet.

    Code
   +-----+
   | 255 |
   +-----+
*/

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption255 extends DhcpOption{

    public DhcpOption255(){
        super();
        code = 255;
        variableLength = false;
        payloadLength =  0;
        name = "End Option";
        minPayloadLength = 0;
    }



    public static DhcpOption valueOf(int[] payload){
        return new DhcpOption255();
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[1];
        result[0] = (byte) code;
        return result;
    }

}
