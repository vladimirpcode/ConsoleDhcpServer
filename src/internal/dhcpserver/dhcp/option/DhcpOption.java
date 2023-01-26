package internal.dhcpserver.dhcp.option;

/* RFC 2132 */
public abstract class DhcpOption {
    int code;
    int payloadLength;
    int minPayloadLength;
    String name;
    boolean variableLength;

    public int getCode(){
         return code;
     }

    public int getPayloadLength(){
         return payloadLength;
     }

    public String getName(){
         return name;
     }

    public boolean isVariableLength(){
         return variableLength;
     }

    public static DhcpOption valueOf(int[] payload){
        throw new UnsupportedOperationException();
    }
    public static DhcpOption valueOf(String[] str) throws Exception{ throw new UnsupportedOperationException();}
    public static String getCmdDescription(){return "There is no description";}

    public void setPayLength(int dataLength){
        this.payloadLength = dataLength;
    }

    @Override
    public String toString() {
        return "Option: (" + code + ") " + name;
    }

    public abstract byte[] toByteArray();
}
