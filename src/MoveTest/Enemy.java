package MoveTest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Enemy {
	private static BufferedImage[] look = new BufferedImage[4];
	private static BufferedImage look_dead;
	private final static float NEEDEDANYTIME = 0.8f;
	private float aniTime = 0;

	public boolean alive = true;
	
	static{
		try {
		look[0] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/enemy1.png"));
		look[1] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/enemy2.png"));
		look[2] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/enemy3.png"));
		look[3] = look[0];
		look_dead = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/enemy_kaputt.png"));
		}catch(IOException e) {
		System.out.println("error du bitch");
		{e.printStackTrace();}
		}
	}
		
		public float posx;
		private float posy;
		private float speedx = 150;

		private Rectangle bounding;
		
		private List<Enemy>enemys;
		private float timeAlive;
		private final float TIMETOLIVE = 200;
		private float timeSinceDead = 0;
		private final float TIMEDEATHMAX  = 0.6f;
		private Player player;

	
	public Enemy(float x, float y, List<Enemy>enemys, Player player){
		this.posx = x;
		this.posy = y;
		bounding = new Rectangle( (int)x+5, (int)y+5, look[0].getWidth()-10, look[0].getHeight()-10);
		this.enemys = enemys;
		this.player = player;
	}
	
	public void update(float timeSinceLastFrame){
		timeAlive+=timeSinceLastFrame;
		if(timeAlive>TIMETOLIVE||player.alive==false){
			enemys.remove(this);
		}
		
		if(posx<0&&player.alive){
			posx= 800;
		}
		
		
		if(!alive){
			timeSinceDead+=timeSinceLastFrame;
			if(timeSinceDead>=TIMEDEATHMAX){
				enemys.remove(this);
				timeSinceDead = 0;
			}
		}
			
		
		aniTime += timeSinceLastFrame;
		if(aniTime>NEEDEDANYTIME)aniTime = 0;
						
		posx-=speedx*timeSinceLastFrame;		
		
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
