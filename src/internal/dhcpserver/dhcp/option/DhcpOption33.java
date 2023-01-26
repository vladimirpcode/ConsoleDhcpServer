package internal.dhcpserver.dhcp.option;


/*
   Static Route Option

   This option specifies a list of static routes that the client should
   install in its routing cache.  If multiple routes to the same
   destination are specified, they are listed in descending order of
   priority.

   The routes consist of a list of IP address pairs.  The first address
   is the destination address, and the second address is the router for
   the destination.

   The default route (0.0.0.0) is an illegal destination for a static
   route.  See section 3.5 for information about the router option.

   The code for this option is 33.  The minimum length of this option is
   8, and the length MUST be a multiple of 8.

    Code   Len         Destination 1           Router 1
   +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
   |  33 |  n  |  d1 |  d2 |  d3 |  d4 |  r1 |  r2 |  r3 |  r4 |
   +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
           Destination 2           Router 2
   +-----+-----+-----+-----+-----+-----+-----+-----+---
   |  d1 |  d2 |  d3 |  d4 |  r1 |  r2 |  r3 |  r4 | ...
   +-----+-----+-----+-----+-----+-----+-----+-----+---
 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;


import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption33 extends DhcpOption{
    int minDataLength;
    ArrayList<Route> routes;

    private DhcpOption33(){
        super();
        code = 33;
        variableLength = true;
        payloadLength = -1;
        name = "Static Route Option";
        minDataLength = 8;
        routes = new ArrayList<>();
    }


    public void addRoutes(Route route){
        routes.add(route);
        this.payloadLength += 8;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption33 option = new DhcpOption33();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 8){
            try {
                option.addRoutes(new Route(new IpAddress(Utils.getIntBytes(payload,i,4)),
                                            new IpAddress(Utils.getIntBytes(payload,i+4,4))));
            }catch (UnknownHostException exc){
                Warning.msg("Option (33) bad route");
            }
        }

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(int i = 0; i < routes.size(); i++){
            byte[] bytes = new byte[8];
            System.arraycopy(routes.get(i).getDestanation().toByteArray(),0,bytes,0,4);
            System.arraycopy(routes.get(i).getNextHop().toByteArray(),0,bytes,4,4);
            System.arraycopy(bytes, 0, result,2+i*8,bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption33 option = new DhcpOption33();
        for(int i = 0; i < str.length; i++){
            option.routes.add(new Route(new IpAddress(str[i]), new IpAddress(str[i+1])));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 33 - Static Route Option\n\n"
            + "set option 33 {<IpAddress destanation> <IpAddress router>}\n"
            + "set option 33 192.168.10.2 192.168.1.1 10.10.50.1 192.168.1.1\n";
    }

}
