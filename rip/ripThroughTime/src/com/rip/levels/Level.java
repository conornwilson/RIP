package com.rip.levels;

import java.util.ArrayList;
import java.util.Random;

import renderers.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rip.RipGame;
import com.rip.objects.Enemy;
import com.rip.objects.Player;
//import com.badlogic.gdx.Gdx;

public abstract class Level {


	public RipGame game;
	Player player;
	LevelRenderer lr;
	ArrayList<Enemy> enemies;
	private InputHandler in;
	public int levelLength;
	public String levelName;
	public String levelHudColor;
	
	Music leveltheme;
	
	public boolean end = false;

	Random r = new Random();

	//////////HUD OBJECTS//////////
	
	Texture timeFreezeOverlay = new Texture(Gdx.files.internal("data/timeFreezeOverlay.png"));
	public Texture level_complete = new Texture(Gdx.files.internal("data/level_complete.png"));
	Texture timebaroutline = new Texture(Gdx.files.internal("data/timebaroutline.png"));
	Texture timebaroutlineWhite = new Texture(Gdx.files.internal("data/timebaroutlineWhite.png"));
	Texture timebar = new Texture(Gdx.files.internal("data/timebar.png"));
	Texture healthbaroutline = new Texture(Gdx.files.internal("data/healthbaroutline.png"));
	Texture healthbaroutlineWhite = new Texture(Gdx.files.internal("data/healthbaroutlineWhite.png"));
	Texture healthbar = new Texture(Gdx.files.internal("data/healthbar.png"));
	Texture pauseOverlay = new Texture(Gdx.files.internal("data/pauseOverlay.png"));
	Texture timeFreezeLine = new Texture(Gdx.files.internal("data/timeLine.png"));
	BitmapFont font = new BitmapFont(Gdx.files.internal("data/arcadeFontBlack18.fnt"),false);
	BitmapFont fontBig = new BitmapFont(Gdx.files.internal("data/arcadeFontBlack32.fnt"),false);
	BitmapFont fontWhite = new BitmapFont(Gdx.files.internal("data/arcadeFontWhite18.fnt"),false);
	BitmapFont fontBigWhite = new BitmapFont(Gdx.files.internal("data/arcadeFontWhite32.fnt"),false);

	public Level(RipGame game) {
			this.game = game;
			enemies = new ArrayList<Enemy>();
			this.player = new Player(250, 158);
			setIn(new InputHandler(this));
			Gdx.input.setInputProcessor(getIn());
	}


	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	public void setRenderer(LevelRenderer lr) {
		this.lr = lr;
	}

	public Player getPlayer(){
		return player;
	}

	public void update() {
		player.update();
	}

//	public Music getLeveltheme() {
//		return leveltheme;
//	}

	public InputHandler getIn() {
		return in;
	}

	public void setIn(InputHandler in) {
		this.in = in;
	}
	
	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public void generateBackground() {

	}

	public void drawBackground(SpriteBatch batch) {

	}

	public void parallax() {

	}

	public void handleCheckPoints(LevelRenderer lr) {

	}

	public void drawHud(SpriteBatch batch, String color, LevelRenderer lr) {
		if (player.getHealth() > 100) {
			player.setHealth(100);
		} else if (player.getHealth() < 0) {
			player.setHealth(0);
		}
		batch.draw(healthbar, LevelRenderer.camPos + 25, 450, player.getHealth()*2, 15);
		batch.draw(timebar, LevelRenderer.camPos + 25, 425, player.getTime()*2, 15);
		if (player.getTimeFreeze() == true) {
			batch.draw(timeFreezeOverlay, LevelRenderer.camPos, 0);
			batch.draw(timeFreezeLine, LevelRenderer.camPos + r.nextInt(960), 0);
			batch.draw(timeFreezeLine, LevelRenderer.camPos + r.nextInt(960), 0);
			batch.draw(timeFreezeLine, LevelRenderer.camPos + r.nextInt(960), 0);
			batch.draw(timeFreezeLine, LevelRenderer.camPos + r.nextInt(960), 0);
			batch.draw(timeFreezeLine, LevelRenderer.camPos + r.nextInt(960), 0);
		}

		if (color == "black") {
			font.draw(batch, levelName, LevelRenderer.camPos + 800, 470);
			font.draw(batch, "Time:     " + (int)LevelRenderer.levelTime, LevelRenderer.camPos + 800, 450);
			font.draw(batch, "Score:     " + LevelRenderer.levelScore, LevelRenderer.camPos + 800, 430);
			batch.draw(healthbaroutline, LevelRenderer.camPos + 25 - 3, 450 - 3, 206, 21);
			batch.draw(timebaroutline, LevelRenderer.camPos + 25 - 3, 425 - 3, 206, 21);
		} else if (color == "white") {
			fontWhite.draw(batch, levelName, LevelRenderer.camPos + 800, 470);
			fontWhite.draw(batch, "Time:     " + (int)LevelRenderer.levelTime, LevelRenderer.camPos + 800, 450);
			fontWhite.draw(batch, "Score:     " + LevelRenderer.levelScore, LevelRenderer.camPos + 800, 430);
			batch.draw(healthbaroutlineWhite, LevelRenderer.camPos + 25 - 3, 450 - 3, 206, 21);
			batch.draw(timebaroutlineWhite, LevelRenderer.camPos + 25 - 3, 425 - 3, 206, 21);

		} else { }

		if (player.getTimeFreeze() == false) {
			LevelRenderer.levelTime = (float)LevelRenderer.levelTime + LevelRenderer.delta;
		}
	}
	
	public Music getLeveltheme() {
		return leveltheme;
	}
	

	public void dispose() {
		this.timeFreezeOverlay.dispose();
		this.level_complete.dispose();
		this.timebaroutline.dispose();
		this.timebar.dispose();
		this.healthbaroutline.dispose();
		this.healthbar.dispose();
		
		
		timeFreezeOverlay.dispose();
		level_complete.dispose();
		timebaroutline.dispose();
		timebar.dispose();
		healthbaroutline.dispose();
		healthbar.dispose();
		player.dispose();
		enemies.clear();
	}

}









/*
package com.rip.levels;

import renderers.LevelRender;
//import com.badlogic.gdx.Gdx;
import com.rip.RipGame;
import com.rip.objects.Player;

public abstract class Level {

	
	RipGame game;
	Player player;
	LevelRender lr;
		
	public Level(RipGame game, Player player) {
			this.game = game;
			this.player = player;
//			Gdx.input.setInputProcessor(new InputHandler(this));
	}
		
	public Player getPlayer(){
		return player;
	}
	
	public void update() {
		player.update();
	}

	public void setRenderer(LevelRender lr) {
		this.lr = lr;
	}
	
	public LevelRender getRenderer() {
		return lr;
	}
	
	public void dispose() {
		
	}	
	
}
*/

