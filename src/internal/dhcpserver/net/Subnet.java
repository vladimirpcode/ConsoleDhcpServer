package internal.dhcpserver.net;

import internal.dhcpserver.CriticalError;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Subnet {
    Inet4Address address;
    SubnetMask mask;
    Inet4Address broadcast;

    public Subnet(Inet4Address address, SubnetMask mask){
        this.address = address;
        this.mask = mask;
    }

    public Subnet(String ipSlashMask){
        try {
            address = (Inet4Address) (Inet4Address.getByName(ipSlashMask.split("/")[0]));
        }catch (UnknownHostException exc){
            CriticalError.crash(exc);
        }
        mask = new SubnetMask(Integer.valueOf(ipSlashMask.split("/")[1]));
    }

    @Override
    public String toString(){
        return address.toString().substring(1) + "/" + String.valueOf(mask.getIntValue());
    }
}
