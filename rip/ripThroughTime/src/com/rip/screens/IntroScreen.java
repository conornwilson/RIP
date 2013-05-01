package com.rip.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	//TextureAtlas atlas;
	//Skin skin;
	Texture introScript = new Texture("data/introScript.png");
	float introScriptHeight = introScript.getHeight() * -1;
	
	protected Texture eatSheet;
	protected TextureRegion[] eatFrames;
	protected Animation eat;
	protected TextureRegion currentFrame;
	
	private static final int EAT_COLS = 2;
	private static final int EAT_ROWS = 1;
	
	//TextButton continueButton;
	Boolean endIntro = false;
	
	float stateTime;
	
	public IntroScreen(RipGame game) {
		this.game = game;
		create_eat();
		stateTime = 0f;
	}
	
	public void create_eat() {
		TextureRegion temp;
		eatSheet = new Texture(Gdx.files.internal("data/ripeat.png"));
		TextureRegion[][] tmp = TextureRegion.split(eatSheet, eatSheet.getWidth() / EAT_COLS, eatSheet.getHeight() / EAT_ROWS);
		eatFrames = new TextureRegion[EAT_COLS * EAT_ROWS];
		
		int index = 0;
		for (int i = 0; i < EAT_ROWS; i++) {
			for (int j = 0; j < EAT_COLS; j++) {
				temp = tmp[i][j];
				eatFrames[index] = temp;
				//walkFramesLeft[index] = temp;
				index++;
			}
		}
		
		eat = new Animation(1f, eatFrames);
		currentFrame = eat.getKeyFrame(stateTime, true);
	}
	
	public void setCurrentFrame(float delta) {
		this.stateTime += delta;
		currentFrame = eat.getKeyFrame(stateTime, true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		setCurrentFrame(delta);
		
		batch.begin();
		batch.draw(introScript, 0, introScriptHeight);
		batch.draw(currentFrame, 700, 150);
		
		batch.end();
		
		introScriptHeight += 20f * delta;
		if (introScriptHeight >= RipGame.HEIGHT + 20) {
			endIntro = true;
		}
		
		if (endIntro || Gdx.input.isKeyPressed(Keys.Q)) {
			game.setScreen(new GameScreen(game, "Tutorial Level"));
		}

	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
	//	atlas = new TextureAtlas("data//.pack"); //need to create our own button graphic!
		//skin = new Skin();
		//skin.addRegions(atlas);
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
