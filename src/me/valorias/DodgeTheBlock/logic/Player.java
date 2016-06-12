package me.valorias.DodgeTheBlock.logic;

import me.valorias.DodgeTheBlock.Game;

public class Player {
	private int x,y;
	private int color;
	
	public Player(int x, int y, int color){
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public void move(int x, int y){
		this.x += x;
		this.y += y;
		if(this.x > Game.width) this.x = 0;
		if(this.x < 0) this.x = Game.width; 
		if(this.y > Game.height) this.y = 0;
		if(this.y < 0) this.y = Game.height; 
		
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
}
