package internal.dhcpserver.net;

public class SubnetMask {
    int[] data;

    public SubnetMask(int[] data){
        this.data = new int[4];
        if(data.length < 4){
            this.data[0] = 0;
            this.data[1] = 0;
            this.data[2] = 0;
            this.data[3] = 0;
        }else{
            System.arraycopy(data,0,this.data,0,4);
        }
    }

    public SubnetMask(String mask){
        data = new int[4];
        String[] octets = mask.split("\\.");
        data[0] = Integer.valueOf(octets[0]);
        data[1] = Integer.valueOf(octets[1]);
        data[2] = Integer.valueOf(octets[2]);
        data[3] = Integer.valueOf(octets[3]);
    }

    public SubnetMask(int mask){
        switch (mask){
            case 0 -> data = new int[]{0,0,0,0};
            case 1 -> data = new int[]{128,0,0,0};
            case 2 -> data = new int[]{192,0,0,0};
            case 3 -> data = new int[]{224,0,0,0};
            case 4 -> data = new int[]{240,0,0,0};
            case 5 -> data = new int[]{248,0,0,0};
            case 6 -> data = new int[]{252,0,0,0};
            case 7 -> data = new int[]{254,0,0,0};
            case 8 -> data = new int[]{255,0,0,0};
            case 9 -> data = new int[]{255,128,0,0};
            case 10 -> data = new int[]{255,192,0,0};
            case 11 -> data = new int[]{255,224,0,0};
            case 12 -> data = new int[]{255,240,0,0};
            case 13 -> data = new int[]{255,248,0,0};
            case 14 -> data = new int[]{255,252,0,0};
            case 15 -> data = new int[]{255,254,0,0};
            case 16 -> data = new int[]{255,255,0,0};
            case 17 -> data = new int[]{255,255,128,0};
            case 18 -> data = new int[]{255,255,192,0};
            case 19 -> data = new int[]{255,255,224,0};
            case 20 -> data = new int[]{255,255,240,0};
            case 21 -> data = new int[]{255,255,248,0};
            case 22 -> data = new int[]{255,255,252,0};
            case 23 -> data = new int[]{255,255,254,0};
            case 24 -> data = new int[]{255,255,255,0};
            case 25 -> data = new int[]{255,255,255,128};
            case 26 -> data = new int[]{255,255,255,192};
            case 27 -> data = new int[]{255,255,255,224};
            case 28 -> data = new int[]{255,255,255,240};
            case 29 -> data = new int[]{255,255,255,248};
            case 30 -> data = new int[]{255,255,255,252};
            case 31 -> data = new int[]{255,255,255,254};
            case 32 -> data = new int[]{255,255,255,255};
        }
    }

    public int getIntValue(){
        return (int)(Integer.bitCount(data[0]) + Integer.bitCount(data[1]) + Integer.bitCount(data[2]) + Integer.bitCount(data[3]));
    }

    @Override
    public String toString(){
        return String.valueOf(data[0]) +"." + String.valueOf(data[1])+"." + String.valueOf(data[2])+"." + String.valueOf(data[3]);
    }

    public byte[] toByteArray(){
        byte[] result = new byte[4];
        result[0] = (byte)(data[0]);
        result[1] = (byte)(data[1]);
        result[2] = (byte)(data[2]);
        result[3] = (byte)(data[3]);
        return result;
    }
}
