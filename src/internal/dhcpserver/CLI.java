package internal.dhcpserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private static ArrayList<Command> commands;

    static{
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
    }

    static void start(){
        Scanner scanner = new Scanner(System.in);
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
