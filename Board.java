
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    private final int width = 300;
    private final int height = 300;
    private final int dotSize = 10;
    private final int allDots = 900;
    private final int randPos = 29;
    private final int DEALY = 140;
    private final int[] x = new int[allDots];
    private final int[] y = new int[allDots];

    private int dots;
    private int appleX;
    private int appleY;

    private boolean leftDirect = false;
    private boolean rightDirect = true;
    private boolean upDirect = false;
    private boolean downDirect = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public Board() {
        initBoard();
    }
    private void initBoard(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(width,height));
        loadImages();
        initGame();
    }
    private void loadImages(){
        ImageIcon iid = new ImageIcon("Resources/dot.png");
        ball = iid.getImage();
        ImageIcon iia = new ImageIcon("Resources/apple.png");
        apple = iia.getImage();
        ImageIcon iih = new ImageIcon("Resources/head.png");
        head = iih.getImage();
    }
    private void initGame(){
        dots = 3;
        for(int i = 0;i<dots;i++){
            x[i] = 50-i*10;
            y[i]=50;
        }
        locateApple();
        timer = new Timer(DEALY,this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    private void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);

            for(int i = 0; i<dots;i++){
                if(i==0){
                    g.drawImage(head,x[i],y[i],this);
                }else{
                    g.drawImage(ball,x[i],y[i],this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }
    private void gameOver(Graphics g){
        String msg = "Game Over!";
        Font small = new Font("Helvatica",Font.BOLD,14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(width-metr.stringWidth(msg))/2,width/2);
    }

    private void checkApple(){
        if((x[0] == appleX)&&(y[0] == appleY)){
            dots++;
            locateApple();
        }
    }
    private void move(){
        for(int i = dots;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];

        }
        if(leftDirect){
            x[0] -=dotSize;

        }
        if(rightDirect){
            x[0]+=dotSize;
        }
        if(upDirect){
            y[0] -=dotSize;
        }
        if(downDirect){
            y[0]+=dotSize;
        }
    }
    private void checkCollision(){
        for(int i = dots;i>0;i--){
            if(i>4 &&(x[0]==x[i])&&(y[0]==y[i])){
                inGame = false;
            }
        }
        if(y[0]>=height){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
        if(x[0]>=width){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(!inGame){
            timer.stop();
        }
    }
    private void locateApple(){
        int r = (int)(Math.random()*randPos);
        appleX = (r*dotSize);

        r = (int)(Math.random()*randPos);
        appleY = r*dotSize;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirect)) {
                leftDirect = true;
                upDirect = false;
                downDirect = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirect)) {
                rightDirect = true;
                upDirect = false;
                downDirect = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirect)) {
                upDirect = true;
                rightDirect = false;
                leftDirect = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirect)) {
                downDirect = true;
                rightDirect = false;
                leftDirect = false;
            }
        }
    }
}
