package internal.dhcpserver.dhcp.option;


/*
    Simple Mail Transport Protocol (SMTP) Server Option

   The SMTP server option specifies a list of SMTP servers available to
   the client.  Servers SHOULD be listed in order of preference.

   The code for the SMTP server option is 69.  The minimum length for
   this option is 4 octets, and the length MUST always be a multiple of
   4.

    Code   Len         Address 1               Address 2
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   | 69  |  n  |  a1 |  a2 |  a3 |  a4 |  a1 |  a2 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
*/


import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption69 extends DhcpOption{
    ArrayList<IpAddress> smtpServers = new ArrayList<>();

    private DhcpOption69(){
        super();
        code = 69;
        variableLength = true;
        payloadLength = -1;
        name = "Simple Mail Transport Protocol (SMTP) Server Option";
        minPayloadLength = 4;
    }

    public void addSmtpServer(IpAddress address){
        smtpServers.add(address);
        payloadLength += 4;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption69 option = new DhcpOption69();
        option.payloadLength = payload.length;
        for(int i = 0; i < payload.length; i += 4){
            try{
                option.smtpServers.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
            }catch (UnknownHostException exc){
                Warning.msg("Option(69) bad Simple Mail Transport Protocol (SMTP) Server address");
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(IpAddress a:smtpServers){
            byte[] bytes = a.toByteArray();
            System.arraycopy(bytes, 0, result, 2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption69 option = new DhcpOption69();
        for(String s:str){
            option.addSmtpServer(new IpAddress(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 69 - Simple Mail Transport Protocol (SMTP) Server Option\n\n"
            + "set option 69 {<ip_address address>}\n"
            + "set option 69 10.10.20.1 172.16.23.54\n";
    }
}
