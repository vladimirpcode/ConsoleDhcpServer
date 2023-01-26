package internal.dhcpserver.dhcp.option;


/*
   Network Information Service+ Servers Option

   This option specifies a list of IP addresses indicating NIS+ servers
   available to the client.  Servers SHOULD be listed in order of
   preference.

   The code for this option is 65.  Its minimum length is 4, and the
   length MUST be a multiple of 4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  65 |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--

*/


import java.net.UnknownHostException;
import java.util.ArrayList;

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

public class DhcpOption65 extends DhcpOption{
    ArrayList<IpAddress> nisServers;

    private DhcpOption65(){
        super();
        code = 65;
        variableLength = true;
        payloadLength = -1;
        name = "Network Information Service+ Servers Option";
        minPayloadLength = 4;
        nisServers = new ArrayList<>();
    }

    public void addNisServer(IpAddress address){
        nisServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption65 option = new DhcpOption65();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.nisServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(65) bad nisServers address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:nisServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption65 option = new DhcpOption65();
        for(String s:str){
            option.addNisServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 65 - Network Information Service+ Servers Option\n\n"
            + "set option 65 {<ip_address address>}\n"
            + "set option 65 10.10.20.1 172.16.23.54\n";
    }
}
