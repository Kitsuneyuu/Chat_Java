/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_chat;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

/**
 *
 * @author vesprada
 */
public class EjemploCliente {

    public static void main(String[] args) throws IOException, InterruptedException {
        new Client();
    }
}

class Client {

    static Socket cli;
    String cad;
    String cadena = "CHAT";
    static boolean tanca = false;
    static boolean enviar = false;
    static OutputStream os = null;
    static DataOutputStream dos = null;
    static String nick;

    public Client() throws IOException, InterruptedException {
        Cliente frame = new Cliente();
        frame.setVisible(true);
        nick = "<" + JOptionPane.showInputDialog("Introduce tu nickname.") + "> ";
        try {
            cli = new Socket("127.0.0.1", 9999);
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }

        Lectura t = new Lectura(cli);
        t.start();

        os = cli.getOutputStream();
        dos = new DataOutputStream(os);

        dos.writeUTF(nick);

    }

    static class Lectura extends Thread {

        private Socket cli;

        public Lectura(Socket cli) {
            this.cli = cli;
        }

        public void run() {
            String cad;
            int color;
            String nick;
            String cadena;
            InputStream is = null;


            try {
                is = cli.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                while (!tanca) {
                    cad = dis.readUTF();
                    color = Integer.parseInt(dis.readUTF());
                    Color Color = new Color(color);
                    nick = dis.readUTF();
                    if (cad.startsWith("/Send :   ")) {
                        cad = cad.substring(10);
                        if (!Cliente.nicks.contains(nick)) {
                            Cliente.add(nick);
                            Cliente.nicks.add(nick);
                        }

                        //JPanel pane = (JPanel) Cliente.tabbedPane.getComponent(Cliente.nicks.indexOf(nick));
                        //System.out.println(nick);
                        //JScrollPane jsp = (JScrollPane) pane.getComponent(3);
                        JTextPane tp2 = Cliente.list.get(Cliente.nicks.indexOf(nick));
                        getFichero g = new getFichero(cad);
                        g.start();

                        Cliente.sc.setForeground(Cliente.set, Color);
                        Cliente.doc = tp2.getStyledDocument();
                        Cliente.doc.insertString(Cliente.doc.getLength(), "***** Usuario ", Cliente.set);
                        Cliente.sc.setBold(Cliente.set, true);
                        Cliente.doc = tp2.getStyledDocument();
                        Cliente.doc.insertString(Cliente.doc.getLength(), nick, Cliente.set);
                        Cliente.sc.setBold(Cliente.set, false);
                        Cliente.doc = tp2.getStyledDocument();
                        Cliente.doc.insertString(Cliente.doc.getLength(), " ha enviado el fichero ", Cliente.set);
                        Cliente.sc.setBold(Cliente.set, true);
                        Cliente.doc = tp2.getStyledDocument();
                        Cliente.doc.insertString(Cliente.doc.getLength(), cad, Cliente.set);
                        Cliente.sc.setBold(Cliente.set, false);
                        Cliente.doc = tp2.getStyledDocument();
                        Cliente.doc.insertString(Cliente.doc.getLength(), "  *****\n", Cliente.set);
                    } else if (cad.startsWith("/private :")) {
                        cad = cad.substring(10);
                        JPanel pane = null;
                        if (!Cliente.nicks.contains(nick) && !nick.equals(Client.nick)) {
                            Cliente.add(nick);
                            System.out.println(nick);
                            Cliente.nicks.add(nick);
                        }

                        for (int x = 0; x < Cliente.nicks.size(); x++) {
                            System.out.print(" CLIENTES: " + Cliente.nicks.get(x));
                        }


                        JTextPane tp2 = Cliente.list.get(Cliente.nicks.indexOf(nick));
                        Cliente.sc.setBold(Cliente.set, true);
                        Cliente.sc.setForeground(Cliente.set, Color);
                        Cliente.doc = tp2.getStyledDocument();
                        Cliente.doc.insertString(Cliente.doc.getLength(), nick, Cliente.set);
                        Cliente.sc.setBold(Cliente.set, false);
                        Cliente.doc = tp2.getStyledDocument();
                        Cliente.doc.insertString(Cliente.doc.getLength(), cad + "\n", Cliente.set);

                    } else if (cad.startsWith("To:       ")) {
                        cad = cad.substring(10);

                            Cliente.sc.setBold(Cliente.set, true);
                            Cliente.sc.setForeground(Cliente.set, Color);
                            Cliente.doc = Cliente.tp.getStyledDocument();
                            Cliente.doc.insertString(Cliente.doc.getLength(), nick + "Te dice: ", Cliente.set);
                            Cliente.sc.setBold(Cliente.set, false);
                            Cliente.doc = Cliente.tp.getStyledDocument();
                            Cliente.doc.insertString(Cliente.doc.getLength(), cad + "\n", Cliente.set);
                        


                    } else {
                        switch (cad) {
                            case "nuevo":

                                Cliente.sc.setForeground(Cliente.set, Color);
                                Cliente.doc = Cliente.tp.getStyledDocument();
                                Cliente.doc.insertString(Cliente.doc.getLength(), "***** Ha entrado ", Cliente.set);
                                Cliente.sc.setBold(Cliente.set, true);
                                Cliente.doc = Cliente.tp.getStyledDocument();
                                Cliente.doc.insertString(Cliente.doc.getLength(), nick, Cliente.set);
                                Cliente.sc.setBold(Cliente.set, false);
                                Cliente.doc = Cliente.tp.getStyledDocument();
                                Cliente.doc.insertString(Cliente.doc.getLength(), " en el Chat. *****\n", Cliente.set);
                                Cliente.model.addElement(nick);
                                break;
                            case "bienvenido":
                                Cliente.color = Color;
                                Cliente.sc.setForeground(Cliente.set, Color);
                                Cliente.doc = Cliente.tp.getStyledDocument();
                                Cliente.doc.insertString(Cliente.doc.getLength(), "***** Bienvenido ", Cliente.set);
                                Cliente.sc.setBold(Cliente.set, true);
                                Cliente.doc = Cliente.tp.getStyledDocument();
                                Cliente.doc.insertString(Cliente.doc.getLength(), Client.nick, Cliente.set);
                                Cliente.sc.setBold(Cliente.set, false);
                                Cliente.doc = Cliente.tp.getStyledDocument();
                                Cliente.doc.insertString(Cliente.doc.getLength(), " al Chat. *****\n", Cliente.set);
                                if (!nick.equals("")) {
                                    String[] nicks = nick.split(",");

                                    for (int x = 0; x < nicks.length; x++) {
                                        Cliente.model.addElement(nicks[x]);
                                    }
                                }

                                break;
                            case "sale":
                                for (int x = 0; x < Cliente.nicks.size(); x++) {
                                    if (Cliente.nicks.get(x).equals(nick)) {
                                        JTextPane tp2 = Cliente.list.get(Cliente.nicks.indexOf(nick));
                                        Cliente.sc.setForeground(Cliente.set, Color);
                                        Cliente.doc = tp2.getStyledDocument();
                                        Cliente.doc.insertString(Cliente.doc.getLength(), "***** El usuario ", Cliente.set);
                                        Cliente.sc.setBold(Cliente.set, true);
                                        Cliente.doc = tp2.getStyledDocument();
                                        Cliente.doc.insertString(Cliente.doc.getLength(), nick, Cliente.set);
                                        Cliente.sc.setBold(Cliente.set, false);
                                        Cliente.doc = tp2.getStyledDocument();
                                        Cliente.doc.insertString(Cliente.doc.getLength(), " ha salido del chat. *****\n", Cliente.set);
                                        Cliente.model.removeElement(nick);
                                    }
                                }
                                Cliente.sc.setForeground(Cliente.set, Color);
                                Cliente.doc = Cliente.tp.getStyledDocument();
                                Cliente.doc.insertString(Cliente.doc.getLength(), "***** El usuario ", Cliente.set);
                                Cliente.sc.setBold(Cliente.set, true);
                                Cliente.doc = Cliente.tp.getStyledDocument();
                                Cliente.doc.insertString(Cliente.doc.getLength(), nick, Cliente.set);
                                Cliente.sc.setBold(Cliente.set, false);
                                Cliente.doc = Cliente.tp.getStyledDocument();
                                Cliente.doc.insertString(Cliente.doc.getLength(), " ha salido del chat. *****\n", Cliente.set);
                                Cliente.model.removeElement(nick);
                                break;

                            default:
                                Cliente.sc.setBold(Cliente.set, true);
                                Cliente.sc.setForeground(Cliente.set, Color);
                                Cliente.doc = Cliente.tp.getStyledDocument();
                                Cliente.doc.insertString(Cliente.doc.getLength(), nick, Cliente.set);
                                Cliente.sc.setBold(Cliente.set, false);
                                Cliente.doc = Cliente.tp.getStyledDocument();
                                Cliente.doc.insertString(Cliente.doc.getLength(), cad + "\n", Cliente.set);
                        }
                    }
                }

            } catch (BadLocationException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("Error en lectura");
                System.out.println(ex);
            }

            try {
                is.close();
                cli.close();
                System.out.println("Hilo y sus conexiones correctamente cerrados");
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    static class getFichero extends Thread {

        private String name;

        public getFichero(String name) {
            this.name = name;
        }

        public void run() {
            try {
                int filesize = 6022386; // filesize temporary hardcoded
                long start = System.currentTimeMillis();
                int bytesRead;
                int current = 0;
                Socket sock = new Socket("127.0.0.1", 8000);
                byte[] mybytearray = new byte[filesize];
                InputStream iss = sock.getInputStream();
                FileOutputStream fos = new FileOutputStream(name);
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
            } catch (IOException ex) {
                System.out.println("Error en lectura");
                System.out.println(ex);
            }



        }
    }
}
