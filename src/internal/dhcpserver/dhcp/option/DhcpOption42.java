package internal.dhcpserver.dhcp.option;


/*
   Network Time Protocol Servers Option

   This option specifies a list of IP addresses indicating NTP [18]
   servers available to the client.  Servers SHOULD be listed in order
   of preference.

   The code for this option is 42.  Its minimum length is 4, and the
   length MUST be a multiple of 4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  42 |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--

 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption42 extends DhcpOption{
    ArrayList<IpAddress> ntpServers;

    private DhcpOption42(){
        super();
        code = 42;
        variableLength = true;
        payloadLength = -1;
        name = "Network Time Protocol Servers Option";
        minPayloadLength = 4;
    }

    public void addNtpServer(IpAddress address){
        ntpServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption42 option = new DhcpOption42();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.ntpServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(42) Network Time Protocol Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:ntpServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption42 option = new DhcpOption42();
        for(String s:str){
            option.addNtpServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 42 - Network Time Protocol Servers Option\n\n"
            + "set option 42 {<IpAddress NtpServer>}\n"
            + "set option 42 192.168.10.2 192.168.1.1 10.10.50.1\n";
    }
}
