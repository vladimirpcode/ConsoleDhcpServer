package internal.dhcpserver.net;

public class EthernetAddress  extends MacAddress{

    public EthernetAddress(int[] bytes){
        super();
        this.bytes = bytes;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 6; i++){
            String b = Integer.toHexString(bytes[i]);
            if(b.length() == 1){
                builder.append("0" + b);
            }else {
                builder.append(b);
            }
            if(i != 5){
                builder.append(":");
            }
        }

        return builder.toString();
    }
}
