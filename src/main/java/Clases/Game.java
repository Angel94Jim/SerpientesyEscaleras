package Clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 320;
    public static final int HEIGHT= WIDTH/12*9; //240
    public static final int SCALE=2;
    public final String TITLE="Tablero: Serpientes y Escaleras";

    private  boolean running =false;
    private  Thread thread;

    private BufferedImage image= new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;
    private BufferedImage fondo = null;

    //temp
    private BufferedImage player;
    private Player p;

    public void init()
    {
        requestFocus();
        BufferedImageLoader loader=new BufferedImageLoader();

        try{
            fondo = loader.loadImage("/tapete.png");
            spriteSheet=loader.loadImage("/player.png");
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        addKeyListener(new KeyInput(this));

        p =new Player(550,400,this);
        SpriteSheet ss=new SpriteSheet(spriteSheet);
    }


    private synchronized void start()
    {
        if(running)
            return;

        running=true;
        thread=new Thread(this);
        thread.start();
    }

    private synchronized void stop()
    {
        if(!running)
            return;

        running=false;
        try { thread.join();
        }catch(InterruptedException e)
        {e.printStackTrace();}//Si por alguna razon falla nos comenta error

        System.exit(1);
    }

    public void run() // runnable game loop
    {
        init();
        long lastTime = System.nanoTime(); //variables de timpo para fps
        final double amountOfTicks=60.0;
        double ns= 1000000000/amountOfTicks;
        double delta=0;
        int updates=0;
        int frames=0;
        long timer=System.currentTimeMillis();

        while(running)
        {
            // Game loop
            long now = System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime=now;

            if(delta >=1)
            {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis()-timer>1000)
            {
                timer+=1000;
                System.out.println(updates+"Ticks , FPS "+frames);
                updates=0;
                frames=0;
            }
        }
        stop();
    }

    private void tick()
    {
       p.tick();

    }

    private void render()
    {
        BufferStrategy bs= this.getBufferStrategy();

        if(bs==null)
        {
            createBufferStrategy(3);// 3 buffer
            return;

        }

        Graphics g = bs.getDrawGraphics();
        //dibujar

        g.drawImage(image,0,0,getWidth(),getHeight(),this);
        g.drawImage(fondo,0,0,getWidth(),getHeight(), this);
        p.render(g);
        //dibujar
        g.dispose();
        bs.show();

    }

    public void keyPressed(KeyEvent e) {

        int key=e.getKeyCode();

        if(key == KeyEvent.VK_RIGHT)
        {
            p.setVelX(5);
        }else if(key == KeyEvent.VK_LEFT)
        {
            p.setVelX(-5);
        }else if(key == KeyEvent.VK_DOWN)
        {
            p.setVelY(5);
        }else if(key == KeyEvent.VK_UP)
        {
            p.setVelY(-5);
        }
    }


    public void keyReleased(KeyEvent e) {

        int key=e.getKeyCode();

        if(key == KeyEvent.VK_RIGHT)
        {
            p.setVelX(0);
        }else if(key == KeyEvent.VK_LEFT)
        {
            p.setVelX(0);
        }else if(key == KeyEvent.VK_DOWN)
        {
            p.setVelY(0);
        }else if(key == KeyEvent.VK_UP)
        {
            p.setVelY(0);
        }


    }





    public static  void main(String args[]){

        String filepath= "C:/Users/Fam/IdeaProjects/Serpientes&Escaleras/src/main/Resources/game.wav";

        MusicStaff music=new MusicStaff();
        music.PlayMusic(filepath);

        Game game=new Game();

        //creando dimensiones
        game.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        game.setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        game.setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));

        JFrame frame =new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Boton

        Boton boton1=new Boton();

        boton1.repaint();
        boton1.setBounds(0,0,300,120);
        boton1.setVisible(true);
        boton1.setResizable(false);
        boton1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JLabel

        JLabel label=new JLabel("Tu turno de lanza: ");
        JPanel panel= new JPanel();
        frame.add(panel);
        panel.add(label);

        game.start();
    }

    public BufferedImage getSpriteSheet(){
    return spriteSheet;
    }
}
