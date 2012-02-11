package online.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import online.server.GUI.SIM;
import online.util.ConnectionCodes.PlayerQueryCodes;
import online.util.ConnectionCodes.ServerQueryCodes;
import online.util.Match;
import online.util.Player;

/**
 *
 * @author gustavo
 */
/**
 * This class was designed in order to creat the bases of a computer network. It
 * determines the parameters of comunication and manages the informations that are
 * sent by the players.
 */

public class Server extends Thread {

    static int playerCount = 0;
    static int matchCount = 0;
    static HashMap<Integer, Player> playerMap;
    static HashMap<Integer, Match> matchMap;
    Queue<String> queueToSend;

    /**
     * It handles the socket(channel) of comunication. 
     * @param args argument by default of the function main.
     */
    public static void main(String[] args) {
        ServerSocket ss = null;
        SIM.main(new String[0] );
        try {
            ss = new ServerSocket(4779);
            System.out.println("host ");
        } catch (java.net.BindException ex) {
            ex.printStackTrace();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        playerMap = new HashMap<Integer, Player>();
        matchMap = new HashMap<Integer, Match>();

        if (ss == null) {
            System.out.println("Error, could not bind to port 4779.");
        } else {
            while (true) {
                try {
                    /*
                     * Waits until a connection is required.
                     */
                    Socket connection = ss.accept();
                    Server server = new Server(connection);
                    server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    private Socket clientConnection;
    private Player player;
    OutputStreamWriter os = null;
    BufferedReader br = null;

    /**
     * Default setter of the parameter <em>clientConnection</em> and promotes the
     * organization of priority of sending priority.
     * @param clientConnection defines the status of the player conection. 
     */
    public Server(Socket clientConnection) {
        this.clientConnection = clientConnection;
        queueToSend = new PriorityQueue<String>();
    }

    @Override
    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            os = new OutputStreamWriter(clientConnection.getOutputStream());

            int timeout = 0;
            boolean barN = false;

            int counter = 0;
            while (timeout < 2000) {
                try {
                    sleep(100);
                } catch (InterruptedException e1) {
                }
                timeout++;
                if (clientConnection.isClosed()) {
                    break;
                }
                if (!clientConnection.isConnected()) {
                    break;
                }
                if (br.ready()) {
                    String q = br.readLine();
                    q = q.trim();
                    /**
                     * Detects two lines and terminates connection if they appear
                     */
                    if (q.length() == 0) { // connection terminated;
                        if (barN) {
                            checkIfThereIsAMatchAndDecline();
                            break;
                        }
                        barN = true;
                    } else {
                        barN = false;
                        timeout = 0;
                        if(player != null){
                            Match m = player.getMatch();
                            if(m != null){
                                m.signalInstrospection(player.getName()+" ("+player.getPlayerId()+")" + q);
                            }
                        }

                        consume(q);
                    }

                }
                sendAllInQueue();
                if (counter++ > 10) {
                    counter = 0;
                    sendPlayerList();
                    SIM.getInstance().setUserList(playerMap);
                    SIM.getInstance().setMatchList(matchMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SIM.getInstance().setUserList(playerMap);
            SIM.getInstance().setMatchList(matchMap);
            try {
                cleanMatch(player.getMatch());
            } catch (Exception e) {
            }
            try {
                cleanClient();
            } catch (Exception e) {
            }
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
     * It terminates the channel of comunication in which the clients was sending
     * the messages.
     */
    private void cleanClient() {
        try {
            send("\n\n");
        } catch (Exception e) {
        }

        try {
            Match m = player.getMatch();
            Player other = m.getOtherPlayer(player.getPlayerId());
            other.getServer().send("\n\n");
        } catch (Exception e) {
        }

        try {
            Match m = player.getMatch();
            matchMap.remove(m.getMatchid());
            m.detachInstrospector();
            SIM.getInstance().removeMatch(m);

        } catch (Exception e) {
        }

        try {
            playerMap.remove(player.getPlayerId());
            SIM.getInstance().removePlayer(player);
        } catch (Exception e) {
        }

        try {
            clientConnection.close();
        } catch (Exception e) {
        }
    }

    /**
     * Resets the mapping of the game in the network.
     * @param m defines the match.
     */
    static private void cleanMatch(Match m) {
        if(m == null)
            return;
        try {
            matchMap.remove(m.getMatchid());
            m.detachInstrospector();
            SIM.getInstance().removeMatch(m);
            for (int i : m.getPlayersIds()) {
                Player p = playerMap.get(i);
                if(p == null)
                    continue;
                Match m2 = p.getMatch();
                if(m2 == null) 
                    continue;
                if(m2.getMatchid() == m.getMatchid()){
                    p.setMatch(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        if (query.length < 1) {
            return;
        }
        PlayerQueryCodes code = null;
        try {
            code = PlayerQueryCodes.valueOf(query[0].toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (code == null) {
            return;
        }

        switch (code) {
            case CREATECLIENT:
                createClient(this, query[1]);
                break;
            case PLIST:
                sendPlayerList();
                break;
            case MATCHREQ:
                requestMatchWith(query[1]);
                break;
            case MATCHACCEPT:
                acceptMatch(query[1]);
                break;
            case GAMEPOINT:
                sendGamePoint();
                break;
            case GAMEOVER:
                sendGameOver();
                break;
            case MATCHDECLINE:
                sendMatchDeclined(query[1]);
                break;
            case BOARD:
                sendBoard(query[1]);
            default:
                break;
        }

    }

    /**
     * It puts the next message to be send in the queue of priority. 
     * @param str informs the message to be send.
     */
    private void send(String str) {
        synchronized (queueToSend) {
            queueToSend.add(str + "\n");
        }
    }

    /**
     * Manages the channel, sending all the messages that was in the queue following 
     * its previous order.
     */
    private void sendAllInQueue() {
        synchronized (queueToSend) {

            while (!queueToSend.isEmpty()) {

                String str = queueToSend.poll();
                try {

                    os.write(str);
                } catch (Exception e) {
                    cleanClient();
                }
            }
            try {
                os.flush();
            } catch (Exception e) {
                cleanClient();
            }
        }
    }

    /**
     * Sends a message with all the players avaliables to the network game.
     */
    private void sendPlayerList() {
        StringBuilder sb = new StringBuilder();
        sb.append(ServerQueryCodes.PLIST.toString());
        sb.append(" ");
        for (Entry<Integer, Player> entry : playerMap.entrySet()) {
            if (entry.getKey().equals(this.player.getPlayerId())) {
                continue;
            }
            sb.append(entry.getKey());
            sb.append(">");
            sb.append(entry.getValue().getName());
            sb.append(">");
            sb.append(entry.getValue().getState());
            sb.append("#");
        }
        send(sb.toString());
    }

    /**
     * Creats a new client in the network, configuring the server to manage it.
     * @param s indicates the server of the network.
     * @param name indicates the indentification of the player.
     */
    synchronized static private void createClient(Server s, String name) {
        int pid = playerCount++;
        s.player = new Player(name, s, pid);
        playerMap.put(pid, s.player);
    }
    /**
     * Initialites a new game between two players on line, setting the network
     * parameters of the network required to a match.
     * @param p1 defines one player.
     * @param p2 defines the second one player.
     */
    synchronized static private void createMatch(Player p1, Player p2) {
        int mid = matchCount++;

        Match m = new Match(p1, p1.getServer(), p2, p2.getServer(), mid);

        matchMap.put(mid, m);
        SIM.getInstance().addMatch(m);

        p1.getServer().send(ServerQueryCodes.MATCHSTART + " " + mid);
        p2.getServer().send(ServerQueryCodes.MATCHSTART + " " + mid);
        p1.setState(Player.PlayerState.PLAYING);
        p2.setState(Player.PlayerState.PLAYING);

    }
    /**
     * Determines if the match with a player required is possible or not. If the
     * selected player is not avaliable (off line) a error message is sent, otherwise
     * the request to the server is done.
     * @param idStr informs the index of the player in a list(map). 
     * @throws IOException 
     */
    private void requestMatchWith(String idStr) throws IOException {
        Integer id = -1;
        try {
            id = Integer.valueOf(idStr);
            Player opp = playerMap.get(id);
            if (opp.getState() == Player.PlayerState.ONLINE) {
                opp.getServer().requestFrom(this.player);
            } else {
                id = -1;
            }
        } catch (NumberFormatException e) {
        }
        if (id == -1) {
            send(ServerQueryCodes.ERROR + " " + PlayerQueryCodes.MATCHREQ + "#" + idStr);
        }
    }
    /**
     * Determines if the match with a player required is possible or not. If the
     * selected player is not avaliable (off line) a error message is sent, otherwise
     * the request to create a match is done.
     * @param idStr informs the index of the player in a list(map).
     * @throws IOException 
     */
    private void acceptMatch(String idStr) throws IOException {
        Integer id = -1;
        try {
            id = Integer.valueOf(idStr);
            Player opp = playerMap.get(id);
            if (opp.getState() == Player.PlayerState.ONLINE) {
                createMatch(this.player, opp);
            } else {
                id = -1;
            }
        } catch (NumberFormatException e) {
        } finally {
            if (id == -1) {
                send(ServerQueryCodes.ERROR + " " + PlayerQueryCodes.MATCHACCEPT + "#" + idStr);
            }
        }
    }
    /**
     * Sends a message informing the request of a player to a new match.
     * @param player informs the concerned player.
     * @throws IOException 
     */
    private void requestFrom(Player player) throws IOException {
        send(ServerQueryCodes.MATCHREQ + " " + player.getPlayerId());
    }
    /**
     * Informes if a point in the game happened to the server and makes a 
     * punishment to the opponent.
     * @throws IOException 
     */
    private void sendGamePoint() throws IOException {
        Match m = player.getMatch();
        int playerid = player.getPlayerId();
        if (m == null) {
            System.err.println("m == null " + playerid);
            return;
        }
        int matchid = m.getMatchid();
        m.getOtherServer(playerid).send(ServerQueryCodes.GAMEPUNN + " " + matchid);
    }
    /**
     * Informes if a the game over in the game happened to the server.
     * @throws IOException 
     */
    private void sendGameOver() throws IOException {
        Match m = player.getMatch();
        if (m == null) {
            return;
        }
        m.getPlayerWithid(m.getPlayersIds()[0]).setState(Player.PlayerState.ONLINE);
        m.getPlayerWithid(m.getPlayersIds()[1]).setState(Player.PlayerState.ONLINE);
        m.getOtherServer(player.getPlayerId()).send(ServerQueryCodes.ENDGAME + " " + m.getMatchid());
        System.out.println("sending "+ServerQueryCodes.ENDGAME + " " + m.getMatchid() + " to: "+player.getPlayerId());
        cleanMatch(m);
    }
    /**
     * Informes to the server if a player does not accepted the game in the network.
     * @param idStr informs the index of the player in a list(map).
     * @throws IOException 
     */
    private void sendMatchDeclined(String idStr) throws IOException {
        Integer id = -1;
        try {
            id = Integer.valueOf(idStr);
            Player opp = playerMap.get(id);
            if (opp.getState() == Player.PlayerState.ONLINE) {
                opp.getServer().send(ServerQueryCodes.MATCHDECLINED + " " + player.getPlayerId());
            } else {
                id = -1;
            }
        } catch (NumberFormatException e) {
        } finally {
            if (id == -1) {
                send(ServerQueryCodes.ERROR + " " + ServerQueryCodes.MATCHDECLINED + "#" + player.getPlayerId());
            }
        }
    }
    /**
     * Sends to the server a message informing the board of a player.
     * @param string informs the index of the player in a list(map).
     */
    private void sendBoard(String string) {
        Match m = player.getMatch();
        if (m == null) {
            return;
        }
        m.getOtherServer(player.getPlayerId()).send(ServerQueryCodes.BOARD + " " + string);
    }

    private void checkIfThereIsAMatchAndDecline() {
        try {
            sendGameOver();
        } catch (Exception ex) {
        }

    }
}
