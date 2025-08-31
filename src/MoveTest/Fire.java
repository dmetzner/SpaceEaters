package MoveTest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Fire {
	private static BufferedImage look;
		
	static{
		try {
		look = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/fire.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
	
	private float f_posx;
	private float f_posy;
	private float f_speedx = 200;
	private Rectangle bounding;
	private List<Fire>fires;
	private float timeAlive;
	private final float TIMETOLIVE = 10;
	private Player player;
	
	
	
	public Fire(float x, float y, List<Fire>fires, Player player){
		this.f_posx = x;
		this.f_posy = y;
		bounding = new Rectangle( (int) x, (int)y, look.getWidth(), look.getHeight());
		this.fires = fires;
		this.player = player;
	}
	
	public void update(float timeSinceLastFrame){
		timeAlive+=timeSinceLastFrame;
		if(timeAlive>TIMETOLIVE){
			fires.remove(this);
		}
		f_posx-=f_speedx*timeSinceLastFrame;
		bounding.x = (int)f_posx;
		bounding.y = (int)f_posy;

		if(bounding.intersects(player.getBounding())){
				fires.remove(this);
				if(player.timeSinceLostLeben>player.lifeProtect){
				player.leben--;
				player.lebenSound=0;
				player.timeSinceLostLeben = 0;
				}
			}
		}

	public Rectangle getBounding(){
		return bounding;
	}
	
	public static BufferedImage getLook(){
		return look;
	}
	
}