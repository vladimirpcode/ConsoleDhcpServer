package internal.dhcpserver;

import internal.dhcpserver.dhcp.option.DhcpOption;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.SubnetMask;
import internal.dhcpserver.net.Subnet;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class Scope {
    public Subnet subnet;
    public IpAddress startAddress;
    public IpAddress endAddress;
    public ArrayList<IpAddress> excludedAddresses = new ArrayList<>();
    public ArrayList<DhcpOption> dhcpOptions = new ArrayList<>();
    public boolean active;

    public Scope(Subnet subnet, IpAddress startAddress, IpAddress endAddress,
                 ArrayList<IpAddress> excludedAddresses, ArrayList<DhcpOption> dhcpOptions,
                 boolean active){
        this.subnet = subnet;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.excludedAddresses = excludedAddresses;
        this.dhcpOptions = dhcpOptions;
        this.active = active;
    }

    public void setSubnet(Subnet subnet){
        this.subnet = subnet;
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
        builder.append(subnet.toString() + "\n" +
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
}
