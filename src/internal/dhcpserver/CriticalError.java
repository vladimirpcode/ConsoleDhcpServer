package internal.dhcpserver;

public class CriticalError {
    public static void crash(Exception exc){
        System.out.print(exc.toString());
        System.exit(100);
    }

    public static void crash(String msg){
        System.out.println(msg);
        System.exit(100);
    }
}
