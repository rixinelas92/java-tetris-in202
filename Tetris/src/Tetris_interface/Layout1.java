/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetris_interface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author Felipe
 */
public class Layout1 extends JFrame {
    private JPanel base,topPanel,initialPanel,selectionPanel,optionsPanel,somPanel,game1pPanel,game2pPanel;
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
    public final void make_top(){

       topPanel = new JPanel(new BorderLayout());
       JToolBar toolbar = new JToolBar();

       ImageIcon newgameIcon = new ImageIcon(getClass().getResource("newgame.png"));
       ImageIcon configIcon = new ImageIcon(getClass().getResource("config.png"));
       ImageIcon somIcon = new ImageIcon(getClass().getResource("som.png"));
       ImageIcon closeIcon = new ImageIcon(getClass().getResource("close.png"));

       JButton newgame = new JButton("New Game", newgameIcon);
       JButton config = new JButton("Configuration", configIcon);
       JButton som = new JButton("Som",somIcon);
       JButton close = new JButton("Exit",closeIcon);

       newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                func_newgame();
            }});

       config.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                func_config();
            }});

       som.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                func_som();
            }});

       close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                func_exit();
            }});

       toolbar.add(newgame);
       toolbar.add(config);
       toolbar.add(som);
       toolbar.add(close);
       toolbar.setBorderPainted(false);
       topPanel.add(toolbar,BorderLayout.NORTH);
    }
    public final void make_initial(){
        initialPanel = new JPanel(new BorderLayout());
        ImageIcon menuIcon=new ImageIcon("tetrismenu.png");
        JLabel menu=new JLabel("Imagem de abertura, com creditos dos criadores");
        initialPanel.add(menu);

    }
    public final void make_selection(){
        selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton player1 = new JButton("1 Player");
        player1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                func_1player();
            }});

        JButton player2 = new JButton("2 Player");
        player2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                func_2players();
            }});

        selectionPanel.add(player1);
        selectionPanel.add(player2);



    }
    public final void make_options(){
        optionsPanel = new JPanel(new BorderLayout());
        JLabel menu=new JLabel("botoes de configuraçao de controles e nome default de jogador");
        optionsPanel.add(menu);

    }
    public final void make_som(){
        somPanel = new JPanel(new BorderLayout());
        JLabel menu=new JLabel("temas dos sons e opçao de volume");
        somPanel.add(menu);

    }
    public final void make_game1p(){
        game1pPanel = new JPanel(new BorderLayout());
        JLabel menu=new JLabel("onde o jog 1player ocorre");
        game1pPanel.add(menu);

    }
    public final void make_game2p(){
        game2pPanel = new JPanel(new BorderLayout());
        JLabel menu=new JLabel("ainda naum entendi como vai funcionar esta janela, sorry");
        game2pPanel.add(menu);

    }
    public final void make_base(){
       base = new JPanel();
       base.setLayout(new BoxLayout(base, BoxLayout.Y_AXIS));

       base.add(topPanel);
       base.add(initialPanel);
       base.add(selectionPanel);
       selectionPanel.setVisible(false);
       base.add(optionsPanel);
       optionsPanel.setVisible(false);
       base.add(somPanel);
       somPanel.setVisible(false);
       base.add(game1pPanel);
       game1pPanel.setVisible(false);
       base.add(game2pPanel);
       game2pPanel.setVisible(false);

  }
    //cria a interface do JFrame
    public final void make_UI(){
       add(base);
       pack();
       setTitle("layout1");
       setSize(360, 250);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    //funçoes ocorridas por Events
    public void func_newgame(){
       initialPanel.setVisible(false);
       selectionPanel.setVisible(true);
       optionsPanel.setVisible(false);
       somPanel.setVisible(false);
       game1pPanel.setVisible(false);
       game2pPanel.setVisible(false);
    }
    public void func_config(){
       initialPanel.setVisible(false);
       selectionPanel.setVisible(false);
       optionsPanel.setVisible(true);
       somPanel.setVisible(false);
       game1pPanel.setVisible(false);
       game2pPanel.setVisible(false);
    }
    public void func_som(){
       initialPanel.setVisible(false);
       selectionPanel.setVisible(false);
       optionsPanel.setVisible(false);
       somPanel.setVisible(true);
       game1pPanel.setVisible(false);
       game2pPanel.setVisible(false);
    }

    public void func_1player(){
       initialPanel.setVisible(false);
       selectionPanel.setVisible(false);
       optionsPanel.setVisible(false);
       somPanel.setVisible(false);
       game1pPanel.setVisible(true);
       game2pPanel.setVisible(false);
    }
    public void func_2players(){
       initialPanel.setVisible(false);
       selectionPanel.setVisible(false);
       optionsPanel.setVisible(false);
       somPanel.setVisible(false);
       game1pPanel.setVisible(false);
       game2pPanel.setVisible(true);
    }
    public void func_exit(){
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Layout1 ex = new Layout1();
                ex.setVisible(true);
            }
        });
    }
}
        