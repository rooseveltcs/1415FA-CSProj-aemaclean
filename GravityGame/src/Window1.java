import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.Timer;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

public class Window1 extends JPanel implements MouseMotionListener,
		KeyListener, ActionListener, MouseListener {

	// vars
	private Timer _timer;
	int mouseX = 0;
	int mouseY = 0;
	int score = 0;
	double goVol = 0.1;
	double total = 0;
	double time = 0;
	boolean start = false;
	int startMass = 1;
	int objectNumber = 1;
	int objectMassMax = 1;
	boolean playerExists = false;
	Color backgroundColor = new Color(1, 2, 3);
	Color objectsColor = new Color(1, 2, 3);
	Color playerColor = new Color(1, 2, 3);
	double difficultyInt = 0;
	float hue = (float) 0.01;
	boolean realistic = false;
	public boolean del = false;
	Object mouseObject = null;
	int text = 100;
	boolean mouse = false;
	int countDown = 10;

	private ArrayList<Object> objectsCopy = new ArrayList<Object>();
	private ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Integer> timeOut = new ArrayList<Integer>();

	JPanel mainPannel = new JPanel();
	JPanel numberPannel = new JPanel();
	JPanel varPannel = new JPanel();
	JPanel massPannel = new JPanel();
	JPanel colorPanel = new JPanel();
	JPanel playerCPanel = new JPanel();
	JPanel objectCPanel = new JPanel();
	JPanel backgroundCPanel = new JPanel();

	JSlider numberSlider = new JSlider(JSlider.HORIZONTAL, 10, 150, 80);
	JSlider varSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 5);
	JSlider massSlider = new JSlider(JSlider.HORIZONTAL, 5, 100, 5);
	JButton beginButton = new JButton("Begin");
	JLabel difficulty = new JLabel(Integer.toString(varSlider.getValue()));

	String[] playerChoices = { "Blue", "Red", "Green", "Pink", "Yellow",
			"White" };
	JComboBox<String> playerDD = new JComboBox<String>(playerChoices);

	String[] objectChoices = { "Realistic", "Light Grey", "Dark Grey", "Black",
			"White" };
	JComboBox<String> objectDD = new JComboBox<String>(objectChoices);

	String[] backgroundChoices = { "Black", "White", "Blue", "Dark Grey" };
	JComboBox<String> backgroundDD = new JComboBox<String>(backgroundChoices);

	public Window1() {
		setDoubleBuffered(true);
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);

		beginButton.setActionCommand("begin");
		beginButton.addActionListener(this);

		numberSlider.setMinorTickSpacing(5);
		numberSlider.setMajorTickSpacing(20);
		numberSlider.setPaintTicks(true);
		numberSlider.setPaintLabels(true);
		numberSlider.setLabelTable(numberSlider.createStandardLabels(10));
		numberSlider.setPreferredSize(new Dimension(500, 50));
		numberSlider.setBackground(Color.WHITE);

		varSlider.setMinorTickSpacing(1);
		varSlider.setMajorTickSpacing(5);
		varSlider.setPaintTicks(true);
		varSlider.setPaintLabels(true);
		varSlider.setLabelTable(varSlider.createStandardLabels(5));
		varSlider.setPreferredSize(new Dimension(500, 50));
		varSlider.setBackground(Color.WHITE);

		massSlider.setMinorTickSpacing(5);
		massSlider.setMajorTickSpacing(1);
		massSlider.setPaintTicks(true);
		massSlider.setPaintLabels(true);
		massSlider.setLabelTable(numberSlider.createStandardLabels(5));
		massSlider.setPreferredSize(new Dimension(800, 50));
		massSlider.setBackground(Color.WHITE);

		mainPannel.setBounds(200, 200, 500, 500);
		numberPannel.setLayout(new GridLayout(2, 1, 0, 0));
		varPannel.setLayout(new GridLayout(2, 1, 0, 0));
		massPannel.setLayout(new GridLayout(2, 1, 0, 0));

		numberPannel.add(new JLabel("Number of Objects"));
		numberPannel.add(numberSlider);

		varPannel.add(new JLabel("Maximum mass of objects"));
		varPannel.add(varSlider);

		massPannel.add(new JLabel("Starting Player Mass"));
		massPannel.add(massSlider);

		colorPanel.add(playerCPanel);
		colorPanel.add(objectCPanel);
		colorPanel.add(backgroundCPanel);
		colorPanel.setLayout(new GridLayout(1, 3, 0, 0));
		colorPanel.setBackground(Color.WHITE);

		playerCPanel.add(new JLabel("Player Color:"));
		playerCPanel.add(playerDD);
		playerCPanel.setBackground(Color.WHITE);

		objectCPanel.add(new JLabel("Other Asteroid Color:"));
		objectCPanel.add(objectDD);
		objectCPanel.setBackground(Color.WHITE);

		backgroundCPanel.add(new JLabel("Background Color:"));
		backgroundCPanel.add(backgroundDD);
		backgroundCPanel.setBackground(Color.WHITE);

		mainPannel.add(numberPannel);
		mainPannel.add(varPannel);
		mainPannel.add(massPannel);
		mainPannel.add(colorPanel);
		mainPannel.add(difficulty);
		mainPannel.add(beginButton);

		mainPannel.setLayout(new GridLayout(6, 2, 100, 30));
		mainPannel.setBackground(Color.WHITE);
		varPannel.setBackground(Color.WHITE);
		massPannel.setBackground(Color.WHITE);
		numberPannel.setBackground(Color.WHITE);

		setBackground(Color.WHITE);
		this.add(mainPannel);

		addMouseMotionListener(this);

		_timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				paintInterval();

			}

		});
		_timer.start();

	}

	private void paintInterval() {
		// maths
		countDown--;
		score = 0;
		if (start) {

			playerExists = false;

			for (int j = 0; j <= objects.size() - 1; j++) {
				Object s1 = objects.get(j);
				for (int k = 1 + j; k <= objects.size() - 1; k++) {

					Object s2 = objects.get(k);

					int stop = s1.vector(s1, s2);
					if (stop == 1) {
						objects.remove(s1);
						return;
					}
					if (stop == 2) {
						objects.remove(s2);
						return;

					}

				}
				s1.tempDecrease();

				// objects loop around
				s1.testBorder(this.getWidth(), this.getHeight());
				// tests if its a player
				if (s1.getType()) {

					s1.setColor(playerColor);
					// confirm player is still there
					playerExists = true;
					// calculate pull on player by mouse
					if (mouse) {
						int xdistance = mouseX - s1.getX();
						int ydistance = mouseY - s1.getY();
						double radius = Math.sqrt((xdistance * xdistance)
								+ (ydistance * ydistance));
						if (radius > 0) {
							double radiusMult = goVol / radius;
							double xvol = xdistance * radiusMult;
							double yvol = ydistance * radiusMult;
							s1.setxVelocity(s1.getxVelocity() + xvol);
							s1.setyVelocity(s1.getyVelocity() + yvol);
						}
					}
					score = (int) Math.round(s1.getMass()) + score;
					// insure player is not going too fast
					if (s1.getxVelocity() > 10) {
						s1.setxVelocity(10);
					}
					if (s1.getxVelocity() < -10) {
						s1.setxVelocity(-10);
					}
					if (s1.getyVelocity() > 10) {
						s1.setyVelocity(10);
					}
					if (s1.getyVelocity() < -10) {
						s1.setyVelocity(-10);
					}

				}

			}

			for (Object s1 : objects) {
				if (s1.getMass() > total / 5) {

					int numberOfObjects = 10;
					for (int i = 1; i <= numberOfObjects; i++) {
						int locationVar = (s1.getDiameter() + 30) * 0;
						int velocityVar = (int) (200 / (s1.getDiameter()) + 16);
						
						int x = s1.getX()+ (int) (Math.random()* locationVar - (locationVar / 2));
						int y = s1.getY()+ (int) (Math.random()* locationVar - (locationVar / 2));
						double Yvelocity = Math.random()* velocityVar - (velocityVar / 2);
						double Xvelocity = Math.random()* velocityVar - (velocityVar / 2);
					objectsCopy.add(new Object(x,y,	s1.getMass() / numberOfObjects, Xvelocity, Yvelocity, false,	objectsColor, 0.1, 100));
						timeOut.add(20);
					}
					objects.remove(s1);
					return;

				}

			}
			for (int j = 0; j <= objectsCopy.size() - 1; j++) {
				timeOut.set(j, timeOut.get(j) - 1);
				if (timeOut.get(j) <= 0) {
					objects.add(objectsCopy.get(j));
					objectsCopy.remove(j);
					timeOut.remove(j);

				}

			}

			for (Object s : objects) {
				s.tick();
			}
			for (Object s : objectsCopy) {

				s.tick();
				s.testBorder(this.getWidth(), this.getHeight());
			}

		}
		if (mouseY < 20 && text < 255) {
			text = text + 5;
		}
		if (mouseY > 20 && text > 100) {
			text = text - 5;
		}
		if (playerExists) {
			time = time + 0.025;
		}
		// make difficulty number at begining of each game
		difficultyInt = (varSlider.getValue() * numberSlider.getValue())
				/ massSlider.getValue();
		if (massSlider.getValue() > ((varSlider.getValue() * numberSlider
				.getValue()) / 2) / 3) {
			difficultyInt = 99999999;
		}
		if (countDown < 1) {
			countDown = 40;
			objects.add(new Object(0, -3, 5, Math.random() * 4 - 2, Math
					.random() * 4 - 2, false, objectsColor, 0.2, 0));

		}

		this.repaint();
	}

	public void paint(Graphics g) {
		difficulty.setText("Difficulty: "
				+ Integer.toString((int) difficultyInt));
		difficulty.repaint();
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setRenderingHints(rh);
		if (!start) {
			// draw circles in start screen
			int playerDiameter = (int) ((Math.sqrt((massSlider.getValue()) / 3.14) * 4));
			g2d.drawOval(100 - (playerDiameter / 2),330 - (playerDiameter / 2), playerDiameter, playerDiameter);
			int objectDiameter = (int) (Math.sqrt((varSlider.getValue()) / 3.14) * 4) + 3;
			g2d.drawOval(100 - (objectDiameter / 2),200 - (objectDiameter / 2), objectDiameter, objectDiameter);
		}

		if (start) {

			setBackground(backgroundColor);

			for (Object s : objects) {
				s.paint(g2d);
			}
			for (Object s : objectsCopy) {
				s.paint(g2d);
			}

			Font font = new Font("Verdana", Font.BOLD, 20);
			g2d.setFont(font);
			Color fontColor = new Color(playerColor.getRed(),playerColor.getGreen(), playerColor.getBlue(), text);
			g2d.setColor(fontColor);
			g2d.drawString("Mass: " + (score) + "/" + total / 5, 5, 5 + font.getSize());
			g2d.drawString("Time: " + Math.round(time), 1060,5 + font.getSize());

			if (!playerExists) {
				g2d.drawString("Game Over [esc] to restart ", 500,5 + font.getSize());
			}
			if (((score + 1) / (total + 1)) > 0.95) {
				g2d.drawString("You Won [esc] to restart ", 500,5 + font.getSize());
			}

		}

	

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_ESCAPE:

			objects.clear();
			total = 0;
			start = false;
			mainPannel.setVisible(true);
			setBackground(Color.WHITE);
			time = 0;
			mouseObject = null;
			realistic=false;

			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "begin") {
			start = true;
			mainPannel.setVisible(false);
			startMass = massSlider.getValue();
			objectMassMax = varSlider.getValue();
			objectNumber = numberSlider.getValue();

			if (playerDD.getItemAt(playerDD.getSelectedIndex()) == "Red") {
				playerColor = Color.RED;
			}
			if (playerDD.getItemAt(playerDD.getSelectedIndex()) == "Blue") {
				playerColor = Color.CYAN;
			}
			if (playerDD.getItemAt(playerDD.getSelectedIndex()) == "Green") {
				playerColor = Color.GREEN;
			}
			if (playerDD.getItemAt(playerDD.getSelectedIndex()) == "Pink") {
				playerColor = Color.PINK;
			}
			if (playerDD.getItemAt(playerDD.getSelectedIndex()) == "Yellow") {
				playerColor = Color.YELLOW;
			}
			if (playerDD.getItemAt(playerDD.getSelectedIndex()) == "White") {
				playerColor = Color.WHITE;
			}

			if (objectDD.getItemAt(objectDD.getSelectedIndex()) == "White") {
				objectsColor = Color.WHITE;
			}
			if (objectDD.getItemAt(objectDD.getSelectedIndex()) == "Light Grey") {
				objectsColor = Color.LIGHT_GRAY;
			}
			if (objectDD.getItemAt(objectDD.getSelectedIndex()) == "Dark Grey") {
				objectsColor = Color.DARK_GRAY;
			}
			if (objectDD.getItemAt(objectDD.getSelectedIndex()) == "Black") {
				objectsColor = Color.BLACK;
			}
			
			if (objectDD.getItemAt(objectDD.getSelectedIndex()) == "Realistic") {
				objectsColor = new Color(184, 129, 59);
				realistic = true;
			}

			if (backgroundDD.getItemAt(backgroundDD.getSelectedIndex()) == "Black") {
				backgroundColor = Color.BLACK;
			}
			if (backgroundDD.getItemAt(backgroundDD.getSelectedIndex()) == "White") {
				backgroundColor = Color.WHITE;
			}
			if (backgroundDD.getItemAt(backgroundDD.getSelectedIndex()) == "Blue") {
				backgroundColor = Color.BLUE;
			}
			if (backgroundDD.getItemAt(backgroundDD.getSelectedIndex()) == "Dark Grey") {
				backgroundColor = Color.DARK_GRAY;
			}

			for (int i = 1; i < objectNumber; i++) {
				double randomMass = (int) (Math.random() * objectMassMax) + 1;
				int randomX=(int) (Math.random() * 1000);
				int randomY=(int) (Math.random() * 732);
				double randomVol= Math.random() * 2 - 1;
				total = total + randomMass;
				
				if (realistic) {
					Color realisticColor =  new Color((int) (Math.random() * 80) + 145,(int) (Math.random() * 80) + 80,(int) (Math.random() * 80) + 15);
					objects.add(new Object(randomX, randomY, randomMass,randomVol, randomVol,false, realisticColor, 0.2, 0));
				} else {

					objects.add(new Object(randomX,randomY, randomMass, randomVol, randomVol,false, objectsColor, 0.2, 0));
				}

			}
			total = total + startMass;
			objects.add(new Object(1100, 450, startMass, 0, 0, true,playerColor, 0.2, 0));

		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mouse = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouse = false;

	}

}
