package internal.dhcpserver.dhcp.option;


/*
   Network Information Service Domain Option

   This option specifies the name of the client's NIS [17] domain.  The
   domain is formatted as a character string consisting of characters
   from the NVT ASCII character set.

   The code for this option is 40.  Its minimum length is 1.

    Code   Len      NIS Domain Name
   +-----+-----+-----+-----+-----+-----+---
   |  40 |  n  |  n1 |  n2 |  n3 |  n4 | ...
   +-----+-----+-----+-----+-----+-----+---

 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

public class DhcpOption40 extends DhcpOption{
    String nisDomain;

    private DhcpOption40(){
        super();
        code = 40;
        variableLength = true;
        payloadLength = -1;
        name = "Network Information Service Domain Option";
        minPayloadLength = 1;
    }

    public void setNisDomain(String nisDomain){
        this.payloadLength = nisDomain.length();
        this.nisDomain = nisDomain;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption40 option = new DhcpOption40();
        option.payloadLength = payload.length;
        option.nisDomain = String.valueOf(Utils.IntArrToSignedByteArr(payload));
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = nisDomain.getBytes();
        System.arraycopy(bytes, 0, result, 2, bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption40 option = new DhcpOption40();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < str.length; i++){
            if(i != str.length -1){
                builder.append(str[i]);
                builder.append(" ");
            }else{
                builder.append(str[i]);
            }
        }
        option.setNisDomain(builder.toString());
        return option;
    }

    public static String getCmdDescription(){return "Option 40 - Network Information Service Domain Option\n\n"
            + "set option 40 <string nisDomainName>\n"
            + "set option 40 oberon\n";
    }
}
