package internal.dhcpserver.dhcp.option;


/*
   Path MTU Plateau Table Option

   This option specifies a table of MTU sizes to use when performing
   Path MTU Discovery as defined in RFC 1191.  The table is formatted as
   a list of 16-bit unsigned integers, ordered from smallest to largest.
   The minimum MTU value cannot be smaller than 68.

   The code for this option is 25.  Its minimum length is 2, and the
   length MUST be a multiple of 2.

    Code   Len     Size 1      Size 2
   +-----+-----+-----+-----+-----+-----+---
   |  25 |  n  |  s1 |  s2 |  s1 |  s2 | ...
   +-----+-----+-----+-----+-----+-----+---
 */

import internal.dhcpserver.Utils;

import java.util.ArrayList;

public class DhcpOption25 extends DhcpOption{
    ArrayList<Integer> pathMtuPlateauTable;

    private DhcpOption25(){
        super();
        code = 25;
        variableLength = true;
        payloadLength =-1;
        name = "Path MTU Plateau Table Option";
        minPayloadLength = 2;
        pathMtuPlateauTable = new ArrayList<>();
    }


    public void addValueToTable(int value){
        pathMtuPlateauTable.add(value);
        this.payloadLength += 2;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption25 option = new DhcpOption25();
        option.payloadLength = payload.length;
        for(int i = 0; i <  payload.length; i += 2){
            option.pathMtuPlateauTable.add(Utils.getUnsignedNumFrom2int(payload[i], payload[i+2]));
        }
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        for(int i = 0; i < pathMtuPlateauTable.size(); i++) {
            byte[] bytes = Utils.intTo2uBytes(pathMtuPlateauTable.get(i));
            System.arraycopy(bytes, 0, result, i*2+2, bytes.length);
        }
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption25 option = new DhcpOption25();
        for(String s:str){
            option.addValueToTable(Integer.valueOf(s));
        }
        return option;
    }

    public static String getCmdDescription(){return "Option 25 - Path MTU Plateau Table Option\n\n"
            + "set option 25 {<16bit_uint_mtuSize>}\n"
            + "set option 25 1120 23 243\n";
    }
}
