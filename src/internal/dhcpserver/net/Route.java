package internal.dhcpserver.net;

import java.net.Inet4Address;

public class Route {
    IpAddress destanation;
    SubnetMask mask;
    IpAddress router;

    public Route(IpAddress destanation, IpAddress router){
        this.destanation = destanation;
        this.router = router;
    }

    public Route(IpAddress destanation, SubnetMask mask, IpAddress router){
        this.destanation = destanation;
        this.mask = mask;
        this.router = router;
    }

    public IpAddress getNextHop(){
        return router;
    }

    public IpAddress getDestanation(){
        return destanation;
    }
}
