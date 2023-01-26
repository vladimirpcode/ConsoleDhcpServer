package internal.dhcpserver.dhcp.option;


/*
    Default Internet Relay Chat (IRC) Server Option

   The IRC server option specifies a list of IRC available to the
   client.  Servers SHOULD be listed in order of preference.

   The code for the IRC server option is 74.  The minimum length for
   this option is 4 octets, and the length MUST always be a multiple of
   4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   | 74  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--


*/

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;


import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption74 extends DhcpOption{
    ArrayList<IpAddress> ircServers = new ArrayList<>();

    private DhcpOption74(){
        super();
        code = 74;
        variableLength = true;
        payloadLength = -1;
        name = "Default Internet Relay Chat (IRC) Server Option";
        minPayloadLength = 4;
    }


    public void addIrcServer(IpAddress address){
        ircServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption74 option = new DhcpOption74();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.ircServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(74) bad Default Internet Relay Chat (IRC) Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:ircServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption74 option = new DhcpOption74();
        for(String s:str){
            option.addIrcServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 74 - Default Internet Relay Chat (IRC) Server Option\n\n"
            + "set option 74 {<ip_address address>}\n"
            + "set option 74 10.10.20.1 172.16.23.54\n";
    }
}
