package internal.dhcpserver.dhcp.option;


/*
   Router Solicitation Address Option

   This option specifies the address to which the client should transmit
   router solicitation requests.

   The code for this option is 32, and its length is 4.

    Code   Len            Address
   +-----+-----+-----+-----+-----+-----+
   |  32 |  4  |  a1 |  a2 |  a3 |  a4 |
   +-----+-----+-----+-----+-----+-----+
 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;

public class DhcpOption32 extends DhcpOption{
    IpAddress routerSolicitationAddress;
    private DhcpOption32(){
        super();
        code = 32;
        variableLength = false;
        payloadLength = 4;
        name = "Router Solicitation Address Option";
    }

    public void setRouterSolicitationAddress(IpAddress routerSolicitationAddress){
        this.routerSolicitationAddress = routerSolicitationAddress;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption32 option = new DhcpOption32();
        option.minPayloadLength = payload.length;
        try {
            option.routerSolicitationAddress = new IpAddress(payload);
        }catch (UnknownHostException exc){
            Warning.msg("Option (32): bad Router Solicitation address");
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = routerSolicitationAddress.toByteArray();
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption32 option = new DhcpOption32();
        option.routerSolicitationAddress = new IpAddress(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 32 - Router Solicitation Address Option\n\n"
            + "set option 32 <IpAddress router>\n"
            + "set option 32 192.168.1.1\n";
    }
}
