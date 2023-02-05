package internal.dhcpserver;

import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Network;
import internal.dhcpserver.net.SubnetMask;

import java.net.NetworkInterface;

public class Program {
    public static void test(){
        try {
            Network network = null;
            network = new Network(new IpAddress("10.10.10.0"), new SubnetMask("255.255.255.0"));
            Scope scope = new Scope(network, new IpAddress("10.10.10.10"), new IpAddress("10.10.10.12"), null,null,true);

        }catch (Exception exc){
        CriticalError.crash(exc);
    }
    }
    public static void main(String[] args){
        test();
        CLI.start();


    }
}
