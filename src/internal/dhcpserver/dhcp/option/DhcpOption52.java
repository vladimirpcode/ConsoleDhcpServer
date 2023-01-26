package internal.dhcpserver.dhcp.option;


/*
   Option Overload

   This option is used to indicate that the DHCP 'sname' or 'file'
   fields are being overloaded by using them to carry DHCP options. A
   DHCP server inserts this option if the returned parameters will
   exceed the usual space allotted for options.

   If this option is present, the client interprets the specified
   additional fields after it concludes interpretation of the standard
   option fields.

   The code for this option is 52, and its length is 1.  Legal values
   for this option are:

           Value   Meaning
           -----   --------
             1     the 'file' field is used to hold options
             2     the 'sname' field is used to hold options
             3     both fields are used to hold options

    Code   Len  Value
   +-----+-----+-----+
   |  52 |  1  |1/2/3|
   +-----+-----+-----+
*/

import internal.dhcpserver.Utils;

public class DhcpOption52 extends DhcpOption{
    int value;

    public DhcpOption52(){
        super();
        code = 52;
        variableLength = false;
        payloadLength = 1;
        name = "Option Overload";
    }

    public void setValue(int value){
        this.value = value;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption52 option = new DhcpOption52();
        option.value = payload[0];
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = new byte[1];
        bytes[0] = (byte)value;
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption52 option = new DhcpOption52();
        option.setValue(Integer.parseInt(str[0]));
        return option;
    }

    public static String getCmdDescription(){return "Option 52 - Option Overload\n\n"
            + "set option 52 <1_u_byte value>\n"
            + "set option 52 3\n";
    }
}
