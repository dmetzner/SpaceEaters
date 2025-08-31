package MoveTest;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Enemy3 {
	private static BufferedImage[] look = new BufferedImage[2];
	private static BufferedImage look_dead;
	private final static float NEEDEDANYTIME = 0.8f;
	private float aniTime = 0;
	private int anzahlLeben = 5;
	public int leben = 0;
	private float turn = 0;
	private float speedy = 0;

	public boolean alive = true;
	
	static{
		try {
		look[0] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/enemy3_1.png"));
		look[1] = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/enemy3_3.png"));
		look_dead = ImageIO.read(Bullet.class.getClassLoader().getResourceAsStream("gfx/enemy3_kapputt.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
		
		public float posx;
		private float posy;
		private float speedx = 70;

		private Rectangle bounding;
		
		private List<Enemy3>enemys3;
		private float timeAlive;
		private final float TIMETOLIVE = 200;
		private float timeSinceDead = 0;
		private final float TIMEDEATHMAX  = 0.6f;
		private Player player;

	
	public Enemy3(float x, float y, float speedy, List<Enemy3>enemys3, Player player){
		this.posx = x;
		this.posy = y;
		this.speedy = speedy;
		bounding = new Rectangle( (int)x+5, (int)y+5, look[0].getWidth()-10, look[0].getHeight()-10);
		this.enemys3 = enemys3;
		this.player = player;
		this.leben = 2;
	}
	
	public void update(float timeSinceLastFrame){
		timeAlive+=timeSinceLastFrame;
		if(timeAlive>TIMETOLIVE||player.alive==false){
			enemys3.remove(this);
		}
		
		if(leben>=2){
			posx-=speedx*timeSinceLastFrame;
		}
		else if(leben<2){
			posx-=speedx*timeSinceLastFrame*2;
		}
		if(leben==0){
			alive= false;
			Bullet.worldScore++;
			Bullet.Score+=2*Bullet.multiplikator;
			leben = 2;
		}
		
		if(posx<0&&player.alive){
			posx= 800;
		}
		
		
		if(!alive){
			timeSinceDead+=timeSinceLastFrame;
			if(timeSinceDead>=TIMEDEATHMAX){
				enemys3.remove(this);
				timeSinceDead = 0;
			}
		}
			
		
		aniTime += timeSinceLastFrame;
		if(aniTime>NEEDEDANYTIME)aniTime = 0;
							
		posy+=speedy*timeSinceLastFrame;
		turn+=timeSinceLastFrame;
		if(turn>2){
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