package internal.dhcpserver.net;

import internal.dhcpserver.CriticalError;

import java.awt.image.renderable.ContextualRenderedImageFactory;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Network {
    IpAddress address;
    SubnetMask mask;
    IpAddress broadcast;

    public Network(IpAddress address, SubnetMask mask){
        this.address = address;
        this.mask = mask;
    }

    public Network(String ipSlashMask){
        try {
            address = new IpAddress(ipSlashMask.split("/")[0]);
        }catch (UnknownHostException exc){
            CriticalError.crash(exc);
        }
        mask = new SubnetMask(Integer.valueOf(ipSlashMask.split("/")[1]));
    }

    @Override
    public String toString(){
        return address.toString() + "/" + String.valueOf(mask.getIntValue());
    }

    public boolean isEntry(IpAddress ip){
        if(!getNetworkPart(ip,this.mask).equals(getNetworkPart(this.address, this.mask))){
            return false;
        }
        return getHostPart(ip, this.mask).equals(getHostPart(this.address, this.mask));
    }

    public static IpAddress getHostPart(IpAddress ip, SubnetMask mask){
        IpAddress result = null;
        try {
            result = new IpAddress(new int[]{0,0,0,0});
        }catch (Exception exc){
            CriticalError.crash(exc);
        }
        result.octets[0] = ip.octets[0] & (~mask.data[0]);
        return result;
    }

    public static IpAddress getNetworkPart(IpAddress ip, SubnetMask mask){
        IpAddress result = null;
        try {
            result = new IpAddress(new int[]{0,0,0,0});
        }catch (Exception exc){
            CriticalError.crash(exc);
        }
        result.octets[0] = ip.octets[0] & mask.data[0];
        return result;
    }

    public IpAddress getBroadcast(){
        IpAddress result = null;
        try {
            IpAddress tmp = new IpAddress(new int[]{255,255,255,255});
            IpAddress hostPart = Network.getHostPart(tmp, this.mask);
            result = new IpAddress(new int[]{0,0,0,0});
            for(int i = 0; i < 4; i++){
                result.octets[i] = result.octets[i] | this.address.octets[i] | hostPart.octets[i];

            }
        }catch (Exception exc){
            CriticalError.crash("Не удалось получить broadcast для отправки offer-a");
        }
        return result;
    }
}
