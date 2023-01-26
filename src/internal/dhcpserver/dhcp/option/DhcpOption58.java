package internal.dhcpserver.dhcp.option;


/*
   Renewal (T1) Time Value

   This option specifies the time interval from address assignment until
   the client transitions to the RENEWING state.

   The value is in units of seconds, and is specified as a 32-bit
   unsigned integer.

   The code for this option is 58, and its length is 4.

    Code   Len         T1 Interval
   +-----+-----+-----+-----+-----+-----+
   |  58 |  4  |  t1 |  t2 |  t3 |  t4 |
   +-----+-----+-----+-----+-----+-----+
*/

import internal.dhcpserver.Utils;

public class DhcpOption58 extends DhcpOption{
    long renewalTime;

    private DhcpOption58(){
        super();
        code = 58;
        variableLength = false;
        payloadLength = 4;
        name = "Renewal (T1) Time Value";
    }

    public void setRenewalTime(long renewalTime){
        this.renewalTime = renewalTime;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption58 option = new DhcpOption58();
        option.renewalTime = Utils.getUnsignedLongFrom4int(payload[0],payload[1],payload[2],payload[3]);

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.longTo4uBytes(renewalTime);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption58 option = new DhcpOption58();
        option.setRenewalTime(Long.parseLong(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 58 - Renewal (T1) Time Value\n\n"
            + "set option 58 <32bit_u_integer time_value>\n"
            + "set option 58 1000\n";
    }
}
