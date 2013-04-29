package com.rip.screens;

import renderers.LevelRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.rip.RipGame;
import com.rip.levels.Level;
import com.rip.levels.Level_1_1;

public class Level_1_1Screen implements Screen {
	Game game;
	Level level;
	LevelRenderer lr;
	
	public Level_1_1Screen(RipGame game) {

		Gdx.app.log(RipGame.LOG, "New Level1 Screen");
		this.game = game;
		this.level = new Level_1_1(game);
		this.lr = new LevelRenderer(level);
	}		

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		level.update();
		if (lr != null) {
			lr.render();
		} else { }
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		dispose();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		lr.dispose();
		level.dispose();
		
	}

}
