package internal.dhcpserver.dhcp.option;


import java.net.UnknownHostException;
import java.util.ArrayList;

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;


/*
   Time Server Option

   The time server option specifies a list of RFC 868 [6] time servers
   available to the client.  Servers SHOULD be listed in order of
   preference.

   The code for the time server option is 4.  The minimum length for
   this option is 4 octets, and the length MUST always be a multiple of
   4.
   Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  4  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
 */

public class DhcpOption4 extends DhcpOption{
    ArrayList<IpAddress> timeServersAddresses;

    private DhcpOption4(){
        super();
        code = 4;
        variableLength = true;
        payloadLength = -1;
        name = "Time Server Option";
        timeServersAddresses = new ArrayList<>();
        minPayloadLength = 4;
    }


    public void addTimeServerAddress(IpAddress address){
        timeServersAddresses.add(address);
        payloadLength+=4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption4 option = new DhcpOption4();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.timeServersAddresses.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(4) bad Time Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:timeServersAddresses){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption4 option = new DhcpOption4();
        for(String ip:str){
            option.addTimeServerAddress(new IpAddress(ip));
        }
        return option;
    }
    public static String getCmdDescription(){return "Option 4 - Time Server Option\n\nset option 4 {<IpAddress_timeServer>}\n"
            +"set option 4 192.168.1.1 192.168.1.2 192.168.1.3\n";}
}
