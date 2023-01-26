package internal.dhcpserver.dhcp.option;


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;


/*
   Resource Location Server Option

   This option specifies a list of RFC 887 [11] Resource Location
   servers available to the client.  Servers SHOULD be listed in order
   of preference.
   The code for this option is 11.  The minimum length for this option
   is 4 octets, and the length MUST always be a multiple of 4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  11 |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
 */

public class DhcpOption11 extends DhcpOption{
    ArrayList<IpAddress> resourceLocationServersAddresses;

    private DhcpOption11(){
        super();
        code = 11;
        variableLength = true;
        payloadLength = -1;
        name = "Resource Location Server Option";
        resourceLocationServersAddresses = new ArrayList<>();
        minPayloadLength = 4;
    }

    public void addResourceLocationServerAddress(IpAddress address){
        resourceLocationServersAddresses.add(address);
        payloadLength+=4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption11 option = new DhcpOption11();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.addResourceLocationServerAddress(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(11) bad Resource Location Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:resourceLocationServersAddresses){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption11 option = new DhcpOption11();
        for(String ip:str){
            option.resourceLocationServersAddresses.add(new IpAddress(ip));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 11 - Resource Location Server Option\n\n"
            + "set option 11 {<IpAddress_ResourceLocationServer>}\n"
            + "set option 11 172.16.1.1 172.16.10.2 192.168.10.10\n";
    }
}
