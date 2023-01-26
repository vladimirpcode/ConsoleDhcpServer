package internal.dhcpserver;

public class Command{
    private String name;
    private ICommandHandler handler;

    public String getName() {
        return name;
    }

    public void run(){
        handler.handleCommand();
    }

    public Command(String name, ICommandHandler handler){
        this.name = name;
        this.handler = handler;
    }
}
