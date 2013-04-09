package com.rip.objects;

import renderers.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
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
	
	protected float stateTime = 0f;

	private static final int WALK_COLS = 10;
	private static final int WALK_ROWS = 1;
	
	private static final int swipe_COLS = 15;
	private static final int swipe_ROWS = 1;
	
	private static final int hit_COLS = 15;
	private static final int hit_ROWS = 1;
	
	public enum Attack_State {SWIPE, HIT};
	
	public Attack_State astate;
	
	protected static int SPEED = 2;
	
	public boolean attacking = false;
	

	public Lucy(int x, int y) {
		super(x, y, 275, 380, SPEED, 100);
		create_animations();
		astate = Attack_State.SWIPE;
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

		swipeAnimationRight = new Animation(0.075f, swipeFramesRight);
		swipeAnimationLeft = new Animation(0.075f, swipeFramesLeft);
		
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
	
		if (Intersector.overlapRectangles(this.hitableBox, LevelRenderer.getPlayer().hitableBox)) {
			LevelRenderer.getPlayer().hitBack(30);
			LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - this.swipe_damage);
			// replace this.hitableBox with a swipeBox
		}
		//swipe noise
		
	}
	
	public void hit() {
		if (Intersector.overlapRectangles(this.hitableBox, LevelRenderer.getPlayer().hitableBox)) {
			LevelRenderer.getPlayer().hitBack(30);
			LevelRenderer.getPlayer().setHealth(LevelRenderer.getPlayer().getHealth() - this.hit_damage);
			// replace this.hitableBox with a hitBox
		}
		//thud noise
		// release time-ups?
		// release health?
	}
	
	public void update_movements() {
		if (this.health <= 50) {
			this.astate = Attack_State.HIT;
		}
		
		if (this.attacking) {
		} else {
		
			if (this.x == attack_point_1 || this.x == attack_point_2 ||
					this.x == attack_point_3) {
				this.attacking = true;
				switch (this.dir) {
				case DIR_LEFT:
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
					switch (this.astate) {
					case SWIPE:
						this.lucy_animation = this.swipeAnimationRight;
						swipe();
						break;
					case HIT:
						this.lucy_animation = this.hitAnimationLeft;
						hit();
						break;
					default:
						break;
					}
					break;
				default:
					break;
				
				}
				
			} else if (this.x == LevelRenderer.camPos) {
				this.SPEED *= -1;
				this.lucy_animation = this.walkAnimationRight;
				//turn around
				
			} else if ((this.x + this.width) == (LevelRenderer.camPos + LevelRenderer.width)) {
				this.SPEED *= -1;
				this.lucy_animation = this.walkAnimationLeft;
				//turn around
			} else {
				//move
				this.setX(this.getX() + (int)(this.SPEED * LevelRenderer.delta));
			}
		//if collides push back?
		//else
		}
	}
	
	public void setCurrentFrame(float delta) {
		this.stateTime += delta;
		/*
		if (this.attacking) {
			if ((this.dir == Directions.DIR_LEFT) && !(lucy_animation == attackAnimationLeft)) {
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
			Gdx.app.log(RipGame.LOG, "setAttack");
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
