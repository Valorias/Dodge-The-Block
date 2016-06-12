package me.valorias.DodgeTheBlock;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import me.valorias.DodgeTheBlock.graphics.Screen;
import me.valorias.DodgeTheBlock.input.Keyboard;
import me.valorias.DodgeTheBlock.logic.Player;
import me.valorias.DodgeTheBlock.logic.Enemy;

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int width = 150;
	public static final int height = width / 12*9;
	private static final int scale = 5;
	private static final String name = "Dodge The Block";
	
	BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
	int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Thread thread;
	private boolean running;
	private JFrame frame;
	
	private Screen screen;
	private Keyboard key;
	private Player player;
	
	private int enemyNum = 15;
	private Enemy[] enemy = new Enemy[enemyNum];
	
	public Game(){
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		
		player = new Player(width/2,height/2, 0x00FF00);
		
		for(int i = 0 ; i < enemyNum; i++){
			enemy[i] = new Enemy();
		}
		
		addKeyListener(key);
		
		
	}
	
	public synchronized void start(){
		running = true;
		thread = new Thread(this,"Game");
		
		thread.start();	
	}
	
	public synchronized void stop(){
		running = false;
		try{
			thread.join();
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		long lastTime = System.nanoTime();
		double ns = 1_000_000_000d/60d;
		double nsT = 1_000_000_000;
		double delta = 0;
		double deltaT = 0;
		int counter = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now- lastTime)/ ns;
			deltaT += (now- lastTime)/nsT;
			lastTime = now;
			while(delta > 1){
				update();
				delta--;
			}
			while(deltaT>1){
				counter++;
				deltaT--;
				frame.setTitle(name + "   |   Score: " + counter);
			}
			
			render();
		}
		stop();
	}
	
	private void update(){
		key.update();
		
		if(key.up) player.move(0, -1);
		if(key.down) player.move(0, 1);
		if(key.left) player.move(-1, 0);
		if(key.right) player.move(1, 0);
		
		for(int i = 0; i< enemyNum; i++){
			enemy[i].move();
			if(enemy[i].getX() == player.getX() && enemy[i].getY() == player.getY()){
				System.out.println("touch");
				stop();
			}
			
		}
		
	}
	
	private void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		for (int i = 0 ; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];
		}
		screen.clear();
		
		{ // Rendering objects
			screen.render(player.getX(),player.getY(), player.getColor());
			for(int i = 0; i < enemyNum; i++){
				screen.render(enemy[i].getX(), enemy[i].getY(), enemy[i].getColor());
			}
		}
		
		Graphics g = bs.getDrawGraphics();
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		}
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args){
		Game g = new Game();
		
		g.frame.setResizable(false);
		g.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.frame.setTitle(name);
		g.frame.add(g);
		g.frame.pack();
		g.frame.setLocationRelativeTo(null);
		g.frame.setVisible(true);
		
		g.start();
		
	}

}
