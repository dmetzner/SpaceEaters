package MoveTest;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Player {
	private Rectangle bounding;
	private float f_posx;
	private float f_posy;
	int worldsize_x;
	int worldsize_y;
	private BufferedImage look_start;
	protected BufferedImage look;
	protected BufferedImage look_protect;
	protected BufferedImage look_dead;
	private List<Bullet> bullets;
	private float timeSinceLastShot = 0;
	private final float SHOTFREQ = 0.5f; //0.5
	private List<Bomb>bombs;
	protected static float timeSinceLastbomb = 0;
	private final float bombFREQ = 3f;
	protected static boolean bombLogo=false;
	private List<Enemy>enemys;
	private List<Enemy2>enemys2;
	private List<Enemy3>enemys3;
	private List<Boss1>bosse1;
	private List<Boss2>bosse2;
	private List<Boss3>bosse3;
	protected static boolean alive = false;
	private static int  onlyOnce = 0;
	private List<SpeedWolke>SpeedWolken;
	private List<SuperSpeedWolke>superSpeedWolken;
	private float respawnTime = 0;
	private float resetTimer = 5;
	protected String respawn = resetTimer-respawnTime +"";
	protected static int leben = 0;
	protected float timeSinceLostLeben = 2;
	protected float lifeProtect = 2;
	public int lebenSound = 1;
	public static int games_counter = 0; 
	public static String games_counterS = ""; 
	public static boolean pause= false;
	public static int test = 0;
	private List<LiveUp>liveUps;
	
	
		
	public Player(int x, int y, int worldsize_x, int worldsize_y, List<Bullet> bullets,  
			List<Bomb>bombs, List<Enemy>enemys,  List<Enemy2>enemys2, List<SpeedWolke>SpeedWolken,
			List<SuperSpeedWolke>superSpeedWolken,List<Boss1>bosse1, List<Boss2>bosse2,
			List<Boss3>bosse3, List<Enemy3>enemys3,  List<LiveUp>liveUps){
		try {
		look_start = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/raumschiffchen.png"));
		look = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/raumschiffchen.png"));
		look_protect = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/raumschiff_protect3.png"));
		look_dead = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/raumschiff_kaputt.png"));
		}catch(IOException e) {e.printStackTrace();}
		
		bounding = new Rectangle(x+5, y+5, look.getWidth()-5, look.getHeight()-10);
		f_posx = x;
		f_posy = y;
		this.worldsize_x = 	worldsize_x;
		this.worldsize_y =  worldsize_y;
		this.bullets = bullets;
		this.bombs = bombs;
		this.enemys = enemys;
		this.enemys2 = enemys2;
		this.enemys3 = enemys3;
		this.SpeedWolken = SpeedWolken;
		this.superSpeedWolken = superSpeedWolken;
		this.bosse3 = bosse3;
		this.bosse2 = bosse2;		
		this.bosse1 = bosse1;
		this.liveUps = liveUps;
	}
	
	public void update(float timeSinceLastFrame){
			
		games_counterS = reader.fileContentsToString("games.txt");
		games_counter = Integer.parseInt(games_counterS);
//---------------------------------------------------------LEBEN SOUNDS--------------------------------------

		if(lebenSound==0&&leben==2){
			lebenSound++;
            try{
            	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/2herz.wav"));
            	BufferedInputStream bufferedInputStream = new BufferedInputStream(audioInputStream);
            	        AudioFormat af = audioInputStream.getFormat();
            	        int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            	        byte[] audio = new byte[size];
            	        DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            	            bufferedInputStream.read(audio, 0, size);
            	            Clip clip = (Clip) AudioSystem.getLine(info);
            	            clip.open(af, audio, 0, size);
            	            clip.start();
            } catch(Exception e){  
                }
		}if(lebenSound==0&&leben==1){
			lebenSound++;
            try{
            	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/1herz.wav"));
            	BufferedInputStream bufferedInputStream = new BufferedInputStream(audioInputStream);
            	        AudioFormat af = audioInputStream.getFormat();
            	        int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            	        byte[] audio = new byte[size];
            	        DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            	            bufferedInputStream.read(audio, 0, size);
            	            Clip clip = (Clip) AudioSystem.getLine(info);
            	            clip.open(af, audio, 0, size);
            	            clip.start();
            }catch(Exception e){  
                }
		}if(lebenSound==0&&leben==0){
			lebenSound++;
            try{
            	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/gameover.wav"));
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
//---------------------------------------------------------------------------------------------------------		
//----------------------------------------------------PROG START-----------------------------------------
		
		if(Keyboard.isKeyDown(KeyEvent.VK_1)&&onlyOnce==0&&alive==false){
			MoveTest.multi=2f;
			MoveTest.einfach=true;
			MoveTest.normal=false;
			MoveTest.schwer=false;
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_2)&&onlyOnce==0&&alive==false){
			MoveTest.multi=1f;
			MoveTest.einfach=false;;
			MoveTest.normal=true;
			MoveTest.schwer=false;
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_3)&&onlyOnce==0&&alive==false){
			MoveTest.multi=0.7f;
			MoveTest.einfach=false;
			MoveTest.normal=false;
			MoveTest.schwer=true;
		}
		
		if(Keyboard.isKeyDown(KeyEvent.VK_SPACE)&&onlyOnce==0&&alive==false){
			if(MoveTest.einfach){
				Bullet.multiplikator=1;
			}if(MoveTest.normal){
				Bullet.multiplikator=2;
			}if(MoveTest.schwer){
				Bullet.multiplikator=4;
			}
			alive = true;
			leben = 3;
			respawnTime=0;
			timeSinceLastbomb=bombFREQ;
			respawn = (Math.round((resetTimer-respawnTime)*10)/10.0) +"";
			onlyOnce = 1;
			if(games_counter<999999999){
			games_counter++;
			   FileWriter writer;
			    try {
			      writer = new FileWriter("games.txt");
			      String c = games_counter+"";
			      writer.write(c);
			      writer.close();
			    } catch (IOException e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			    }
			
			}else{
				   FileWriter writer;
				    try {
				      writer = new FileWriter("games.txt");
				      String c = "games played: too much!";;
				      writer.write(c);
				      writer.close();
				    } catch (IOException e) {
				      // TODO Auto-generated catch block
				      e.printStackTrace();
				    }
			}
	}
//---------------------------------------------------------------------------------------------------------			
		if(!alive&&respawnTime<=resetTimer){
				respawn = "wait " + (Math.round((resetTimer-respawnTime)*10)/10.0) + " sec for reset";
				respawnTime+=timeSinceLastFrame;
				
		}
		if((!alive)&& (Keyboard.isKeyDown(KeyEvent.VK_R))&&respawnTime>resetTimer){
			respawnTime=0;
			f_posx = 10;
			f_posy = 300;
			onlyOnce=0;
			Starter.counter=0;
			Bullet.Score = 0;
			Bullet.worldScore=0;
			Bullet.multiplikator =0;

			for(int i =0; i<enemys.size(); i++){
				Enemy e = enemys.get(i);
				e.alive= false;
			}
			for(int i =0; i<enemys2.size(); i++){
				Enemy2 e2 = enemys2.get(i);
				e2.alive= false;
			}
			for(int i =0; i<enemys3.size(); i++){
				Enemy3 e3 = enemys3.get(i);
				e3.alive= false;
			}
			for(int i =0; i<bosse1.size(); i++){
				Boss1 b1 = bosse1.get(i);
				b1.alive= false;
			}
			for(int i =0; i<bosse2.size(); i++){
				Boss2 b2 = bosse2.get(i);
				b2.alive= false;
			}
			for(int i =0; i<bosse3.size(); i++){
				Boss3 b3 = bosse3.get(i);
				b3.alive= false;
			}
			
		}
//----------------------------no move etc.---------------------------------------------------------------------						
		else if(!alive)return;
	
		if(Keyboard.isKeyDown(KeyEvent.VK_K)){
			leben=0;
			lebenSound=0;
		}		
		
//---------------------------------------------------------------------------------------------------------			
		timeSinceLastShot+=timeSinceLastFrame;
		timeSinceLastbomb+=timeSinceLastFrame;
		timeSinceLostLeben+= timeSinceLastFrame;
//----------------------------move--------------------------------------------------------------------			
		SpeedWolken.add(new SpeedWolke(f_posx+5, f_posy+(look.getWidth()/2), SpeedWolken));
		
		if(Keyboard.isKeyDown(KeyEvent.VK_UP)||Keyboard.isKeyDown(KeyEvent.VK_W)){
			f_posy-=300*timeSinceLastFrame;
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_DOWN)||Keyboard.isKeyDown(KeyEvent.VK_S)){
			f_posy+=300*timeSinceLastFrame;
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_LEFT)||Keyboard.isKeyDown(KeyEvent.VK_A)){
			f_posx-=300*timeSinceLastFrame;
		}
		if(Keyboard.isKeyDown(KeyEvent.VK_RIGHT)||Keyboard.isKeyDown(KeyEvent.VK_D)){
			f_posx+=300*timeSinceLastFrame;
			superSpeedWolken.add(new SuperSpeedWolke(f_posx+5, f_posy+(look.getWidth()/2)-(SuperSpeedWolke.getLook().getHeight()/4), superSpeedWolken));
		}
		
//----------------------------shoot--------------------------------------------------------------------			
		if(timeSinceLastShot>SHOTFREQ&&Keyboard.isKeyDown(KeyEvent.VK_SPACE)){
			timeSinceLastShot=0;
			bullets.add(new Bullet(f_posx+(look.getWidth()/2)-Bullet.getLook().getWidth()/2, f_posy+(look.getHeight()/2)-Bullet.getLook().getHeight()/2,
					500, 0, bullets, enemys, enemys2, bosse1, bosse2, bosse3, enemys3));
            try{
            	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/Laser.wav"));
            	BufferedInputStream bufferedInputStream = new BufferedInputStream(audioInputStream);
            	        AudioFormat af = audioInputStream.getFormat();
            	        int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            	        byte[] audio = new byte[size];
            	        DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            	            bufferedInputStream.read(audio, 0, size);
            	            Clip clip = (Clip) AudioSystem.getLine(info);
            	            clip.open(af, audio, 0, size);
            	            clip.start();
            }catch(Exception e){  
                }
            }
//----------------------------bomb--------------------------------------------------------------------	
		if((timeSinceLastbomb>bombFREQ&&bombs.size()<1)){
			bombLogo=true;
		}
		
		if(timeSinceLastbomb>bombFREQ&&Keyboard.isKeyDown(KeyEvent.VK_ENTER)&&bombs.size()<1){
			timeSinceLastbomb=0;
			bombLogo=false;
			bombs.add(new Bomb(f_posx+(look.getWidth()/2)-Bomb.getLook().getWidth()/2, f_posy+(look.getHeight()/2)-Bullet.getLook().getHeight()/2, 0,
					bombs, enemys, enemys2, bosse3, enemys3, bosse1, bosse2));
		}
//----------------------------bounding--------------------------------------------------------------------	
		
		if(f_posx<0)f_posx=0;
		if(f_posx>(worldsize_x-bounding.width))f_posx=(worldsize_x-bounding.width);
		
		if(f_posy<0-look.getHeight()/2)f_posy=worldsize_y-bounding.height;
		if(f_posy>(worldsize_y-bounding.height+look.getHeight()/2))f_posy=(0);//
		
		bounding.x=(int)f_posx+5;
		bounding.y=(int)f_posy+5;
//----------------------------crash--------------------------------------------------------------------	
		for(int i =0; i<enemys.size(); i++){
			Enemy e = enemys.get(i);
			
			if(bounding.intersects(e.getBounding())&&e.alive==true){
				if(timeSinceLostLeben>lifeProtect){
				leben--;
				lebenSound=0;
				timeSinceLostLeben = 0;
				}
				e.alive = false;
			}
		}
		
		for(int i =0; i<enemys3.size(); i++){
			Enemy3 e3 = enemys3.get(i);
			
			if(bounding.intersects(e3.getBounding())&&e3.alive==true){
				if(timeSinceLostLeben>lifeProtect){
				leben--;
				lebenSound=0;
				timeSinceLostLeben = 0;
				}
				e3.leben--;
			}
		}
		
			for(int i =0; i<enemys2.size(); i++){
				Enemy2 e2 = enemys2.get(i);
				
				if(bounding.intersects(e2.getBounding())&&e2.alive==true){
					if(timeSinceLostLeben>lifeProtect){
					leben--;
					lebenSound=0;
					timeSinceLostLeben = 0;
					}
					e2.alive = false;
				}
			}
			
			for(int i =0; i<bosse1.size(); i++){
				Boss1 b1 = bosse1.get(i);
				
				if(bounding.intersects(b1.getBounding())&&b1.alive==true){
					
					if(timeSinceLostLeben>lifeProtect){
					leben--;
					lebenSound=0;
					timeSinceLostLeben = 0;
					}
					
				}
			}
			
			for(int i =0; i<bosse2.size(); i++){
				Boss2 b2 = bosse2.get(i);
				
				if(bounding.intersects(b2.getBounding())&&b2.alive==true){
					
					if(timeSinceLostLeben>lifeProtect){
					leben--;
					lebenSound=0;
					timeSinceLostLeben = 0;
					}
				}
			}
			
			for(int i =0; i<bosse3.size(); i++){
				Boss3 b3 = bosse3.get(i);
				
				if(bounding.intersects(b3.getBounding())&&b3.alive==true){
					
					if(timeSinceLostLeben>lifeProtect){
					leben--;
					lebenSound=0;
					timeSinceLostLeben = 0;
					}	
				}
			}
			
			if(leben<=0){
				alive=false;
				Background.world=0;
				bombLogo=false;
			}
	}
	
	public Rectangle getBounding(){
		return bounding;
	}
	
	public float getX(){
		return f_posx;
	}
	
	public float getY(){
		return f_posy;
	}
	
	public BufferedImage getLook(){
		if(alive==false&&onlyOnce==0)return look_start;
		else if(alive&&leben<=2&&timeSinceLostLeben<=lifeProtect)return look_protect;
		else if(alive==true)return look;
		else return look_dead;
	}
}
