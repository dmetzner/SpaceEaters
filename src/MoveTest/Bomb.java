package MoveTest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Bomb {
	private static BufferedImage look;
	private static BufferedImage look_dead;

	public static boolean alive =true;
	
	static{
		try {
		look = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/bombe.png"));
		look_dead = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/bombe_boom.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
	
	private float f_posx;
	private float f_posy;
	private float f_speedx;
	private float speedx;
	private float counter = 0;
	private Rectangle bounding;
	
	private List<Bomb>bombs;

	private final float TIMETOLIVE = (float) 2;
	
	private List<Enemy>enemys;
	private List<Enemy2>enemys2;
	private List<Enemy3>enemys3;
	private List<Boss1>bosse1;
	private List<Boss2>bosse2;
	private List<Boss3>bosse3;
	
	private float safe =2;
	private int soundTime=0;
	
	
	public Bomb(float x, float y, float speedx, List<Bomb>bombs, List<Enemy>enemys, List<Enemy2>enemys2, 
			List<Boss3>bosse3, List<Enemy3>enemys3, List<Boss1>bosse1, List<Boss2>bosse2){
		this.f_posx = x;
		this.f_posy = y;
		this.f_speedx = speedx;
		this.speedx = speedx;
		bounding = new Rectangle( (int) x, (int)y, look.getWidth(), look.getHeight());
		this.bombs = bombs;
		this.enemys = enemys;
		this.enemys2 = enemys2;
		this.bosse1 = bosse1;
		this.bosse2 = bosse2;
		this.bosse3 = bosse3;
		this.enemys3 = enemys3;
		
	}
	
	public void update(float timeSinceLastFrame){
		Bullet.HS = Bullet.Score + "";
		Bullet.finalScore = "HighScore " + Bullet.Score;
		
		if(!alive&&soundTime==0){
			soundTime++;
            try{
            	
            	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/bombe.wav"));
            	BufferedInputStream bufferedInputStream = new BufferedInputStream(audioInputStream);
            	        AudioFormat af = audioInputStream.getFormat();
            	        int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            	        byte[] audio = new byte[size];
            	        DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            	            bufferedInputStream.read(audio, 0, size);
            	            Clip clip = (Clip) AudioSystem.getLine(info);
            	            clip.open(af, audio, 0, size);
            	            clip.start();
         
            }
            catch(Exception e){  
                }
		}
		if(!Player.alive){
			bombs.remove(this);
		}
		
		if(!alive){
			counter+=timeSinceLastFrame;
			if(counter>TIMETOLIVE){
				bombs.remove(this);
				Player.timeSinceLastbomb=0;
				alive=true;
				f_speedx = speedx;
				counter= 0;
				soundTime = 0;
				
			}
		}
		
		f_posx+=f_speedx*timeSinceLastFrame;
		bounding.x = (int)f_posx;
		bounding.y = (int)f_posy;
		
		safe += timeSinceLastFrame;

		for(int i = 0; i <enemys.size(); i++){
			Enemy e = enemys.get(i);
			if(bounding.intersects(e.getBounding())){
				if(safe>=TIMETOLIVE){
					Bullet.Score+= (1*Bullet.multiplikator);
					Bullet.worldScore++;
					safe = 0;
				}
				alive=false;
				f_speedx = -100;
				e.alive = false;
			}
		}
		
		for(int i = 0; i <enemys3.size(); i++){
			Enemy3 e3 = enemys3.get(i);
			if(bounding.intersects(e3.getBounding())){
				if(safe>=TIMETOLIVE){
					if(e3.leben>1){
						e3.leben-=2;
					}else{
						e3.leben--;
					}
					safe=0;
				}
				alive=false;
				f_speedx = -100;
				
			}
		}
		for(int i = 0; i <enemys2.size(); i++){
			Enemy2 e2 = enemys2.get(i);
			if(bounding.intersects(e2.getBounding())){
				if(safe>=TIMETOLIVE){
					Bullet.Score+= (3*Bullet.multiplikator);
					Bullet.worldScore++;
					safe=0;
				}
				alive=false;
				f_speedx = -100;
				e2.alive = false;
			}
		}
		
		for(int i = 0; i <bosse1.size(); i++){
			Boss1 b1 = bosse1.get(i);
			if(bounding.intersects(b1.getBounding())){
				if(safe>=TIMETOLIVE){
					if(b1.leben>1){
						b1.leben-=2;
					}else{
						b1.leben--;
					}
					safe=0;
				}
				alive=false;
				f_speedx = -100;
			}
		}
			for(int i = 0; i <bosse2.size(); i++){
				Boss2 b2 = bosse2.get(i);
				if(bounding.intersects(b2.getBounding())){
					if(safe>=TIMETOLIVE){
						if(b2.leben>1){
							b2.leben-=2;
						}else{
							b2.leben--;
						}
						safe=0;
					}
					alive=false;
					f_speedx = -100;
				}
			}
		for(int i = 0; i <bosse3.size(); i++){
			Boss3 b3 = bosse3.get(i);
			if(bounding.intersects(b3.getBounding())){
				if(safe>=TIMETOLIVE){
					if(b3.leben>1){
						b3.leben-=2;
					}else{
						b3.leben--;
					}
					safe=0;
				}
				alive=false;
				f_speedx = -100;
			}
		}
	}
	public Rectangle getBounding(){
		return bounding;
	}
	
	public static BufferedImage getLook(){
		if(alive==true)return look;
		else return look_dead;
	}

	
}
