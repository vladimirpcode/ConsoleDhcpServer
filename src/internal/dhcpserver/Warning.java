package internal.dhcpserver;

public class Warning {
    public static void msg(Exception exc){
        System.out.println(exc);
    }

    public static void msg(String msg){
        System.out.println("Warning: " + msg);
    }
}
