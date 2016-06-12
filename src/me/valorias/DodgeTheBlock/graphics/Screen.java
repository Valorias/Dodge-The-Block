package me.valorias.DodgeTheBlock.graphics;

public class Screen {
	private int width;
	private int height;
	public int[] pixels;
	
	public Screen(int w, int h){
		this.width = w;
		this.height = h;
		
		pixels = new int[width * height];
	}
	
	public void clear(){
		for (int i = 0; i < pixels.length; i++) pixels[i] = 0;
	}
	
	public void render(int xPos, int yPos, int color){
		for(int y = 0 ; y < height; y++ ){
			if(yPos >= height || yPos < 0) break;
			for(int x = 0; x < width; x++){
				if(xPos >= width || xPos < 0) break;
				pixels[xPos + (yPos * width)] = color;
			}
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

}
