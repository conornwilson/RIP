package com.rip.screens;

import renderers.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rip.RipGame;

public class LevelSelect implements Screen {

	RipGame game;
	Stage stage;
	BitmapFont black;
	BitmapFont white;
	TextureAtlas atlas;
	Skin skin;
	SpriteBatch batch;
	TextButton level1_1Button;
	TextButton TutorialLevelButton;
	TextButton level1_2Button;
	TextButton level1_3Button;
	TextButton level1_4Button;
	TextButton level1_5Button;
	TextButton returnButton;
	Texture title = new Texture(Gdx.files.internal("data/levelselect.png"));
	
	Music maintheme;

	public LevelSelect(RipGame game) {
		this.game = game;
		Gdx.app.log(RipGame.LOG, "LevelSelect Screen Started");
		maintheme = Gdx.audio.newMusic(Gdx.files.internal("data/Main Menu.mp3"));
		maintheme.setLooping(true);
		maintheme.play();
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);

		batch.begin();
			batch.draw(title, 0, 0);
		batch.end();
		
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {

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
		
		TutorialLevelButton = new TextButton("Tutorial Level", style);
		TutorialLevelButton.setWidth(250);
		TutorialLevelButton.setHeight(75);
		TutorialLevelButton.setX(535);
		TutorialLevelButton.setY(325);

		level1_1Button = new TextButton("Level 1", style);
		level1_1Button.setWidth(250);
		level1_1Button.setHeight(75);
		level1_1Button.setX(410);
		level1_1Button.setY(225);

		level1_2Button = new TextButton("Level 2", style);
		level1_2Button.setWidth(250);
		level1_2Button.setHeight(75);
		level1_2Button.setX(410);
		level1_2Button.setY(125);

		level1_3Button = new TextButton("Level 3", style);
		level1_3Button.setWidth(250);
		level1_3Button.setHeight(75);
		level1_3Button.setX(410);
		level1_3Button.setY(25);

		level1_4Button = new TextButton("Level 4", style);
		level1_4Button.setWidth(250);
		level1_4Button.setHeight(75);
		level1_4Button.setX(685);
		level1_4Button.setY(225);

		level1_5Button = new TextButton("Level 5", style);
		level1_5Button.setWidth(250);
		level1_5Button.setHeight(75);
		level1_5Button.setX(685);
		level1_5Button.setY(125);

		returnButton = new TextButton("Return", style);
		returnButton.setWidth(250);
		returnButton.setHeight(75);
		returnButton.setX(685);
		returnButton.setY(25);
		
		
		TutorialLevelButton.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				TutorialLevelButton.setY(322);
				TutorialLevelButton.setHeight(81);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				TutorialLevelButton.setY(325);
				TutorialLevelButton.setHeight(75);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(RipGame.LOG, "Start Game: pushed");
				maintheme.stop();
//				selectPlay.play();

				game.setScreen(new GameScreen(game, "Tutorial Level"));
			}
		});

		level1_1Button.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				level1_1Button.setY(222);
				level1_1Button.setHeight(81);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				level1_1Button.setY(225);
				level1_1Button.setHeight(75);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(RipGame.LOG, "Start Game: pushed");
//				maintheme.stop();
//				selectPlay.play();
				maintheme.stop();
				//game.setScreen(new Level_1_1Screen(game));
				game.setScreen(new GameScreen(game, "level1_1"));
			}
		});

		level1_2Button.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				level1_2Button.setY(122);
				level1_2Button.setHeight(81);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				level1_2Button.setY(125);
				level1_2Button.setHeight(75);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(RipGame.LOG, "Start Game: pushed");
//				maintheme.stop();
//				selectPlay.play();
				maintheme.stop();
				game.setScreen(new GameScreen(game, "level1_2"));
			}
		});

		level1_3Button.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				level1_3Button.setY(22);
				level1_3Button.setHeight(81);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				level1_3Button.setY(25);
				level1_3Button.setHeight(75);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(RipGame.LOG, "Start Game: pushed");
//				maintheme.stop();
//				selectPlay.play();
				maintheme.stop();
				game.setScreen(new GameScreen(game, "level1_3"));
			}
		});

		level1_4Button.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				level1_4Button.setY(222);
				level1_4Button.setHeight(81);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				level1_4Button.setY(225);
				level1_4Button.setHeight(75);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(RipGame.LOG, "Start Game: pushed");
//				maintheme.stop();
//				selectPlay.play();
				maintheme.stop();
				game.setScreen(new GameScreen(game, "level1_4"));
			}
		});

		level1_5Button.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				level1_5Button.setY(122);
				level1_5Button.setHeight(81);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				level1_5Button.setY(125);
				level1_5Button.setHeight(75);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(RipGame.LOG, "Start Game: pushed");
//				maintheme.stop();
//				selectPlay.play();
				maintheme.stop();
				game.setScreen(new GameScreen(game, "level1_5"));
			}
		});

		returnButton.addListener(new InputListener() {
			public void enter (InputEvent event, float x, float y, int pointer, Actor formActor) {
				returnButton.setY(22);
				returnButton.setHeight(81);
			}
			
			public void exit (InputEvent event, float x, float y, int pointer, Actor formActor) {
				returnButton.setY(25);
				returnButton.setHeight(75);
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(RipGame.LOG, "Start Game: pushed");
//				maintheme.stop();
//				selectPlay.play();
				maintheme.stop();
				game.setScreen(new MainMenu(game));;
			}
		});
		stage.addActor(TutorialLevelButton);
		stage.addActor(level1_1Button);
		stage.addActor(level1_2Button);
		stage.addActor(level1_3Button);
		stage.addActor(level1_4Button);
		stage.addActor(level1_5Button);
		stage.addActor(returnButton);

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
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		white.dispose();
		black.dispose();
		stage.dispose();
		title.dispose();

	}

}