package internal.dhcpserver;

import internal.dhcpserver.dhcp.option.DhcpOption;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Network;

import java.util.ArrayList;

public class Scope {
    public Network network;
    public IpAddress startAddress;
    public IpAddress endAddress;
    public ArrayList<IpAddress> excludedAddresses = new ArrayList<>();
    public ArrayList<DhcpOption> dhcpOptions = new ArrayList<>();
    public boolean active;
    public ArrayList<IpAddress> leasedAddresses = new ArrayList<>();

    public Scope(Network network, IpAddress startAddress, IpAddress endAddress,
                 ArrayList<IpAddress> excludedAddresses, ArrayList<DhcpOption> dhcpOptions,
                 boolean active){
        this.network = network;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.excludedAddresses = excludedAddresses;
        if(this.excludedAddresses == null){
            this.excludedAddresses = new ArrayList<>();
        }
        this.dhcpOptions = dhcpOptions;
        if(this.dhcpOptions == null){
            this.dhcpOptions = new ArrayList<>();
        }
        this.active = active;
    }

    public void setSubnet(Network network){
        this.network = network;
    }

    public void setStartAddress(IpAddress startAddress){
        this.startAddress = startAddress;
    }

    public void setEndAddress(IpAddress endAddress){
        this.endAddress = endAddress;
    }

    public void setExcludedAddresses(ArrayList<IpAddress> excludedAddresses){
        this.excludedAddresses = excludedAddresses;
    }

    public void setDhcpOptions(ArrayList<DhcpOption> dhcpOptions){
        this.dhcpOptions = dhcpOptions;
    }

    public void setActiveStatus(boolean active){
        this.active = active;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(network.toString() + "\n" +
                startAddress.toString() + "-" + endAddress.toString() + "\n" +
                "excluded:\n");
        if(excludedAddresses.size() == 0){
            builder.append("\t*\n");
        }
        for(int i = 0; i < excludedAddresses.size(); i++){
            builder.append("\t" + excludedAddresses.get(i).toString() + "\n");
        }
        builder.append("options:\n");
        if(dhcpOptions.size() == 0){
            builder.append("\t*\n");
        }
        for(int i = 0; i < dhcpOptions.size(); i++){
            builder.append("\t" + dhcpOptions.get(i).toString() + "\n");
        }
        builder.append("active: " + active);
        return  builder.toString();
    }

    public boolean isLeased(IpAddress ip){
        for(IpAddress currentIp:this.leasedAddresses){
            if(currentIp.equals(ip)){
                return true;
            }
        }
        return false;
    }

    public boolean isExcluded(IpAddress ip){
        for(IpAddress currentIp:this.excludedAddresses){
            if(currentIp.equals(ip)){
                return true;
            }
        }
        return false;
    }
    public boolean isFull(){
        IpAddress ip = this.startAddress;
        while(!ip.equals(this.endAddress)){
            if(!this.isExcluded(ip)){
                if(!this.isLeased(ip)){
                    return false;
                }
            }
            ip = ip.getNext();
        }
        return true;
    }

    public IpAddress getFreeAddress() throws Exception{
        IpAddress currentAddress = this.startAddress;
        while (!currentAddress.equals(this.endAddress)){
            if(!isLeased(currentAddress) && !isExcluded(currentAddress)){
                return new IpAddress(currentAddress);
            }
            currentAddress = currentAddress.getNext();
        }
        throw new Exception();
    }
}
