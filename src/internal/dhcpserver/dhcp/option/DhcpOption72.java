package internal.dhcpserver.dhcp.option;


/*
    Default World Wide Web (WWW) Server Option

   The WWW server option specifies a list of WWW available to the
   client.  Servers SHOULD be listed in order of preference.

   The code for the WWW server option is 72.  The minimum length for
   this option is 4 octets, and the length MUST always be a multiple of
   4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   | 72  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
*/

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;


import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption72 extends DhcpOption{
    ArrayList<IpAddress> wwwServers = new ArrayList<>();

    private DhcpOption72(){
        super();
        code = 72;
        variableLength = true;
        payloadLength = -1;
        name = "Default World Wide Web (WWW) Server Option";
        minPayloadLength = 4;
    }

    public void addWwwServer(IpAddress address){
        wwwServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption72 option = new DhcpOption72();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.wwwServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(72) bad Default World Wide Web (WWW) Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:wwwServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption72 option = new DhcpOption72();
        for(String s:str){
            option.addWwwServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 72 - Default World Wide Web (WWW) Server Option\n\n"
            + "set option 72 {<ip_address address>}\n"
            + "set option 72 10.10.20.1 172.16.23.54\n";
    }
}
