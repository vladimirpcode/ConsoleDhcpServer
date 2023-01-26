package internal.dhcpserver.dhcp;

public enum DhcpMessageType {
    //RFC 2132 п 9.6
    UNDEFINED(-1, "UNDEFINED"),
    DHCPDISCOVER(1, "DISCOVER"),
    DHCPOFFER(2, "OFFER"),
    DHCPREQUEST(3, "REQUEST"),
    DHCPDECLINE(4, "DECLINE"),
    DHCPACK(5, "ACK"),
    DHCPNAK(6, "NAK"),
    DHCPRELEASE(7, "RELEASE"),
    DHCPINFORM(8, "INFORM");

    private final int value;
    private final String name;

    DhcpMessageType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public static DhcpMessageType valueOf(int value){
        for (DhcpMessageType a:DhcpMessageType.values()) {
            if(a.value == value){
                return a;
            }
        }
        return UNDEFINED;
    }

    @Override
    public String toString(){
        return name;
    }

    public int getCode(){
        return value;
    }
}
/*
             1     DHCPDISCOVER
             2     DHCPOFFER
             3     DHCPREQUEST
             4     DHCPDECLINE
             5     DHCPACK
             6     DHCPNAK
             7     DHCPRELEASE
             8     DHCPINFORM
*/

