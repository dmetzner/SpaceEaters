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

public class Boss3 {
	private static BufferedImage[] look = new BufferedImage[4];
	private static BufferedImage look_dead;
	private final static float NEEDEDANYTIME = 0.8f;
	private float aniTime = 0;

	public static boolean alive = false;
	protected int leben = 0;
	
	static{
		try {
		look[0] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss3_1.png"));
		look[1] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss3_2.png"));
		look[2] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss3_3.png"));
		look[3] = look[0];
		look_dead = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/boss3_kapputt.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
		
		public float posx;
		private float posy;
		private float speedy = 150;

		private Rectangle bounding;
		private List<Fire>fires;
		private List<Boss3>bosse3;
		private float timeAlive;
		private final float TIMETOLIVE = 200;
		private float timeSinceDead = 0;
		private final float TIMEDEATHMAX  = 0.6f;
		private Player player;
		private float LastFire = 0;
		private float LastFire2 = 0;
		private final float FIREFREQ = 1.8f;
		private int anzahlLeben = 70; 
		private float turn = 0;
		private float timeSinceLastRoar =10;
		private List<LiveUp>liveUps;

	
	public Boss3(float x, float y, List<Boss3>bosse3, Player player, List<Fire>fires, List<LiveUp>liveUps){
		this.posx = x;
		this.posy = y;
		bounding = new Rectangle( (int)x+5, (int)y+5, look[0].getWidth()-10, look[0].getHeight()-10);
		this.bosse3 = bosse3;
		this.player = player;
		alive = true;
		leben = (int) (anzahlLeben/MoveTest.multi);
		this.fires = fires;
		this.liveUps= liveUps;
	}
	
	public void update(float timeSinceLastFrame){
		timeAlive+=timeSinceLastFrame;
		if(timeAlive>TIMETOLIVE||player.alive==false){
			alive = false;
			bosse3.remove(this);
		}
		
		if(leben==0){
			alive= false;
			liveUps.add(new LiveUp(700 , 250, liveUps, player));
			Background.world++;
			Bullet.Score+=30*Bullet.multiplikator;
			Bullet.worldScore = 0;
			Bullet.multiplikator+=3;
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
		if((alive&&leben<=(int)(anzahlLeben/4/MoveTest.multi)&&timeSinceLastRoar>10)){
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
		
		LastFire+= timeSinceLastFrame;
		if(LastFire>FIREFREQ&&alive&&leben>(int)(anzahlLeben/4/MoveTest.multi)-1){
			LastFire=0;
			fires.add(new Fire(posx+50, posy+150, fires, player));
			fires.add(new Fire(posx+50, posy+100, fires, player));
			fires.add(new Fire(posx+50, posy+50, fires, player));
			fires.add(new Fire(posx+50, posy+200, fires, player));
			fires.add(new Fire(posx+50, posy, fires, player));
		}
		LastFire2+= timeSinceLastFrame;
		if(alive&&LastFire2>FIREFREQ&&leben<=(int)(anzahlLeben/4/MoveTest.multi)-1){
			LastFire2=0;
			fires.add(new Fire(posx+50, posy+150, fires, player));
			fires.add(new Fire(posx+50, posy+100, fires, player));
			fires.add(new Fire(posx+50, posy+50, fires, player));
			fires.add(new Fire(posx+50, posy+200, fires, player));
			fires.add(new Fire(posx+50, posy, fires, player));
			fires.add(new Fire(posx+50, posy+250, fires, player));
			fires.add(new Fire(posx+50, posy-50, fires, player));
			fires.add(new Fire(posx+50, posy+300, fires, player));
			fires.add(new Fire(posx+50, posy-100, fires, player));
		}
		
		
		if(!alive){
			timeSinceDead+=timeSinceLastFrame;
			if(timeSinceDead>=TIMEDEATHMAX){
				alive = false;
				bosse3.remove(this);
				timeSinceDead = 0;
			}
		}
			
		
		aniTime += timeSinceLastFrame;
		if(aniTime>NEEDEDANYTIME)aniTime = 0;
		
		
		posy+=speedy*timeSinceLastFrame;
		turn+=timeSinceLastFrame;
		if(turn>3){
			speedy=-speedy;
			turn= 0;
		}
		
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
