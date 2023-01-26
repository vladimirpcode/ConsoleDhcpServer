package internal.dhcpserver.dhcp.option;


/*
   IP Address Lease Time

   This option is used in a client request (DHCPDISCOVER or DHCPREQUEST)
   to allow the client to request a lease time for the IP address.  In a
   server reply (DHCPOFFER), a DHCP server uses this option to specify
   the lease time it is willing to offer.

   The time is in units of seconds, and is specified as a 32-bit
   unsigned integer.

   The code for this option is 51, and its length is 4.

    Code   Len         Lease Time
   +-----+-----+-----+-----+-----+-----+
   |  51 |  4  |  t1 |  t2 |  t3 |  t4 |
   +-----+-----+-----+-----+-----+-----+
*/


import internal.dhcpserver.Utils;
import internal.dhcpserver.net.IpAddress;

import java.util.ArrayList;

public class DhcpOption51 extends DhcpOption{
    long leaseTime;

    public DhcpOption51(){
        super();
        code = 51;
        variableLength = false;
        payloadLength = 4;
        name = "IP Address Lease Time";
    }

    public void setLeaseTime(long leaseTime){
        this.leaseTime = leaseTime;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption51 option = new DhcpOption51();
        option.leaseTime = Utils.getUnsignedLongFrom4int(payload[0], payload[1], payload[2], payload[3]);
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.longTo4uBytes(leaseTime);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption51 option = new DhcpOption51();
        option.setLeaseTime(Long.valueOf(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 51 - IP Address Lease Time\n\n"
            + "set option 51 <32bit_uint ipAddrLeaseTime>\n"
            + "set option 51 236\n";
    }

}
