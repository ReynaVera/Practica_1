package peer;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import javax.swing.JTextArea;
/**
 *
 * @author Larios
 */
public class PeerMC implements Runnable{
    private String hosT;
    private int porT;    
    private byte [] mensaje;

    private JTextArea area;
    //"230.0.0.4"
    public PeerMC(String host, int port){
            hosT = host;
            porT = port;        
    }
    public void set_Area(JTextArea area){
        this.area=area;
    }
    public void enviar_Mensaje(String msj){    
        try {           
            InetAddress group = InetAddress.getByName(hosT);
            MulticastSocket socket = new MulticastSocket();            
            mensaje = msj.getBytes();
            DatagramPacket paquete = new DatagramPacket(mensaje,mensaje.length,group,porT);           
            socket.send(paquete);
            System.out.println("Mensaje enviado");    
        } catch (IOException ex) {
        }
    }
    
   
    public void run() {
        try {
                InetAddress group = InetAddress.getByName(hosT);
                MulticastSocket msocket = new MulticastSocket(porT);             
                msocket.joinGroup(group);
                String mensaje="";
                while(true){
                  
                    byte[] msj_enviado = new byte[100];
                    DatagramPacket paquete = new DatagramPacket(msj_enviado,msj_enviado.length);                    
                    msocket.receive(paquete);                    
                    mensaje+=new String(paquete.getData())+ "\n";                    
                    this.area.setText(mensaje);       
                }               
        } catch (IOException ex) {

        }
    }
}
