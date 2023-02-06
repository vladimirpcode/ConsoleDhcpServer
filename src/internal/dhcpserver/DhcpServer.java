package internal.dhcpserver;

import internal.dhcpserver.dhcp.*;
import internal.dhcpserver.dhcp.option.*;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.SubnetMask;
import java.util.Enumeration;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Optional;

public class DhcpServer {
    private static final int SERVER_PORT = 67;
    private static final int CLIENT_PORT = 68;
    private static final int RECV_BUFFER_SIZE = 1024 * 10;
    private static final int SEND_BUFFER_SIZE = 1024 * 10;
    private static boolean stopFlag;


    private static DatagramSocket serverSocket;
    private static DatagramSocket broadcastClientSocket;
    private static InetAddress broadcastIp;
    private static IpAddress serverIp;
    private static ArrayList<Scope> scopes = new ArrayList<>();



    private static DhcpMessage makeOffer(DhcpMessage clientMsg){
        DhcpMessage dhcpMessage = new DhcpMessage();
        dhcpMessage.setOp(DhcpMessageOpCode.ServerMessage);
        dhcpMessage.setHtype(NumberHardvareType.Ethernet10Mb);
        dhcpMessage.setHlen(0x6);
        dhcpMessage.setHops(0);
        dhcpMessage.setXid(clientMsg.getXid());
        dhcpMessage.setSecs(0);
        dhcpMessage.setFlags(0);
        try {
            dhcpMessage.setCiaddr(new IpAddress("0.0.0.0"));
        }
        catch (UnknownHostException exc){
            CriticalError.crash("bad ciaddr (OFFER)");
        }
        try {
            dhcpMessage.setYiaddr(new IpAddress("192.168.10.100"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad yiaddr (OFFER)");
        }
        try {
            dhcpMessage.setSiaddr(new IpAddress("0.0.0.0"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad siaddr (OFFER)");
        }
        try {
            dhcpMessage.setGiaddr(new IpAddress("0.0.0.0"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad giaddr (OFFER)");
        }
        dhcpMessage.setChaddr(clientMsg.getChaddr());
        dhcpMessage.setSname(String.valueOf(new byte[64]));
        dhcpMessage.setFile(String.valueOf(new int[128]));
        //options
        DhcpOption53 option53 = new DhcpOption53();
        option53.setDhcpMessageType(DhcpMessageType.DHCPOFFER);
        dhcpMessage.addOption(option53);
        DhcpOption1 option1 = new DhcpOption1();
        option1.setSubnetMask(new SubnetMask("255.255.255.0"));
        dhcpMessage.addOption(option1);
        DhcpOption28 option28 = new DhcpOption28();
        try {
            option28.setBroadcastAddress(new IpAddress("192.168.10.255"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad broadcast address");
        }
        dhcpMessage.addOption(option28);
        DhcpOption3 option3 = new DhcpOption3();
        try {
            option3.addRouterAddress(new IpAddress("192.168.10.1"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad default router address");
        }
        dhcpMessage.addOption(option3);
        DhcpOption6 option6 = new DhcpOption6();
        try {
            option6.addDnsServerAddress(new IpAddress("77.88.8.8"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad dns address");
        }
        dhcpMessage.addOption(option6);
        DhcpOption51 option51 = new DhcpOption51();
        option51.setLeaseTime(0x330400015180L);
        dhcpMessage.addOption(option51);
        DhcpOption54 option54 = new DhcpOption54();
        option54.setDhcpServerAddress(serverIp);
        dhcpMessage.addOption(option54);
        dhcpMessage.addOption(new DhcpOption255());
        return dhcpMessage;
    }

    private static DhcpMessage makeAck(DhcpMessage clientMsg){
        DhcpMessage dhcpMessage = new DhcpMessage();
        dhcpMessage.setOp(DhcpMessageOpCode.ServerMessage);
        dhcpMessage.setHtype(NumberHardvareType.Ethernet10Mb);
        dhcpMessage.setHlen(0x6);
        dhcpMessage.setHops(0);
        dhcpMessage.setXid(clientMsg.getXid());
        dhcpMessage.setSecs(0);
        dhcpMessage.setFlags(0);
        try {
            dhcpMessage.setCiaddr(new IpAddress("0.0.0.0"));
        }
        catch (UnknownHostException exc){
            CriticalError.crash("bad ciaddr (OFFER)");
        }
        try {
            dhcpMessage.setYiaddr(new IpAddress("192.168.10.100"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad yiaddr (OFFER)");
        }
        try {
            dhcpMessage.setSiaddr(new IpAddress("0.0.0.0"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad siaddr (OFFER)");
        }
        try {
            dhcpMessage.setGiaddr(new IpAddress("0.0.0.0"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad giaddr (OFFER)");
        }
        dhcpMessage.setChaddr(clientMsg.getChaddr());
        dhcpMessage.setSname(String.valueOf(new byte[64]));
        dhcpMessage.setFile(String.valueOf(new int[128]));
        //options
        DhcpOption53 option53 = new DhcpOption53();
        option53.setDhcpMessageType(DhcpMessageType.DHCPACK);
        dhcpMessage.addOption(option53);
        DhcpOption1 option1 = new DhcpOption1();
        option1.setSubnetMask(new SubnetMask("255.255.255.0"));
        dhcpMessage.addOption(option1);
        DhcpOption28 option28 = new DhcpOption28();
        try {
            option28.setBroadcastAddress(new IpAddress("192.168.10.255"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad broadcast address");
        }
        dhcpMessage.addOption(option28);
        DhcpOption3 option3 = new DhcpOption3();
        try {
            option3.addRouterAddress(new IpAddress("192.168.10.1"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad default router address");
        }
        dhcpMessage.addOption(option3);
        DhcpOption6 option6 = new DhcpOption6();
        try {
            option6.addDnsServerAddress(new IpAddress("77.88.8.8"));
        }catch (UnknownHostException exc){
            CriticalError.crash("bad dns address");
        }
        dhcpMessage.addOption(option6);
        DhcpOption51 option51 = new DhcpOption51();
        option51.setLeaseTime(0x330400015180L);
        dhcpMessage.addOption(option51);
        DhcpOption54 option54 = new DhcpOption54();
        option54.setDhcpServerAddress(serverIp);
        dhcpMessage.addOption(option54);
        dhcpMessage.addOption(new DhcpOption255());
        return dhcpMessage;
    }

    private static void handleDatagram(byte[] data){
        DhcpMessage dhcpMessage = null;
        try {
            dhcpMessage = DhcpMessage.valueOf(data);
            //пишем инфу в консоль
            System.out.println(dhcpMessage.getChaddr() + "\t" + dhcpMessage.findOption(53));
            //кидаем в ответ офер
            DhcpOption option = dhcpMessage.findOption(53);
            if(option != null){
                DhcpOption53 option53 = (DhcpOption53) option;
                if(option53.getDhcpMessageType().getCode() == 1){
                    DhcpMessage offer = makeOffer(dhcpMessage);
                    byte[] sendBytes = offer.toByteArray();
                    try {
                        System.out.println("Пробую отправить на броадкаст " + getBroadcast());
                        broadcastClientSocket.send(new DatagramPacket(sendBytes,sendBytes.length, getBroadcast(), CLIENT_PORT));
                    }catch (Exception exc){
                        System.out.println("Не удалось отправить offer (DHCP-сервер)");
                        System.out.println(exc);
                        exc.printStackTrace();
                    }
                } else if (option53.getDhcpMessageType().getCode() == 3) {
                    DhcpMessage ack = makeAck(dhcpMessage);
                    byte[] sendBytes = ack.toByteArray();
                    try {
                        System.out.println("Пробую отправить ack на броадкаст " + getBroadcast());
                        broadcastClientSocket.send(new DatagramPacket(sendBytes,sendBytes.length, getBroadcast(), CLIENT_PORT));
                    }catch (Exception exc){
                        System.out.println("Не удалось отправить ack (DHCP-сервер)");
                        System.out.println(exc);
                        exc.printStackTrace();
                    }
                }

            }
        }catch (DhcpMessageParseException exc){
            System.out.println("Ошибка разбора DHCP сообщения");
        }

    }

    private static void handleDhcp() {
        stopFlag = false;
        try {
            serverIp = new IpAddress("192.168.10.50");
        } catch (UnknownHostException exc) {
            CriticalError.crash(exc);
        }


        try {
            serverSocket = new DatagramSocket(SERVER_PORT);
            broadcastClientSocket = new DatagramSocket();
        } catch (SocketException se) {
            System.out.println("Ошибка! Не удалось создать серверный сокет! (DHCP Server)");
            System.out.println(se);
            return;
        }
        try {
            serverSocket.setReceiveBufferSize(RECV_BUFFER_SIZE);
            serverSocket.setSendBufferSize(SEND_BUFFER_SIZE);
        } catch (SocketException exc) {
            System.out.println("Ошибка установки размеров буферов (DHCP Server)");
        }
        while (!stopFlag) {
            try {
                byte[] recvBuf = new byte[RECV_BUFFER_SIZE];
                byte[] sendBuf = new byte[SEND_BUFFER_SIZE];
                DatagramPacket inputDatagram = new DatagramPacket(recvBuf, RECV_BUFFER_SIZE);
                serverSocket.receive(inputDatagram);
                handleDatagram(recvBuf);
            } catch (Exception exc) {
                System.out.println("Ошибка обработки UDP-датаграм (DHCP Server)");
                exc.printStackTrace();
            }
        }
    }


    public static void start(){
        Thread dhcpServerThread = new Thread(()->{
            handleDhcp();
        });

        dhcpServerThread.start();
    }

    public static InetAddress getBroadcast(){
        InetAddress broadcast = null;
        try {


            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                //System.out.println(networkInterface.getIndex()+"$"+networkInterface.getDisplayName());
                if(networkInterface.getDisplayName().equals("VMware Virtual Ethernet Adapter for VMnet2")) {
                    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                        broadcast = interfaceAddress.getBroadcast();
                        if (broadcast == null)
                            broadcastIp = broadcast;
                        return broadcast;
                    }
                }
            }
        }
        catch (Exception exc){
            System.out.println("Не удалось получить broadcast");
        }
        return broadcast;
    }

    public static void addScope(Scope scope){
        scopes.add(scope);
    }
}
