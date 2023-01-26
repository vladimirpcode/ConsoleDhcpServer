package internal.dhcpserver.dhcp.option;


/*
   NetBIOS over TCP/IP Datagram Distribution Server Option

   The NetBIOS datagram distribution server (NBDD) option specifies a
   list of RFC 1001/1002 NBDD servers listed in order of preference. The
   code for this option is 45.  The minimum length of the option is 4
   octets, and the length must always be a multiple of 4.

    Code   Len           Address 1              Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+----
   |  45 |  n  |  a1 |  a2 |  a3 |  a4 |  b1 |  b2 |  b3 |  b4 | ...
   +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+----

 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption45 extends DhcpOption{
    ArrayList<IpAddress> netBiosDatagramDistibutionServers;

    private DhcpOption45(){
        super();
        code = 45;
        variableLength = true;
        payloadLength = -1;
        name = "NetBIOS over TCP/IP Datagram Distribution Server Option";
        minPayloadLength = 4;
        netBiosDatagramDistibutionServers = new ArrayList<>();
    }

    public void addNetBiosDatagramDistibutionServer(IpAddress address){
        netBiosDatagramDistibutionServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption45 option = new DhcpOption45();
        option.payloadLength = payload.length;
        option.minPayloadLength = 4;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.netBiosDatagramDistibutionServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(45) bad NetBIOS over TCP/IP Datagram Distribution Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:netBiosDatagramDistibutionServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption45 option = new DhcpOption45();
        for(String s:str){

            option.addNetBiosDatagramDistibutionServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 45 - NetBIOS over TCP/IP Datagram Distribution Server Option\n\n"
            + "set option 45 {<IpAddress NetBiosDgDb>}\n"
            + "set option 45 192.168.10.2 192.168.1.1 10.10.50.1 192.168.121.1\n";
    }
}
