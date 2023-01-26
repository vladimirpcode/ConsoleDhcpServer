package internal.dhcpserver.dhcp.option;

/*
   The subnet mask option specifies the client's subnet mask as per RFC
   950 [5].

   If both the subnet mask and the router option are specified in a DHCP
   reply, the subnet mask option MUST be first.

   The code for the subnet mask option is 1, and its length is 4 octets.

    Code   Len        Subnet Mask
   +-----+-----+-----+-----+-----+-----+
   |  1  |  4  |  m1 |  m2 |  m3 |  m4 |
   +-----+-----+-----+-----+-----+-----+
 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.net.SubnetMask;

public class DhcpOption1 extends DhcpOption{
    SubnetMask subnetMask;

    public DhcpOption1(){
        super();
        code = 1;
        variableLength = false;
        payloadLength = 4;
        name = "Subnet Mask";
    }

    public void setSubnetMask(SubnetMask mask){
        this.subnetMask = mask;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption1 option = new DhcpOption1();

        option.subnetMask = new SubnetMask(payload);

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] mask = this.subnetMask.toByteArray();
        System.arraycopy(mask,0,result,2,mask.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption1 option = new DhcpOption1();
        option.setSubnetMask(new SubnetMask(str[0]));
        return option;
    }
    public static String getCmdDescription(){return "Option 1 - Subnet Mask\n\nset option 1 <subnet mask>\n" +
            "set option 1 255.255.255.0\n";}
}