package internal.dhcpserver.dhcp.option;


/*
   Merit Dump File

   This option specifies the path-name of a file to which the client's
   core image should be dumped in the event the client crashes.  The
   path is formatted as a character string consisting of characters from
   the NVT ASCII character set.

   The code for this option is 14.  Its minimum length is 1.

    Code   Len      Dump File Pathname
   +-----+-----+-----+-----+-----+-----+---
   |  14 |  n  |  n1 |  n2 |  n3 |  n4 | ...
   +-----+-----+-----+-----+-----+-----+---
 */

import internal.dhcpserver.Utils;

public class DhcpOption14 extends DhcpOption{
    String meritDumpFileName;

    private DhcpOption14(){
        super();
        code = 14;
        variableLength = true;
        payloadLength = -1;
        name = "Merit Dump File";
        minPayloadLength = 1;
    }


    public void setMeritDumpFileName(String meritDumpFileName){
        this.meritDumpFileName = meritDumpFileName;
        payloadLength+=meritDumpFileName.length();
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption14 option = new DhcpOption14();
        option.payloadLength = payload.length;
        option.minPayloadLength = 1;
        option.meritDumpFileName = String.valueOf(Utils.IntArrToSignedByteArr(payload));
        return option;

    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = meritDumpFileName.getBytes();
        System.arraycopy(bytes, 0, result, 2, bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption14 option = new DhcpOption14();
        option.setMeritDumpFileName(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 14 - Merit Dump File\n\n"
            + "set option 14 <string_meritDumpFile>\n"
            + "set option 14 any.file\n";
    }
}
