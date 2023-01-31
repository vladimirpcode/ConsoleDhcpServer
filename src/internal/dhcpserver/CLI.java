package internal.dhcpserver;

import internal.dhcpserver.dhcp.DhcpMessage;
import internal.dhcpserver.dhcp.option.DhcpOption;
import internal.dhcpserver.dhcp.option.DhcpOptionFactory;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.Subnet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private static ArrayList<Command> commands;
    private static Scanner scanner;
    static{
        scanner = new Scanner(System.in);
        commands = new ArrayList<>();
        commands.add(new Command("help", () -> {
            try {
                BufferedReader br = new BufferedReader(new FileReader("internal/dhcpserver/help.txt"));
                System.out.println(br.readLine());
                br.close();
            }catch (IOException exception){
                System.out.println("Ошибка! Не удалось прочитать файл справки.");
            }
        }));
        commands.add(new Command("start", () ->{
            DhcpServer.start();
        }));
        commands.add(new Command("add scope", () ->{
            Subnet subnet;
            IpAddress startAddress = null;
            IpAddress endAddress = null;
            ArrayList<IpAddress> excludedAddresses = new ArrayList<>();
            ArrayList<DhcpOption> options = new ArrayList<>();
            boolean active;
            System.out.println("Введите подсеть по образцу: 172.16.1.0/24");
            subnet = new Subnet(scanner.nextLine());
            System.out.println("Введите стартовый IP адрес");
            try{
                 startAddress = new IpAddress(scanner.nextLine());
            }catch (Exception exc){
                CriticalError.crash(exc);
            }
            System.out.println("Введите конечный IP адрес");
            try{
                endAddress = new IpAddress(scanner.nextLine());
            }catch (Exception exc){
                CriticalError.crash(exc);
            }
            System.out.println("Введите диапозоны исключения по примеру:\n" +
                    "\t172.16.1.1-172.16.1.10\n" +
                    "\t172.16.1.15\n" +
                    "\tend");
            String line = scanner.nextLine();
            while(!line.equals("end")){
                String[] lines = line.split("-");
                if(lines.length == 2){
                    int startIndex = Integer.valueOf(lines[0].split("\\.")[3]);
                    int endIndex = Integer.valueOf(lines[1].split("\\.")[3]);
                    String[] octets = lines[0].split("\\.");
                    for(int i = startIndex; i <= endIndex; i++){
                        try {
                            excludedAddresses.add(new IpAddress(octets[0] + "." +
                                    octets[1] + "." +
                                    octets[2] + "." +
                                    String.valueOf(i)));
                        }catch (Exception exc){
                            CriticalError.crash(exc);
                        }
                    }
                }else{
                    try {
                        excludedAddresses.add(new IpAddress(line));
                    }catch (Exception exc){
                        CriticalError.crash(exc);
                    }
                }
                line = scanner.nextLine();
            }
            System.out.println("Введите опции DHCP (end - конец, help 2 - описание как ввести опцию два, \n" +
                    "set option ...)");
            line = scanner.nextLine();
            while (!line.equals("end")){
                String lines[] = line.split(" ");
                if(lines[0].equals("help")){
                    System.out.println(DhcpOptionFactory.getCmdDescription(Integer.valueOf(lines[1])));
                }else if(lines[0].equals("set") && lines[1].equals("option")){
                    StringBuilder builder = new StringBuilder();
                    for(int i = 2; i < lines.length; i++){
                        if(i != lines.length-1)
                            builder.append(lines[i] + " ");
                        else
                            builder.append(lines[i]);
                    }
                    options.add(DhcpOptionFactory.getDhcpOption(Integer.valueOf(lines[2]), builder.toString()));
                }else{
                    System.out.println("Нераспознанная команда");
                }
                line = scanner.nextLine();
            }

            System.out.println("Активировать диапозон? (y/n)");
            String answer = scanner.nextLine();
            if(answer.startsWith("y")){
                active = true;
            }else{
                active = false;
            }
            Scope scope = new Scope(subnet,startAddress,endAddress,excludedAddresses,options, active);
            DhcpServer.addScope(scope);
            System.out.println(scope);
        } ));
    }

    static void start(){

        String cmdStr = scanner.nextLine();
        while(!cmdStr.equals("exit")){
            for(Command cmd:commands){
                if(cmd.getName().equals(cmdStr)){
                    cmd.run();
                    break;
                }
            }
            cmdStr = scanner.nextLine();
        }
    }
}
