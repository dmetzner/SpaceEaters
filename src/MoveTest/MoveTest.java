package MoveTest;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class MoveTest {

		public static int Score = 0;
		public static boolean pause = false;
		public static boolean pauseLogo = false;
		public static float pausenTimer = 0;
		public static boolean win = false;
		public static boolean winLogo = false;
		public static float winLogoTime = 0;
		public static float winLogoTime2 = 0;
		public static float winLogoTime3 = 4;
		public static String winLogoTime4 = "";
		public static float timeSinceMusic=208;
		public static boolean quitYN= false;
		public static boolean einfach = false;
		public static boolean normal = true;
		public static boolean schwer = false;
		public static float multi = 1;
	
		static List<Bullet> bullets = new LinkedList<Bullet>();
		static List<Bomb> bombs = new LinkedList<Bomb>();
		static List<Enemy> enemys = new LinkedList<Enemy>();
		static List<Enemy2> enemys2 = new LinkedList<Enemy2>();
		static List<Enemy3> enemys3 = new LinkedList<Enemy3>();
		static List<Fire> fires = new LinkedList<Fire>();
		static List<Boss1>bosse1 = new LinkedList<Boss1>();
		static List<Boss2>bosse2 = new LinkedList<Boss2>();
		static List<Boss3>bosse3 = new LinkedList<Boss3>();
		static List<SpeedWolke> SpeedWolken = new LinkedList<SpeedWolke>();
		static List<SuperSpeedWolke> superSpeedWolken = new LinkedList<SuperSpeedWolke>();
		static List<LiveUp>liveUps = new LinkedList<LiveUp>();
		
		static Player player = new Player(10, 300, 800, 600, bullets, bombs, enemys, enemys2, SpeedWolken, superSpeedWolken, bosse1, bosse2, bosse3, enemys3, liveUps);
		static Background bg = new Background(100, enemys, enemys2, enemys3);
		static Starter s = new Starter(10, 50, player);
		static Game_over go = new Game_over(0, 100, player);
		static Leben leben = new Leben(20, 550, player);
		
	public static void main(String[] args) {
		
		float timeSinceLastEnemySpawn = 0;
		float timeSinceLastEnemy2Spawn = 0;
		float timeSinceLastEnemy3Spawn = 0;
		float EnemySpawnTime;
		float Enemy2SpawnTime;
		float Enemy3SpawnTime;
		boolean EnemySpawn = false;
		boolean Enemy2Spawn = false;
		boolean Enemy3Spawn = false;
		boolean Boss1Spawn = false;
		boolean Boss2Spawn = false;
		boolean Boss3Spawn = false;
		

		Frame f = new Frame(s, player, bg, bullets, bombs, enemys, enemys2, fires, go, SpeedWolken, superSpeedWolken, leben, bosse1, bosse2, bosse3, enemys3, liveUps);							
		f.setSize(800, 600);
		f.setUndecorated(true); // vor f.setvisible!!
		f.setVisible(true);  //-> da device, nicht nötig
		f.setResizable (false);	
		
/*
		DisplayMode displayMode = new DisplayMode(800, 600, 32, 60);
		GraphicsEnvironment enviroment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = enviroment.getDefaultScreenDevice();
		device.setFullScreenWindow(f);
		device.setDisplayMode(displayMode);
*/	
		
		f.makeStrat();
		long lastFrame = System.currentTimeMillis();
		long lastFrame2 = System.currentTimeMillis();
		
		while(true){
			long thisFrame = System.currentTimeMillis();
			float timeSinceLastFrame2 = (float)(thisFrame -lastFrame2)/1000f;
			lastFrame2= thisFrame;
	
			timeSinceMusic+=timeSinceLastFrame2;
			if(timeSinceMusic>=208){
				timeSinceMusic=0;
	            try{
	            	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/beat.wav"));
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
			}
	
			pausenTimer+=timeSinceLastFrame2;
			if(Player.leben>0&&Keyboard.isKeyDown(KeyEvent.VK_P)&&!pause&&pausenTimer>0.2){
				pause = true;	
				pauseLogo = true;
				pausenTimer = 0;
				
				}
			if(Keyboard.isKeyDown(KeyEvent.VK_P)&&pause&&pausenTimer>0.2){
				pause = false;
				pauseLogo = false;
				pausenTimer = 0;
				thisFrame = System.currentTimeMillis();
				lastFrame = System.currentTimeMillis();
				
				}
			
			if(Player.leben>0&&!pause&&win){
				pause = true;
				win = false;
				winLogo = true;
				winLogoTime2 =0;
				}
			if(winLogo&&winLogoTime2<=winLogoTime3){
				winLogoTime2+=timeSinceLastFrame2;
				winLogoTime4 = "wait " + (Math.round((winLogoTime3-winLogoTime2)*10)/10.0) + ")";
			}
			if(winLogo&&Keyboard.isKeyDown(KeyEvent.VK_SPACE)&&winLogoTime2>=4){
				winLogoTime=10;
			}
			if(Player.leben>0&&pause&&winLogoTime>=10){
				winLogoTime=0;
				pause = false;
				pauseLogo = false;
				winLogo=false;	
				quitYN=false;
				pausenTimer = 0;
				thisFrame = System.currentTimeMillis();
				lastFrame = System.currentTimeMillis();
				}
//-------------------------------------beenden------------------------------
			if(!quitYN&&Keyboard.isKeyDown(KeyEvent.VK_ESCAPE)&&pausenTimer>0.2||!quitYN&&Keyboard.isKeyDown(KeyEvent.VK_Q)&&pausenTimer>0.2){
				quitYN=true;
				pause = true;	
				pausenTimer = 0;
			}
			if(quitYN&&Keyboard.isKeyDown(KeyEvent.VK_Y))System.exit(0);
			if(quitYN&&Keyboard.isKeyDown(KeyEvent.VK_N)||(quitYN&&Keyboard.isKeyDown(KeyEvent.VK_ESCAPE)&&pausenTimer>0.2)||(quitYN&&Keyboard.isKeyDown(KeyEvent.VK_Q)&&pausenTimer>0.2)){
				quitYN = false;
				pause = false;
				pauseLogo = false;
				winLogo=false;
				pausenTimer = 0;
				thisFrame = System.currentTimeMillis();
				lastFrame = System.currentTimeMillis();
			}
				
//--------------------------------------------------------------------------			
			f.repaintScreen();  
			
			if(!pause){

			float timeSinceLastFrame = (float)(thisFrame -lastFrame)/1000f;
			lastFrame= thisFrame;
			
//-------------------------------enemyspawns-----------------------------------------
			
			
			if(Background.world==0){
				EnemySpawn = true;
				EnemySpawnTime = 1.1f*multi;
				Enemy2Spawn = false;
				Enemy2SpawnTime = 0*multi;
				Enemy3Spawn = false;
				Enemy3SpawnTime = 0*multi;
				
				Boss1Spawn = true;
				Boss2Spawn = false;
				Boss3Spawn = false;
				
			}else if(Background.world==2){
				EnemySpawn = false;
				EnemySpawnTime = 0*multi;
				Enemy2Spawn = false;
				Enemy2SpawnTime = 0*multi;
				Enemy3Spawn = true;
				Enemy3SpawnTime = 2.5f*multi;
				
				Boss1Spawn = false;
				Boss2Spawn = true;
				Boss3Spawn = false;
				
			}else if(Background.world==4){
				EnemySpawn = false;
				EnemySpawnTime = 0*multi;
				Enemy2Spawn = true;
				Enemy2SpawnTime = 1.3f*multi;
				Enemy3Spawn = false;
				Enemy3SpawnTime = 0*multi;
				
				Boss1Spawn = false;
				Boss2Spawn = false;
				Boss3Spawn = true;
				
			}else if(Background.world==5){
				normal=true;
				einfach=false;
				schwer=false;
				multi=1;
				EnemySpawn = false;
				EnemySpawnTime = 0;
				Enemy2Spawn = false;
				Enemy2SpawnTime = 0;
				Enemy3Spawn = false;
				Enemy3SpawnTime = 0f;
				Boss1Spawn = false;
				Boss2Spawn = false;
				Boss3Spawn = false;	
			
			}else if(Background.world>=6){
	
				EnemySpawn = true;
				EnemySpawnTime = 2.3f*multi;
				Enemy2Spawn = true;
				Enemy2SpawnTime = 4.7f*multi;
				Enemy3Spawn = true;
				Enemy3SpawnTime = 5.8f*multi;
				
				Boss1Spawn = false;
				Boss2Spawn = false;
				Boss3Spawn = false;	
				
				if(Bullet.worldScore>=Bullet.worldScoreBreak){
					liveUps.add(new LiveUp(700 , 250, liveUps, player));
					Bullet.multiplikator+=10;
					if(multi>=0.7){
						multi-=0.05;
					}else if(multi<0.7&&multi>=0){
						multi-=0.01;
					}
					Bullet.worldScore=0;
					Bullet.worldScoreBreak*=1.5;
				}

				
			}else{
				EnemySpawn = false;
				EnemySpawnTime = 0;
				Enemy2Spawn = false;
				Enemy2SpawnTime = 0;
				Enemy3Spawn = false;
				Enemy3SpawnTime = 0f;
				
				Boss1Spawn = false;
				Boss2Spawn = false;
				Boss3Spawn = false;	
			}
			
			
			timeSinceLastEnemySpawn+=timeSinceLastFrame;
			if(timeSinceLastEnemySpawn>EnemySpawnTime&&EnemySpawn){
				timeSinceLastEnemySpawn = 0;
				spwanEnemy();
			}
			
			timeSinceLastEnemy3Spawn+=timeSinceLastFrame;
			if(timeSinceLastEnemy3Spawn>Enemy3SpawnTime&&Enemy3Spawn){
				timeSinceLastEnemy3Spawn = 0;
				spwanEnemy3();
			}
			
			timeSinceLastEnemy2Spawn+=timeSinceLastFrame;
			if(timeSinceLastEnemy2Spawn>Enemy2SpawnTime&&Enemy2Spawn){
				timeSinceLastEnemy2Spawn = 0;
				spwanEnemy2();
			}
			
			if(Bullet.worldScore>=25/MoveTest.multi&&bosse1.size()<1&&Boss1Spawn){ //25
				spwanBoss1();
			}
			
			if(Bullet.worldScore>=25/MoveTest.multi&&bosse2.size()<1&&Boss2Spawn){ // 60
				spwanBoss2();
			}
			if(Bullet.worldScore>=45/MoveTest.multi&&bosse3.size()<1&&Boss3Spawn){ // 105
				spwanBoss3();
			}
			
			
			leben.update(timeSinceLastFrame);
			s.update(timeSinceLastFrame);
			player.update(timeSinceLastFrame);
			bg.update(timeSinceLastFrame);

			
			for(int i = 0; i<bullets.size(); i++){
				bullets.get(i).update(timeSinceLastFrame); //
			}
			
			for(int i = 0; i<bombs.size(); i++){
				bombs.get(i).update(timeSinceLastFrame); //
			}
			
			
			for(int i = 0; i<enemys.size(); i++){
				enemys.get(i).update(timeSinceLastFrame);
			}
			for(int i = 0; i<enemys3.size(); i++){
				enemys3.get(i).update(timeSinceLastFrame);
			}
			for(int i = 0; i<enemys2.size(); i++){
				enemys2.get(i).update(timeSinceLastFrame);
			}

			for(int i = 0; i<fires.size(); i++){
				fires.get(i).update(timeSinceLastFrame);
			}
			
			for(int i = 0; i<bosse1.size(); i++){
				bosse1.get(i).update(timeSinceLastFrame);
			}
			
			for(int i = 0; i<bosse2.size(); i++){
				bosse2.get(i).update(timeSinceLastFrame);
			} 
			
			for(int i = 0; i<bosse3.size(); i++){
				bosse3.get(i).update(timeSinceLastFrame);
			}
			
			for(int i = 0; i<liveUps.size(); i++){
				liveUps.get(i).update(timeSinceLastFrame);
			}
			
			for(int i = 0; i<SpeedWolken.size(); i++){
				SpeedWolken.get(i).update(timeSinceLastFrame);
			}
			for(int i = 0; i<superSpeedWolken.size(); i++){
				superSpeedWolken.get(i).update(timeSinceLastFrame);
			}
			
			
			
			go.update(timeSinceLastFrame);
			
			f.repaintScreen();  
			/*try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}}
	}

public static void spwanEnemy(){
	if(player.alive&&Boss1.alive==false&&Boss2.alive==false&&Boss3.alive==false){
		enemys.add(new Enemy(700 + (int)(Math.random()*50), -50 + (int)(Math.random()*600), enemys, player));
	}
}
public static void spwanEnemy3(){
	if(player.alive&&Boss1.alive==false&&Boss2.alive==false&&Boss3.alive==false){
		float x = (float) Math.random();
		if(x<=0.5){
			enemys3.add(new Enemy3(700 + (int)(Math.random()*50), -50, 300, enemys3, player));
		}else if(x>0.5){
			enemys3.add(new Enemy3(700 + (int)(Math.random()*50), 500, -300, enemys3, player));
		}
  }
}
public static void spwanEnemy2(){
	if(player.alive&&Boss1.alive==false&&Boss2.alive==false&&Boss3.alive==false){
		enemys2.add(new Enemy2(700 + (int)(Math.random()*50), -50 + (int)(Math.random()*600), enemys2, player, fires));
  }
}

public static void spwanBoss1(){
	if(player.alive){
		bosse1.add(new Boss1(600 + (int)(Math.random()*50),-50+ (int)(Math.random()*500) ,bosse1, player, liveUps));
	      try{
	        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/Boss1Spawn.wav"));
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

public static void spwanBoss2(){
	if(player.alive){
		bosse2.add(new Boss2(600 + (int)(Math.random()*50),-50 ,bosse2, player, liveUps));
	      try{
	        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/Boss1Spawn.wav"));
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

public static void spwanBoss3(){
	if(player.alive){
		bosse3.add(new Boss3(600 + (int)(Math.random()*50),-50 ,bosse3, player, fires, liveUps));
	      try{
	        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("musik/Boss1Spawn.wav"));
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

}



