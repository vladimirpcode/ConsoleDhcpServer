package internal.dhcpserver.dhcp.option;

import internal.dhcpserver.net.SubnetMask;

public class DhcpOption0 extends DhcpOption{

    public DhcpOption0(){
        super();
        code = 0;
        payloadLength = 0;
        variableLength = false;
        name = "Padding";
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption0 option = new DhcpOption0();

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[1];
        result[0] = 0;
        return result;
    }


    public static DhcpOption valueOf(String[] str) throws Exception{
        DhcpOption0 option = new DhcpOption0();
        return option;
    }
    public static String getCmdDescription(){return "Option 0 - Padding\n\nset option 0\n";}

}
