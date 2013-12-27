/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Ivan
 */
public class Controller {

    public static Socket socket;
    public static Scanner scanner;
    public static PrintWriter writer;
    public static String myNick, oponentNick, currentPlayer;
    String host_address;
    int port;
    public static CommunicationThread thread;

    public void connect(String host, int port) {
        System.out.println("IP:" + host.toString() + "\nport: " + port);
        host_address = host.toString();
        this.port = port;
        try {
            System.out.println("im connect()");
            socket = new Socket(InetAddress.getByName(host.toString()), port);
            System.out.println("verbunden mit Server");
            MainGUI.label_status.setText("connected");
            MainGUI.label_status.setForeground(Color.GREEN);
            scanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());
            thread = new CommunicationThread();
            thread.start();
            sendMessage("NICK:", myNick);
            System.out.println("connect() verlassen");
        } catch (Exception ex) {
            System.out.println("Fehler: Client in connect():\n" + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Verbindung konnte nicht "
                    + "hergestellt werden");
        }
    }

    public void decode(String str) {
        System.out.println("im decode(): " + str);
        StringTokenizer st = new StringTokenizer(str, ":");
        String cmd = "", rest = "";

        cmd = st.nextToken();
        if (st.hasMoreTokens()) {
            rest = st.nextToken();
        }

        switch (cmd) {
            case "SET":
                if(MainGUI.label_player1_ist_dran.getText().equals("ist dran")) {
                    MainGUI.setField("rot.png", rest);
                }
                
                if(MainGUI.label_player2_ist_dran.getText().equals("ist dran")) {
                    MainGUI.setField("blau.png", rest);
                }
                
                break;

            case "CURRENT":
                currentPlayer = rest.toString();
                if (rest.equals(MainGUI.label_player1.getText().toString())) {
                    MainGUI.label_player1_ist_dran.setForeground(Color.BLUE);
                    MainGUI.label_player1_ist_dran.setText("ist dran");
                    MainGUI.label_player2_ist_dran.setText("");
                }
                if (rest.equals(MainGUI.label_player2.getText().toString())) {
                    MainGUI.label_player2_ist_dran.setForeground(Color.BLUE);
                    MainGUI.label_player2_ist_dran.setText("ist dran");
                    MainGUI.label_player1_ist_dran.setText("");
                }
                break;

            case "ERROR":
                JOptionPane.showMessageDialog(null, rest.toString());
                disconnect();
                reset();
                break;

            case "CHAT":
                MainGUI.ta_chat.append("\n" + rest + "\n");
                break;

            case "NICK1":
                MainGUI.label_player1.setText(rest.toString());
                break;

            case "NICK2":
                MainGUI.label_player2.setText(rest.toString());
                break;

            case "GAMEOVER":
                JOptionPane.showMessageDialog(null, rest.toString());
                int choice = JOptionPane.showConfirmDialog(null, "Neues Spiel?", null, 0, 3, null);
                if(choice == 0) { // wenn 'ja'
                    sendMessage("REMATCH:", "yes");
                }
                if(choice == 1) { // wenn 'nein'
                    sendMessage("REMATCH:", "no");
                }
                break;

            case "RESET":
                reset();
                break;

            case "DENIED":
                JOptionPane.showMessageDialog(null, "Die Spalte ist voll!");
                break;

            default:
                break;
        }
    }

    public void sendMessage(String cmd, String msg) {
        System.out.println("Im sendMessage():\n übergegebene String: " + msg.toString());
        try {
            writer.println(cmd.toString() + msg);
            writer.flush();
        } catch (Exception e) {
            System.out.println("Client: Exception in sendMessage():\n" + e.getMessage());
        }
    }

    public void reset() {
        System.out.println("im reset()");
             
        MainGUI.label_player1.setText("Player 1");
        MainGUI.label_player2.setText("Player 2");
        MainGUI.label_player1_ist_dran.setText("");
        MainGUI.label_player2_ist_dran.setText("");
        
        MainGUI.label_00.setIcon(null);
        MainGUI.label_01.setIcon(null);
        MainGUI.label_02.setIcon(null);
        MainGUI.label_03.setIcon(null);
        MainGUI.label_04.setIcon(null);
        MainGUI.label_05.setIcon(null);
        MainGUI.label_06.setIcon(null);
        MainGUI.label_10.setIcon(null);
        MainGUI.label_11.setIcon(null);
        MainGUI.label_12.setIcon(null);
        MainGUI.label_13.setIcon(null);
        MainGUI.label_14.setIcon(null);
        MainGUI.label_15.setIcon(null);
        MainGUI.label_16.setIcon(null);
        MainGUI.label_20.setIcon(null);
        MainGUI.label_21.setIcon(null);
        MainGUI.label_22.setIcon(null);
        MainGUI.label_23.setIcon(null);
        MainGUI.label_24.setIcon(null);
        MainGUI.label_25.setIcon(null);
        MainGUI.label_26.setIcon(null);
        MainGUI.label_30.setIcon(null);
        MainGUI.label_31.setIcon(null);
        MainGUI.label_32.setIcon(null);
        MainGUI.label_33.setIcon(null);
        MainGUI.label_34.setIcon(null);
        MainGUI.label_35.setIcon(null);
        MainGUI.label_36.setIcon(null);
        MainGUI.label_40.setIcon(null);
        MainGUI.label_41.setIcon(null);
        MainGUI.label_42.setIcon(null);
        MainGUI.label_43.setIcon(null);
        MainGUI.label_44.setIcon(null);
        MainGUI.label_45.setIcon(null);
        MainGUI.label_46.setIcon(null);
        MainGUI.label_50.setIcon(null);
        MainGUI.label_51.setIcon(null);
        MainGUI.label_52.setIcon(null);
        MainGUI.label_53.setIcon(null);
        MainGUI.label_54.setIcon(null);
        MainGUI.label_55.setIcon(null);
        MainGUI.label_56.setIcon(null);
        
        System.out.println("reset() verlassen");
    }
    
    public void disconnect() {
        System.out.println("im disconnect()");
        thread.stopped = true;
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("Exception beim Socket-Schließen\n" + ex.getMessage());
        }
        MainGUI.label_status.setForeground(Color.red); 
        MainGUI.label_status.setText("unconnected");
        System.out.println("disconnect() verlassen");
    }
    

    private class CommunicationThread extends Thread {

        String msg;
        public boolean stopped = false;
        
        public void run() {
            System.out.println("im run()");
            while (stopped == false) {
                try {
                    msg = scanner.nextLine().toString();
                    System.out.println("empfangen: " + msg.toString());
                    decode(msg);
                } catch (Exception e) {
                    System.out.println("Fehler in CommThread beim lesen\n" + e.getMessage());
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Verbinndung mit Server unterbrochen!");
            disconnect();
            reset();
        }
    }
}
