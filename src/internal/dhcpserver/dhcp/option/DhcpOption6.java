package internal.dhcpserver.dhcp.option;


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;


/*
    Domain Name Server Option

   The domain name server option specifies a list of Domain Name System
   (STD 13, RFC 1035 [8]) name servers available to the client.  Servers
   SHOULD be listed in order of preference.

   The code for the domain name server option is 6.  The minimum length
   for this option is 4 octets, and the length MUST always be a multiple
   of 4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  6  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
 */

public class DhcpOption6 extends DhcpOption{
    ArrayList<IpAddress> dnsServersAddresses;

    public DhcpOption6(){
        super();
        code = 6;
        variableLength = true;
        payloadLength = 0;
        name = "Domain Name Server Option";
        dnsServersAddresses = new ArrayList<>();
        minPayloadLength = 4;
    }

    public void addDnsServerAddress(IpAddress address){
        payloadLength += 4;
        dnsServersAddresses.add(address);
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption6 option = new DhcpOption6();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.dnsServersAddresses.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(6) bad Domain Name Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:dnsServersAddresses){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption6 option = new DhcpOption6();
        for(String ip:str){
            option.addDnsServerAddress(new IpAddress(ip));
        }
        return option;
    }
    public static String getCmdDescription(){return "Option 6 - Domain Name Server Option\n\n"
            + "set option 6 {<IpAddress_DNS>}\n"
            + "set option 6 77.88.8.8 8.8.8.8 192.168.1.30\n";}
}
