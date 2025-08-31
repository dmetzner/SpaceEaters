package MoveTest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Bullet {
	private static BufferedImage look;
		
	static{
		try {
		look = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/schuss.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
	
	private float f_posx;
	private float f_posy;
	private float f_speedx;
	private float f_speedy;
	private Rectangle bounding;
	private List<Bullet>bullets;
	private float timeAlive;
	private final float TIMETOLIVE = 10;
	private List<Enemy>enemys;
	private List<Enemy2>enemys2;
	private List<Enemy3>enemys3;
	private List<Boss1>bosse1;
	private List<Boss2>bosse2;
	private List<Boss3>bosse3;
	
	public static int Score = 0;
	public static int worldScore = 0;
	public static float worldScoreBreak = 150;
	public static int multiplikator = 1;
	public static String HS = Score + "";
	public static String finalScore = "Score " + Score;
	public static int highScore = 0;
	public static String highScoreS = "";
	
	
	
	public Bullet(float x, float y, float speedx, float speedy, List<Bullet>bullets,
			List<Enemy>enemys, List<Enemy2>enemys2, List<Boss1>bosse1,List<Boss2>bosse2,
			List<Boss3>bosse3, List<Enemy3>enemys3){
		this.f_posx = x;
		this.f_posy = y;
		this.f_speedx = speedx;
		this.f_speedy = speedy;
		bounding = new Rectangle( (int) x, (int)y, look.getWidth(), look.getHeight());
		this.bullets = bullets;
		this.enemys= enemys;
		this.enemys2= enemys2;
		this.bosse1 = bosse1;
		this.bosse2 = bosse2;
		this.bosse3 = bosse3;
		this.enemys3= enemys3;
	}
	
	public void update(float timeSinceLastFrame){
		timeAlive+=timeSinceLastFrame;
		if(timeAlive>TIMETOLIVE){
			bullets.remove(this);
		}
		
		highScoreS = reader.fileContentsToString("highscore.txt");
		highScore = Integer.parseInt(highScoreS);
		
		
		if(Score<1000000000){
			HS = Score + "";
			finalScore = "Score " + Score;
			
			if(Score>=highScore){
				highScore= Score;
			    FileWriter writer;
			    try {
			      writer = new FileWriter("highscore.txt");
			      String c = highScore+"";
			      writer.write(c);
			      writer.close();
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
		}}else{
			finalScore= "YOU HAVE NO LIFE";
			highScoreS= "999999999";
			Player.leben=0;
			   FileWriter writer;
			    try {
			      writer = new FileWriter("highscore.txt");
			      String c = highScore+"";
			      writer.write(c);
			      writer.close();
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
		}
		
		f_posx+=f_speedx*timeSinceLastFrame;
		f_posy+=f_speedy*timeSinceLastFrame;
		bounding.x = (int)f_posx;
		bounding.y = (int)f_posy;
		
		for(int i = 0; i <enemys.size(); i++){
			Enemy e = enemys.get(i);
			if(bounding.intersects(e.getBounding())){
				bullets.remove(this);
				e.alive = false;
				Score+= 1*multiplikator;
				worldScore++;
			}
		}
		
		for(int i = 0; i <enemys2.size(); i++){
			Enemy2 e2 = enemys2.get(i);
			if(bounding.intersects(e2.getBounding())){
				bullets.remove(this);
				e2.alive = false;
				Score+= 3*multiplikator;
				worldScore++;
			}
		}
		
		for(int i = 0; i <enemys3.size(); i++){
			Enemy3 e3 = enemys3.get(i);
			if(bounding.intersects(e3.getBounding())){
				bullets.remove(this);
				e3.leben--;
				
			}
		}
		
		for(int i = 0; i <bosse1.size(); i++){
			Boss1 b1 = bosse1.get(i);
			if(bounding.intersects(b1.getBounding())){
				bullets.remove(this);
				b1.leben--;
			}
		}
		
		for(int i = 0; i <bosse2.size(); i++){
			Boss2 b2 = bosse2.get(i);
			if(bounding.intersects(b2.getBounding())){
				bullets.remove(this);
				b2.leben--;
			}
		}
		for(int i = 0; i <bosse3.size(); i++){
			Boss3 b3 = bosse3.get(i);
			if(bounding.intersects(b3.getBounding())){
				bullets.remove(this);
				b3.leben--;
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
