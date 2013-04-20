package com.rip.objects;

import java.util.ArrayList;

import renderers.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.rip.RipGame;
import com.rip.objects.MovableEntity.Directions;

public class Lucy extends Enemy {
	
	protected int attack_point_1 = LevelRenderer.camPos + 270;
	protected int attack_point_2 = LevelRenderer.camPos + 550;
	protected int attack_point_3;
	
	protected int swipe_damage = 30;
	protected int hit_damage = 50;
	
	protected Animation lucy_animation;
	protected TextureRegion currentFrame;
	
	protected Animation walkAnimationRight;
	protected Animation walkAnimationLeft;
	protected Texture walkSheet;
	protected TextureRegion[] walkFramesRight;
	protected TextureRegion[] walkFramesLeft;
	protected TextureRegion currentwalkFrame;
	
	protected Animation swipeAnimationRight;
	protected Animation swipeAnimationLeft;
	protected Texture swipeSheet;
	protected TextureRegion[] swipeFramesRight;
	protected TextureRegion[] swipeFramesLeft;
	protected TextureRegion currentswipeFrame;
	
	protected Animation hitAnimationRight;
	protected Animation hitAnimationLeft;
	protected Texture hitSheet;
	protected TextureRegion[] hitFramesRight;
	protected TextureRegion[] hitFramesLeft;
	protected TextureRegion currenthitFrame;
	
	protected Animation fallAnimationRight;
	protected Animation fallAnimationLeft;
	protected Texture fallSheet;
	protected TextureRegion[] fallFramesRight;
	protected TextureRegion[] fallFramesLeft;
	protected TextureRegion currentfallFrame;
	
	protected Animation waitAnimationRight;
	protected Animation waitAnimationLeft;
	protected Texture waitSheet;
	protected TextureRegion[] waitFramesRight;
	protected TextureRegion[] waitFramesLeft;
	protected TextureRegion currentwaitFrame;
	
	protected float stop_health;
	
	boolean has_been_seen = false;
	boolean fallen = false;
	boolean has_fallen = false;
	public boolean not_moving = false;
	public boolean waiting = false;
	public boolean intro = true;
	
	//protected float stateTime = 0f;

	private static final int WALK_COLS = 10;
	private static final int WALK_ROWS = 1;
	
	private static final int swipe_COLS = 15;
	private static final int swipe_ROWS = 1;
	
	private static final int hit_COLS = 15;
	private static final int hit_ROWS = 1;
	
	private static final int fall_COLS = 4;
	private static final int fall_ROWS = 1;
	
	private static final int wait_COLS = 2;
	private static final int wait_ROWS = 1;
	
	public enum Attack_State {SWIPE, HIT};
	
	public Attack_State astate;
	
	public int localSPEED = -2;
	
	public int preattackX, preattackY;
	
	//protected Rectangle leftAttackBox, rightAttackBox;
	public Rectangle leftHitableBox, rightHitableBox;
	
	Sound no_hit[] = {Gdx.audio.newSound(Gdx.files.internal("data/Club No Hit_01.wav")),
			Gdx.audio.newSound(Gdx.files.internal("data/Club No Hit_02.wav"))};
	
	Sound swipe1, swipe2, swipe3;
	

	public Lucy(int x, int y) {
		super(x, y, 275, 380, -2, 300);
		create_animations();
		astate = Attack_State.SWIPE;
		this.setDir(dir.DIR_LEFT);
		this.leftHitableBox = new Rectangle(this.x + 100, this.y, 120, this.height);
		this.rightHitableBox  = new Rectangle(this.x + 100, this.y, 120, this.height);
		this.leftAttackBox = new Rectangle(this.x, this.y, (this.width / 2), this.height);
		this.rightAttackBox = new Rectangle((this.x + (this.width / 2)), this.y, (this.width / 2), this.height);
		Sound hs[] = {Gdx.audio.newSound(Gdx.files.internal("data/Lucy Grunt_01.wav")),
				Gdx.audio.newSound(Gdx.files.internal("data/Lucy Grunt_02.wav"))};
		swipe1 = Gdx.audio.newSound(Gdx.files.internal("data/Club Smash_01.wav"));
		swipe2 = Gdx.audio.newSound(Gdx.files.internal("data/Club Smash_02.wav"));
		swipe3 = Gdx.audio.newSound(Gdx.files.internal("data/Club Smash_03.wav"));
		this.hit_sounds = hs;
		this.stop_health = this.getHealth() + 21;
		this.hitableBox = this.leftHitableBox;
	}
	
	public void create_animations() {
		int index;
		
		//Initiate Walk Animation
		TextureRegion temp;
		walkSheet = new Texture(Gdx.files.internal("data/lucy_walk.png"));
		TextureRegion[][] tmpwRight = TextureRegion.split(walkSheet, walkSheet.getWidth() / WALK_COLS, walkSheet.getHeight() / WALK_ROWS);
		TextureRegion[][] tmpwLeft = TextureRegion.split(walkSheet, walkSheet.getWidth() / WALK_COLS, walkSheet.getHeight() / WALK_ROWS);
		walkFramesRight = new TextureRegion[WALK_COLS * WALK_ROWS];
		walkFramesLeft = new TextureRegion[WALK_COLS * WALK_ROWS];
		index = 0;
		for (int i = 0; i < WALK_ROWS; i++) {
			for (int j = 0; j < WALK_COLS; j++) {
				temp = tmpwLeft[i][j];
				walkFramesLeft[index] = temp;
				//walkFramesLeft[index] = temp;
				index++;
			}
		}
		
		
		index = 0;
		for (int i = 0; i < WALK_ROWS; i++) {
			for (int j = 0; j < WALK_COLS; j++) {
				temp = tmpwRight[i][j];
				walkFramesRight[index] = temp;
				walkFramesRight[index].flip(true, false);
				index++;
			}
		}

		walkAnimationRight = new Animation(0.075f, walkFramesRight);
		walkAnimationLeft = new Animation(0.075f, walkFramesLeft);
		
		//Initiate Swipe Animation
		swipeSheet = new Texture(Gdx.files.internal("data/lucy_swipe.png"));
		TextureRegion[][] tmpsRight = TextureRegion.split(swipeSheet, swipeSheet.getWidth() / swipe_COLS, swipeSheet.getHeight() / swipe_ROWS);
		TextureRegion[][] tmpsLeft = TextureRegion.split(swipeSheet, swipeSheet.getWidth() / swipe_COLS, swipeSheet.getHeight() / swipe_ROWS);
		swipeFramesRight = new TextureRegion[swipe_COLS * swipe_ROWS];
		swipeFramesLeft = new TextureRegion[swipe_COLS * swipe_ROWS];
		index = 0;
		for (int i = 0; i < swipe_ROWS; i++) {
			for (int j = 0; j < swipe_COLS; j++) {
				temp = tmpsLeft[i][j];
				swipeFramesLeft[index] = temp;
				//walkFramesLeft[index] = temp;
				index++;
			}
		}
		
		
		index = 0;
		for (int i = 0; i < swipe_ROWS; i++) {
			for (int j = 0; j < swipe_COLS; j++) {
				temp = tmpsRight[i][j];
				swipeFramesRight[index] = temp;
				swipeFramesRight[index].flip(true, false);
				index++;
			}
		}

		swipeAnimationRight = new Animation(0.025f, swipeFramesRight);
		swipeAnimationLeft = new Animation(0.025f, swipeFramesLeft);
		
		//Initiate Hit Animation
		hitSheet = new Texture(Gdx.files.internal("data/lucy_hit.png"));
		TextureRegion[][] tmphRight = TextureRegion.split(hitSheet, hitSheet.getWidth() / hit_COLS, hitSheet.getHeight() / hit_ROWS);
		TextureRegion[][] tmphLeft = TextureRegion.split(hitSheet, hitSheet.getWidth() / hit_COLS, hitSheet.getHeight() / hit_ROWS);
		hitFramesRight = new TextureRegion[hit_COLS * hit_ROWS];
		hitFramesLeft = new TextureRegion[hit_COLS * hit_ROWS];
		index = 0;
		for (int i = 0; i < hit_ROWS; i++) {
			for (int j = 0; j < hit_COLS; j++) {
				temp = tmphLeft[i][j];
				hitFramesLeft[index] = temp;
				//walkFramesLeft[index] = temp;
				index++;
			}
		}
		
		
		index = 0;
		for (int i = 0; i < hit_ROWS; i++) {
			for (int j = 0; j < hit_COLS; j++) {
				temp = tmphRight[i][j];
				hitFramesRight[index] = temp;
				hitFramesRight[index].flip(true, false);
				index++;
			}
		}
		

		hitAnimationRight = new Animation(0.035f, hitFramesRight);
		hitAnimationLeft = new Animation(0.035f, hitFramesLeft);
		
		//Initiate Fall Animation
		fallSheet = new Texture(Gdx.files.internal("data/lucy_fall.png"));
		TextureRegion[][] tmpfRight = TextureRegion.split(fallSheet, fallSheet.getWidth() / fall_COLS, fallSheet.getHeight() / fall_ROWS);
		TextureRegion[][] tmpfLeft = TextureRegion.split(fallSheet, fallSheet.getWidth() / fall_COLS, fallSheet.getHeight() / fall_ROWS);
		fallFramesRight = new TextureRegion[fall_COLS * fall_ROWS];
		fallFramesLeft = new TextureRegion[fall_COLS * fall_ROWS];
		index = 0;
		for (int i = 0; i < fall_ROWS; i++) {
			for (int j = 0; j < fall_COLS; j++) {
				temp = tmpfLeft[i][j];
				fallFramesLeft[index] = temp;
				//walkFramesLeft[index] = temp;
				index++;
			}
		}
		
		
		index = 0;
		for (int i = 0; i < fall_ROWS; i++) {
			for (int j = 0; j < fall_COLS; j++) {
				temp = tmpfRight[i][j];
				fallFramesRight[index] = temp;
				fallFramesRight[index].flip(true, false);
				index++;
			}
		}
		

		fallAnimationRight = new Animation(0.1f, fallFramesRight);
		fallAnimationLeft = new Animation(0.1f, fallFramesLeft);
		
		//Initiate Wait Animation
		waitSheet = new Texture(Gdx.files.internal("data/lucywait.png"));
		TextureRegion[][] tmpwtRight = TextureRegion.split(waitSheet, waitSheet.getWidth() / wait_COLS, waitSheet.getHeight() / wait_ROWS);
		TextureRegion[][] tmpwtLeft = TextureRegion.split(waitSheet, waitSheet.getWidth() / wait_COLS, waitSheet.getHeight() / wait_ROWS);
		waitFramesRight = new TextureRegion[wait_COLS * wait_ROWS];
		waitFramesLeft = new TextureRegion[wait_COLS * wait_ROWS];
		index = 0;
		for (int i = 0; i < wait_ROWS; i++) {
			for (int j = 0; j < wait_COLS; j++) {
				temp = tmpwtLeft[i][j];
				waitFramesLeft[index] = temp;
				//walkFramesLeft[index] = temp;
				index++;
			}
		}
		
		
		index = 0;
		for (int i = 0; i < wait_ROWS; i++) {
			for (int j = 0; j < wait_COLS; j++) {
				temp = tmpwtRight[i][j];
				waitFramesRight[index] = temp;
				waitFramesRight[index].flip(true, false);
				index++;
			}
		}
		

		waitAnimationRight = new Animation(0.4f, waitFramesRight);
		waitAnimationLeft = new Animation(0.4f, waitFramesLeft);
		
		
		lucy_animation = walkAnimationLeft;
		currentFrame = lucy_animation.getKeyFrame(stateTime, true);
	
	}
	
	public void attackPosition() {
		Gdx.app.log(RipGame.LOG, "Attack Postion");
		Gdx.app.log(RipGame.LOG, Integer.toString(this.x));
		this.preattackX = this.x;
		this.preattackY = this.y; 
		switch (this.dir) {
		case DIR_LEFT:
			this.x = this.x - 50;
			//this.hitableBox.x -= 50;
			//this.leftAttackBox.x -= 50;
			//this.rightAttackBox.x -= 50;
			break;
		case DIR_RIGHT:
			this.x = this.x + 50;
			//this.hitableBox.x += 50;
			//this.leftAttackBox.x += 50;
			//this.rightAttackBox.x += 50;
			break;
		default:
			break;
		}
		Gdx.app.log(RipGame.LOG, "Post Attack Postion");
		Gdx.app.log(RipGame.LOG, Integer.toString(this.x));
	}
	
	public void revertPosition() {
		this.waiting = true;
		this.x = this.preattackX;
		this.y = this.preattackY; 
		Gdx.app.log(RipGame.LOG, "Revert Postion");
		Gdx.app.log(RipGame.LOG, Integer.toString(this.x));
		switch (this.dir) {
		case DIR_LEFT:
			//this.hitableBox.x += 50;
			//this.leftAttackBox.x += 50;
			//this.rightAttackBox.x += 50;
			this.lucy_animation = this.waitAnimationLeft;
			break;
		case DIR_RIGHT:
			//this.hitableBox.x -= 50;
			//this.leftAttackBox.x -= 50;
			//this.rightAttackBox.x -= 50;
			this.lucy_animation = this.waitAnimationRight;
			break;
		default:
			break;
		}
		//this.setCurrentFrame(0f);
	}
	
	
	
	public void swipe() {
		attackPosition();
		this.stop_health = this.getHealth();
		this.stateTime = 0f;
		switch (this.dir) {
		case DIR_LEFT:
			this.lucy_animation = this.swipeAnimationLeft;
			if (Intersector.overlapRectangles(this.leftAttackBox, LevelRenderer.getPlayer().hitableBox)) {
				this.swipe1.play();
				Gdx.app.log(RipGame.LOG, "Swipe Left");
				LevelRenderer.getPlayer().makeHit();
				LevelRenderer.getPlayer().hitBack(-60);
				LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - this.swipe_damage);
			} else {
				Sound n = this.getRandomNohit_sounds();
				n.play(1.0f);
			}
			
			break;
		case DIR_RIGHT:
			this.lucy_animation = this.swipeAnimationRight;
			if (Intersector.overlapRectangles(this.rightAttackBox, LevelRenderer.getPlayer().hitableBox)) {
				this.swipe1.play();
				LevelRenderer.getPlayer().makeHit();
				LevelRenderer.getPlayer().hitBack(60);
				LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - this.swipe_damage);
			} else {
				Sound n = this.getRandomNohit_sounds();
				n.play(1.0f);
			}
			break;
		default:
			break;
		}
			// replace this.hitableBox with a swipeBox
		//swipe noise
	}

		

	
	public void hit() {
		attackPosition();
		this.stop_health = this.getHealth();
		this.stateTime = 0f;
		switch (this.dir) {
		case DIR_LEFT:
			this.lucy_animation = this.hitAnimationLeft;
			if (Intersector.overlapRectangles(this.leftAttackBox, LevelRenderer.getPlayer().hitableBox)) {
				this.swipe2.play();
				LevelRenderer.getPlayer().makeHit();
				LevelRenderer.getPlayer().hitBack(-70);
				LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - this.hit_damage);
			} else {
				Sound n = this.getRandomNohit_sounds();
				n.play(1.0f);
			}
			break;
		case DIR_RIGHT:
			this.lucy_animation = this.hitAnimationRight;
			if (Intersector.overlapRectangles(this.rightAttackBox, LevelRenderer.getPlayer().hitableBox)) {
				this.swipe2.play();
				LevelRenderer.getPlayer().makeHit();
				LevelRenderer.getPlayer().hitBack(70);
				LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - this.hit_damage);
			} else {
				Sound n = this.getRandomNohit_sounds();
				n.play(1.0f);
			}
			break;
		default:
			break;
		}
		
		// replace this.hitableBox with a hitBox
		//thud noise
		// release time-ups?
		// release health?
	}
	
	public void update_movements() {
		
		if (!this.fallen && this.getHealth() <= (this.stop_health - 20)) {
			this.waiting = false;
			switch (this.dir) {
			case DIR_LEFT:
				this.lucy_animation = this.walkAnimationLeft;
				break;
			case DIR_RIGHT:
				this.lucy_animation = this.walkAnimationRight;
				break;
			default:
				break;
			}
		}
		
		if (this.fallen && (this.health > 20 && this.health <= 120)) {
			this.fallen = false;
			this.stateTime = 0f;
			switch (this.dir) {
			case DIR_LEFT:
				this.lucy_animation = this.walkAnimationLeft;
				break;
			case DIR_RIGHT:
				this.lucy_animation = this.walkAnimationRight;
				break;
			default:
				break;
			}
		}
		
		if (this.attacking) {

		} else {
			if (this.intro && (this.x <=  LevelRenderer.camPos + 650)) {
				this.lucy_animation = this.waitAnimationLeft;
				this.waiting = true;
				Gdx.app.log(RipGame.LOG, "Intro");
			}
			
			else if (this.fallen) {
				Gdx.app.log(RipGame.LOG, "Fallen");
			}
			else if ((this.health <= 150 && !this.has_fallen) || this.health <= 20) {
				Gdx.app.log(RipGame.LOG, "Fall");
				this.fallen = true;
				this.has_fallen = true;
				this.astate = Attack_State.HIT;
				this.stateTime = 0f;
				
				switch (this.dir) {
				case DIR_LEFT:
					this.lucy_animation = this.fallAnimationLeft;
					break;
				case DIR_RIGHT:
					this.lucy_animation = this.fallAnimationRight;
					break;
				default:
					break;
				}
				
				TimePack tp1 = new TimePack((this.x - 30), this.y);
				LevelRenderer.drawables.add(tp1);
				LevelRenderer.timepacks.add(tp1);
				
				TimePack tp2 = new TimePack(((this.x + (int) this.width) + 30), this.y);
				LevelRenderer.drawables.add(tp2);
				LevelRenderer.timepacks.add(tp2);
				
			} else if ((this.x <= attack_point_1 && this.x >= (this.attack_point_1 - 2)) || 
					(this.x <= attack_point_2 && this.x >= (this.attack_point_2 - 2))) {
				this.attacking = true;
				this.stateTime = 0;
				switch (this.dir) {
				case DIR_LEFT:
					this.setX(this.getX() - 3);
					break;
				case DIR_RIGHT:
					this.setX(this.getX() + 3);
					break;
				default:
					break;
				}
				
				switch (this.astate) {
				case SWIPE:
					swipe();
					break;
				case HIT:
					hit();
					break;
				default:
					break;
				}
				
				
			} else if (this.x <= LevelRenderer.camPos) {
				//Gdx.app.log(RipGame.LOG, "Hit Wall Left");
				this.localSPEED = 2;
				this.setX(LevelRenderer.camPos + 1);
				switchDir();
				this.lucy_animation = this.walkAnimationRight;
				this.has_been_seen = true;
				//turn around
				
			} else if (((this.x + this.width) >= (LevelRenderer.camPos + LevelRenderer.width))
					&& this.has_been_seen) {
				//Gdx.app.log(RipGame.LOG, "Hit Wall Right");
				this.localSPEED = -2;
				this.setX(((LevelRenderer.camPos + LevelRenderer.width) -1) - (int) this.width);
				switchDir();
				this.lucy_animation = this.walkAnimationLeft;
				//turn around
			} else if (!this.waiting) {
				this.not_moving = false;
				Gdx.app.log(RipGame.LOG, "Moving Postion");
				Gdx.app.log(RipGame.LOG, Integer.toString(this.x));
				//Gdx.app.log(RipGame.LOG, "-20");
				
				this.setX(this.getX() + this.localSPEED);
				this.leftAttackBox.x += this.localSPEED;
				this.rightAttackBox.x += this.localSPEED;
				this.leftHitableBox.x += this.localSPEED;
				this.rightHitableBox.x += this.localSPEED;
				switch (this.dir) {
				case DIR_LEFT:
					if (Intersector.intersectRectangles(this.leftHitableBox, LevelRenderer.getPlayer().hitableBox)) {
						Gdx.app.log(RipGame.LOG, "IF Left");
						LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - 1);
						LevelRenderer.getPlayer().makeHit();
						LevelRenderer.getPlayer().hitBack(-30);
						//LevelRenderer.getPlayer().setX(LevelRenderer.getPlayer().getX() + this.localSPEED);
					}
					break;
				case DIR_RIGHT:
					if (Intersector.intersectRectangles(this.rightHitableBox, LevelRenderer.getPlayer().hitableBox)) {
						Gdx.app.log(RipGame.LOG, "IF Right");
						LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - 1);
						LevelRenderer.getPlayer().makeHit();
						LevelRenderer.getPlayer().hitBack(30);
						//LevelRenderer.getPlayer().setX(LevelRenderer.getPlayer().getX() + this.localSPEED);
					}
					break;
				default:
					break;
				}
				
			} else {
				this.not_moving = true;
				//Gdx.app.log(RipGame.LOG, "Not Moving");
			}
		//if collides push back?
		//else
		}
	}
	
	public void switchDir() {
		switch(this.dir) {
		case DIR_LEFT:
			this.setDir(dir.DIR_RIGHT);
			this.hitableBox = this.leftHitableBox;
			break;
		case DIR_RIGHT:
			this.setDir(dir.DIR_LEFT);
			this.hitableBox = this.rightHitableBox;
			break;
		default:
			break;
		}
	}
	
	public void hitBack(int distance, ArrayList<Enemy> e) {
		
	}
	
	public void setCurrentFrame(float delta) {
		this.stateTime += delta;
		//this.currentFrame = player_animation.getKeyFrame(stateTime, true);
		if (this.lucy_animation == this.swipeAnimationLeft ||
				this.lucy_animation == this.swipeAnimationRight ||
				this.lucy_animation == this.hitAnimationLeft ||
				this.lucy_animation == this.hitAnimationRight ||
				this.lucy_animation == this.fallAnimationLeft ||
				this.lucy_animation == this.fallAnimationRight) {
			//Gdx.app.log(RipGame.LOG, "setAttack");
			this.currentFrame = this.lucy_animation.getKeyFrame(this.stateTime, false);
		} else {
			this.currentFrame = this.lucy_animation.getKeyFrame(this.stateTime, true);
		}
	}

	public Animation getLucy_animation() {
		return lucy_animation;
	}

	public Sound getRandomNohit_sounds() {
		int index = rand.nextInt(this.no_hit.length);
		return this.no_hit[index];
	}
	
	public void setLucy_animation(Animation lucy_animation) {
		this.lucy_animation = lucy_animation;
	}

	public Animation getWalkAnimationRight() {
		return walkAnimationRight;
	}

	public void setWalkAnimationRight(Animation walkAnimationRight) {
		this.walkAnimationRight = walkAnimationRight;
	}

	public Animation getWalkAnimationLeft() {
		return walkAnimationLeft;
	}

	public void setWalkAnimationLeft(Animation walkAnimationLeft) {
		this.walkAnimationLeft = walkAnimationLeft;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}
	
	public void setX(int x) {
		this.x = x;
		this.bounds.x = x;
		this.hitableBox.x = x + 100;
		this.leftHitableBox.x = x + 100;
		this.rightHitableBox.x = x + 100;
		this.rightAttackBox.x = x + (this.width / 2);
		this.leftAttackBox.x = x;
		
	}

	
	

}
