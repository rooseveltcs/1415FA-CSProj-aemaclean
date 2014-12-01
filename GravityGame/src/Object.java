/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics2D;

public class Object {
	private double x;
	private double y;
	private double xVelocity;
	private double yVelocity;
	private double mass;
	private boolean type;
	private Color color;
	private int diameter;
	boolean del;
	private double density;
	private double temperature;

	public Object(int x, int y, double mass, double xvol, double yvol,
			boolean type, Color color, double density, double temperature) {
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.xVelocity = xvol;
		this.yVelocity = yvol;
		this.type = type;
		this.color = color;
		this.density = density;
		this.temperature = temperature;

	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public double getDensity() {
		return density;
	}

	public double getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getTemp() {
		return temperature;
	}

	public void setTemp(double temperature) {
		this.temperature = temperature;
	}

	public double getyVelocity() {
		return yVelocity;
	}

	public void tempDecrease() {
		if (temperature > 0) {
			temperature--;
		}
	}

	public double getMass() {
		return mass;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public void setMass(double Mass) {
		this.mass = Mass;
	}

	public void tick() {
		double speed = 0.3;
		x += xVelocity * speed;
		y += yVelocity * speed;
		diameter = (int) (Math.sqrt((mass / density) / 3.14)) * 2;
	}

	public boolean getType() {
		return type;
	}

	public int getDiameter() {
		return diameter;
	}

	public void sety(int y) {
		this.y = y;
	}

	public void setx(int x) {
		this.x = x;
	}

	public void setDensity(double d) {
		this.density = d;
	}

	public void setColor(Color c) {
		this.color = c;
	}

	public Color getColor() {
		return color;
	}

	public void testBorder(int xBorder, int yBorder) {
		int diameter = (int) (Math.sqrt((mass * 2) / 3.14) * 2) + 1;
		if (x > xBorder + (diameter / 2)) {
			x = 0 - (diameter / 2);
		}
		if (x < 0 - (diameter / 2)) {
			x = xBorder + (diameter / 2);
		}
		if (y > yBorder + (diameter / 2)) {
			y = 0 - (diameter / 2);
		}
		if (y < 0 - (diameter / 2)) {
			y = yBorder + (diameter / 2);
		}
	}

	public int vector(Object s1, Object s2) {
		// calculate x and y distance
		int xdistance = s2.getX() - s1.getX();
		int ydistance = s2.getY() - s1.getY();
		// Calculate distance using pythagreon theorem
		double radius = Math
				.max(Math.sqrt((xdistance * xdistance)
						+ (ydistance * ydistance)), 1);
		// test for collision
		if (radius < (s1.getDiameter() / 2) + (s2.getDiameter() / 2) + 2) {
			if (s1.getMass() <= s2.getMass()) {
				colosionCalculate(s1, s2);
				return 1;
			} else {
				colosionCalculate(s2, s1);
				return 2;
			}

		}
		int mass1 = (int) Math.round(s2.getMass());
		// calculate motion vector
		double vol1 = (double) ((mass1 / (radius * radius)));
		double volP1 = (vol1 / radius);
		double xvol1 = xdistance * volP1;
		double yvol1 = ydistance * volP1;

		s1.setxVelocity(s1.getxVelocity() + xvol1);
		s1.setyVelocity(s1.getyVelocity() + yvol1);

		int mass2 = (int) Math.round(s1.getMass());
		// calculate motion vector
		double vol2 = (double) ((mass2 / (radius * radius * -1)));
		double volP2 = (vol2 / radius * -1);
		double xvol2 = xdistance * volP2 * -1;
		double yvol2 = ydistance * volP2 * -1;

		s2.setxVelocity(s2.getxVelocity() + xvol2 * 2);
		s2.setyVelocity(s2.getyVelocity() + yvol2 * 2);
		return 0;

	}

	public void colosionCalculate(Object s1, Object s2) {

		double totalMass;
		totalMass = s2.getMass() + s1.getMass();
		
		s2.setxVelocity((s1.getxVelocity() * ((double) s1.getMass() / (double) totalMass))+ (s2.getxVelocity() * ((double) s2.getMass() / (double) totalMass)));
		s2.setyVelocity((s1.getyVelocity() * ((double) s1.getMass() / (double) totalMass))+ (s2.getyVelocity() * ((double) s2.getMass() / (double) totalMass)));

		s2.setMass(totalMass);
		

	}

	public void paint(Graphics2D g2d) {
		int b = Math.min(255, color.getBlue() + (int) (temperature / 0.8));
		int g = Math.min(255, color.getGreen() + (int) (temperature / 0.8));
		int r = Math.min(255, color.getRed() + (int) (temperature / 0.5));

		g2d.setColor(new Color(r, g, b, 8));

		for (int i = diameter; i < Math.min(temperature / 2, 400) + diameter; i = i + 8) {

			g2d.setColor(new Color(g2d.getColor().getRed(), Math.max(1, g2d	.getColor().getGreen() - 1), Math.max(1, g2d.getColor()	.getBlue() - 2), 4));
			g2d.fillOval((int) x - i / 2, (int) y - i / 2, (int) i, (int) i);
		}

		Color color = new Color(r, g, b, 255);
		g2d.setColor(color);

		g2d.drawOval((int) x - diameter / 2, (int) y - diameter / 2,(int) diameter, (int) diameter);
		g2d.fillOval((int) x - diameter / 2, (int) y - diameter / 2,(int) diameter, (int) diameter);

		if (type) {
			g2d.setColor(new Color(r, g, b, 200));
			diameter = diameter + 10;
			g2d.drawOval((int) x - diameter / 2, (int) y - diameter / 2,(int) diameter, (int) diameter);
		}

	}

}
