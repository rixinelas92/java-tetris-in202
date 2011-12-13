/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetris_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

/**
 *
 * @author Felipe
 */
public class Layout1 extends JFrame {
    private JPanel base,topPanel,initialPanel,selectionPanel,optionsPanel,somPanel,game1pPanel,game2pPanel;
    private JCheckBox mouse;
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
    private void make_top(){

       topPanel = new JPanel(new AbsoluteLayout());
       
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

       toolbar.add(newgame, new AbsoluteConstraints(20, 20));
       toolbar.add(config, new AbsoluteConstraints(80, 20));
       toolbar.add(som, new AbsoluteConstraints(140, 20));
       toolbar.add(close, new AbsoluteConstraints(200, 20));
       toolbar.setBorderPainted(false);
       
       topPanel.add(toolbar, new AbsoluteConstraints(0,0));
    }
    private void make_initial(){
        initialPanel = new JPanel(new AbsoluteLayout());
        ImageIcon menuIcon=new ImageIcon("tetrismenu.png");
        JLabel menu=new JLabel("Imagem de abertura, com creditos dos criadores");
        initialPanel.add(menu, new AbsoluteConstraints(0,0));

    }
    private void make_selection(){
        selectionPanel = new JPanel(new AbsoluteLayout());

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

        selectionPanel.add(player1, new AbsoluteConstraints(0,0));
        selectionPanel.add(player2, new AbsoluteConstraints(80,0));



    }
    private void make_options(){
        try{
        optionsPanel = new JPanel(new AbsoluteLayout());
        JLabel menu=new JLabel("botoes de configuraçao de controles e nome default de jogador");
        
        JLabel controls = new JLabel("Controles");
        JLabel right = new JLabel("right");
        JTextField rightKey = new JTextField("D");
        JLabel left = new JLabel("left");
        JTextField leftKey = new JTextField("Q");
        JLabel rotate = new JLabel("rotate");
        JTextField rotateKey = new JTextField("Z");
        JLabel down = new JLabel("down");
        JTextField downKey = new JTextField("S");
        downKey.setBounds(60, 260, 40, 50);
        
        mouse = new JCheckBox("Control just using the mouse");
               
        JButton apply = new JButton("Apply");
        apply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                func_changeConfig();
            }});
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                func_initial();
            }});
        
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);
        
        optionsPanel.add(controls, new AbsoluteConstraints(0,0));
        optionsPanel.add(right, new AbsoluteConstraints(0,50));
        optionsPanel.add(rightKey, new AbsoluteConstraints(60,50));
        optionsPanel.add(left, new AbsoluteConstraints(120,50));
        optionsPanel.add(leftKey, new AbsoluteConstraints(180,50));
        optionsPanel.add(rotate, new AbsoluteConstraints(0,100));
        optionsPanel.add(rotateKey, new AbsoluteConstraints(60,100));
        optionsPanel.add(down, new AbsoluteConstraints(120,100));
        optionsPanel.add(downKey, new AbsoluteConstraints(180,100));
        
    
    }catch(Exception e){
            System.out.println("oi+++++++++++");
        }
        //optionsPanel.add(separator, BorderLayout.SOUTH);
        //optionsPanel.add(menu);

    }
    private void make_som(){
        somPanel = new JPanel(new AbsoluteLayout());
        JLabel menu=new JLabel("temas dos sons e opçao de volume");
        somPanel.add(menu,new AbsoluteConstraints(0,0));
        
         

    }
    private void make_game1p(){
       game1pPanel = new JPanel(new AbsoluteLayout());
        JLabel menu=new JLabel("onde o jog 1player ocorre");
        game1pPanel.add(menu,new AbsoluteConstraints(0,0));

    }
    private void make_game2p(){
        game2pPanel = new JPanel(new AbsoluteLayout());
        JLabel menu=new JLabel("ainda naum entendi como vai funcionar esta janela, sorry");
        game2pPanel.add(menu,new AbsoluteConstraints(0,0));

    }
    private void make_base(){
       
       base = new JPanel(new AbsoluteLayout());
       base.add(topPanel, new AbsoluteConstraints(0, 0));
       base.add(initialPanel, new AbsoluteConstraints(0,50));
       base.add(selectionPanel, new AbsoluteConstraints(50,0));
       selectionPanel.setVisible(false);
       base.add(optionsPanel, new AbsoluteConstraints(0,30));
       optionsPanel.setVisible(false);
       base.add(somPanel, new AbsoluteConstraints(50,0));
       somPanel.setVisible(false);
       base.add(game1pPanel, new AbsoluteConstraints(50,0));
       game1pPanel.setVisible(false);
       base.add(game2pPanel, new AbsoluteConstraints(50,0));
       game2pPanel.setVisible(false);
        
  }
    //cria a interface do JFrame
    private void make_UI(){
       getContentPane().setLayout(new AbsoluteLayout());
       add(base, new AbsoluteConstraints(0,0));
       pack();
       setTitle("layout1");
       setSize(360, 250);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setResizable(false);
      
       
    }
    //funçoes ocorridas por Events
    private void func_initial(){
       initialPanel.setVisible(true);
       selectionPanel.setVisible(false);
       optionsPanel.setVisible(false);
       somPanel.setVisible(false);
       game1pPanel.setVisible(false);
       game2pPanel.setVisible(false);
    }
    private void func_newgame(){
       initialPanel.setVisible(false);
       selectionPanel.setVisible(true);
       optionsPanel.setVisible(false);
       somPanel.setVisible(false);
       game1pPanel.setVisible(false);
       game2pPanel.setVisible(false);
    }
    private void func_config(){
       initialPanel.setVisible(false);
       selectionPanel.setVisible(false);
       optionsPanel.setVisible(true);
       somPanel.setVisible(false);
       game1pPanel.setVisible(false);
       game2pPanel.setVisible(false);
    }
    private void func_som(){
       initialPanel.setVisible(false);
       selectionPanel.setVisible(false);
       optionsPanel.setVisible(false);
       somPanel.setVisible(true);
       game1pPanel.setVisible(false);
       game2pPanel.setVisible(false);
    }
    private void func_1player(){
       initialPanel.setVisible(false);
       selectionPanel.setVisible(false);
       optionsPanel.setVisible(false);
       somPanel.setVisible(false);
       game1pPanel.setVisible(true);
       game2pPanel.setVisible(false);
    }
    private void func_2players(){
       initialPanel.setVisible(false);
       selectionPanel.setVisible(false);
       optionsPanel.setVisible(false);
       somPanel.setVisible(false);
       game1pPanel.setVisible(false);
       game2pPanel.setVisible(true);
    }
    private void func_exit(){
        System.exit(0);
    }
    
    private void func_changeConfig(){
       //atualiza hotkeys
        if(mouse.isSelected()){
            //habilita movimento pelo mouse
    }
        else{
        //desabilita o mouse
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
}
        