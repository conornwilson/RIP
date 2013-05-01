package com.rip.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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


public class MainMenu implements Screen {

	RipGame game;
	Stage stage;
	BitmapFont black;
	BitmapFont white;
	TextureAtlas atlas;
	Skin skin;
	SpriteBatch batch;
	TextButton startButton;
	TextButton selectButton;
	TextButton controlsButton;
	TextButton creditsButton;
	TextButton quitButton;
	Texture title = new Texture(Gdx.files.internal("data/mainmenu.png"));
	
	boolean start, select, controls, credits, quit;
	
	Music maintheme;
	Music selectPlay;

	public MainMenu(RipGame game) {
		this.game = game;

		maintheme = Gdx.audio.newMusic(Gdx.files.internal("data/Main Menu.mp3"));
		maintheme.setLooping(true);
		maintheme.play();

		selectPlay = Gdx.audio.newMusic(Gdx.files.internal("data/Main Menu Select.mp3"));
		selectPlay.setLooping(false);
		this.start = false;
		this.select = false;
		this.controls = false;
		this.credits = false;
		this.quit = false;

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);

		//maintheme.play();
/*
		if (!this.selectPlay.isPlaying()) {

			if (this.start) {
				game.setScreen(new IntroScreen(game));
			} else if (this.select) {
				game.setScreen(new LevelSelect(game));
			} else if (this.controls) {
				game.setScreen(new Controls(game));
			} else if (this.credits) {

			} else if (this.quit) {
				Gdx.app.exit();
			}
		}*/
		batch.begin();
			//white.draw(batch, "Rip Through Time", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
			batch.draw(title, 0, 0);
			
		batch.end();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null) {
			stage = new Stage(width, 330, true);
		}
		stage.clear();
		Gdx.input.setInputProcessor(stage);


		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;

		startButton = new TextButton("Start Game", style);
		startButton.setWidth(250);
		startButton.setHeight(50);
		startButton.setX(Gdx.graphics.getWidth() / 2 + 125);
		startButton.setY(Gdx.graphics.getHeight() /2 - startButton.getHeight() / 2 + 50); //-10 + 60

		selectButton = new TextButton("Level Select", style);
		selectButton.setWidth(250);
		selectButton.setHeight(50);
		selectButton.setX(Gdx.graphics.getWidth()/2 + 125);
		selectButton.setY(Gdx.graphics.getHeight()/2 - selectButton.getHeight() / 2 - 10);
		
		controlsButton = new TextButton("Controls", style);
		controlsButton.setWidth(250);
		controlsButton.setHeight(50);
		controlsButton.setX(Gdx.graphics.getWidth()/2 + 125);
		controlsButton.setY(Gdx.graphics.getHeight()/2 - controlsButton.getHeight() / 2 - 70);

		
		quitButton = new TextButton("Quit", style);
		quitButton.setWidth(250);
		quitButton.setHeight(50);
		quitButton.setX(Gdx.graphics.getWidth() / 2 + 125);
		quitButton.setY(Gdx.graphics.getHeight() / 2 - quitButton.getHeight() / 2 - 130);

		startButton.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				startButton.setY(startButton.getY() - 1);
				startButton.setHeight(52);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				startButton.setY(Gdx.graphics.getHeight() /2 - startButton.getHeight() / 2 + 50);
				startButton.setHeight(50);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(RipGame.LOG, "Start Game: pushed");
				maintheme.stop();
				//selectPlay.play();
				
				start = true;
				game.setScreen(new IntroScreen(game));
				//game.setScreen(new GameScreen(game, "level1_1"));
			}
		});

		selectButton.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				selectButton.setY(selectButton.getY() - 1);
				selectButton.setHeight(52);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				selectButton.setY(Gdx.graphics.getHeight()/2 - selectButton.getHeight() / 2 - 10);
				selectButton.setHeight(50);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(RipGame.LOG, "select: pushed");
				maintheme.stop();
				//selectPlay.play();
				game.setScreen(new LevelSelect(game));
				select = true;
				
			}
		});
		
		
		controlsButton.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				controlsButton.setY(controlsButton.getY() - 1);
				controlsButton.setHeight(52);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				controlsButton.setY(Gdx.graphics.getHeight()/2 - controlsButton.getHeight() / 2 - 70);
				controlsButton.setHeight(50);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(RipGame.LOG, "controls: pushed");
				maintheme.stop();
				//selectPlay.play();
				controls = true;
				game.setScreen(new Controls(game));
				
			}
		});

		
		
		quitButton.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				quitButton.setY(quitButton.getY() - 1);
				quitButton.setHeight(52);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				quitButton.setY(Gdx.graphics.getHeight() / 2 - quitButton.getHeight() / 2 - 130);
				quitButton.setHeight(50);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				maintheme.stop();
				//selectPlay.play();
				quit = true;
				Gdx.app.log(RipGame.LOG, "Quit: pushed");
				Gdx.app.exit();
				
			}
		});
		
		
		stage.addActor(startButton);
		stage.addActor(selectButton);
		stage.addActor(controlsButton);
		
		stage.addActor(quitButton);
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
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		white.dispose();
		black.dispose();
		stage.dispose();
		title.dispose();

		selectPlay.dispose();
		maintheme.dispose();
	}

}