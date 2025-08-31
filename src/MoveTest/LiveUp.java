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

public class LiveUp {
	private static BufferedImage[] look = new BufferedImage[4];
		
	static{
		try {
			look[0] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/LiveUp.png"));
			look[1] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/LiveUp45.png"));
			look[2] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/LiveUp90.png"));
			look[3] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/LiveUp135.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
	
	private float f_posx;
	private float f_posy;
	private float f_speedx = 200;
	private Rectangle bounding;
	private List<LiveUp>liveUps;
	private float timeAlive;
	private final float TIMETOLIVE = 6;
	private Player player;
	private final static float NEEDEDANYTIME = 0.8f;
	private static float aniTime = 0;
	
	
	
	public LiveUp(float x, float y, List<LiveUp>liveUps, Player player){
		this.f_posx = x;
		this.f_posy = y;
		bounding = new Rectangle( (int) x, (int)y, look[0].getWidth(), look[0].getHeight());
		this.liveUps = liveUps;
		this.player = player;
		this.f_speedx= 200;
	}
	
	public void update(float timeSinceLastFrame){
		timeAlive+=timeSinceLastFrame;
		if(timeAlive>TIMETOLIVE){
			liveUps.remove(this);
		}
		if(f_posx<0){
			f_posx= 20;
			f_speedx= 0;
		}
		
		
		f_posx-=f_speedx*timeSinceLastFrame;
		bounding.x = (int)f_posx;
		bounding.y = (int)f_posy;

		if(bounding.intersects(player.getBounding())){
			liveUps.remove(this);
			if(Player.leben<3&&Player.leben>0){
				Player.leben++;
			      try{
			        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/heal.wav"));
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
		}
		
		aniTime += timeSinceLastFrame;
		if(aniTime>NEEDEDANYTIME)aniTime = 0;
	}

	public Rectangle getBounding(){
		return bounding;
	}
	
	public static BufferedImage getLook(){
		if(look.length==0)return null;
		
		for(int i = 0; i<look.length; i++){
			if(aniTime<(float)(NEEDEDANYTIME/look.length*(i+1))){
				return look[i];
			}	}
		return look[look.length-1];
	}
	
}