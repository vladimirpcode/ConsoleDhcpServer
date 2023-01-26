package internal.dhcpserver;

import internal.dhcpserver.dhcp.option.DhcpOption;
import internal.dhcpserver.dhcp.option.DhcpOptionFactory;
import internal.dhcpserver.net.Subnet;
import internal.dhcpserver.net.SubnetMask;

import javax.annotation.processing.SupportedSourceVersion;
import java.security.PublicKey;

public class Program {
    public static void test(){
        DhcpServer.getBroadcast();
    }
    public static void main(String[] args){
        test();
        CLI.start();


    }
}
