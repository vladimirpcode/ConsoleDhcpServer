package internal.dhcpserver.dhcp.option;


/*
   Domain Name

   This option specifies the domain name that client should use when
   resolving hostnames via the Domain Name System.

   The code for this option is 15.  Its minimum length is 1.

    Code   Len        Domain Name
   +-----+-----+-----+-----+-----+-----+--
   |  15 |  n  |  d1 |  d2 |  d3 |  d4 |  ...
   +-----+-----+-----+-----+-----+-----+--
 */

import internal.dhcpserver.Utils;

public class DhcpOption15 extends DhcpOption{
    String domainName;

    private DhcpOption15(){
        super();
        code = 15;
        variableLength = true;
        payloadLength = -1;
        name = "Domain Name";
        minPayloadLength = 1;
    }

    public void setDomainName(String domainName){
        this.domainName = domainName;
        payloadLength += domainName.length();
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption15 option = new DhcpOption15();
        option.payloadLength = payload.length;
        option.minPayloadLength = 1;
        option.domainName = String.valueOf(Utils.IntArrToSignedByteArr(payload));
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = domainName.getBytes();
        System.arraycopy(bytes, 0, result, 2, bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption15 option = new DhcpOption15();
        option.setDomainName(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 15 - Domain Name\n\n"
            + "set option 15 <string_domainName>\n"
            + "set option 15 myCompany.internal\n";
    }
}
