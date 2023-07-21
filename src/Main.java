import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class Main extends JPanel implements ActionListener {
    final int SCREEN_HEIGHT = 600, SCREEN_WIDTH = 700, UNIT_SIZE = 42;
    boolean running, save = false, body = false, clear = false, start = false, wrong = false;
    int offset = 42, square = 41, until = 0;
    int headCountStart = 0, headCountEnd = 0;
    int[][] board = new int[10][10];
   // Class
    JButton resetButton = new JButton("Play Again?");
    Mouse mouse  = new Mouse(this);
    ImageIcon xIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("Photo/orangex.png")));
    public static ImageIcon logo = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("Photo/plane.png")));

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Plane");
        frame.setIconImage(logo.getImage());
        frame.getContentPane().add(new Main());

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public Main(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.addMouseListener(mouse);
        this.setLayout(null);
        resetButton.addActionListener(this);
        resetButton.setBounds(SCREEN_WIDTH/2-50, SCREEN_HEIGHT-200, 100,30);
        this.add(resetButton);
        startGame();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawCoordinate(g);
        drawGame(g);
        drawUI(g);
    }
    private void drawUI(Graphics g) {
        if (!save) {
            g.setColor(Color.yellow);
            g.drawRect(SCREEN_WIDTH-200, 42, 60, 33);
            g.drawString("Head", SCREEN_WIDTH-192, 68);
            g.drawString("-> "+ headCountStart,SCREEN_WIDTH-130, 66);

            g.setColor(Color.CYAN);
            g.drawRect(SCREEN_WIDTH-200, 80, 60, 33);
            g.drawString("Body", SCREEN_WIDTH-192, 108);
            g.drawString("-> "+body,SCREEN_WIDTH-130, 108);

            g.setColor(Color.BLUE);
            g.drawRect(SCREEN_WIDTH-200, 118, 60, 33);
            g.drawString("Clear", SCREEN_WIDTH-192, 148);
            g.drawString("-> "+ clear,SCREEN_WIDTH-130, 146);

            g.setColor(Color.PINK);
            g.drawRect(SCREEN_WIDTH-200, 160, 60, 33);
            g.drawString("Until", SCREEN_WIDTH-192, 189);
            g.drawString("-> " + until, SCREEN_WIDTH - 130, 188);

            g.setColor(Color.magenta);
            g.drawRect(SCREEN_WIDTH-200, 202, 60, 33);
            g.drawString("Save", SCREEN_WIDTH-192, 231);

        } else {
            if (running) {
                g.setColor(Color.BLUE);
                g.drawString("Head ->"+headCountEnd, SCREEN_WIDTH - 192, offset*5);

                g.setColor(Color.red);
                g.drawString(" END UNTIL ->"+until, SCREEN_WIDTH - 192, offset*6);
            }
        }
    }
    private void drawCoordinate(Graphics g) {
        if (running) {
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            // GRID
            for (int i = 0; i <=11; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT-140);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH-240, i * UNIT_SIZE);
                // 0 -> 10 dugaaralt
                if (i <= 10) {
                    g.drawString(String.valueOf(i), i * UNIT_SIZE + 20, 30);
                    if (i >=1)
                        g.drawString(String.valueOf(i), 22, (i + 1) * UNIT_SIZE - 22);
                }
                // X, Y COORDINATE CLICK
                g.drawString(" CLICK= x:  " + mouse.a + ", y: " + mouse.b, 36, 534);
            }
            //
            if (mouse.fail) {
                //TEXT
                g.setColor(Color.red);
                g.drawString("WRONG!", 276, 534);
                //IMAGE
                Image xImg = xIcon.getImage();
                g.drawImage(xImg, 368, 496, null);
            }
        }
        repaint();
    }
    private void drawGame(Graphics g){
        if (running) {
            if (!save)
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        if (board[i][j] == 1) {
                            g.setColor(Color.red);
                            g.fillRect(43 + offset * i, 43 + offset * j, square, square);

                        } else if (board[i][j] == 2) {
                            g.setColor(Color.gray);
                            g.fillRect(43 + offset * i, 43 + offset * j, square, square);
                        } else if (board[i][j] == 0) {
                            g.setColor(Color.black);
                            g.fillRect(43 + offset * i, 43 + offset * j, square, square);
                        }
                    }
                }
            else {
                if (!start)
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board.length; j++) {
                            g.setColor(Color.black);
                            g.fillRect(43 + offset * i, 43 + offset * j, square, square);
                            if (i == board.length-1 && j == board.length-1) {
                                start = true;
                            }
                        }
                    }
                else
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board.length; j++) {
                            if (board[i][j] == 11) {
                                g.setColor(Color.red);
                                g.fillRect(43 + offset * i, 43 + offset * j, square, square);
                            } else if (board[i][j] == 22) {
                                g.setColor(Color.gray);
                                g.fillRect(43 + offset * i, 43 + offset * j, square, square);
                            } else if (board[i][j] == 33) {
                                Image xImg = xIcon.getImage();
                                Image newXImg = xImg.getScaledInstance(27, 27, java.awt.Image.SCALE_SMOOTH);
                                ImageIcon newXIcon = new ImageIcon(newXImg);
                                g.drawImage(newXIcon.getImage(), 48 + offset * i, 48 + offset * j, null);
                            }

                        }
                    }

            }
        }else{
            getResetButton().setVisible(true);
            if (wrong)  gameWrong(g);
            else        gameOver(g);
        }
    }
    private void gameWrong(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("ERROR!", (SCREEN_WIDTH - metrics2.stringWidth("ERROR!")) / 2, SCREEN_HEIGHT / 2);
    }
    private void gameOver(Graphics g) {

        if (until==0){
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("GAME OVER", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        }else{
            g.setColor(Color.yellow);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("VICTORY", (SCREEN_WIDTH - metrics2.stringWidth("VICTORY")) / 2, SCREEN_HEIGHT / 2);
        }
    }
    private void startGame() {
        running = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = 0; // all spots are empty
            }
        }
        getResetButton().setVisible(false);
    }
    private void resetGame(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = 0; // all spots are empty
            }
        }
        headCountEnd = headCountStart = until =0;
        wrong = save = clear = false;
        running = true;
        getResetButton().setVisible(false);
    }
    private JButton getResetButton() { return resetButton; }
    @Override
    public void actionPerformed(ActionEvent e) {
        resetGame();
    }
}
