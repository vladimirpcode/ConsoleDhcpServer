package internal.dhcpserver.dhcp.option;


/*
   Parameter Request List

   This option is used by a DHCP client to request values for specified
   configuration parameters.  The list of requested parameters is
   specified as n octets, where each octet is a valid DHCP option code
   as defined in this document.

   The client MAY list the options in order of preference.  The DHCP
   server is not required to return the options in the requested order,
   but MUST try to insert the requested options in the order requested
   by the client.

   The code for this option is 55.  Its minimum length is 1.

    Code   Len   Option Codes
   +-----+-----+-----+-----+---
   |  55 |  n  |  c1 |  c2 | ...
   +-----+-----+-----+-----+---
*/


import internal.dhcpserver.Utils;

public class DhcpOption55 extends DhcpOption{
    int[] parametres;


    private DhcpOption55(){
        super();
        code = 55;
        variableLength = true;
        payloadLength = -1;
        name = "Parameter Request List";
    }

    public void setParametres(int[] parametres){
        this.parametres = parametres;
        this.payloadLength = parametres.length;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption55 option = new DhcpOption55();
        option.payloadLength = payload.length;
        option.parametres = payload;
        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.IntArrToSignedByteArr(parametres);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }

    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption55 option = new DhcpOption55();
        int[] parameters = new int[str.length];
        for(int i = 0; i < parameters.length; i++){
            parameters[i] = Integer.parseInt(str[i]);
        }
        option.setParametres(parameters);
        return option;
    }

    public static String getCmdDescription(){return "Option 55 - Parameter Request List\n\n"
            + "set option 55 <{1byte_u_int parameter}>\n"
            + "set option 55 2 240 31 20\n";
    }

}
