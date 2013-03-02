package renderers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.rip.RipGame;
//import com.rip.RipGame;
//import com.rip.levels.Level;
import com.rip.levels.Level_1_1;
import com.rip.objects.Enemy;
import com.rip.objects.Background;
import com.rip.objects.BackgroundObject;
//import com.rip.objects.Enemy;
import com.rip.objects.MovableEntity;
import com.rip.objects.NegaPlayer;
import com.rip.objects.Player;


public class LevelRender {
	//abstract level once more levels are in use
	Level_1_1 level;
	SpriteBatch batch;
	Music leveltheme;
	
	OrthographicCamera cam;
	public static int camPos = 0;
	
	public static float delta;
	
	// Load Textures
	Texture playerTexture;
	Texture timeFreezeOverlay = new Texture(Gdx.files.internal("data/timeFreezeOverlay.png"));
	Texture timebaroutline = new Texture(Gdx.files.internal("data/timebaroutline.png"));
	Texture timebar = new Texture(Gdx.files.internal("data/timebar.png"));
	Texture healthbaroutline = new Texture(Gdx.files.internal("data/healthbaroutline.png"));
	Texture healthbar = new Texture(Gdx.files.internal("data/healthbar.png"));
	
	
	
	ShapeRenderer sr;
	Player player;
	int width, height;
	public final static int Y_LIMIT = 180;
	

	ArrayList<Enemy> enemy_list;
	ArrayList<MovableEntity> drawables;
	
	//checkpoint boolean holders
	boolean checkPoint1, checkPoint2, checkPoint3, checkPoint4, levelComplete = false;
	boolean cp2Wave1, cp2Wave2 = false;
	boolean cp3Wave1, cp3Wave2 = false;
	boolean cp4Wave1, cp4Wave2 = false;
	
	Background bg;
	Background fg;
	
	float stateTime = 0f;
	//float delta;
	
	
	// Boolean value states whether or not the world will continue to scroll
	boolean move = true;
	
	BackgroundObject sk;
	Array<BackgroundObject> grounds = new Array<BackgroundObject>(5);

	//background object arrays (pass from init -> render)
	Array<BackgroundObject> trees = new Array<BackgroundObject>(100);
	Array<BackgroundObject> bushes = new Array<BackgroundObject>(100);
	Array<BackgroundObject> volcanos = new Array<BackgroundObject>(100);
	Array<BackgroundObject> clouds = new Array<BackgroundObject>(100);
	Array<BackgroundObject> debris = new Array<BackgroundObject>(100);
	

	public LevelRender (Level_1_1 level) {
		this.level = level;
		level.setRenderer(this);
		this.leveltheme = level.getLeveltheme();
		leveltheme.play();
		
		
		width = 960;
		height = 480;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		
		batch = new SpriteBatch();
		
		sr = new ShapeRenderer();
		
		
		drawables = new ArrayList<MovableEntity>();
		
//////////GENERATE ALL BACKGROUND OBJECTS//////////		

		Random r = new Random();

		int levelLength = 13000;
		
		//background objects
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
	
	public void render() {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		delta = Gdx.graphics.getDeltaTime();
		stateTime += delta;
		
		player = level.getPlayer();
		enemy_list = level.getEnemies();
		drawables.add(player);
		drawables.addAll(enemy_list);
		//cam.position.set(player.getX(), player.getY(), 0);
		
		cam.update();
		
		batch.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		
		//sort enemies by Y position for drawling.
		Collections.sort(drawables, new Comparator<MovableEntity>() {
			public int compare(MovableEntity a, MovableEntity b) {
				return a.getY() >= b.getY() ? -1 : 1;
			}
		});
	
		
//////////CHECKPOINT HANDLING//////////

		if (level.getEnemies().isEmpty() && move == false && camPos < 11500) {
			move = true;
		}

		//CHECKPOINT 1//
		if (camPos >= 1500 && checkPoint1 == false) {
			Gdx.app.log(RipGame.LOG, "checkpoint1");
			move = false;
			level.checkPoint(0,1);
			checkPoint1 = true;
		}

		//CHECKPOINT 2//
		//wave 1
		if (camPos >= 4000 && checkPoint2 == false && cp2Wave1 == false) {
			Gdx.app.log(RipGame.LOG, "checkpoint2");
			move = false;
			level.checkPoint(0, 3);
			cp2Wave1 = true;
		}

		//wave2
		if (level.getEnemies().isEmpty() && cp2Wave2 == false && cp2Wave1 == true) {
			move = false;
			level.checkPoint(0,2);
			cp2Wave2 = true;
			checkPoint2 = true;
		}

		//CHECKPOINT 3//
		//wave 1
		if (camPos >= 7000 && checkPoint3 == false && cp3Wave1 == false) {
			move = false;
			level.checkPoint(0,2);
			cp3Wave1 = true;
		}

		//wave 2
		if (level.getEnemies().isEmpty() && cp3Wave2 == false && cp3Wave1 == true) {
			move = false;
			level.checkPoint(1,0);
			cp3Wave2 = true;
			checkPoint3 = true;
		}

		//CHECKPOINT 4 -- FINAL//
		//wave1
		if (camPos >= 11000 && checkPoint4 == false && cp4Wave1 == false) {
			move = false;
			level.checkPoint(0,6);
			cp4Wave1 = true;
		}

		//wave2
		if (level.getEnemies().isEmpty() && cp4Wave2 == false && cp4Wave1 == true) {
			move = false;
			level.checkPoint(3,0);
			cp4Wave2 = true;
			checkPoint4 = true;
		}

		//END LEVEL//

		if (checkPoint4 == true && camPos >= 11500) {
			move = false;
			Gdx.app.log(RipGame.LOG, "End Level 1-1");
		}
		batch.begin();
		sr.begin(ShapeType.Rectangle);
		
		
		//////////RENDER ALL BACKGROUND OBJECTS//////////


		//draw sky
		batch.draw(sk.getTexture(), sk.getX(), sk.getY());

		//draw random clouds only in visible screen.
		for (BackgroundObject i : clouds) {
			if (i.getX() > camPos - 250 && i.getX() < camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw random volcanos only in visible screen.
		for (BackgroundObject i : volcanos) {
			if (i.getX() > camPos - 250 && i.getX() < camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}

		}

		//draw random trees only in visible screen.
		for (BackgroundObject i : trees) {
			if (i.getX() > camPos - 130 && i.getX() < camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw ground
		for (BackgroundObject i : grounds) {
			if (i.getX() > camPos - i.getTexture().getWidth() && i.getX() < camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw random bushes only in visible screen.
		for (BackgroundObject i : bushes) {
			if (i.getX() > camPos - 100 && i.getX() < camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}

		//draw random debris objects only in visible screen.
		for (BackgroundObject i : debris) {
			if (i.getX() > camPos - 20 && i.getX() < camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}	
		}
		
		
		//////////DRAW ALL MOVABLE OBJECTS//////////

	    // Layered drawing effect:
		// MovableEntities with higher Y values are drawn before those with lower Y values
		for (int i = 0; i < drawables.size(); i++) {
			//Gdx.app.log(RipGame.LOG, "Drawables");
			MovableEntity me = drawables.get(i);
			if ((me instanceof Player) && player.getTimeFreeze() == false){
				batch.draw(me.getCurrentFrame(), me.getX(), me.getY());
			} else {
				batch.draw(me.getTexture(), me.getX(), me.getY());
			}
			//sr.rect(me.hitableBox.x, me.hitableBox.y, me.hitableBox.width, me.hitableBox.height);
			//sr.rect(me.getX(), me.getY(), me.hitableBox.width, me.hitableBox.height);
		}	
		
		batch.draw(healthbar, camPos + 25, 450, player.getHealth()*2, 15);
		batch.draw(healthbaroutline, camPos + 25 - 3, 450 - 3, 206, 21);

		if (player.getTimeFreeze() == true || Gdx.input.isKeyPressed(Keys.SPACE)) {
			batch.draw(timeFreezeOverlay, camPos, 0);
			batch.draw(player.getCurrentFrame(), player.getX(), player.getY());
		}

		batch.draw(timebar, camPos + 25, 425, player.getTime()*2, 15);
		batch.draw(timebaroutline, camPos + 25 - 3, 425 - 3, 206, 21);


		//batch.draw(player.getCurrentFrame(), player.getX(), player.getY());
		
		/*
		sr.rect(player.hitableBox.x, player.hitableBox.y, player.hitableBox.width, player.hitableBox.height);
		
		sr.rect(player.punchBoxLeft.x, player.punchBoxLeft.y, player.punchBoxLeft.width, player.punchBoxLeft.height);
		sr.rect(player.punchBoxRight.x, player.punchBoxRight.y, player.punchBoxRight.width, player.punchBoxRight.height);
		*/
		batch.end();
		sr.end();

		//////////RENDER ENEMY TRACKING (AI)//////////
/*
		for (int i = 0; i < drawables.size(); i++) {
			Enemy e = (Enemy) drawables.get(i);
			e.track(player);
		}
*/
		if (player.getTime() <= 100) {
			player.setTime(player.getTime() + (2 * delta));
		} else if (player.getTime() > 100) {
			player.setTime(100f);
		}

		if (player.getTimeFreeze() == true && player.getTime() <= 0) {
			player.flipTimeFreeze();
		}

		if (player.getTimeFreeze() == true && player.getTime() > 0) {
			player.setTime(player.getTime() - (25 * delta));
		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && Gdx.input.isKeyPressed(Keys.A) && player.getTime() > 0 && player.getX() > camPos && player.getTimeFreeze() == false) {
			player.setTime(player.getTime() - (100 * delta));
			player.setX(player.getX() - 20);
		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && Gdx.input.isKeyPressed(Keys.D) && player.getTime() > 0 && player.getX() < camPos + RipGame.WIDTH - player.getWidth() && player.getTimeFreeze() == false) {
			player.setTime(player.getTime() - (100 * delta));
			player.setX(player.getX() + 20);
		} else {
			for (int i = 0; i < drawables.size(); i++) {
				MovableEntity me = drawables.get(i);
				if (me instanceof Player) {
					continue;
				}
				Enemy e = (Enemy) me;
				e.track(player);
			}
		}
		
		
		
		//Create array of booleans in which each boolean corresponds with a direction
		// i.e. [<UP>, <DOWN>, <LEFT>, <RIGHT>]
		boolean[] c = player.collides(enemy_list);
		//Gdx.app.log(RipGame.LOG, c.toString());
		
		if (player.isATTACK_ANIMATION()) {
			player.setCurrentFrame(delta);
			level.getIn().setWAIT(true);
			if (player.getPlayer_animation().isAnimationFinished(player.getStateTime())) {
				for (int i = 0; i < enemy_list.size(); i++) {
					Enemy e = enemy_list.get(i);
						if (e.getHealth() <= 0)	{
							enemy_list.remove(i);
							drawables.remove(e);
						}
				}
				player.setATTACK_ANIMATION(false);
				level.getIn().setWAIT(false);
				switch(player.getDir()){
					case DIR_LEFT:
						player.setPlayer_animation(player.getWalkAnimationLeft());
						player.setStateTime(0f);
						player.setCurrentFrame(0f);
						break;
					case DIR_RIGHT:
						player.setPlayer_animation(player.getWalkAnimationRight());
						player.setStateTime(0f);
						player.setCurrentFrame(0f);
						break;
				}
					
				Gdx.app.log(RipGame.LOG, "ANIMATION OVER");
			}
		}
		
		else if(Gdx.input.isKeyPressed(Keys.A) && !c[2]) { 
			if (player.getX() > camPos) {
				player.setX((player.getX() - player.getSPEED()));
				player.setCurrentFrame(delta);
			}
		
		}
		
		else if(Gdx.input.isKeyPressed(Keys.D) && !c[3]) { 
			if (player.getX() + player.getWidth() < camPos + RipGame.WIDTH) {
				player.setX((player.getX() + player.getSPEED()));
				player.setCurrentFrame(delta);
			}
			
			if (move && (player.getX()) - camPos > 450) {
				//moves camera along level. 
					cam.translate(3, 0);
					camPos += 3;

					//background parallax
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
			
		}
		else if(Gdx.input.isKeyPressed(Keys.W) && !c[0]) {
			if (player.getY() >= Y_LIMIT) {
				player.setY(player.getY());
				
			} else {
				player.setY(player.getY() + 2);
			}
			player.setCurrentFrame(delta);
		}
		else if(Gdx.input.isKeyPressed(Keys.S) && !c[1]) { 
			if (player.getY() <= 0) {
				player.setY(player.getY());
			} else {
				player.setY(player.getY() - 2);
			}
			player.setCurrentFrame(delta);
		}
		
		/*
		//needs work
		else if(Gdx.input.isKeyPressed(Keys.K) || Gdx.input.isKeyPressed(Keys.L)) {
			player.setCurrentFrame(delta);
			Gdx.app.log(RipGame.LOG, "space");
			for (int i = 0; i < enemy_list.size(); i++) {
				Enemy e = enemy_list.get(i);
					if (e.getHealth() <= 0)	{
						enemy_list.remove(i);
						drawables.remove(e);
					}
			}
		}
		*/

		drawables.clear();
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
	public void dispose() {
		batch.dispose();
		playerTexture.dispose();
		sr.dispose();
		leveltheme.dispose();
	}
	
}
