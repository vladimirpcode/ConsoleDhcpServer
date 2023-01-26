package internal.dhcpserver.dhcp.option;


/*
    Network News Transport Protocol (NNTP) Server Option

   The NNTP server option specifies a list of NNTP available to the
   client.  Servers SHOULD be listed in order of preference.

   The code for the NNTP server option is 71. The minimum length for
   this option is 4 octets, and the length MUST always be a multiple of
   4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   | 71  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
*/

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;


import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption71 extends DhcpOption{
    ArrayList<IpAddress> nntpServers = new ArrayList<>();

    private DhcpOption71(){
        super();
        code = 71;
        variableLength = true;
        payloadLength = -1;
        name = "Network News Transport Protocol (NNTP) Server Option";
        minPayloadLength = 4;
    }


    public void addNntpServer(IpAddress address){
        nntpServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption71 option = new DhcpOption71();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.nntpServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(71) bad Network News Transport Protocol (NNTP) Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:nntpServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption71 option = new DhcpOption71();
        for(String s:str){
            option.addNntpServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 71 - Network News Transport Protocol (NNTP) Server Option\n\n"
            + "set option 71 {<ip_address address>}\n"
            + "set option 71 10.10.20.1 172.16.23.54\n";
    }
}
