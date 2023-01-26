package internal.dhcpserver.dhcp.option;


/*
    Default Finger Server Option

   The Finger server option specifies a list of Finger available to the
   client.  Servers SHOULD be listed in order of preference.

   The code for the Finger server option is 73.  The minimum length for
   this option is 4 octets, and the length MUST always be a multiple of
   4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   | 73  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--

*/

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;


import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption73 extends DhcpOption{
    ArrayList<IpAddress> fingerServers = new ArrayList<>();

    private DhcpOption73(){
        super();
        code = 73;
        variableLength = true;
        payloadLength = -1;
        name = "Default Finger Server Option";
        minPayloadLength = 4;

    }

    public void addFingerServer(IpAddress address){
        fingerServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption73 option = new DhcpOption73();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.fingerServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(73) bad Default Finger Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:fingerServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption73 option = new DhcpOption73();
        for(String s:str){
            option.addFingerServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 73 - Default Finger Server Option\n\n"
            + "set option 74 {<ip_address address>}\n"
            + "set option 74 10.10.20.1 172.16.23.54\n";
    }
}
