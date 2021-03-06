package com.rip.screens;

import renderers.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.rip.RipGame;
import com.rip.levels.Level;
import com.rip.levels.Level_1_1;
import com.rip.levels.Level_1_2;
import com.rip.levels.Level_1_3;
import com.rip.levels.Level_1_4;
import com.rip.levels.Level_1_5;
//import com.rip.levels.Level;
import com.rip.levels.TutorialLevel;

public class GameScreen implements Screen {
	RipGame game;
	// abstract once more levels are available
	Level_1_1 level1_1;
	Level level1_2;
	Level level;
	LevelRenderer lr;
	boolean hidden;

	public GameScreen(RipGame game, String l) {
		
		Gdx.app.log(RipGame.LOG, "New Game Screen");
		this.game = game;
		hidden = false;

		if (l == "level1_1") {
			this.level = new Level_1_1(game);
			this.lr = new LevelRenderer(level);
		} else if (l == "level1_2") {
			this.level = new Level_1_2(game);
			this.lr = new LevelRenderer(level);
		} else if (l == "level1_3") {
			this.level = new Level_1_3(game);
			this.lr = new LevelRenderer(level);
		} else if (l == "level1_4") {
			this.level = new Level_1_4(game);
			this.lr = new LevelRenderer(level);
		} else if (l == "level1_5") {
			this.level = new Level_1_5(game);
			this.lr = new LevelRenderer(level);
		} else if (l == "Tutorial Level") {
			this.level = new TutorialLevel(game);
			this.lr = new LevelRenderer(level);
		}

	}

	/*
public GameScreen(RipGame game, int level_num) {
this.game = game;
switch (level_num) {
case 1:
this.level = new Level_1_1(this.game);
break;
default:
break;
}
this.lr = new LevelRender(this.level);
}
	 */

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if (hidden) {
			return;
		}
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
		hidden = true;
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
		Gdx.app.log(RipGame.LOG, "Game Screen Dispose");
		// TODO Auto-generated method stub
		level.dispose();
		lr.dispose();
	}

}


