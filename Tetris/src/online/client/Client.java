/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package online.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import online.util.ConnectionCodes.PlayerQueryCodes;
import online.util.ConnectionCodes.ServerQueryCodes;
import online.util.Player;
import online.util.Player.PlayerState;

/**
 *
 * @author gustavo
 */
public class Client extends Thread {

    Socket socket;
    BufferedReader br;
    OutputStreamWriter os;
    final String playername;

    public static void main(String[] str) {
        try {
            Client c = new Client("localhost","ppp1");
            Thread.sleep(1000);
            Client c2 = new Client("localhost","ppp2");

            c.start();
            c2.start();


            c.flush();
            c2.flush();

            Thread.sleep(1000);
            c2.requestMatchWith("0");
            Thread.sleep(1000);
            c.acceptMatchWith("1");
            Thread.sleep(1000);
            c2.gamePoint();
            Thread.sleep(1000);
            c.gamePoint();
            Thread.sleep(1000);
            c2.gamePoint();
            Thread.sleep(1000);
            c2.gameOver();

        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Client(String serverAddress, String playername) throws UnknownHostException, IOException {
        Socket s = new Socket(serverAddress, 4779);
        this.playername = playername;
        InputStreamReader is = new InputStreamReader(s.getInputStream());
        br = new BufferedReader(is);
        os = new OutputStreamWriter(s.getOutputStream());
        createClient(playername);
        requestList();
    }


    private void flush(){
        try {
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void send(String str) throws IOException{
        os.write(str+"\n");
        os.flush();
    }
    
    @Override
    public void run() {
        try {
            int timeout = 0;
            boolean barN = false;

            while (timeout < 20000) {
                try {
                    sleep(100);
                } catch (InterruptedException e1) {
                }
                timeout++;

                if (br.ready()) {
                    String q = br.readLine();
                    q = q.trim();
                    /**
                     * Detects two lines and terminates connection if they appear
                     */
                    if (q.length() == 0) { // connection terminated;
                        if (barN) {
                            break;
                        }
                        barN = true;
                    } else {
                        barN = false;
                        timeout = 0;
                        consume(q);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanServer();
            try {
                br.close();
            } catch (Exception e) {
            }
            try {
                os.close();
            } catch (Exception e) {
            }
        }


    }

    private void consume(String q) throws IOException {
        String[] query = q.split("\\s");
        if(query.length < 2)
            return;
        ServerQueryCodes code = null;
        try{
            code = ServerQueryCodes.valueOf(query[0].toUpperCase());
        }catch(Exception e){
            e.printStackTrace();
        }


         switch(code){
            case PLIST:
                receivePlayerList(query[1]);
                break;
            case MATCHREQ:
                receiveMatchRequest(query[1]);
                break;
            case MATCHSTART:
                matchStart(query[1]);
                break;
            case GAMEPUNN:
                receiveGamePunn(query[1]);
                break;
            case ENDGAME:
                endGame(query[1]);
                break;
            case ERROR:
                receivedError();
                break;
            default:
                break;
        }
    }
/*****************
 * RECEIVERS
 */

    private void receivePlayerList(String list) {
            list = list.trim();
            String[] players = list.split("#");
            System.out.println(" no \t| Player \t| State");
            for(String s: players){
                String[] data = s.split(">");
                if(data.length < 3)
                    continue;
                int key = Integer.valueOf(data[0]);
                String name = data[1];
                PlayerState state = PlayerState.valueOf(data[2]);
                System.out.println(" "+key+" \t| "+name+" \t| "+state);
            }
    }

    private void receiveMatchRequest(String uid) throws IOException {
        System.out.println("MATCHREQUESTED FROM "+uid);
    }

    private void matchStart(String mid){
        System.out.println(" MATCH STARTED WITH ID="+mid);
    }

    private void receiveGamePunn(String mid){
        System.out.println(" Game punnition from match: "+mid);
    }

    private void endGame(String mid) throws IOException{
        System.out.println(" Game over: "+mid);
        send("\n\n");
    }

    private void receivedError(){
        System.out.println("ERROR!");
    }
/*****************
 * RECEIVERS - END
 */


/*****************
 * METHODS
 */
    public void createClient(String playername) throws IOException{
        send(PlayerQueryCodes.CREATECLIENT+" "+Player.validName(playername));
    }
    public void requestList() throws IOException{
        send(PlayerQueryCodes.PLIST.toString());
    }
    public void requestMatchWith(String playerId) throws IOException{
        send(PlayerQueryCodes.MATCHREQ+" "+playerId);
    }
    public void acceptMatchWith(String playerId) throws IOException{
        send(PlayerQueryCodes.MATCHACCEPT+" "+playerId);
    }

    public void gamePoint() throws IOException{
        send(PlayerQueryCodes.GAMEPOINT+"");
    }

    public void gameOver() throws IOException{
        send(PlayerQueryCodes.GAMEOVER+"");
    }

    private void cleanServer() {
        try{
            send("\n\n");
        }catch(Exception e){

        }
    }
}
