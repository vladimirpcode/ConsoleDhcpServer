package internal.dhcpserver.dhcp.option;

/*
Time Offset

   The time offset field specifies the offset of the client's subnet in
   seconds from Coordinated Universal Time (UTC).  The offset is
   expressed as a two's complement 32-bit integer.  A positive offset
   indicates a location east of the zero meridian and a negative offset
   indicates a location west of the zero meridian.

   The code for the time offset option is 2, and its length is 4 octets.

    Code   Len        Time Offset
   +-----+-----+-----+-----+-----+-----+
   |  2  |  4  |  n1 |  n2 |  n3 |  n4 |
   +-----+-----+-----+-----+-----+-----+

 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.net.SubnetMask;

public class DhcpOption2 extends DhcpOption{
    long timeOffset;

    private DhcpOption2(){
        super();
        code = 2;
        variableLength = false;
        payloadLength = 4;
        name = "Time Offset";
    }

    public void setTimeOffset(long offset){
        this.timeOffset = offset;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption2 option = new DhcpOption2();

        option.timeOffset = Utils.getUnsignedNumFrom2int(payload[0], payload[1]);

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.longTo4uBytes(timeOffset);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption2 option = new DhcpOption2();
        option.setTimeOffset(Long.valueOf(str[0]));
        return option;
    }
    public static String getCmdDescription(){return "Option 2 - Time Offset\n\nset option 2 <uint_timeOffset>\n" +
            "set option 2 35236\n";}
}