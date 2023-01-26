package internal.dhcpserver.dhcp.option;


/*
   Root Path

   This option specifies the path-name that contains the client's root
   disk.  The path is formatted as a character string consisting of
   characters from the NVT ASCII character set.

   The code for this option is 17.  Its minimum length is 1.

    Code   Len      Root Disk Pathname
   +-----+-----+-----+-----+-----+-----+---
   |  17 |  n  |  n1 |  n2 |  n3 |  n4 | ...
   +-----+-----+-----+-----+-----+-----+---
 */


import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;

public class DhcpOption17 extends DhcpOption{
    String rootPath;

    private DhcpOption17(){
        super();
        code = 17;
        variableLength = true;
        payloadLength = -1;
        name = "Root Path";
        minPayloadLength = 1;
    }


    public void setRootPath(String rootPath){
        this.rootPath = rootPath;
        rootPath += rootPath.length();
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption17 option = new DhcpOption17();
        option.payloadLength = payload.length;
        option.minPayloadLength = 1;
        option.rootPath = String.valueOf(Utils.IntArrToSignedByteArr(payload));
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = rootPath.getBytes();
        System.arraycopy(bytes, 0, result, 2, bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption17 option = new DhcpOption17();
        option.setRootPath(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 17 - Root Path\n\n"
            + "set option 17 <string_rootPath>\n"
            + "set option 17 /root\n";
    }
}
