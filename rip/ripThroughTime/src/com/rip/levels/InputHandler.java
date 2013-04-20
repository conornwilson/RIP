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
import com.rip.objects.Player.Attack_Directions;
import com.rip.objects.Player.attack_state;


public class InputHandler implements InputProcessor {
	Level level;
	Player player;
	ArrayList<Enemy> enemies;
	
	private boolean WAIT;
	boolean miss = true;
	
	int newX;
	int newY;
	
	public InputHandler(Level level) {
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
			if (player.isATTACK_ANIMATION()){
				//public enum attack_state { PUNCH_ONE, PUNCH_TWO, PUNCH_THREE, 
				//KICK_ONE, KICK_TWO, KICK_THREE };
				Gdx.app.log(RipGame.LOG, "Punch C-c-combo!");
				switch (player.getAstate()) {
				case DONE:
					player.prevstate = attack_state.PUNCH_ONE;
					player.setAstate(attack_state.PUNCH_ONE);
					break;
				case PUNCH_ONE:
					player.setAstate(attack_state.PUNCH_TWO);
					break;
				case PUNCH_TWO:
					player.setAstate(attack_state.PUNCH_THREE);
					break;
				case KICK_ONE:
					player.setAstate(attack_state.DONE);
					break;
				case KICK_TWO:
					player.setAstate(attack_state.DONE);
					break;
				
				default:
					player.setAstate(attack_state.DONE);
					break;
				}
				break;
			}
			break;
		case Keys.L:
			if (player.isATTACK_ANIMATION()){
				//public enum attack_state { PUNCH_ONE, PUNCH_TWO, PUNCH_THREE, 
				//KICK_ONE, KICK_TWO, KICK_THREE };
				Gdx.app.log(RipGame.LOG, "Kick C-c-combo!");
				switch (player.getAstate()) {
				case DONE:
					player.prevstate = attack_state.KICK_ONE;
					player.setAstate(attack_state.KICK_ONE);
					break;
				case PUNCH_ONE:
					player.setAstate(attack_state.DONE);
					break;
				case PUNCH_TWO:
					player.setAstate(attack_state.KICK_THREE);
					break;
				case KICK_ONE:
					player.setAstate(attack_state.KICK_THREE);
					break;
				case KICK_TWO:
					player.setAstate(attack_state.DONE);
					break;
				default:
					player.setAstate(attack_state.DONE);
					break;
				}
				break;
			}
			break;
		
		case Keys.W:
			break;
		case Keys.S:
			break;
		case Keys.A:
			//player.setTexture(player.getLEFT());
			if (!player.isATTACK_ANIMATION() && (player.getHealth() > 0)) {
				Gdx.app.log(RipGame.LOG, "IN A");
				player.setDir(Directions.DIR_LEFT);
				player.setAttack_dir(Attack_Directions.DIR_LEFT);
				player.setPlayer_animation(player.getWalkAnimationLeft());
				player.setStateTime(0f);
				player.setCurrentFrame(0f);
			} else {
				player.setDir(Directions.DIR_LEFT);
			}
			break;
		case Keys.D:
			//player.setTexture(player.getRIGHT());
			if (!player.isATTACK_ANIMATION() && (player.getHealth() > 0)) {
				Gdx.app.log(RipGame.LOG, "IN D");
				player.setDir(Directions.DIR_RIGHT);
				player.setAttack_dir(Attack_Directions.DIR_RIGHT);
				player.setPlayer_animation(player.getWalkAnimationRight());
				player.setStateTime(0f);
				player.setCurrentFrame(0f);
			} else {
				player.setDir(Directions.DIR_RIGHT);
			}
			break;
		default:
			player.setAstate(attack_state.DONE);
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
			if (!player.isATTACK_ANIMATION() && (player.getHealth() > 0)) {
				player.setStateTime(0f);	
				player.setCurrentFrame(0f);
			}
			
			break;
		case Keys.D:

			if (!player.isATTACK_ANIMATION() && (player.getHealth() > 0)) {
				player.setStateTime(0f);	
				player.setCurrentFrame(0f);
			}
			
			break;
		case Keys.W:
			if (!player.isATTACK_ANIMATION() && (player.getHealth() > 0)) {
				player.setStateTime(0f);	
				player.setCurrentFrame(0f);
			}
			break;
		case Keys.S:
			if (!player.isATTACK_ANIMATION() && (player.getHealth() > 0)) {
				player.setStateTime(0f);	
				player.setCurrentFrame(0f);
			}
			break;
			
			//add state change
		case Keys.K:
			switch(player.getAttack_dir() ) {
			case DIR_LEFT:
				if (player.isATTACK_ANIMATION()){
					Gdx.app.log(RipGame.LOG, "Punch C-c-combo!");
					break;
				}
				
				//player.setTexture(player.getLEFT());
				player.setPlayer_animation(player.getPunchAnimationLeft());
				player.setStateTime(0f);
				player.setCurrentFrame(0f);
				//player.setATTACK_ANIMATION(true);
				WAIT = true;
				break;
			case DIR_RIGHT:
				if (player.isATTACK_ANIMATION()){
					Gdx.app.log(RipGame.LOG, "Punch C-c-combo!");
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
				player.handlePunch(enemies);
				player.setATTACK_ANIMATION(true);
			}
			break;
			case Keys.L:
				switch(player.getAttack_dir()) {
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
						Gdx.app.log(RipGame.LOG, "Kick C-c-combo!");
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
					player.handleKick(enemies);
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
	
	public void playHitSound(Enemy e) {
		if ((float) Math.random() >= .5) {
			Sound hit = e.gethitSound();
			hit.play(1.0f);
		}
	}
	
}
