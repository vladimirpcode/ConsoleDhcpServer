package internal.dhcpserver.dhcp.option;


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;


/*
   Log Server Option

   The log server option specifies a list of MIT-LCS UDP log servers
   available to the client.  Servers SHOULD be listed in order of
   preference.

   The code for the log server option is 7.  The minimum length for this
   option is 4 octets, and the length MUST always be a multiple of 4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  7  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
 */

public class DhcpOption7 extends DhcpOption{
    ArrayList<IpAddress> logServersAddresses;

    public DhcpOption7(){
        super();
        code = 7;
        variableLength = true;
        payloadLength = -1;
        name = "Log Server Option";
        logServersAddresses = new ArrayList<>();
        minPayloadLength = 4;
    }

    public void addLogServerAddress(IpAddress address){
        logServersAddresses.add(address);
        payloadLength+=4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption7 option = new DhcpOption7();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.logServersAddresses.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(7) bad Log Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:logServersAddresses){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption7 option = new DhcpOption7();
        for(String ip:str){
            option.addLogServerAddress(new IpAddress(ip));
        }
        return option;
    }
    public static String getCmdDescription(){return "Option 7 - Log Server Option Option\n\n"
            + "set option 7 {<IpAddress_logServer>}\n"
            + "set option 7 192.168.100.120 10.10.31.150 172.16.15.1\n";}
}
