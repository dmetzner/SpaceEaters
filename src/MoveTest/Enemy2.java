package MoveTest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Enemy2 {
	private static BufferedImage[] look = new BufferedImage[4];
	private static BufferedImage look_dead;
	private final static float NEEDEDANYTIME = 0.8f;
	private float aniTime = 0;
	private Player player;

	public boolean alive = true;
	
	static{
		try {
		look[0] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/enemy2_1.png"));
		look[1] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/enemy2_2.png"));
		look[2] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/enemy2_3.png"));
		look[3] = look[0];
		look_dead = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/enemy2_kaputt.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
		
		public float posx;
		private float posy;

		private Rectangle bounding;
		
		private List<Enemy2>enemys2;
		private float timeAlive;
		private final float TIMETOLIVE = 200;
		private float speedx = 150;
		private float timeSinceDead = 0;
		private final float TIMEDEATHMAX  = 0.6f;
		private float timeNoFire= 0;
		private List<Fire> fires;
	
	public Enemy2(float x, float y, List<Enemy2>enemys2, Player player, List<Fire> fires){
		this.posx = x;
		this.posy = y;
		bounding = new Rectangle( (int)x+5, (int)y+5, look[0].getWidth()-10, look[0].getHeight()-10);
		this.enemys2 = enemys2;
		this.player = player;
		this.fires = fires;
		fires.add(new Fire(posx, posy, fires, player));
	}
	
	public void update(float timeSinceLastFrame){
		timeAlive+=timeSinceLastFrame;
		if(timeAlive>TIMETOLIVE){
			enemys2.remove(this);
		}
		
		if(!alive||player.alive==false){
			timeSinceDead+=timeSinceLastFrame;
			posx-= speedx*timeSinceLastFrame;
			if(timeSinceDead>=TIMEDEATHMAX){
				enemys2.remove(this);
				timeSinceDead = 0;
			}
		}

		aniTime += timeSinceLastFrame;
		if(aniTime>NEEDEDANYTIME)aniTime = 0;
		
		
		if(alive&&player.alive){	
			timeNoFire+=timeSinceLastFrame;
			if(timeNoFire>2){
				timeNoFire=0;
				fires.add(new Fire(posx, posy, fires, player));
			}
			if(player.getY()-posy<-10){
				posy-=100*timeSinceLastFrame;
			}
			else if(player.getY()-posy>10){
				posy+=100*timeSinceLastFrame;
			}
			if(player.getX()<posx){
				posx-=100*timeSinceLastFrame;
			}
			
			if(player.getX()>posx){
				posx+=100*timeSinceLastFrame;
			}
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
