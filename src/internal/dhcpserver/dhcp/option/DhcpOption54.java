package internal.dhcpserver.dhcp.option;


/*
   Server Identifier

   This option is used in DHCPOFFER and DHCPREQUEST messages, and may
   optionally be included in the DHCPACK and DHCPNAK messages.  DHCP
   servers include this option in the DHCPOFFER in order to allow the
   client to distinguish between lease offers.  DHCP clients use the
   contents of the 'server identifier' field as the destination address
   for any DHCP messages unicast to the DHCP server.  DHCP clients also
   indicate which of several lease offers is being accepted by including
   this option in a DHCPREQUEST message.

   The identifier is the IP address of the selected server.

   The code for this option is 54, and its length is 4.

    Code   Len            Address
   +-----+-----+-----+-----+-----+-----+
   |  54 |  4  |  a1 |  a2 |  a3 |  a4 |
   +-----+-----+-----+-----+-----+-----+
*/

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.dhcp.DhcpMessageType;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;


public class DhcpOption54 extends DhcpOption{
    IpAddress dhcpServerAddress;

    public DhcpOption54(){
        super();
        code = 54;
        variableLength = false;
        payloadLength = 4;
        name = "Server Identifier";
    }

    public void setDhcpServerAddress(IpAddress address){
        dhcpServerAddress = address;
    }

    public static DhcpOption valueOf(int[] payload) {
        DhcpOption54 option = new DhcpOption54();

        try {
            option.dhcpServerAddress = new IpAddress(Utils.getIntBytes(payload, 0, 4));
        } catch (UnknownHostException exc) {
            Warning.msg("Option(54) bad DHCP Server address");
        }

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = dhcpServerAddress.toByteArray();
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }
}
