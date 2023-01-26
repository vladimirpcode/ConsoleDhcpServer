package internal.dhcpserver.dhcp.option;


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;


/*
   Impress Server Option

   The Impress server option specifies a list of Imagen Impress servers
   available to the client.  Servers SHOULD be listed in order of
   preference.

   The code for the Impress server option is 10.  The minimum length for
   this option is 4 octets, and the length MUST always be a multiple of
   4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  10 |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
 */

public class DhcpOption10 extends DhcpOption{
    ArrayList<IpAddress> impressServersAddresses;
    private DhcpOption10(){
        super();
        code = 10;
        variableLength = true;
        payloadLength = -1;
        name = "Impress Server Option";
        impressServersAddresses = new ArrayList<>();
        minPayloadLength = 4;
    }


    public void addImpressServerAddress(IpAddress address){
        impressServersAddresses.add(address);
        payloadLength+=4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption10 option = new DhcpOption10();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.addImpressServerAddress(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(10) bad Impress Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:impressServersAddresses){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption10 option = new DhcpOption10();
        for(String ip:str){
            option.impressServersAddresses.add(new IpAddress(ip));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 10 - Impress Server Option\n\n"
            + "set option 10 {<IpAddress_ImpressServer>}\n"
            + "set option 10 172.16.1.1 172.16.10.2 192.168.10.10\n";
    }
}
