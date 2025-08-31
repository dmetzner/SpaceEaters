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

public class Boss2 {
	private static BufferedImage[] look = new BufferedImage[4];
	private static BufferedImage look_dead;
	private final static float NEEDEDANYTIME = 0.8f;
	private float aniTime = 0;

	public static boolean alive = false;
	protected int leben = 0;
	
	static{
		try {
		look[0] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss2_1.png"));
		look[1] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss2_2.png"));
		look[2] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss2_3.png"));
		look[3] = look[0];
		look_dead = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss2_kaputt.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
		
		public float posx;
		private float posy;
		private float speedx = 100;
		private float yspeed = 300;
		private float speedy = yspeed;
		private float turn = 1.8f;
		

		private Rectangle bounding;
		private List<Boss2>bosse2;
		private float timeAlive;
		private final float TIMETOLIVE = 200;
		private float timeSinceDead = 0;
		private final float TIMEDEATHMAX  = 0.6f;
		private Player player;
		private int anzahlLeben = 50; 
		private float timeSinceLastRoar =10;
		private List<LiveUp>liveUps;

	
	public Boss2(float x, float y, List<Boss2>bosse2, Player player, List<LiveUp>liveUps){
		this.posx = x;
		this.posy = y;
		bounding = new Rectangle( (int)x+5, (int)y+5, look[0].getWidth()-10, look[0].getHeight()-10);
		this.bosse2 = bosse2;
		this.player = player;
		this.liveUps = liveUps;
		alive = true;
		leben = anzahlLeben;
		turn = 0;
	}
	
	public void update(float timeSinceLastFrame){
		timeAlive+=timeSinceLastFrame;
		if(timeAlive>TIMETOLIVE||player.alive==false){
			alive = false;
			bosse2.remove(this);
		}
		
		if(posx<-100&&player.alive){
			posx = 800;
			turn=0;
			float x = (float) Math.random();
			if(x<=0.5){
				posy = -50;
				speedy = yspeed;
			}else if(x>0.5){
				posy = 500;
				speedy = -yspeed;
			}
		}
		
		if(leben>(int)(anzahlLeben/4/MoveTest.multi)-1){
			posx-=speedx*timeSinceLastFrame;
		}
		else if(leben<=(int)(anzahlLeben/4/MoveTest.multi)-1){
			posx-=speedx*timeSinceLastFrame*3;
		}
		

		if(leben==0){
			alive= false;
			liveUps.add(new LiveUp(700 , 250, liveUps, player));
			Background.world++;
			Bullet.Score+=20*Bullet.multiplikator;
			Bullet.worldScore = 0;
			Bullet.multiplikator+=2;
			leben= (int)(anzahlLeben/MoveTest.multi);
		      try{
		        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/Boss1Down.wav"));
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
		
		if(alive){
			timeSinceLastRoar+=timeSinceLastFrame;
		}
		if(alive&&leben<=(int)(anzahlLeben/4/MoveTest.multi)&&timeSinceLastRoar>10){
			timeSinceLastRoar=0;
		      try{
		        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/Boss1LowHealth.wav"));
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
		
		posy+=speedy*timeSinceLastFrame;
		turn+=timeSinceLastFrame;
		if(turn>1.8){
			speedy=-speedy;
			turn= 0;
		}
		
		if(!alive){
			timeSinceDead+=timeSinceLastFrame;
			if(timeSinceDead>=TIMEDEATHMAX){
				alive = false;
				bosse2.remove(this);
				timeSinceDead = 0;
			}
		}

		aniTime += timeSinceLastFrame;
		if(aniTime>NEEDEDANYTIME)aniTime = 0;
		
		bounding.x = (int)posx+5;
		bounding.y = (int)posy+5;
		
	}
	
	public Rectangle getBounding(){
		return bounding;
	}
	
	public BufferedImage getLook(){
		if(!alive)return look_dead;
		else{
		if(look.length==0)return null;
		for(int i = 0; i<look.length; i++){
			if(aniTime<(float)(NEEDEDANYTIME/look.length*(i+1))){
				return look[i];
			}	}
		}
		return look[look.length-1];
		
	}
	
}