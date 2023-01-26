package internal.dhcpserver.dhcp.option;


/*
   Requested IP Address

   This option is used in a client request (DHCPDISCOVER) to allow the
   client to request that a particular IP address be assigned.

   The code for this option is 50, and its length is 4.

    Code   Len          Address
   +-----+-----+-----+-----+-----+-----+
   |  50 |  4  |  a1 |  a2 |  a3 |  a4 |
   +-----+-----+-----+-----+-----+-----+
*/


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption50 extends DhcpOption{
    IpAddress requestedIpAddress;
    private DhcpOption50(){
        super();
        code = 50;
        variableLength = false;
        payloadLength = 4;
        name = "Requested IP Address";
    }

    public void setRequestedAddress(IpAddress address){
        requestedIpAddress = address;
    }


    public static DhcpOption valueOf(int[] payload){
        DhcpOption50 option = new DhcpOption50();
        try {
            option.requestedIpAddress = new IpAddress(payload);
        }
        catch (UnknownHostException exc){
            Warning.msg("bad requested IP address");
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = requestedIpAddress.toByteArray();
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }


}
