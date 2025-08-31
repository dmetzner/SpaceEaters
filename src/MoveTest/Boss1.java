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

public class Boss1 {
	private static BufferedImage[] look = new BufferedImage[4];
	private static BufferedImage look_dead;
	private final static float NEEDEDANYTIME = 0.8f;
	private float aniTime = 0;

	public static boolean alive = false;
	protected int leben = 0;
	
	static{
		try {
		look[0] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss1_1.png"));
		look[1] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss1_2.png"));
		look[2] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss1_3.png"));
		look[3] = look[0];
		look_dead = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss1_kapputt.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
		
		public float posx;
		private float posy;
		private float speedx = 200;

		private Rectangle bounding;
		private List<Boss1>bosse1;
		private float timeAlive;
		private final float TIMETOLIVE = 200;
		private float timeSinceDead = 0;
		private final float TIMEDEATHMAX  = 0.6f;
		private Player player;
		private int anzahlLeben = 25; 
		private float timeSinceLastRoar =10;
		private List<LiveUp>liveUps;

	
	public Boss1(float x, float y, List<Boss1>bosse1, Player player, List<LiveUp>liveUps){
		this.posx = x;
		this.posy = y;
		bounding = new Rectangle( (int)x+5, (int)y+5, look[0].getWidth()-10, look[0].getHeight()-10);
		this.bosse1 = bosse1;
		this.player = player;
		this.liveUps = liveUps;
		alive = true;
		leben = (int) (anzahlLeben/MoveTest.multi);
	}
	
	public void update(float timeSinceLastFrame){
		timeAlive+=timeSinceLastFrame;
		if(timeAlive>TIMETOLIVE||player.alive==false){
			alive = false;
			bosse1.remove(this);
		}
		if(leben>(int)(anzahlLeben/4/MoveTest.multi)-1){
			posx-=speedx*timeSinceLastFrame;
		}
		else if(leben<=(int)(anzahlLeben/4/MoveTest.multi)-1){
			posx-=speedx*timeSinceLastFrame*1.5;
		}
		
		if(posx<0&&player.alive){
			posx = 800;
			posy = -50+ (int)(Math.random()*500);
		}
		
		
		if(leben==0){
			alive= false;
			Background.world++;
			Bullet.worldScore = 0;
			Bullet.Score+=10*Bullet.multiplikator;
			Bullet.multiplikator++;
			liveUps.add(new LiveUp(700 , 250, liveUps, player));
			leben= (int) (anzahlLeben/MoveTest.multi);
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
		
		if(!alive){
			timeSinceDead+=timeSinceLastFrame;
			if(timeSinceDead>=TIMEDEATHMAX){
				alive = false;
				bosse1.remove(this);
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
