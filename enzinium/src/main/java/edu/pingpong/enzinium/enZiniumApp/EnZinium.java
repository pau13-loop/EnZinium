package edu.pingpong.enzinium.enZiniumApp;

/**
 * Contratos inteligentes con enZinium.
 * 
 * Rick va a organizar un concieto de Los Ricknillos
 * y quiere poner a la venta 100 entradas en nuestra
 * plataforma enZinium.
 * Para ello va a utilizar un contrato inteligente 
 * construido sobre nuestra plataforma.
 * Este contrato recibira instrucciones sobre a quien vender
 * las entradas. El contrato almacenara que usuarios/as 
 * de la plataforma poseen las entradas.
 * Cada usuario/a dipone de una Address /direccion en la
 * plataforma, desde la que gestionar sus enZiniums 
 * y las entradas.
 */
public class EnZinium 
{
    public static void main( String[] args )
    {
        /**
         * Crea una Address en nuestro sistema para Rick
         * Genera las claves privada y publica de la direccion
         * El balance de enZinium de su direccion es cero
         * El simbolo del enZinium es EZI
         */

        Address rick = new Address();
        rick.generateKeyPair();
    }
}
