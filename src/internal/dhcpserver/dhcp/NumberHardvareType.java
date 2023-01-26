package internal.dhcpserver.dhcp;

public enum NumberHardvareType {
    /* RFC 1700 */
    Ethernet10Mb(1, "Ethernet (10Mb)"),
    ExperimentalEthernet3Mb(2, "Experimental Ethernet (3Mb)"),
    AmauterRadioAx25(3, "Amateur Radio AX.25"),
    ProteonProNetTokenRing(4, "Proteon ProNET Token Ring"),
    Chaos(5, "Chaos"),
    Ieee802Networks(6, "IEEE 802 Networks"),
    ARCNET(7, "ARCNET"),
    Hyperchannel(8, "Hyperchannel"),
    Lanstar(9, "Lanstar"),
    AutonetShortAddress(10, "Autonet Short Address"),
    LocalTalk(11, "LocalTalk"),
    LocalNet(12, "LocalNet (IBM PCNet or SYTEK LocalNET)"),
    UltraLink(13, "Ultra link"),
    SMDS(14, "SMDS"),
    FrameRealy(15, "Frame Relay"),
    ATM(16, "Asynchronous Transmission Mode (ATM)"),
    HDLC(17, "HDLC"),
    FibreChannel(18, "Fibre Channel"),
    ATM19(19, "Asynchronous Transmission Mode (ATM)"),
    SerialLine(20, "Serial Line"),
    ATM21(21, "Asynchronous Transmission Mode (ATM)"),
    UNKNOWN(-1, "UNKNOWN");


    private final int number;
    private final String hardwareType;

    NumberHardvareType(int number, String hardwareType){
        this.number = number;
        this.hardwareType = hardwareType;
    }

    public static NumberHardvareType getInstance(int number){
        switch (number){
            case 1: return NumberHardvareType.Ethernet10Mb;
            case 2: return NumberHardvareType.ExperimentalEthernet3Mb;
            case 3: return NumberHardvareType.AmauterRadioAx25;
            case 4: return NumberHardvareType.ProteonProNetTokenRing;
            case 5: return NumberHardvareType.Chaos;
            case 6: return NumberHardvareType.Ieee802Networks;
            case 7: return NumberHardvareType.ARCNET;
            case 8: return NumberHardvareType.Hyperchannel;
            case 9: return NumberHardvareType.Lanstar;
            case 10: return NumberHardvareType.AutonetShortAddress;
            case 11: return NumberHardvareType.LocalTalk;
            case 12: return NumberHardvareType.LocalNet;
            case 13: return NumberHardvareType.UltraLink;
            case 14: return NumberHardvareType.SMDS;
            case 15: return NumberHardvareType.FrameRealy;
            case 16: return NumberHardvareType.ATM;
            case 17: return NumberHardvareType.HDLC;
            case 18: return NumberHardvareType.FibreChannel;
            case 19: return NumberHardvareType.ATM19;
            case 20: return NumberHardvareType.SerialLine;
            case 21: return NumberHardvareType.ATM21;
            default: return NumberHardvareType.UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return this.hardwareType;
    }

    public int getNumber(){
        return this.number;
    }
}
