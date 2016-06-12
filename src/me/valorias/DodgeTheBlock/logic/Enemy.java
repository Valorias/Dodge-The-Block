package me.valorias.DodgeTheBlock.logic;

import java.util.Random;

import me.valorias.DodgeTheBlock.Game;

public class Enemy {
	private int x, y;
	private int xSpeed =1, ySpeed = 2;
	private int color = 0xff0000;
	private int topSpeed = 2;
	
	
	Random r = new Random();
	
	public Enemy(){
		this.x = r.nextInt(Game.width - 1) +1;
		this.y = r.nextInt(Game.height - 1) +1;

	}
	
	public void move(){		
		if(x > Game.width-1) xSpeed = -1 * (r.nextInt(topSpeed)+1);
		if(x < 1) xSpeed = r.nextInt(topSpeed)+1;;
		if(y > Game.height-1) ySpeed = -1 * (r.nextInt(topSpeed)+1);
		if(y < 1) ySpeed = r.nextInt(topSpeed)+1;;
		
		x += xSpeed;
		y += ySpeed;	
		
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getColor(){
		return this.color;
	}
	
}
