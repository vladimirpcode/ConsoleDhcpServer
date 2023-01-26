package internal.dhcpserver.dhcp;

public enum DhcpMessageOpCode {
    ClientMessage(1),
    ServerMessage(2);

    private final int opCode;

    DhcpMessageOpCode(int opCode){
        this.opCode = opCode;
    }

    public int getOpCode(){
        return opCode;
    }
}
