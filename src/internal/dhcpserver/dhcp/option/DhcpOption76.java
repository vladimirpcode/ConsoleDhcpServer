package internal.dhcpserver.dhcp.option;


/*
   StreetTalk Directory Assistance (STDA) Server Option

   The StreetTalk Directory Assistance (STDA) server option specifies a
   list of STDA servers available to the client.  Servers SHOULD be
   listed in order of preference.

   The code for the StreetTalk Directory Assistance server option is 76.
   The minimum length for this option is 4 octets, and the length MUST
   always be a multiple of 4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   | 76  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
*/

import internal.dhcpserver.CriticalError;
import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;


import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption76 extends DhcpOption{
    ArrayList<IpAddress> stdaServers = new ArrayList<>();

    private DhcpOption76(){
        super();
        code = 76;
        variableLength = true;
        payloadLength = -1;
        name = "StreetTalk Directory Assistance (STDA) Server Option";
        minPayloadLength = 4;
    }


    public void addStdaServer(IpAddress address){
        stdaServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption76 option = new DhcpOption76();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.stdaServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(76) bad StreetTalk Directory Assistance (STDA) Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:stdaServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption76 option = new DhcpOption76();
        for(String s:str){
            option.addStdaServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 76 - StreetTalk Directory Assistance (STDA) Server Option\n\n"
            + "set option 76 {<ip_address address>}\n"
            + "set option 76 10.10.20.1 172.16.23.54\n";
    }
}
