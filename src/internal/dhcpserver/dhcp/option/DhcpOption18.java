package internal.dhcpserver.dhcp.option;


/*
   Extensions Path

   A string to specify a file, retrievable via TFTP, which contains
   information which can be interpreted in the same way as the 64-octet
   vendor-extension field within the BOOTP response, with the following
   exceptions:

          - the length of the file is unconstrained;
          - all references to Tag 18 (i.e., instances of the
            BOOTP Extensions Path field) within the file are
            ignored.

   The code for this option is 18.  Its minimum length is 1.

    Code   Len      Extensions Pathname
   +-----+-----+-----+-----+-----+-----+---
   |  18 |  n  |  n1 |  n2 |  n3 |  n4 | ...
   +-----+-----+-----+-----+-----+-----+---
 */

import internal.dhcpserver.Utils;

public class DhcpOption18 extends DhcpOption{
    String extensionsPath;

    private DhcpOption18(){
        super();
        code = 18;
        variableLength = true;
        payloadLength = -1;
        name = "Extensions Path";
        minPayloadLength = 1;
    }


    public void setExtensionsPath(String extensionsPath){
        this.extensionsPath = extensionsPath;
        payloadLength += extensionsPath.length();
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption18 option = new DhcpOption18();
        option.payloadLength = payload.length;
        option.minPayloadLength = 1;
        option.extensionsPath = String.valueOf(Utils.IntArrToSignedByteArr(payload));
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = extensionsPath.getBytes();
        System.arraycopy(bytes, 0, result, 2, bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption18 option = new DhcpOption18();
        option.setExtensionsPath(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 18 - Extensions Path\n\n"
            + "set option 18 <string_extensionsPath>\n"
            + "set option 18 /path\n";
    }
}
