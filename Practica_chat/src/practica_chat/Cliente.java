/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Lucia Tortosa
 */
public class Cliente extends javax.swing.JFrame {

    static SimpleAttributeSet set = new SimpleAttributeSet();
    static StyleConstants sc = null;
    static Document doc = null;
    static DefaultListModel model;
    protected static ArrayList<String> nicks = new ArrayList<String>();
    private static Dimension closeButtonSize;
    private static ImageIcon closeXIcon = new ImageIcon("./src/Image/button.gif");
    private int tabCounter = 0;
    public static Color color = null;
    protected static ArrayList<JTextPane> list = new ArrayList<JTextPane>();
    protected String to = null;

    public Cliente() {
        model = new DefaultListModel();
        model.addElement("Todos");
        nicks.add("Todos");
        initComponents();
        setTitle(Client.nick);
        ta2.setLineWrap(true);
        AbstractDocument pDoc=(AbstractDocument)ta2.getDocument();
        pDoc.setDocumentFilter(new DocumentSizeFilter(500));
       
        closeButtonSize = new Dimension(closeXIcon.getIconWidth() / 5, closeXIcon.getIconHeight() / 5);
        list.add(tp);

        JMenuBar mb = new JMenuBar();
        JMenu mFile = new JMenu("File");
        JMenuItem mi = new JMenuItem("Guardar");
        ActionListener guardar = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    guardar();
                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        mi.addActionListener(guardar);
        mFile.add(mi);
        mb.add(mFile);
        setJMenuBar(mb);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tp = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        ta2 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        List = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aloha");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setToolTipText("Todos");

        jScrollPane2.setViewportView(tp);

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        ta2.setColumns(20);
        ta2.setRows(5);
        jScrollPane3.setViewportView(ta2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 25, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Todos", jPanel1);

        List.setModel(model);
        List.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(List);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabbedPane)))
                .addContainerGap())
        );

        tabbedPane.getAccessibleContext().setAccessibleName("Todos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String cad = ta2.getText();
        ta2.setText("");
        try {
            if (!jLabel1.getText().equals("")) {
                String nick = jLabel1.getText().substring(4);
                Client.dos.writeUTF("To:       " + cad);
                Client.dos.writeUTF(nick);
                
                Cliente.sc.setBold(Cliente.set, true);
                Cliente.sc.setForeground(Cliente.set, color);
                Cliente.doc = tp.getStyledDocument();
                Cliente.doc.insertString(Cliente.doc.getLength(), "Dices a " + to +": ", Cliente.set);
                Cliente.sc.setBold(Cliente.set, false);
                Cliente.doc = tp.getStyledDocument();
                Cliente.doc.insertString(Cliente.doc.getLength(), cad + "\n", Cliente.set);
                jLabel1.setText("");
            } else {
                Client.dos.writeUTF(cad);
                Client.dos.writeUTF("Todos");
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Client.tanca = true;
        try {
            Client.dos.writeUTF("/closedpassword");
            Client.dos.writeUTF("Todos");
            Client.os.close();
            Client.cli.close();
            System.out.println("Conexiones correctamente cerradas");
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_formWindowClosing

    private void ListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListMouseClicked
        List = (JList) evt.getSource();
        int index = List.locationToIndex(evt.getPoint());
        if (evt.getClickCount() == 2) {
            System.out.println("Doble clic" + model.getElementAt(index));
            if (!nicks.contains(model.getElementAt(index).toString())) {
                add(model.getElementAt(index).toString());
                nicks.add(model.getElementAt(index).toString());
            }

        } else if (evt.getClickCount() == 1 && tabbedPane.getSelectedIndex() == 0) {   
            jLabel1.setText("To: " + model.getElementAt(index).toString());
            to = model.getElementAt(index).toString();
            
            if (index == 0){
                jLabel1.setText("");
            }
        }
    }//GEN-LAST:event_ListMouseClicked

    public static void add(final String nick) {
        final JPanel content = createPane(nick);
        JPanel tab = new JPanel();

        tab.setOpaque(false);

        JLabel tabLabel = new JLabel(nick);

        JButton tabCloseButton = new JButton(closeXIcon);
        tabCloseButton.setPreferredSize(closeButtonSize);
        tabCloseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int closeTabNumber = tabbedPane.indexOfComponent(content);
                list.remove(nicks.indexOf(nick));
                tabbedPane.removeTabAt(closeTabNumber);

                nicks.remove(nick);


            }
        });

        tab.add(tabLabel, BorderLayout.WEST);
        tab.add(tabCloseButton, BorderLayout.EAST);

        tabbedPane.addTab(null, content);
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tab);
    }

    public void guardar() throws IOException {

        JFileChooser archivos = new JFileChooser();

        BufferedWriter bw = null;
        File f;
        int resp;

        resp = archivos.showSaveDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            f = archivos.getSelectedFile();
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(list.get(tabbedPane.getSelectedIndex()).getText());
            bw.close();
        }
    }

    public static JPanel createPane(String cad) {
        final String nick = cad;
        JPanel p = new JPanel();
        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        final JTextPane jTextPane1 = new javax.swing.JTextPane();
        JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        final JTextArea jTextArea1 = new javax.swing.JTextArea();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();
        jTextArea1.setLineWrap(true);
                AbstractDocument pDoc=(AbstractDocument)jTextArea1.getDocument();
        pDoc.setDocumentFilter(new DocumentSizeFilter(500));
        jScrollPane1.setViewportView(jTextPane1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jButton1ActionPerformed(evt);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void jButton1ActionPerformed(ActionEvent evt) throws BadLocationException {

                try {
                    Client.dos.writeUTF("/private :" + jTextArea1.getText());
                    Client.dos.writeUTF(nick);
   
                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                Cliente.sc.setBold(Cliente.set, true);
                Cliente.sc.setForeground(Cliente.set, color);
                Cliente.doc = jTextPane1.getStyledDocument();
                Cliente.doc.insertString(Cliente.doc.getLength(), Client.nick, Cliente.set);
                Cliente.sc.setBold(Cliente.set, false);
                Cliente.doc = jTextPane1.getStyledDocument();
                Cliente.doc.insertString(Cliente.doc.getLength(), jTextArea1.getText() + "\n", Cliente.set);
                jTextArea1.setText("");
            }
        });

        jButton2.setText("Adjuntar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    try {
                        jButton2ActionPerformed(evt);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void jButton2ActionPerformed(ActionEvent evt) throws FileNotFoundException, IOException, BadLocationException {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(chooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    Cliente.sc.setBold(Cliente.set, true);
                    Cliente.sc.setForeground(Cliente.set, color);
                    Cliente.doc = jTextPane1.getStyledDocument();
                    Cliente.doc.insertString(Cliente.doc.getLength(), Client.nick, Cliente.set);
                    Cliente.sc.setBold(Cliente.set, false);
                    Cliente.doc = jTextPane1.getStyledDocument();
                    Cliente.doc.insertString(Cliente.doc.getLength(), " ***Enviando archivos " + chooser.getSelectedFile().getName() + "***\n", Cliente.set);
                    jTextArea1.setText("");
                    try {
                        Client.dos.writeUTF("/Send :   " + chooser.getSelectedFile().getName());
                        Client.dos.writeUTF(nick);
                    } catch (IOException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int filesize = 6022386; // filesize temporary hardcoded

                    long start = System.currentTimeMillis();
                    int bytesRead;
                    int current = 0;

                    // localhost for testing
                    Socket sock = new Socket("127.0.0.1", 8000);
                    System.out.println("Connecting...");

                    File myFile = new File(chooser.getSelectedFile().getAbsolutePath());
                    byte[] mybytearray = new byte[(int) myFile.length()];
                    FileInputStream fis = new FileInputStream(myFile);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    bis.read(mybytearray, 0, mybytearray.length);
                    OutputStream os = sock.getOutputStream();
                    System.out.println("Sending...");
                    os.write(mybytearray, 0, mybytearray.length);
                    os.flush();
                    sock.close();

                }
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(p);
        p.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jButton2)
                .addComponent(jButton1)))
                .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        p.setToolTipText(nick);
        list.add(jTextPane1);
        return p;
    }
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList List;
    protected static javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    protected static javax.swing.JTextArea ta2;
    protected static javax.swing.JTabbedPane tabbedPane;
    public static javax.swing.JTextPane tp;
    // End of variables declaration//GEN-END:variables
}


class DocumentSizeFilter extends DocumentFilter {

   private int max_Characters;
   private boolean DEBUG;

   public DocumentSizeFilter(int max_Chars) {

      max_Characters = max_Chars;
      DEBUG = false;
   }

   public void insertString(DocumentFilter.FilterBypass fb
                            , int offset
                              , String str
                                , AttributeSet a) 
   throws BadLocationException {

      if (DEBUG) {

         System.out.println("In DocumentSizeFilter's insertString method");
      }

      if ((fb.getDocument().getLength() + str.length()) <= max_Characters) 
         super.insertString(fb, offset, str, a);
      else 
         Toolkit.getDefaultToolkit().beep();
   }

   public void replace(DocumentFilter.FilterBypass fb
                       , int offset, int length
                       , String str, AttributeSet a)
   throws BadLocationException {

      if (DEBUG) {

         System.out.println("In DocumentSizeFilter's replace method");
      }
      if ((fb.getDocument().getLength() + str.length()
           - length) <= max_Characters) 
         super.replace(fb, offset, length, str, a);
      else
         Toolkit.getDefaultToolkit().beep();
   }
}
