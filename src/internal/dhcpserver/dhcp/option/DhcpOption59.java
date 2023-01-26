package internal.dhcpserver.dhcp.option;


/*
   Rebinding (T2) Time Value

   This option specifies the time interval from address assignment until
   the client transitions to the REBINDING state.

   The value is in units of seconds, and is specified as a 32-bit
   unsigned integer.

   The code for this option is 59, and its length is 4.

    Code   Len         T2 Interval
   +-----+-----+-----+-----+-----+-----+
   |  59 |  4  |  t1 |  t2 |  t3 |  t4 |
   +-----+-----+-----+-----+-----+-----+
*/

import internal.dhcpserver.Utils;

public class DhcpOption59 extends DhcpOption{
    long rebindingTime;

    private DhcpOption59(){
        super();
        code = 59;
        variableLength = false;
        payloadLength = 4;
        name = "Rebinding (T2) Time Value";
    }

    public void setRebindingTime(long rebindingTime){
        this.rebindingTime = rebindingTime;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption59 option = new DhcpOption59();
        option.rebindingTime = Utils.getUnsignedLongFrom4int(payload[0],payload[1],payload[2],payload[3]);

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.longTo4uBytes(rebindingTime);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption59 option = new DhcpOption59();
        option.setRebindingTime(Long.parseLong(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 59 - Rebinding (T2) Time Value\n\n"
            + "set option 59 <32bit_u_integer time_value>\n"
            + "set option 59 1000\n";
    }

}
