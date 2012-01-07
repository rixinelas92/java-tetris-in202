/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetris_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;
import tetris.Main;
import tetris.Position;

public class Layout1 extends JFrame {

    private JPanel base, topPanel, initialPanel, selectionPanel, optionsPanel, somPanel, game1pPanel, game2pPanel;
    private JLabelCont[] currentPiece, nextPiece; //array with the position of the 4 boxes of the 2 pieces
    private JLabelCont[][] screen;// Sreen 10 x 13 with the pointer for all the lavels in use
    //constants
    private int pieceSize = 20, screenWidth = 10, screenHeight = 13, levelScore = 20, levelScoreAnt = 0, scoreFactor = 50, levelNumber = 0;
    //options components
    private JTextField leftKey, rightKey, downKey, rotateKey, playerName;
    private JCheckBox mouseBox;
    //sound components
    private JComboBox themeBox;
    private JSlider volumeSlider;
    //1 players components
    private JPanel gameScreen1pPanel, gameNext1pPanel;
    private JProgressBar scoreBar;
    private JTextField score, timePassed;
    private JLabel level;
    //listenets
    static ActionListener gameViewReady = null;
    private static final int X_BASE = 30;
    private static final int Y_BASE = 30;

    public Layout1() {

        make_top();
        make_initial();
        make_selection();
        make_options();
        make_som();
        make_game1p();
        make_game2p();
        make_base();
        make_UI();
        screen = new JLabelCont[screenWidth][screenHeight+3];
        currentPiece = new JLabelCont[4];
        nextPiece = new JLabelCont[4];
    }
    //cria os panels usados

    private void make_top() {

        topPanel = new JPanel(new AbsoluteLayout());

        JToolBar toolbar = new JToolBar();
        try {
            toolbar.setFloatable(false);
            ImageIcon newgameIcon = new ImageIcon(getClass().getResource("newgame.png"));
            ImageIcon configIcon = new ImageIcon(getClass().getResource("config.png"));
            ImageIcon somIcon = new ImageIcon(getClass().getResource("som.png"));
            ImageIcon closeIcon = new ImageIcon(getClass().getResource("close.png"));

            JButton newgame = new JButton("New Game", newgameIcon);
            JButton config = new JButton("Configuration", configIcon);
            JButton som = new JButton("Sound", somIcon);
            JButton close = new JButton("Exit", closeIcon);

            newgame.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    func_newgame();
                }
            });

            config.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    func_config();
                }
            });

            som.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    func_som();
                }
            });

            close.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    func_exit();
                }
            });

            toolbar.add(newgame, new AbsoluteConstraints(20, 20));
            toolbar.add(config, new AbsoluteConstraints(80, 20));
            toolbar.add(som, new AbsoluteConstraints(140, 20));
            toolbar.add(close, new AbsoluteConstraints(200, 20));

            topPanel.add(toolbar, new AbsoluteConstraints(0, 0));
        } catch (Exception e) {
            System.out.println("Problem in top icons load");
            e.printStackTrace();
        }
    }

    private void make_initial() {
        initialPanel = new JPanel(new AbsoluteLayout());
        JLabel menuImage = new JLabel();
        try {
            menuImage.setIcon(new ImageIcon(getClass().getResource("/Tetris_interface/menuimage.png")));

            initialPanel.add(menuImage, new AbsoluteConstraints(0, 50, -1, -1));

            JLabel initialMenu = new JLabel("TETRIS");
            initialMenu.setFont(new java.awt.Font("Neuropol", 0, 48));

            initialPanel.add(initialMenu, new AbsoluteConstraints(70, 5, -1, -1));

            JLabel credits1 = new JLabel("By: Gustavo PACIANOTTO G.");
            credits1.setFont(new Font("Segoe Print", 0, 13));
            JLabel credits2 = new JLabel("     Adriano Tacilo RIBEIRO");
            credits2.setFont(new Font("Segoe Print", 0, 13));
            JLabel credits3 = new JLabel("     Ademir Felipe TELES");
            credits3.setFont(new Font("Segoe Print", 0, 13));

            initialPanel.add(credits1, new AbsoluteConstraints(127, 180, -1, -1));
            initialPanel.add(credits2, new AbsoluteConstraints(127, 202, -1, -1));
            initialPanel.add(credits3, new AbsoluteConstraints(127, 224, -1, -1));
        } catch (Exception e) {
            System.out.println("Problem in top icons load");
            e.printStackTrace();
        }

    }

    private void make_selection() {
        selectionPanel = new JPanel(new AbsoluteLayout());

        JLabel selectionMenu = new JLabel("Game Mode");
        selectionMenu.setFont(new java.awt.Font("Neuropol", 0, 24));

        JSeparator separator = new JSeparator();

        JButton player1 = new JButton("1 Player");
        player1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_1player();
            }
        });

        JButton player2 = new JButton("2 Players");
        player2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_2players();
            }
        });

        selectionPanel.add(selectionMenu, new AbsoluteConstraints(80, 20));
        selectionPanel.add(separator, new AbsoluteConstraints(15, 50, 290, 11));

        selectionPanel.add(player1, new AbsoluteConstraints(2, 55, 320, 130));
        selectionPanel.add(player2, new AbsoluteConstraints(2, 182, 320, 130));

    }

    private void make_options() {

        optionsPanel = new JPanel(new AbsoluteLayout());

        JLabel optionTitle = new JLabel("Options");
        optionTitle.setFont(new java.awt.Font("Neuropol", 0, 24));

        JSeparator separator = new JSeparator();

        optionsPanel.add(optionTitle, new AbsoluteConstraints(110, 20, -1, -1));
        optionsPanel.add(separator, new AbsoluteConstraints(15, 50, 290, 11));

        //controls panel
        JPanel controlsPanel = new JPanel(new AbsoluteLayout());
        controlsPanel.setBorder(BorderFactory.createTitledBorder(null, "Controls", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Sybil Green", 0, 14)));

        JLabel moveLeft = new JLabel("Move Left");
        moveLeft.setFont(new Font("Segoe Print", 0, 12));

        leftKey = new JTextField("Q");
        leftKey.setFont(new Font("Segoe Print", 0, 12));
        leftKey.setHorizontalAlignment(JTextField.CENTER);
        leftKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel moveRight = new JLabel("Move Right");
        moveRight.setFont(new Font("Segoe Print", 0, 12));

        rightKey = new JTextField("D");
        rightKey.setFont(new Font("Segoe Print", 0, 12));
        rightKey.setHorizontalAlignment(JTextField.CENTER);
        rightKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel moveDown = new JLabel("Move Down");
        moveDown.setFont(new Font("Segoe Print", 0, 12));

        downKey = new JTextField("S");
        downKey.setFont(new Font("Segoe Print", 0, 12));
        downKey.setHorizontalAlignment(JTextField.CENTER);
        downKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel rotate = new JLabel("Rotate");
        rotate.setFont(new Font("Segoe Print", 0, 12));

        rotateKey = new JTextField("Z");
        rotateKey.setFont(new Font("Segoe Print", 0, 12));
        rotateKey.setHorizontalAlignment(JTextField.CENTER);
        rotateKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));


        mouseBox = new JCheckBox("Use mouse");
        mouseBox.setFont(new Font("Segoe Print", 0, 12));

        controlsPanel.add(moveLeft, new AbsoluteConstraints(30, 30, -1, -1));
        controlsPanel.add(leftKey, new AbsoluteConstraints(115, 32, 25, 22));

        controlsPanel.add(moveRight, new AbsoluteConstraints(160, 30, -1, -1));
        controlsPanel.add(rightKey, new AbsoluteConstraints(240, 32, 25, 22));

        controlsPanel.add(moveDown, new AbsoluteConstraints(30, 60, -1, -1));
        controlsPanel.add(downKey, new AbsoluteConstraints(115, 63, 25, 22));

        controlsPanel.add(rotate, new AbsoluteConstraints(160, 60, -1, -1));
        controlsPanel.add(rotateKey, new AbsoluteConstraints(240, 63, 25, 22));

        controlsPanel.add(mouseBox, new AbsoluteConstraints(30, 90, -1, -1));

        optionsPanel.add(controlsPanel, new AbsoluteConstraints(20, 60, 290, 120));

        //begin of the player name panel
        JPanel playerPanel = new JPanel(new AbsoluteLayout());
        playerPanel.setBorder(BorderFactory.createTitledBorder(null, "Player Name", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Sybil Green", 0, 14)));

        playerName = new JTextField("ENSTA Project");
        playerName.setFont(new Font("Segoe Print", 0, 12));
        playerName.setHorizontalAlignment(JTextField.CENTER);
        playerName.setBorder(null);
        playerName.setBackground(new Color(playerPanel.getBackground().getRed(), playerPanel.getBackground().getGreen(), playerPanel.getBackground().getBlue()));



        playerPanel.add(playerName, new AbsoluteConstraints(12, 20, 260, 30));

        optionsPanel.add(playerPanel, new AbsoluteConstraints(20, 190, 290, 60));

        //bottons

        JButton apply = new JButton("Apply");
        apply.setFont(new Font("Planet Benson 2", 0, 14));
        apply.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_changeConfig();
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Planet Benson 2", 0, 14));
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_initial();
            }
        });
        optionsPanel.add(apply, new AbsoluteConstraints(60, 250, -1, -1));
        optionsPanel.add(cancel, new AbsoluteConstraints(180, 250, -1, -1));

    }

    private void make_som() {
        somPanel = new JPanel(new AbsoluteLayout());
        JLabel optionTitle = new JLabel("Sound");
        optionTitle.setFont(new java.awt.Font("Neuropol", 0, 24));

        JSeparator separator = new JSeparator();

        somPanel.add(optionTitle, new AbsoluteConstraints(110, 20, -1, -1));
        somPanel.add(separator, new AbsoluteConstraints(15, 50, 290, 11));
        //options of sounds

        JLabel themeTitle = new JLabel("Theme");
        themeTitle.setFont(new Font("Segoe Print", 0, 14));

        themeBox = new JComboBox();
        themeBox.setFont(new Font("Segoe Print", 0, 12));
        themeBox.setModel(new DefaultComboBoxModel(new String[]{"Classic", "MarioBros", "PacMan", "Bomberman"}));

        JLabel volumeTitle = new JLabel("Volume");
        volumeTitle.setFont(new Font("Segoe Print", 0, 14));

        volumeSlider = new JSlider();

        somPanel.add(themeTitle, new AbsoluteConstraints(30, 90, -1, -1));
        somPanel.add(themeBox, new AbsoluteConstraints(110, 85, -1, -1));
        somPanel.add(volumeTitle, new AbsoluteConstraints(30, 140, -1, -1));
        somPanel.add(volumeSlider, new AbsoluteConstraints(110, 140, -1, -1));

        //buttons
        JButton apply = new JButton("Apply");
        apply.setFont(new Font("Planet Benson 2", 0, 14));
        apply.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_changeConfig();
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Planet Benson 2", 0, 14));
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_initial();
            }
        });
        somPanel.add(apply, new AbsoluteConstraints(60, 250, -1, -1));
        somPanel.add(cancel, new AbsoluteConstraints(180, 250, -1, -1));

    }

    private void make_game1p() {
        game1pPanel = new JPanel(new AbsoluteLayout());

        JLabel game1pTitle = new JLabel("1 Player");
        game1pTitle.setFont(new java.awt.Font("Neuropol", 0, 24));

        JSeparator separator = new JSeparator();

        game1pPanel.add(game1pTitle, new AbsoluteConstraints(110, 20, -1, -1));
        game1pPanel.add(separator, new AbsoluteConstraints(5, 50, 330, 10));

        //panels

        gameScreen1pPanel = new JPanel(new AbsoluteLayout());
        gameScreen1pPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        gameScreen1pPanel.setFocusable(true);
        gameNext1pPanel = new JPanel(new AbsoluteLayout());
        gameNext1pPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        game1pPanel.add(gameScreen1pPanel, new AbsoluteConstraints(0, 50, 200, 260));
        game1pPanel.add(gameNext1pPanel, new AbsoluteConstraints(200, 50, 90, 60));
        //game status
        scoreBar = new JProgressBar();
        scoreBar.setValue(10);
        game1pPanel.add(scoreBar, new AbsoluteConstraints(205, 210, 120, -1));

        level = new JLabel("Level: 0");
        level.setFont(new java.awt.Font("Neuropol", 0, 14));
        game1pPanel.add(level, new AbsoluteConstraints(235, 190, -1, -1));

        score = new JTextField();
        score.setText("0");
        score.setFont(new Font("Neuropol", 0, 14)); // NOI18N
        score.setHorizontalAlignment(JTextField.CENTER);
        score.setEditable(false);
        game1pPanel.add(score, new AbsoluteConstraints(205, 150, 60, 25));

        JLabel scoreLabel = new JLabel("SCORE");
        scoreLabel.setFont(new Font("Neuropol", 0, 14));
        game1pPanel.add(scoreLabel, new AbsoluteConstraints(210, 130, -1, -1));

        JLabel timeLabel = new JLabel("TIME");
        timeLabel.setFont(new Font("Neuropol", 0, 14));
        game1pPanel.add(timeLabel, new AbsoluteConstraints(275, 130, -1, -1));

        timePassed = new JTextField("01:02");
        timePassed.setBackground(new java.awt.Color(0, 0, 0));
        timePassed.setFont(new java.awt.Font("Neuropol", 0, 14)); // NOI18N
        timePassed.setForeground(new java.awt.Color(51, 255, 0));
        timePassed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timePassed.setEditable(false);
        game1pPanel.add(timePassed, new AbsoluteConstraints(265, 150, 60, 25));

        //buttons
        JButton apply = new JButton("Pause");
        apply.setFont(new Font("Planet Benson 2", 0, 14));
        apply.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_pause();
            }
        });
        JButton cancel = new JButton("Restart");
        cancel.setFont(new Font("Planet Benson 2", 0, 14));
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_restart();
            }
        });
        game1pPanel.add(apply, new AbsoluteConstraints(215, 230, 100, 30));
        game1pPanel.add(cancel, new AbsoluteConstraints(215, 260, 100, -1));

    }

    private void make_game2p() {
        game2pPanel = new JPanel(new AbsoluteLayout());
        JLabel menu = new JLabel("ainda naum entendi como vai funcionar esta janela, sorry");
        game2pPanel.add(menu, new AbsoluteConstraints(0, 0));

    }

    private void make_base() {

        base = new JPanel(new AbsoluteLayout());
        base.add(topPanel, new AbsoluteConstraints(0, 0));
        base.add(initialPanel, new AbsoluteConstraints(0, 20, 330, 340));
        base.add(selectionPanel, new AbsoluteConstraints(0, 10, 330, 320));
        selectionPanel.setVisible(false);
        base.add(optionsPanel, new AbsoluteConstraints(0, 10, 330, 320));
        optionsPanel.setVisible(false);
        base.add(somPanel, new AbsoluteConstraints(0, 10, 330, 320));
        somPanel.setVisible(false);
        base.add(game1pPanel, new AbsoluteConstraints(0, 10, 330, 320));
        game1pPanel.setVisible(false);
        base.add(game2pPanel, new AbsoluteConstraints(0, 10, 330, 320));
        game2pPanel.setVisible(false);

    }
    //cria a interface do JFrame

    private void make_UI() {
        getContentPane().setLayout(new AbsoluteLayout());
        add(base, new AbsoluteConstraints(0, 0));
        pack();
        setTitle("Tetris");
        setSize(330, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);


    }
    //functions by Events

    private void func_initial() {
        initialPanel.setVisible(true);
        selectionPanel.setVisible(false);
        optionsPanel.setVisible(false);
        somPanel.setVisible(false);
        game1pPanel.setVisible(false);
        game2pPanel.setVisible(false);
    }

    private void func_newgame() {
        initialPanel.setVisible(false);
        selectionPanel.setVisible(true);
        optionsPanel.setVisible(false);
        somPanel.setVisible(false);
        game1pPanel.setVisible(false);
        game2pPanel.setVisible(false);
    }

    private void func_config() {
        initialPanel.setVisible(false);
        selectionPanel.setVisible(false);
        optionsPanel.setVisible(true);
        somPanel.setVisible(false);
        game1pPanel.setVisible(false);
        game2pPanel.setVisible(false);
    }

    private void func_som() {
        initialPanel.setVisible(false);
        selectionPanel.setVisible(false);
        optionsPanel.setVisible(false);
        somPanel.setVisible(true);
        game1pPanel.setVisible(false);
        game2pPanel.setVisible(false);
    }

    private void func_1player() {
        initialPanel.setVisible(false);
        selectionPanel.setVisible(false);
        optionsPanel.setVisible(false);
        somPanel.setVisible(false);
        game1pPanel.setVisible(true);
        game2pPanel.setVisible(false);

        if (gameViewReady != null) {
            gameViewReady.actionPerformed(null);
        }
       }

    private void func_2players() {
        initialPanel.setVisible(false);
        selectionPanel.setVisible(false);
        optionsPanel.setVisible(false);
        somPanel.setVisible(false);
        game1pPanel.setVisible(false);
        game2pPanel.setVisible(true);
    }

    private void func_exit() {
        System.exit(0);
    }

    private void func_changeConfig() {
        //atualiza hotkeys
        if (mouseBox.isSelected()) {
            //habilita movimento pelo mouse
        } else {
            //desabilita o mouse
        }

        func_initial();
    }

    private void func_pause() {
       Main.togglePause();
    }

    private void func_restart() {
        //just for test
        Position[] tester = new Position[4];
        try {
            tester[0] = new Position(5, 5);
            tester[1] = new Position(3, 5);
            tester[2] = new Position(4, 5);
            tester[3] = new Position(5, 6);

            setPiecePosition(tester);

            System.out.println("xau");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //publics functions

    public void setPiecePosition(Position[] newPiece) {
        //if the bloc shouldn't keep hide, pass position X or/and Y =-1
        for(int i=0;i<4;i++){
            gameScreen1pPanel.remove(currentPiece[i]);
            gameScreen1pPanel.add(currentPiece[i], new AbsoluteConstraints(xPos(newPiece[i].getX()), yPos(newPiece[i].getY()), pieceSize, pieceSize));
         //   currentPiece[i].setLocation(xPos(newPiece[i].getX()), yPos(newPiece[i].getY()));
        }
        for(int i=0;i<4;i++){
            screen[currentPiece[i].getCX()][currentPiece[i].getCY()] = null;
        }
        for(int i=0;i<4;i++){
            screen[newPiece[i].getX()][newPiece[i].getY()] = currentPiece[i];
            screen[newPiece[i].getX()][newPiece[i].getY()].setP(newPiece[i].getX(),newPiece[i].getY());
        }
        gameScreen1pPanel.setVisible(false);
        gameScreen1pPanel.setVisible(true);
  
    }
    
    public void newFirstPiece(Position[] newpiece, String c){
        for(int i=0;i<4;i++){
            nextPiece[i] = new JLabelCont(new ImageIcon(getClass().getResource("/Tetris_interface/" + c + ".png")));
        }      
        Position min= Position.getMinCoord(newpiece);
        for(int i=0;i<4;i++){
            gameNext1pPanel.add(nextPiece[i], new AbsoluteConstraints(X_BASE + ( newpiece[i].getX()-min.getX()) * pieceSize, Y_BASE - (newpiece[i].getY()-min.getY()) * pieceSize, pieceSize, pieceSize));
        }
    }

    public void newPiece(Position[] newPosCurrentPiece, Position[] newPosNextPiece, String colorPiece) {
        //this function receive the 4 positions of the new piece and her color
        for(int i=0;i<4;i++){
            currentPiece[i] = nextPiece[i];
        }
        //put the piece in the game
        if (currentPiece[0] != null) {
            System.out.println("hi");
            
            for(int i=0;i<4;i++){
                gameScreen1pPanel.add(currentPiece[i], new AbsoluteConstraints(xPos(newPosCurrentPiece[i].getX()), yPos(newPosCurrentPiece[i].getY()), pieceSize, pieceSize));
            }
            for(int i=0;i<4;i++){
            screen[currentPiece[i].getCX()][currentPiece[i].getCY()] = null;
            }
            for(int i=0;i<4;i++){   
                screen[newPosCurrentPiece[i].getX()][newPosCurrentPiece[i].getY()] = currentPiece[i];
                screen[newPosCurrentPiece[i].getX()][newPosCurrentPiece[i].getY()].setP(newPosCurrentPiece[i].getX(),newPosCurrentPiece[i].getY());
            }
            //add one new piece in NextPiece box
            gameNext1pPanel.removeAll();
        }
        for(int i=0;i<4;i++){
            nextPiece[i] = new JLabelCont(new ImageIcon(getClass().getResource("/Tetris_interface/" + colorPiece + ".png")));
        }
        
        Position min= Position.getMinCoord(newPosNextPiece);
        for(int i=0;i<4;i++){
            gameNext1pPanel.add(nextPiece[i], new AbsoluteConstraints(X_BASE + ( newPosNextPiece[i].getX()-min.getX()) * pieceSize, Y_BASE - (newPosNextPiece[i].getY()-min.getY()) * pieceSize, pieceSize, pieceSize));
        }     
    }

    public void eraseLine(int line) {

        //linhas começam do zero
        int i, j;

        for (j = screenHeight-1; j >=0; j--) {
            for (i = 0; i < screenWidth; i++) {
                if(screen[i][j] != null){
                    System.out.print(".");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        for (i = 0; i < screenWidth; i++) {
            if(screen[i][line] != null){
                screen[i][line].setVisible(false);
                screen[i][line].setEnabled(false);
                gameScreen1pPanel.remove(screen[i][line]);
            }
        }
        
        for (j = line; j < screenHeight; j++) {
            int count = 0;
            for (i=0; i < screenWidth; i++) {
                screen[i][j] = screen[i][j+1];
                if(screen[i][j] != null){
                    screen[i][j].setP(i, j);
                    count++;
                    gameScreen1pPanel.remove(screen[i][j]);
                    gameScreen1pPanel.add(screen[i][j], new AbsoluteConstraints(xPos(i),yPos(j), pieceSize, pieceSize));
                }
            }
            for (i=0; i < screenWidth; i++) {
                screen[i][j+1] = null;
            }
            if(count == 0)
                break;
        }
        toggleVisiblePropOnGame();
    }



    void verifiIntegrity(String str){
        for (int j = 0; j < screenHeight; j++) {
            for (int i = 0; i < screenWidth; i++) {
                if(screen[i][j] != null){
                    if(screen[i][j].getCX() != i || screen[i][j].getCY() != j){
                        System.out.println(str+" HEYNHEY HEYH! POBREMA!"+i+" "+j+" =/= "+screen[i][j].getCX()+" "+screen[i][j].getCY());
                    }
                }

            }
        }

    }
    public void toggleVisiblePropOnGame(){
        gameNext1pPanel.setVisible(false);
        gameNext1pPanel.setVisible(true);
        gameScreen1pPanel.setVisible(false);
        gameScreen1pPanel.setVisible(true);
    }

    public void setScore(int newScore, int newLevel, int scoreMin, int scoreMax) {
        //if change of level, set the new level maximum score and change the label
        score.setText(String.valueOf(newScore));
        level.setText("Level:" + newLevel);
        scoreBar.setValue((newScore - scoreMin) * 100 / (scoreMax - scoreMin));
    }

    public void setKeyListener(KeyListener newListener) {
        gameScreen1pPanel.addKeyListener(newListener);
    }

    public void setMouseListener(MouseListener newListener) {
        gameScreen1pPanel.addMouseListener(newListener);
    }

    public void setMouseMotionListener(MouseMotionListener newListener) {
        gameScreen1pPanel.addMouseMotionListener(newListener);
    }
    //internal use funcition

    private int xPos(int newX) {
        return newX * pieceSize;
    }

    private int yPos(int newY) {
        newY++;
        return gameScreen1pPanel.getHeight() - newY * pieceSize;
    }

    public static void addGameViewReady(ActionListener newGameViewReady) {
        gameViewReady = newGameViewReady;
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Layout1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Layout1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Layout1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Layout1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Layout1 ex = new Layout1();
                ex.setVisible(true);
            }
        });
    }


    public class JLabelCont extends JLabel{
        int x;
        int y;

        private JLabelCont(ImageIcon imageIcon) {
            super(imageIcon);
        }
        public void setP(int x, int y){
            this.x =x;
            this.y =y;
        }
        public int getCX(){
            return x;
        }
        public int getCY(){
            return y;
        }

    }
}
