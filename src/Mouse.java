import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class Mouse implements MouseListener {
     int  a = 0, b = 0;
     boolean fail = false;
     Main m;
     Mouse(Main m) {
         this.m = m;
     }
    @Override
    public void mouseClicked(MouseEvent e) {
        // GET MOUSE CLICK
        a = e.getX();
        b = e.getY();
        // SELECTION X, Y
        int selX = -1, selY = -1;
        // COORDINATE selX
        if (a >= 46 && a <= 80)     selX = 0;
        else if (a >=88 && a<=122)  selX = 1;
        else if (a >= 130 && a <= 164) selX = 2;
        else if (a >=172 && a<=206)  selX = 3;
        else if (a >= 214 && a <= 248) selX = 4;
        else if (a >=256 && a<=290)  selX = 5;
        else if (a >= 298 && a <= 332) selX = 6;
        else if (a >=340 && a<=374)  selX = 7;
        else if (a >= 382 && a <= 416) selX = 8;
        else if (a >= 424 && a <= 458) selX = 9;
        // COORDINATE selY
        if ( b >= 46 && b <= 80) selY = 0;
        else if (b >=88 && b <=122) selY = 1;
        else if (b >= 130 && b <= 164) selY = 2;
        else if (b >=172 && b<=206)  selY = 3;
        else if (b >= 214 && b <= 248) selY = 4;
        else if (b >=256 && b<=290)  selY = 5;
        else if (b >= 298 && b <= 332) selY = 6;
        else if (b >=340 && b<=374)  selY = 7;
        else if (b >= 382 && b <= 416) selY = 8;
        else if (b >= 424 && b <= 458) selY = 9;
        if (!m.save) {
            //HEAD COORDINATE
            if (a >= 500 && a <= 560 && b >= 42 && b <= 75) {
                selX = 10;
                selY = 10;
            }// BODY COORDINATE
            else if (a >= 500 && a <= 560 && b >= 80 && b <= 113) {
                selX = 11;
                selY = 11;
            }// CLEAR COORDINATE
            else if (a >= 500 && a <= 560 && b >= 118 && b <= 151) {
                selX = 12;
                selY = 12;
            }// UNTIL COORDINATE
            else if (a >= 500 && a <= 560 && b >= 160 && b <= 193) {
                selX = 13;
                selY = 13;
            } // SAVE COORDINATE
            else if (a >= 500 && a <= 560 && b >= 202 && b <= 235){
                selX = 14;
                selY = 14;
            }
            createPlane(selX, selY);
        }
        else
            checkPlane(selX, selY);
    }
    private void createPlane(int x, int y) {
         if (x != -1 && y != -1) {
             // PLANE OF HEAD ADD
             if (x==14 && y==14) m.save = true;
             else if (x==10 && y==10) {
                 m.headCountStart++;
                 m.body = false;
                 m.clear = false;
             }// BODY MAKE TRUE
             else if (x==11 && y==11) {
                 m.body = true;
                 m.clear = false;
             }// CLEAR MAKE TRUE
             else if (x==12 && y==12) {
                 m.clear = true;
                 m.body = false;
             }// UNTIL ADD
             else if (x==13 && y==13) m.until++;
             // BODY MAKE ARRAY
             else if (m.body && !m.clear) m.board[x][y] = 2;
             // HEAD ARRAY MAKE
             else if (m.headCountStart >0 && !m.clear) {
                 m.headCountStart--;
                 m.headCountEnd++;
                 m.board[x][y] = 1;
             }// ARRAY MAKE CLEAR
             else if (m.clear) {
                 if (m.board[x][y]==1) m.headCountEnd--;
                 m.board[x][y] = 0;
             }

             fail = false;
         } else fail = true;
    }
    private void checkPlane(int x, int y) {
        if (x != -1 && y != -1) {
            m.until--;
            if (m.board[x][y] == 1) {
                m.headCountEnd--;
                m.board[x][y] = 11;
            }
            else if (m.board[x][y] == 2)
                m.board[x][y] = 22;
            else if (m.board[x][y]!=11 && m.board[x][y]!=22)
                m.board[x][y] = 33;

            if (m.headCountEnd==0)m.running=false;
            fail = false;
        } else fail = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
