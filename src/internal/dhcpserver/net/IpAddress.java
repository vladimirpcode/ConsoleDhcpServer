package internal.dhcpserver.net;

import internal.dhcpserver.CriticalError;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class IpAddress {
    int[] octets = new int[4];
    String strAddress;

    public IpAddress(int[] octets) throws UnknownHostException{
        this.octets = octets;
        strAddress = octets[0] + "." + octets[1] + "."
                + octets[2] + "." + octets[3];
    }

    public IpAddress(String address) throws UnknownHostException{
        String[] strOctets = address.split("\\.");
        octets[0] = Integer.valueOf(strOctets[0]);
        octets[1] = Integer.valueOf(strOctets[1]);
        octets[2] = Integer.valueOf(strOctets[2]);
        octets[3] = Integer.valueOf(strOctets[3]);
        strAddress = address;
    }

    public Inet4Address toInet4Address(){
        try {
            return (Inet4Address) (Inet4Address.getByName(toString()));
        }catch (UnknownHostException exc){
            CriticalError.crash(exc);
        }
        return null;
    }

    public byte[] toByteArray(){
        byte[] result = new byte[4];
        result[0] = (byte)(octets[0]);
        result[1] = (byte)(octets[1]);
        result[2] = (byte)(octets[2]);
        result[3] = (byte)(octets[3]);
        return result;
    }

    @Override
    public String toString(){
        return strAddress;
    }
}
