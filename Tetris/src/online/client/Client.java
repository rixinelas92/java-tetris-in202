/**
 * This class was designed in order to define the parameters of the protocol that
 * allows the comunication in the network when the game is configured for two 
 * players.
 */
package online.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.PriorityQueue;
import java.util.Queue;
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
abstract public class Client extends Thread {

    Socket socket;
    BufferedReader br;
    OutputStreamWriter os;
    final String playername;

    /**
     * Defines a standard/protocol to manage the network
     * @param str default parameter of the function main. 
     */
    public static void main(String[] str) {
        try {
            Client c = new ClientTest("localhost","ppp1");
            Thread.sleep(1000);
            Client c2 = new ClientTest("localhost","ppp2");

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
    /**
     * Defines parameters to the client in the network and the basic 
     * @param serverAddress defines a port of comunication.
     * @param playername identifies the player in the network.
     * @throws UnknownHostException if a sever adress is not well identified.
     * @throws IOException if a client cannot be created or list is not avaliable.
     */
    public Client(String serverAddress, String playername) throws UnknownHostException, IOException {
        queueToSend = new PriorityQueue<String>();
        Socket s = new Socket(serverAddress, 4779);
        this.playername = playername;
        InputStreamReader is = new InputStreamReader(s.getInputStream());
        br = new BufferedReader(is);
        os = new OutputStreamWriter(s.getOutputStream());
        createClient(playername);
        requestList();
    }
    /**
     * Refreshes the informations sent to the client, allowing that the comunication
     * arrives without problems of delay.
     */
    private void flush(){
        try {
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Queue<String> queueToSend;
    /**
     * Puts a message in the queue, organizing the messages.
     * @param str defines the message to be sent.
     * @throws IOException 
     */
    protected void send(String str) {
        
        synchronized(queueToSend){
            queueToSend.add(str+"\n");
        }
    }
    /**
     * Manages the channel, sending all the messages that was in the queue following 
     * its previous order.
     */
    private void sendAllInQueue(){
        synchronized(queueToSend){
            while(!queueToSend.isEmpty()){
                String str = queueToSend.poll();
                try{
                    
                    os.write(str);
                }catch (Exception e){}
            }
            try{
                os.flush();
            }catch (Exception e){}
        }
    }
    @Override
    public void run() {
        try {
            int timeout = 0;
            boolean barN = false;
            int counter = 0;
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
                int[] board = returnBoard();

                if(counter%5 == 2){
                    if(board.length > 0)
                        sendBoard(board);
                }
                if(counter++ > 20){
                 
                    send(PlayerQueryCodes.ALIVE+"");
                    counter = 0;
                }
                sendAllInQueue();
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
    /**
     * Treats a string passed like parameter and executes a fonction in according 
     * to the request.
     * @param q informs the message of request. 
     * @throws IOException if some of the cosume fonction cannot handle the request.
     */
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
             case BOARD:
                 receiveBoard(query[1]);
            default:
                break;
        }
    }
    /**
     * Receives
     * @param list default of the method.
     */
    abstract public  void receivePlayerList(String list);

    abstract public void receiveMatchRequest(String uid) throws IOException;

    abstract public void matchStart(String mid);

    abstract public void receiveGamePunn(String mid);

    abstract public void endGame(String mid) throws IOException;

    abstract public void receivedError();
    
    abstract public void receiveBoard(String board);

    /**
     * Methods
     */
    /**
     * Sends a message to create a new client in the network.
     * @param playername defines the identity of the player.
     * @throws IOException default of the method.
     */
    public void createClient(String playername) throws IOException{
        send(PlayerQueryCodes.CREATECLIENT+" "+Player.validName(playername));
    }
    /**
     * Sends a message to obtain a list of players avaliables.
     * @throws IOException default of the method.
     */
    public void requestList() throws IOException{
        send(PlayerQueryCodes.PLIST.toString());
    }
    /**
     * Sends a message to begin a match against a determined player.
     * @param playerId number that identifies a player in the network.
     * @throws IOException default of the method.
     */
    public void requestMatchWith(String playerId) throws IOException{
        send(PlayerQueryCodes.MATCHREQ+" "+playerId);
    }
    /**
     * Sends a message informing that a competition between the two players was 
     * accepted.
     * @param playerId number that identifies a player in the network.
     * @throws IOException default of the method.
     */
    public void acceptMatchWith(String playerId) throws IOException{
        send(PlayerQueryCodes.MATCHACCEPT+" "+playerId);
    }
    /**
     * Sends a message informing if points were marked by a player.
     * @throws IOException default of the method.
     */
    public void gamePoint() throws IOException{
        send(PlayerQueryCodes.GAMEPOINT+"");
    }
    /**
     * Sends a message informing if it is the end of the game.
     * @throws IOException default of the method.
     */
    public void gameOver() throws IOException{
        send(PlayerQueryCodes.GAMEOVER+"");
    }

    public void sendBoard(int[] board) throws IOException{
        StringBuilder sb = new StringBuilder();
        sb.append(PlayerQueryCodes.BOARD);

        sb.append(" ");
        for(int i: board){
            sb.append(i);
            sb.append("#");
        }
        send(sb.toString());
    }
    
    public void sayBye(){
            send("\n\n");
    }

    private void cleanServer() {
        try{
            send("\n\n");
        }catch(Exception e){

        }
    }

    protected abstract int[] returnBoard();



    static public class ClientTest extends Client {

        public ClientTest(String serverAddress, String playername) throws UnknownHostException, IOException {
            super(serverAddress,playername);
        }

        public void receivePlayerList(String list) {
            list = list.trim();
            String[] players = list.split("#");
            System.out.println(" no \t| Player \t| State");
            for (String s : players) {
                String[] data = s.split(">");
                if (data.length < 3) {
                    continue;
                }
                int key = Integer.valueOf(data[0]);
                String name = data[1];
                PlayerState state = PlayerState.valueOf(data[2]);
                System.out.println(" " + key + " \t| " + name + " \t| " + state);
            }
        }

        public void receiveMatchRequest(String uid) throws IOException {
            System.out.println("MATCHREQUESTED FROM " + uid);
        }

        public void matchStart(String mid) {
            System.out.println(" MATCH STARTED WITH ID=" + mid);
        }

        public void receiveGamePunn(String mid) {
            System.out.println(" Game punnition from match: " + mid);
        }

        public void endGame(String mid) throws IOException {
            System.out.println(" Game over: " + mid);
            send("\n\n");
        }

        public void receivedError() {
            System.out.println("ERROR!");
        }

        @Override
        public void receiveBoard(String board) {
            System.out.println(" Received Board: " + board);
        }

        @Override
        protected int[] returnBoard() {
            int[] board = new int[10];
            for(int i = 0;i<10;i++){
                board[i] = i;
            }
            return board;
        }
    }
}
