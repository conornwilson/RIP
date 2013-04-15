package com.rip.objects;

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
	
	protected int attack_point_1 = LevelRenderer.camPos + 230;
	protected int attack_point_2 = LevelRenderer.camPos + 600;
	protected int attack_point_3;
	
	protected int swipe_damage = 20;
	protected int hit_damage = 25;
	
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
	
	boolean has_been_seen = false;
	
	//protected float stateTime = 0f;

	private static final int WALK_COLS = 10;
	private static final int WALK_ROWS = 1;
	
	private static final int swipe_COLS = 15;
	private static final int swipe_ROWS = 1;
	
	private static final int hit_COLS = 15;
	private static final int hit_ROWS = 1;
	
	public enum Attack_State {SWIPE, HIT};
	
	public Attack_State astate;
	
	public int localSPEED = -1;
	
	//protected Rectangle leftAttackBox, rightAttackBox;
	public Rectangle leftHitableBox, rightHitableBox;
	

	public Lucy(int x, int y) {
		super(x, y, 275, 380, -2, 100);
		create_animations();
		astate = Attack_State.SWIPE;
		this.setDir(dir.DIR_LEFT);
		this.hitableBox = new Rectangle(this.x + this.boxset, this.y, (this.width * 0.7f), this.height);
		this.leftHitableBox = new Rectangle(this.x + this.boxset, this.y, ((this.width * 0.7f) / 2), this.height);
		this.rightHitableBox  = new Rectangle(((this.x + this.boxset) + ((this.width * 0.7f) / 2)), this.y, 
					((this.width * 0.7f) / 2), this.height);
		this.leftAttackBox = new Rectangle(this.x, this.y, (this.width / 2), this.height);
		this.rightAttackBox = new Rectangle(this.x + (this.width / 2), this.y, (this.width / 2), this.height);
		Sound s[] = {Gdx.audio.newSound(Gdx.files.internal("data/GorillaGrunt_01.wav")),
				Gdx.audio.newSound(Gdx.files.internal("data/GorillaGrunt_02.wav")),
				Gdx.audio.newSound(Gdx.files.internal("data/GorillaGrunt_03.wav"))};
		this.hit_sounds = s;
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

		swipeAnimationRight = new Animation(0.045f, swipeFramesRight);
		swipeAnimationLeft = new Animation(0.045f, swipeFramesLeft);
		
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

		hitAnimationRight = new Animation(0.075f, hitFramesRight);
		hitAnimationLeft = new Animation(0.075f, hitFramesLeft);
		
		
		lucy_animation = walkAnimationLeft;
		currentFrame = lucy_animation.getKeyFrame(stateTime, true);
	
	}
	
	
	public void swipe() {
		switch (this.dir) {
		case DIR_LEFT:
			
			if (Intersector.overlapRectangles(this.leftAttackBox, LevelRenderer.getPlayer().hitableBox)) {
				Gdx.app.log(RipGame.LOG, "Swipe Left");
				LevelRenderer.getPlayer().makeHit();
				LevelRenderer.getPlayer().hitBack(-60);
				LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - this.swipe_damage);
			}
			
			break;
		case DIR_RIGHT:
			if (Intersector.overlapRectangles(this.rightAttackBox, LevelRenderer.getPlayer().hitableBox)) {
				LevelRenderer.getPlayer().makeHit();
				LevelRenderer.getPlayer().hitBack(60);
				LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - this.swipe_damage);
			}
			break;
		default:
			break;
		}
			// replace this.hitableBox with a swipeBox
		//swipe noise
	}

		

	
	public void hit() {
		switch (this.dir) {
		case DIR_LEFT:
			if (Intersector.overlapRectangles(this.leftAttackBox, LevelRenderer.getPlayer().hitableBox)) {
				LevelRenderer.getPlayer().makeHit();
				LevelRenderer.getPlayer().hitBack(-70);
				LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - this.hit_damage);
			}
			break;
		case DIR_RIGHT:
			if (Intersector.overlapRectangles(this.rightAttackBox, LevelRenderer.getPlayer().hitableBox)) {
				LevelRenderer.getPlayer().makeHit();
				LevelRenderer.getPlayer().hitBack(70);
				LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - this.hit_damage);
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
		if (this.health <= 50) {
			this.astate = Attack_State.HIT;
		}
		
		if (this.attacking) {
			//Gdx.app.log(RipGame.LOG, "Attacking");
			/*
			if (this.lucy_animation.isAnimationFinished(this.getStateTime())) {
				switch (this.dir){
				case DIR_LEFT:
					this.lucy_animation = this.walkAnimationLeft;
					break;
				case DIR_RIGHT:
					this.lucy_animation = this.walkAnimationRight;
					break;
				default:
					break;
				}
			}*/
		} else {
			//need to make it a range
			if ((this.x <= attack_point_1 && this.x >= (this.attack_point_1 - 2)) || 
					(this.x <= attack_point_2 && this.x >= (this.attack_point_2 - 2))) {
				this.attacking = true;
				switch (this.dir) {
				case DIR_LEFT:
					this.setX(this.getX() - 3);
					//if (this.leftAttackBox.overlaps(LevelRenderer.getPlayer().hitableBox)) {
						//Gdx.app.log(RipGame.LOG, "IF Left");
					Gdx.app.log(RipGame.LOG, "Attack Left");
					switch (this.astate) {
					case SWIPE:
						this.lucy_animation = this.swipeAnimationLeft;
						swipe();
						//set animation
						//attack
						break;
					case HIT:
						this.lucy_animation = this.hitAnimationLeft;
						hit();
						break;
					default:
						break;
					}
					break;
				case DIR_RIGHT:
					this.setX(this.getX() + 3);
					Gdx.app.log(RipGame.LOG, "Attack Right");
					switch (this.astate) {
					case SWIPE:
						this.lucy_animation = this.swipeAnimationRight;
						swipe();
						break;
					case HIT:
						this.lucy_animation = this.hitAnimationRight;
						hit();
						break;
					default:
						break;
					}
					break;
				default:
					break;
				
				}
				this.stateTime = 0;
				
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
			} else {
				
				this.setX(this.getX() + this.localSPEED);
				this.leftAttackBox.x += this.localSPEED;
				this.rightAttackBox.x += this.localSPEED;
				this.leftHitableBox.x += this.localSPEED;
				this.rightHitableBox.x += this.localSPEED;
				switch (this.dir) {
				case DIR_LEFT:
					if (Intersector.intersectRectangles(this.leftHitableBox, LevelRenderer.getPlayer().hitableBox)) {
						Gdx.app.log(RipGame.LOG, "IF Left");
						LevelRenderer.getPlayer().makeHit();
						LevelRenderer.getPlayer().hitBack(-30);
						//LevelRenderer.getPlayer().setX(LevelRenderer.getPlayer().getX() + this.localSPEED);
					}
					break;
				case DIR_RIGHT:
					if (Intersector.intersectRectangles(this.rightHitableBox, LevelRenderer.getPlayer().hitableBox)) {
						Gdx.app.log(RipGame.LOG, "IF Right");
						LevelRenderer.getPlayer().makeHit();
						LevelRenderer.getPlayer().hitBack(30);
						//LevelRenderer.getPlayer().setX(LevelRenderer.getPlayer().getX() + this.localSPEED);
					}
					break;
				default:
					break;
				}
				
			}
		//if collides push back?
		//else
		}
	}
	
	public void switchDir() {
		switch(this.dir) {
		case DIR_LEFT:
			this.setDir(dir.DIR_RIGHT);
			break;
		case DIR_RIGHT:
			this.setDir(dir.DIR_LEFT);
			break;
		default:
			break;
		}
	}
	
	public void setCurrentFrame(float delta) {
		this.stateTime += delta;
		/*
		if (this.attacking) {
			if ((this.dir == Directions.DIR_LEFT) && !((lucy_animation == swipeAnimationLeft) ||
						!(lucy_animation == this.hitAnimationLeft))) {
				lucy_animation = attackAnimationLeft;
			} else if ((this.dir == Directions.DIR_RIGHT) && !(lucy_animation == attackAnimationRight)) {
				lucy_animation = attackAnimationRight;
			}
		} else  {
			 if ((this.dir == Directions.DIR_LEFT) && !(lucy_animation == walkAnimationLeft)) {
				lucy_animation = walkAnimationLeft;
			} else if ((this.dir == Directions.DIR_RIGHT) && !(lucy_animation == walkAnimationRight)) {
				lucy_animation = walkAnimationRight;
			}
		}
		*/
		//this.currentFrame = player_animation.getKeyFrame(stateTime, true);
		if (this.lucy_animation == this.swipeAnimationLeft ||
				this.lucy_animation == this.swipeAnimationRight ||
				this.lucy_animation == this.hitAnimationLeft ||
				this.lucy_animation == this.hitAnimationRight) {
			//Gdx.app.log(RipGame.LOG, "setAttack");
			this.currentFrame = this.lucy_animation.getKeyFrame(this.stateTime, false);
		} else {
			this.currentFrame = this.lucy_animation.getKeyFrame(this.stateTime, true);
		}
	}

	public Animation getLucy_animation() {
		return lucy_animation;
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

	
	

}
