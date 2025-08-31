package MoveTest;

	import java.awt.Rectangle;
	import java.awt.image.BufferedImage;
	import java.io.IOException;
	import java.util.List;
	import javax.imageio.ImageIO;
	
public class SpeedWolke {

		private static BufferedImage look;
		private List<SpeedWolke>SpeedWolken;
			
		static{
			try {
			look = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/rauch.png"));
			}catch(IOException e) {e.printStackTrace();}
		}
		
		private float f_posx;
		private float f_posy;
		private float f_speedx = 100;
		private Rectangle bounding;

		private float timeAlive;
		private final float TIMETOLIVE = 15;

		
		public SpeedWolke(float x, float y ,List<SpeedWolke>SpeedWolken){
			this.f_posx = x;
			this.f_posy = y;
			bounding = new Rectangle( (int) x, (int)y, look.getWidth(), look.getHeight());
			this.SpeedWolken = SpeedWolken;
		}
		
		public void update(float timeSinceLastFrame){
			timeAlive+=timeSinceLastFrame;
			if(timeAlive>TIMETOLIVE){
				SpeedWolken.remove(this);
			}
			bounding.x = (int)f_posx;
			bounding.y = (int)f_posy;
			f_posx-=f_speedx*timeSinceLastFrame;

		}
		
		public Rectangle getBounding(){
			return bounding;
		}
		public static BufferedImage getLook(){
			return look;
		}
		
	}


