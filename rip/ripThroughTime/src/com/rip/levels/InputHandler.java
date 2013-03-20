package com.rip.levels;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.rip.RipGame;
import com.rip.objects.MovableEntity.Directions;
import com.rip.objects.Enemy;
import com.rip.objects.Player;


public class InputHandler implements InputProcessor {
	Level_1_1 level;
	Player player;
	ArrayList<Enemy> enemies;
	
	private boolean WAIT;
	
	int newX;
	int newY;
	
	public InputHandler(Level_1_1 level) {
		this.level = level;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		player = level.getPlayer();
		enemies = level.getEnemies();
		//this.ATTACK_ANIMATION = player.isATTACK_ANIMATION();
		switch(keycode){
		case Keys.K:
			break;
		case Keys.L:
			break;
		
		case Keys.W:
			break;
		case Keys.S:
			break;
		case Keys.A:
			//player.setTexture(player.getLEFT());
			if (!player.isATTACK_ANIMATION()) {
				player.setDir(Directions.DIR_LEFT);
				player.setPlayer_animation(player.getWalkAnimationLeft());
				player.setStateTime(0f);
				player.setCurrentFrame(0f);
			}
			break;
		case Keys.D:
			//player.setTexture(player.getRIGHT());
			if (!player.isATTACK_ANIMATION()) {
				player.setDir(Directions.DIR_RIGHT);
				player.setPlayer_animation(player.getWalkAnimationRight());
				player.setStateTime(0f);
				player.setCurrentFrame(0f);
			}
			break;
		default:
			break;
		}
		return true;
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		player = level.getPlayer();
		switch(keycode) {
		case Keys.A:
			/*
			if (player.getPlayer_animation() != player.getWalkAnimationLeft()) {
				player.setPlayer_animation(player.getWalkAnimationLeft());
			}
			//player.setPlayer_animation(player.getWalkAnimationLeft());
			 */
			if (!player.isATTACK_ANIMATION()) {
				player.setStateTime(0f);	
				player.setCurrentFrame(0f);
			}
			
			break;
		case Keys.D:

			if (!player.isATTACK_ANIMATION()) {
				player.setStateTime(0f);	
				player.setCurrentFrame(0f);
			}
			
			break;
		case Keys.W:
			if (!player.isATTACK_ANIMATION()) {
				player.setStateTime(0f);	
				player.setCurrentFrame(0f);
			}
			break;
		case Keys.S:
			if (!player.isATTACK_ANIMATION()) {
				player.setStateTime(0f);	
				player.setCurrentFrame(0f);
			}
			break;
		case Keys.K:
			switch(player.getDir()) {
			case DIR_LEFT:
				if (player.isATTACK_ANIMATION()){
					break;
				}
				
				//player.setTexture(player.getLEFT());
				player.setPlayer_animation(player.getPunchAnimationLeft());
				player.setStateTime(0f);
				player.setCurrentFrame(0f);
				//player.setATTACK_ANIMATION(true);
				WAIT = true;
				/*
				while (WAIT) {
					continue;
				}
				
				// original code
				player.setPlayer_animation(player.getWalkAnimationLeft());
				player.setStateTime(0f);
				player.setCurrentFrame(0f);
				*/
				break;
			case DIR_RIGHT:
				if (player.isATTACK_ANIMATION()){
					break;
				}
				//player.setTexture(player.getRIGHT());
				player.setPlayer_animation(player.getPunchAnimationRight());
				player.setStateTime(0f);
				player.setCurrentFrame(0f);
				//player.setATTACK_ANIMATION(true);
				WAIT = true;
				break;
			default:
				break;
			}
			
			//punching action
			if (player.isATTACK_ANIMATION()){
				break;
			} else {
				for (int i = 0; i < enemies.size(); i++) {
					Enemy e = enemies.get(i);
					if (player.punches(e.hitableBox)) {
						//Gdx.app.log(RipGame.LOG, "Punch");
						Sound punch = player.getRandomPunch_sounds();
						punch.play(1.0f);
						Sound hit = e.gethitSound();
						hit.play(1.0f);
						e.setHealth(e.getHealth() - player.getPunch_damage());
						
						// Cause enemy to be pushed back
						switch(player.getDir()) {
						case DIR_LEFT:
							e.setX(e.getX() - 50);
							break;
						case DIR_RIGHT:
							e.setX(e.getX() + 50);
							break;
						default:
							break;
						}
					}
				}
				player.setATTACK_ANIMATION(true);
			}
			break;
			case Keys.L:
				switch(player.getDir()) {
				case DIR_LEFT:
					if (player.isATTACK_ANIMATION()){
						break;
					}
					//player.setTexture(player.getLEFT());
					player.setPlayer_animation(player.getKickAnimationLeft());
					player.setStateTime(0f);
					player.setCurrentFrame(0f);
					//player.setATTACK_ANIMATION(true);
					WAIT = true;
					break;
				case DIR_RIGHT:
					if (player.isATTACK_ANIMATION()){
						break;
					}
					//player.setTexture(player.getRIGHT());
					player.setPlayer_animation(player.getKickAnimationRight());
					player.setStateTime(0f);
					player.setCurrentFrame(0f);
					//player.setATTACK_ANIMATION(true);
					WAIT = true;
					break;
				default:
					break;
				}
				
				//punching action
				if (player.isATTACK_ANIMATION()){
					break;
				} else {
					for (int i = 0; i < enemies.size(); i++) {
						Enemy e = enemies.get(i);
						if (player.punches(e.hitableBox)) {
							//Gdx.app.log(RipGame.LOG, "Punch");
							Sound kick = player.getRandomKick_sounds();
							kick.play(1.0f);
							Sound hit = e.gethitSound();
							hit.play(1.0f);
							e.setHealth(e.getHealth() - player.getKick_damage());
							
							// Cause enemy to be pushed back
							switch(player.getDir()) {
							case DIR_LEFT:
								e.setX(e.getX() - 50);
								break;
							case DIR_RIGHT:
								e.setX(e.getX() + 50);
								break;
							default:
								break;
							}
						}
					}
					player.setATTACK_ANIMATION(true);
				}
				break;
		default:
			break;
		}
		return true;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isWAIT() {
		return WAIT;
	}

	public void setWAIT(boolean wAIT) {
		WAIT = wAIT;
	}
	
}
