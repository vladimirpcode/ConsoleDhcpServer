package internal.dhcpserver.dhcp.option;

public class DhcpOptionFactory {
    //не передвигает указатель, просто возвращает опцию
    public static DhcpOption getDhcpOption(int code, int[] payload){
        DhcpOption option = null;
        switch (code){
            case 0 -> option = DhcpOption0.valueOf(payload);
            case 1 -> option = DhcpOption1.valueOf(payload);
            case 2 -> option = DhcpOption2.valueOf(payload);
            case 3 -> option = DhcpOption3.valueOf(payload);
            case 4 -> option = DhcpOption4.valueOf(payload);
            case 5 -> option = DhcpOption5.valueOf(payload);
            case 6 -> option = DhcpOption6.valueOf(payload);
            case 7 -> option = DhcpOption7.valueOf(payload);
            case 8 -> option = DhcpOption8.valueOf(payload);
            case 9 -> option = DhcpOption9.valueOf(payload);
            case 10 -> option = DhcpOption10.valueOf(payload);
            case 11 -> option = DhcpOption11.valueOf(payload);
            case 12 -> option = DhcpOption12.valueOf(payload);
            case 13 -> option = DhcpOption13.valueOf(payload);
            case 14 -> option = DhcpOption14.valueOf(payload);
            case 15 -> option = DhcpOption15.valueOf(payload);
            case 16 -> option = DhcpOption16.valueOf(payload);
            case 17 -> option = DhcpOption17.valueOf(payload);
            case 18 -> option = DhcpOption18.valueOf(payload);
            case 19 -> option = DhcpOption19.valueOf(payload);
            case 20 -> option = DhcpOption20.valueOf(payload);
            case 21 -> option = DhcpOption21.valueOf(payload);
            case 22 -> option = DhcpOption22.valueOf(payload);
            case 23 -> option = DhcpOption23.valueOf(payload);
            case 24 -> option = DhcpOption24.valueOf(payload);
            case 25 -> option = DhcpOption25.valueOf(payload);
            case 26 -> option = DhcpOption26.valueOf(payload);
            case 27 -> option = DhcpOption27.valueOf(payload);
            case 28 -> option = DhcpOption28.valueOf(payload);
            case 29 -> option = DhcpOption29.valueOf(payload);
            case 30 -> option = DhcpOption30.valueOf(payload);
            case 31 -> option = DhcpOption31.valueOf(payload);
            case 32 -> option = DhcpOption32.valueOf(payload);
            case 33 -> option = DhcpOption33.valueOf(payload);
            case 34 -> option = DhcpOption34.valueOf(payload);
            case 35 -> option = DhcpOption35.valueOf(payload);
            case 36 -> option = DhcpOption36.valueOf(payload);
            case 37 -> option = DhcpOption37.valueOf(payload);
            case 38 -> option = DhcpOption38.valueOf(payload);
            case 39 -> option = DhcpOption39.valueOf(payload);
            case 40 -> option = DhcpOption40.valueOf(payload);
            case 41 -> option = DhcpOption41.valueOf(payload);
            case 42 -> option = DhcpOption42.valueOf(payload);
            case 43 -> option = DhcpOption43.valueOf(payload);
            case 44 -> option = DhcpOption44.valueOf(payload);
            case 45 -> option = DhcpOption45.valueOf(payload);
            case 46 -> option = DhcpOption46.valueOf(payload);
            case 47 -> option = DhcpOption47.valueOf(payload);
            case 48 -> option = DhcpOption48.valueOf(payload);
            case 49 -> option = DhcpOption49.valueOf(payload);
            case 50 -> option = DhcpOption50.valueOf(payload);
            case 51 -> option = DhcpOption51.valueOf(payload);
            case 52 -> option = DhcpOption52.valueOf(payload);
            case 53 -> option = DhcpOption53.valueOf(payload);
            case 54 -> option = DhcpOption54.valueOf(payload);
            case 55 -> option = DhcpOption55.valueOf(payload);
            case 56 -> option = DhcpOption56.valueOf(payload);
            case 57 -> option = DhcpOption57.valueOf(payload);
            case 58 -> option = DhcpOption58.valueOf(payload);
            case 59 -> option = DhcpOption59.valueOf(payload);
            case 60 -> option = DhcpOption60.valueOf(payload);
            case 61 -> option = DhcpOption61.valueOf(payload);
            case 64 -> option = DhcpOption64.valueOf(payload);
            case 65 -> option = DhcpOption65.valueOf(payload);
            case 66 -> option = DhcpOption66.valueOf(payload);
            case 67 -> option = DhcpOption67.valueOf(payload);
            case 68 -> option = DhcpOption68.valueOf(payload);
            case 69 -> option = DhcpOption69.valueOf(payload);
            case 70 -> option = DhcpOption70.valueOf(payload);
            case 71 -> option = DhcpOption71.valueOf(payload);
            case 72 -> option = DhcpOption72.valueOf(payload);
            case 73 -> option = DhcpOption73.valueOf(payload);
            case 74 -> option = DhcpOption74.valueOf(payload);
            case 75 -> option = DhcpOption75.valueOf(payload);
            case 76 -> option = DhcpOption76.valueOf(payload);
            case 255 -> option = DhcpOption255.valueOf(payload);
        }

        return option;
    }

    public static DhcpOption getDhcpOption(int code, String str){
        return null;
    }

    public static String getCmdDescription(int code){
        return null;
    }
}
