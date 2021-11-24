package com.company;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {
    static  final  int Scrn_Height = 600;
    static  final  int Scrn_Width = 600;
    static  final  int Unit_Size = 25;
    static  final  int Game_Units = (Scrn_Height*Scrn_Width)/Unit_Size;
    static final int delay = 100;
    final int x[] = new int[Unit_Size];
    final int y[] = new int[Unit_Size];
    int bodyParts = 6,appleEaten,appleX,appleY;
    char direction = 'R';
    boolean running = true;
    Timer timer;
    Random random;

    public GamePanel(){

        random = new Random();
        this.setPreferredSize(new Dimension(Scrn_Width,Scrn_Height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();



    }





    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
       super.paintComponent(g);
       draw(g);

    }
    public void move(){
        for (int i = bodyParts; i >0 ; i--) {
            y[i] = y[i-1];
            x[i] = x[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0]-Unit_Size;
                break;
            case 'D':
                y[0] = y[0]+Unit_Size;
                break;
            case 'L':
                x[0] = x[0]-Unit_Size;
                break;
            case 'R':
                x[0] = x[0]+Unit_Size;
                break;
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(Scrn_Width/Unit_Size))*Unit_Size;
        appleY = random.nextInt((int)(Scrn_Height/Unit_Size))*Unit_Size;

    }
    public void checkApple(){
        if (x[0]==appleX&&y[0]==appleY){
            bodyParts++;
            appleEaten++;
            newApple();

        }
    }
    public void checkCollisions(){
        for (int i = bodyParts; i >0; i--) {
            if ((x[0]==x[i]&&y[0]==y[i] )){
                running = false;
            }
        }
        if (x[0]<0)
            running = false;
        if(x[0]>Scrn_Width)
            running = false;
        if (y[0]<0)
            running = false;
        if(y[0]>Scrn_Height)
            running = false;
        if (!running)
            timer.stop();

    }
    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score "+appleEaten,(Scrn_Width-metrics1.stringWidth("Score"+appleEaten))/2,g.getFont().getSize());

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(Scrn_Width-metrics.stringWidth("game Over"))/2,Scrn_Height/2);
    }
    public void draw(Graphics g) {
        if (running) {
            for (int i = 0; i < Scrn_Height / Unit_Size; i++) {
                g.drawLine(i * Unit_Size, 0, i * Unit_Size, Scrn_Height);
                g.drawLine(0, i * Unit_Size, Scrn_Width, i * Unit_Size);

            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, Unit_Size, Unit_Size);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], Unit_Size, Unit_Size);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], Unit_Size, Unit_Size);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score "+appleEaten,(Scrn_Width-metrics.stringWidth("Score"+appleEaten))/2,g.getFont().getSize());

        }else{
            gameOver(g);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public  class  MyKeyAdapter extends KeyAdapter{
        public  void  keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case  KeyEvent.VK_LEFT:
                    if (direction!='R') {
                        direction = 'L';
                    }
                    break;
                case  KeyEvent.VK_RIGHT:
                    if (direction!='L'){
                        direction = 'R';
                    }
                    break;
                case  KeyEvent.VK_UP:
                    if (direction!='D') {
                        direction = 'U';
                    }
                    break;
                case  KeyEvent.VK_DOWN:
                    if (direction!='U') {
                        direction = 'D';
                    }
                    break;

            }
        }
    }
}
