package internal.dhcpserver.dhcp.option;


/*
   Policy Filter Option

   This option specifies policy filters for non-local source routing.
   The filters consist of a list of IP addresses and masks which specify
   destination/mask pairs with which to filter incoming source routes.

   Any source routed datagram whose next-hop address does not match one
   of the filters should be discarded by the client.

   See [4] for further information.

   The code for this option is 21.  The minimum length of this option is
   8, and the length MUST be a multiple of 8.

    Code   Len         Address 1                  Mask 1
   +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
   |  21 |  n  |  a1 |  a2 |  a3 |  a4 |  m1 |  m2 |  m3 |  m4 |
   +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
           Address 2                  Mask 2
   +-----+-----+-----+-----+-----+-----+-----+-----+---
   |  a1 |  a2 |  a3 |  a4 |  m1 |  m2 |  m3 |  m4 | ...
   +-----+-----+-----+-----+-----+-----+-----+-----+---
 */

import internal.dhcpserver.Utils;
import internal.dhcpserver.Warning;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.SubnetMask;


import java.net.UnknownHostException;
import java.util.ArrayList;

public class DhcpOption21 extends DhcpOption{
    int minDataLength;
    ArrayList<IpAddress> addresses;
    ArrayList<SubnetMask> masks;

    private DhcpOption21(){
        super();
        code = 21;
        variableLength = true;
        payloadLength = -1;
        name = "Policy Filter Option";
        minDataLength = 8;
        addresses = new ArrayList<>();
        masks = new ArrayList<>();
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption21 option = new DhcpOption21();
        option.payloadLength = payload.length;
        option.minDataLength = 8;
        boolean isMask = false;
        for(int i = 0; i < payload.length; i += 4){
            if(isMask){
                option.masks.add(new SubnetMask(Utils.getIntBytes(payload,i,4)));
            }else{
                try {
                    option.addresses.add(new IpAddress(Utils.getIntBytes(payload,i,4)));
                }catch (UnknownHostException exc){
                    Warning.msg("Option (21): bad address");
                }
            }
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(int i = 0; i < addresses.size(); i++){
            byte[] bytes = new byte[8];
            System.arraycopy(addresses.get(i).toByteArray(),0,bytes,0,4);
            System.arraycopy(masks.get(i).toByteArray(),0,bytes,4,4);
            System.arraycopy(bytes, 0, result,2+i*8,bytes.length);
        }
        return result;
    }

    public void addIpAndMask(IpAddress address, SubnetMask mask){
        payloadLength += 8;
        this.addresses.add(address);
        this.masks.add(mask);
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption21 option = new DhcpOption21();
        for(int i = 0; i < str.length; i += 2){
            option.addIpAndMask(new IpAddress(str[i]), new SubnetMask(str[i+1]));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 21 - Policy Filter Option\n\n"
            + "set option 21 {<IpAddress_addr> <SubnetMask_mask>}\n"
            + "set option 21 10.10.21.1 255.255.255.0 192.168.1.1 255.255.0.0\n";
    }
}
