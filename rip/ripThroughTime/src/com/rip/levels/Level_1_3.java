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
import com.rip.objects.BackgroundObject;
import com.rip.objects.Enemy;
import com.rip.objects.Player;

public class Level_1_3 extends Level {

	public RipGame game;
	Player player;
	LevelRenderer lr;
	ArrayList<Enemy> enemies;
	// private InputHandler in;

	Random r = new Random();

	Timer t = new Timer();


	BackgroundObject sk;
	Array<BackgroundObject> grounds = new Array<BackgroundObject>(5);
	Array<BackgroundObject> trees = new Array<BackgroundObject>(100);
	Array<BackgroundObject> bushes = new Array<BackgroundObject>(100);
	Array<BackgroundObject> volcanos = new Array<BackgroundObject>(100);
	Array<BackgroundObject> clouds = new Array<BackgroundObject>(100);
	Array<BackgroundObject> debris = new Array<BackgroundObject>(100);
	Array<BackgroundObject> fog = new Array<BackgroundObject>(100);

	boolean checkPoint1, checkPoint2, checkPoint3, miniBoss, levelComplete = false;
	boolean cp1Wave1, cp1Wave2 = false;
	boolean cp2Wave1, cp2Wave2, cp2Wave3 = false;
	boolean cp3Wave1, cp3Wave2, cp3Wave3 = false;
	//boolean end = false;
	float spawnChance = 0;
	boolean spawnToggle = false;
	boolean randomSpawnToggle = false;
	
	boolean goldenspawned = false;
	
	Pixmap ground1, ground2, ground3, ground4, tree1, tree2, tree3, tree4,
	tree5, tree6, tree7, tree8, bush1, bush2, bush3, bush4,
	volcano1, volcano2, cloud1, cloud2, cloud3, cloud4, debris1, debris2,
	debris3, fog1, fog2, fog3, fog4, s;

	public Level_1_3(RipGame game) {
		super(game);
		this.player = new Player(250, 158);
		setIn(new InputHandler(this));
		Gdx.input.setInputProcessor(getIn());

		levelLength = 11000;
		levelName = "Level 1 1";
		levelHudColor = "black";
		
		leveltheme = Gdx.audio.newMusic(Gdx.files.internal("data/Prehistoric Main.mp3"));
		leveltheme.setLooping(true);
		
		beatlevel = Gdx.audio.newMusic(Gdx.files.internal("data/Beat Level.mp3"));
		beatlevel.setLooping(false);
	}

	@Override
	public void handleCheckPoints(LevelRenderer lr) {
		if (this.goldenspawned) {
			this.drawGRHealth();
		}
		
		if (getEnemies().isEmpty() && LevelRenderer.move == false && LevelRenderer.camPos < 11500) {
			LevelRenderer.move = true;
		}

		if (getEnemies().isEmpty()) {
			randomSpawnToggle = true;
		} else {
			randomSpawnToggle = false;
		}

		if (LevelRenderer.camPos >= 1000 && !checkPoint1 && !cp1Wave1) {
			LevelRenderer.move = false;
			newSpawnPoint();
			spawnApe(3);
			cp1Wave1 = true;
		} else if (getEnemies().size() <= 1 && cp1Wave1 && !cp1Wave2) {
			LevelRenderer.move = false;
			newSpawnPoint();
			spawnApe(4);
			cp1Wave2 = true;
			checkPoint1 = true;
		} else if (LevelRenderer.camPos >= 4000 && !checkPoint2 && !cp2Wave1) {
			LevelRenderer.move = false;
			newSpawnPoint();
			spawnRaptor(2);
			cp2Wave1 = true;
		} else if (getEnemies().size() <= 2 && cp2Wave1 && !cp2Wave2) {
			LevelRenderer.move = false;
			newSpawnPoint();
			spawnRaptor(3);
			cp2Wave2 = true;
		} else if (getEnemies().isEmpty() && cp2Wave2 && !cp2Wave3) {
			LevelRenderer.move = false;
			newSpawnPoint();
			spawnRedRaptor(1);
			cp2Wave3 = true;
			checkPoint3 = true;
		} else if (LevelRenderer.camPos >= 8000 && !checkPoint3 && !cp3Wave1) {
			LevelRenderer.move = false;
			newSpawnPoint();
			spawnApe(2);
			spawnRaptor(1);
			cp3Wave1 = true;
		} else if (getEnemies().isEmpty() && cp3Wave1 && !cp3Wave2) {
			LevelRenderer.move = false;
			newSpawnPoint();
			spawnApe(2);
			spawnRaptor(1);
			spawnRedRaptor(2);
			cp3Wave2 = true;
		} else if (getEnemies().size() <= 1 && cp3Wave2 && !cp3Wave3) {
			LevelRenderer.move = false;
			newSpawnPoint();
			spawnApe(3);
			spawnRedRaptor(2);
			cp3Wave3 = true;
			checkPoint3 = true;
		} else if (LevelRenderer.camPos >= 9000 && !miniBoss) {
			LevelRenderer.move = false;
			newSpawnPoint();
			this.goldenspawned = true;
			spawnGoldenRaptor();
			miniBoss = true;
		} else if (getEnemies().isEmpty() && miniBoss) {
			LevelRenderer.move = false;
			levelComplete = true;
		} else if (checkPoint1 && !checkPoint3 && randomSpawnToggle) {
			if (player.getHealth() > player.getTotalHealth() * .75) {
				newSpawnPoint();
				randomSpawn(5, 3);
			} else if (player.getHealth() > player.getTotalHealth() * .25) {
				newSpawnPoint();
				randomSpawn(12, 5);
			} else {
				newSpawnPoint();
				randomSpawn(20, 7);
			}
		}

		if (levelComplete && this.getEnemies().size() == 0) {
			//end level.
			if (!this.end) {
				lr.getBeatlevel().play();
				this.end = true;
				lr.getBeatlevel().play();
				Gdx.app.log(RipGame.LOG, "Level 1_3 Complete.");
			}
			LevelRenderer.move = false;
		}
	}

	public void randomSpawn(int freq, int prob) {
		if (spawnChance >= freq && spawnToggle) {
			spawnChance = 0;
			if (r.nextInt(prob) == 1) {
				float ran2 = r.nextFloat();
				if (ran2 <= .5) {
					spawnApe(2);
				} else if (ran2 > .5 && ran2 <= .7) {
					spawnRaptor(2);
				} else {
					spawnApe(1);
					spawnRaptor(1);
				}
			} else {
				float ran2 = r.nextFloat();
				if (ran2 <= .5) {
					spawnApe(1);
				} else {
					spawnRaptor(1);
				}
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
		ground1 = new Pixmap(Gdx.files.internal("level1_3/ground1.png"));
		ground2 = new Pixmap(Gdx.files.internal("level1_3/ground2.png"));
		ground3 = new Pixmap(Gdx.files.internal("level1_3/ground3.png"));
		ground4 = new Pixmap(Gdx.files.internal("level1_3/ground4.png"));
		Array<Pixmap> groundPix = new Array<Pixmap>();
		groundPix.add(ground1);
		groundPix.add(ground2);
		groundPix.add(ground3);
		groundPix.add(ground4);
		int startX = -20;
		int startY = 0;
		while (startX < levelLength) {
			BackgroundObject gr = new BackgroundObject(groundPix, startX, startY);
			gr.setTexture();
			grounds.add(gr);
			startX = startX + ground1.getWidth();
		}


		//sky -- doesn't ever move.
		s = new Pixmap(Gdx.files.internal("level1_3/sky.png"));
		sk = new BackgroundObject(s,0,0);


		//random tree objects.
		tree1 = new Pixmap(Gdx.files.internal("level1_3/tree1.png"));
		tree2 = new Pixmap(Gdx.files.internal("level1_3/tree2.png"));
		tree3 = new Pixmap(Gdx.files.internal("level1_3/tree3.png"));
		tree4 = new Pixmap(Gdx.files.internal("level1_3/tree4.png"));
		tree5 = new Pixmap(Gdx.files.internal("level1_3/tree5.png"));
		tree6 = new Pixmap(Gdx.files.internal("level1_3/tree6.png"));
		tree7 = new Pixmap(Gdx.files.internal("level1_3/tree7.png"));
		tree8 = new Pixmap(Gdx.files.internal("level1_3/tree8.png"));
		Array<Pixmap> treesPix = new Array<Pixmap>();
		treesPix.add(tree1);
		treesPix.add(tree2);
		treesPix.add(tree3);
		treesPix.add(tree4);
		treesPix.add(tree5);
		treesPix.add(tree6);
		treesPix.add(tree7);
		treesPix.add(tree8);
		int ranPos = -100;
		while (ranPos < levelLength * (1-(.5/3))) {
			int randomX = r.nextInt(75-50) + 50;
			int randomY = 225;
			ranPos = ranPos + randomX;
			BackgroundObject t = new BackgroundObject(treesPix, ranPos, randomY);
			t.setTexture();
			trees.add(t);
		}

		//random bush objects.
		bush1 = new Pixmap(Gdx.files.internal("level1_3/flower1.png"));
		bush2 = new Pixmap(Gdx.files.internal("level1_3/flower2.png"));
		bush3 = new Pixmap(Gdx.files.internal("level1_3/flower3.png"));
		bush4 = new Pixmap(Gdx.files.internal("level1_3/flower4.png"));
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
		volcano1 = new Pixmap(Gdx.files.internal("level1_3/mountainbig.png"));
		volcano2 = new Pixmap(Gdx.files.internal("level1_3/mountainsmall.png"));
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
		cloud1 = new Pixmap(Gdx.files.internal("level1_3/cloud1.png"));
		cloud2 = new Pixmap(Gdx.files.internal("level1_3/cloud2.png"));
		cloud3 = new Pixmap(Gdx.files.internal("level1_3/cloud3.png"));
		cloud4 = new Pixmap(Gdx.files.internal("level1_3/cloud4.png"));
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
		debris1 = new Pixmap(Gdx.files.internal("level1_3/smallgrass.png"));
		debris2 = new Pixmap(Gdx.files.internal("level1_3/smallrock1.png"));
		debris3 = new Pixmap(Gdx.files.internal("level1_3/smallrock2.png"));
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
		fog1 = new Pixmap(Gdx.files.internal("level1_3/fog1.png"));
		fog2 = new Pixmap(Gdx.files.internal("level1_3/fog2.png"));
		fog3 = new Pixmap(Gdx.files.internal("level1_3/fog3.png"));
		fog4 = new Pixmap(Gdx.files.internal("level1_3/fog4.png"));
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
			if (i.getX() > LevelRenderer.camPos - 300 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
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
	
	public void drawGRHealth() {
		LevelRenderer.batch.draw(healthbar, LevelRenderer.camPos + 680 + 3, 450, goldenraptor.getHealth()* 1.25f, 15);
		LevelRenderer.batch.draw(healthbaroutline, LevelRenderer.camPos + 680, 450 - 3, 206, 21);
		fontBig.draw(LevelRenderer.batch, "Golden     Raptor", LevelRenderer.camPos + 400, 464);
	}
	
	public void dispose() {
		/*
		ground1.dispose(); 
		ground2.dispose(); 
		ground3.dispose();
		ground4.dispose(); 
		tree1.dispose(); 
		tree2.dispose(); 
		tree3.dispose(); 
		tree4.dispose();
		tree5.dispose(); 
		tree6.dispose(); 
		tree7.dispose(); 
		tree8.dispose();  
		bush1.dispose(); 
		bush2.dispose();
		bush3.dispose();
		bush4.dispose();
		volcano1.dispose();
		volcano2.dispose();
		cloud1.dispose();
		cloud2.dispose();
		cloud3.dispose();
		cloud4.dispose(); 
		debris1.dispose(); 
		debris2.dispose();
		debris3.dispose(); 
		fog1.dispose(); 
		fog2.dispose(); 
		fog3.dispose(); 
		fog4.dispose();
		s.dispose();
		*/
		grounds.clear();
		trees.clear();
		bushes.clear();
		volcanos.clear();
		clouds.clear();
		debris.clear();
		fog.clear();
		
		super.dispose();
	}

}