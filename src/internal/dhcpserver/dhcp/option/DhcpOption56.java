package internal.dhcpserver.dhcp.option;


/*
   Message

   This option is used by a DHCP server to provide an error message to a
   DHCP client in a DHCPNAK message in the event of a failure. A client
   may use this option in a DHCPDECLINE message to indicate the why the
   client declined the offered parameters.  The message consists of n
   octets of NVT ASCII text, which the client may display on an
   available output device.

   The code for this option is 56 and its minimum length is 1.

    Code   Len     Text
   +-----+-----+-----+-----+---
   |  56 |  n  |  c1 |  c2 | ...
   +-----+-----+-----+-----+---

*/

import internal.dhcpserver.Utils;

public class DhcpOption56 extends DhcpOption{
    String message;

    private DhcpOption56(){
        super();
        code = 56;
        variableLength = true;
        payloadLength = 1;
        name = "Message";
    }

    public void setMessage(String message){
        this.message = message;
        payloadLength = message.length();
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption56 option = new DhcpOption56();
        option.message = String.valueOf(Utils.IntArrToSignedByteArr(payload));
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = message.getBytes();
        System.arraycopy(bytes, 0, result, 2, bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption56 option = new DhcpOption56();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < str.length; i++){
            if(i != str.length-1){
                builder.append(str[i]);
                builder.append(" ");
            }else{
                builder.append(str[i]);
            }
        }
        option.setMessage(builder.toString());
        return option;
    }

    public static String getCmdDescription(){return "Option 56 - Message\n\n"
            + "set option 56 <string message>\n"
            + "set option 56 Hello\n";
    }
}
