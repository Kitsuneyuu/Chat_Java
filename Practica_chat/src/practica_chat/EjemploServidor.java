package practica_chat;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author vesprada
 */
public class EjemploServidor {

    public static void main(String[] args) throws IOException {
        new Server();

    }
}

class Server {

    private static int maxClientsCount = 10;
    private static final Lectura[] cliente = new Lectura[maxClientsCount];
    static ServerSocket server;
    static ServerSocket servsock;
    String cad;
    String cadena = "CHAT";
    public static String linea = "";
    

    public Server() throws IOException {
        server = new ServerSocket(9999);
        servsock = new ServerSocket(8000);
        SpinnerNumberModel sModel = new SpinnerNumberModel(10, 0, 30, 1);
        JSpinner spinner = new JSpinner(sModel);
        int con = 10;
        con = JOptionPane.showOptionDialog(null, spinner, "Introduce numero maximo de conexiones", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (con == JOptionPane.OK_OPTION) {
            maxClientsCount = (int) spinner.getValue();
            System.out.println("Se han asignado " + maxClientsCount + " conexiones como maximo.");
        } else if (con == JOptionPane.CANCEL_OPTION) {
            maxClientsCount = 10;
            System.out.println("Numero máximo de conexiones por defecto: 10");
        }
        while (true) {
            Socket c = null;
            c = server.accept();

            if (c != null) {
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (cliente[i] == null) {
                        (cliente[i] = new Lectura(c, cliente)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    System.out.println("Otros clientes desean conectarse... Hay que abrir mas conexiones a la proxima");
                }
                OutputStream os = c.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
            } else {
                System.out.println("Socket cliente no creado");
            }
        }
    }
}

class Lectura extends Thread {

    private boolean cerrar = false;
    private int maxClientsCount;
    String cadena = null;
    String nicks = "";
    String privat = "";
    int color = 0;
    private String nick = null;
    private Socket c;
    private final Lectura[] cliente;
    private DataOutputStream dos = null;

    public Lectura(Socket c, Lectura[] cliente) {
        this.c = c;
        this.cliente = cliente;
        maxClientsCount = cliente.length;
    }

    public void cerrar() {
        cerrar = true;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        Lectura[] cliente = this.cliente;
        try {
            InputStream is = c.getInputStream();
            OutputStream os = c.getOutputStream();
            DataInputStream dis = new DataInputStream(is);
            dos = new DataOutputStream(os);
            nick = dis.readUTF();
            Random numGen = new Random();


            Color aleatori = new Color(numGen.nextInt(256), numGen.nextInt(256), numGen.nextInt(256));
            color = aleatori.getRGB();


            synchronized (cliente) {
                for (int i = 0; i < maxClientsCount; i++) {

                    if (cliente[i] != null && cliente[i] != this) {
                        cliente[i].dos.writeUTF("nuevo");
                        cliente[i].dos.writeUTF(color + "");
                        cliente[i].dos.writeUTF(nick);
                        nicks += cliente[i].nick + ",";
                    }
                    if (cliente[i] == this) {
                        cliente[i].dos.writeUTF("bienvenido");
                        cliente[i].dos.writeUTF(color + "");
                        cliente[i].dos.writeUTF(nicks);
                    }
                }
            }

            while (true) {
                cadena = dis.readUTF();
                privat = dis.readUTF();
                
                if (cadena.startsWith("/closedpassword")) {
                    break;
                }
                synchronized (cliente) {
                    if (!privat.equals("Todos") && cadena.startsWith("/private :")) {
                        for (int i = 0; i < maxClientsCount; i++) {
                            if (cliente[i] != null && cliente[i].nick.equals(privat)) {
                                cliente[i].dos.writeUTF(cadena);
                                cliente[i].dos.writeUTF(color + "");
                                cliente[i].dos.writeUTF(nick);

                            }
                        }
                    }else if (cadena.startsWith("To:       ")){
                          for (int i = 0; i < maxClientsCount; i++) {
                            if (cliente[i] != null && cliente[i].nick.equals(privat)) {
                                cliente[i].dos.writeUTF(cadena);
                                cliente[i].dos.writeUTF(color + "");
                                cliente[i].dos.writeUTF(nick);

                            }
                        }
                    }
                    else if (cadena.startsWith("/Send :   ")) {
                        
                        Socket sock;

                        int filesize = 6022386; // filesize temporary hardcoded
                        long start = System.currentTimeMillis();
                        int bytesRead;
                        int current = 0;
                        // create socket


                        System.out.println("Waiting...");

                        sock = Server.servsock.accept();
                        System.out.println("Accepted connection : " + sock);

                        byte[] mybytearray = new byte[filesize];
                        InputStream iss = sock.getInputStream();
                        FileOutputStream fos = new FileOutputStream("proba");
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        bytesRead = iss.read(mybytearray, 0, mybytearray.length);
                        current = bytesRead;

                        // thanks to A. Cádiz for the bug fix
                        do {
                            bytesRead =
                                    iss.read(mybytearray, current, (mybytearray.length - current));
                            if (bytesRead >= 0) {
                                current += bytesRead;
                            }
                        } while (bytesRead > -1);

                        bos.write(mybytearray, 0, current);
                        bos.flush();
                        long end = System.currentTimeMillis();
                        System.out.println(end - start);
                        bos.close();
                        sock.close();


                        System.out.println("Waiting...");
                        for (int i = 0; i < maxClientsCount; i++) {
                            System.out.append(privat);
                            if (cliente[i] != null && cliente[i].nick.equals(privat)) {
                                cliente[i].dos.writeUTF(cadena);
                                cliente[i].dos.writeUTF(color + "");
                                cliente[i].dos.writeUTF(nick);
                                sock = Server.servsock.accept();
                                File myFile = new File("./proba");
                                System.out.println("Proba");
                                mybytearray = new byte[(int) myFile.length()];
                                FileInputStream fis = new FileInputStream(myFile);
                                BufferedInputStream bis = new BufferedInputStream(fis);
                                bis.read(mybytearray, 0, mybytearray.length);
                                OutputStream oss = sock.getOutputStream();
                                System.out.println("Sending...");
                                oss.write(mybytearray, 0, mybytearray.length);
                                oss.flush();
                                sock.close();
                            }
                        }



                    } else {
                        for (int i = 0; i < maxClientsCount; i++) {
                            if (cliente[i] != null) {
                                cliente[i].dos.writeUTF(cadena);
                                cliente[i].dos.writeUTF(color + "");
                                cliente[i].dos.writeUTF(nick);
                            }
                        }
                    }
                }
            }
            synchronized (cliente) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (cliente[i] != null && cliente[i] != this) {
                        cliente[i].dos.writeUTF("sale");
                        cliente[i].dos.writeUTF(color + "");
                        cliente[i].dos.writeUTF(nick);
                    }
                    if (cliente[i] == this) {
                        cliente[i] = null;
                        System.out.println("Se ha liberado el recurso del cliente.");
                    }
                }
            }

            is.close();
            os.close();
            c.close();
            System.out.println("Se han cerrado correctamente el hilo y conexiones del cliente.");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
