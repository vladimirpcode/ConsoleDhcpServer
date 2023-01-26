package internal.dhcpserver.dhcp.option;


import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;

import java.util.ArrayList;


/*
   Host Name Option

   This option specifies the name of the client.  The name may or may
   not be qualified with the local domain name (see section 3.17 for the
   preferred way to retrieve the domain name).  See RFC 1035 for
   character set restrictions.

   The code for this option is 12, and its minimum length is 1.

    Code   Len                 Host Name
   +-----+-----+-----+-----+-----+-----+-----+-----+--
   |  12 |  n  |  h1 |  h2 |  h3 |  h4 |  h5 |  h6 |  ...
   +-----+-----+-----+-----+-----+-----+-----+-----+--
 */

public class DhcpOption12 extends DhcpOption{
    String hostName;

    private DhcpOption12(){
        super();
        code = 12;
        variableLength = true;
        payloadLength = -1;
        name = "Host Name Option";
        minPayloadLength = 1;
    }


    public void setHostName(String hostName){
        this.hostName = hostName;
        payloadLength += hostName.length();
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption12 option = new DhcpOption12();
        option.setHostName(String.valueOf(Utils.IntArrToSignedByteArr(payload)));
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = hostName.getBytes();
        System.arraycopy(bytes, 0, result, 2, bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption12 option = new DhcpOption12();
        StringBuilder builder = new StringBuilder();
        for(String s:str){
            builder.append(s + " ");
        }
        option.setHostName(builder.toString().trim());
        return option;
    }

    public static String getCmdDescription(){return "Option 12 - Host Name Option\n\n"
            + "set option 12 <string_hostName>\n"
            + "set option 12 Accounting_PC5\n";
    }
}
