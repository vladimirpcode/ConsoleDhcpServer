package internal.dhcpserver.dhcp.option;


/*
   This option specifies the name of the client's NIS+ [17] domain.  The
   domain is formatted as a character string consisting of characters
   from the NVT ASCII character set.

   The code for this option is 64.  Its minimum length is 1.

    Code   Len      NIS Client Domain Name
   +-----+-----+-----+-----+-----+-----+---
   |  64 |  n  |  n1 |  n2 |  n3 |  n4 | ...
   +-----+-----+-----+-----+-----+-----+---
*/


import internal.dhcpserver.Utils;

import java.util.ArrayList;

public class DhcpOption64 extends DhcpOption{
    String nisDomainName;

    private DhcpOption64(){
        super();
        code = 64;
        variableLength = true;
        payloadLength = -1;
        name = "Network Information Service+ Domain Option";
        minPayloadLength = 1;
    }

    public void setNisDomainName(String nisDomainName){
        this.nisDomainName = nisDomainName;
        payloadLength = nisDomainName.length();
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption64 option = new DhcpOption64();
        option.payloadLength = payload.length;
        option.nisDomainName = String.valueOf(Utils.IntArrToSignedByteArr(payload));

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = nisDomainName.getBytes();
        System.arraycopy(bytes, 0, result, 2, bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption64 option = new DhcpOption64();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < str.length; i++){
            if(i != str.length-1){
                builder.append(str[i]);
                builder.append(" ");
            }else{
                builder.append(str[i]);
            }
        }
        option.setNisDomainName(builder.toString());
        return option;
    }

    public static String getCmdDescription(){return "Option 64 - Network Information Service+ Domain Option\n\n"
            + "set option 64 <string nis_domain_name>\n"
            + "set option 64 anyName\n";
    }
}
