package Tetris_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;
import java.util.Set;
import javax.swing.Timer;
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
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;
import online.util.PlayerDescriptor;
import sound.SoundEffect;
import tetris.Controller;
import tetris.Main;
import tetris.Position;
import tetris.Screen;

public class Layout1 extends JFrame {

    private JPanel base, topPanel, initialPanel, selectionPanel, optionsPanel, somPanel, game1pPanel, game2pPanel;
    private JLabelCont[] currentPiece, nextPiece, holdPiece; //array with the position of the 4 boxes of the 2 pieces
    private JLabelCont[][] screen;// Sreen 10 x 13 with the pointer for all the lavels in use
    public Font neuropol14, neuropol24, segoePrint12, segoePrint11, planetBenson14, sevenSegments14;
    //constants
    private int pieceSize = 19, screenWidth = Screen.SIZE_X, screenHeight = Screen.SIZE_Y, levelScore = 20, levelScoreAnt = 0, scoreFactor = 50, levelNumber = 0;
    //options components
    private JTextField leftKey, rightKey, downKey, rotateKey, goToBottonKey, playerName;
    private JCheckBox mouseBox;
    private static JButton applyOptions, cancelOptions;
    //sound components
    private JComboBox themeBox;
    private JSlider volumeSlider;
    //1 players components
    private JPanel gameScreen1pPanel, gameNext1pPanel, gameHold1pPanel;
    private JProgressBar scoreBar;
    private JTextField score, timePassed;
    private JLabel level, gameover, tester;
    //listenets
    static ActionListener gameViewReady = null;
    private static final int X_BASE = 42;
    private static final int Y_BASE = 33;
    public Clock clock;
    Random r = new Random();
    private JList playersList;
    private int[] keys = Controller.keysStart;

    public Layout1() {

        make_fonts();
        make_top();
        make_initial();
        make_selection();
        make_options();
        make_som();
        make_game1p();
        make_game2p();
        make_base();
        make_UI();
        screen = new JLabelCont[screenWidth][screenHeight + 3];
        currentPiece = new JLabelCont[4];
        holdPiece = new JLabelCont[4];
    }
    //cria os panels usados

    private void make_fonts() {
        try {
          //  File f = new File("src/Tetris_interface/fightingspiritTBS.ttf");
          //  FileInputStream in = new FileInputStream(f);
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("fightingspiritTBS.ttf"));
            neuropol24 = dynamicFont.deriveFont(24f);
            neuropol14 = dynamicFont.deriveFont(18f);

         //   f = new File("src/Tetris_interface/segoepr.ttf");
         //   in = new FileInputStream(f);
            dynamicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("segoepr.ttf"));
            segoePrint12 = dynamicFont.deriveFont(12f);
            segoePrint11 = dynamicFont.deriveFont(11f);

       //     f = new File("src/Tetris_interface/PLANETBE.TTF");
       //     in = new FileInputStream(f);
            dynamicFont = Font.createFont(Font.TRUETYPE_FONT,  getClass().getResourceAsStream("PLANETBE.TTF"));
            planetBenson14 = dynamicFont.deriveFont(13f);

         //   f = new File("src/Tetris_interface/DS-DIGI.TTF");
         //   in = new FileInputStream(f);
            dynamicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("DS-DIGI.TTF"));
            sevenSegments14 = dynamicFont.deriveFont(18f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            newgame.setFont(segoePrint11);
            JButton config = new JButton("Configuration", configIcon);
            config.setFont(segoePrint11);
            JButton som = new JButton("Sound", somIcon);
            som.setFont(segoePrint11);
            JButton close = new JButton("Exit", closeIcon);
            close.setFont(segoePrint11);
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
            toolbar.add(config, new AbsoluteConstraints(70, 20));
            toolbar.add(som, new AbsoluteConstraints(120, 20));
            toolbar.add(close, new AbsoluteConstraints(160, 20));

            topPanel.add(toolbar, new AbsoluteConstraints(0, 0, -1, 30));
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

    private void make_selection() {
        selectionPanel = new JPanel(new AbsoluteLayout());

        JLabel selectionMenu = new JLabel("Game Mode");
        selectionMenu.setFont(neuropol24);

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

        selectionPanel.add(selectionMenu, new AbsoluteConstraints(100, 20));
        selectionPanel.add(separator, new AbsoluteConstraints(15, 50, 290, 11));

        selectionPanel.add(player1, new AbsoluteConstraints(2, 55, 320, 130));
        selectionPanel.add(player2, new AbsoluteConstraints(2, 182, 320, 130));

    }

    private void make_options() {

        optionsPanel = new JPanel(new AbsoluteLayout());

        JLabel optionTitle = new JLabel("Options");
        optionTitle.setFont(neuropol24);

        JSeparator separator = new JSeparator();

        optionsPanel.add(optionTitle, new AbsoluteConstraints(110, 20, -1, -1));
        optionsPanel.add(separator, new AbsoluteConstraints(15, 50, 290, 11));

        //controls panel
        JPanel controlsPanel = new JPanel(new AbsoluteLayout());
        controlsPanel.setBorder(BorderFactory.createTitledBorder(null, "Controls", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Sybil Green", 0, 14)));

        JLabel moveLeft = new JLabel("Move Left");
        moveLeft.setFont(segoePrint12);

        leftKey = new JTextField(KeyEvent.getKeyText(keys[0]));
        leftKey.setFont(segoePrint12);
        leftKey.setHorizontalAlignment(JTextField.CENTER);
        leftKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel moveRight = new JLabel("Move Right");
        moveRight.setFont(segoePrint12);

        rightKey = new JTextField(KeyEvent.getKeyText(keys[2]));
        rightKey.setFont(segoePrint12);
        rightKey.setHorizontalAlignment(JTextField.CENTER);
        rightKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel moveDown = new JLabel("Move Down");
        moveDown.setFont(segoePrint12);

        downKey = new JTextField(KeyEvent.getKeyText(keys[1]));
        downKey.setFont(segoePrint12);
        downKey.setHorizontalAlignment(JTextField.CENTER);
        downKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel rotate = new JLabel("Rotate");
        rotate.setFont(segoePrint12);

        rotateKey = new JTextField(KeyEvent.getKeyText(keys[4]));
        rotateKey.setFont(segoePrint12);
        rotateKey.setHorizontalAlignment(JTextField.CENTER);
        rotateKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));

        JLabel goBotton = new JLabel("To Botton");
        goBotton.setFont(segoePrint12);

        goToBottonKey = new JTextField(KeyEvent.getKeyText(keys[3]));
        goToBottonKey.setFont(segoePrint12);
        goToBottonKey.setHorizontalAlignment(JTextField.CENTER);
        goToBottonKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));


        mouseBox = new JCheckBox("Use mouse");
        mouseBox.setFont(segoePrint12);

        controlsPanel.add(moveLeft, new AbsoluteConstraints(15, 30, -1, -1));
        controlsPanel.add(leftKey, new AbsoluteConstraints(95, 32, 55, 25));

        controlsPanel.add(moveRight, new AbsoluteConstraints(160, 30, -1, -1));
        controlsPanel.add(rightKey, new AbsoluteConstraints(240, 32, 55, 25));

        controlsPanel.add(moveDown, new AbsoluteConstraints(15, 60, -1, -1));
        controlsPanel.add(downKey, new AbsoluteConstraints(95, 63, 55, 25));

        controlsPanel.add(rotate, new AbsoluteConstraints(160, 60, -1, -1));
        controlsPanel.add(rotateKey, new AbsoluteConstraints(240, 63, 55, 25));

        controlsPanel.add(goBotton, new AbsoluteConstraints(15, 90, -1, -1));
        controlsPanel.add(goToBottonKey, new AbsoluteConstraints(95, 91, 55, 25));

        controlsPanel.add(mouseBox, new AbsoluteConstraints(160, 91, -1, -1));

        optionsPanel.add(controlsPanel, new AbsoluteConstraints(10, 60, 310, 130));

        //begin of the player name panel
        JPanel playerPanel = new JPanel(new AbsoluteLayout());
        playerPanel.setBorder(BorderFactory.createTitledBorder(null, "Player Name", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Sybil Green", 0, 14)));

        playerName = new JTextField("ENSTA Project");
        playerName.setFont(segoePrint12);
        playerName.setHorizontalAlignment(JTextField.CENTER);
        playerName.setBorder(null);
        playerName.setBackground(new Color(playerPanel.getBackground().getRed(), playerPanel.getBackground().getGreen(), playerPanel.getBackground().getBlue()));



        playerPanel.add(playerName, new AbsoluteConstraints(12, 20, 260, 30));

        optionsPanel.add(playerPanel, new AbsoluteConstraints(10, 190, 310, 60));

        //bottons

        applyOptions = new JButton("Apply");
        applyOptions.setFont(planetBenson14);

        JButton cancelOptions = new JButton("Cancel");
        cancelOptions.setFont(planetBenson14);
        cancelOptions.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_initial();
            }
        });
        optionsPanel.add(applyOptions, new AbsoluteConstraints(60, 250, -1, -1));
        optionsPanel.add(cancelOptions, new AbsoluteConstraints(180, 250, -1, -1));


        goToBottonKey.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent fe) {
                getKeyEvent( 3, goToBottonKey);
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
    }





    private void make_som() {
        somPanel = new JPanel(new AbsoluteLayout());
        JLabel optionTitle = new JLabel("Sound");
        optionTitle.setFont(neuropol24);

        JSeparator separator = new JSeparator();

        somPanel.add(optionTitle, new AbsoluteConstraints(110, 20, -1, -1));
        somPanel.add(separator, new AbsoluteConstraints(15, 50, 290, 11));
        //options of sounds

        JLabel themeTitle = new JLabel("Theme");
        themeTitle.setFont(new Font("Segoe Print", 0, 14));

        themeBox = new JComboBox();
        themeBox.setFont(segoePrint12);
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
        apply.setFont(planetBenson14);
        apply.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                SoundEffect.setGlobalVolume(volumeSlider.getValue());
                func_initial();
            }
        });


        JButton cancel = new JButton("Cancel");
        cancel.setFont(planetBenson14);
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
        game1pTitle.setFont(neuropol24);

        JSeparator separator = new JSeparator();

        game1pPanel.add(game1pTitle, new AbsoluteConstraints(110, 20, -1, -1));
        game1pPanel.add(separator, new AbsoluteConstraints(5, 50, 330, 10));

        //panels

        gameScreen1pPanel = new JPanel(new AbsoluteLayout());
        gameScreen1pPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        gameScreen1pPanel.setFocusable(true);
        gameScreen1pPanel.setOpaque(false);

        gameNext1pPanel = new JPanel(new AbsoluteLayout());
        gameNext1pPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        gameHold1pPanel = new JPanel(new AbsoluteLayout());
        gameHold1pPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        game1pPanel.add(gameScreen1pPanel, new AbsoluteConstraints(10, 60, 10 * pieceSize, 20 * pieceSize));
        game1pPanel.add(gameNext1pPanel, new AbsoluteConstraints(220, 60, 85, 67));
        game1pPanel.add(gameHold1pPanel, new AbsoluteConstraints(220, 140, 85, 67));
        //game status
        scoreBar = new JProgressBar();
        scoreBar.setValue(0);
        game1pPanel.add(scoreBar, new AbsoluteConstraints(202, 340, 120, -1));

        level = new JLabel("Level: 0");
        level.setFont(neuropol14);
        game1pPanel.add(level, new AbsoluteConstraints(225, 320, -1, -1));

        score = new JTextField();
        score.setText("0");
        score.setFont(neuropol14); // NOI18N
        score.setHorizontalAlignment(JTextField.CENTER);
        score.setEditable(false);
        game1pPanel.add(score, new AbsoluteConstraints(232, 290, 60, 25));

        JLabel scoreLabel = new JLabel("SCORE");
        scoreLabel.setFont(neuropol14);
        game1pPanel.add(scoreLabel, new AbsoluteConstraints(230, 270, -1, -1));

        JLabel timeLabel = new JLabel("TIME");
        timeLabel.setFont(neuropol14);
        game1pPanel.add(timeLabel, new AbsoluteConstraints(235, 215, -1, -1));

        timePassed = new JTextField("00:00");
        timePassed.setBackground(new java.awt.Color(0, 0, 0));
        timePassed.setFont(sevenSegments14); // NOI18N
        timePassed.setForeground(new java.awt.Color(51, 255, 0));
        timePassed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timePassed.setEditable(false);
        game1pPanel.add(timePassed, new AbsoluteConstraints(232, 235, 60, 25));

        //buttons
        JButton pauseButton = new JButton("Pause");
        pauseButton.setFont(planetBenson14);
        pauseButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_pause();
            }
        });
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(planetBenson14);
        restartButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                func_restart();
            }
        });
        game1pPanel.add(pauseButton, new AbsoluteConstraints(212, 365, 100, 35));
        game1pPanel.add(restartButton, new AbsoluteConstraints(212, 405, 100, 35));

        ImageIcon backim = new ImageIcon(getClass().getResource("backGround.png"));

        JLabel back = new JLabel(backim);
        game1pPanel.add(back, new AbsoluteConstraints(10, 59, 10 * pieceSize, 20 * pieceSize));

        gameover = new JLabel(new ImageIcon(getClass().getResource("imgs/gameover.png")));
        //tester =new JLabel("");
        game1pPanel.add(gameover, new AbsoluteConstraints(-5, 220, -1, -1));
        game1pPanel.setComponentZOrder(gameover, 0);
        gameover.setVisible(false);
    }

    private void make_game2p() {
        game2pPanel = new JPanel(new AbsoluteLayout());

        JLabel game2pTitle = new JLabel("2 Player");
        game2pTitle.setFont(neuropol24);

        JSeparator separator = new JSeparator();

        game2pPanel.add(game2pTitle, new AbsoluteConstraints(110, 20, -1, -1));



        playersList = new JList();
        playersList.setCellRenderer(new MyCellRenderer());
        playersList.setModel(new DefaultListModel());
        JScrollPane scrollPane = new JScrollPane(playersList);

        game2pPanel.add(scrollPane, new AbsoluteConstraints(10, 60, 300, 250));

        Main.start2pConnection();

        //      game2pPanel = new JPanel(new AbsoluteLayout());
        //    JLabel menu = new JLabel("ainda naum entendi como vai funcionar esta janela, sorry");
        //  game2pPanel.add(menu, new AbsoluteConstraints(0, 0));

    }

    private void make_base() {

        base = new JPanel(new AbsoluteLayout());
        base.add(topPanel, new AbsoluteConstraints(0, 0));
        base.add(initialPanel, new AbsoluteConstraints(0, 0, 330, 450));
        base.add(selectionPanel, new AbsoluteConstraints(0, 10, 330, 450));
        selectionPanel.setVisible(false);
        base.add(optionsPanel, new AbsoluteConstraints(0, 10, 330, 450));
        optionsPanel.setVisible(false);
        base.add(somPanel, new AbsoluteConstraints(0, 10, 330, 450));
        somPanel.setVisible(false);
        base.add(game1pPanel, new AbsoluteConstraints(0, 10, 330, 450));
        game1pPanel.setVisible(false);
        base.add(game2pPanel, new AbsoluteConstraints(0, 10, 330, 450));
        game2pPanel.setVisible(false);

    }
    //cria a interface do JFrame

    private void make_UI() {
        getContentPane().setLayout(new AbsoluteLayout());
        add(base, new AbsoluteConstraints(0, 0));
        pack();
        setTitle("Tetris");
        setSize(330, 490);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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

    public void func_newgame() {
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

        Main.pauseGame();
        Main.removeListeners();

    }

    private void func_som() {

        initialPanel.setVisible(false);
        selectionPanel.setVisible(false);
        optionsPanel.setVisible(false);
        somPanel.setVisible(true);
        game1pPanel.setVisible(false);
        game2pPanel.setVisible(false);
        Main.pauseGame();
        Main.removeListeners();

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


    private void checkAndIfCaseSetOtherKeyOnConfig(int keyNumber, int keyValue){
        for(int i = 0;i<keys.length;i++){
            if(keyNumber == i)
                continue;
            if(keyValue == keys[i]){
                switch(i){
                    case 0:
                        getKeyEvent(0,leftKey);
                        break;
                    case 1:
                        getKeyEvent(1,downKey);
                        break;
                    case 2:
                        getKeyEvent(2,rightKey);
                        break;
                    case 3:
                        getKeyEvent(3,goToBottonKey);
                        break;
                    case 4:
                        getKeyEvent(4,rotateKey);
                        break;
                    default:
                        break;

                }
            }
        }
    }

    private void getKeyEvent(int keyNumber, JTextField field) {
        KeyListener kl = new KeyListener() {

            int keyNumber;
            JTextField field;

            public void keyTyped(KeyEvent ke) {
            }

            public KeyListener setKeyNumberAndField(int keyNumber, JTextField field) {
                this.keyNumber = keyNumber;
                this.field = field;
                return this;
            }

            public void keyPressed(KeyEvent ke) {
                keys[keyNumber] = ke.getKeyCode();
                field.setText(ke.getKeyText(ke.getKeyCode()));
                field.setBackground(Color.LIGHT_GRAY);
                removeKeyListener(this);
                checkAndIfCaseSetOtherKeyOnConfig(keyNumber,ke.getKeyCode());
            }

            public void keyReleased(KeyEvent ke) {
            }

            @Override
            public int hashCode() {
                return keyNumber;
            }
        }.setKeyNumberAndField(keyNumber, field);
        addKeyListener(kl);

        field.setBackground(Color.yellow);
        requestFocusInWindow();

    }

    private void func_exit() {
        Main.terminateInternetConnection();
        System.exit(0);
    }

    public int[] getConfigChange() {
        return keys;
    }

    private void func_pause() {
        Main.togglePause();
    }

    private void func_restart() {
        //just for test
        if (gameViewReady != null) {
            gameViewReady.actionPerformed(null);
        }

    }
    //publics functions

    public void setPiecePosition(Position[] newPiece) {
        //if the bloc shouldn't keep hide, pass position X or/and Y =-1
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
        gameScreen1pPanel.setVisible(false);
        gameScreen1pPanel.setVisible(true);

    }

    public String getStringForColor(String color) {
        return "imgs/"  + color + r.nextInt(5) + ".png";
    }

    public void restart1pScreen() {
        if (clock != null) {
            clock.restart();
        } else {
            clock = new Clock();
        }
        gameScreen1pPanel.removeAll();
        gameNext1pPanel.removeAll();
        gameHold1pPanel.removeAll();
        screen = new JLabelCont[screenWidth][screenHeight + 3];
        currentPiece = new JLabelCont[4];
        nextPiece = new JLabelCont[4];

    }

    public void newFirstPiece(Position[] newpiece, String c) {
        for (int i = 0; i < 4; i++) {
            nextPiece[i] = new JLabelCont(new ImageIcon(getClass().getResource(getStringForColor(c))));
        }
        setNextPiecePosition(newpiece);
    }

    public JLabel showGameOver() {

        game1pPanel.setVisible(false);
        game1pPanel.setVisible(true);
        game1pPanel.setComponentZOrder(gameover, 0);
        gameover.setVisible(true);
        return gameover;
    }

    public void removeGameOver(JLabel gameover) {
        if (gameover != null) {
            game1pPanel.setVisible(false);
            game1pPanel.setVisible(true);
            gameover.setVisible(false);
        }
    }

    public void newPiece(Position[] newPosCurrentPiece, Position[] newPosNextPiece, String colorPiece) {
        //this function receive the 4 positions of the new piece and her color
        for (int i = 0; i < 4; i++) {
            currentPiece[i] = nextPiece[i];
        }
        //put the piece in the game
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
            //add one new piece in NextPiece box   
        }

        for (int i = 0; i < 4; i++) {
            nextPiece[i] = new JLabelCont(new ImageIcon(getClass().getResource(getStringForColor(colorPiece))));
        }
        setNextPiecePosition(newPosNextPiece);
        toggleVisiblePropOnGame();
    }

    private void setNextPiecePosition(Position[] newPosNextPiece) {
        Position min = Position.getMinCoord(newPosNextPiece);
        Position max = Position.getMaxCoord(newPosNextPiece);
        int yd = Y_BASE - ((-(max.getY() + min.getY() - 1)) * pieceSize) / 2;
        int xd = X_BASE + ((-(max.getX() + min.getX() + 1)) * pieceSize) / 2;
        for (int i = 0; i < 4; i++) {
            int xx = xd + (newPosNextPiece[i].getX()) * pieceSize;
            int yy = yd - (newPosNextPiece[i].getY()) * pieceSize;
            gameNext1pPanel.add(nextPiece[i], new AbsoluteConstraints(xx, yy, pieceSize, pieceSize));
        }
    }

    public void holdPiece(Position[] currentPiecePos, Position[] holdPiecePos, String colorCurrentPiece) {
        //this function receive the 4 positions of the new piece and her color
        JLabelCont[] auxPiece;
//remove the labels of the panels
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

        //add one new piece in NextPiece box

        setHoldPiecePosition(holdPiecePos);
        gameHold1pPanel.setVisible(false);
        gameHold1pPanel.setVisible(true);
        gameScreen1pPanel.setVisible(false);
        gameScreen1pPanel.setVisible(true);
    }

    private void setHoldPiecePosition(Position[] newPosHoldPiece) {
        Position min = Position.getMinCoord(newPosHoldPiece);
        Position max = Position.getMaxCoord(newPosHoldPiece);
        int yd = Y_BASE - ((-(max.getY() + min.getY() - 1)) * pieceSize) / 2;
        int xd = X_BASE + ((-(max.getX() + min.getX() + 1)) * pieceSize) / 2;
        for (int i = 0; i < 4; i++) {
            int xx = xd + (newPosHoldPiece[i].getX()) * pieceSize;
            int yy = yd - (newPosHoldPiece[i].getY()) * pieceSize;
            System.out.println(holdPiece[i].getX());
            gameHold1pPanel.add(holdPiece[i], new AbsoluteConstraints(xx, yy, pieceSize, pieceSize));
        }
    }

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

    public void eraseLine(int line) {

        //linhas começam do zero
        int i, j;

        for (j = screenHeight - 1; j >= 0; j--) {
            for (i = 0; i < screenWidth; i++) {
                if (screen[i][j] != null) {
                    System.out.print(".");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        for (i = 0; i < screenWidth; i++) {
            if (screen[i][line] != null) {
                screen[i][line].setVisible(false);
                screen[i][line].setEnabled(false);
                gameScreen1pPanel.remove(screen[i][line]);
            } else {
                System.out.println("   +++++ " + i + " " + line);
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

    public void toggleVisiblePropOnGame() {
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

    public static void addConfigChanger(ActionListener newConfigChanger) {
        applyOptions.addActionListener(newConfigChanger);
    }

    public void setPlayerList(Set<PlayerDescriptor> set) {
        ((DefaultListModel) (playersList.getModel())).removeAllElements();
        for (PlayerDescriptor pd : set) {
            ((DefaultListModel) (playersList.getModel())).addElement(pd);
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Layout1 ex = new Layout1();
                ex.setVisible(true);
            }
        });
    }

    public boolean getMouseControler() {
        return mouseBox.isSelected();
    }

    public class JLabelCont extends JLabel {

        int x = -1;
        int y = -1;

        private JLabelCont(ImageIcon imageIcon) {
            super(imageIcon);
        }

        public void setP(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getCX() throws Exception {
            if (x == -1) {
                throw new Exception("x null");
            }
            return x;
        }

        public int getCY() throws Exception {
            if (y == -1) {
                throw new Exception("y null");
            }
            return y;
        }
    }

    public class Clock implements ActionListener {

        Timer timer;
        long time;
        static final int delay = 500;

        public Clock() {
            timer = new Timer(delay, this);
            time = 0;
            timer.start();
        }

        public void restart() {
            time = 0;
            timer.restart();
            timePassed.setText(String.format("%1$tM:%1$tS", time, time));
        }

        public void actionPerformed(ActionEvent ae) {
            time += delay;
            timePassed.setText(String.format("%1$tM:%1$tS", time, time));
        }

        public void togglePause() {
            if (!timer.isRunning()) {
                timer.start();
            } else {
                timer.stop();
            }
        }

        public void pauseScreen() {
            timer.stop();
        }
    }

    static class MyCellRenderer extends JLabel implements ListCellRenderer {

        final static ImageIcon offline = new ImageIcon(MyCellRenderer.class.getResource("OFFLINE.png"));
        final static ImageIcon playing = new ImageIcon(MyCellRenderer.class.getResource("PLAYING.png"));
        final static ImageIcon online = new ImageIcon(MyCellRenderer.class.getResource("ONLINE.png"));

        // This is the only method defined by ListCellRenderer.
        // We just reconfigure the JLabel each time we're called.
        public Component getListCellRendererComponent(
                JList list,
                Object value, // value to display
                int index, // cell index
                boolean isSelected, // is the cell selected
                boolean cellHasFocus) // the list and the cell have the focus
        {

            PlayerDescriptor player = (PlayerDescriptor) value;


            String s = value.toString();
            setText(s);

            switch (player.getState()) {
                case OFFLINE:
                    setIcon(offline);
                    break;
                case ONLINE:
                    setIcon(online);
                    break;
                case PLAYING:
                    setIcon(playing);
                    break;
            }
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            return this;
        }
    }
}
