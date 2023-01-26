package internal.dhcpserver.dhcp.option;


/*
This option specifies a list of IP addresses of systems that are
   running the X Window System Display Manager and are available to the
   client.

   Addresses SHOULD be listed in order of preference.

   The code for the this option is 49. The minimum length of this option
   is 4, and the length MUST be a multiple of 4.

    Code   Len         Address 1               Address 2

   +-----+-----+-----+-----+-----+-----+-----+-----+---
   |  49 |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |   ...
   +-----+-----+-----+-----+-----+-----+-----+-----+---
 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption49 extends DhcpOption{
    ArrayList<IpAddress> xWindowsDisplayManagers;

    private DhcpOption49(){
        super();
        code = 49;
        variableLength = true;
        payloadLength = -1;
        name = "X Window System Display Manager Option";
        minPayloadLength = 4;
        xWindowsDisplayManagers = new ArrayList<>();
    }

    public void addXWindowsDisplayManager(IpAddress address){
        xWindowsDisplayManagers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption49 option = new DhcpOption49();
        option.payloadLength = payload.length;
        option.minPayloadLength = 4;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.xWindowsDisplayManagers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(49) bad X Window System Display Manager address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:xWindowsDisplayManagers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption49 option = new DhcpOption49();
        for(String s:str){
            option.addXWindowsDisplayManager(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 49 - X Window System Display Manager Option\n\n"
            + "set option 49 {<IpAddress xwinSysDisplayManager>}\n"
            + "set option 49 192.168.10.2 192.168.1.1 10.10.50.1 192.168.2.1\n";
    }
}
