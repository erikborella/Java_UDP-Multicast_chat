/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.html.HTMLDocument;
import multicast.Receiver;
import multicast.Sender;
import utils.JsonableMessage;

/**
 *
 * @author erik0
 */
public class ChatView extends javax.swing.JFrame {
    private String address = "230.0.0.0";
    private int port = 50000;
    
    private String username;
    
    private Sender sender;
    private Receiver receiver;
   
    public ChatView() {
        initComponents();
    }
    
    public ChatView(String username) {
        initComponents();
        this.username = username;
        
        initMulticast();
       
        this.sendEnterMessage(); 
    }

    private void initMulticast() {
        try {
            this.sender = new Sender(address, port);
            this.receiver = new Receiver(address, port);
        } catch (Exception e) {
            showException(e);
        }
        
        receiver.startListen(
                (message) -> { showMessage(message); }, 
                (e) -> { showException(e); }
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messagePanel = new javax.swing.JPanel();
        messageScrollPanel = new javax.swing.JScrollPane();
        messageTextPane = new javax.swing.JTextPane();
        messageTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        changeAdressButton = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Multicast Chat: Chat");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        messagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Messages"));

        messageTextPane.setEditable(false);
        messageTextPane.setContentType("text/html"); // NOI18N
        messageScrollPanel.setViewportView(messageTextPane);

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageScrollPanel)
                .addContainerGap())
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(messagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addContainerGap())
        );

        messageTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageTextFieldKeyPressed(evt);
            }
        });

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        jMenu1.setText("Settings");

        changeAdressButton.setText("Change address");
        changeAdressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeAdressButtonActionPerformed(evt);
            }
        });
        jMenu1.add(changeAdressButton);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(messagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(messageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        sendMessage();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void changeAdressButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeAdressButtonActionPerformed
        address = JOptionPane.showInputDialog(
                this, "Type our new multicast group (224.0.0.0 - 239.255.255.255):", this.address);
        
        port = Integer.parseInt(
            JOptionPane.showInputDialog(
                    this, "Type our new port: ", this.port));
        
        this.sendExitMessage();
        
        this.sender.destroy();
        this.receiver.destroy();
        
        initMulticast();
        
        messageTextPane.setText("");
        this.sendEnterMessage();
    }//GEN-LAST:event_changeAdressButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        sendExitMessage();
    }//GEN-LAST:event_formWindowClosing

    private void messageTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageTextFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            sendMessage();
    }//GEN-LAST:event_messageTextFieldKeyPressed

    private void sendEnterMessage() {
        try {
            this.sender.send(String.format("<strong>%s Entered!!</strong>", this.username));
        } catch (IOException e) {
            showException(e);
        }
    }
    
    private void sendExitMessage() {
        try {
            this.sender.send(String.format("<strong>%s Left!!</strong>", this.username));
        } catch (IOException e) {
            showException(e);
        }
    }
    
    private void sendMessage() {
        String message = messageTextField.getText();
        
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Type some message!", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JsonableMessage jm = new JsonableMessage(username, message);
        
        try {
            sender.send(jm.toJson());
            messageTextField.setText("");
        } catch (Exception e) {
            showException(e);
        }
    }
    
    private void showException(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    
    private void showMessage(String message) {
        String output = "";
        
        try {
            JsonableMessage jm = JsonableMessage.fromJson(message);
            output = generateMessage(jm);
        } catch (Exception e) {
            output = generateMessage(message);
        }
        
        HTMLDocument document = 
                (HTMLDocument) messageTextPane.getStyledDocument();
        
        try {
            document.insertAfterEnd(
                    document.getCharacterElement(document.getLength()),
                    output
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        scrollDown();
    }

    private String generateMessage(JsonableMessage jm) {
        StringBuilder output = new StringBuilder();
        
        output.append(String.format("<small>%s</small><br>", jm.getUsername()));
        output.append(String.format("<strong><big>%s</big></strong><br>", jm.getMessage()));
        output.append(String.format("<small>%s : %s</small><br>", jm.getDate(), jm.getTime()));
        output.append("-------------------------<br>");
        
        return output.toString();
    }
    
    private String generateMessage(String s) {
        StringBuilder output = new StringBuilder();
        
        output.append(s).append("<br>");
        output.append("-------------------------<br>");
        
        return output.toString();
    }
    
    private void scrollDown() {
        messageTextPane.selectAll();
        int x = messageTextPane.getSelectionEnd();
        messageTextPane.select(x, x);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem changeAdressButton;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JScrollPane messageScrollPanel;
    private javax.swing.JTextField messageTextField;
    private javax.swing.JTextPane messageTextPane;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
