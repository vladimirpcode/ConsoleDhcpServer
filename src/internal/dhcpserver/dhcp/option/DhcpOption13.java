package internal.dhcpserver.dhcp.option;


/*
   Boot File Size Option

   This option specifies the length in 512-octet blocks of the default
   boot image for the client.  The file length is specified as an
   unsigned 16-bit integer.

   The code for this option is 13, and its length is 2.

    Code   Len   File Size
   +-----+-----+-----+-----+
   |  13 |  2  |  l1 |  l2 |
   +-----+-----+-----+-----+
 */

import internal.dhcpserver.Utils;

public class DhcpOption13 extends DhcpOption{
    int fileSize;

    private DhcpOption13(){
        super();
        code = 13;
        variableLength = false;
        payloadLength = 2;
        name = "Boot File Size Option";
    }

    public void setFileSize(int fileSize){
        this.fileSize = fileSize;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption13 option = new DhcpOption13();
        option.fileSize = Utils.getUnsignedNumFrom2int(payload[0], payload[1]);
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.intTo2uBytes(fileSize);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption13 option = new DhcpOption13();
        option.setFileSize(Integer.valueOf(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 13 - Boot File Size Option\n\n"
            + "set option 13 <u_16bit_int_bootFileSize>\n"
            + "set option 13 587\n";
    }
}
