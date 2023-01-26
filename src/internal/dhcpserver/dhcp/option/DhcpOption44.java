package internal.dhcpserver.dhcp.option;


/*
   NetBIOS over TCP/IP Name Server Option

   The NetBIOS name server (NBNS) option specifies a list of RFC
   1001/1002 [19] [20] NBNS name servers listed in order of preference.

   The code for this option is 44.  The minimum length of the option is
   4 octets, and the length must always be a multiple of 4.

    Code   Len           Address 1              Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+----
   |  44 |  n  |  a1 |  a2 |  a3 |  a4 |  b1 |  b2 |  b3 |  b4 | ...
   +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+----
 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption44 extends DhcpOption{
    ArrayList<IpAddress> netBiosNameServers;

    private DhcpOption44(){
        super();
        code = 44;
        variableLength = true;
        payloadLength = -1;
        name = "NetBIOS over TCP/IP Name Server Option";
        minPayloadLength = 4;
        netBiosNameServers = new ArrayList<>();
    }

    public void addNetBiosNameServer(IpAddress address){
        netBiosNameServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption44 option = new DhcpOption44();
        option.payloadLength = payload.length;
        option.minPayloadLength = 4;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.netBiosNameServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(44) bad NetBIOS over TCP/IP Name Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:netBiosNameServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption44 option = new DhcpOption44();
        for(String s:str){
            option.addNetBiosNameServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 44 - NetBIOS over TCP/IP Name Server Option\n\n"
            + "set option 44 {<IpAddress NetBiosServer>}\n"
            + "set option 44 192.168.10.2 192.168.1.1 10.10.50.1 192.168.100.1\n";
    }
}
