/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetris_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

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
import javax.swing.border.TitledBorder;


/**
 *
 * @author Felipe
 */
public class Layout1 extends JFrame {

    private JPanel base, topPanel, initialPanel, selectionPanel, optionsPanel, somPanel, game1pPanel, game2pPanel;
    
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
    }
    //cria os panels usados

    private void make_top() {
        
        topPanel = new JPanel(new AbsoluteLayout());
        
        JToolBar toolbar = new JToolBar();
        
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
    }

    private void make_initial() {
        initialPanel = new JPanel(new AbsoluteLayout());
        JLabel menuImage = new JLabel();
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
        
        JTextField leftKey = new JTextField("Q");
        leftKey.setFont(new Font("Segoe Print", 0, 12));
        leftKey.setHorizontalAlignment(JTextField.CENTER);
        leftKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));
                
        JLabel moveRight = new JLabel("Move Right");
        moveRight.setFont(new Font("Segoe Print", 0, 12));
        
        JTextField rightKey = new JTextField("D");
        rightKey.setFont(new Font("Segoe Print", 0, 12));
        rightKey.setHorizontalAlignment(JTextField.CENTER);
        rightKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));
                
        JLabel moveDown = new JLabel("Move Down");
        moveDown.setFont(new Font("Segoe Print", 0, 12));
        
        JTextField downKey = new JTextField("S");
        downKey.setFont(new Font("Segoe Print", 0, 12));
        downKey.setHorizontalAlignment(JTextField.CENTER);
        downKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));
                
        JLabel rotate = new JLabel("Rotate");
        moveDown.setFont(new Font("Segoe Print", 0, 12));
        
        JTextField rotateKey = new JTextField("Z");
        rotateKey.setFont(new Font("Segoe Print", 0, 12));
        rotateKey.setHorizontalAlignment(JTextField.CENTER);
        rotateKey.setBackground(new Color(controlsPanel.getBackground().getRed(), controlsPanel.getBackground().getGreen(), controlsPanel.getBackground().getBlue()));
                
        
        JCheckBox mouseBox = new JCheckBox("Use mouse");
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
                
        JTextField playerName = new JTextField("ENSTA Project");
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
        JLabel optionTitle = new JLabel("Options");
        optionTitle.setFont(new java.awt.Font("Neuropol", 0, 24));
        
        JSeparator separator = new JSeparator();
        
        somPanel.add(optionTitle, new AbsoluteConstraints(110, 20, -1, -1));
        somPanel.add(separator, new AbsoluteConstraints(15, 50, 290, 11));
        //options of sounds
        
        JLabel themeTitle =new JLabel("Theme");
        themeTitle.setFont(new Font("Segoe Print", 0, 14));
        
        JComboBox themeBox=new JComboBox();
        themeBox.setFont(new Font("Segoe Print", 0, 12));
        themeBox.setModel(new DefaultComboBoxModel(new String[] { "Classic", "MarioBros", "PacMan", "Bomberman" }));
        
        JLabel volumeTitle =new JLabel("Volume");
        volumeTitle.setFont(new Font("Segoe Print", 0, 14));
        
        JSlider volumeSlider=new JSlider();
        
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
        JLabel menu = new JLabel("onde o jog 1player ocorre");
        game1pPanel.add(menu, new AbsoluteConstraints(0, 0));
        
    }

    private void make_game2p() {
        game2pPanel = new JPanel(new AbsoluteLayout());
        JLabel menu = new JLabel("ainda naum entendi como vai funcionar esta janela, sorry");
        game2pPanel.add(menu, new AbsoluteConstraints(0, 0));
        
    }

    private void make_base() {
        
        base = new JPanel(new AbsoluteLayout());
        base.add(topPanel, new AbsoluteConstraints(0, 0));
        base.add(initialPanel, new AbsoluteConstraints(0, 20,330,340));
        base.add(selectionPanel, new AbsoluteConstraints(0, 10, 330, 320));
        selectionPanel.setVisible(false);
        base.add(optionsPanel, new AbsoluteConstraints(0, 10,330,320));
        optionsPanel.setVisible(false);
        base.add(somPanel, new AbsoluteConstraints(0, 10,330,320));
        somPanel.setVisible(false);
        base.add(game1pPanel, new AbsoluteConstraints(0, 10,330,320));
        game1pPanel.setVisible(false);
        base.add(game2pPanel, new AbsoluteConstraints(0, 10,330,320));
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
    //funçoes ocorridas por Events

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
        /**atualiza hotkeys
        if (mouse.isSelected()) {
            //habilita movimento pelo mouse
        } else {
            //desabilita o mouse
        }
         * **/
        func_initial();
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
}
