package internal.dhcpserver.dhcp.option;


/*
   Client-identifier

   This option is used by DHCP clients to specify their unique
   identifier.  DHCP servers use this value to index their database of
   address bindings.  This value is expected to be unique for all
   clients in an administrative domain.

   Identifiers SHOULD be treated as opaque objects by DHCP servers.

   The client identifier MAY consist of type-value pairs similar to the
   'htype'/'chaddr' fields defined in [3]. For instance, it MAY consist
   of a hardware type and hardware address. In this case the type field
   SHOULD be one of the ARP hardware types defined in STD2 [22].  A
   hardware type of 0 (zero) should be used when the value field
   contains an identifier other than a hardware address (e.g. a fully
   qualified domain name).

   For correct identification of clients, each client's client-
   identifier MUST be unique among the client-identifiers used on the
   subnet to which the client is attached.  Vendors and system
   administrators are responsible for choosing client-identifiers that
   meet this requirement for uniqueness.

   The code for this option is 61, and its minimum length is 2.

   Code   Len   Type  Client-Identifier
   +-----+-----+-----+-----+-----+---
   |  61 |  n  |  t1 |  i1 |  i2 | ...
   +-----+-----+-----+-----+-----+---
*/

import internal.dhcpserver.Utils;

public class DhcpOption61 extends DhcpOption{
    int[] clientId;

    private DhcpOption61(){
        super();
        code = 61;
        variableLength = true;
        payloadLength = -1;
        minPayloadLength = 2;
        name = "Client-identifier";
    }

    public void setClientId(int[] clientId){
        this.clientId = clientId;
        payloadLength = clientId.length;
    }

    public static DhcpOption valueOf(int[] payload){
        DhcpOption61 option = new DhcpOption61();
        option.payloadLength = payload.length;
        option.clientId = payload;

        return option;
    }

    @Override
    public byte[] toByteArray(){
        byte[] result = new byte[2+payloadLength];
        result[0] = (byte)code;
        result[1] = (byte)payloadLength;
        byte[] bytes = Utils.IntArrToSignedByteArr(clientId);
        System.arraycopy(bytes, 0, result,2,bytes.length);
        return result;
    }
}
