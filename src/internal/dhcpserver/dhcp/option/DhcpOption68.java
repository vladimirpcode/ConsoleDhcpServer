package internal.dhcpserver.dhcp.option;


/*
   Mobile IP Home Agent option

   This option specifies a list of IP addresses indicating mobile IP
   home agents available to the client.  Agents SHOULD be listed in
   order of preference.

   The code for this option is 68.  Its minimum length is 0 (indicating
   no home agents are available) and the length MUST be a multiple of 4.
   It is expected that the usual length will be four octets, containing
   a single home agent's address.

    Code Len    Home Agent Addresses (zero or more)
   +-----+-----+-----+-----+-----+-----+--
   | 68  |  n  | a1  | a2  | a3  | a4  | ...
   +-----+-----+-----+-----+-----+-----+--

*/


import java.net.UnknownHostException;
import java.util.ArrayList;

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

public class DhcpOption68 extends DhcpOption{
    ArrayList<IpAddress> mobileIpAgents = new ArrayList<>();

    private DhcpOption68(){
        super();
        code = 68;
        variableLength = true;
        payloadLength = -1;
        name = "Mobile IP Home Agent option";
        minPayloadLength = 0;
    }


    public void addMobileIpAgent(IpAddress address){
        mobileIpAgents.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption68 option = new DhcpOption68();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.mobileIpAgents.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(68) bad Mobile IP Home Agent address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:mobileIpAgents){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption68 option = new DhcpOption68();
        for(String s:str){
            option.addMobileIpAgent(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 68 - Mobile IP Home Agent option\n\n"
            + "set option 68 {<ip_address address>}\n"
            + "set option 68 10.10.20.1 172.16.23.54\n";
    }
}
