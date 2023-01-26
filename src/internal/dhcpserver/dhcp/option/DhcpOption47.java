package internal.dhcpserver.dhcp.option;


/*
   NetBIOS over TCP/IP Scope Option

   The NetBIOS scope option specifies the NetBIOS over TCP/IP scope
   parameter for the client as specified in RFC 1001/1002. See [19],
   [20], and [8] for character-set restrictions.

   The code for this option is 47.  The minimum length of this option is
   1.

    Code   Len       NetBIOS Scope
   +-----+-----+-----+-----+-----+-----+----
   |  47 |  n  |  s1 |  s2 |  s3 |  s4 | ...
   +-----+-----+-----+-----+-----+-----+----
 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

public class DhcpOption47 extends DhcpOption{
    String netBiosScope;

    private DhcpOption47(){
        super();
        code = 47;
        variableLength = true;
        payloadLength = -1;
        name = "NetBIOS over TCP/IP Scope Option";
        minPayloadLength = 1;
    }

    public void setNetBiosScope(String name){
        netBiosScope = name;
        payloadLength = name.length();
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption47 option = new DhcpOption47();
        option.payloadLength = payload.length;
        option.minPayloadLength = 1;
        option.netBiosScope = String.valueOf(Utils.IntArrToSignedByteArr(payload));
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = netBiosScope.getBytes();
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption47 option = new DhcpOption47();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < str.length; i++){
            if(i != str.length - 1){
                builder.append(str[i]);
                builder.append(" ");
            }else{
                builder.append(str[i]);
            }
        }
        option.setNetBiosScope(builder.toString());
        return option;
    }

    public static String getCmdDescription(){return "Option 47 - NetBIOS over TCP/IP Scope Option\n\n"
            + "set option 47 <String NetBiosScope>\n"
            + "set option 47 scope1\n";
    }
}
