import java.awt.*;
import java.applet.*;
import java.io.*;
import javax.sound.sampled.*;
import java.awt.event.*;
/**
 * The bat sprite/ controllable object in Cave Rave
 * 
 * @author (Matt Barton) 
 * @version (10/24/16)
 */
public class Bat extends Applet implements Runnable, KeyListener
{
    Thread flapper;
    private int time, x, y, n, t, y2, y3, y4, w, w2, w3, q, z, r;
    private double f;
    Image bat[] = new Image[2];
    Image currentimg;
    AudioClip flaps, ghost;
    public void init()
    {
        String move[] = {"bat1.png","bat2.png"}; //image names here
        for (int i=0; i < bat.length; i++)
        {
            bat[i] = getImage(getCodeBase(), move[i]);
        }
        q = 0;
        f = 3000;
        t = 500;
        y2 = 250;
        z = 0;
        w = 50;
        r = 0;

        time = 200; //this will be the amount that I pause between pictures
        x=10;
        y=230;
        n=500;
        flaps = getAudioClip(getCodeBase(), "dbg.wav"); //sound file here
        ghost = getAudioClip(getCodeBase(), "ghost.wav");
        addKeyListener(this);
        setFocusable(true);
    }

    public void start()
    {
        if (flapper == null)
        {
            flapper = new Thread(this);
            flapper.start();
        }
    }

    public void stop()
    {
        if (flapper != null)
        {
            if (flaps != null) flaps.stop();
            //flapper.stop(); //do I need this?
            flapper = null;
        }
    }
    // I made these methods, pause and transform, as shortcuts 
    // I don't have to write all that out everytime now in the run section
    void pause(int microseconds)
    {
        try{ Thread.sleep(microseconds);}
        catch(InterruptedException e){}
    }

    void slay()
    {
        //currentimg = bat[0]; and I could pause it and stuff
        if(r==1){
            while(f>=3000 && f<=10000)
                f+=10;
            if(t>0)
                t-=f/300;
            else
            {
                t+=500;
                y2 = (int)(400*Math.random());
                y3 = (int)(400*Math.random());
                y4= (int)(400*Math.random());
                w = (int)(70*Math.random())+30;
                w2 = (int)(70*Math.random())+30;
                w3= (int)(70*Math.random())+30;

                q++;
            }
            if((x+40 >= t && x<=t) && (y+40 >=y2 && y<=y2))
            {
                x = y = t = y2 = 0;
                z=100;

            }
        }
    }

    void transform(int amtOfTimes)
    {
        for (int i = amtOfTimes; i>0; i--)
        {
            currentimg = bat[0]; //0 is really the first one
            repaint();
            pause(time); //i didn't need to make a variable for this but I did for fun. :p
            currentimg = bat[1];
            repaint();
            pause(time);

        }
    }

    public void run()
    {
        setBackground(Color.white);
        if(ghost !=null) 
            ghost.loop();
        //checked first if flaps existed as an AudioClip 
        //otherwise it would try to play nothing
        while(true)
        {//if I wanted it to start a certain way, I could be like

            slay();
            repaint();
            transform(1);

        }
    }

    public void keyPressed(KeyEvent ke)
    {
        int key = ke.getKeyCode();

        switch(key)
        {
            case KeyEvent.VK_LEFT:
            if(x>=5)
                x-=5;
            break;

            case KeyEvent.VK_RIGHT:
            if(x<=450)
                x+=5;
            break;

            case KeyEvent.VK_UP:
            if(y>=5)
                y-=5;
            break;

            case KeyEvent.VK_DOWN:
            if(y<=450)
                y+=5;
            break;

            //how to allow for diagonal movement?
            //this will be a pause button
            case KeyEvent.VK_P:
            if(flaps!=null)
                flaps.stop();            
            break;

            case KeyEvent.VK_ENTER:
            if(ghost!=null)
            {
                ghost.stop();
                if(r==0)
                    r+=1;

                if (ghost!=null)
                    flaps.loop();
                if (r==1)n-=500;
            }

        }
    }

    public void keyReleased(KeyEvent ke)
    {
        int key = ke.getKeyCode();
        switch(key)
        {   case KeyEvent.VK_LEFT:
            if(x>=115)
                x-=25;
            break;

            case KeyEvent.VK_RIGHT:
            if(x<=450)
                x+=25;
            break;

            case KeyEvent.VK_UP:
            if(y>=15)
                y-=25;
            break;

            case KeyEvent.VK_DOWN:
            if(y<=450)
                y+=25;
            break;

            //how to allow for diagonal movement?
            //this will be a pause button
            case KeyEvent.VK_P:
            if(flaps!=null)
                flaps.stop();            
            break;
        }
    }

    public void keyTyped(KeyEvent ke)
    {
    }

    public void paint(Graphics g)
    {
        int rr = (int)(256*Math.random());
        int gg = (int)(256*Math.random());
        int bb = (int)(256*Math.random());
        int rn = (int)(30*Math.random());
        g.setColor(new Color(4*rn/5+50,4*rn/5+50,rn+50));
        g.fillRect(0,0,500,500);

        g.setColor(new Color(rr,gg,bb));
        g.fillRect(t,y2,w,w);
        if(q>1)
        {
            g.fillRect(t,y3,w2,w2);
            if(((x+40 >= t && x<=t)||(x+40>=t+w2 && x<=t+w2)) && ((y+40 >=y3 && y<=y3)||
                (y+40>=y3+w2 && y<= y3+w2)))
            {
                x = y = t = w2 = 0; 
                t+=0;
                z=100;
                if(flaps !=null)
                    flaps.stop();
            }
        }
        if(q>3)
        {
            g.fillRect(t,y4,w3,w3);
            if(((x+40 >= t && x<=t)||(x+40>=t+w3 && x<=t+w3)) && ((y+40 >=y4 && y<=y4)||
                (y+40>=y4+w3 && y<= y4+w3)))
            {
                x = y = t = w3 = 0; 
                t+=0;
                z=100;
                if(flaps !=null)
                    flaps.stop();
            }
        }
        if (q>5)
        {
            int b7 = rn+(y4+y3)/2;
            int b8 = rn/10 + ((w3+w2)/2);
            g.fillRect(t*2,b7,b8,b8);
            if(((x+40 >= t*2 && x<=t*2)||(x+40>=t*2+b8 && x<=t*2+b8)) && ((y+40 >=b7 && y<=b7)||
                (y+40>=b7+b8 && y<= b7+b8)))
            {
                x = y = t = b8 = 0; 
                t+=0;
                z=100;
                if(flaps !=null)
                    flaps.stop();
            }

        }
        if (q>8)
        {
            int eqq = ((w3+w2)/2);
            g.fillRect(t*2,500-y2,eqq,eqq);
            g.fillRect(t*3/2,500-y2,(2*w+w2)/2,(2*w+w2)/2);
            if(((x+40 >= t*2 && x<=t*2)||(x+40>=t*2+eqq && x<=t*2+eqq)) 
            && ((y+40 >=500-y2 && y<=500-y2)||(y+40>=500-y2+eqq && y<= 500-y+eqq)))
            {                
                x = y = t = eqq = 0; 
                t+=0;
                z=100;
                if(flaps !=null)
                    flaps.stop();
            }
        }
        if (q>12)
        {
            int b = 90;
            g.fillRect(2*t,y*y-250*y,b,b);
            if(((x+40 >= t*2 && x<=t*2)||(x+40>=t*2+b && x<=t*2+b)) 
            && ((y+40 >=y*y-250*y && y<=y*y-250*y)||(y+40>=(y*y-250*y)+b && y<= (y*y-250*y)+b)))
            {                
                x = y = t = b = 0; 
                t+=0;
                z=100;
                if(flaps !=null)
                    flaps.stop();
            }
        }
        if (q>15)
        {
            int b2 = 100;
            int eqq2 = 200+w2+t*t/500;
            int eqq3 = 500-t*t/500;
            if(q%3==1||q%3==2)
            {
                g.fillRect(t, eqq2 ,b2, b2);
                if(((x+40 >= t && x<=t)||(x+40>=t+b2 && x<=t+b2)) 
                && ((y+40 >= eqq2 && y<= eqq2)||(y+40>= eqq2+b2 && y<= eqq2+b2)))
                {                
                    x = y = t = b2 = 0; 
                    t+=0;
                    z=100;
                    if(flaps !=null)
                        flaps.stop();
                }
            }

            else
            {
                g.fillRect(t, eqq3 ,b2, b2);
                if(((x+40 >= t && x<=t)||(x+40>=t+b2 && x<=t+b2)) 
                && ((y+40 >= eqq3 && y<= eqq3)||(y+40>= eqq3+b2 && y<= eqq3+b2)))
                {                
                    x = y = t = b2 = 0; 
                    t+=0;
                    z=100;
                    if(flaps !=null)
                        flaps.stop();
                }
            }
        }
        if(n<=0)
            if (currentimg != null)
                g.drawImage(currentimg, x, y, 40, 40, this);

        if(((x+40 >= t && x<=t)||(x+40>=t+w && x<=t+w)) && ((y+40 >=y2 && y<=y2)||
            (y+40>=y2+w && y<= y2+w)))
        {
            x = y = t = w = 0; 
            t+=0;
            z=100;
            if(flaps !=null)
                flaps.stop();
        }
        Image menu = getImage(getCodeBase(), "menu.jpg");
        g.drawImage(menu,0,0,n,n,this);
        g.setColor(Color.black);
        g.fillRect(0,0,5*z, 5*z);
        g.setColor(Color.white);
        if(q<=5)
            g.drawString("Yikes ...", z,z);
        if(q>5 && q<10)
            g.drawString("GG man", z,z);
        if(q>=10)
            g.drawString("Solid", z,z);

    }
}
