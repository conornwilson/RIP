package com.rip.levels;

import java.util.ArrayList;
import java.util.Random;

import renderers.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.rip.RipGame;
import com.rip.objects.Ape;
import com.rip.objects.Enemy;
import com.rip.objects.GoldenRaptor;
import com.rip.objects.Player;
import com.rip.objects.Raptor;
import com.rip.objects.RedRaptor;
import com.rip.objects.SuperApe;
import com.rip.screens.MainMenu;

public abstract class Level {


	public RipGame game;
	Player player;
	LevelRenderer lr;
	ArrayList<Enemy> enemies;
	private InputHandler in;
	public int levelLength;
	public String levelName;
	public String levelHudColor;
	//public Stage stage;
	
	public Music leveltheme;
	public Music additional_theme1;
	public Music additional_theme2;
	public Music beatlevel;
	
	public boolean end = false;

	Random r = new Random();
	
	int rightside;
	int leftside;
	boolean leftright;
	int x;
	int y;
	int xmin_right, xmax_right, xmin_left, xmax_left;
	int ymin, ymax;
	
	GoldenRaptor goldenraptor;
	
	
	//////////PAUSE ITEMS//////////
	/*
	BitmapFont black;
	BitmapFont white;
	TextureAtlas atlas;
	Skin skin;
	TextButton resumeButton;
	TextButton mainMenuButton;
	TextButton quitButton;
	*/

	//////////HUD OBJECTS//////////
	
	public Texture timeFreezeOverlay = new Texture(Gdx.files.internal("data/timeFreezeOverlay.png"));
	public Texture level_complete = new Texture(Gdx.files.internal("data/end.png"));
	public Texture deadOverlay = new Texture(Gdx.files.internal("data/dead.png"));
	public Texture pauseOverlay = new Texture(Gdx.files.internal("data/pause.png"));
	Texture timebaroutline = new Texture(Gdx.files.internal("data/timebaroutline.png"));
	Texture timebaroutlineWhite = new Texture(Gdx.files.internal("data/timebaroutlineWhite.png"));
	Texture timebar = new Texture(Gdx.files.internal("data/timebar.png"));
	Texture healthbaroutline = new Texture(Gdx.files.internal("data/healthbaroutline.png"));
	Texture healthbaroutlineWhite = new Texture(Gdx.files.internal("data/healthbaroutlineWhite.png"));
	Texture healthbar = new Texture(Gdx.files.internal("data/healthbar.png"));
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
			beatlevel = Gdx.audio.newMusic(Gdx.files.internal("data/Beat Level.mp3"));
			beatlevel.setLooping(false);
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
			//font.draw(batch, levelName, LevelRenderer.camPos + 800, 470);
			//font.draw(batch, "Time:     " + (int)LevelRenderer.levelTime, LevelRenderer.camPos + 800, 450);
			//font.draw(batch, "Score:     " + LevelRenderer.levelScore, LevelRenderer.camPos + 800, 430);
			batch.draw(healthbaroutline, LevelRenderer.camPos + 25 - 3, 450 - 3, 206, 21);
			batch.draw(timebaroutline, LevelRenderer.camPos + 25 - 3, 425 - 3, 206, 21);
		} else if (color == "white") {
			//fontWhite.draw(batch, levelName, LevelRenderer.camPos + 800, 470);
			//fontWhite.draw(batch, "Time:     " + (int)LevelRenderer.levelTime, LevelRenderer.camPos + 800, 450);
			//fontWhite.draw(batch, "Score:     " + LevelRenderer.levelScore, LevelRenderer.camPos + 800, 430);
			batch.draw(healthbaroutlineWhite, LevelRenderer.camPos + 25 - 3, 450 - 3, 206, 21);
			batch.draw(timebaroutlineWhite, LevelRenderer.camPos + 25 - 3, 425 - 3, 206, 21);

		} else { }

		if (player.getTimeFreeze() == false) {
			LevelRenderer.levelTime = (float)LevelRenderer.levelTime + LevelRenderer.delta;
		}
	}
	
	/////////Enemy Spawning Functions//////////
	
	
	public void newSpawnPoint() {
		this.r = new Random();
		this.ymax = LevelRenderer.Y_LIMIT;
		this.ymin = LevelRenderer.Y_LIMIT - 87;
		
		this.xmin_right = LevelRenderer.camPos + RipGame.WIDTH + 200;
		this.xmax_right = xmin_right + 224;
		
		this.xmax_left = LevelRenderer.camPos - 100;
		this.xmin_left = xmax_left - 224;
	}
	

	public void spawnApe(int num) {
		/*
		Random r = new Random();
		int rightside;
		int leftside;
		boolean leftright;
		int x;
		int y;
		int xmin_right, xmax_right, xmin_left, xmax_left;
		int ymin, ymax;
		
		//avoid initiating with overlap
		ymax = LevelRenderer.Y_LIMIT;
		ymin = LevelRenderer.Y_LIMIT - 87;
		
		xmin_right = LevelRenderer.camPos + RipGame.WIDTH + 200;
		xmax_right = xmin_right + 224;
		
		xmax_left = LevelRenderer.camPos - 100;
		xmin_left = xmax_left - 224;
		*/
		
		for (int i = 0; i < num; i++) {
			if ((float) Math.random() <= .75) {
				Gdx.app.log(RipGame.LOG, "right******************");
				rightside = LevelRenderer.camPos + RipGame.WIDTH;
				x = generateXY(xmin_right, xmax_right);
				y = generateXY(ymin, ymax);
				
				ymin -= 87;
				ymax -= 88;
				
				if (ymax <= LevelRenderer.Y_LIMIT - 300) {
					ymax = LevelRenderer.Y_LIMIT;
					ymin = LevelRenderer.Y_LIMIT - 87;
					
					xmax_right += 224;
					xmin_right += 224;
				}
				
				
				
				enemies.add(new Ape(x, y));
			} else {
				Gdx.app.log(RipGame.LOG, "left******************");
				leftside = LevelRenderer.camPos;
				x = generateXY(xmin_left, xmax_left);
				y = generateXY(ymin, ymax);
				
				ymin -= 87;
				ymax -= 88;
				
				if (ymax <= LevelRenderer.Y_LIMIT - 300) {
					ymax = LevelRenderer.Y_LIMIT;
					ymin = LevelRenderer.Y_LIMIT - 87;
					
					xmax_left -= 224;
					xmin_left -= 224;
				}
				
				
				enemies.add(new Ape(x, y));
				
			}

		}

	}

	public void spawnSuperApe(int num) {
		for (int i = 0; i < num; i++) {
			if ((float) Math.random() <= .75) {
				rightside = LevelRenderer.camPos + RipGame.WIDTH;
				x = generateXY(xmin_right, xmax_right);
				y = generateXY(ymin, ymax);
				
				ymin -= 87;
				ymax -= 88;
				
				if (ymax <= LevelRenderer.Y_LIMIT - 300) {
					ymax = LevelRenderer.Y_LIMIT;
					ymin = LevelRenderer.Y_LIMIT - 87;
					
					xmax_right += 224;
					xmin_right += 224;
				}
				
				
				
				enemies.add(new SuperApe(x, y));
			} else {
				leftside = LevelRenderer.camPos;
				x = generateXY(xmin_left, xmax_left);
				y = generateXY(ymin, ymax);
				
				ymin -= 87;
				ymax -= 88;
				
				if (ymax <= LevelRenderer.Y_LIMIT - 300) {
					ymax = LevelRenderer.Y_LIMIT;
					ymin = LevelRenderer.Y_LIMIT - 87;
					
					xmax_left -= 224;
					xmin_left -= 224;
				}
				
				
				enemies.add(new SuperApe(x, y));
				
			}

		}

	}

	public void spawnRedRaptor(int num) {
		
		for (int i = 0; i < num; i++) {
			if ((float) Math.random() <= .75) {
				rightside = LevelRenderer.camPos + RipGame.WIDTH;
				x = generateXY(xmin_right, xmax_right);
				y = generateXY(ymin, ymax);
				
				ymin -= 87;
				ymax -= 88;
				
				if (ymax <= LevelRenderer.Y_LIMIT - 300) {
					ymax = LevelRenderer.Y_LIMIT;
					ymin = LevelRenderer.Y_LIMIT - 87;
					
					xmax_right += 224;
					xmin_right += 224;
				}
				
				
				
				enemies.add(new RedRaptor(x, y));
			} else {
				leftside = LevelRenderer.camPos;
				x = generateXY(xmin_left, xmax_left);
				y = generateXY(ymin, ymax);
				
				ymin -= 87;
				ymax -= 88;
				
				if (ymax <= LevelRenderer.Y_LIMIT - 300) {
					ymax = LevelRenderer.Y_LIMIT;
					ymin = LevelRenderer.Y_LIMIT - 87;
					
					xmax_left -= 224;
					xmin_left -= 224;
				}
				
				
				enemies.add(new RedRaptor(x, y));
				
			}

		}
		
	}

	public void spawnGoldenRaptor() {
		//fix this
		goldenraptor = new GoldenRaptor(LevelRenderer.camPos + RipGame.WIDTH + 300, 140, this);
		LevelRenderer.enemy_list.add(goldenraptor);
	}

	public void spawnRaptor(int num) {
		
		for (int i = 0; i < num; i++) {
			if ((float) Math.random() <= .75) {
				rightside = LevelRenderer.camPos + RipGame.WIDTH;
				x = generateXY(xmin_right, xmax_right);
				y = generateXY(ymin, ymax);
				
				ymin -= 87;
				ymax -= 88;
				
				if (ymax <= LevelRenderer.Y_LIMIT - 300) {
					ymax = LevelRenderer.Y_LIMIT;
					ymin = LevelRenderer.Y_LIMIT - 87;
					
					xmax_right += 224;
					xmin_right += 224;
				}
				
				enemies.add(new Raptor(x, y));
			} else {
				leftside = LevelRenderer.camPos;
				x = generateXY(xmin_left, xmax_left);
				y = generateXY(ymin, ymax);
				
				ymin -= 87;
				ymax -= 88;
				
				if (ymax <= LevelRenderer.Y_LIMIT - 300) {
					ymax = LevelRenderer.Y_LIMIT;
					ymin = LevelRenderer.Y_LIMIT - 87;
					
					xmax_left -= 224;
					xmin_left -= 224;
				}
				
				
				enemies.add(new Raptor(x, y));
				
			}

		}
	
	}
/*
	public void createPauseMenu() {
		stage = new Stage(960, 480, true);
		
		atlas = new TextureAtlas("data/button.pack"); //need to create our own button graphic!
		skin = new Skin();
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("data/arcadeFontWhite32.fnt"),false);
		black = new BitmapFont(Gdx.files.internal("data/arcadeFontBlack32.fnt"),false);
		
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;
		
		resumeButton = new TextButton("Resume", style);
		resumeButton.setWidth(300);
		resumeButton.setHeight(75);
		resumeButton.setX(Gdx.graphics.getWidth() / 2 - 150);
		resumeButton.setY(Gdx.graphics.getHeight() /2 - resumeButton.getHeight() / 2 + 150);
		
		mainMenuButton = new TextButton("Main Menu", style);
		mainMenuButton.setWidth(300);
		mainMenuButton.setHeight(75);
		mainMenuButton.setX(Gdx.graphics.getWidth() / 2 - 150);
		mainMenuButton.setY(Gdx.graphics.getHeight() /2 - resumeButton.getHeight() / 2 + 50);
		
		quitButton = new TextButton("quit", style);
		quitButton.setWidth(300);
		quitButton.setHeight(75);
		quitButton.setX(Gdx.graphics.getWidth() / 2 - 150);
		quitButton.setY(Gdx.graphics.getHeight() /2 - resumeButton.getHeight() / 2 - 50);
		
		resumeButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			return true;
		}
		
		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
			LevelRenderer.pause = false;
			Gdx.input.setInputProcessor(getIn());
			}
		});
		
		mainMenuButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			return true;
			}
		
		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
			lr.dispose();
			game.setScreen(new MainMenu(game));
			}
		});
		
		quitButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				game.dispose();
			}
		});
	}

	public void checkPause() {
		if (LevelRenderer.pause) {
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		stage.addActor(resumeButton);
		stage.addActor(mainMenuButton);
		stage.addActor(quitButton);
		stage.draw();
		}
	}
	*/
	
	public int generateXY(int min, int max) {
		//Min + (int)(Math.random() * ((Max - Min) + 1))
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	public Music getLeveltheme() {
		return leveltheme;
	}
	

	public void dispose() {
		Gdx.app.log(RipGame.LOG, "Level Dispose");
		player.dispose();
		//lr.dispose();
		this.enemies.clear();
		this.timeFreezeOverlay.dispose();
		this.level_complete.dispose();
		this.deadOverlay.dispose();
		this.pauseOverlay.dispose();
		this.level_complete.dispose();
		this.timebaroutline.dispose();
		this.timebar.dispose();
		this.healthbaroutline.dispose();
		this.healthbar.dispose();
		
		
		this.font.dispose();
		this.fontBig.dispose();
		this.fontBigWhite.dispose();
		this.fontWhite.dispose();
		//this.atlas.dispose();
		//this.stage.dispose();
		
		timeFreezeOverlay.dispose();
		level_complete.dispose();
		timebaroutline.dispose();
		timebar.dispose();
		healthbaroutline.dispose();
		healthbar.dispose();
		enemies.clear();
		if (this.leveltheme != null) {
			this.leveltheme.dispose();
		}
		
		if (this.additional_theme1 != null) {
			this.additional_theme1.dispose();
		}
		
		if (this.additional_theme2 != null) {
			this.additional_theme2.dispose();
		}
		
		if (this.beatlevel != null) {
			this.beatlevel.dispose();
		}
	}

}

