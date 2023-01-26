package internal.dhcpserver.dhcp.option;


/*
   Vendor class identifier

   This option is used by DHCP clients to optionally identify the vendor
   type and configuration of a DHCP client.  The information is a string
   of n octets, interpreted by servers.  Vendors may choose to define
   specific vendor class identifiers to convey particular configuration
   or other identification information about a client.  For example, the
   identifier may encode the client's hardware configuration.  Servers
   not equipped to interpret the class-specific information sent by a
   client MUST ignore it (although it may be reported). Servers that

   respond SHOULD only use option 43 to return the vendor-specific
   information to the client.

   The code for this option is 60, and its minimum length is 1.

   Code   Len   Vendor class Identifier
   +-----+-----+-----+-----+---
   |  60 |  n  |  i1 |  i2 | ...
   +-----+-----+-----+-----+---
*/

import internal.dhcpserver.Utils;

public class DhcpOption60 extends DhcpOption{
    int[] vendorId;

    private DhcpOption60(){
        super();
        code = 60;
        variableLength = true;
        payloadLength = -1;
        minPayloadLength = 1;
        name = "Vendor class identifier";
    }

    public void setVendorId(int[] vendorId){
        payloadLength = vendorId.length;
        this.vendorId = vendorId;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption60 option = new DhcpOption60();
        option.payloadLength = payload.length;
        option.vendorId = payload;

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.IntArrToSignedByteArr(vendorId);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption60 option = new DhcpOption60();
        int[] vendorId = new int[str.length];
        for(int i = 0; i < vendorId.length; i++){
            vendorId[i] = Integer.parseInt(str[i]);
        }
        option.setVendorId(vendorId);
        return option;
    }

    public static String getCmdDescription(){return "Option 60 - Vendor class identifier\n\n"
            + "set option 60 {<8bit_u_integer id>}\n"
            + "set option 60 23 54 61 27 34 155\n";
    }

}
