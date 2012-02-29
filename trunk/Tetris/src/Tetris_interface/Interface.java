package Tetris_interface;

import Tetris_interface.components.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import online.util.PlayerDescriptor;

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
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import tetris.Controller;
import tetris.Main;
import tetris.Position;
import tetris.Screen;

public class Interface extends JFrame {

    // global use
    private JPanel screenPanel, topPanel, initialPanel, selectionPanel, optionsPanel, somPanel, game1pPanel, game2pPanel;
    private JLabelCont[] currentPiece, nextPiece, holdPiece, shadowPiece;
    private JLabelCont[][] screen;// Sreen 10 x 20 with the pointer for all the labels in used.
    private Font neuropol14, neuropol18, neuropol28, segoePrint12, segoePrint11, planetBenson14, sevenSegments14;
    static public final Color COLOR_opaque = new Color(200, 200, 200, 255);
    static public final Color COLOR_semiopaque = new Color(200, 200, 200, 140);
    static public final Color COLOR_translucent = new Color(0, 0, 0, 0);
    private Image imageb = null;
    //constants
    private int pieceSize = 19, screenWidth = Screen.SIZE_X, screenHeight = Screen.SIZE_Y;
    //options components
    private JTextField leftKey, rightKey, downKey, rotateKey, goToBottonKey, holdKey, pauseKey, playerName, ipName;
    private JCheckBox mouseBox;
    private static JButton applyOptions;
    private Integer[] keys = Controller.keysStart;
    //sound components
    private JComboBox themeBox;
    private JSlider volumeSlider;
    private static JButton applySom;
    //selection components
    private JComboBox levelGame1p;
    //1 players components
    private JPanel gameScreen1pPanel, gameNext1pPanel, gameHold1pPanel;
    private JProgressBar scoreBar;
    private JTextField score, timePassed;
    private JLabel level, gameover, gamewin;
    private int initialLevel,initialScore;
    private static final int X_screenPanel = 43;
    private static final int Y_screenPanel = 49;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    //others
    static ActionListener gameViewReady = null;
    public ClockTimer clock;
    Random r = new Random();
    private JList playersList;
    private boolean is2PlayerGame;
    private SmallBoard secondPlayerBoard;

    /**
     * Initialize the configuration of the screen.
     */
    public Interface() {
        try {
            imageb = ImageIO.read(getClass().getResource("imgs/back7.png"));
        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
        make_fonts();
        make_top();
        make_initial();
        make_selection();
        make_options();
        make_sound();
        make_game1p();
        make_game2p();
        make_screenPanel();
        make_UI();
        screen = new JLabelCont[screenWidth][screenHeight + 3];
        currentPiece = new JLabelCont[4];
        holdPiece = new JLabelCont[4];
        shadowPiece = new JLabelCont[4];
    }

    /**
     * The following methods create the panel that will be used.
     */
    /**
     * Defines the fonts that will be used in the layout.
     */
    private void make_fonts() {
        try {
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("components/font1.ttf"));
            neuropol28 = dynamicFont.deriveFont(28f);
            neuropol18 = dynamicFont.deriveFont(18f);
            neuropol14 = dynamicFont.deriveFont(14f);
            
            dynamicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("components/font2.ttf"));
            segoePrint12 = dynamicFont.deriveFont(12f);
            segoePrint11 = dynamicFont.deriveFont(11f);
            
            dynamicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("components/font3.ttf"));
            planetBenson14 = dynamicFont.deriveFont(13f);
            
            dynamicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("components/font4.ttf"));
            sevenSegments14 = dynamicFont.deriveFont(18f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the main screen, configuring the way in wich the icons are 
     * displayed in the initial screen and managing the action on theese icons.
     */
    private void make_top() {
        topPanel = new JPanel(new AbsoluteLayout());

        JToolBar toolbar = new JToolBar();
        try {
            //Setup of the main screen.
            toolbar.setFloatable(false);
            ImageIcon newgameIcon = new ImageIcon(getClass().getResource("imgs/newgame.png"));
            ImageIcon configIcon = new ImageIcon(getClass().getResource("imgs/config.png"));
            ImageIcon somIcon = new ImageIcon(getClass().getResource("imgs/som.png"));
            ImageIcon closeIcon = new ImageIcon(getClass().getResource("imgs/close.png"));
            JButton newgame = new JButton("New Game", newgameIcon);
            newgame.setFont(segoePrint11);
            JButton config = new JButton("Configuration", configIcon);
            config.setFont(segoePrint11);
            JButton som = new JButton("Sound", somIcon);
            som.setFont(segoePrint11);
            JButton close = new JButton("Exit", closeIcon);
            close.setFont(segoePrint11);
            newgame.setBorderPainted(false);
            newgame.setOpaque(false);
            config.setBorderPainted(false);
            config.setOpaque(false);
            som.setBorderPainted(false);
            som.setOpaque(false);
            close.setOpaque(false);
            close.setBorderPainted(false);
            //Managing the actions on the icons.
            newgame.addActionListener(new ActionListener() {

                /**
                 * Method to manager the button new game.
                 */
                public void actionPerformed(ActionEvent event) {
                    func_showPanel(1);
                }
            });
            config.addActionListener(new ActionListener() {

                /**
                 * Method to manager the button confiration.
                 */
                public void actionPerformed(ActionEvent event) {
                    func_showPanel(2);
                }
            });
            som.addActionListener(new ActionListener() {

                /**
                 * Method to manager the button sound.
                 */
                public void actionPerformed(ActionEvent event) {
                    func_showPanel(3);
                }
            });
            close.addActionListener(new ActionListener() {

                /**
                 * Method to manager the button exit.
                 */
                public void actionPerformed(ActionEvent event) {
                    func_exit();
                }
            });
            toolbar.add(newgame, new AbsoluteConstraints(20, 20));
            toolbar.add(config, new AbsoluteConstraints(70, 20));
            toolbar.add(som, new AbsoluteConstraints(120, 20));
            toolbar.add(close, new AbsoluteConstraints(160, 20));
            topPanel.add(toolbar, new AbsoluteConstraints(0, 0, -1, 30));
        } catch (Exception e) {
            System.out.println("Problem in top icons load");
            e.printStackTrace();
        }
    }

    /**
     * Configuring, managing actions and setting the game's presentation screen.
     */
    private void make_initial() {
        initialPanel = new JPanel(new AbsoluteLayout());
        initialPanel.setBackground(COLOR_semiopaque);

        JLabel menuImage = new JLabel();
        try {
            initialPanel.add(menuImage, new AbsoluteConstraints(0, 100, 260, 250));
            JLabel initialMenu = new JLabel("TETRIS");
            initialMenu.setFont(new Font("Neuropol", 0, 48));
            initialPanel.add(initialMenu, new AbsoluteConstraints(70, 30, -1, -1));
            JLabel credits1 = new JLabel("By: Gustavo PACIANOTTO G.");
            credits1.setFont(segoePrint12);
            JLabel credits2 = new JLabel("     Adriano Tacilo RIBEIRO");
            credits2.setFont(segoePrint12);
            JLabel credits3 = new JLabel("     Ademir Felipe TELES");
            credits3.setFont(segoePrint12);
            initialPanel.add(credits1, new AbsoluteConstraints(10, 390, -1, -1));
            initialPanel.add(credits2, new AbsoluteConstraints(10, 410, -1, -1));
            initialPanel.add(credits3, new AbsoluteConstraints(10, 430, -1, -1));
        } catch (Exception e) {
            System.out.println("Problem in top icons load");
            e.printStackTrace();
        }
    }

    /**
     * Configuring, managing actions and setting the selection screen.
     */
    private void make_selection() {
        selectionPanel = new JPanel(new AbsoluteLayout());
        selectionPanel.setBackground(COLOR_semiopaque);

        JLabel selectionMenu = new JLabel("Game Mode");
        selectionMenu.setFont(neuropol28);
        JSeparator separator = new JSeparator();
        JButton player1 = new JButton("1 Player");
        player1.setFont(neuropol14);
        player1.addActionListener(new ActionListener() {

            /**
             * Method to manager the button 1 player.
             */
            public void actionPerformed(ActionEvent event) {
                configureScreenForGameType(false);
                func_showPanel(4);
            }
        });
        JLabel levelLabel = new JLabel("Initial Level");
        levelLabel.setFont(neuropol14);
        levelGame1p = new JComboBox();
        levelGame1p.setModel(new DefaultComboBoxModel(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
        levelGame1p.setFont(sevenSegments14); 
        
        JButton player2 = new JButton("Multiplayer");
        player2.setFont(neuropol14);
        player2.addActionListener(new ActionListener() {

            /**
             * Method to manager the button 2 players.
             */
            public void actionPerformed(ActionEvent event) {
                func_showPanel(5);
            }
        });
        selectionPanel.add(selectionMenu, new AbsoluteConstraints(100, 20));
        selectionPanel.add(separator, new AbsoluteConstraints(5, 50, 313, 10));
        selectionPanel.add(player1, new AbsoluteConstraints(2, 55, 220, 130));
        selectionPanel.add(levelLabel, new AbsoluteConstraints(235, 85, -1, -1));
        selectionPanel.add(levelGame1p, new AbsoluteConstraints(245, 115, 37, 27));
        selectionPanel.add(player2, new AbsoluteConstraints(2, 202, 320, 130));
    }

    /**
     * Configuring, managing actions and setting the options screen.
     */
    private void make_options() {
        optionsPanel = new JPanel(new AbsoluteLayout());
        optionsPanel.setBackground(COLOR_semiopaque);

        JLabel optionTitle = new JLabel("Options");
        optionTitle.setFont(neuropol28);

        JSeparator separator = new JSeparator();

        optionsPanel.add(optionTitle, new AbsoluteConstraints(110, 20, -1, -1));
        optionsPanel.add(separator, new AbsoluteConstraints(5, 50, 313, 10));

        //Controls panel.
        JPanel controlsPanel = new JPanel(new AbsoluteLayout());
        controlsPanel.setBackground(COLOR_semiopaque);
        controlsPanel.setBorder(BorderFactory.createTitledBorder(null, "Controls", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, neuropol18));
       
        //Configuring presentation on the screen of the options.
        JLabel moveLeftLabel = new JLabel("Move Left");
        moveLeftLabel.setFont(segoePrint12);
        leftKey = new JTextField(KeyEvent.getKeyText(keys[0]));
        leftKey.setFont(segoePrint12);
        leftKey.setHorizontalAlignment(JTextField.CENTER);
        leftKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel moveRightLabel = new JLabel("Move Right");
        moveRightLabel.setFont(segoePrint12);
        rightKey = new JTextField(KeyEvent.getKeyText(keys[2]));
        rightKey.setFont(segoePrint12);
        rightKey.setHorizontalAlignment(JTextField.CENTER);
        rightKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel moveDownLabel = new JLabel("Move Down");
        moveDownLabel.setFont(segoePrint12);
        downKey = new JTextField(KeyEvent.getKeyText(keys[1]));
        downKey.setFont(segoePrint12);
        downKey.setHorizontalAlignment(JTextField.CENTER);
        downKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel rotateLabel = new JLabel("Rotate");
        rotateLabel.setFont(segoePrint12);
        rotateKey = new JTextField(KeyEvent.getKeyText(keys[4]));
        rotateKey.setFont(segoePrint12);
        rotateKey.setHorizontalAlignment(JTextField.CENTER);
        rotateKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel goBottonLabel = new JLabel("To Botton");
        goBottonLabel.setFont(segoePrint12);
        goToBottonKey = new JTextField(KeyEvent.getKeyText(keys[3]));
        goToBottonKey.setFont(segoePrint12);
        goToBottonKey.setHorizontalAlignment(JTextField.CENTER);
        goToBottonKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel holdLabel = new JLabel("Hold");
        holdLabel.setFont(segoePrint12);
        holdKey = new JTextField(KeyEvent.getKeyText(keys[5]));
        holdKey.setFont(segoePrint12);
        holdKey.setHorizontalAlignment(JTextField.CENTER);
        holdKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel pauseLabel = new JLabel("Pause");
        pauseLabel.setFont(segoePrint12);
        pauseKey = new JTextField(KeyEvent.getKeyText(keys[6]));
        pauseKey.setFont(segoePrint12);
        pauseKey.setHorizontalAlignment(JTextField.CENTER);
        pauseKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        mouseBox = new JCheckBox("Use mouse");
        mouseBox.setFont(segoePrint12);
        mouseBox.setOpaque(false);


        controlsPanel.add(moveLeftLabel, new AbsoluteConstraints(15, 30, -1, -1));
        controlsPanel.add(leftKey, new AbsoluteConstraints(95, 32, 55, 25));
        controlsPanel.add(moveRightLabel, new AbsoluteConstraints(160, 30, -1, -1));
        controlsPanel.add(rightKey, new AbsoluteConstraints(240, 32, 55, 25));
        controlsPanel.add(moveDownLabel, new AbsoluteConstraints(15, 60, -1, -1));
        controlsPanel.add(downKey, new AbsoluteConstraints(95, 63, 55, 25));
        controlsPanel.add(rotateLabel, new AbsoluteConstraints(160, 60, -1, -1));
        controlsPanel.add(rotateKey, new AbsoluteConstraints(240, 63, 55, 25));
        controlsPanel.add(goBottonLabel, new AbsoluteConstraints(15, 90, -1, -1));
        controlsPanel.add(goToBottonKey, new AbsoluteConstraints(95, 91, 55, 25));
        controlsPanel.add(holdLabel, new AbsoluteConstraints(160, 90, -1, -1));
        controlsPanel.add(holdKey, new AbsoluteConstraints(240, 91, 55, 25));
        controlsPanel.add(pauseLabel, new AbsoluteConstraints(15, 120, -1, -1));
        controlsPanel.add(pauseKey, new AbsoluteConstraints(95, 121, 55, 25));
        controlsPanel.add(mouseBox, new AbsoluteConstraints(157, 117, -1, -1));
        optionsPanel.add(controlsPanel, new AbsoluteConstraints(10, 60, 310, 160));

        //Begin of the player name panel.
        JPanel playerPanel = new JPanel(new AbsoluteLayout());
        playerPanel.setBackground(COLOR_semiopaque);

        playerPanel.setBorder(BorderFactory.createTitledBorder(null, "Player Name", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, neuropol18));
        playerName = new JTextField("ENSTA Project");
        playerName.setFont(segoePrint12);
        playerName.setHorizontalAlignment(JTextField.CENTER);
        playerName.setBorder(null);
        playerName.setOpaque(false);
        playerPanel.add(playerName, new AbsoluteConstraints(12, 20, 290, 30));
        optionsPanel.add(playerPanel, new AbsoluteConstraints(10, 223, 310, 55));

        JPanel ipPanel = new JPanel(new AbsoluteLayout());
        ipPanel.setBackground(COLOR_semiopaque);
        ipPanel.setBorder(BorderFactory.createTitledBorder(null, "Server IP Address", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, neuropol18));
        ipName = new JTextField("Ip to 2 Players Mode");
        ipName.setFont(sevenSegments14);
        ipName.setHorizontalAlignment(JTextField.CENTER);
        ipName.setBorder(null);
        ipName.setOpaque(false);
        ipPanel.add(ipName, new AbsoluteConstraints(12, 20, 290, 30));
        optionsPanel.add(ipPanel, new AbsoluteConstraints(10, 282, 310, 55));
        //Buttons.
        applyOptions = new JButton("Apply");
        applyOptions.setFont(planetBenson14);
        JButton cancelOptions = new JButton("Cancel");
        cancelOptions.setFont(planetBenson14);
        cancelOptions.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_showPanel(0);
            }
        });
        optionsPanel.add(applyOptions, new AbsoluteConstraints(60, 340, -1, -1));
        optionsPanel.add(cancelOptions, new AbsoluteConstraints(180, 340, -1, -1));
        goToBottonKey.addFocusListener(new FocusListener() {
            /**
             * Some classes of internal use.
             */
            public void focusGained(FocusEvent fe) {
                getKeyEvent(3, goToBottonKey);
            }

            public void focusLost(FocusEvent fe) {
            }
        });
        leftKey.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent fe) {
                getKeyEvent(0, leftKey);
            }

            public void focusLost(FocusEvent fe) {
            }
        });
        rightKey.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent fe) {
                getKeyEvent(2, rightKey);
            }

            public void focusLost(FocusEvent fe) {
            }
        });
        downKey.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent fe) {
                getKeyEvent(1, downKey);
            }

            public void focusLost(FocusEvent fe) {
            }
        });
        rotateKey.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent fe) {
                getKeyEvent(4, rotateKey);
            }

            public void focusLost(FocusEvent fe) {
            }
        });
        holdKey.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent fe) {
                getKeyEvent(5, holdKey);
            }

            public void focusLost(FocusEvent fe) {
            }
        });
        pauseKey.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent fe) {
                getKeyEvent(6, pauseKey);
            }

            public void focusLost(FocusEvent fe) {
            }
        });
    }

    /**
     * Configuring, managing actions and setting the sound screen.
     */
    private void make_sound() {
        somPanel = new JPanel(new AbsoluteLayout());
        somPanel.setBackground(COLOR_semiopaque);
        JLabel somTitle = new JLabel("Sound");
        somTitle.setFont(neuropol28);

        JSeparator separator = new JSeparator();

        somPanel.add(somTitle, new AbsoluteConstraints(120, 20, -1, -1));
        somPanel.add(separator, new AbsoluteConstraints(5, 50, 313, 10));
        //Options of sounds.
        JPanel musicPanel = new JPanel(new AbsoluteLayout());
        musicPanel.setBackground(COLOR_semiopaque);

        musicPanel.setBorder(BorderFactory.createTitledBorder(null, "Musics", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, neuropol18));

        JLabel themeTitle = new JLabel("Theme");
        themeTitle.setFont(segoePrint12);
        themeBox = new JComboBox();
        themeBox.setFont(segoePrint12);
        themeBox.setModel(new DefaultComboBoxModel(new String[]{"Classic", "MarioBros", "PacMan", "Star Wars", "Silence"}));
        JLabel volumeTitle = new JLabel("Volume");
        volumeTitle.setFont(segoePrint12);
        
        volumeSlider = new JSlider();
        volumeSlider.setValue(100);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(10);
        volumeSlider.setBorder(null);
        volumeSlider.setOpaque(false);
        
        musicPanel.add(themeTitle, new AbsoluteConstraints(20, 30, -1, -1));
        musicPanel.add(themeBox, new AbsoluteConstraints(100, 25, -1, -1));
        musicPanel.add(volumeTitle, new AbsoluteConstraints(20, 80, -1, -1));
        musicPanel.add(volumeSlider, new AbsoluteConstraints(100, 80, -1, -1));

        somPanel.add(musicPanel, new AbsoluteConstraints(10, 60, 310, 135));

        //Buttons.
        applySom = new JButton("Apply");
        applySom.setFont(planetBenson14);
        JButton cancel = new JButton("Cancel");
        cancel.setFont(planetBenson14);
        cancel.addActionListener(new ActionListener() {

            /**
             * Method to manager the buttons apply and cancel to return the initial 
             * screen after configuration of the keys.
             */
            public void actionPerformed(ActionEvent event) {
                func_showPanel(0);
            }
        });
        somPanel.add(applySom, new AbsoluteConstraints(60, 340, -1, -1));
        somPanel.add(cancel, new AbsoluteConstraints(180, 340, -1, -1));
    }

    /**
     * Configuring, managing actions and setting the screen to one player.
     */
    private void make_game1p() {
        game1pPanel = new JPanel(new AbsoluteLayout());
        JLabel game1pTitle = new JLabel("1 Player");
        game1pTitle.setFont(neuropol28);
        JSeparator separator = new JSeparator();
        separator.setOpaque(false);
        game1pPanel.add(game1pTitle, new AbsoluteConstraints(110, 22, -1, -1));
        game1pPanel.add(separator, new AbsoluteConstraints(5, 50, 313, 10));
        //Panels.
        gameScreen1pPanel = new JPanel(new AbsoluteLayout());
        gameScreen1pPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        gameScreen1pPanel.setFocusable(true);
        gameScreen1pPanel.setOpaque(false);
        gameNext1pPanel = new JPanel(new AbsoluteLayout());
        gameNext1pPanel.setBackground(COLOR_translucent);
        gameNext1pPanel.setBorder(BorderFactory.createTitledBorder(null, "Next", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, neuropol14));
        gameHold1pPanel = new JPanel(new AbsoluteLayout());
        gameHold1pPanel.setBorder(BorderFactory.createTitledBorder(null, "Hold", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, neuropol14));
        gameHold1pPanel.setBackground(COLOR_translucent);
        game1pPanel.add(gameScreen1pPanel, new AbsoluteConstraints(10, 60, 10 * pieceSize, 20 * pieceSize));
        game1pPanel.add(gameNext1pPanel, new AbsoluteConstraints(220, 52, 85, 90));
        game1pPanel.add(gameHold1pPanel, new AbsoluteConstraints(220, 140, 85, 90));
        //Game status.
        scoreBar = new JProgressBar();
        scoreBar.setValue(0);
        game1pPanel.add(scoreBar, new AbsoluteConstraints(202, 345, 120, -1));
        level = new JLabel("Level: 0");
        level.setFont(neuropol14);
        game1pPanel.add(level, new AbsoluteConstraints(235, 325, -1, -1));
        score = new JTextField();
        score.setText("0");
        score.setFont(neuropol18); // NOI18N
        score.setHorizontalAlignment(JTextField.CENTER);
        score.setEditable(false);
        game1pPanel.add(score, new AbsoluteConstraints(232, 295, 60, 25));
        scoreLabel = new JLabel("SCORE");
        scoreLabel.setFont(neuropol14);
        game1pPanel.add(scoreLabel, new AbsoluteConstraints(235, 278, -1, -1));
        timeLabel = new JLabel("TIME");
        timeLabel.setFont(neuropol14);
        game1pPanel.add(timeLabel, new AbsoluteConstraints(240, 230, -1, -1));
        timePassed = new JTextField("00:00");
        timePassed.setBackground(new java.awt.Color(0, 0, 0));
        timePassed.setFont(sevenSegments14); // NOI18N
        timePassed.setForeground(new java.awt.Color(51, 255, 0));
        timePassed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timePassed.setEditable(false);
        game1pPanel.add(timePassed, new AbsoluteConstraints(232, 248, 60, 25));

        secondPlayerBoard = new SmallBoard(new int[Screen.SIZE_X], this);
        game1pPanel.add(secondPlayerBoard, new AbsoluteConstraints(232, 240, -1, -1));

        //Buttons.
        JButton pauseButton = new JButton("Pause");
        pauseButton.setFont(planetBenson14);
        pauseButton.addActionListener(new ActionListener() {

            /**
             * Method to manager the button pause.
             */
            public void actionPerformed(ActionEvent event) {
                func_pause();
            }
        });
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(planetBenson14);
        restartButton.addActionListener(new ActionListener() {

            /**
             * Method to manager the button restart.
             */
            public void actionPerformed(ActionEvent event) {
                func_restart();
            }
        });
        //Setting one palyer panel.
        game1pPanel.add(pauseButton, new AbsoluteConstraints(212, 370, 100, 35));
        game1pPanel.add(restartButton, new AbsoluteConstraints(212, 410, 100, 35));
        ImageIcon backim = new ImageIcon(getClass().getResource("imgs/backGround.png"));
        JLabel back = new JLabel(backim);
        game1pPanel.add(back, new AbsoluteConstraints(10, 59, 10 * pieceSize, 20 * pieceSize));
        gameover = new JLabel(new ImageIcon(getClass().getResource("imgs/gameover.png")));
        //Tester =new JLabel("");
        game1pPanel.add(gameover, new AbsoluteConstraints(-5, 240, -1, -1));
        game1pPanel.setComponentZOrder(gameover, 0);
        gameover.setVisible(false);

        gamewin = new JLabel(new ImageIcon(getClass().getResource("imgs/win.png")));
        //Tester =new JLabel("");
        game1pPanel.add(gamewin, new AbsoluteConstraints(-5, 240, -1, -1));
        game1pPanel.setComponentZOrder(gamewin, 0);
        gamewin.setVisible(false);
    }

    /**
     * Configuring, managing actions and setting the screen to two players.
     */
    private void make_game2p() {
        game2pPanel = new JPanel(new AbsoluteLayout());
        JLabel game2pTitle = new JLabel("2 Player");
        game2pTitle.setFont(neuropol28);
        JSeparator separator = new JSeparator();
        game2pPanel.add(game2pTitle, new AbsoluteConstraints(110, 20, -1, -1));
        playersList = new JList();
        playersList.setCellRenderer(new CellRendererWithImage(this));
        playersList.setModel(new DefaultListModel());
        playersList.addMouseListener(new ActionJList(playersList));
        JScrollPane scrollPane = new JScrollPane(playersList);
        game2pPanel.add(scrollPane, new AbsoluteConstraints(10, 60, 300, 250));
        Main.getInstance().start2pConnection();
 }

    /**
     * This method allows to set the screenPanel of panel, calling basic panels and hiding
     * others that we are not interested in the moment.
     */
    private void make_screenPanel() {
        screenPanel = new JPanel(new AbsoluteLayout()) {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(imageb, 0, 0, null);
                super.paint(g);
            }
        };
        screenPanel.setBackground(COLOR_translucent);
        screenPanel.add(topPanel, new AbsoluteConstraints(0, 0));
        screenPanel.add(initialPanel, new AbsoluteConstraints(0, 10, 330, 450));
        screenPanel.add(selectionPanel, new AbsoluteConstraints(0, 10, 330, 450));
        selectionPanel.setVisible(false);
        screenPanel.add(optionsPanel, new AbsoluteConstraints(0, 10, 330, 450));
        optionsPanel.setVisible(false);
        screenPanel.add(somPanel, new AbsoluteConstraints(0, 10, 330, 450));
        somPanel.setVisible(false);
        screenPanel.add(game1pPanel, new AbsoluteConstraints(0, 10, 330, 450));
        game1pPanel.setVisible(false);
        screenPanel.add(game2pPanel, new AbsoluteConstraints(0, 10, 330, 450));
        game2pPanel.setVisible(false);
        game1pPanel.setBackground(COLOR_semiopaque);
        game2pPanel.setBackground(COLOR_semiopaque);
        optionsPanel.setBackground(COLOR_semiopaque);
    }
    //Creates the interface of the Jpanel.
    private void make_UI() {
        getContentPane().setLayout(new AbsoluteLayout());
        add(screenPanel, new AbsoluteConstraints(0, 0));
        pack();
        setTitle("Tetris");
        setSize(330, 490);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
    }

    /**
     * This method sets the painel in the initial screen. Making visible panels 
     * that we are interested and hiding - setvisible(false)- those that do not
     * compose this screen.
     */
    public void func_showPanel(int i) {
        boolean[] flag = new boolean[6];
        int count;
        for (count = 0; count < 6; count++) {
            if (count == i) {
                flag[count] = true;
            } else {
                flag[count] = false;
            }
        }
        initialPanel.setVisible(flag[0]);
        selectionPanel.setVisible(flag[1]);
        optionsPanel.setVisible(flag[2]);
        somPanel.setVisible(flag[3]);
        game1pPanel.setVisible(flag[4]);
        game2pPanel.setVisible(flag[5]);
        if (i > 0 && i < 4) {
            Main.getInstance().pauseGame();
            Main.getInstance().removeListeners();
            Main.getInstance().sendGameOver();
        } else if (i == 4) {
            initialLevel=levelGame1p.getSelectedIndex()+1;
            initialScore=(initialLevel)*(initialLevel)*160;
            if (gameViewReady != null) {
                gameViewReady.actionPerformed(null);
        }
    }
    }

    /**
     * This method sets the painel to two players. Making visible panels 
     * that we are interested and hiding - setvisible(false)- those that do not
     * compose this screen. In this case just the secondPlayerScreen is setted 
     * true. The others panels are hidden.
     */
    public void configureScreenForGameType(boolean is2PlayerGame) {
        Main.getInstance().set2PlayerGame(is2PlayerGame);
        this.is2PlayerGame = is2PlayerGame;
        score.setVisible(!is2PlayerGame);
        scoreLabel.setVisible(!is2PlayerGame);
        level.setVisible(!is2PlayerGame);
        scoreBar.setVisible(!is2PlayerGame);
        timePassed.setVisible(!is2PlayerGame);
        timeLabel.setVisible(!is2PlayerGame);
        secondPlayerBoard.setVisible(is2PlayerGame);

    }
    /**
     * Configures the keys fo the game.
     * @param keyNumber index of the key.
     * @param keyValue value of the key.
     */
    private boolean checkAndIfCaseSetOtherKeyOnConfig(int keyNumber, int keyValue) {
        for (int i = 0; i < keys.length; i++) { // let's complicate things!!!! still not using goto...
            if (keyNumber == i) {
                continue;
            }
        if (keyValue == keys[i]) {
                switch (i) {
                    case 0:
                        getKeyEvent(0, leftKey);
                        return false;
                    case 1:
                        getKeyEvent(1, downKey);
                        return false;
                    case 2:
                        getKeyEvent(2, rightKey);
                        return false;
                    case 3:
                        getKeyEvent(3, goToBottonKey);
                        return false;
                    case 4:
                        getKeyEvent(4, rotateKey);
                        return false;
                    case 5:
                        getKeyEvent(5, holdKey);
                        return false;
                    case 6:
                        getKeyEvent(6, pauseKey);
                        return false;
                    default:
                        break;
                }
            }
        }
        return true;
    }

    /**
     * This class was implemented in order to keep the informations send by the 
     * keyboard.
     * @param keyNumber informes the code of each key.
     * @param field informes the parameter that is been changed.
     */
    private void getKeyEvent(int keyNumber, JTextField field) {
        KeyListener kl = new KeyListener() {

            int keyNumber;
            JTextField field;

            /**
             * Method that must be implemented but without executing anything, 
             * according to the language.
             */
            public void keyTyped(KeyEvent ke) {
            }

            /**
             * Default setter of the parameters <em>keyNumber</em> and <field</em>.
             */
            public KeyListener setKeyNumberAndField(int keyNumber, JTextField field) {
                this.keyNumber = keyNumber;
                this.field = field;
                return this;
            }

            /**
             * Analises the key pressed and sets the key in the index keyNumber
             * in according with it.
             */
            public void keyPressed(KeyEvent ke) {
                keys[keyNumber] = ke.getKeyCode();
                field.setText(KeyEvent.getKeyText(ke.getKeyCode()));
                field.setBackground(Color.LIGHT_GRAY);
                removeKeyListener(this);
                if (checkAndIfCaseSetOtherKeyOnConfig(keyNumber, ke.getKeyCode())) {
                    for (int i = 0; i < keys.length; i++) {
                        if (!checkAndIfCaseSetOtherKeyOnConfig(i, keys[i])) {
                            break;
                        }
                    }
                }
            }

            /**
             * Method that must be implemented but without executing anything.
             */
            public void keyReleased(KeyEvent ke) {
            }

            @Override
            /**
             * Defalut getter of the parameter <em>keyNumber</em>.
             */
            public int hashCode() {
                return keyNumber;
            }

            @Override
            @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
            /**
             * Checks if there is another key already setted for the same action.
             */
            public boolean equals(Object o) {
                if (!this.getClass().isInstance(o)) {
                    return false;
                }
                if (o.hashCode() == this.hashCode()) // HashCode is unique!
                {
                    return true;
                }
                return false;
            }
        }.setKeyNumberAndField(keyNumber, field);
        addKeyListener(kl);
        field.setBackground(Color.yellow);
        requestFocusInWindow();
    }

    /**
     * Implements the function exit.
     */
    private void func_exit() {
        Main.getInstance().terminateInternetConnection();
        Main.getInstance().saveProp();
        System.exit(0);
    }
    
     /**
     * Default getter of the selection menu .
     * @return the initial level.
     */
   public int getInitialLevel() {
         return initialLevel;
    }
   
    /**
     * Default getter of the chagement of configurations.
     * @return the keys configureted.
     */
    public Integer[] getConfigChange() {
        return keys;
    }
  
   /**
     * Default getter of the chagement of configurations.
     * @return the Ip fo the server.
     */
    public String getIPChange() {
        return ipName.getText();
    }

    /**
     * Default getter of the chagement of configurations.
     * @return the name of the user for the server.
     */
    public String getUserName() {
        return playerName.getText();
    }

    /**
     * Default setter of the chagement of configurations.
     * @return the name of the user for the server.
     */
    public void setUserName(String str) {
        playerName.setText(str);
    }
    
/**
     * Default setter of the chagement of configurations.
     * @return the IP of the server.
     */
    public void setIP(String str) {
        ipName.setText(str);
    }
    
 /**
     * Default getter of the sound volume.
     * @return the actual value of the sound.
     */
    public int getSomVolume() {
        return volumeSlider.getValue();
    }

    /**
     * Default getter of the theme sound.
     * @return the actual theme of the sound.
     */
    public int getSomTheme() {
        return themeBox.getSelectedIndex();
    }

    /**
     * Implements the function pause.
     */
    private void func_pause() {
        Main.getInstance().togglePause();
    }

    /**
     * Implements the function pause.
     */
    private void func_restart() {
        //Just for test.
        if (gameViewReady != null) {
            gameViewReady.actionPerformed(null);
        }
    }

    /**
     * Default setter of the piece position, updating the new coordinates.
     * @param newPiece definies the coordinates of each box of the newpiece.
     */
    public void setPiecePosition(Position[] newPiece) {
        //If the bloc shouldn't keep hide, pass position X or/and Y =-1
        for (int i = 0; i < 4; i++) {
            gameScreen1pPanel.remove(currentPiece[i]);
            gameScreen1pPanel.add(currentPiece[i], new AbsoluteConstraints(xPos(newPiece[i].getX()), yPos(newPiece[i].getY()), pieceSize, pieceSize));
        }
        for (int i = 0; i < 4; i++) {
            try {
                screen[currentPiece[i].getCX()][currentPiece[i].getCY()] = null;
            } catch (Exception ex) {
            }
        }
        for (int i = 0; i < 4; i++) {
            screen[newPiece[i].getX()][newPiece[i].getY()] = currentPiece[i];
            screen[newPiece[i].getX()][newPiece[i].getY()].setP(newPiece[i].getX(), newPiece[i].getY());
        }
        toggleVisiblePropOnGame();
    }

    /**
     * Default setter of the piece position, updating the new coordinates.
     * @param shadowPiece definies the coordinates of each box of the shadowpiece.
     */
    public void setShadowPosition(Position[] newPiece) {
        //If the bloc shouldn't keep hide, pass position X or/and Y =-1
        for (int i = 0; i < 4; i++) {
            gameScreen1pPanel.remove(shadowPiece[i]);
            gameScreen1pPanel.add(shadowPiece[i], new AbsoluteConstraints(xPos(newPiece[i].getX()), yPos(newPiece[i].getY()), pieceSize, pieceSize));
        }
        toggleVisiblePropOnGame();
    }

    /**
     * Deafult getter of the string with an relative path corresponding with
     * the parameter <em>color</em>.
     * @param color definies the color.
     * @return the relative path of the color.
     */
    public String getStringForColor(String color) {
        return "imgs/" + color + r.nextInt(5) + ".png";
    }

    /**
     * Reconfigures the one player screen in according to the action restart.
     */
    public void restart1pScreen() {
        if (clock != null) {
            clock.restart();
        } else {
            clock = new ClockTimer(timePassed);
        }
        gameScreen1pPanel.removeAll();
        gameNext1pPanel.removeAll();
        gameHold1pPanel.removeAll();
        screen = new JLabelCont[screenWidth][screenHeight + 3];
        currentPiece = new JLabelCont[4];
        nextPiece = new JLabelCont[4];
        removeGameOver(gameover);
        removeGameWin(gamewin);
    }

    /**
     * Generates the image of the new piece in the screen
     * @param newpiece defines the parameters of the new piece.
     * @param c defines the grafic features of the piece.
     */
    public void newFirstPiece(Position[] newpiece, String c) {
        for (int i = 0; i < 4; i++) {
            nextPiece[i] = new JLabelCont(new ImageIcon(getClass().getResource(getStringForColor(c))));
        }
        setNextPiecePosition(newpiece);
    }

    /**
     * Implements the case 'Game over', setting the panels game one player and
     * and game over: visible.
     * @return the panel showing the case 'game over'.
     */
    public JLabel showGameOver() {
        gameover.setVisible(true);
        game1pPanel.setVisible(false);
        game1pPanel.setVisible(true);
        return gameover;
    }

    /**
     * Implements the case 'Win', setting the panels game multiplayer
     *@return the panel showing the case 'win'.
     */
    public JLabel showGameWin() {
        gamewin.setVisible(true);
        game1pPanel.setVisible(false);
        game1pPanel.setVisible(true);
        return gamewin;
    }

    /**
     * Removes the panel configured to game, preparing the screen to another
     * cofiguration, restart for example.
     * @return the one player panel setted visible.
     */
    public void removeGameOver(JLabel gameover) {
        if (gameover != null) {
            game1pPanel.setVisible(false);
            game1pPanel.setVisible(true);
            gameover.setVisible(false);
        }
    }

    /**
     * Removes the panel configured to game, preparing the screen to another
     * cofiguration, restart for example.
     * @return the multiplayer panel setted visible.
     */
    public void removeGameWin(JLabel gameover) {
        if (gamewin != null) {
            game1pPanel.setVisible(false);
            game1pPanel.setVisible(true);
            gamewin.setVisible(false);
        }
    }

    /**
     * Creates the interface with the user showing the generated logical piece
     * in the screen with its features already defined.
     * @param newPosCurrentPiece defines the new position to the current piece.
     * @param newPosNextPiece defines the position to the next generates piece.
     * @param colorPiece defines the grafic features of the piece.
     */
    public void newPiece(Position[] newPosCurrentPiece, Position[] newPosNextPiece, String colorPiece, String shadowColor) {
        //This function receive the 4 positions of the new piece and her color
        if (shadowPiece[0] != null) {
            for (int i = 0; i < 4; i++) {
                gameScreen1pPanel.remove(shadowPiece[i]);
            }
        }
        for (int i = 0; i < 4; i++) {
            currentPiece[i] = nextPiece[i];
        }
        //Put the piece in the game
        if (currentPiece[0] != null) {
            for (int i = 0; i < 4; i++) {
                gameScreen1pPanel.add(currentPiece[i], new AbsoluteConstraints(xPos(newPosCurrentPiece[i].getX()), yPos(newPosCurrentPiece[i].getY()), pieceSize, pieceSize));
            }
            for (int i = 0; i < 4; i++) {
                try {
                    screen[currentPiece[i].getCX()][currentPiece[i].getCY()] = null;
                } catch (Exception ex) {
                }
            }
            for (int i = 0; i < 4; i++) {
                screen[newPosCurrentPiece[i].getX()][newPosCurrentPiece[i].getY()] = currentPiece[i];
                screen[newPosCurrentPiece[i].getX()][newPosCurrentPiece[i].getY()].setP(newPosCurrentPiece[i].getX(), newPosCurrentPiece[i].getY());
            }
            for (int i = 0; i < 4; i++) {
                gameNext1pPanel.remove(currentPiece[i]);
            }
            //Add one new piece in NextPiece box
        }
        for (int i = 0; i < 4; i++) {
            nextPiece[i] = new JLabelCont(new ImageIcon(getClass().getResource(getStringForColor(colorPiece))));
            shadowPiece[i] = new JLabelCont(new ImageIcon(getClass().getResource(getStringForColor("shadow"))));//shadowColor))));
        }
        setNextPiecePosition(newPosNextPiece);
        Main.getInstance().updateShadowPositions();
        toggleVisiblePropOnGame();
    }
    
    /**
     * Default setter of the position of the next piece.
     * @param newPosNextPiece defines the positons of the next piece.
     */
    private void setNextPiecePosition(Position[] newPosNextPiece) {
        Position min = Position.getMinCoord(newPosNextPiece);
        Position max = Position.getMaxCoord(newPosNextPiece);
        int yd = Y_screenPanel - ((-(max.getY() + min.getY() - 1)) * pieceSize) / 2;
        int xd = X_screenPanel + ((-(max.getX() + min.getX() + 1)) * pieceSize) / 2;
        for (int i = 0; i < 4; i++) {
            int xx = xd + (newPosNextPiece[i].getX()) * pieceSize;
            int yy = yd - (newPosNextPiece[i].getY()) * pieceSize;
            gameNext1pPanel.add(nextPiece[i], new AbsoluteConstraints(xx, yy, pieceSize, pieceSize));
        }
    }

    /**
     * Updates the holdPiece and the nextPiece, in according to the generation of
     * the new pieces.
     * @param currentPiecePos defines the position of the current piece.
     * @param holdPiecePos defines the position of the hold piece.
     * @param colorCurrentPiece defines the grafic features of the current piece.
     */
    public void holdPiece(Position[] currentPiecePos, Position[] holdPiecePos, String colorCurrentPiece) {
        //This function receive the 4 positions of the new piece and her color.

        //Remove the labels of the panels.
        for (int i = 0; i < 4; i++) {
            gameScreen1pPanel.remove(currentPiece[i]);
        }
        for (int i = 0; i < 4; i++) {
            gameHold1pPanel.remove(holdPiece[i]);
        }
        for (int i = 0; i < 4; i++) {
            try {
                screen[currentPiece[i].getCX()][currentPiece[i].getCY()] = null;
            } catch (Exception ex) {
            }
        }
        for (int i = 0; i < 4; i++) {
            holdPiece[i] = currentPiece[i];
        }
        for (int i = 0; i < 4; i++) {
            currentPiece[i] = new JLabelCont(new ImageIcon(getClass().getResource(getStringForColor(colorCurrentPiece))));
        }
        for (int i = 0; i < 4; i++) {
            gameScreen1pPanel.add(currentPiece[i], new AbsoluteConstraints(xPos(currentPiecePos[i].getX()), yPos(currentPiecePos[i].getY()), pieceSize, pieceSize));
        }
        for (int i = 0; i < 4; i++) {
            screen[currentPiecePos[i].getX()][currentPiecePos[i].getY()] = currentPiece[i];
            screen[currentPiecePos[i].getX()][currentPiecePos[i].getY()].setP(currentPiecePos[i].getX(), currentPiecePos[i].getY());
        }
        //Add one new piece in NextPiece box.
        setHoldPiecePosition(holdPiecePos);
        gameHold1pPanel.setOpaque(is2PlayerGame);
        gameHold1pPanel.setVisible(false);
        gameHold1pPanel.setVisible(true);
        gameScreen1pPanel.setVisible(false);
        gameScreen1pPanel.setVisible(true);
    }

    /**
     * Default setter of the position of the newPosHoldPiece.
     * @param newPosHoldPiece defines the parameters 
     */
    private void setHoldPiecePosition(Position[] newPosHoldPiece) {
        Position min = Position.getMinCoord(newPosHoldPiece);
        Position max = Position.getMaxCoord(newPosHoldPiece);
        int yd = Y_screenPanel - ((-(max.getY() + min.getY() - 1)) * pieceSize) / 2;
        int xd = X_screenPanel + ((-(max.getX() + min.getX() + 1)) * pieceSize) / 2;
        for (int i = 0; i < 4; i++) {
            int xx = xd + (newPosHoldPiece[i].getX()) * pieceSize;
            int yy = yd - (newPosHoldPiece[i].getY()) * pieceSize;
            System.out.println(holdPiece[i].getX());
            gameHold1pPanel.add(holdPiece[i], new AbsoluteConstraints(xx, yy, pieceSize, pieceSize));
        }
    }
    
    /**
     * Implements the actions of visualisation of the pieces.
     * @param newPosHoldPiece informes the current piece.
     */
    public void holdFirstPiece(Position[] newPosHoldPiece) {

        for (int i = 0; i < 4; i++) {
            try {
                screen[currentPiece[i].getCX()][currentPiece[i].getCY()] = null;
            } catch (Exception ex) {
            }
        }
        for (int i = 0; i < 4; i++) {
            gameScreen1pPanel.remove(currentPiece[i]);
        }
        for (int i = 0; i < 4; i++) {
            holdPiece[i] = currentPiece[i];
        }
        setHoldPiecePosition(newPosHoldPiece);
        gameHold1pPanel.setVisible(false);
        gameHold1pPanel.setVisible(true);
        gameScreen1pPanel.setVisible(false);
        gameScreen1pPanel.setVisible(true);
    }

    /**
     * Removes the specified line of the screen.
     * @param line specifies the line to be removed.
     */
    public void eraseLine(int line) {
        if (shadowPiece != null && shadowPiece[0] != null) {
            for (int i = 0; i < 4; i++) {
                shadowPiece[i].setVisible(false);
            }
        }
        //Lines begin in 0.
        int i, j;

        for (i = 0; i < screenWidth; i++) {
            if (screen[i][line] != null) {
                screen[i][line].setVisible(false);
                screen[i][line].setEnabled(false);
                gameScreen1pPanel.remove(screen[i][line]);
            } else {
            }
        }
        for (j = line; j < screenHeight; j++) {
            int count = 0;
            for (i = 0; i < screenWidth; i++) {
                screen[i][j] = screen[i][j + 1];
                if (screen[i][j] != null) {
                    screen[i][j].setP(i, j);
                    count++;
                    gameScreen1pPanel.remove(screen[i][j]);
                    gameScreen1pPanel.add(screen[i][j], new AbsoluteConstraints(xPos(i), yPos(j), pieceSize, pieceSize));
                }
            }
            for (i = 0; i < screenWidth; i++) {
                screen[i][j + 1] = null;
            }
            if (count == 0) {
                break;
            }
        }
        toggleVisiblePropOnGame();
    }

    /**
     * Sets the panel prepared to the game.
     */
    public void toggleVisiblePropOnGame() {
        gameNext1pPanel.setVisible(false);
        gameNext1pPanel.setVisible(true);
        gameScreen1pPanel.setVisible(false);
        gameScreen1pPanel.setVisible(true);
        game1pPanel.setVisible(false);
        game1pPanel.setVisible(true);
    }

    /**
     * Sets the score, the level and the score bar in screen with updated values.
     * @param newScore defines the actual value of the score.
     * @param newLevel defines the actual level of the game.
     * @param scoreMin defines the minimum value of the score.
     * @param scoreMax defines the maximum value of the score.
     */
    public void setScore(int newScore, int newLevel, int scoreMin, int scoreMax) {
        //If change of level, set the new level maximum score and change the label.
        score.setText(String.valueOf(newScore-initialScore));
        level.setText("Level:" + newLevel);
        scoreBar.setValue((newScore - scoreMin) * 100 / (scoreMax - scoreMin));
    }

    /**
     * Default setter of listeners to the keys configured to the actions.
     * @param newListener defines the key to be observed.
     */
    public void setKeyListener(KeyListener newListener) {
        gameScreen1pPanel.addKeyListener(newListener);
    }

    /**
     * Default setter of listeners to the mouse actions.
     * @param newListener defines the mouse to be observed.
     */
    public void setMouseListener(MouseListener newListener) {
        gameScreen1pPanel.addMouseListener(newListener);
    }

    /**
     * Default setter of listeners to the mouse actions.
     * @param newListener defines the mouse to be observed.
     */
    public void setMouseMotionListener(MouseMotionListener newListener) {
        gameScreen1pPanel.addMouseMotionListener(newListener);
    }
    
    //Internal use funcition.
    private int xPos(int newX) {
        return newX * pieceSize;
    }
    
    //Internal use funcition.
    private int yPos(int newY) {
        newY++;
        return gameScreen1pPanel.getHeight() - newY * pieceSize;
    }
    
    //listeners

    /**
     * It implements the listener to the game.
     */
    public static void addGameViewReady(ActionListener newGameViewReady) {
        gameViewReady = newGameViewReady;
    }

    /**
     * It implements the listener to new configurations.
     */
    public static void addConfigChanger(ActionListener newConfigChanger) {
        applyOptions.addActionListener(newConfigChanger);
    }
    /**
     * It implements the listener to new sounds.
     */
    public static void addSomChanger(ActionListener newSomChanger) {
        applySom.addActionListener(newSomChanger);
    }
  
    /**
     * Defines the list of players.
     */
    public void setPlayerList(Set<PlayerDescriptor> set) {
        ((DefaultListModel) (playersList.getModel())).removeAllElements();
        for (PlayerDescriptor pd : set) {
            ((DefaultListModel) (playersList.getModel())).addElement(pd);
        }
    }

    /**
     * Checks if the mouse was selected like control.
     * @return true if it was selected.
     */
    public boolean getMouseControler() {
        return mouseBox.isSelected();
    }
    
    /**
     * Default setter of two players screen.
     * @param isFilled
     */
    public void set2pScreenGame(int[] isFilled) {
        secondPlayerBoard.updateBoardDescription(isFilled);
    }
   
}
