package renderers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rip.RipGame;
import com.rip.levels.Level;
import com.rip.objects.Ape;
import com.rip.objects.Enemy;
import com.rip.objects.HealthPack;
import com.rip.objects.Lucy;
import com.rip.objects.MovableEntity;
import com.rip.objects.Player;
import com.rip.objects.Raptor;
import com.rip.objects.TimePack;

public class LevelRenderer {
	//////////UNIVERSAL VARIABLES//////////
	public static Level level;
	public static SpriteBatch batch;
	private static Music leveltheme, additional_theme1, additional_theme2;
	RipGame game;
	public static ShapeRenderer sr;
	public static Player player;
	public static int width;
	public int height;
	public final static int Y_LIMIT = 180;
	public static float levelTime = 0;
	public static int levelScore = 0;
	public static OrthographicCamera cam;
	public static int camPos = 0;
	public static float delta;
	public static ArrayList<Enemy> enemy_list;
	public static ArrayList<HealthPack> healthpacks = new ArrayList<HealthPack>();
	public static ArrayList<MovableEntity> drawables;
	public static ArrayList<TimePack> timepacks = new ArrayList<TimePack>();
	public Random r = new Random();
	float stateTime = 0f;
	public static boolean move = true;
	
	public static Stage stage;
	public static boolean pause = false;
	public boolean frozen;
	boolean warp_play = false;
	
	public static boolean ripMove = true;
	
	Sound warp;

	//////////UNIVERSAL TEXTURES//////////
	Texture playerTexture;

	public LevelRenderer() {
		width = 960;
		height = 480;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);

		batch = new SpriteBatch();

		sr = new ShapeRenderer();

		drawables = new ArrayList<MovableEntity>();
		warp = Gdx.audio.newSound(Gdx.files.internal("data/Time Warp_01.wav"));
	}

	public LevelRenderer(Level level) {
		this.level = level;
		level.setRenderer(this);

		width = 960;
		height = 480;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);

		batch = new SpriteBatch();

		sr = new ShapeRenderer();

		drawables = new ArrayList<MovableEntity>();
		game = level.game;

		level.generateBackground();
		
		// place holder for real sound effects - must be 16bit
		warp = Gdx.audio.newSound(Gdx.files.internal("data/Time Warp_01.wav"));
		
		if (level.additional_theme1 != null) {
			LevelRenderer.setAdditional_theme1(level.additional_theme1);
		}
		if (level.additional_theme2 != null) {
			LevelRenderer.setAdditional_theme1(level.additional_theme2);
		}
		if (level.leveltheme != null) {
			LevelRenderer.setLeveltheme(level.getLeveltheme());
			getLeveltheme().play();
		}
		
	}

	public void render() {
//		Gdx.app.log(RipGame.LOG, "rendering...");

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		delta = Gdx.graphics.getDeltaTime();
		stateTime += delta;

		player = level.getPlayer();
		enemy_list = level.getEnemies();
		drawables.add(player);
		drawables.addAll(healthpacks);
		drawables.addAll(timepacks);
		drawables.addAll(enemy_list);
		
		//Gdx.app.log(RipGame.LOG, Integer.toString(this.enemy_list.size()));
		
		//sort enemies by Y position for drawling.
		Collections.sort(drawables, new Comparator<MovableEntity>() {
			public int compare(MovableEntity a, MovableEntity b) {
				return a.getY() >= b.getY() ? -1 : 1;
			}
		});

		cam.update();

		batch.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);

		batch.begin();
		sr.begin(ShapeType.Rectangle);

		level.drawBackground(batch);

		drawDrawables();
		
		if (!pause && ripMove) {
			player.handleTime(this, level, game);
			player.handleMovement(this, level, game);
			if (player.getTimeFreeze() == true || Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && !player.ULT) {
				if (warp_play) {
					
				} else {
					warp.play();
				}
				batch.draw(level.timeFreezeOverlay, camPos, 0);
				batch.draw(player.getCurrentFrame(), player.getX(), player.getY());
				this.frozen = true;
			} else {
				warp.stop();
				this.frozen = false;
			}
		}

		level.drawHud(batch, level.levelHudColor, this);

		level.handleCheckPoints(this);
		
		if (level.isEnd()) {
			this.getLeveltheme().stop();
			batch.draw(level.level_complete, camPos, 0);
			
		}
		
		if (player.dead) {
			batch.draw(level.deadOverlay, camPos, 0);
		}

		drawables.clear();
		level.checkPause();

		batch.end();
		sr.end();

	}

	public void drawDrawables() {
		for (int i = 0; i < drawables.size(); i++) {
			MovableEntity me = drawables.get(i);
			//sr.rect(me.hitableBox.x, me.hitableBox.y, me.hitableBox.width, me.hitableBox.height);
			if ((me instanceof Player) && player.getTimeFreeze() == false){
				batch.draw(me.getCurrentFrame(), me.getX(), me.getY());
				sr.rect(player.punchBoxLeft.x, player.punchBoxLeft.y, player.punchBoxLeft.width, player.punchBoxLeft.height);
				sr.rect(player.punchBoxRight.x, player.punchBoxRight.y, player.punchBoxRight.width, player.punchBoxRight.height);
			} else if (me instanceof Raptor){
				batch.draw(me.getCurrentFrame(), me.getX(), me.getY());
				if (!this.frozen) {
					((Raptor) me).setCurrentFrame(delta);
				}
				if (((Raptor) me).attacking && 
							((Raptor) me).getRaptor_animation().isAnimationFinished(me.getStateTime())) {
					//Gdx.app.log(RipGame.LOG, "Attack End");
					//player.setHealth(player.getHealth() - ((Raptor) me).getDamage());
					((Raptor) me).attacking = false;
					me.setStateTime(0);
					switch (me.getDir()) {
					case DIR_LEFT:
						((Raptor) me).setRaptor_animation(((Raptor) me).getWalkAnimationLeft());
						break;
					case DIR_RIGHT:
						((Raptor) me).setRaptor_animation(((Raptor) me).getWalkAnimationRight());
						break;
					default:
						break;
					}
				}
				 if (((Raptor) me).getHealth() <= 0) {
						if (((Raptor) me).getRaptor_animation() == ((Raptor) me).getEXPAnimation()) {
							if (((Raptor) me).getRaptor_animation().isAnimationFinished(me.getStateTime())) {
								((Raptor) me).dead = true;
							}
						} else {
							me.setStateTime(0f);
							((Raptor) me).setRaptor_animation(((Raptor) me).getEXPAnimation());
						}
					}
			} else if (me instanceof Ape) {
				batch.draw(me.getCurrentFrame(), me.getX(), me.getY());
				if (!this.frozen) {
					((Ape) me).setCurrentFrame(delta);
				}
				if (((Ape) me).attacking && 
							((Ape) me).getApe_animation().isAnimationFinished(me.getStateTime())) {
					((Ape) me).attacking = false;
					me.setStateTime(0);
					switch (me.getDir()) {
					case DIR_LEFT:
						((Ape) me).setApe_animation(((Ape) me).getWalkAnimationLeft());
						break;
					case DIR_RIGHT:
						((Ape) me).setApe_animation(((Ape) me).getWalkAnimationRight());
						break;
					default:
						break;
					}
				}  
				if (((Ape) me).getHealth() <= 0) {
					Gdx.app.log(RipGame.LOG, "Set Dead Animation");
					if (((Ape) me).getApe_animation() == ((Ape) me).getEXPAnimation()) {
						Gdx.app.log(RipGame.LOG, "Set Dead Animation ==");
						if (((Ape) me).getApe_animation().isAnimationFinished(me.getStateTime())) {
							((Ape) me).dead = true;
						}
					} else {
						me.setStateTime(0f);
						((Ape) me).setApe_animation(((Ape) me).getEXPAnimation());
					}
				}
			} else if (me instanceof HealthPack) {
				batch.draw(me.getTexture(), me.getX(), me.getY());
				if (((HealthPack) me).collides(player)) {
					healthpacks.remove(me);
				}
			} else if (me instanceof TimePack) {
				batch.draw(me.getTexture(), me.getX(), me.getY());
				//Gdx.app.log(RipGame.LOG, "Timepack draw");
				if (((TimePack) me).collides(player)) {
					timepacks.remove(me);
					drawables.remove(me);
				}
			} else if (me instanceof Lucy) {
				batch.draw(me.getCurrentFrame(), me.getX(), me.getY());
				//this.x + 50, this.y, (this.width * 0.7f), this.height);
				sr.rect(((Lucy) me).hitableBox.x, ((Lucy) me).hitableBox.y, ((Lucy) me).hitableBox.width, ((Lucy) me).hitableBox.height);
				//sr.rect(((Lucy) me).leftHitableBox.x, ((Lucy) me).leftHitableBox.y, ((Lucy) me).leftHitableBox.width, ((Lucy) me).leftHitableBox.height);
				sr.rect(((Lucy) me).leftAttackBox.x, ((Lucy) me).leftAttackBox.y, ((Lucy) me).leftAttackBox.width, ((Lucy) me).leftAttackBox.height);
				sr.rect(((Lucy) me).rightAttackBox.x, ((Lucy) me).rightAttackBox.y, ((Lucy) me).rightAttackBox.width, ((Lucy) me).rightAttackBox.height);
				if (((Lucy) me).waiting || ((!this.frozen) && !((Lucy) me).not_moving) || ((Lucy) me).isFallen()) {
					((Lucy) me).setCurrentFrame(delta);
				}
				if (((Lucy) me).attacking) {
					//Gdx.app.log(RipGame.LOG, "Lucy Attack");
				}
				if (((Lucy) me).attacking && 
							((Lucy) me).getLucy_animation().isAnimationFinished(me.getStateTime())) {
					((Lucy) me).attacking = false;
					me.setStateTime(0);
					((Lucy) me).revertPosition();
				} if (((Lucy) me).getHealth() <= 0) {
					if (((Lucy) me).getLucy_animation() == ((Lucy) me).getEXPAnimation()) {
						if (((Lucy) me).getLucy_animation().isAnimationFinished(me.getStateTime())) {
							((Lucy) me).dead = true;
						}
					} else {
						me.setStateTime(0f);
						((Lucy) me).setLucy_animation(((Lucy) me).getEXPAnimation());
					}
				}
				
			} else {
				batch.draw(me.getTexture(), me.getX(), me.getY());
			}
			
		}

	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public static Music getLeveltheme() {
		return leveltheme;
	}

	public static void setLeveltheme(Music lt) {
		leveltheme = lt;
	}
	

	public static Music getAdditional_theme1() {
		return additional_theme1;
	}

	public static void setAdditional_theme1(Music additional_theme1) {
		LevelRenderer.additional_theme1 = additional_theme1;
	}

	public static Music getAdditional_theme2() {
		return additional_theme2;
	}

	public static void setAdditional_theme2(Music additional_theme2) {
		LevelRenderer.additional_theme2 = additional_theme2;
	}

	public void setLevel(Level level) {
		this.level = level;
		level.generateBackground();
	}
	
	
	public void dispose() {
		this.camPos = 0;
		
		if (leveltheme != null) {
			leveltheme.dispose();
		}
		
		if (additional_theme1 != null) {
			additional_theme1.dispose();
		}
		
		if (additional_theme2 != null) {
			additional_theme2.dispose();
		}
		
	}
}