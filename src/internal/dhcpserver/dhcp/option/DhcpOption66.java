package internal.dhcpserver.dhcp.option;


/*
   TFTP server name

   This option is used to identify a TFTP server when the 'sname' field
   in the DHCP header has been used for DHCP options.

   The code for this option is 66, and its minimum length is 1.

       Code  Len   TFTP server
      +-----+-----+-----+-----+-----+---
      | 66  |  n  |  c1 |  c2 |  c3 | ...
      +-----+-----+-----+-----+-----+---

*/

import internal.dhcpserver.Utils;

public class DhcpOption66 extends DhcpOption{
    String tftpServerName;

    private DhcpOption66(){
        super();
        code = 66;
        variableLength = true;
        payloadLength = -1;
        name = "TFTP server name";
        minPayloadLength = 1;
    }

    public void setTftpServerName(String tftpServerName){
        this.tftpServerName = tftpServerName;
        this.payloadLength = tftpServerName.length();
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption66 option = new DhcpOption66();
        option.payloadLength = payload.length;
        option.tftpServerName = String.valueOf(Utils.IntArrToSignedByteArr(payload));

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = tftpServerName.getBytes();
        System.arraycopy(bytes, 0, result, 2, bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption66 option = new DhcpOption66();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < str.length; i++){
            if(i != str.length-1){
                builder.append(str[i]);
                builder.append(" ");
            }else{
                builder.append(str[i]);
            }
        }
        option.setTftpServerName(builder.toString());
        return option;
    }

    public static String getCmdDescription(){return "Option 66 - TFTP server name\n\n"
            + "set option 66 <string bootfile_name>\n"
            + "set option 66 server.name\n";
    }
}
