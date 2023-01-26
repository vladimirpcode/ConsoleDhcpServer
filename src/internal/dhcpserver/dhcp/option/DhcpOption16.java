package internal.dhcpserver.dhcp.option;


/*
   Swap Server

   This specifies the IP address of the client's swap server.

   The code for this option is 16 and its length is 4.

    Code   Len    Swap Server Address
   +-----+-----+-----+-----+-----+-----+
   |  16 |  4  |  a1 |  a2 |  a3 |  a4 |
   +-----+-----+-----+-----+-----+-----+
 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;

public class DhcpOption16 extends DhcpOption{
    IpAddress swapServerAddress;

    private DhcpOption16(){
        super();
        code = 16;
        variableLength = false;
        payloadLength = 4;
        name = "Swap Server";
    }

    public void  setSwapServerAddress(IpAddress swapServerAddress){
        this.swapServerAddress = swapServerAddress;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption16 option = new DhcpOption16();
        option.minPayloadLength = option.payloadLength;
        try {
            option.swapServerAddress = new IpAddress(payload);
        }catch (UnknownHostException exc){
            Warning.msg("Option (16) bad swap Server address");
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = swapServerAddress.toByteArray();
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption16 option = new DhcpOption16();
        option.setSwapServerAddress(new IpAddress(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 16 - Swap Server\n\n"
            + "set option 16 <IpAddress_swapServer>\n"
            + "set option 16 10.52.36.110\n";
    }
}
