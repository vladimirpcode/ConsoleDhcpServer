package internal.dhcpserver.dhcp.option;


/*
   X Window System Font Server Option

   This option specifies a list of X Window System [21] Font servers
   available to the client. Servers SHOULD be listed in order of
   preference.

   The code for this option is 48.  The minimum length of this option is
   4 octets, and the length MUST be a multiple of 4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+---
   |  48 |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |   ...
   +-----+-----+-----+-----+-----+-----+-----+-----+---
 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption48 extends DhcpOption{
    ArrayList<IpAddress> xWindowsSystemFontServers;

    private DhcpOption48(){
        super();
        code = 48;
        variableLength = true;
        payloadLength = -1;
        name = "X Window System Font Server Option";
        minPayloadLength = 4;
        xWindowsSystemFontServers = new ArrayList<>();
    }


    public void addXWindowSystemFontServer(IpAddress address){
        xWindowsSystemFontServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption48 option = new DhcpOption48();
        option.payloadLength = payload.length;
        option.minPayloadLength = 4;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.xWindowsSystemFontServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(48) bad X Window System Font Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:xWindowsSystemFontServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption48 option = new DhcpOption48();
        for(String s:str){
            option.addXWindowSystemFontServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 48 - X Window System Font Server Option\n"
            + "set option 48 {<IpAddress xwinSysFontServer>}\n"
            + "set option 48 192.168.10.2 192.168.1.1 10.10.50.1 192.168.1.11\n";
    }
}
