package pl.czupryn.Klocek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class PanelGry extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = 1000;
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT/UNIT_SIZE);
    int coins = 0;
    int x = UNIT_SIZE*4;
    int y = UNIT_SIZE*5;
    int blockPrice = 1;
    int gemPrice = 5;
    int blocks = 100;
    int gems = 5;

    int[] blockY = new int[blocks];
    int[] blockX = new int[blocks];
    int[] bY = new int[blocks];
    int[] bX = new int[blocks];

    int[] gemY = new int[gems];
    int[] gemX = new int[gems];
    int[] gY = new int[gems];
    int[] gX = new int[gems];

    static final int DELEY = 75;
    char direction = 'B';
    char lastDirection = 'B';
    boolean running = false;
    boolean leftMap = true;
    boolean rightMap = false;
    boolean up = true;
    boolean down = true;
    boolean left = true;
    boolean right = true;
    boolean upgrade = false;
    Timer timer;
    Random random;

    PanelGry() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        random = new Random();
        startGame();
    }
    public void startGame() {
        newBlock();
        newGem();

        running = true;
        timer = new Timer(DELEY,this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);

            }
            g.setColor(Color.RED);
            g.fillRect(x, y, UNIT_SIZE, UNIT_SIZE);

            //rysowanie blokow na 1 mapie
            if (leftMap) {
                g.setColor(new Color(90, 81, 81));
                for (int i = 0; i < blockX.length; i++) {
                    g.fillRect(blockX[i], blockY[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //rysowanie blokow na 2 mapie
            if (rightMap) {
                g.setColor(new Color(90, 81, 81));
                for (int i = 0; i < bX.length; i++) {
                    g.fillRect(bX[i], bY[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //rysowanie gemow na 1 mapie
            if(leftMap) {
                g.setColor(new Color(39, 189, 174));
                for (int i = 0; i < gems; i++) {
                    g.fillRect(gemX[i], gemY[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //rysowanie gemow na 2 mapie
            if(rightMap) {
                g.setColor(new Color(39, 189, 174));
                for (int i = 0; i < gems; i++) {
                    g.fillRect(gX[i], gY[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(new Color(151, 59, 59));
            g.setFont(new Font("Roboto", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Monety: "+coins,(SCREEN_WIDTH-metrics.stringWidth("Monety: "+coins))/2,g.getFont().getSize());
        } else if (upgrade){
//            upgrade();
            //monety
            g.setColor(new Color(151, 59, 59));
            g.setFont(new Font("Roboto", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Monety: "+coins,(SCREEN_WIDTH-metrics.stringWidth("Monety: "+coins))/2,g.getFont().getSize());
        }
    }
    public void move(){

        switch(direction) {
            case 'U':
                if (up) {
                    y = y - UNIT_SIZE;
                }
                lastDirection = direction;
                direction = 'B';
                break;
            case 'D':
                if (down) {
                    y = y + UNIT_SIZE;
                }
                lastDirection = direction;
                direction = 'B';
                break;
            case 'L':
                if (left) {
                    x = x - UNIT_SIZE;
                }
                lastDirection = direction;
                direction = 'B';
                break;
            case 'R':
                if (right) {
                    x = x + UNIT_SIZE;
                }
                lastDirection = direction;
                direction = 'B';
                break;
        }
    }
    public void mine() {
        if (leftMap) {
            for (int i = 0; i < blocks; i++) {
                if (lastDirection == 'D') {
                    if (x == blockX[i] && y == (blockY[i] - UNIT_SIZE)) {
                        blockX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        blockY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + blockPrice);
                    }

                }
                if (lastDirection == 'U') {
                    if (x == blockX[i] && y == (blockY[i] + UNIT_SIZE)) {
                        blockX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        blockY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + blockPrice);
                    }
                }
                if (lastDirection == 'L') {
                    if (x == (blockX[i] + UNIT_SIZE) && y == blockY[i]) {
                        blockX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        blockY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + blockPrice);
                    }
                }
                if (lastDirection == 'R') {
                    if (x == (blockX[i] - UNIT_SIZE) && y == blockY[i]) {
                        blockX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        blockY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + blockPrice);
                    }
                }
            }
            //kopanie gemow
            for (int i = 0; i < gems; i++) {
                if (lastDirection == 'D') {
                    if (x == gemX[i] && y == (gemY[i] - UNIT_SIZE)) {
                        gemX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        gemY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + gemPrice);
                    }
                }
                if (lastDirection == 'U') {
                    if (x == gemX[i] && y == (gemY[i] + UNIT_SIZE)) {
                        gemX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        gemY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + gemPrice);
                    }
                }
                if (lastDirection == 'L') {
                    if (x == (gemX[i] + UNIT_SIZE) && y == gemY[i]) {
                        gemX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        gemY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + gemPrice);
                    }
                }
                if (lastDirection == 'R') {
                    if (x == (gemX[i] - UNIT_SIZE) && y == gemY[i]) {
                        gemX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        gemY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + gemPrice);
                    }
                }
            }
        }
        if (rightMap) {
            for (int i = 0; i < blocks; i++) {
                if (lastDirection == 'D') {
                    if (x == bX[i] && y == (bY[i] - UNIT_SIZE)) {
                        bX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        bY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + blockPrice);
                    }
                }
                if (lastDirection == 'U') {
                    if (x == bX[i] && y == (bY[i] + UNIT_SIZE)) {
                        bX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        bY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + blockPrice);
                    }
                }
                if (lastDirection == 'L') {
                    if (x == (bX[i] + UNIT_SIZE) && y == bY[i]) {
                        bX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        bY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + blockPrice);
                    }
                }
                if (lastDirection == 'R') {
                    if (x == (bX[i] - UNIT_SIZE) && y == bY[i]) {
                        bX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        bY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + blockPrice);
                    }
                }
            }
            for (int i = 0; i < gems; i++) {
                if (lastDirection == 'D') {
                    if (x == gX[i] && y == (gY[i] - UNIT_SIZE)) {
                        gX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        gY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + gemPrice);
                    }
                }
                if (lastDirection == 'U') {
                    if (x == gX[i] && y == (gY[i] + UNIT_SIZE)) {
                        gX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        gY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + gemPrice);
                    }
                }
                if (lastDirection == 'L') {
                    if (x == (gX[i] + UNIT_SIZE) && y == gY[i]) {
                        gX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        gY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + gemPrice);
                    }
                }
                if (lastDirection == 'R') {
                    if (x == (gX[i] - UNIT_SIZE) && y == gY[i]) {
                        gX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                        gY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
                        coins = (coins + gemPrice);
                    }
                }
            }
        }
    }
    public void upgrade() {

    }
    public void checkCollisions(){
        //sprawdzanie czy zderza sie ze scianami
        if (x > 0) {
            left = true;
        }
        if (x < (SCREEN_WIDTH)) {
            right = true;
        }
        if (y > 0) {
            up = true;
        }
        if (y< SCREEN_HEIGHT) {
            down = true;
        }
        if (x == (SCREEN_WIDTH - (UNIT_SIZE)) && rightMap) {
            right = false;
        }
        if (x <= 0 && leftMap) {
            left = false;
        }
        if (y > (SCREEN_HEIGHT - UNIT_SIZE * 2)) {
            down = false;
        }
        if (y == 0) {
            up = false;
        }
        //sprawdzanie czy zderza sie z blokiem
        if (leftMap) {
            for (int i = 0; i < blocks; i++) {
                if (x == (blockX[i] - UNIT_SIZE) && y == blockY[i]) {
                    right = false;
                }
                if (x == (blockX[i] + UNIT_SIZE) && y == blockY[i]) {
                    left = false;
                }
                if (y == (blockY[i] + UNIT_SIZE) && x == blockX[i]) {
                    up = false;
                }
                if (y == (blockY[i] - UNIT_SIZE) && x == blockX[i]) {
                    down = false;
                }
            }
        }
        if (rightMap) {
            for (int i = 0; i < blocks; i++) {
                if (x == (bX[i] - UNIT_SIZE) && y == bY[i]) {
                    right = false;
                }
                if (x == (bX[i] + UNIT_SIZE) && y == bY[i]) {
                    left = false;
                }
                if (y == (bY[i] + UNIT_SIZE) && x == bX[i]) {
                    up = false;
                }
                if (y == (bY[i] - UNIT_SIZE) && x == bX[i]) {
                    down = false;
                }
            }
        }
        //przechodzenie miedzy mapami
        if (leftMap && x == SCREEN_WIDTH) {
            rightMap = true;
            leftMap = false;
            x = 0;
        }
        if (rightMap && x < 0) {
            leftMap = true;
            rightMap = false;
            x = (SCREEN_WIDTH - UNIT_SIZE);
        }
        //sprawdzanie czy zderaz sie z gemami
        if (leftMap) {
            for (int i = 0; i < gems; i++) {
                if (x == (gemX[i] - UNIT_SIZE) && y == gemY[i]) {
                    right = false;
                }
                if (x == (gemX[i] + UNIT_SIZE) && y == gemY[i]) {
                    left = false;
                }
                if (y == (gemY[i] + UNIT_SIZE) && x == gemX[i]) {
                    up = false;
                }
                if (y == (gemY[i] - UNIT_SIZE) && x == gemX[i]) {
                    down = false;
                }
            }
        }
        if (rightMap) {
            for (int i = 0; i < gems; i++) {
                if (x == (gX[i] - UNIT_SIZE) && y == gY[i]) {
                    right = false;
                }
                if (x == (gX[i] + UNIT_SIZE) && y == gY[i]) {
                    left = false;
                }
                if (y == (gY[i] + UNIT_SIZE) && x == gX[i]) {
                    up = false;
                }
                if (y == (gY[i] - UNIT_SIZE) && x == gX[i]) {
                    down = false;
                }
            }
        }
    }
    public void newBlock() {
        //TODO bloki nie powinny pojawiac sie w sobie nawzajem
        // -za zniszczenie 1 bloka mozna dostac 1 punkt.

        for (int i = 0; i < blocks;i++) {
            blockX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            blockY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        }

        for (int i = 0; i < blocks;i++) {
            bX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            bY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        }
    }
    public void newGem() {
        //losowanie pozycji gema
        for (int i = 0; i < gems;i++) {
            gemX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            gemY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        }

        //losowanie pozycji gema dla drugiej mapy
        for (int i = 0; i < gems;i++) {
            gX[i] = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            gY[i] = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        }

    }
    public void gameOver(Graphics g) {

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            checkCollisions();
            move();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                        direction = 'L';
                        break;
                case KeyEvent.VK_RIGHT:
                        direction = 'R';
                        break;
                case KeyEvent.VK_UP:
                        direction = 'U';
                        break;
                case KeyEvent.VK_DOWN:
                        direction = 'D';
                        break;
                case KeyEvent.VK_SPACE:
                    mine();
                    break;
                case KeyEvent.VK_U:
                    if (running) {
                        upgrade = true;
                        running = false;
                    } else if (!running) {
                        upgrade = false;
                        running = true;
                    }
                    break;
            }
        }
    }
}





//k
