package internal.dhcpserver.dhcp.option;

public enum DhcpOptionsEnum {
    /* RFC 2132 */
    UNDEFINED(-1,0,"UNDEFINED"),
    Padding(0, 0, "Padding"), //1 байт
    SubnetMask(1, 4, "Subnet Mask"),
    TimeOffset(2, 4, "Time Offset"),
    RouterOption(3, -1, "Router Option"),
    TimeServerOption(4, -1, "Time Server Option"),
    NameServerOption(5, -1, "Name Server Option"),
    DomainNameServerOption(6, -1, "Domain Name Server Option"),
    LogServerOption(7, -1, "Log Server Option"),
    CookieServerOption(8, -1, "Cookie Server Option"),
    LprServerOption(9,  -1, "LRP Server Option"),
    ImpressServerOption(10, -1, "Impress Server Option"),
    ResourceLocationServerOption(11, -1, "Resource Location Server Option"),
    HostNameOption(12, -1, "Host Name Option"),
    BootFileSizeOption(13, 2, "Boot File Size Option"),
    MeritDumpFile(14, -1, "Merit Dump File"),
    DomainName(15, -1, "Domain Name"),
    SwapServer(16, -1, "Swap Server"),
    RootPath(17, -1, "Root Path"),
    ExtensionsPath(18, -1, "Extensions Path"),
    IpForwardingEnableDisableOption(19, 1, "IP Forwarding Enable/Disable Option"),
    NonLocalSourceRoutingEnableDisableOption(20, 1, "Non-Local Source Routing Enable/Disable Option"),
    PolicyFilterOption(21, -1, "Policy Filter Option"),
    MaximumDatagramReassemblySize(22, 2, "Maximum Datagram Reassembly Size"),
    DefaultIpTTL(23, 1, "Default IP Time-to-live"),
    PathMtuAgingTimeoutOption(24, 4, "Path MTU Aging Timeout Option"),
    PathMtuPlateauTableOption(25, -1, "Path MTU Plateau Table Option"),
    InterfaceMtuOption(26, 2, "Interface MTU Option"),
    AllSubnetsAreLocalOption(27, 1, "All Subnets are Local Option"),
    BroadcastAddressOption(28, 4, "Broadcast Address Option"),
    PerformMaskDiscoveryOption(29, 1, "Perform Mask Discovery Option"),
    MaskSupplierOption(30, 1, "Mask Supplier Option"),
    PerformRouterDiscoveryOption(31, 1, "Perform Router Discovery Option"),
    RouterSolicitationAddressOption(32, 4, "Router Solicitation Address Option"),
    StaticRouteOption(33, -1, "Static Route Option"),
    TrailerEncapsulationOption(34, 1, "Trailer Encapsulation Option"),
    ArpCacheTimeoutOption(35, 4, "ARP Cache Timeout Option"),
    EthernetEncapsulationOption(36, 1, "Ethernet Encapsulation Option"),
    TcpDefaultTtlOption(37, 1, "TCP Default TTL Option"),
    TcpKeepaliveIntervalOption(38, 4, "TCP Keepalive Interval Option"),
    TcpKeepaliveGarbageOption(39, 1, "TCP Keepalive Garbage Option"),
    NetworkInformationServiceDomainOption(40, -1, "Network Information Service Domain Option"),
    NetworkInformationServersOption(41, -1, "Network Information Servers Option"),
    NetworkTimeProtocolServersOption(42, -1, "Network Time Protocol Servers Option"),
    VendorSpecificInformation(43, -1, "Vendor Specific Information"),
    NetBiosOverTcpIpNameServerOption(44, -1, "NetBIOS over TCP/IP Name Server Option"),
    NetBiosOverTcpIpDatagramDistributionServerOption(45, -1, "NetBIOS over TCP/IP Datagram Distribution Server Option"),
    NetBiosOverTcpIpNodeTypeOption(46, 1, "NetBIOS over TCP/IP Node Type Option"),
    NetBiosOverTcpIpScopeOption(47, -1, "NetBIOS over TCP/IP Scope Option"),
    XWindowSystemFontServerOption(48, -1, "X Window System Font Server Option"),
    XWindowSystemDisplayManagerOption(49, -1, "X Window System Display Manager Option"),
    NetworkInformationServicePlusDomainOption(64, -1, "Network Information Service+ Domain Option"),
    NetworkInformationServicePlusServersOption(65, -1, "Network Information Service+ Servers Option"),
    MobileIpHomeAgentOption(68, -1, "Mobile IP Home Agent option"),
    SmtpServerOption(69, -1, "Simple Mail Transport Protocol (SMTP) Server Option"),
    Pop3ServerOption(70, -1, "Post Office Protocol (POP3) Server Option"),
    NntpServerOption(71, -1, "Network News Transport Protocol (NNTP) Server Option"),
    DefaultWwwServerOption(72, -1, "Default World Wide Web (WWW) Server Option"),
    DefaultFingerServerOption(73, -1, "Default Finger Server Option"),
    DefaultIrcServerOption(74, -1, " Default Internet Relay Chat (IRC) Server Option"),
    StreetTalkServerOption(75, -1, "StreetTalk Server Option"),
    StdaServerOption(76, -1, "StreetTalk Directory Assistance (STDA) Server Option"),
    RequestedIpAddress(50, 4, "Requested IP Address"),
    IpAddressLeaseTime(51, 4, "IP Address Lease Time"),
    OptionOverload(52, 1, "Option Overload"),
    TftpServerName(66, -1, "TFTP server name"),
    BootfileName(67, -1, "Bootfile name"),
    DhcpMessageType(53, 1, "DHCP Message Type"),
    ServerIdentifier(54, 4, "Server Identifier"),
    ParameterRequestList(55, -1, "Parameter Request List"),
    Message(56, -1, "Message"),
    MaximumDhcpMessageSize(57, 2, "Maximum DHCP Message Size"),
    RenewalT1TimeValue(58, 4, "Renewal (T1) Time Value"),
    RebindingT2TimeValue(59, 4, " Rebinding (T2) Time Value"),
    VendorClassidentifier(60, -1, "Vendor class identifier"),
    ClientIdentifier(61, -1, "Client-identifier"),
    End(255, 0, "End option"); //1 байт

    private final int code;
    private final int dataLength; //0 - 1 бит в целом, -1 - переменная длина
    private final String name;

    DhcpOptionsEnum(int code, int dataLength, String name) {
        this.code = code;
        this.dataLength = dataLength;
        this.name = name;
    }

    public int getCode(){
        return code;
    }

    public static DhcpOptionsEnum valueOf(int code){
        for (DhcpOptionsEnum a:DhcpOptionsEnum.values()) {
            if(a.code == code){
                return a;
            }
        }
        return UNDEFINED;
    }

}
