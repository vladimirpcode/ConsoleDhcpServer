package internal.dhcpserver.dhcp.option;


/*
   DHCP Message Type

   This option is used to convey the type of the DHCP message.  The code
   for this option is 53, and its length is 1.  Legal values for this
   option are:

           Value   Message Type
           -----   ------------
             1     DHCPDISCOVER
             2     DHCPOFFER
             3     DHCPREQUEST
             4     DHCPDECLINE
             5     DHCPACK
             6     DHCPNAK
             7     DHCPRELEASE
             8     DHCPINFORM

    Code   Len  Type
   +-----+-----+-----+
   |  53 |  1  | 1-9 |
   +-----+-----+-----+
*/

import internal.dhcpserver.Utils;
import internal.dhcpserver.dhcp.DhcpMessageType;

public class DhcpOption53 extends DhcpOption{
    DhcpMessageType dhcpMessageType;

    public DhcpOption53(){
        super();
        code = 53;
        variableLength = false;
        payloadLength = 1;
        name = "DHCP Message Type";
    }

    public void setDhcpMessageType(DhcpMessageType dhcpMessageType){
        this.dhcpMessageType = dhcpMessageType;
    }

    public DhcpMessageType getDhcpMessageType(){
        return dhcpMessageType;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption53 option = new DhcpOption53();
        option.dhcpMessageType = DhcpMessageType.valueOf(payload[0]);

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = new byte[1];
        bytes[0] = (byte)(dhcpMessageType.getCode());
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }


}
