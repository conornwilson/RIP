package com.rip.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.rip.RipGame;

public class IntroScreen implements Screen {
	
	RipGame game;
	SpriteBatch batch;
	Stage stage;
	BitmapFont black;
	BitmapFont white;
	TextureAtlas atlas;
	Skin skin;
	Texture introScript = new Texture("data/introScript.png");
	float introScriptHeight = introScript.getHeight() * -1;
	
	TextButton continueButton;
	Boolean endIntro = false;
	
	public IntroScreen(RipGame game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(introScript, 0, introScriptHeight);	
//			if (endIntro) {
//				if (stage == null) {
//					stage = new Stage(RipGame.WIDTH, RipGame.HEIGHT, true);
//				}
//				stage.clear();
//				Gdx.input.setInputProcessor(stage);
//
//				TextButtonStyle style = new TextButtonStyle();
//				style.up = skin.getDrawable("buttonnormal");
//				style.down = skin.getDrawable("buttonpressed");
//				style.font = black;
//				
//				continueButton = new TextButton("Continue", style);
//				continueButton.setWidth(300);
//				continueButton.setHeight(75);
//				continueButton.setX(Gdx.graphics.getWidth() / 2 + 125);
//				continueButton.setY(Gdx.graphics.getHeight() / 2 - continueButton.getHeight() / 2 - 150);
//				
//				continueButton.addListener(new InputListener() {
//					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//						return true;
//					}
//
//					public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//						Gdx.app.log(RipGame.LOG, "Start Game: pushed");
//						game.setScreen(new GameScreen(game, "tutorialLevel"));
//						dispose();
//					}
//				});
//				stage.addActor(continueButton);
//				stage.act(delta);
//				stage.draw();
//			}
		batch.end();
		
		introScriptHeight += 20f * delta;
		if (introScriptHeight >= RipGame.HEIGHT + 20) {
			endIntro = true;
		}
		
		if (endIntro || Gdx.input.isKeyPressed(Keys.Q)) {
			game.setScreen(new GameScreen(game, "tutorialLevel"));
		}

	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void show() {
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
		
	}

}
