package internal.dhcpserver.dhcp.option;


/*
    Post Office Protocol (POP3) Server Option

   The POP3 server option specifies a list of POP3 available to the
   client.  Servers SHOULD be listed in order of preference.

   The code for the POP3 server option is 70.  The minimum length for
   this option is 4 octets, and the length MUST always be a multiple of
   4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   | 70  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--

*/


import java.net.UnknownHostException;
import java.util.ArrayList;

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

public class DhcpOption70 extends DhcpOption{
    ArrayList<IpAddress> pop3Servers = new ArrayList<>();

    private DhcpOption70(){
        super();
        code = 70;
        variableLength = true;
        payloadLength = -1;
        name = "Post Office Protocol (POP3) Server Option";
        minPayloadLength = 4;
    }


    public void addPop3Server(IpAddress address){
        pop3Servers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption70 option = new DhcpOption70();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.pop3Servers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(70) bad Post Office Protocol (POP3) Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:pop3Servers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption70 option = new DhcpOption70();
        for(String s:str){
            option.addPop3Server(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 70 - Post Office Protocol (POP3) Server Option\n\n"
            + "set option 70 {<ip_address address>}\n"
            + "set option 70 10.10.20.1 172.16.23.54\n";
    }
}
