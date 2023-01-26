package internal.dhcpserver.net;

public class MacAddress {
    int[] bytes = new int[16];

    @Override
    public String toString() {
        return super.toString();
    }

    public byte[] toByteArray(){
        byte[] result = new byte[bytes.length];
        for(int i = 0; i < bytes.length; i++){
            result[i] = (byte) (bytes[i]);
        }
        return result;
    }
}
