package internal.dhcpserver.dhcp.option;


/*
    StreetTalk Server Option

   The StreetTalk server option specifies a list of StreetTalk servers
   available to the client.  Servers SHOULD be listed in order of
   preference.

   The code for the StreetTalk server option is 75.  The minimum length
   for this option is 4 octets, and the length MUST always be a multiple
   of 4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   | 75  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
*/

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;


import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption75 extends DhcpOption{
    ArrayList<IpAddress> streetTalkServers = new ArrayList<>();


    private DhcpOption75(){
        super();
        code = 75;
        variableLength = true;
        payloadLength = -1;
        name = "StreetTalk Server Option";
        minPayloadLength = 4;
    }


    public void addStreetTalkServer(IpAddress address){
        streetTalkServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption75 option = new DhcpOption75();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.streetTalkServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(75) bad StreetTalk Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:streetTalkServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption75 option = new DhcpOption75();
        for(String s:str){
            option.addStreetTalkServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 75 - StreetTalk Server Option\n\n"
            + "set option 75 {<ip_address address>}\n"
            + "set option 75 10.10.20.1 172.16.23.54\n";
    }
}
