package calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Window extends JPanel implements ActionListener, KeyListener {

    JButton b1 = new JButton("1");
    JButton b2 = new JButton("2");
    JButton b3 = new JButton("3");
    JButton b4 = new JButton("4");
    JButton b5 = new JButton("5");
    JButton b6 = new JButton("6");
    JButton b7 = new JButton("7");
    JButton b8 = new JButton("8");
    JButton b9 = new JButton("9");
    JButton b0 = new JButton("0");
    JButton bPlus = new JButton("+");
    JButton bMinus = new JButton("-");
    JButton bDivide = new JButton("/");
    JButton bMult = new JButton("*");
    JButton bEqu = new JButton("=");
    JButton bClear = new JButton("   CLEAR   ");

    JTextField tf = new JTextField(18);

    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JPanel p4 = new JPanel();

    String opp = null;

    boolean equalEnabled = false;
    boolean oppEnabled = false;

    public Window() {

        b1.setActionCommand("1");
        b1.addActionListener(this);

        b2.setActionCommand("2");
        b2.addActionListener(this);

        b3.setActionCommand("3");
        b3.addActionListener(this);

        b4.setActionCommand("4");
        b4.addActionListener(this);

        b5.setActionCommand("5");
        b5.addActionListener(this);

        b6.setActionCommand("6");
        b6.addActionListener(this);

        b7.setActionCommand("7");
        b7.addActionListener(this);

        b8.setActionCommand("8");
        b8.addActionListener(this);

        b9.setActionCommand("9");
        b9.addActionListener(this);

        b0.setActionCommand("0");
        b0.addActionListener(this);

        bPlus.setActionCommand("+");
        bPlus.addActionListener(this);

        bMinus.setActionCommand("-");
        bMinus.addActionListener(this);

        bDivide.setActionCommand("/");
        bDivide.addActionListener(this);

        bMult.setActionCommand("*");
        bMult.addActionListener(this);

        bEqu.setActionCommand("=");
        bEqu.addActionListener(this);

        bClear.setActionCommand("clear");
        bClear.addActionListener(this);

        Font timeFont = new Font("Verdana", Font.BOLD, 15);

        bClear.setFont(timeFont);

        tf.setEditable(false);

        p1.add(tf);

        p2.setLayout(new GridLayout(2, 5, 0, 0));

        p2.add(b1);
        p2.add(b2);
        p2.add(b3);
        p2.add(b4);
        p2.add(b5);
        p2.add(b6);
        p2.add(b7);
        p2.add(b8);
        p2.add(b9);
        p2.add(b0);

        p3.setLayout(new GridLayout(1, 5, 0, 0));
        p3.add(bPlus);
        p3.add(bMinus);
        p3.add(bMult);
        p3.add(bDivide);
        p3.add(bEqu);

        p4.add(bClear);
        p4.setBackground(Color.RED);

        bPlus.setEnabled(oppEnabled);
        bMinus.setEnabled(oppEnabled);
        bDivide.setEnabled(oppEnabled);
        bMult.setEnabled(oppEnabled);
        bEqu.setEnabled(equalEnabled);

        this.add(p1);
        this.add(p2);
        this.add(p3);
        this.add(p4);

        setFocusable(true);
        addKeyListener(this);
       
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        doCommand(command);
    }
    //takes string 
    private void doCommand(String command) {
        if ("clear".equals(command)) {
            tf.setText("");
            opp = null;
            equalEnabled = false;
            oppEnabled = false;

        } else if ("=".equals(command)) {
            String[] arrNums = tf.getText().split("\\" + opp);
            String result = "somethings not working sorry";
            try
            {
            int leftNum = Integer.parseInt(arrNums[0]);
            int rightNum = Integer.parseInt(arrNums[1]);
            if ("+".equals(opp)) {
                result = Integer.toString(leftNum + rightNum);
            }
            if ("-".equals(opp)) {
                result = Integer.toString(leftNum - rightNum);
            }
            if ("*".equals(opp)) {
                result = Integer.toString(leftNum * rightNum);
            }
            if ("/".equals(opp)) {
                if (rightNum == 0) {
                    result = "devision by 0";
                } else {
                    int remainder = leftNum % rightNum;
                    result = Integer.toString(leftNum / rightNum);
                    if (remainder > 0)
                        result += " r " + remainder;
                }
            }
            }
            catch (NumberFormatException e)
            {
                result = "integer oveflow";
            }
            catch (ArithmeticException e)
            {
                result = "ArithmaticException";
            }
            tf.setText(tf.getText() + "=" + result);
            equalEnabled = false;
        } else {
            String newText = tf.getText() + command;
            tf.setText(newText);
            if (Character.isDigit(newText.charAt(newText.length() - 1))) {
                if (opp == null) {
                    oppEnabled = true;
                    equalEnabled = false;

                } else {
                    oppEnabled = false;
                    equalEnabled = true;
                }

            } else {
                oppEnabled = false;
                equalEnabled = false;
                opp = command;
            }

        }

        bPlus.setEnabled(oppEnabled);
        bMinus.setEnabled(oppEnabled);
        bDivide.setEnabled(oppEnabled);
        bMult.setEnabled(oppEnabled);
        bEqu.setEnabled(equalEnabled);
        requestFocus();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isDigit(c)
                || (oppEnabled && (c == '+' || c == '-' || c == '*' || c == '/'))
                || (equalEnabled && c == '=')) {
            doCommand(Character.toString(e.getKeyChar()));
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            doCommand("clear");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

