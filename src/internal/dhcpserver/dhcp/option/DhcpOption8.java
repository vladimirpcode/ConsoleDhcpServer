package internal.dhcpserver.dhcp.option;


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;


/*
   Cookie Server Option

   The cookie server option specifies a list of RFC 865 [9] cookie
   servers available to the client.  Servers SHOULD be listed in order
   of preference.

   The code for the log server option is 8.  The minimum length for this
   option is 4 octets, and the length MUST always be a multiple of 4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  8  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
 */

public class DhcpOption8 extends DhcpOption{
    ArrayList<IpAddress> cookieServersAddresses;

    private DhcpOption8(){
        super();
        code = 8;
        variableLength = true;
        payloadLength = -1;
        name = "Cookie Server Option";
        cookieServersAddresses = new ArrayList<>();
        minPayloadLength = 4;
    }

    public void addCookieServerAddress(IpAddress address){
        cookieServersAddresses.add(address);
        payloadLength+=4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption8 option = new DhcpOption8();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.addCookieServerAddress(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(8) bad Cookie Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:cookieServersAddresses){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption8 option = new DhcpOption8();
        for(String ip:str){
            option.cookieServersAddresses.add(new IpAddress(ip));
        }
        return option;
    }
    public static String getCmdDescription(){return "Option 8 - Cookie Server Option\n\n"
            + "set option 8 {<IpAddress_CookieServer>}\n"
            + "set option 8 172.16.1.1 172.16.10.2 192.168.10.10\n";}
}
