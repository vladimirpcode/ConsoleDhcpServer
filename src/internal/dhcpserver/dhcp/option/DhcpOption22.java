package internal.dhcpserver.dhcp.option;


/*
   Maximum Datagram Reassembly Size

   This option specifies the maximum size datagram that the client
   should be prepared to reassemble.  The size is specified as a 16-bit
   unsigned integer.  The minimum value legal value is 576.

   The code for this option is 22, and its length is 2.

    Code   Len      Size
   +-----+-----+-----+-----+
   |  22 |  2  |  s1 |  s2 |
   +-----+-----+-----+-----+
 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.SubnetMask;

public class DhcpOption22 extends DhcpOption{
    int maxDatagramReassemblySize;

    private DhcpOption22(){
        super();
        code = 22;
        variableLength = false;
        payloadLength = 2;
        name = "Maximum Datagram Reassembly Size";
    }

    public void setMaxDatagramReassemblySize(int maxDatagramReassemblySize){
        this.maxDatagramReassemblySize = maxDatagramReassemblySize;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption22 option = new DhcpOption22();
        option.minPayloadLength = option.payloadLength;
        option.maxDatagramReassemblySize = Utils.getUnsignedNumFrom2int(payload[0], payload[1]);
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.intTo2uBytes(maxDatagramReassemblySize);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption22 option = new DhcpOption22();
        option.setMaxDatagramReassemblySize(Integer.valueOf(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 22 - Maximum Datagram Reassembly Size\n\n"
            + "set option 22 <16bit_uint_maxDgramReassemblySize>\n"
            + "set option 22 536\n";
    }

}
