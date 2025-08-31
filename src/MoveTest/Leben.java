package MoveTest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Leben {
	private static BufferedImage[] look = new BufferedImage[4];
	private Rectangle bounding;
	private static Player player;
	
	
	static{
		try {
		look[0]= null;
		look[1] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/1herz.png"));
		look[2] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/2herz.png"));
		look[3] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/3herz.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
	
	private float f_posx;
	private float f_posy;
	
	
	public Leben(float x, float y, Player player){
		this.f_posx = x;
		this.f_posy = y;
		bounding = new Rectangle( (int) x, (int)y, look[1].getWidth(), look[1].getHeight());
		this.player = player;
		
	}
	
	public void update(float timeSinceLastFrame){
				
		bounding.x = (int)f_posx;
		bounding.y = (int)f_posy;
		
	}
	
	public Rectangle getBounding(){
		return bounding;
	}
	
	public static BufferedImage getLook(){
		if(player.leben == 0) return look[0];	
		else if(player.leben == 1) return look[1];
		else if(player.leben == 2) return look[2];
		else if(player.leben == 3) return look[3];
		else return null;
	}
	
	

}