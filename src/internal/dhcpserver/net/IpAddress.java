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

    public IpAddress(IpAddress ip){
        this.octets[0] = ip.octets[0];
        this.octets[1] = ip.octets[1];
        this.octets[2] = ip.octets[2];
        this.octets[3] = ip.octets[3];
        this.strAddress = String.valueOf(ip.octets[0]) + String.valueOf(ip.octets[1]) +
                String.valueOf(ip.octets[2]) + String.valueOf(ip.octets[3]);
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

    @Override
    public boolean equals(Object obj) {
        IpAddress anotherIp = (IpAddress) obj;
        return octets[0] == anotherIp.octets[0] &&
                octets[1] == anotherIp.octets[1] &&
                octets[2] == anotherIp.octets[2] &&
                octets[3] == anotherIp.octets[3];
    }


    public IpAddress getNext(){
        IpAddress result = new IpAddress(this);
        int index = 3;
        while(result.octets[index] == 255 && index >= 0){
            index--;
        }
        if(index < 0){
            //переполнение
            try {
                return new IpAddress(new int[]{0, 0, 0, 0});
            }catch (Exception exc){
                CriticalError.crash(exc);
            }
        }
        result.octets[index]++;
        index++;
        while(index <= 3){
            result.octets[index] = 0;
            index++;
        }

        return result;
    }
}
