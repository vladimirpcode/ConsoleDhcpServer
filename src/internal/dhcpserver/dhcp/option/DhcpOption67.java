package internal.dhcpserver.dhcp.option;


/*
   Bootfile name

   This option is used to identify a bootfile when the 'file' field in
   the DHCP header has been used for DHCP options.

   The code for this option is 67, and its minimum length is 1.

       Code  Len   Bootfile name
      +-----+-----+-----+-----+-----+---
      | 67  |  n  |  c1 |  c2 |  c3 | ...
      +-----+-----+-----+-----+-----+---
*/

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;

import java.net.UnknownHostException;

public class DhcpOption67 extends DhcpOption{
    String bootfileName;

    private DhcpOption67(){
        super();
        code = 67;
        variableLength = true;
        payloadLength = -1;
        name = "Bootfile name";
        minPayloadLength = 1;
    }

    public void setBootfileName(String bootfileName){
        this.bootfileName = bootfileName;
        payloadLength = bootfileName.length();
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption67 option = new DhcpOption67();
        option.payloadLength = payload.length;
        option.bootfileName = String.valueOf(Utils.IntArrToSignedByteArr(payload));

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = bootfileName.getBytes();
        System.arraycopy(bytes, 0, result, 2, bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption67 option = new DhcpOption67();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < str.length; i++){
            if(i != str.length-1){
                builder.append(str[i]);
                builder.append(" ");
            }else{
                builder.append(str[i]);
            }
        }
        option.setBootfileName(builder.toString());
        return option;
    }

    public static String getCmdDescription(){return "Option 67 - Bootfile name\n\n"
            + "set option 67 <string bootfile_name>\n"
            + "set option 67 boot.bin\n";
    }
}
