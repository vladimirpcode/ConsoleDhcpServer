package internal.dhcpserver.dhcp.option;


/*
   Vendor Specific Information

   This option is used by clients and servers to exchange vendor-
   specific information.  The information is an opaque object of n
   octets, presumably interpreted by vendor-specific code on the clients
   and servers.  The definition of this information is vendor specific.
   The vendor is indicated in the vendor class identifier option.
   Servers not equipped to interpret the vendor-specific information
   sent by a client MUST ignore it (although it may be reported).
   Clients which do not receive desired vendor-specific information
   SHOULD make an attempt to operate without it, although they may do so
   (and announce they are doing so) in a degraded mode.

   If a vendor potentially encodes more than one item of information in
   this option, then the vendor SHOULD encode the option using
   "Encapsulated vendor-specific options" as described below:

   The Encapsulated vendor-specific options field SHOULD be encoded as a
   sequence of code/length/value fields of identical syntax to the DHCP
   options field with the following exceptions:

      1) There SHOULD NOT be a "magic cookie" field in the encapsulated
         vendor-specific extensions field.

      2) Codes other than 0 or 255 MAY be redefined by the vendor within
         the encapsulated vendor-specific extensions field, but SHOULD
         conform to the tag-length-value syntax defined in section 2.

      3) Code 255 (END), if present, signifies the end of the
         encapsulated vendor extensions, not the end of the vendor
         extensions field. If no code 255 is present, then the end of
         the enclosing vendor-specific information field is taken as the
         end of the encapsulated vendor-specific extensions field.

   The code for this option is 43 and its minimum length is 1.

   Code   Len   Vendor-specific information
   +-----+-----+-----+-----+---
   |  43 |  n  |  i1 |  i2 | ...
   +-----+-----+-----+-----+---

   When encapsulated vendor-specific extensions are used, the
   information bytes 1-n have the following format:

    Code   Len   Data item        Code   Len   Data item       Code
   +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
   |  T1 |  n  |  d1 |  d2 | ... |  T2 |  n  |  D1 |  D2 | ... | ... |
   +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Route;

import java.util.ArrayList;

public class DhcpOption43 extends DhcpOption{
    int[] vendorSpecificInformation;

    private DhcpOption43(){
        super();
        code = 43;
        variableLength = true;
        payloadLength = -1;
        name = "Vendor Specific Information";
        minPayloadLength = 1;
    }

    public void setVendorSpecificInformation(int[] data){
        vendorSpecificInformation = data;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption43 option = new DhcpOption43();
        option.payloadLength = payload.length;
        option.vendorSpecificInformation = payload;
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.IntArrToSignedByteArr(vendorSpecificInformation);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption43 option = new DhcpOption43();
        option.setVendorSpecificInformation(Utils.byteArrToIntArr(str[0].getBytes()));
        return option;
    }

    public static String getCmdDescription(){return "Option 43 - Vendor Specific Information\n\n"
            + "set option 43 {<string bytes>}\n"
            + "set option 43 !@#1easeqdasf12312312\n";
    }
}
