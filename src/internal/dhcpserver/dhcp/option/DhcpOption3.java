package internal.dhcpserver.dhcp.option;


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;


/*
Router Option

   The router option specifies a list of IP addresses for routers on the
   client's subnet.  Routers SHOULD be listed in order of preference.

   The code for the router option is 3.  The minimum length for the
   router option is 4 octets, and the length MUST always be a multiple
   of 4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  3  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
 */

public class DhcpOption3 extends DhcpOption{
    ArrayList<IpAddress> routersAdresses;

    public DhcpOption3(){
        super();
        code = 3;
        variableLength = true;
        payloadLength = 0;
        name = "Router Option";
        routersAdresses = new ArrayList<>();
        minPayloadLength = 4;
    }



    public void addRouterAddress(IpAddress address){
        payloadLength += 4;
        routersAdresses.add(address);
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption3 option = new DhcpOption3();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.addRouterAddress(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(3) bad Router address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:routersAdresses){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption3 option = new DhcpOption3();
        for(String ip:str){
            option.routersAdresses.add(new IpAddress(ip));
        }
        return option;
    }
    public static String getCmdDescription(){return "Option 3 - Router Option\n\nset option 3 {<IpAddress_router>}\n"
            +"set option 3 192.168.1.1 192.168.1.2 192.168.1.3\n";}
}
