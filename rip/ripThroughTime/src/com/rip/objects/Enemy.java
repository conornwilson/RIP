package com.rip.objects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.rip.RipGame;
import com.rip.objects.MovableEntity.Directions;

import renderers.LevelRender;

public abstract class Enemy extends MovableEntity {

	protected float health;
	protected float damage;
	boolean collides_player;
	
	//collision objects.
	public Intersector in;
	//public Rectangle hitableBox;
	
	boolean flank;
	Random r = new Random();
	int trackX;
	int trackY;
	boolean flankPoint1;
	boolean flankPoint2;
	
	public boolean spawnPoint = false;
	
	public enum Directions { DIR_LEFT, DIR_RIGHT };
	
	Directions dir = Directions.DIR_RIGHT;
	
	public Enemy(int x, int y, float width, float height, Texture texture,
			int SPEED, float health) {
		super(x, y, width, height, SPEED, texture);
		this.health = health;
		collides_player = false;
		make_flanks();
		//hitableBox = new Rectangle(x, y, width, height);
	}
	
	public Enemy(int x, int y, float width, float height, int SPEED, float health) {
		super(x, y, width, height, SPEED);
		this.health = health;
		collides_player = false;
		make_flanks();
		//hitableBox = new Rectangle(x, y, width, height);
	}
	
	public void make_flanks() {
		if (r.nextBoolean()) {
			flank = false;
		} else {
			Gdx.app.log(RipGame.LOG, "flank true");
			flank = true;
			trackX = 0;
			trackY = 0;
			flankPoint1 = false;
			flankPoint2 = false;
		}
	}
	
	
	public void track(Player p) {
		if (this.collides_player) {
			return;
			
		} else {
		
			int dx, dy;
	
	//		Gdx.app.log(RipGame.LOG, "flank: " + flank);
	
			if (flank == false) {
				int pX;
				int pY;
				boolean positiveY = r.nextBoolean(); 
				boolean positiveX = r.nextBoolean();
	
				if (positiveX) {
					pX = p.getX() + r.nextInt(50);
				} else {
					pX = p.getX() - r.nextInt(50);
				}
	
				if (positiveY) {
					pY = p.getY() + r.nextInt(50);
				} else {
					pY = p.getY() - r.nextInt(50);
				}
	
				if (pY > 180) { pY = 180; }
	
				dx = pX - x;
				dy = pY - y;
				
				if (dx > 0) {
					dir = Directions.DIR_RIGHT;
				} else if (dx < 0) {
					dir = Directions.DIR_LEFT;
				}
	
				this.setX(this.getX() + (int)((dx - this.SPEED) * LevelRender.delta));
				this.setY(this.getY() + (int)((dy - this.SPEED) * LevelRender.delta));
	
	
			} else {
				if (trackX == 0 && trackY == 0) {
					Gdx.app.log(RipGame.LOG, "flank Initiated");
	
					trackX = p.getX();
					trackY = p.getY() - (r.nextInt(400-200) + 200);
					Gdx.app.log(RipGame.LOG, "trackX: " + trackX + " trackY: " + trackY );
					Gdx.app.log(RipGame.LOG, "playerX: " + p.getX() + " playerY: " + p.getY() );
				} else if (flankPoint1 == false) {
					dx = trackX - x;
					dy = trackY - y;
					this.setX(this.getX() + (int)((dx - this.SPEED + 2) * LevelRender.delta));
					this.setY(this.getY() + (int)((dy - this.SPEED + 2) * LevelRender.delta));
					if (x <= trackX + this.width + 10 && x >= trackX - this.width + 10 && y <= trackY + this.height + 10 && y >= trackY - this.height + 10 ) {
						flankPoint1 = true;
						Gdx.app.log(RipGame.LOG, "flankPoin1 = " + flankPoint1);
						if (spawnPoint) {
							trackX = p.getX() - (r.nextInt(500-300) + 300);
							trackY = p.getY();
						} else {
							trackX = p.getX() + (r.nextInt(500-300) + 300);
							trackY = p.getY();
						}
					}
				} else if (flankPoint2 == false) {
					dx = trackX - x;
					dy = trackY - y;
					setX(getX() + (int)((dx - SPEED + 2) * LevelRender.delta));
					setY(getY() + (int)((dy - SPEED + 2) * LevelRender.delta));
					if (x <= trackX + this.width + 10 && x >= trackX - this.width + 10 && y <= trackY + this.height + 10 && y >= trackY - this.height + 10 ) {
						flankPoint2 = true;
						trackY = p.getY();
						flank = false;
						}
				} 
			}
		}

	}

	
	
	/*
	public void track(Player p) {
		if (this.collides_player) {
			return;
			
		} else {
		
			int pX = p.getX();
			int pY = p.getY();
	
			int dx = pX - x;
			int dy = pY - y;
			
			if (dx > 0) {
				dir = Directions.DIR_RIGHT;
			} else if (dx < 0) {
				dir = Directions.DIR_LEFT;
			}
	
			this.setX(this.getX() + (int)((dx - this.SPEED) * LevelRender.delta));
	
	
			this.setY(this.getY() + (int)((dy - this.SPEED) * LevelRender.delta));
		}
	}
	*/
	
	
	
	
	
	
	public float getDamage() {
		return damage;
	}


	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public Directions getDir() {
		return dir;
	}

	public void setDir(Directions dir) {
		this.dir = dir;
	}

	public boolean isCollides_player() {
		return collides_player;
	}

	public void setCollides_player(boolean collides_player) {
		this.collides_player = collides_player;
	}

	

}
