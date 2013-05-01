package com.rip.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.rip.RipGame;

public class Controls implements Screen {
	
	RipGame game;
	Stage stage;
	BitmapFont black;
	BitmapFont white;
	TextureAtlas atlas;
	Skin skin;
	SpriteBatch batch;
	TextButton ReturnButton;
	
	Texture con = new Texture("data/controls.png");

	
	public Controls(RipGame game) {
		this.game = game;
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);

		batch.begin();
		batch.draw(con, 0, 0);
		batch.end();
		
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		if (stage == null) {
			stage = new Stage(width, height, true);
			Gdx.app.log(RipGame.LOG, "new stage started");
		}
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;
		
		ReturnButton = new TextButton("Return", style);
		ReturnButton.setWidth(150);
		ReturnButton.setHeight(50);
		ReturnButton.setX(800);
		ReturnButton.setY(10);
		
		ReturnButton.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				ReturnButton.setY(7);
				ReturnButton.setHeight(56);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				ReturnButton.setY(10);
				ReturnButton.setHeight(50);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(RipGame.LOG, "Start Game: pushed");
//				maintheme.stop();
//				selectPlay.play();

				game.setScreen(new MainMenu(game));
			}
		});
		
		stage.addActor(ReturnButton);

		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/button.pack"); //need to create our own button graphic!
		skin = new Skin();
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("data/arcadeFontWhite32.fnt"),false);
		black = new BitmapFont(Gdx.files.internal("data/arcadeFontBlack32.fnt"),false);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		this.dispose();
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
		this.con.dispose();
	}

}
