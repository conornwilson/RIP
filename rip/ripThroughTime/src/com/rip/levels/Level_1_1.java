package com.rip.levels;

import java.util.Random;

import renderers.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.rip.RipGame;
import com.rip.objects.Ape;
import com.rip.objects.BackgroundObject;
import com.rip.objects.Player;
import com.rip.objects.Raptor;


public class Level_1_1 extends Level {

	// public RipGame game;
	// Player player;
	// LevelRenderer lr;
	// ArrayList<Enemy> enemies;
	// private InputHandler in;

	// Random r = new Random();

	Timer t = new Timer();



	BackgroundObject sk;
	Array<BackgroundObject> grounds = new Array<BackgroundObject>(5);
	Array<BackgroundObject> trees = new Array<BackgroundObject>(100);
	Array<BackgroundObject> bushes = new Array<BackgroundObject>(100);
	Array<BackgroundObject> volcanos = new Array<BackgroundObject>(100);
	Array<BackgroundObject> clouds = new Array<BackgroundObject>(100);
	Array<BackgroundObject> debris = new Array<BackgroundObject>(100);
	Array<BackgroundObject> fog = new Array<BackgroundObject>(100);

	boolean checkPoint1, checkPoint2, checkPoint3, levelComplete = false;
	boolean cp2Wave1, cp2Wave2 = false;
	boolean cp3Wave1, cp3Wave2 = false;
	float spawnChance = 0;
	boolean spawnToggle, randomSpawnToggle = false;
	//public boolean end = false;

	public Level_1_1(RipGame game) {
		super(game);
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

		levelLength = 10000;
		levelName = "Level 1 1";
		levelHudColor = "black";
	}




	public int generateXY(int min, int max) {
		//Min + (int)(Math.random() * ((Max - Min) + 1))
		return min + (int)(Math.random() * ((max - min) + 1));
	}

	@Override
	public void handleCheckPoints(LevelRenderer lr) {
		if (getEnemies().isEmpty() && LevelRenderer.move == false && LevelRenderer.camPos < 11500) {
			LevelRenderer.move = true;
		}

		if (getEnemies().isEmpty()) {
			randomSpawnToggle = true;
		} else {
			randomSpawnToggle = false;
		}

		if (LevelRenderer.camPos >= 1000 && !checkPoint1) {
			LevelRenderer.move = false;
			spawnApe(1);
			checkPoint1 = true;
		} else if (LevelRenderer.camPos >= 4000 && !checkPoint2 && !cp2Wave1) {
			LevelRenderer.move = false;
			spawnApe(3);
			cp2Wave1 = true;
		} else if (getEnemies().size() <= 1 && !cp2Wave2 && cp2Wave1) {
			LevelRenderer.move = false;
			spawnApe(2);
			cp2Wave2 = true;
			checkPoint2 = true;
		} else if (LevelRenderer.camPos >= 8000 && !checkPoint3 && !cp3Wave1) {
			LevelRenderer.move = false;
			spawnApe(4);
			cp3Wave1 = true;
		} else if (getEnemies().size() <= 1 && !cp3Wave2 && cp3Wave1) {
			LevelRenderer.move = false;
			spawnApe(3);
			cp3Wave2 = true;
			checkPoint3 = true;
			levelComplete = true;
		} else if (checkPoint1 && !levelComplete && randomSpawnToggle) {
			if (player.getHealth() > player.getTotalHealth() * .75) {
				randomSpawn(5, 3);
			} else if (player.getHealth() > player.getTotalHealth() * .25) {
				randomSpawn(12, 5);
			} else {
				randomSpawn(20, 7);
			}
		}

		if (levelComplete && this.getEnemies().size() == 0) {
			//end level.
			this.end = true;
			LevelRenderer.move = false;
			Gdx.app.log(RipGame.LOG, "End level 1_1");
		}
	}

	public void randomSpawn(int freq, int prob) {
		if (spawnChance >= freq && spawnToggle) {
			spawnChance = 0;
			if (r.nextInt(prob) == 1) {
				spawnApe(2);
			} else {
				spawnApe(1);
			}
			spawnToggle = false;
		} else {
			spawnChance += r.nextFloat() * LevelRenderer.delta;
		}

		if (spawnChance >= freq && !spawnToggle) {
			spawnToggle = true;
		}
	}

	@Override
	public void generateBackground() {
		Pixmap ground1 = new Pixmap(Gdx.files.internal("level1_1/ground1.png"));
		Pixmap ground2 = new Pixmap(Gdx.files.internal("level1_1/ground2.png"));
		Pixmap ground3 = new Pixmap(Gdx.files.internal("level1_1/ground3.png"));
		Pixmap ground4 = new Pixmap(Gdx.files.internal("level1_1/ground4.png"));
		Array<Pixmap> groundPix = new Array<Pixmap>();
		groundPix.add(ground1);
		groundPix.add(ground2);
		groundPix.add(ground3);
		groundPix.add(ground4);
		int startX = -200;
		int startY = 0;
		while (startX < levelLength) {
			BackgroundObject gr = new BackgroundObject(groundPix, startX, startY);
			gr.setTexture();
			grounds.add(gr);
			startX = startX + ground1.getWidth();
		}


		//sky -- doesn't ever move.
		Pixmap s = new Pixmap(Gdx.files.internal("level1_1/sky.png"));
		sk = new BackgroundObject(s,0,0);


		//random tree objects.
		Pixmap tree1 = new Pixmap(Gdx.files.internal("level1_1/tree1.png"));
		Pixmap tree2 = new Pixmap(Gdx.files.internal("level1_1/tree2.png"));
		Pixmap tree3 = new Pixmap(Gdx.files.internal("level1_1/tree3.png"));
		Pixmap tree4 = new Pixmap(Gdx.files.internal("level1_1/tree4.png"));
		Pixmap tree5 = new Pixmap(Gdx.files.internal("level1_1/tree5.png"));
		Pixmap tree6 = new Pixmap(Gdx.files.internal("level1_1/tree6.png"));
		Pixmap tree7 = new Pixmap(Gdx.files.internal("level1_1/tree7.png"));
		Pixmap tree8 = new Pixmap(Gdx.files.internal("level1_1/tree8.png"));
		Pixmap tree9 = new Pixmap(Gdx.files.internal("level1_1/tree9.png"));
		Pixmap tree10 = new Pixmap(Gdx.files.internal("level1_1/tree10.png"));
		Array<Pixmap> treesPix = new Array<Pixmap>();
		treesPix.add(tree1);
		treesPix.add(tree2);
		treesPix.add(tree3);
		treesPix.add(tree4);
		treesPix.add(tree5);
		treesPix.add(tree6);
		treesPix.add(tree7);
		treesPix.add(tree8);
		treesPix.add(tree9);
		treesPix.add(tree10);
		int ranPos = -100;
		while (ranPos < levelLength * (1-(.5/3))) {
			int randomX = r.nextInt(150-100) + 100;
			int randomY = 225;
			ranPos = ranPos + randomX;
			BackgroundObject t = new BackgroundObject(treesPix, ranPos, randomY);
			t.setTexture();
			trees.add(t);
		}

		//random bush objects.
		Pixmap bush1 = new Pixmap(Gdx.files.internal("level1_1/bush1.png"));
		Pixmap bush2 = new Pixmap(Gdx.files.internal("level1_1/bush2.png"));
		Pixmap bush3 = new Pixmap(Gdx.files.internal("level1_1/bush3.png"));
		Pixmap bush4 = new Pixmap(Gdx.files.internal("level1_1/bush4.png"));
		Array<Pixmap> bushPix = new Array<Pixmap>();
		bushPix.add(bush1);
		bushPix.add(bush2);
		bushPix.add(bush3);
		bushPix.add(bush4);
		ranPos = -30;
		while (ranPos < levelLength) {
			int randomX = r.nextInt(75-30) + 30;
			int randomY = r.nextInt(200-180) + 180;
			ranPos = ranPos + randomX;
			BackgroundObject b = new BackgroundObject(bushPix, ranPos, randomY);
			b.setTexture();
			bushes.add(b);
		}

		//random volcano objects
		Pixmap volcano1 = new Pixmap(Gdx.files.internal("level1_1/volcano.png"));
		Pixmap volcano2 = new Pixmap(Gdx.files.internal("level1_1/volcanosmall.png"));
		Array<Pixmap> volcanoPix = new Array<Pixmap>();
		volcanoPix.add(volcano1);
		volcanoPix.add(volcano2);
		ranPos = -300;
		while (ranPos < levelLength * (1-(1.5/3))) {
			int randomX = r.nextInt(500-300) + 300;
			int randomY = 230;
			ranPos = ranPos + randomX;
			BackgroundObject v = new BackgroundObject(volcanoPix, ranPos, randomY);
			v.setTexture();
			volcanos.add(v);
		}

		//random cloud objects
		Pixmap cloud1 = new Pixmap(Gdx.files.internal("level1_1/cloud1.png"));
		Pixmap cloud2 = new Pixmap(Gdx.files.internal("level1_1/cloud2.png"));
		Pixmap cloud3 = new Pixmap(Gdx.files.internal("level1_1/cloud3.png"));
		Pixmap cloud4 = new Pixmap(Gdx.files.internal("level1_1/cloud4.png"));
		Array<Pixmap> cloudPix = new Array<Pixmap>();
		cloudPix.add(cloud1);
		cloudPix.add(cloud2);
		cloudPix.add(cloud3);
		cloudPix.add(cloud4);
		ranPos = -150;
		while (ranPos < levelLength * (1-(2.5/3))) {
			int randomX = r.nextInt(380-150) + 150;
			int randomY = r.nextInt(460-300) + 300;
			ranPos = ranPos + randomX;
			BackgroundObject c = new BackgroundObject(cloudPix, ranPos, randomY);
			c.setTexture();
			clouds.add(c);
		}

		//random debris objects
		Pixmap debris1 = new Pixmap(Gdx.files.internal("data/smallgrass.png"));
		Pixmap debris2 = new Pixmap(Gdx.files.internal("data/smallrock1.png"));
		Pixmap debris3 = new Pixmap(Gdx.files.internal("data/smallrock2.png"));
		Array<Pixmap> debrisPix = new Array<Pixmap>();
		debrisPix.add(debris1);
		debrisPix.add(debris2);
		debrisPix.add(debris3);
		ranPos = 0;
		while (ranPos < levelLength) {
			int randomX = r.nextInt(100-50) + 50;
			int randomY = r.nextInt(178);
			ranPos = ranPos + randomX;
			BackgroundObject d = new BackgroundObject(debrisPix, ranPos, randomY);
			d.setTexture();
			debris.add(d);
		}

		//fog objects
		Pixmap fog1 = new Pixmap(Gdx.files.internal("level1_1/fog1.png"));
		Pixmap fog2 = new Pixmap(Gdx.files.internal("level1_1/fog2.png"));
		Pixmap fog3 = new Pixmap(Gdx.files.internal("level1_1/fog3.png"));
		Pixmap fog4 = new Pixmap(Gdx.files.internal("level1_1/fog4.png"));
		Array<Pixmap> fogPix = new Array<Pixmap>();
		fogPix.add(fog1);
		fogPix.add(fog2);
		fogPix.add(fog3);
		fogPix.add(fog4);
		ranPos = -500;
		while (ranPos < levelLength) {
			int randomX = r.nextInt(800 - 550) + 550;
			int randomY = r.nextInt(200 - 150) + 180;
			ranPos = ranPos + randomX;
			BackgroundObject f = new BackgroundObject(fogPix, ranPos, randomY);
			f.setTexture();
			fog.add(f);
		}
	}

	@Override
	public void drawBackground(SpriteBatch batch) {
		batch.draw(sk.getTexture(), sk.getX(), sk.getY());

		//draw random clouds only in visible screen.
		for (BackgroundObject i : clouds) {
			if (i.getX() > LevelRenderer.camPos - 250 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw random volcanos only in visible screen.
		for (BackgroundObject i : volcanos) {
			if (i.getX() > LevelRenderer.camPos - 250 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}

		}

		//draw random trees only in visible screen.
		for (BackgroundObject i : trees) {
			if (i.getX() > LevelRenderer.camPos - 175 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw ground
		for (BackgroundObject i : grounds) {
			if (i.getX() > LevelRenderer.camPos - i.getTexture().getWidth() && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw random bushes only in visible screen.
		for (BackgroundObject i : bushes) {
			if (i.getX() > LevelRenderer.camPos - 100 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		for (BackgroundObject i : fog) {
			if (i.getX() > LevelRenderer.camPos - 750 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw random debris objects only in visible screen.
		for (BackgroundObject i : debris) {
			if (i.getX() > LevelRenderer.camPos - 20 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}	
		}

	}

	@Override
	public void parallax() {
		sk.setX(sk.getX() + 3f);

		for (BackgroundObject i : clouds) {
			i.setX(i.getX() + 2.5f);
		}

		for (BackgroundObject i : volcanos) {
			i.setX(i.getX() + 1.5f);
		}

		for (BackgroundObject i : trees) {
			i.setX(i.getX() + 0.5f);
		}

		for (BackgroundObject i : fog) {
			i.setX(i.getX() + 0.25f);
		}

	}



	public void dispose() {
		/*
	leveltheme.dispose();
	this.g.dispose();
	this.s.dispose();
	this.tree1.dispose();
	this.tree2.dispose();
	this.bush1.dispose();
	this.bush2.dispose();
	this.volcano1.dispose();
	this.volcano2.dispose();
	this.cloud1.dispose();
	this.cloud2.dispose();
	this.cloud3.dispose();
	this.cloud4.dispose();
	this.debris1.dispose();
	this.debris2.dispose();
	this.debris3.dispose();
		 */


		super.dispose();

	}




}

















/*
package com.rip.levels;

import java.util.ArrayList;
import java.util.Random;

import renderers.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.rip.RipGame;
import com.rip.objects.Ape;
import com.rip.objects.BackgroundObject;
import com.rip.objects.Enemy;
import com.rip.objects.Player;
import com.rip.objects.Raptor;


public class Level_1_1 extends Level {


	Timer t = new Timer();

	

	BackgroundObject sk;
	Array<BackgroundObject> grounds = new Array<BackgroundObject>(5);
	Array<BackgroundObject> trees = new Array<BackgroundObject>(100);
	Array<BackgroundObject> bushes = new Array<BackgroundObject>(100);
	Array<BackgroundObject> volcanos = new Array<BackgroundObject>(100);
	Array<BackgroundObject> clouds = new Array<BackgroundObject>(100);
	Array<BackgroundObject> debris = new Array<BackgroundObject>(100);

	boolean checkPoint1, checkPoint2, checkPoint3, checkPoint4, levelComplete = false;
	boolean cp2Wave1, cp2Wave2 = false;
	boolean cp3Wave1, cp3Wave2 = false;
	boolean cp4Wave1, cp4Wave2 = false;
	//public boolean end = false;

	public Level_1_1(RipGame game) {
			super(game);
			this.player = new Player(250, 158);
			setIn(new InputHandler(this));
			Gdx.input.setInputProcessor(getIn());
			leveltheme = Gdx.audio.newMusic(Gdx.files.internal("data/Prehistoric Main.mp3"));
			leveltheme.setLooping(true);

			levelLength = 14000;
			levelName = "Level 1  1";
			levelHudColor = "black";
	}

	
	@Override
	public void handleCheckPoints(LevelRenderer lr) {
		if (getEnemies().isEmpty() && LevelRenderer.move == false && LevelRenderer.camPos < 11500) {
			LevelRenderer.move = true;
		}

		//CHECKPOINT 1//
		if (LevelRenderer.camPos >= 1500 && checkPoint1 == false) {
			Gdx.app.log(RipGame.LOG, "checkpoint1");
			LevelRenderer.move = false;
			checkPoint(0,1);
			checkPoint1 = true;
		}

		//CHECKPOINT 2//
		//wave 1
		if (LevelRenderer.camPos >= 4000 && checkPoint2 == false && cp2Wave1 == false) {
			Gdx.app.log(RipGame.LOG, "checkpoint2");
			LevelRenderer.move = false;
			checkPoint(0,3);
			cp2Wave1 = true;
		}

		//wave2
		if (getEnemies().isEmpty() && cp2Wave2 == false && cp2Wave1 == true) {
			LevelRenderer.move = false;
			checkPoint(0,2);
			cp2Wave2 = true;
			checkPoint2 = true;
		}
		

		//CHECKPOINT 3//
		//wave 1
		if (LevelRenderer.camPos >= 7000 && checkPoint3 == false && cp3Wave1 == false) {
			LevelRenderer.move = false;
			checkPoint(0,2);
			cp3Wave1 = true;
		}

		//wave 2
		if (getEnemies().isEmpty() && cp3Wave2 == false && cp3Wave1 == true) {
			LevelRenderer.move = false;
			checkPoint(1,0);
			cp3Wave2 = true;
			checkPoint3 = true;
		}

		//CHECKPOINT 4 -- FINAL//
		//wave1
		if (LevelRenderer.camPos >= 11000 && checkPoint4 == false && cp4Wave1 == false) {
			LevelRenderer.move = false;
			checkPoint(0,6);
			cp4Wave1 = true;
		}

		//wave2
		if (getEnemies().isEmpty() && cp4Wave2 == false && cp4Wave1 == true) {
			LevelRenderer.move = false;
			checkPoint(3,0);
			cp4Wave2 = true;
			checkPoint4 = true;
		}

		//END LEVEL//

		if (checkPoint4 == true && LevelRenderer.camPos >= 11500) {
			LevelRenderer.move = false;
			end = true;
			Gdx.app.log(RipGame.LOG, "End Level 1-1");

		}
	}

	@Override
	public void generateBackground() {
		Pixmap g = new Pixmap(Gdx.files.internal("data/ground.png"));
		int startX = -20;
		int startY = 0;
		while (startX < levelLength) {
			BackgroundObject gr = new BackgroundObject(g, startX, startY);
			grounds.add(gr);
			startX = startX + g.getWidth();
		}


		//sky -- doesn't ever move. 
		Pixmap s = new Pixmap(Gdx.files.internal("data/sky.png"));
		sk = new BackgroundObject(s,0,0);


		//random tree objects.
		Pixmap tree1 = new Pixmap(Gdx.files.internal("data/tree.png"));
		Pixmap tree2 = new Pixmap(Gdx.files.internal("data/tree2.png"));
		Array<Pixmap> treesPix = new Array<Pixmap>();
		treesPix.add(tree1);
		treesPix.add(tree2);
		int ranPos = -100;
		while (ranPos < levelLength * (1-(.5/3))) {
			int randomX = r.nextInt(150-100) + 100;
			int randomY = r.nextInt(235-210) + 210;
			ranPos = ranPos + randomX;
			BackgroundObject t = new BackgroundObject(treesPix, ranPos, randomY);
			t.setTexture();
			trees.add(t);
		}

		    //random bush objects.
			Pixmap bush1 = new Pixmap(Gdx.files.internal("data/bush.png"));
			Pixmap bush2 = new Pixmap(Gdx.files.internal("data/bush2.png"));
			Array<Pixmap> bushPix = new Array<Pixmap>();
			bushPix.add(bush1);
			bushPix.add(bush2);
			ranPos = -30;
			while (ranPos < levelLength) {
				int randomX = r.nextInt(75-30) + 30;
				int randomY = r.nextInt(200-180) + 180;
				ranPos = ranPos + randomX;
				BackgroundObject b = new BackgroundObject(bushPix, ranPos, randomY);
				b.setTexture();
				bushes.add(b);
			}

			//random volcano objects
			Pixmap volcano1 = new Pixmap(Gdx.files.internal("data/volcano.png"));
			Pixmap volcano2 = new Pixmap(Gdx.files.internal("data/volcanosmall.png"));
			Array<Pixmap> volcanoPix = new Array<Pixmap>();
			volcanoPix.add(volcano1);
			volcanoPix.add(volcano2);
			ranPos = -300;
			while (ranPos < levelLength * (1-(1.5/3))) {
				int randomX = r.nextInt(500-300) + 300;
				int randomY = r.nextInt(260-230) + 230;
				ranPos = ranPos + randomX;
				BackgroundObject v = new BackgroundObject(volcanoPix, ranPos, randomY);
				v.setTexture();
				volcanos.add(v);
			}

			//random cloud objects
			Pixmap cloud1 = new Pixmap(Gdx.files.internal("data/cloud1.png"));
			Pixmap cloud2 = new Pixmap(Gdx.files.internal("data/cloud2.png"));
			Pixmap cloud3 = new Pixmap(Gdx.files.internal("data/cloud3.png"));
			Pixmap cloud4 = new Pixmap(Gdx.files.internal("data/cloud4.png"));
			Array<Pixmap> cloudPix = new Array<Pixmap>();
			cloudPix.add(cloud1);
			cloudPix.add(cloud2);
			cloudPix.add(cloud3);
			cloudPix.add(cloud4);
			ranPos = -150;
			while (ranPos < levelLength * (1-(2.5/3))) {
				int randomX = r.nextInt(380-150) + 150;
				int randomY = r.nextInt(460-300) + 300;
				ranPos = ranPos + randomX;
				BackgroundObject c = new BackgroundObject(cloudPix, ranPos, randomY);
				c.setTexture();
				clouds.add(c);
			}

			//random debris objects
			Pixmap debris1 = new Pixmap(Gdx.files.internal("data/smallgrass.png"));
			Pixmap debris2 = new Pixmap(Gdx.files.internal("data/smallrock1.png"));
			Pixmap debris3 = new Pixmap(Gdx.files.internal("data/smallrock2.png"));
			Array<Pixmap> debrisPix = new Array<Pixmap>();
			debrisPix.add(debris1);
			debrisPix.add(debris2);
			debrisPix.add(debris3);
			ranPos = 0;
			while (ranPos < levelLength) {
				int randomX = r.nextInt(100-50) + 50;
				int randomY = r.nextInt(178);
				ranPos = ranPos + randomX;
				BackgroundObject d = new BackgroundObject(debrisPix, ranPos, randomY);
				d.setTexture();
				debris.add(d);
			}
	}

	@Override
	public void drawBackground(SpriteBatch batch) {
		batch.draw(sk.getTexture(), sk.getX(), sk.getY());

		//draw random clouds only in visible screen.
		for (BackgroundObject i : clouds) {
			if (i.getX() > LevelRenderer.camPos - 250 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw random volcanos only in visible screen.
		for (BackgroundObject i : volcanos) {
			if (i.getX() > LevelRenderer.camPos - 250 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}

		}

		//draw random trees only in visible screen.
		for (BackgroundObject i : trees) {
			if (i.getX() > LevelRenderer.camPos - 130 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw ground
		for (BackgroundObject i : grounds) {
			if (i.getX() > LevelRenderer.camPos - i.getTexture().getWidth() && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw random bushes only in visible screen.
		for (BackgroundObject i : bushes) {
			if (i.getX() > LevelRenderer.camPos - 100 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw random debris objects only in visible screen.
		for (BackgroundObject i : debris) {
			if (i.getX() > LevelRenderer.camPos - 20 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}	
		}

	}

	@Override
	public void parallax() {
		sk.setX(sk.getX() + 3f);

		for (BackgroundObject i : clouds) {
			i.setX(i.getX() + 2.5f);
		}

		for (BackgroundObject i : volcanos) {
			i.setX(i.getX() + 1.5f);
		}

		for (BackgroundObject i : trees) {
			i.setX(i.getX() + 0.5f);
		}

	}



	public void dispose() {
		
		
		super.dispose();
		
	}

	
	
	
}

*/


