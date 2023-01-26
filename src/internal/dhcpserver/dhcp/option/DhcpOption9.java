package internal.dhcpserver.dhcp.option;


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;


/*
   LPR Server Option

   The LPR server option specifies a list of RFC 1179 [10] line printer
   servers available to the client.  Servers SHOULD be listed in order
   of preference.

   The code for the LPR server option is 9.  The minimum length for this
   option is 4 octets, and the length MUST always be a multiple of 4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  9  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
 */

public class DhcpOption9 extends DhcpOption{
    ArrayList<IpAddress> lrpServersAddresses;

    private DhcpOption9(){
        super();
        code = 9;
        variableLength = true;
        payloadLength = -1;
        name = "LPR Server Option";
        lrpServersAddresses = new ArrayList<>();
        minPayloadLength = 4;
    }


    public void addLrpServerAddress(IpAddress address){
        lrpServersAddresses.add(address);
        payloadLength+=4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption9 option = new DhcpOption9();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.lrpServersAddresses.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(9) bad LPR Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:lrpServersAddresses){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption9 option = new DhcpOption9();
        for(String ip:str){
            option.addLrpServerAddress(new IpAddress(ip));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 9 - LPR Server Option\n\n"
            + "set option 9 {<IpAddress_LrpServer>}\n"
            + "set option 9 172.16.1.1 172.16.10.2 192.168.10.10\n";
    }
}
