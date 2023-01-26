package internal.dhcpserver.dhcp.option;


/*
    Interface MTU Option

   This option specifies the MTU to use on this interface.  The MTU is
   specified as a 16-bit unsigned integer.  The minimum legal value for
   the MTU is 68.

   The code for this option is 26, and its length is 2.

    Code   Len      MTU
   +-----+-----+-----+-----+
   |  26 |  2  |  m1 |  m2 |
   +-----+-----+-----+-----+
 */

import internal.dhcpserver.Utils;

import java.util.ArrayList;
import java.util.Optional;

public class DhcpOption26 extends DhcpOption{
    int MTU;
    private DhcpOption26(){
        super();
        code = 26;
        variableLength = false;
        payloadLength = 2;
        name = "Interface MTU Option";
    }

    public void setMTU(int MTU){
        this.MTU = MTU;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption26 option = new DhcpOption26();
        option.minPayloadLength = payload.length;
        option.MTU = Utils.getUnsignedNumFrom2int(payload[0], payload[1]);
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.intTo2uBytes(MTU);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption26 option = new DhcpOption26();
        option.MTU = Integer.valueOf(str[0]);
        return option;
    }

    public static String getCmdDescription(){return "Option 26 - Interface MTU Option\n\n"
            + "set option 26 <16bit_uint_mtuSize>\n"
            + "set option 26 2000\n";
    }

}
