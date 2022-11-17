import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.plaf.FontUIResource;
class GUI extends JFrame implements KeyListener{
    JPanel p1,p2,p3;
    JTextField tf;
    JButton bg1,bg2,bg3,bg4,bg5;
    JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18,b19,b20,b21,b22,b23,b24,b25;
    JLabel name;
    Font font=new FontUIResource("palne", Font.BOLD, 20);// for Font
    boolean b[]=new boolean[26];
    int matrix[][];
    int score=0,option=1;
    boolean r1,r2,r3,r4,r5,c1,c2,c3,c4,c5,d1,d2;
    boolean bool;
    Socket s;
    DataInputStream din;
    DataOutputStream dout;
    GUI(String player,int matrix[][]){
        this.matrix=matrix;
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        tf=new JTextField();
        this.name=new JLabel(player);
        /*layout for the hole window*/this.setLayout(new GridBagLayout());GridBagConstraints grid = new GridBagConstraints();
        p1.add(this.name);name.setFont(font);tf.setFont(font);
        /*This for panel 1*/grid.fill=GridBagConstraints.HORIZONTAL;grid.ipadx=500;grid.ipady=40;this.add(p1,grid);
        grid.fill=GridBagConstraints.HORIZONTAL;grid.ipadx=150;grid.ipady=30;grid.gridx=0;grid.gridy=3;this.add(tf,grid);
        /*This for panel 2*/grid.fill=GridBagConstraints.HORIZONTAL;grid.ipadx=150;grid.ipady=30;grid.gridx=0;grid.gridy=1;p2.setLayout(new GridLayout(1,5));this.add(p2,grid);
        /*This for panel 3*/grid.fill=GridBagConstraints.HORIZONTAL;grid.ipadx=150;grid.ipady=300;grid.gridx=0;grid.gridy=2;p3.setLayout(new GridLayout(5,5));this.add(p3,grid);
        /*For the window*/this.setVisible(true);this.setSize(550,650);this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//fot window
        this.setLocationRelativeTo(null);  
        tf.addKeyListener(this);
    }

    void BingoCreate(){
        bg1=new JButton("B");bg1.setFont(font);bg2=new JButton("I");bg2.setFont(font);bg3=new JButton("N");bg3.setFont(font);bg4=new JButton("G");bg4.setFont(font);bg5=new JButton("O");bg5.setFont(font);p2.add(bg1);p2.add(bg2);p2.add(bg3);p2.add(bg4);p2.add(bg5);
    }
    void ButtonCreating(){
        b1=new JButton(String.valueOf(this.matrix[0][0]));b2=new JButton(String.valueOf(this.matrix[0][1]));b3=new JButton(String.valueOf(this.matrix[0][2]));b4=new JButton(String.valueOf(this.matrix[0][3]));b5=new JButton(String.valueOf(this.matrix[0][4]));b6=new JButton(String.valueOf(this.matrix[1][0]));b7=new JButton(String.valueOf(this.matrix[1][1]));b8=new JButton(String.valueOf(this.matrix[1][2]));b9=new JButton(String.valueOf(this.matrix[1][3]));b10=new JButton(String.valueOf(this.matrix[1][4]));b11=new JButton(String.valueOf(this.matrix[2][0]));b12=new JButton(String.valueOf(this.matrix[2][1]));b13=new JButton(String.valueOf(this.matrix[2][2]));b14=new JButton(String.valueOf(this.matrix[2][3]));b15=new JButton(String.valueOf(this.matrix[2][4]));b16=new JButton(String.valueOf(this.matrix[3][0]));b17=new JButton(String.valueOf(this.matrix[3][1]));b18=new JButton(String.valueOf(this.matrix[3][2]));b19=new JButton(String.valueOf(this.matrix[3][3]));b20=new JButton(String.valueOf(this.matrix[3][4]));b21=new JButton(String.valueOf(this.matrix[4][0]));b22=new JButton(String.valueOf(this.matrix[4][1]));b23=new JButton(String.valueOf(this.matrix[4][2]));b24=new JButton(String.valueOf(this.matrix[4][3]));b25=new JButton(String.valueOf(this.matrix[4][4]));
        p3.add(b1);p3.add(b2);p3.add(b3);p3.add(b4);p3.add(b5);p3.add(b6);p3.add(b7);p3.add(b8);p3.add(b9);p3.add(b10);p3.add(b11);p3.add(b12);p3.add(b13);p3.add(b14);p3.add(b15);p3.add(b16);p3.add(b17);p3.add(b18);p3.add(b19);p3.add(b20);p3.add(b21);p3.add(b22);p3.add(b23);p3.add(b24);p3.add(b25);
    }
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e) {}
    public void keyPressed(KeyEvent source) {
        if(s.isClosed()){
            try {
                if(s.isClosed()){
                    din.close();;
                    dout.close();
                    this.dispose();
                }
            } catch (Exception e) {}
        }
        if(source.getKeyCode()==10){
            try{
                this.writeMessage(tf.getText());
                int value=Integer.parseInt(tf.getText());
                this.option++;
                if(this.ValidInput(value)){
                    this.Mark(value);
                    this.Check();
                    tf.setEditable(false);
                }
                else{
                    tf.setEditable(true);
                    JOptionPane.showMessageDialog(this, "Input_Is_Out_of_Range or Already_Marked","InvalidInput",JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(Exception exception){
                tf.setEditable(true);
                JOptionPane.showMessageDialog(this, "Please enter the valid input");
            }
            tf.setText("");
        }
    }
    private boolean ValidInput(int value){
        if(value<=0 || value>25 || b[value]==true ){
            return false;
        }
        b[value]=true;
        return true;
    }
    private void Mark(int value){
        int la[]=this.Search(matrix, value);
        this.Color(la[2]);
        matrix[la[0]][la[1]]=-1;
    }
    private void Color(int label){
        if(label==1){this.b1.setBackground(Color.green);}else if(label==2){this.b2.setBackground(Color.green);}else if(label==3){this.b3.setBackground(Color.green);}else if(label==4){this.b4.setBackground(Color.green);}else if(label==5){this.b5.setBackground(Color.green);}else if(label==6){this.b6.setBackground(Color.green);}else if(label==7){this.b7.setBackground(Color.green);}else if(label==8){this.b8.setBackground(Color.green);}else if(label==9){this.b9.setBackground(Color.green);}else if(label==10){this.b10.setBackground(Color.green);}else if(label==11){this.b11.setBackground(Color.green);}else if(label==12){this.b12.setBackground(Color.green);}else if(label==13){this.b13.setBackground(Color.green);}else if(label==14){this.b14.setBackground(Color.green);}else if(label==15){this.b15.setBackground(Color.green);}else if(label==16){this.b16.setBackground(Color.green);}else if(label==17){this.b17.setBackground(Color.green);}else if(label==18){this.b18.setBackground(Color.green);}else if(label==19){this.b19.setBackground(Color.green);}else if(label==20){this.b20.setBackground(Color.green);}else if(label==21){this.b21.setBackground(Color.green);}else if(label==22){this.b22.setBackground(Color.green);}else if(label==23){this.b23.setBackground(Color.green);}else if(label==24){this.b24.setBackground(Color.green);}else if(label==25){this.b25.setBackground(Color.green);}
    }
    private void PaintRed(int label){
        if(label==1){this.bg1.setBackground(Color.red);}else if(label==2){this.bg2.setBackground(Color.red);}else if(label==3){this.bg3.setBackground(Color.red);}else if(label==4){this.bg4.setBackground(Color.red);}else if(label==5){this.bg5.setBackground(Color.red);}
    }
    private int[] Search(int a[][],int value){
        int cnt=1;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(a[i][j]==value){
                    a[i][j]=-1;
                    return new int[]{i,j,cnt};
                }
                cnt++;
            }
        }
        return new int[2];
    }
    private void Check(){
        if(RowsOrColSame(this.matrix)){
            score++;
            this.PaintRed(score);
            if(score==5)    {
                JOptionPane.showMessageDialog(this,"Winner is Ravi");
                try{
                    dout.writeUTF("Ravi");
                    din.close();
                    dout.close();
                    s.close();
                }
                catch(Exception exception){}
            }
        }
    }
    private boolean RowsOrColSame(int a[][]){
        if(a[0][0]==a[0][1] && a[0][0]==a[0][2] && a[0][0]==a[0][3] && a[0][0]==a[0][4] && !this.r1){this.r1=true;return true;}
        else if(a[1][0]==a[1][1] && a[1][0]==a[1][2] && a[1][0]==a[1][3] && a[1][0]==a[1][4] && !this.r2){this.r2=true;return true;}
        else if((a[2][0]==a[2][1] && a[2][0]==a[2][2] && a[2][0]==a[2][3] && a[2][0]==a[2][4]) && !this.r3){this.r3=true;return true;}
        else if((a[3][0]==a[3][1] && a[3][0]==a[3][2] && a[3][0]==a[3][3] && a[3][3]==a[3][4]) && !this.r4) {this.r4=true ;return true ;}
        else if(a[4][0]==a[4][1] && a[4][0]==a[4][2] && a[4][0]==a[4][3] && a[4][0]==a[4][4] && !this.r5){this.r5=true;return true;}
        //for columns
        if(a[0][0]==a[1][0] && a[0][0]==a[2][0] && a[0][0]==a[3][0] && a[0][0]==a[4][0] && !this.c1){this.c1=true;return true;}
        else if(a[0][1]==a[1][1] && a[0][1]==a[2][1] && a[0][1]==a[3][1] && a[0][1]==a[4][1] && !this.c2){this.c2=true;return true;}
        else if(a[0][2]==a[1][2] && a[0][2]==a[2][2] && a[0][2]==a[3][2] && a[0][2]==a[4][2] && !this.c3){this.c3=true;return true;}
        else if(a[0][3]==a[1][3] && a[0][3]==a[2][3] && a[0][3]==a[3][3] && a[0][3]==a[4][4] && !this.c4){this.c4=true;return true;}
        else if(a[0][4]==a[1][4] && a[0][4]==a[2][4] && a[0][4]==a[3][4] && a[0][4]==a[4][4] && !this.c5){this.c5=true;return true;}
        //for diagonals
        if(a[0][0]==a[1][1] && a[0][0] == a[2][2] && a[0][0] == a[3][3] && a[0][0]==a[4][4] && !this.d1){this.d1=true;return true;}
        else if(a[0][4]==a[1][3] && a[0][4]== a[2][2] && a[0][4]==a[3][1] && a[0][4]==a[4][1] && !this.d2){this.d2=true;return true;}
        return false;
    }
    void readMessage(){
        Thread t=new Thread(()->{
            while(true){
                try{
                    String inp=din.readUTF();
                    if(s.isClosed()){
                        din.close();;
                        dout.close();
                        this.dispose();
                    }
                    if(inp.equals("Ravi")){
                        try{
                            JOptionPane.showMessageDialog(this,"Winner is Ravi","Winner",JOptionPane.INFORMATION_MESSAGE);
                            s.close();
                            din.close();
                            dout.close();
                        }
                        catch(Exception exception){}
                    }
                    int value=Integer.parseInt(inp);
                    if(this.ValidInput(value)){
                        tf.setEditable(true);
                        this.Mark(value);
                        this.Check();
                    }
                    else{
                        tf.setEditable(false);
                        JOptionPane.showMessageDialog(this, "Input_Is_Out_of_Range or Already_Marked","InvalidInput",JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(Exception exception){
                    tf.setEditable(false);
                    JOptionPane.showMessageDialog(this, "He/Her enter the invalid input");
                }
                tf.setText("");
            }
        });
        t.start();
    }
    void writeMessage(String text){
        if(s.isClosed()){
            try {
                if(s.isClosed()){
                    din.close();;
                    dout.close();
                    this.dispose();
                }
            } catch (Exception e) {}
        }
        try{
            dout.writeUTF(text);
        }
        catch(Exception exception){}
    }
}
public class ClientBingo {
    public static void shuffle(int[][] matrix, int columns, Random rnd) {
        int size = matrix.length * columns;
        for (int i = size; i > 1; i--)
            swap(matrix, columns, i - 1, rnd.nextInt(i));
    }
    public static void swap(int[][] matrix, int columns, int i, int j) {
        int tmp = matrix[i / columns][i % columns];
        matrix[i / columns][i % columns] = matrix[j / columns][j % columns];
        
        matrix[j / columns][j % columns] = tmp;
    }
    public static void main(String args[]){
        int[][] matrix1 = { {1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
        shuffle(matrix1, 5, new Random()); 
        GUI gui=new GUI("Ravi",matrix1);
        gui.BingoCreate();
        gui.ButtonCreating();
        try{
            gui.s=new Socket("192.168.0.106",6666);
            gui.din=new DataInputStream(gui.s.getInputStream());
            gui.dout=new DataOutputStream(gui.s.getOutputStream());
        }
        catch(Exception exception){}
        gui.readMessage();
        Audio();
    }
    private static void Audio(){
        File file=new File("C://Users//Lenovo//Desktop//Projects//OnlineBingo//BackGround.wav");
        try{
            AudioInputStream audio=AudioSystem.getAudioInputStream(file);
            Clip clip=AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}