package com.rip.levels;

import java.util.ArrayList;
import java.util.Random;

import renderers.LevelRender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.rip.RipGame;
import com.rip.objects.Ape;
import com.rip.objects.Enemy;
import com.rip.objects.Player;
import com.rip.objects.Raptor;


public class Level_1_1 {
	
	public RipGame game;
	Player player;
	LevelRender lr;
	ArrayList<Enemy> enemies;
	private InputHandler in;
		
	Music leveltheme;
	
	public Level_1_1(RipGame game) {
			this.game = game;
			enemies = new ArrayList<Enemy>();
			this.player = new Player(250, 158);
			setIn(new InputHandler(this));
			Gdx.input.setInputProcessor(getIn());
			/*
			Raptor raptor_one = new Raptor(800, 50);
			Raptor raptor_two = new Raptor(500, 150);
			enemies.add(raptor_one);
			enemies.add(raptor_two);
			*/
			leveltheme = Gdx.audio.newMusic(Gdx.files.internal("data/Prehistoric Main.mp3"));
			leveltheme.setLooping(true);
	}
	
	public void checkPoint(int numOfEnemiesRap, int numOfEnemiesApe) {
		Random r = new Random();
		int rightside;
		int leftside;
		boolean lr;
		int x;
		int y;
		int xmin_right, xmax_right, xmin_left, xmax_left;
		int ymin, ymax;
		
		//avoid initiating with overlap
		ymax = LevelRender.Y_LIMIT;
		ymin = LevelRender.Y_LIMIT - 87;
		
		xmin_right = LevelRender.camPos + RipGame.WIDTH + 50;
		xmax_right = xmin_right + 224;
		
		xmax_left = LevelRender.camPos - 50;
		xmin_left = xmax_left - 224;
		
		for (int i = 0; i < numOfEnemiesRap; i++) {
			lr = r.nextBoolean();
			if (lr) {
				rightside = LevelRender.camPos + RipGame.WIDTH;
				x = generateXY(xmin_right, xmax_right);
				y = generateXY(ymin, ymax);
				
				ymin -= 87;
				ymax -= 87;
				
				if (ymax <= LevelRender.Y_LIMIT - 300) {
					ymax = LevelRender.Y_LIMIT;
					ymin = LevelRender.Y_LIMIT - 87;
				}
				
				xmax_right += 224;
				xmin_right += 224;
				
				enemies.add(new Raptor(x, y));
			} else {
				leftside = LevelRender.camPos;
				x = generateXY(xmin_right, xmax_right);
				y = generateXY(ymin, ymax);
				
				ymin -= 87;
				ymax -= 87;
				
				if (ymax <= LevelRender.Y_LIMIT - 300) {
					ymax = LevelRender.Y_LIMIT;
					ymin = LevelRender.Y_LIMIT - 87;
				}
				
				xmax_left -= 224;
				xmin_left -= 224;
				enemies.add(new Raptor(x, y));
			}

		}
		
		

		for (int i = 0; i < numOfEnemiesApe; i++) {
			lr = r.nextBoolean();
			if (lr) {
				rightside = LevelRender.camPos + RipGame.WIDTH;
				enemies.add(new Ape(r.nextInt((rightside + RipGame.WIDTH) - (rightside + 50)) + (rightside + 50), r.nextInt(LevelRender.Y_LIMIT)));
			} else {
				leftside = LevelRender.camPos;
				enemies.add(new Ape(r.nextInt((leftside - 50) - (leftside - RipGame.WIDTH)) + (leftside - RipGame.WIDTH), r.nextInt(LevelRender.Y_LIMIT)));
			}

		}
	}
	
	
	
	public int generateXY(int min, int max) {
		//Min + (int)(Math.random() * ((Max - Min) + 1))
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
		
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}


	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
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


	public Music getLeveltheme() {
		return leveltheme;
	}

	public InputHandler getIn() {
		return in;
	}

	public void setIn(InputHandler in) {
		this.in = in;
	}
	
	
	public void dispose() {
		/*
		player.dispose();
		enemies.clear();
		leveltheme.dispose();
		*/
		
	}
	
	
}

