package internal.dhcpserver.dhcp.option;


/*
   Maximum DHCP Message Size

   This option specifies the maximum length DHCP message that it is
   willing to accept.  The length is specified as an unsigned 16-bit
   integer.  A client may use the maximum DHCP message size option in
   DHCPDISCOVER or DHCPREQUEST messages, but should not use the option
   in DHCPDECLINE messages.

   The code for this option is 57, and its length is 2.  The minimum
   legal value is 576 octets.

    Code   Len     Length
   +-----+-----+-----+-----+
   |  57 |  2  |  l1 |  l2 |
   +-----+-----+-----+-----+
*/

import internal.dhcpserver.Utils;

public class DhcpOption57 extends DhcpOption{
    int maxDhcpMsgSize;

    private DhcpOption57(){
        super();
        code = 57;
        variableLength = false;
        payloadLength = 2;
        name = "Maximum DHCP Message Size";
    }

    public void setMaxDhcpMsgSize(int maxDhcpMsgSize){
        this.maxDhcpMsgSize = maxDhcpMsgSize;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption57 option = new DhcpOption57();
        option.maxDhcpMsgSize = Utils.getUnsignedNumFrom2int(payload[0], payload[1]);
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.intTo2uBytes(maxDhcpMsgSize);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption57 option = new DhcpOption57();
        option.setMaxDhcpMsgSize(Integer.parseInt(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 57 - Maximum DHCP Message Size\n\n"
            + "set option 57 <16bit_u_integer max_dhcp_size>\n"
            + "set option 57 700\n";
    }
}
