package internal.dhcpserver.dhcp.option;


/*
    Broadcast Address Option

   This option specifies the broadcast address in use on the client's
   subnet.  Legal values for broadcast addresses are specified in
   section 3.2.1.3 of [4].

   The code for this option is 28, and its length is 4.

    Code   Len     Broadcast Address
   +-----+-----+-----+-----+-----+-----+
   |  28 |  4  |  b1 |  b2 |  b3 |  b4 |
   +-----+-----+-----+-----+-----+-----+
 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;

public class DhcpOption28 extends DhcpOption{
    IpAddress broadcastAddress;
    public DhcpOption28(){
        super();
        code = 28;
        variableLength = false;
        payloadLength = 4;
        name = "Broadcast Address Option";
    }

    public void setBroadcastAddress(IpAddress broadcastAddress){
        this.broadcastAddress = broadcastAddress;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption28 option = new DhcpOption28();
        option.minPayloadLength = payload.length;
        try {
            option.broadcastAddress = new IpAddress(payload);
        }catch (UnknownHostException exc){
            Warning.msg("Option (28): bad broadcast address");
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = broadcastAddress.toByteArray();
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption28 option = new DhcpOption28();
        option.setBroadcastAddress(new IpAddress(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 28 - Broadcast Address Option\n\n"
            + "set option 28 <IpAddress broadcast>\n"
            + "set option 28 10.255.255.255\n";
    }
}
