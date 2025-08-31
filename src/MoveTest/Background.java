package MoveTest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Background {
	private float f_posx;
	private float f_speed;
	private static BufferedImage[] look = new BufferedImage[4];
	public static int world =0;
	public static float wait=0;
	public static float nextLv=6;
	public static float nextLvL;
	private List<Enemy>enemys;
	private List<Enemy2>enemys2;
	private List<Enemy3>enemys3;
	
	public Background(float f_speed, List<Enemy>enemys,  List<Enemy2>enemys2, List<Enemy3>enemys3){
		this.f_speed = f_speed;
		this.enemys = enemys;
		this.enemys2 = enemys2;
		this.enemys3 = enemys3;
		
		try {
			look[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/weltraum.png"));
			look[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/weltraum1.png"));
			look[2] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/weltraum2.png"));
		}catch(IOException e) {e.printStackTrace();}
	}
	
	public void update(float timeSinceLastFrame) {
		f_posx-=f_speed*timeSinceLastFrame;
		
		if(f_posx<-look[0].getWidth()){
			f_posx+=look[0].getWidth();
		}
			
		if(world==1&&wait<=nextLv||world==3&&wait<=nextLv||world==5&&wait<=nextLv){
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
			wait+=timeSinceLastFrame;
			nextLvL = (float) (Math.round((nextLv-wait)*10)/10.0);
		}
		if((world==1&&wait>nextLv)||(world==3&&wait>nextLv)||(world==5&&wait>nextLv)){
			world++;
			wait=0;
			if(world==6){
				MoveTest.win=true;
				world++;
			}
		}
	}
	
	public int getX(){
		return (int)f_posx;
	}
	
	public BufferedImage getLook(){
		if(world==0||world==1){return look[0];
		}if(world==2||world==3){return look[1];
		}if(world==4||world==5){return look[2];
		}else return look[0];
	}
}
