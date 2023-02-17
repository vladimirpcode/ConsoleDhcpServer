package internal.dhcpserver;

import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Network;
import internal.dhcpserver.net.SubnetMask;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Program {
    public static void test() {
        try {



        }
        catch (Exception exc){
            CriticalError.crash(exc);
        }
    }
    public static void main(String[] args){
        test();
        CLI.start();


    }
}
