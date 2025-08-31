package MoveTest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Starter {
	private static BufferedImage[] look = new BufferedImage[4];
	private Rectangle bounding;
	private static Player player;
	public static int counter = 0; 
	
	
	static{
		try {
		look[0]= null;
		look[1] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/Start.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
	
	private float f_posx;
	private float f_posy;
	
	
	public Starter(float x, float y, Player player){
		this.f_posx = x;
		this.f_posy = y;
		bounding = new Rectangle( (int) x, (int)y, look[1].getWidth(), look[1].getHeight());
		this.player = player;
		
	}
	
	public void update(float timeSinceLastFrame){
		
		if(player.alive==true){
			counter++;
		}
		
		bounding.x = (int)f_posx;
		bounding.y = (int)f_posy;
		
	}
	
	public Rectangle getBounding(){
		return bounding;
	}
	
	public static BufferedImage getLook(){
		if(player.alive==false&&counter==0) return look[1];	
		else return null;
	}
	
	

}
