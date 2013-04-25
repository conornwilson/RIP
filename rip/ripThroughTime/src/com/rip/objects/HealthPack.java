package com.rip.objects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;

public class HealthPack extends MovableEntity{
	protected int health_increase = 10;
	protected static float height = 35;
	protected static float width = 34;
	Random rand = new Random();
	static Texture texture = new Texture("data/healthpack.png");
	Sound pickup = Gdx.audio.newSound(Gdx.files.internal("data/Health PickUp.wav"));
	Sound[] vocal_sounds = {Gdx.audio.newSound(Gdx.files.internal("data/Rip Voice_Health Pickup_01.wav")),
            Gdx.audio.newSound(Gdx.files.internal("data/Rip Voice_Health Pickup_02.wav"))};
	
	public HealthPack(int x, int y) {
		super(x, y, width, height, 0, texture);

	}
	
	public boolean collides(Player p){
		if (Intersector.overlapRectangles(this.bounds, p.bounds)) {
			p.setHealth(p.getHealth() + this.health_increase);
			pickup.play();
			if ((float) Math.random() >= .65) {
				Sound v = this.getRandomVocal_sounds();
				v.play(1.0f);
			}
			return true;
		} else {
			return false;
		}
	}


	public Sound getRandomVocal_sounds() {
		int index = rand.nextInt(vocal_sounds.length);
		return vocal_sounds[index];
	}
	

}
