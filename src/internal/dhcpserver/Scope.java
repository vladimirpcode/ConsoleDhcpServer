package internal.dhcpserver;

import internal.dhcpserver.dhcp.option.DhcpOption;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.SubnetMask;
import internal.dhcpserver.net.Subnet;

import java.util.ArrayList;

public class Scope {
    public Subnet subnet;
    public IpAddress startAddress;
    public IpAddress endAddress;
    public ArrayList<IpAddress> excludedAddresses = new ArrayList<>();
    public ArrayList<DhcpOption> dhcpOptions = new ArrayList<>();
    public boolean active;

    public void setSubnet(Subnet subnet){
        this.subnet = subnet;
    }
}
