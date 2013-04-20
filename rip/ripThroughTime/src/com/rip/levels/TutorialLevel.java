package com.rip.levels;

import java.util.ArrayList;
import java.util.Random;

import renderers.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.rip.RipGame;
import com.rip.objects.BackgroundObject;
import com.rip.objects.Enemy;

public class TutorialLevel extends Level {

	public RipGame game;
	LevelRenderer lr;
	ArrayList<Enemy> enemies;

	Random r = new Random();
	
	Texture clock1 = new Texture("tutorialLevel/grandfatherClock1.png");
	Texture clock2 = new Texture("tutorialLevel/grandfatherClock2.png");
	Texture clock3 = new Texture("tutorialLevel/grandfatherClock3.png");
	Texture clock4 = new Texture("tutorialLevel/grandfatherClock4.png");
	Texture ripTalk = new Texture("tutorialLevel/SMALLriptalk1.png");
	Texture vanTalk = new Texture("tutorialLevel/SMALLvan.png");
	
	Texture ripTalk1 = new Texture("tutorialLevel/ripTalk1.png");
	Texture ripTalk2 = new Texture("tutorialLevel/ripTalk2.png");
	Texture ripTalk3 = new Texture("tutorialLevel/ripTalk3.png");
	Texture ripTalk4 = new Texture("tutorialLevel/ripTalk4.png");
	Texture ripTalk5 = new Texture("tutorialLevel/ripTalk5.png");
	Texture ripTalk6 = new Texture("tutorialLevel/ripTalk6.png");
	Texture ripTalk7_1 = new Texture("tutorialLevel/ripTalk7-1.png");
	Texture ripTalk7_2 = new Texture("tutorialLevel/ripTalk7-2.png");
	Texture ripTalk8 = new Texture("tutorialLevel/ripTalk8.png");
	Texture ripTalk9 = new Texture("tutorialLevel/ripTalk9.png");
	Texture ripTalk10_1 = new Texture("tutorialLevel/ripTalk10-1.png");
	Texture ripTalk10_2 = new Texture("tutorialLevel/ripTalk10-2.png");
	Texture ripTalk10_3 = new Texture("tutorialLevel/ripTalk10-3.png");
	Texture ripTalk11 = new Texture("tutorialLevel/ripTalk11.png");
	Texture ripTalk12 = new Texture("tutorialLevel/ripTalk12.png");
	
	Texture vanTalk1 = new Texture("tutorialLevel/vanTalk1.png");
	Texture vanTalk2_1 = new Texture("tutorialLevel/vanTalk2-1.png");
	Texture vanTalk2_2 = new Texture("tutorialLevel/vanTalk2-2.png");
	Texture vanTalk2_3 = new Texture("tutorialLevel/vanTalk2-3.png");
	Texture vanTalk3 = new Texture("tutorialLevel/vanTalk3.png");
	Texture vanTalk4 = new Texture("tutorialLevel/vanTalk4.png");
	Texture vanTalk5 = new Texture("tutorialLevel/vanTalk5.png");
	Texture vanTalk6 = new Texture("tutorialLevel/vanTalk6.png");
	Texture vanTalk7 = new Texture("tutorialLevel/vanTalk7.png");
	Texture vanTalk8 = new Texture("tutorialLevel/vanTalk8.png");
	Texture vanTalk9 = new Texture("tutorialLevel/vanTalk9.png");
	Texture vanTalk10_1 = new Texture("tutorialLevel/vanTalk10-1.png");
	Texture vanTalk10_2 = new Texture("tutorialLevel/vanTalk10-2.png");
	Texture vanTalk11 = new Texture("tutorialLevel/vanTalk11.png");
	Texture vanTalk12 = new Texture("tutorialLevel/vanTalk12.png");
	Texture vanTalk13 = new Texture("tutorialLevel/vanTalk13.png");
	Texture vanTalk14 = new Texture("tutorialLevel/vanTalk14.png");
	Texture vanTalk15 = new Texture("tutorialLevel/vanTalk15.png");
	Texture vanTalk16 = new Texture("tutorialLevel/vanTalk16.png");
	
	Texture punchDir = new Texture("tutorialLevel/PunchDirections.png");
	Texture kickDir = new Texture("tutorialLevel/KickDirections.png");
	Texture punchComboDir = new Texture("tutorialLevel/PunchComboDirections.png");
	Texture kickComboDir = new Texture("tutorialLevel/KickComboDirections.png");
	Texture timeDir = new Texture("tutorialLevel/TimeDirections.png");
	
	
	Rectangle clockHitBox = new Rectangle(600, 100, clock1.getWidth(), clock1.getHeight());
	
	BackgroundObject ground;
	BackgroundObject sky;
	Array<BackgroundObject> clouds = new Array<BackgroundObject>(100);
	Array<BackgroundObject> watches = new Array<BackgroundObject>(100);
	
	int dialogCounter;
	float timer;
	boolean punch, kick, combo, combo1, combo2, timePower, levelComplete = false;
	
	Boolean nextPressed = false;
	
	public TutorialLevel(RipGame game) {
		super(game);
		setIn(new InputHandler(this));
		Gdx.input.setInputProcessor(getIn());
		
		leveltheme = Gdx.audio.newMusic(Gdx.files.internal("data/Prehistoric Main.mp3"));
		leveltheme.setLooping(true);
		
		levelLength = 2000;
		levelName = "";
		levelHudColor = "";
		
		dialogCounter = 1;
	}
	
	@Override
	public void handleCheckPoints(LevelRenderer lr) {
		LevelRenderer.move = false;
		
		timer += LevelRenderer.delta;
		
		Gdx.app.log(RipGame.LOG, "dialogCounter: " + dialogCounter);
		
		player.setTime(100f);
		if (Gdx.input.isKeyPressed(Keys.ANY_KEY) && !nextPressed) {
			nextPressed = true;
		}
		
		if (nextPressed && !Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
			nextPressed = false;
			dialogCounter += 1;
		}
		
		if (!punch && timer >= 3) {
			//first set of dialog.
			LevelRenderer.ripMove = false;
			if (!nextPressed && dialogCounter == 1) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk1, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 2) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk1, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 3) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk2, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 4) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk2_1, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 5) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk2_2, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 6) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk2_3, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 7) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk3, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 8) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk3, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 9) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk4, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 10) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk4, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 11) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk5, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 12) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk5, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (dialogCounter > 12 && !punch) {
				LevelRenderer.ripMove = true;
				LevelRenderer.batch.draw(punchDir, RipGame.WIDTH / 2 - punchDir.getWidth() /2, RipGame.HEIGHT - punchDir.getHeight() - 20);
				if (clockHitBox.overlaps(player.hitableBox)) {
					if (player.getPlayer_animation() == player.getPunchAnimationRight() || player.getPlayer_animation() == player.getPunchAnimationLeft()) {
						punch = true;
						dialogCounter = 1;
						timer = 0;
					}
				}
			}
		} else if (!kick) {
			if (!nextPressed && dialogCounter == 1 && timer >= 3) {
				LevelRenderer.ripMove = false;
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk6, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (dialogCounter > 1 && !kick) {
				LevelRenderer.ripMove = true;
				LevelRenderer.batch.draw(kickDir, RipGame.WIDTH / 2 - punchDir.getWidth() /2, RipGame.HEIGHT - punchDir.getHeight() - 20);
				if (clockHitBox.overlaps(player.hitableBox) && player.getPlayer_animation() == player.getKickAnimationRight()) {
					kick = true;
					dialogCounter = 1;
					timer = 0;
				}
			}
		} else if (!combo) {
			if (!nextPressed && dialogCounter == 1 && timer > 3) {
				LevelRenderer.ripMove = false;
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk7, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (dialogCounter > 1 && !combo1) {
				LevelRenderer.ripMove = true;
				LevelRenderer.batch.draw(punchComboDir, RipGame.WIDTH / 2 - punchDir.getWidth() /2, RipGame.HEIGHT - punchDir.getHeight() - 20);
				if (clockHitBox.overlaps(player.hitableBox) && player.getPlayer_animation() == player.getPunch2AnimationRight()) {
					combo1 = true;
					timer = 0;
					
				} 
			} else if (!nextPressed && combo1 && !combo2 && timer > 3) {
				LevelRenderer.batch.draw(kickComboDir, RipGame.WIDTH / 2 - punchDir.getWidth() /2, RipGame.HEIGHT - punchDir.getHeight() - 20);
				if (clockHitBox.overlaps(player.hitableBox) && player.getPlayer_animation() == player.getKick3AnimationRight()) {
					combo2 = true;
					combo = true;
					dialogCounter = 1;
					timer = 0;
				}
			}
			
		} else if (!timePower) {
			if (!nextPressed && dialogCounter == 1 && timer > 3) {
				LevelRenderer.ripMove = false;
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk8, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && !timePower && dialogCounter == 2) {
				LevelRenderer.batch.draw(timeDir, RipGame.WIDTH / 2 - punchDir.getWidth() /2, RipGame.HEIGHT - punchDir.getHeight() - 20);
				if (player.getTimeFreeze()) {
					//player will have to do the super time attack to get through this checkPoint.
					//Will also show how to time warp.
				}
			} else if (dialogCounter > 2) {
				timePower = true;
				dialogCounter = 1;
				timer = 0;
			}
		} else if (!levelComplete) {
			LevelRenderer.ripMove = false; 
			if (!nextPressed && dialogCounter == 1 && timer > 3) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk9, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 2) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk6, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 3) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk10_1, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 4) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk10_2, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 5) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk7_1, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 6) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk7_2, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 7) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk11, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 8) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk8, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 9) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk12, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 10) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk9, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 11) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk13, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 12) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk10_1, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed  && dialogCounter == 13) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk10_2, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 14) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk10_3, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 15) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk14, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 16) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk11, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 17) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk15, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 18) {
				LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
				LevelRenderer.batch.draw(ripTalk12, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
			} else if (!nextPressed && dialogCounter == 19) {
				LevelRenderer.batch.draw(vanTalk, RipGame.WIDTH - vanTalk.getWidth() - 20, RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.batch.draw(vanTalk16, RipGame.WIDTH - vanTalk.getWidth() - 40 - vanTalk1.getWidth(), RipGame.HEIGHT - vanTalk.getHeight() - 20);
				LevelRenderer.ripMove = true;
			} else if (dialogCounter > 19) {
				levelComplete = true;
			}
		}
	}
		
//		else if (timer >= 24 && timer < 35) {
//			LevelRenderer.batch.draw(ripTalk, 20, RipGame.HEIGHT - ripTalk.getHeight() - 20);
//			if (timer >= 24 && timer < 28) {
//				LevelRenderer.batch.draw(ripTalk8_1, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
//			} else if (timer >= 28 && timer < 32) {
//				LevelRenderer.batch.draw(ripTalk8_2, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
//			} else {
//				LevelRenderer.batch.draw(ripTalk8_3, 40 + ripTalk.getWidth(), RipGame.HEIGHT - ripTalk.getHeight() - 20);
//			}
//		}
	
	@Override
	public void generateBackground() {
		Pixmap g = new Pixmap(Gdx.files.internal("tutorialLevel/ground.png"));
		ground = new BackgroundObject(g,0,0);
		
		Pixmap s = new Pixmap(Gdx.files.internal("tutorialLevel/sky.png"));
		sky = new BackgroundObject(s,0,0);
		
		Pixmap cloud1 = new Pixmap(Gdx.files.internal("tutorialLevel/cloud1.png"));
		Pixmap cloud2 = new Pixmap(Gdx.files.internal("tutorialLevel/cloud2.png"));
		Pixmap cloud3 = new Pixmap(Gdx.files.internal("tutorialLevel/cloud3.png"));
		Pixmap cloud4 = new Pixmap(Gdx.files.internal("tutorialLevel/cloud4.png"));
		Array<Pixmap> cloudPix = new Array<Pixmap>();
		cloudPix.add(cloud1);
		cloudPix.add(cloud2);
		cloudPix.add(cloud3);
		cloudPix.add(cloud4);
		int ranPos = -300;
		while (ranPos < levelLength * 10) {
			int randomX = r.nextInt(100 - 50) + 50;
			int randomY = r.nextInt(400 - 180) + 180;
			ranPos += randomX;
			BackgroundObject c = new BackgroundObject(cloudPix,ranPos,randomY);
			c.setTexture();
			clouds.add(c);
		}
		
		Pixmap watch1 = new Pixmap(Gdx.files.internal("tutorialLevel/watch1.png"));
		Pixmap watch2 = new Pixmap(Gdx.files.internal("tutorialLevel/watch2.png"));
		Pixmap watch3 = new Pixmap(Gdx.files.internal("tutorialLevel/watch3.png"));
		Pixmap watch4 = new Pixmap(Gdx.files.internal("tutorialLevel/watch4.png"));
		Pixmap watch5 = new Pixmap(Gdx.files.internal("tutorialLevel/watch5.png"));
		Pixmap watch6 = new Pixmap(Gdx.files.internal("tutorialLevel/watch6.png"));
		Pixmap watch7 = new Pixmap(Gdx.files.internal("tutorialLevel/watch7.png"));
		Pixmap watch8 = new Pixmap(Gdx.files.internal("tutorialLevel/watch8.png"));
		Pixmap watch9 = new Pixmap(Gdx.files.internal("tutorialLevel/watch9.png"));
		Pixmap watch10 = new Pixmap(Gdx.files.internal("tutorialLevel/watch10.png"));
		Pixmap watch11 = new Pixmap(Gdx.files.internal("tutorialLevel/watch11.png"));
		Pixmap watch12 = new Pixmap(Gdx.files.internal("tutorialLevel/watch12.png"));
		Pixmap watch13 = new Pixmap(Gdx.files.internal("tutorialLevel/watch13.png"));
		Pixmap watch14 = new Pixmap(Gdx.files.internal("tutorialLevel/watch14.png"));
		Array<Pixmap> watchPix = new Array<Pixmap>();
		watchPix.add(watch1);
		watchPix.add(watch2);
		watchPix.add(watch3);
		watchPix.add(watch4);
		watchPix.add(watch5);
		watchPix.add(watch6);
		watchPix.add(watch7);
		watchPix.add(watch8);
		watchPix.add(watch9);
		watchPix.add(watch10);
		watchPix.add(watch11);
		watchPix.add(watch12);
		watchPix.add(watch13);
		watchPix.add(watch14);
		ranPos = -500;
		while (ranPos < levelLength * 10) {
			int randomX = r.nextInt(500 - 400) + 400;
			int randomY = 0;
			ranPos += randomX;
			BackgroundObject w = new BackgroundObject(watchPix,ranPos,randomY);
			w.setTexture();
			watches.add(w);
		}
		
	}
	
	@Override
	public void drawBackground(SpriteBatch batch) {
		LevelRenderer.sr.rect(600, 100, clock1.getWidth(), clock1.getHeight());
		parallax();
		
		batch.draw(sky.getTexture(), sky.getX(), sky.getY());
		batch.draw(sky.getTexture(), sky.getX() + sky.getTexture().getWidth(), sky.getY());
		
		
		for (BackgroundObject i : clouds) {
			if (i.getX() > LevelRenderer.camPos - 250 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}
		
		for (BackgroundObject i : watches) {
			int randomRotation = r.nextInt(30);
			if (r.nextBoolean()) {
				randomRotation = randomRotation * -1;
			}
			if (i.getX() > LevelRenderer.camPos - 500 && i.getX() < LevelRenderer.camPos + RipGame.WIDTH + 20) {
				batch.draw(i.getTexture(), i.getX(), i.getY());
			}
		}
		
		batch.draw(ground.getTexture(), ground.getX(), ground.getY());
		batch.draw(ground.getTexture(), ground.getX() + ground.getTexture().getWidth(), ground.getY());
		
		if (!punch) {
			batch.draw(clock1, 600, 100);
		} else if (!kick) {
			batch.draw(clock2, 600, 100);
		} else if (!combo) {
			batch.draw(clock3, 600, 100);
		} else if (combo) {
			batch.draw(clock4, 600, 100);
		}
	}
	
	@Override
	public void parallax() {
		for (BackgroundObject i : watches) {
			i.setX(i.getX() + (-20f * LevelRenderer.delta));
		}
		
		for (BackgroundObject i : clouds) {
			i.setX(i.getX() + (-10 * LevelRenderer.delta));
		}
	}
	
	@Override
	public void drawHud(SpriteBatch batch, String color, LevelRenderer lr) {
		//do nothing
	}
	
}