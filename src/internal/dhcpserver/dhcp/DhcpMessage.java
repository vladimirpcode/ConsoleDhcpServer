package internal.dhcpserver.dhcp;

import internal.dhcpserver.Utils;
import internal.dhcpserver.dhcp.option.*;
import internal.dhcpserver.net.EthernetAddress;
import internal.dhcpserver.net.IpAddress;
import internal.dhcpserver.net.MacAddress;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.net.Inet4Address;

public class DhcpMessage{
    /* RFC 2131 */
    private static final int permanentSize = 1 * 4 + 4 + 2 + 2 + 4 * 4 + 16 + 64 + 128;
    public final long MAGIC_COOKIE = 0x63825363;

    private DhcpMessageOpCode op;
    private NumberHardvareType htype;
    private int hlen;
    private int hops;
    private long xid;
    private int secs;
    private int flags;
    private IpAddress ciaddr;
    private IpAddress yiaddr;
    private IpAddress siaddr;
    private IpAddress giaddr;
    private MacAddress chaddr;
    private String sname;
    private String file;
    private ArrayList<DhcpOption> options = new ArrayList<>();

    public static DhcpMessage valueOf(byte[] bData) throws DhcpMessageParseException{
        int[] data = new int[bData.length];
        //сразу преобразуем беззнаковые байты в знаковые int-ы для Java
        for(int i = 0; i < bData.length; i++){
            data[i] = Byte.toUnsignedInt(bData[i]);
        }
        if(data.length < permanentSize){
            throw new DhcpMessageParseException();
        }
        DhcpMessage dhcpMessage = new DhcpMessage();
        int index = 0;
        /* op */
        if(data[index] == 1){
            dhcpMessage.op = DhcpMessageOpCode.ClientMessage;
        } else if(data[index] == 2){
            dhcpMessage.op = DhcpMessageOpCode.ServerMessage;
        } else{
            throw new DhcpMessageParseException();
        }
        index++;
        /* htype */
        dhcpMessage.htype = NumberHardvareType.getInstance(data[index]);
        if(dhcpMessage.htype == NumberHardvareType.UNKNOWN){
            throw new DhcpMessageParseException();
        }
        index++;
        /* hlen */
        dhcpMessage.hlen = data[index];
        index++;
        /* hops */
        dhcpMessage.hops = data[index];
        index++;
        /* xid */
        int[] tmp = Utils.getIntBytes(data, index, 4);
        dhcpMessage.xid = Utils.getUnsignedLongFrom4int(tmp[0],tmp[1],tmp[2],tmp[3]);
        index += 4;
        /* secs */
        tmp = Utils.getIntBytes(data, index, 2);
        dhcpMessage.secs = Utils.getUnsignedNumFrom2int(tmp[0], tmp[1]);
        index += 2;
        /* flags */
        tmp = Utils.getIntBytes(data, index, 2);
        dhcpMessage.flags = Utils.getUnsignedNumFrom2int(tmp[0], tmp[1]);
        index += 2;
        /* ciaddr */
        try {
            dhcpMessage.ciaddr = new IpAddress(Utils.getIntBytes(data,index,4));
        }catch (Exception exc){
            throw new DhcpMessageParseException();
        }
        index += 4;
        /* yiaddr */
        try {
            dhcpMessage.yiaddr = new IpAddress(Utils.getIntBytes(data,index,4));
        }catch (Exception exc){
            throw new DhcpMessageParseException();
        }
        index += 4;
        /* siaddr */
        try {
            dhcpMessage.siaddr = new IpAddress(Utils.getIntBytes(data,index,4));
        }catch (Exception exc){
            throw new DhcpMessageParseException();
        }
        index += 4;
        /* giaddr */
        try {
            dhcpMessage.giaddr = new IpAddress(Utils.getIntBytes(data,index,4));
        }catch (Exception exc){
            throw new DhcpMessageParseException();
        }
        index += 4;
        /* chaddr */
        dhcpMessage.chaddr = new EthernetAddress(Utils.getIntBytes(data, index, 16));
        index += 16;
        /* sname */
        try {
            dhcpMessage.sname = Utils.getStringFromIntArr(Utils.getIntBytes(data, index, 64));
        }catch (Exception exc){
            throw new DhcpMessageParseException();
        }
        index += 64;
        /* file */
        try {
            dhcpMessage.file = Utils.getStringFromIntArr(Utils.getIntBytes(data, index, 128));
        }catch (Exception exc){
            throw new DhcpMessageParseException();
        }
        index += 128;
        /* options */
        if(data.length < permanentSize + 4){
            System.out.println("bad len");
            return dhcpMessage;
        }
        try{
            //"магическое число"
            if(Utils.getUnsignedLongFrom4int(data[index], data[index + 1],
                    data[index + 2], data[index + 3])!= 0x63825363){
                return dhcpMessage;
            }
            index += 4;
            int code = data[index];
            while(code != 255){
                if(code == 0){
                    dhcpMessage.options.add(DhcpOptionFactory.getDhcpOption(0, new int[]{}));
                    index++;
                }else if(code == 255){
                    dhcpMessage.options.add(DhcpOptionFactory.getDhcpOption(255, new int[]{}));
                }else{
                    int curOptPayloadLength = data[index + 1];
                    System.out.println("option code " + (data[index]));
                    dhcpMessage.options.add(DhcpOptionFactory.getDhcpOption(data[index], Utils.getIntBytes(data,index+2, curOptPayloadLength)));
                    index += 2 + curOptPayloadLength;

                }
                code = data[index];
            }
        }catch (ArrayIndexOutOfBoundsException exc){
            throw new DhcpMessageParseException();
        }
        return dhcpMessage;
    }

    public DhcpMessage(){

    }

    public void setOp(DhcpMessageOpCode op){
        this.op = op;
    }

    public DhcpMessageOpCode getOp(){
        return op;
    }

    public void setHtype(NumberHardvareType htype){
        this.htype = htype;
    }

    public NumberHardvareType getHtype() {
        return htype;
    }

    public void setHlen(int hlen){
        this.hlen = hlen;
    }

    public int getHlen(){
        return hlen;
    }

    public void setHops(int hops){
        this.hops = hops;
    }

    public int getHops(){
        return hops;
    }

    public void setXid(long xid){
        this.xid = xid;
    }

    public long getXid() {
        return xid;
    }

    public void setSecs(int secs){
        this.secs = secs;
    }

    public int getSecs(){
        return secs;
    }

    public void setFlags(int flags){
        this.flags = flags;
    }

    public int getFlags(){
        return flags;
    }

    public void setCiaddr(IpAddress address){
        ciaddr = address;
    }

    public IpAddress getCiaddr(){
        return ciaddr;
    }

    public void setYiaddr(IpAddress address){
        yiaddr = address;
    }

    public IpAddress getYiaddr(){
        return yiaddr;
    }

    public void setSiaddr(IpAddress address){
        siaddr = address;
    }

    public IpAddress getsiaddr(){
        return siaddr;
    }

    public void setGiaddr(IpAddress address){
        giaddr = address;
    }

    public IpAddress getGiaddr(){
        return giaddr;
    }

    public void setChaddr(MacAddress address){
        chaddr = address;
    }

    public MacAddress getChaddr(){
        return chaddr;
    }

    public void setSname(String sname){
        this.sname = sname;
    }

    public String getSname(){
        return sname;
    }

    public void setFile(String file){
        this.file = file;
    }

    public String getFile(){
        return file;
    }

    public void setOptions(ArrayList<DhcpOption> options){
        this.options = options;
    }

    public ArrayList<DhcpOption> getOptions(){
        return options;
    }

    public void addOption(DhcpOption option){
        options.add(option);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Message op code: " + this.op + "\n");
        builder.append("Hardware address type" + this.htype + "\n");
        builder.append("Hardware address length: " + this.hlen + "\n");
        builder.append("hops: " + this.hops + "\n");
        builder.append("Transaction ID: 0x" + Long.toHexString(this.xid) + "\n");
        builder.append("secs: " + this.secs + "\n");
        builder.append("flags: " + this.flags + "\n");
        builder.append("Client IP address: " + this.ciaddr + "\n");
        builder.append("'your' (client) IP address: " + this.yiaddr + "\n");
        builder.append("IP address of next server to use in bootstrap: " + this.siaddr + "\n");
        builder.append("Relay agent IP address: " + this.giaddr + "\n");
        builder.append("Client hardware address: ");
        builder.append(chaddr + "\n");
        builder.append("server host name: " + this.sname + "\n");
        builder.append("Boot file name: " + this.file + "\n");
        for(DhcpOption option : this.options){
            builder.append(option);
            builder.append("\n");
        }
        builder.append("\n\n");
        return builder.toString();
    }

    public DhcpOption findOption(int code){
        for(DhcpOption option:options){
            if(option.getCode() == code){
                return option;
            }
        }
        return null;
    }

    public void fromArrToArrList(ArrayList<Byte> arrayList, byte[] arr){
        for(int i = 0; i < arr.length; i++){
            arrayList.add(arr[i]);
        }
    }

    public byte[] toByteArray(){
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.add((byte)op.getOpCode());
        bytes.add((byte)htype.getNumber());
        bytes.add((byte)hlen);
        bytes.add((byte)hops);
        fromArrToArrList(bytes, Utils.longTo4uBytes(xid));
        fromArrToArrList(bytes, Utils.intTo2uBytes(secs));
        fromArrToArrList(bytes, Utils.intTo2uBytes(flags));
        fromArrToArrList(bytes, ciaddr.toByteArray());
        fromArrToArrList(bytes, yiaddr.toByteArray());
        fromArrToArrList(bytes, siaddr.toByteArray());
        fromArrToArrList(bytes, giaddr.toByteArray());
        fromArrToArrList(bytes, chaddr.toByteArray());
        fromArrToArrList(bytes, Utils.nullTermStrToBytes(sname,64));
        fromArrToArrList(bytes, Utils.nullTermStrToBytes(file,128));
        //опции
        //magic cookie
        fromArrToArrList(bytes, Utils.longTo4uBytes(0x63825363));
        for (DhcpOption option:options){
            fromArrToArrList(bytes, option.toByteArray());
        }
        byte[] result = new byte[bytes.size()];
        for(int i = 0; i < result.length; i++){
            result[i] = bytes.get(i);
        }
        return result;
    }
}
