package internal.dhcpserver;

import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Network;
import internal.dhcpserver.net.SubnetMask;

import java.net.InterfaceAddress;
import java.util.Enumeration;
import java.net.NetworkInterface;

public class Program {
    public static void test(){
        try {
            Network network = null;
            network = new Network(new IpAddress("10.10.10.0"), new SubnetMask("255.255.255.0"));
            Scope scope = new Scope(network, new IpAddress("10.10.10.10"), new IpAddress("10.10.10.12"), null,null,true);
            System.out.println(scope);

        }catch (Exception exc){
            CriticalError.crash(exc);
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                //System.out.println(networkInterface.getIndex()+"$"+networkInterface.getDisplayName());
                System.out.println(networkInterface.getDisplayName() + " " + networkInterface.getInterfaceAddresses());
            }

            Network network = new Network(new IpAddress("10.10.10.0"), new SubnetMask("255.255.254.0"));
            System.out.println(network.getBroadcast());
        }catch (Exception exc){
            CriticalError.crash(exc);
        }


    }
    public static void main(String[] args){
        test();
        CLI.start();


    }
}
