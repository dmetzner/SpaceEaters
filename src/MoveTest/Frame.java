package MoveTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Frame extends JFrame {
	//private Screen screen;
	final Player player;
	final Background bg;
	private Game_over go;
	private Starter s;
	private Leben leben;
	private List<Bullet> bullets;
	private List<Fire> fires;
	private List<Bomb>bombs;
	private List<Enemy>enemys;
	private List<Enemy2>enemys2;
	private List<Enemy3>enemys3;
	private List<Boss1>bosse1;
	private List<Boss2>bosse2;
	private List<Boss3>bosse3;
	private List<SuperSpeedWolke>superSpeedWolken;
	private List<SpeedWolke>SpeedWolken;
	private List<LiveUp>liveUps;
	private BufferedImage bombLook;
	
	Color player_color = Color.BLUE;
	
	private BufferStrategy strat;
	
	public Frame(Starter s, Player player, Background bg, List<Bullet> bullets, List<Bomb> bombs, List<Enemy>enemys, List<Enemy2>enemys2, 
			List<Fire> fires, Game_over go, List<SpeedWolke>SpeedWolken, List<SuperSpeedWolke>superSpeedWolken, Leben leben, List<Boss1>bosse1,
			List<Boss2>bosse2, List<Boss3>bosse3, List<Enemy3>enemys3, List<LiveUp>liveUps){
		super("SpaceEaters");
		setCursor(java.awt.Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_4BYTE_ABGR),new java.awt.Point(0,0),"NOCURSOR"));
	/*	screen = new Screen();
		screen.setBounds(0, 0, 800, 600);
		add(screen);
	*/	addKeyListener(new Keyboard());
		this.s = s;
		this.player = player;
		this.bg = bg;
		this.bullets = bullets;
		this.bombs = bombs;
		this.enemys = enemys;
		this.enemys2 = enemys2;
		this.enemys3 = enemys3;
		this.fires = fires;
		this.go = go;
		this.SpeedWolken = SpeedWolken;
		this.superSpeedWolken = superSpeedWolken;
		this.leben = leben;
		this.bosse1 = bosse1;
		this.bosse2 = bosse2;
		this.bosse3 = bosse3;
		this.liveUps = liveUps;
		try {
			this.bombLook = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/bombLogo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void makeStrat(){
		createBufferStrategy(2);
		strat = getBufferStrategy();
	}

	public void repaintScreen(){
		Graphics g = strat.getDrawGraphics();
		draw(g);
		g.dispose();
		strat.show();
	}
	
	private void draw(Graphics g){
		g.setColor(Color.RED);
		g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 30));
		g.drawImage(bg.getLook(), bg.getX(), 0, null); //hintergrund vor spieler!!
		g.drawImage(bg.getLook(), bg.getX()+bg.getLook().getWidth(),0 , null);
		
		if(MoveTest.einfach&&player.alive==false&&Starter.counter==0){
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 36));
			g.setColor(Color.GREEN);
			g.drawString("easy (1)" , 620, 70);
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 18));
			g.setColor(Color.GRAY);
			g.drawString("normal (2)" , 620, 90);
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 18));
			g.setColor(Color.GRAY);
			g.drawString("hard (3)" , 620, 110);
		}
		if(MoveTest.normal&&player.alive==false&&Starter.counter==0){
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 18));
			g.setColor(Color.GRAY);
			g.drawString("easy (1)" , 620, 60);
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 36));
			g.setColor(Color.GREEN);
			g.drawString("normal (2)" , 620, 90);
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 18));
			g.setColor(Color.GRAY);
			g.drawString("hard (3)" , 620, 110);
		}
		if(MoveTest.schwer&&player.alive==false&&Starter.counter==0){
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 18));
			g.setColor(Color.GRAY);
			g.drawString("easy (1)" , 620, 60);
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 18));
			g.setColor(Color.GRAY);
			g.drawString("normal (2)" , 620, 80);
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 36));
			g.setColor(Color.GREEN);
			g.drawString("hard (3)" , 620, 110);
		}
		
		for(int i = 0; i<enemys.size(); i++){
			Enemy e = enemys.get(i);
			g.drawImage(e.getLook(), e.getBounding().x, e.getBounding().y, null);	
		}
		for(int i = 0; i<enemys3.size(); i++){
			Enemy3 e3 = enemys3.get(i);
			g.drawImage(e3.getLook(), e3.getBounding().x, e3.getBounding().y, null);	
		}
		for(int i = 0; i<enemys2.size(); i++){
			Enemy2 e2 = enemys2.get(i);
			g.drawImage(e2.getLook(), e2.getBounding().x, e2.getBounding().y, null);	
		}
		for(int i = 0; i<fires.size(); i++){
			Fire f = fires.get(i);
			g.drawImage(Fire.getLook(), f.getBounding().x, f.getBounding().y, null);
		}
		for(int i = 0; i<liveUps.size(); i++){
			LiveUp pUp = liveUps.get(i);
			g.drawImage(LiveUp.getLook(), pUp.getBounding().x, pUp.getBounding().y, null);
		}
		for(int i = 0; i<bosse1.size(); i++){
			Boss1 b1 = bosse1.get(i);
			g.drawImage(b1.getLook(), b1.getBounding().x, b1.getBounding().y, null);	
		}
		for(int i = 0; i<bosse2.size(); i++){
			Boss2 b2 = bosse2.get(i);
			g.drawImage(b2.getLook(), b2.getBounding().x, b2.getBounding().y, null);	
		} 
		
		for(int i = 0; i<bosse3.size(); i++){
			Boss3 b3 = bosse3.get(i);
			g.drawImage(b3.getLook(), b3.getBounding().x, b3.getBounding().y, null);	
		}
		
		for(int i = 0; i<bullets.size(); i++){
			Bullet b = bullets.get(i);
			g.drawImage(Bullet.getLook(), b.getBounding().x, b.getBounding().y, null);
		}
		for(int i = 0; i<bombs.size(); i++){
			Bomb s = bombs.get(i);
			g.drawImage(Bomb.getLook(), s.getBounding().x, s.getBounding().y, null);
		}
		
		
		for(int i = 0; i<SpeedWolken.size(); i++){
			SpeedWolke pa = SpeedWolken.get(i);
			g.drawImage(SpeedWolke.getLook(), pa.getBounding().x, pa.getBounding().y, null);
		}
		for(int i = 0; i<superSpeedWolken.size(); i++){
			SuperSpeedWolke pa = superSpeedWolken.get(i);
			g.drawImage(SuperSpeedWolke.getLook(), pa.getBounding().x, pa.getBounding().y, null);
		}
		

		g.drawImage(player.getLook(), player.getBounding().x, player.getBounding().y, null);
		
		if(player.getLook()==player.look||player.getLook()==player.look_protect){
			g.drawString(Bullet.HS, 25, 50);
		}
		g.drawImage(Leben.getLook(), leben.getBounding().x, leben.getBounding().y, null);
		
		if(Player.bombLogo){
			g.drawImage(bombLook, 100, 550, null);
		}
		
		g.drawImage(Starter.getLook(), s.getBounding().x, s.getBounding().y, null);
		g.drawImage(Game_over.getLook(), go.getBounding().x, go.getBounding().y, null);
		if(player.getLook()==player.look_dead){
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 60));
			g.drawString(Bullet.finalScore, 80, 80);
			g.setColor(Color.red);
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 25));
			g.drawString("games played: " +Player.games_counterS, 450, 470);
			g.drawString("Highscore: "+Bullet.highScoreS, 100, 470);
			g.setColor(Color.BLUE);
			g.drawString(player.respawn, 500, 350);
		}	
		if(MoveTest.pauseLogo){
			g.setColor(Color.GREEN);
			g.setFont(new Font("Castellar", Font.BOLD + Font.ITALIC, 100));
			g.drawString("PAUSE", 220, 325);
		}
		if(MoveTest.quitYN){
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 35));
			g.setColor(Color.BLUE);
			g.drawString("YOU WANNA LEAVE THE GAME?, press " , 50, 425);
			g.setColor(Color.RED);
			g.drawString("Y(es) for quit    /", 240, 525);
			g.setColor(Color.YELLOW);
			g.drawString("/    N(o) for cancel", 460, 525);
		}
		if(MoveTest.winLogo){
			g.setFont(new Font("Castellar", Font.BOLD + Font.ITALIC, 60));
			g.setColor(Color.GREEN);
			g.drawString("CONGRATULATION" , 50, 125);
			g.setFont(new Font("Castellar", Font.BOLD + Font.ITALIC, 80));
			g.drawString("  - you won" , 50, 225);
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 30));
			g.setColor(Color.BLUE);
			g.drawString("for EndlessMode press SPACE (" + MoveTest.winLogoTime4 , 70, 465);
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 40));
			g.setColor(Color.RED);
			g.drawString("Can You beat the Highscore?!" , 150, 355);
		}
		if(Background.world==1){
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 70));
			g.setColor(Color.BLUE);
			g.drawString("NEXT LVL in " + Background.nextLvL + "sec." , 70, 150);
		}else if(Background.world==3){
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 70));
			g.setColor(Color.GREEN);
			g.drawString("NEXT LVL in " + Background.nextLvL + "sec." , 70, 150);
		}else if(Background.world==5){
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 70));
			g.setColor(Color.RED);
			g.drawString("NEXT LVL in " + Background.nextLvL + "sec." , 70, 150);
		}
		
	}

/*	private class Screen extends JLabel{
		@Override
		protected void SuperSpeedWolkeComponent(Graphics g) {
			super.SuperSpeedWolkeComponent(g);
			g.setColor(player_color);
			g.drawImage(bg.getLook(), bg.getX(), 0, null); //hintergrund vor spieler!!
			g.drawImage(bg.getLook(), bg.getX()+bg.getLook().getWidth(),0 , null);
			g.drawImage(player.getBounding(), player.getBounding().x, player.getBounding().y, null);
		}
	}
	
	*/
}
