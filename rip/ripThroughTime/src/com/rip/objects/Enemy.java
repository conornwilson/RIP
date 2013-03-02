package com.rip.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import renderers.LevelRender;

public abstract class Enemy extends MovableEntity {

	protected float health;
	protected float damage;
	boolean collides_player;
	
	//collision objects.
	public Intersector in;
	//public Rectangle hitableBox;
	
	public enum Directions { DIR_LEFT, DIR_RIGHT };
	
	Directions dir = Directions.DIR_RIGHT;
	
	public Enemy(int x, int y, float width, float height, Texture texture,
			int SPEED, float health) {
		super(x, y, width, height, SPEED, texture);
		this.health = health;
		collides_player = false;
		//hitableBox = new Rectangle(x, y, width, height);
	}
	
	public Enemy(int x, int y, float width, float height, int SPEED, float health) {
		super(x, y, width, height, SPEED);
		this.health = health;
		collides_player = false;
		//hitableBox = new Rectangle(x, y, width, height);
	}
	
	public void track(Player p) {
		if (this.collides_player) {
			return;
			
		} else {
		
			int pX = p.getX();
			int pY = p.getY();
	
			int dx = pX - x;
			int dy = pY - y;
	
			this.setX(this.getX() + (int)((dx - this.SPEED) * LevelRender.delta));
	
	
			this.setY(this.getY() + (int)((dy - this.SPEED) * LevelRender.delta));
		}
	}

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
