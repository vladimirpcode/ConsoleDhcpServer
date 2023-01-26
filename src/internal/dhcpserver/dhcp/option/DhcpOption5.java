package internal.dhcpserver.dhcp.option;


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;


/*
    Name Server Option

   The name server option specifies a list of IEN 116 [7] name servers
   available to the client.  Servers SHOULD be listed in order of
   preference.

   The code for the name server option is 5.  The minimum length for
   this option is 4 octets, and the length MUST always be a multiple of
   4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  5  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
 */

public class DhcpOption5 extends DhcpOption{
    ArrayList<IpAddress> nameServersAddresses;

    private DhcpOption5(){
        super();
        code = 5;
        variableLength = true;
        payloadLength = -1;
        name = "Name Server Option";
        nameServersAddresses = new ArrayList<>();
        minPayloadLength = 4;
    }



    public void addNameServerAddress(IpAddress address){
        nameServersAddresses.add(address);
        payloadLength+=4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption5 option = new DhcpOption5();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.nameServersAddresses.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(5) bad Name Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:nameServersAddresses){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption5 option = new DhcpOption5();
        for(String ip:str){
            option.addNameServerAddress(new IpAddress(ip));
        }
        return option;
    }
    public static String getCmdDescription(){return "Option 5 - Name Server Option\n\nset option 5 {<IpAddress_nameServer>}\n"
            +"set option 5 192.168.1.1 192.168.1.2 192.168.1.3\n";}
}
