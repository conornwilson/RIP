
package com.rip.objects;



import java.util.ArrayList;
import java.util.Random;

import renderers.LevelRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.rip.RipGame;
import com.rip.levels.Level;
import com.rip.objects.MovableEntity.Directions;
import com.rip.screens.MainMenu;

public class Player extends MovableEntity {

	Texture punch;
	float health;
	float totalHealth = 100;
	float punch_damage;
	float kick_damage;
	float time;
	boolean timeFreeze;
	
	public boolean dead = false;
	
	boolean miss = true;
	
	public boolean hit = false;
	public boolean scripted = false;
	public boolean ULT = false;

	boolean ATTACK_ANIMATION = false;
	
	public enum attack_state { PUNCH_ONE, PUNCH_TWO, PUNCH_THREE, 
			KICK_ONE, KICK_TWO, KICK_THREE, DONE };
	public enum Attack_Directions { DIR_LEFT, DIR_RIGHT };
	Attack_Directions attack_dir = Attack_Directions.DIR_RIGHT;
			
	public attack_state astate = attack_state.DONE;
	public attack_state prevstate = attack_state.DONE;

	//The player class has several animations
	////At any given time, the renderer can only 
	////access the current frame from the current
	////animation
	protected Animation player_animation;
	protected TextureRegion currentFrame;
	protected float stateTime = 0f;

	//Walk Animation
	private static final int WALK_COLS = 5;
	private static final int WALK_ROWS = 1;

	protected Animation walkAnimationRight;
	protected Animation walkAnimationLeft;
	protected Texture walkSheet;
	protected TextureRegion[] walkFramesRight;
	protected TextureRegion[] walkFramesLeft;
	protected TextureRegion currentwalkFrame;

    float walkTime = 0f;
    
    //Walk Animation
    private static final int ULT_COLS = 1;
    private static final int ULT_ROWS = 13;

    protected Animation ULTAnimationRight;
    protected Animation ULTAnimationLeft;
    protected Texture ULTSheet;
    protected TextureRegion[] ULTFramesRight;
    protected TextureRegion[] ULTFramesLeft;
    protected TextureRegion currentULTFrame;

    float ULTTime = 0f;
    
	//Kick Animation
    private static final int KICK_COLS = 7;
	private static final int KICK_ROWS = 1;

	protected Animation kickAnimationRight;
	protected Animation kickAnimationLeft;
	protected Texture kickSheet;
	protected TextureRegion[] kickFramesRight;
	protected TextureRegion[] kickFramesLeft;
	protected TextureRegion currentkickFrame;

    float kickTime = 0f;
    
    //Kick Animation 3
    private static final int KICK_COLS3 = 8;
	private static final int KICK_ROWS3 = 1;

	protected Animation kick3AnimationRight;
	protected Animation kick3AnimationLeft;
	protected Texture kick3Sheet;
	protected TextureRegion[] kick3FramesRight;
	protected TextureRegion[] kick3FramesLeft;
	protected TextureRegion currentkick3Frame;

    float kick3Time = 0f;

    //Hit Animation
    private static final int HIT_COLS = 3;
	private static final int HIT_ROWS = 1;
	
	protected Animation hitAnimationRight;
	protected Animation hitAnimationLeft;
	protected Texture hitSheet;
	protected TextureRegion[] hitFramesRight;
	protected TextureRegion[] hitFramesLeft;
	
    float hitTime = 0f;

	//Punch Animation
	private static final int PUNCH_COLS = 5;
	private static final int PUNCH_ROWS = 1;

	protected Animation punchAnimationRight;
	protected Animation punchAnimationLeft;
	protected Texture punchSheet;
	protected TextureRegion[] punchFramesRight;
	protected TextureRegion[] punchFramesLeft;
	protected TextureRegion currentpunchFrame;

    float punchTime = 0f;
    
    //Punch Animation 2
  	private static final int PUNCH_COLS2 = 5;
  	private static final int PUNCH_ROWS2 = 1;

  	protected Animation punch2AnimationRight;
  	protected Animation punch2AnimationLeft;
  	protected Texture punch2Sheet;
  	protected TextureRegion[] punch2FramesRight;
  	protected TextureRegion[] punch2FramesLeft;
  	protected TextureRegion currentpunch2Frame;

     float punch2Time = 0f;
      
    //Punch Animation 3
	private static final int PUNCH_COLS3 = 4;
	private static final int PUNCH_ROWS3 = 1;

	protected Animation punch3AnimationRight;
	protected Animation punch3AnimationLeft;
	protected Texture punch3Sheet;
	protected TextureRegion[] punch3FramesRight;
	protected TextureRegion[] punch3FramesLeft;
	protected TextureRegion currentpunch3Frame;

    float punch3Time = 0f;
    
    //Death Animation 
	private static final int DEATH_COLS = 12;
	private static final int DEATH_ROWS = 1;

	protected Animation DEATHAnimationRight;
	protected Animation DEATHAnimationLeft;
	protected Texture DEATHSheet;
	protected TextureRegion[] DEATHFramesRight;
	protected TextureRegion[] DEATHFramesLeft;
	protected TextureRegion currentDEATHFrame;

    float DEATHTime = 0f;
    

	Random rand = new Random();
	
	public int preultX, preultY;

	// Remove as soon as sprite sheets are available
	protected final Texture PUNCH_RIGHT = new Texture("data/RIP_PUNCH_RIGHT.png");
	protected final Texture PUNCH_LEFT = new Texture("data/RIP_PUNCH_LEFT.png");
	protected final static Texture RIGHT = new Texture("data/RIP_RIGHT.png");
	protected final Texture LEFT = new Texture("data/RIP_LEFT.png");

	Sound[] punch_sounds = {Gdx.audio.newSound(Gdx.files.internal("data/Cartoon Punches_01.wav")),
	                        Gdx.audio.newSound(Gdx.files.internal("data/Cartoon Punches_02.wav")),
	                        Gdx.audio.newSound(Gdx.files.internal("data/Cartoon Punches_03.wav")),
	                        Gdx.audio.newSound(Gdx.files.internal("data/Cartoon Punches_04.wav"))};
	
	Sound[] hit_sounds = {Gdx.audio.newSound(Gdx.files.internal("data/Rip Voice_Grunt_01.wav")),
            Gdx.audio.newSound(Gdx.files.internal("data/Rip Voice_Grunt_02.wav")),
            Gdx.audio.newSound(Gdx.files.internal("data/Rip Voice_Grunt_03.wav"))};
	
	Sound[] punch_miss_sounds = {Gdx.audio.newSound(Gdx.files.internal("data/No Hit_Punch_01.wav")),
			Gdx.audio.newSound(Gdx.files.internal("data/No Hit_Punch_02.wav")),
			Gdx.audio.newSound(Gdx.files.internal("data/No Hit_Punch_Uppercut.wav"))};
	
	Sound[] kick_miss_sounds = {Gdx.audio.newSound(Gdx.files.internal("data/No Hit_Kick_01.wav")),
			Gdx.audio.newSound(Gdx.files.internal("data/No Hit_Kick_02.wav"))};
	
	Sound[] kick_sounds = {Gdx.audio.newSound(Gdx.files.internal("data/Cartoon Kicks_01.wav")),
            Gdx.audio.newSound(Gdx.files.internal("data/Cartoon Kicks_02.wav"))};

	//collision objects.
//	public Intersector in = new Intersector();
//	public Rectangle hitableBox = new Rectangle(this.getX() + this.getHeight() / 2,this.getY() + this.getWidth() / 2,this.getWidth() / 2,this.getHeight()/2);
	public Rectangle punchBoxRight = new Rectangle(this.getX() + (this.width / 2), (this.getY() + (this.height / 2)), (this.width / 2), (this.height / 2) - 30);
	public Rectangle punchBoxLeft =  new Rectangle(this.getX(), (this.getY() + (this.height / 2)), (this.width / 2), (this.height / 2) - 30);

	//public Rectangle punchBoxRight = new Rectangle(this.getX() + 60, this.getY() + (this.height / 2), 65, (this.height / 2));
	//public Rectangle punchBoxLeft =  new Rectangle(this.getX() + 10, this.getY() + (this.height / 2), 50, (this.height / 2));


	public Player(int x, int y, float width, float height, int SPEED, Texture text) {
		super(x, y, width, height, SPEED, text);
		this.health = 100;
		this.punch_damage = 10;
		this.kick_damage = 15;
		CreateAnimations();
		hitableBox = new Rectangle(this.x + boxset, 
				this.y + (height/2), (width * 0.7f), (height / 2));

	}

	public Player(int x, int y) {
		super(x, y, 128, 163, 3, RIGHT);
		this.health = 100;
		this.punch_damage = 10;
		this.kick_damage = 15;
		CreateAnimations();
		hitableBox = new Rectangle(this.x + boxset, 
				this.y + (height/2), (width * 0.7f), (height / 2));

	}

	public void CreateAnimations() {
		int index;

		//Initiate Walk Animation
		TextureRegion temp;
		walkSheet = new Texture(Gdx.files.internal("data/rip_walk.png"));
		TextureRegion[][] tmpwRight = TextureRegion.split(walkSheet, walkSheet.getWidth() / WALK_COLS, walkSheet.getHeight() / WALK_ROWS);
		TextureRegion[][] tmpwLeft = TextureRegion.split(walkSheet, walkSheet.getWidth() / WALK_COLS, walkSheet.getHeight() / WALK_ROWS);
		walkFramesRight = new TextureRegion[WALK_COLS * WALK_ROWS];
		walkFramesLeft = new TextureRegion[WALK_COLS * WALK_ROWS];
		index = 0;
		for (int i = 0; i < WALK_ROWS; i++) {
			for (int j = 0; j < WALK_COLS; j++) {
				temp = tmpwRight[i][j];
				walkFramesRight[index] = temp;
				//walkFramesLeft[index] = temp;
				index++;
			}
		}


		index = 0;
		for (int i = 0; i < WALK_ROWS; i++) {
			for (int j = 0; j < WALK_COLS; j++) {
				temp = tmpwLeft[i][j];
				walkFramesLeft[index] = temp;
				walkFramesLeft[index].flip(true, false);
				index++;
			}
		}
		/*
		for (TextureRegion region : walkFramesLeft) {
			region.flip(true, false);
		}
		*/
		walkAnimationRight = new Animation(0.1f, walkFramesRight);
		walkAnimationLeft = new Animation(0.1f, walkFramesLeft);

		//Initiate Kick Animation
		kickSheet = new Texture(Gdx.files.internal("data/rip_kick.png"));
		TextureRegion[][] tmpkRight = TextureRegion.split(kickSheet, kickSheet.getWidth() / KICK_COLS, kickSheet.getHeight() / KICK_ROWS);
		TextureRegion[][] tmpkLeft = TextureRegion.split(kickSheet, kickSheet.getWidth() / KICK_COLS, kickSheet.getHeight() / KICK_ROWS);
		kickFramesRight = new TextureRegion[KICK_COLS * KICK_ROWS];
		kickFramesLeft = new TextureRegion[KICK_COLS * KICK_ROWS];
		index = 0;
		for (int i = 0; i < KICK_ROWS; i++) {
			for (int j = 0; j < KICK_COLS; j++) {
				kickFramesRight[index++] = tmpkRight[i][j];
			}
		}

		index = 0;
		for (int i = 0; i < KICK_ROWS; i++) {
			for (int j = 0; j < KICK_COLS; j++) {
				kickFramesLeft[index] = tmpkLeft[i][j];
				kickFramesLeft[index].flip(true, false);
				index++;
			}
		}
		kickAnimationRight = new Animation(0.05f, kickFramesRight);
		kickAnimationLeft = new Animation(0.05f, kickFramesLeft);

		//Initiate Kick3 Animation
		kick3Sheet = new Texture(Gdx.files.internal("data/rip_kick3.png"));
		TextureRegion[][] tmpk3Right = TextureRegion.split(kick3Sheet, kick3Sheet.getWidth() / KICK_COLS3, kick3Sheet.getHeight() / KICK_ROWS3);
		TextureRegion[][] tmpk3Left = TextureRegion.split(kick3Sheet, kick3Sheet.getWidth() / KICK_COLS3, kick3Sheet.getHeight() / KICK_ROWS3);
		kick3FramesRight = new TextureRegion[KICK_COLS3 * KICK_ROWS3];
		kick3FramesLeft = new TextureRegion[KICK_COLS3 * KICK_ROWS3];
		index = 0;
		for (int i = 0; i < KICK_ROWS3; i++) {
			for (int j = 0; j < KICK_COLS3; j++) {
				kick3FramesRight[index++] = tmpk3Right[i][j];
			}
		}

		index = 0;
		for (int i = 0; i < KICK_ROWS3; i++) {
			for (int j = 0; j < KICK_COLS3; j++) {
				kick3FramesLeft[index] = tmpk3Left[i][j];
				kick3FramesLeft[index].flip(true, false);
				index++;
			}
		}
		kick3AnimationRight = new Animation(0.05f, kick3FramesRight);
		kick3AnimationLeft = new Animation(0.05f, kick3FramesLeft);

		//Initiate Hit Animation
		hitSheet = new Texture(Gdx.files.internal("data/riphit.png"));
		TextureRegion[][] tmphRight = TextureRegion.split(hitSheet, hitSheet.getWidth() / HIT_COLS, hitSheet.getHeight() / HIT_ROWS);
		TextureRegion[][] tmphLeft = TextureRegion.split(hitSheet, hitSheet.getWidth() / HIT_COLS, hitSheet.getHeight() / HIT_ROWS);
		hitFramesRight = new TextureRegion[HIT_COLS * HIT_ROWS];
		hitFramesLeft = new TextureRegion[HIT_COLS * HIT_ROWS];
		index = 0;
		for (int i = 0; i < HIT_ROWS; i++) {
			for (int j = 0; j < HIT_COLS; j++) {
				hitFramesRight[index++] = tmphRight[i][j];
			}
		}
		
		index = 0;
		for (int i = 0; i < HIT_ROWS; i++) {
			for (int j = 0; j < HIT_COLS; j++) {
				hitFramesLeft[index] = tmphLeft[i][j];
				hitFramesLeft[index].flip(true, false);
				index++;
			}
		}
		hitAnimationRight = new Animation(0.05f, hitFramesRight);
		hitAnimationLeft = new Animation(0.05f, hitFramesLeft);
		
		//Initiate Punch Animation
		punchSheet = new Texture(Gdx.files.internal("data/rip_punch.png"));
		TextureRegion[][] tmppRight = TextureRegion.split(punchSheet, punchSheet.getWidth() / PUNCH_COLS, punchSheet.getHeight() / PUNCH_ROWS);
		TextureRegion[][] tmppLeft = TextureRegion.split(punchSheet, punchSheet.getWidth() / PUNCH_COLS, punchSheet.getHeight() / PUNCH_ROWS);
		punchFramesRight = new TextureRegion[PUNCH_COLS * PUNCH_ROWS];
		punchFramesLeft = new TextureRegion[PUNCH_COLS * PUNCH_ROWS];
		index = 0;
		for (int i = 0; i < PUNCH_ROWS; i++) {
			for (int j = 0; j < PUNCH_COLS; j++) {
				punchFramesRight[index++] = tmppRight[i][j];
			}
		}

		index = 0;
		for (int i = 0; i < PUNCH_ROWS; i++) {
			for (int j = 0; j < PUNCH_COLS; j++) {
				punchFramesLeft[index] = tmppLeft[i][j];
				punchFramesLeft[index].flip(true, false);
				index++;
			}
		}
		punchAnimationRight = new Animation(0.08f, punchFramesRight);
		punchAnimationLeft = new Animation(0.08f, punchFramesLeft);


		//Initiate Punch Animation 2
		punch2Sheet = new Texture(Gdx.files.internal("data/rip_punch2.png"));
		TextureRegion[][] tmpp2Right = TextureRegion.split(punch2Sheet, punch2Sheet.getWidth() / PUNCH_COLS2, punch2Sheet.getHeight() / PUNCH_ROWS2);
		TextureRegion[][] tmpp2Left = TextureRegion.split(punch2Sheet, punch2Sheet.getWidth() / PUNCH_COLS2, punch2Sheet.getHeight() / PUNCH_ROWS2);
		punch2FramesRight = new TextureRegion[PUNCH_COLS2 * PUNCH_ROWS2];
		punch2FramesLeft = new TextureRegion[PUNCH_COLS2 * PUNCH_ROWS2];
		index = 0;
		for (int i = 0; i < PUNCH_ROWS2; i++) {
			for (int j = 0; j < PUNCH_COLS2; j++) {
				punch2FramesRight[index++] = tmpp2Right[i][j];
			}
		}

		index = 0;
		for (int i = 0; i < PUNCH_ROWS2; i++) {
			for (int j = 0; j < PUNCH_COLS2; j++) {
				punch2FramesLeft[index] = tmpp2Left[i][j];
				punch2FramesLeft[index].flip(true, false);
				index++;
			}
		}
		punch2AnimationRight = new Animation(0.08f, punch2FramesRight);
		punch2AnimationLeft = new Animation(0.08f, punch2FramesLeft);
		
		//Initiate Punch Animation 3
		punch3Sheet = new Texture(Gdx.files.internal("data/rip_punch3.png"));
		TextureRegion[][] tmpp3Right = TextureRegion.split(punch3Sheet, punch3Sheet.getWidth() / PUNCH_COLS3, punch3Sheet.getHeight() / PUNCH_ROWS3);
		TextureRegion[][] tmpp3Left = TextureRegion.split(punch3Sheet, punch3Sheet.getWidth() / PUNCH_COLS3, punch3Sheet.getHeight() / PUNCH_ROWS3);
		punch3FramesRight = new TextureRegion[PUNCH_COLS3 * PUNCH_ROWS3];
		punch3FramesLeft = new TextureRegion[PUNCH_COLS3 * PUNCH_ROWS3];
		index = 0;
		for (int i = 0; i < PUNCH_ROWS3; i++) {
			for (int j = 0; j < PUNCH_COLS3; j++) {
				punch3FramesRight[index++] = tmpp3Right[i][j];
			}
		}

		index = 0;
		for (int i = 0; i < PUNCH_ROWS3; i++) {
			for (int j = 0; j < PUNCH_COLS3; j++) {
				punch3FramesLeft[index] = tmpp3Left[i][j];
				punch3FramesLeft[index].flip(true, false);
				index++;
			}
		}
		punch3AnimationRight = new Animation(0.07f, punch3FramesRight);
		punch3AnimationLeft = new Animation(0.07f, punch3FramesLeft);

		
		//Initiate DEATH Animation
		DEATHSheet = new Texture(Gdx.files.internal("data/ripfall.png"));
		TextureRegion[][] tmpdRight = TextureRegion.split(DEATHSheet, DEATHSheet.getWidth() / DEATH_COLS, DEATHSheet.getHeight() / DEATH_ROWS);
		TextureRegion[][] tmpdLeft = TextureRegion.split(DEATHSheet, DEATHSheet.getWidth() / DEATH_COLS, DEATHSheet.getHeight() / DEATH_ROWS);
		DEATHFramesRight = new TextureRegion[DEATH_COLS * DEATH_ROWS];
		DEATHFramesLeft = new TextureRegion[DEATH_COLS * DEATH_ROWS];
		index = 0;
		for (int i = 0; i < DEATH_ROWS; i++) {
			for (int j = 0; j < DEATH_COLS; j++) {
				DEATHFramesRight[index++] = tmpdRight[i][j];
			}
		}

		index = 0;
		for (int i = 0; i < DEATH_ROWS; i++) {
			for (int j = 0; j < DEATH_COLS; j++) {
				DEATHFramesLeft[index] = tmpdLeft[i][j];
				DEATHFramesLeft[index].flip(true, false);
				index++;
			}
		}
		DEATHAnimationRight = new Animation(0.08f, DEATHFramesRight);
		DEATHAnimationLeft = new Animation(0.08f, DEATHFramesLeft);
		
		//Initiate ULTIMATE Animation
		ULTSheet = new Texture(Gdx.files.internal("data/ripaultimate.png"));
		TextureRegion[][] tmpuRight = TextureRegion.split(ULTSheet, ULTSheet.getWidth() / ULT_COLS, ULTSheet.getHeight() / ULT_ROWS);
		TextureRegion[][] tmpuLeft = TextureRegion.split(ULTSheet, ULTSheet.getWidth() / ULT_COLS, ULTSheet.getHeight() / ULT_ROWS);
		ULTFramesRight = new TextureRegion[ULT_COLS * ULT_ROWS];
		ULTFramesLeft = new TextureRegion[ULT_COLS * ULT_ROWS];
		index = 0;
		for (int i = 0; i < ULT_ROWS; i++) {
			for (int j = 0; j < ULT_COLS; j++) {
				ULTFramesRight[index++] = tmpuRight[i][j];
			}
		}

		index = 0;
		for (int i = 0; i < ULT_ROWS; i++) {
			for (int j = 0; j < ULT_COLS; j++) {
				ULTFramesLeft[index] = tmpuLeft[i][j];
				ULTFramesLeft[index].flip(true, false);
				index++;
			}
		}
		ULTAnimationRight = new Animation(0.08f, ULTFramesRight);
		ULTAnimationLeft = new Animation(0.08f, ULTFramesLeft);

		//Set player_animation
		player_animation = walkAnimationRight;
		currentFrame = player_animation.getKeyFrame(stateTime, true);
	}



	public float getHealth() {
		return health;
	}

	

	public void setHealth(float health) {
		this.health = health;
	}

	public boolean getTimeFreeze() {
		return timeFreeze;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public void flipTimeFreeze() {
		if (timeFreeze == false) {
			timeFreeze = true;
		} else {
			timeFreeze = false;
		}
	}


	public float getPunch_damage() {
		return punch_damage;
	}

	public void setPunch_damage(float punch_damage) {
		this.punch_damage = punch_damage;
	}

	public float getKick_damage() {
		return kick_damage;
	}

	public void setKick_damage(float kick_damage) {
		this.kick_damage = kick_damage;
	}

	
	
	public Attack_Directions getAttack_dir() {
		return attack_dir;
	}

	public void setAttack_dir(Attack_Directions attack_dir) {
		this.attack_dir = attack_dir;
	}

	public Texture getPunch() {
		return punch;
	}
	
	public float getTotalHealth() {
		return this.totalHealth;
	}


	public void setPunch(Texture punch) {
		this.punch = punch;
	}


	public Directions getDir() {
		return dir;
	}


	public void setDir(Directions dir) {
		this.dir = dir;
	}


	public void update() {

		bounds.x = this.x;
		bounds.y = this.y;

	}

	public Rectangle getPunchBoxRight() {
		return punchBoxRight;
	}

	public Rectangle getPunchBoxLeft() {
		return punchBoxLeft;
	}

	public void setPunchBoxRight(Rectangle punchBox) {
		this.punchBoxRight = punchBox;
	}

	public void setPunchBoxLeft(Rectangle punchBox) {
		this.punchBoxLeft = punchBox;
	}

	public boolean isHit(Rectangle attacker) {
		return Intersector.intersectRectangles(hitableBox, attacker);
	}
	
	public void makeHit() {
		if (this.health > 0) { 
			this.hit = true;
			switch (this.dir) {
			case DIR_LEFT:
				this.player_animation = this.hitAnimationLeft;
				break;
			case DIR_RIGHT:
				this.player_animation = this.hitAnimationRight;
				break;
			default:
				break;
			}
			this.stateTime = 0f;
		}
	}

	public boolean punches(Rectangle attacker) {
		switch (this.getDir()) {
			case DIR_LEFT:
				return this.punchBoxLeft.overlaps(attacker);
			case DIR_RIGHT:
				return this.punchBoxRight.overlaps(attacker);
			default:
				return false;
		}
	}


	public boolean[] collides(ArrayList<Enemy> me) {
		//Create array of booleans in which each boolean corresponds with a direction
		// i.e. [<UP>, <DOWN>, <LEFT>, <RIGHT>]
		boolean[] c = {false, false, false, false};
		//boolean[] c = {true, true, true, true};
		
		for (int i = 0; i < me.size(); i++) {
			Enemy m = me.get(i);
			
			if (Intersector.overlapRectangles(this.hitableBox, m.hitableBox)) {
			//if (this.hitableBox.overlaps(m.hitableBox)) {
				m.setCollides_player(true);
				//Is Left occupied?
				if ((this.hitableBox.x <= (m.hitableBox.x + m.hitableBox.width)) && (this.hitableBox.x >= m.hitableBox.x)) {
					c[2] = true;
				}
				//Is Down occupied
				if ((this.hitableBox.y <= (m.hitableBox.y + m.hitableBox.height)) && (this.hitableBox.y >= m.hitableBox.y)) {
					c[1] = true;
				}
				//Is Right occupied?
				if (((this.hitableBox.x + this.hitableBox.width) >= m.hitableBox.x) && (m.hitableBox.x >= this.hitableBox.x)){
					c[3] = true;
				}
				// Is Up occupied?
				if (((this.hitableBox.y + this.hitableBox.height) >= m.hitableBox.y) && (m.hitableBox.y >= this.hitableBox.y)){
					c[0] = true;
				}
			} else {
				m.setCollides_player(false);
			}
			
		}
		
		return c;
	}


	// Remove once spritesheet becomes available

	public Texture getPUNCH_RIGHT() {
		return PUNCH_RIGHT;
	}

	public Texture getPUNCH_LEFT() {
		return PUNCH_LEFT;
	}

	public Texture getRIGHT() {
		return RIGHT;
	}

	public Texture getLEFT() {
		return LEFT;
	}

	public void setX(int x) {
		this.x = x;
		this.bounds.x = x;
		this.hitableBox.x = x + this.getBoxset();
		this.punchBoxRight.x = x + (width / 2);
		this.punchBoxLeft.x = x; //+ 10; 

	}

	public void setY(int y) {
		this.y = y;
		this.bounds.y = y;
		this.hitableBox.y = y + (height / 2);
		this.punchBoxLeft.y = y + (height / 2);
		this.punchBoxRight.y = y + (height / 2);
	}



	public static Texture getRight() {
		return RIGHT;
	}


	public Animation getPlayer_animation() {
		return player_animation;
	}

	public void setPlayer_animation(Animation player_animation) {
		this.player_animation = player_animation;
	}

	public void setCurrentFrame(float delta) {
		this.stateTime += delta;
		//this.currentFrame = player_animation.getKeyFrame(stateTime, true);

		if (player_animation == this.kickAnimationLeft || player_animation == this.kickAnimationRight
			|| player_animation == this.punchAnimationLeft || player_animation == this.punchAnimationRight
			|| player_animation == this.punch2AnimationRight || player_animation == this.punch2AnimationLeft
			|| player_animation == this.punch3AnimationRight || player_animation == this.punch3AnimationLeft
			|| player_animation == this.kick3AnimationLeft || player_animation == this.kick3AnimationRight ||
			player_animation == this.DEATHAnimationLeft || player_animation == this.DEATHAnimationRight ||
			player_animation == this.ULTAnimationLeft || player_animation == this.ULTAnimationRight) {
			this.currentFrame = player_animation.getKeyFrame(stateTime, false);
		} else {
			this.currentFrame = player_animation.getKeyFrame(stateTime, true);
		}

	}

	public void setCurrentFrame(TextureRegion currentFrame) {
		this.currentFrame = currentFrame;
	}
	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public Animation getWalkAnimationRight() {
		return walkAnimationRight;
	}

	public void setWalkAnimationRight(Animation walkAnimationRight) {
		this.walkAnimationRight = walkAnimationRight;
	}

	public Animation getWalkAnimationLeft() {
		return walkAnimationLeft;
	}

	public void setWalkAnimationLeft(Animation walkAnimationLeft) {
		this.walkAnimationLeft = walkAnimationLeft;
	}

	public Animation getKickAnimationRight() {
		return kickAnimationRight;
	}

	public void setKickAnimationRight(Animation kickAnimationRight) {
		this.kickAnimationRight = kickAnimationRight;
	}

	public Animation getKickAnimationLeft() {
		return kickAnimationLeft;
	}

	public void setKickAnimationLeft(Animation kickAnimationLeft) {
		this.kickAnimationLeft = kickAnimationLeft;
	}

	public Animation getPunchAnimationRight() {
		return punchAnimationRight;
	}

	public void setPunchAnimationRight(Animation punchAnimationRight) {
		this.punchAnimationRight = punchAnimationRight;
	}

	public Animation getPunchAnimationLeft() {
		return punchAnimationLeft;
	}

	public void setPunchAnimationLeft(Animation punchAnimationLeft) {
		this.punchAnimationLeft = punchAnimationLeft;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public boolean isATTACK_ANIMATION() {
		return ATTACK_ANIMATION;
	}

	public void setATTACK_ANIMATION(boolean aTTACK_ANIMATION) {
		ATTACK_ANIMATION = aTTACK_ANIMATION;
	}
	

	public attack_state getAstate() {
		return astate;
	}

	public void setAstate(attack_state astate) {
		this.astate = astate;
	}

	public void handleMovement(LevelRenderer lr, Level level, RipGame game) {
		boolean[] c = collides(LevelRenderer.enemy_list);
		
		if (level.isEnd() || this.dead) {
			if (Gdx.input.isKeyPressed(Keys.ENTER)){
				
				//game.getScreen().dispose();
				game.setScreen(new MainMenu(game));
			}
		}

		if (this.isATTACK_ANIMATION()) {
			
			this.setCurrentFrame(LevelRenderer.delta);
			level.getIn().setWAIT(true);
			if (this.getPlayer_animation().isAnimationFinished(this.getStateTime())) {
				this.hit = false;
				for (int i = 0; i < LevelRenderer.enemy_list.size(); i++) {
					Enemy e = LevelRenderer.enemy_list.get(i);
						if (e.getHealth() <= 0)	{
							
							LevelRenderer.enemy_list.remove(i);
							if (e.HealthDrop) {
								HealthPack hp = e.getHealthDrop();
								LevelRenderer.drawables.add(hp);
								LevelRenderer.healthpacks.add(hp);
							}
							LevelRenderer.drawables.remove(e);
						}
				} 
				if (this.astate == attack_state.DONE || this.astate == this.prevstate) {
					this.astate = attack_state.DONE;
					this.prevstate = attack_state.DONE;
					Gdx.app.log(RipGame.LOG, "Done");
					this.ATTACK_ANIMATION = false;
					//this.setATTACK_ANIMATION(false);
					level.getIn().setWAIT(false);
					switch(this.getDir()){
						case DIR_LEFT:
							this.setAttack_dir(Attack_Directions.DIR_LEFT);
							this.setPlayer_animation(this.getWalkAnimationLeft());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
						case DIR_RIGHT:
							this.setAttack_dir(Attack_Directions.DIR_RIGHT);
							this.setPlayer_animation(this.getWalkAnimationRight());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
					}
						
					Gdx.app.log(RipGame.LOG, "ANIMATION OVER");
				} else {
					Gdx.app.log(RipGame.LOG, "Combo");
					switch(this.astate) {
					case PUNCH_ONE:
						//this.setAstate(attack_state.DONE);
						this.prevstate = this.astate;
						switch(this.getAttack_dir()){
						case DIR_LEFT:
							this.setPlayer_animation(this.getPunchAnimationLeft());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
						case DIR_RIGHT:
							this.setPlayer_animation(this.getPunchAnimationRight());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
						}
						this.handlePunch(level.getEnemies());
						break;
					case KICK_ONE:
						this.prevstate = this.astate;
						this.setAstate(attack_state.DONE);
						switch(this.getAttack_dir()){
						case DIR_LEFT:
							this.setPlayer_animation(this.getKickAnimationLeft());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
						case DIR_RIGHT:
							this.setPlayer_animation(this.getKickAnimationRight());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
						}
						this.handleKick(level.getEnemies());
						break;
					case PUNCH_TWO:
						//this.setAstate(attack_state.DONE);
						this.prevstate = this.astate;
						switch(this.getAttack_dir()){
						case DIR_LEFT:
							this.setPlayer_animation(this.getPunch2AnimationLeft());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
						case DIR_RIGHT:
							this.setPlayer_animation(this.getPunch2AnimationRight());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
						}
						this.handlePunch(level.getEnemies());
						break;
					case KICK_TWO:
						//this.setAstate(attack_state.DONE);
						break;
					case PUNCH_THREE:
						this.prevstate = this.astate;
						this.setAstate(attack_state.DONE);
						switch(this.getAttack_dir()){
						case DIR_LEFT:
							this.setPlayer_animation(this.getPunch3AnimationLeft());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
						case DIR_RIGHT:
							this.setPlayer_animation(this.getPunch3AnimationRight());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
						}
						this.handlePunch(level.getEnemies());
						break;
					case KICK_THREE:
						this.prevstate = this.astate;
						this.setAstate(attack_state.DONE);
						switch(this.getAttack_dir()){
						case DIR_LEFT:
							this.setPlayer_animation(this.getKick3AnimationLeft());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
						case DIR_RIGHT:
							this.setPlayer_animation(this.getKick3AnimationRight());
							this.setStateTime(0f);
							this.setCurrentFrame(0f);
							break;
						}
						this.handleKick(level.getEnemies());
						break;
					default:
						break;
					}
				}
			}
		} else if (this.hit) {
			Gdx.app.log(RipGame.LOG, "Hit!");
			this.setCurrentFrame(LevelRenderer.delta);
			if (this.getPlayer_animation().isAnimationFinished(this.getStateTime())) {
				this.hit = false;
				switch(this.getDir()){
				case DIR_LEFT:
					this.setPlayer_animation(this.getWalkAnimationLeft());
					this.setStateTime(0f);
					this.setCurrentFrame(0f);
					break;
				case DIR_RIGHT:
					this.setPlayer_animation(this.getWalkAnimationRight());
					this.setStateTime(0f);
					this.setCurrentFrame(0f);
					break;
				}
			}
		} else if (this.scripted) {
		} else if (this.ULT) {
			if (this.getPlayer_animation().isAnimationFinished(this.getStateTime())) {
				this.resetUltXY();
				//handle damage
				float udamage = 100f / LevelRenderer.enemy_list.size();
				for (int i = 0; i < LevelRenderer.enemy_list.size(); i++) {
					Enemy e = LevelRenderer.enemy_list.get(i);
					if (e.x >= this.x && e.x <= (this.x + 960)) {
						e.setHealth(e.getHealth() - udamage);
					}
				}
			} else {
				setCurrentFrame(LevelRenderer.delta);
			}
		} else if (this.health <= 0) {
			Gdx.app.log(RipGame.LOG, "Death!");
			if (this.player_animation != this.DEATHAnimationLeft && this.player_animation != this.DEATHAnimationRight) {
				Gdx.app.log(RipGame.LOG, "Set Death Animation!");
				this.stateTime = 0f;
				this.width = 237;
				this.height = 222;
				switch (this.dir) {
				case DIR_LEFT:
					this.player_animation = this.DEATHAnimationLeft;
					break;
				case DIR_RIGHT:
					this.player_animation = this.DEATHAnimationRight;
					break;
				default:
					break;
				}
			} else if (this.getPlayer_animation().isAnimationFinished(this.getStateTime())) {
				this.dead = true;
			} else {
				
				setCurrentFrame(LevelRenderer.delta);
				if (this.x > LevelRenderer.camPos && (this.x + this.width) < (LevelRenderer.camPos + LevelRenderer.width)) {
					Gdx.app.log(RipGame.LOG, "Death on the move!");
					switch (this.dir) {
					case DIR_LEFT:
						this.x += 2;
						break;
					case DIR_RIGHT:
						this.x -= 2;
						break;
					default:
						break;
					}
				}
			}
		} else if (this.dead) {
			
		} else {
			
			
			if(Gdx.input.isKeyPressed(Keys.A) && !c[2] && (getDir() == Directions.DIR_LEFT)) {
				if (getX() > LevelRenderer.camPos) {
					setX((getX() - getSPEED()));
					this.setCurrentFrame(LevelRenderer.delta);
				}
			}
	
			else if(Gdx.input.isKeyPressed(Keys.D) && !c[3] && (getDir() == Directions.DIR_RIGHT)) { 
	
				if (getX() + getWidth() < LevelRenderer.camPos + RipGame.WIDTH) {
					setX((getX() + getSPEED()));
					setCurrentFrame(LevelRenderer.delta);
				}
	
				if (LevelRenderer.move && (getX()) - LevelRenderer.camPos > 450) {
					//moves camera along level. 
					LevelRenderer.cam.translate(3, 0);
					LevelRenderer.camPos += 3;
					//background parallax
					level.parallax();
				}
	
			}
			else if(Gdx.input.isKeyPressed(Keys.W) && !c[0]) {
				if (getY() >= LevelRenderer.Y_LIMIT) {
					setY(getY());
	
				} else {
					setY(getY() + 2);
				}
				setCurrentFrame(LevelRenderer.delta);
			}
			else if(Gdx.input.isKeyPressed(Keys.S) && !c[1]) { 
				if (getY() <= 0) {
					setY(getY());
				} else {
					setY(getY() - 2);
				}
				setCurrentFrame(LevelRenderer.delta);
			}
		}
	}
	
	
	public void handlePunch(ArrayList<Enemy> enemies) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if (this.punches(e.hitableBox)) {
				miss = false;
				//Gdx.app.log(RipGame.LOG, "Punch");
				Sound punch = this.getRandomPunch_sounds();
				punch.play(1.0f);
				playHitSound(e);
				e.setHealth(e.getHealth() - this.getPunch_damage());
				
				// Cause enemy to be pushed back
				switch(this.getAttack_dir()) {
				case DIR_LEFT:
					e.hitBack(-50, enemies);
					//e.setX(e.getX() - 50);
					break;
				case DIR_RIGHT:
					e.hitBack(50, enemies);
					//e.setX(e.getX() + 50);
					break;
				default:
					break;
				} 
			} 
		}
		if (miss) {
			Sound miss = this.getPunchRandomMiss_sounds();
			miss.play(1.0f);
			
		} else {
			miss = true;
		}
	}
	
	public void handleKick(ArrayList<Enemy> enemies) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if (this.punches(e.hitableBox)) {
				miss = false;
				//Gdx.app.log(RipGame.LOG, "Punch");
				Sound kick = this.getRandomKick_sounds();
				kick.play(1.0f);
				playHitSound(e);
				e.setHealth(e.getHealth() - this.getKick_damage());
				
				// Cause enemy to be pushed back
				switch(this.getAttack_dir()) {
				case DIR_LEFT:
					e.hitBack(-50, enemies);
					//e.setX(e.getX() - 50);
					break;
				case DIR_RIGHT:
					e.hitBack(50, enemies);
					//e.setX(e.getX() + 50);
					break;
				default:
					break;
				} 
			}
		} if (miss) {
			Sound miss = this.getRandomKick_sounds();
			miss.play(1.0f);
			
		} else {
			miss = true;
		}
	}
	
	public Sound getRandomPunch_sounds() {
		int index = rand.nextInt(punch_sounds.length);
		return punch_sounds[index];
	}
	
	public Sound getRandomKick_sounds() {
		int index = rand.nextInt(kick_sounds.length);
		return kick_sounds[index];
	}
	
	public Sound getPunchRandomMiss_sounds() {
		int index = rand.nextInt(punch_miss_sounds.length);
		return punch_miss_sounds[index];
	}
	
	public Sound getKickRandomMiss_sounds() {
		int index = rand.nextInt(kick_miss_sounds.length);
		return kick_miss_sounds[index];
	}
	
	public Sound getRandomHit_sounds() {
		int index = rand.nextInt(hit_sounds.length);
		return hit_sounds[index];
	}
	
	
	
	public Animation getKick3AnimationRight() {
		return kick3AnimationRight;
	}

	public void setKick3AnimationRight(Animation kick3AnimationRight) {
		this.kick3AnimationRight = kick3AnimationRight;
	}

	public Animation getKick3AnimationLeft() {
		return kick3AnimationLeft;
	}

	public void setKick3AnimationLeft(Animation kick3AnimationLeft) {
		this.kick3AnimationLeft = kick3AnimationLeft;
	}

	public Animation getPunch2AnimationRight() {
		return punch2AnimationRight;
	}

	public void setPunch2AnimationRight(Animation punch2AnimationRight) {
		this.punch2AnimationRight = punch2AnimationRight;
	}

	public Animation getPunch2AnimationLeft() {
		return punch2AnimationLeft;
	}

	public void setPunch2AnimationLeft(Animation punch2AnimationLeft) {
		this.punch2AnimationLeft = punch2AnimationLeft;
	}

	public Animation getPunch3AnimationRight() {
		return punch3AnimationRight;
	}

	public void setPunch3AnimationRight(Animation punch3AnimationRight) {
		this.punch3AnimationRight = punch3AnimationRight;
	}

	public Animation getPunch3AnimationLeft() {
		return punch3AnimationLeft;
	}

	public void setPunch3AnimationLeft(Animation punch3AnimationLeft) {
		this.punch3AnimationLeft = punch3AnimationLeft;
	}
	
	public void playHitSound(Enemy e) {
		if ((float) Math.random() >= .5) {
			Sound hit = e.gethitSound();
			hit.play(1.0f);
		}
	}
	
	
	
	public void hitBack(int distance) {
		Sound h = this.getRandomHit_sounds();
		h.play(1.0f);
		if (distance == 0) {
			return;
		} else if (((distance < 0) && (this.getX() >= LevelRenderer.camPos + 5)) || 
				((distance > 0) && ((this.getX() + this.width) <= ((LevelRenderer.camPos + LevelRenderer.width) - 5)))){
			this.setX(this.getX() + distance);
		}
	}

	public void hitBack(int distance, ArrayList<Enemy> e) {
		Sound h = this.getRandomHit_sounds();
		h.play(1.0f);
		if (distance == 0) {
			return;
		}
		// make sure you can't be moved off screen
		
		if (((distance < 0) && (this.getX() >= LevelRenderer.camPos + 5)) || 
				((distance > 0) && ((this.getX() + this.width) <= ((LevelRenderer.camPos + LevelRenderer.width) - 5)))){
			this.setX(this.getX() + distance);
			//distance += 10;
			
			for (Enemy m : e) {
				// reverse this
				if (Intersector.overlapRectangles(this.hitableBox, m.hitableBox)) {
					if (distance < 0) {
						if (((this.hitableBox.x + this.hitableBox.width) >= m.hitableBox.x) && (m.hitableBox.x >= this.hitableBox.x)){
							m.hitBack(distance / 2, e);
							break;
						}
							
					} else {
						if ((this.hitableBox.x <= (m.hitableBox.x + m.hitableBox.width)) && (this.hitableBox.x >= m.hitableBox.x)) {
							m.hitBack(distance / 2, e);
							break;
						}
					}
				}
				
			}
		}
		
		this.stateTime = 0f;
		switch (this.getDir()) {
		case DIR_LEFT:
			this.player_animation = this.hitAnimationLeft;
			break;
		case DIR_RIGHT:
			this.player_animation = this.hitAnimationRight;
			break;
		default:
			break;
		}
		this.ATTACK_ANIMATION = false;
		this.hit = true;
		return;
	}

	public void handleTime(LevelRenderer lr, Level level, RipGame game) {
		if (getTime() <= 100) {
			setTime(getTime() + (2 * LevelRenderer.delta));
		} else if (getTime() > 100) {
			setTime(100f);
		}

		if (getTimeFreeze() == true && getTime() <= 0) {
			flipTimeFreeze();
		}

		if (Gdx.input.isKeyPressed(Keys.SPACE) && getTime() == 100) {
			this.time = 0f;
			this.setUltXY();
			//ultimate
		} else if (getTimeFreeze() == true && getTime() > 0) {
			setTime(getTime() - (25 * LevelRenderer.delta));
		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && Gdx.input.isKeyPressed(Keys.A) && getTime() > 0 && getX() > LevelRenderer.camPos && getTimeFreeze() == false) {
			setTime(getTime() - (100 * LevelRenderer.delta));
			setX(getX() - 20);
		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && Gdx.input.isKeyPressed(Keys.D) && getTime() > 0 && getX() < LevelRenderer.camPos + RipGame.WIDTH - getWidth() && getTimeFreeze() == false) {
			setTime(getTime() - (100 * LevelRenderer.delta));
			setX(getX() + 20);
		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && Gdx.input.isKeyPressed(Keys.W) && getTime() > 0 && getX() < LevelRenderer.camPos + RipGame.WIDTH - getWidth() && getTimeFreeze() == false) {
			setTime(getTime() - (100 * LevelRenderer.delta));
			setY(getY() + 10);
		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && Gdx.input.isKeyPressed(Keys.S) && getTime() > 0 && getX() < LevelRenderer.camPos + RipGame.WIDTH - getWidth() && getTimeFreeze() == false) {
			setTime(getTime() - (100 * LevelRenderer.delta));
			setY(getY() - 10);
		} else {
			for (int i = 0; i < LevelRenderer.drawables.size(); i++) {
				MovableEntity me = LevelRenderer.drawables.get(i);
				if ((me instanceof Player) || (me instanceof HealthPack) || (me instanceof TimePack)) {
					continue;
				}
				else if (me instanceof Lucy) {
					((Lucy) me).update_movements(); 
				} else  {
					Enemy e = (Enemy) me;
					e.track(this, LevelRenderer.enemy_list);
				}
			}
		}

	}
	
	public void setUltXY() {
		this.preultX = this.x;
		this.preultY = this.y;
		this.x = this.x - 412;
		this.y = this.y - 9;
		this.ULT = true;
		
		Sound u = Gdx.audio.newSound(Gdx.files.internal("data/Time Ult_01.wav"));
		u.play();
		
		this.stateTime = 0f;
		switch (this.dir) {
		case DIR_LEFT:
			this.player_animation = this.ULTAnimationLeft;
			break;
		case DIR_RIGHT:
			this.player_animation = this.ULTAnimationRight;
			break;
		default:
			break;
		}

	}

	public void resetUltXY() {
		this.ULT = false;
		this.x = this.preultX;
		this.y = this.preultY;
		
		this.stateTime = 0f;
		switch (this.dir) {
		case DIR_LEFT:
			this.player_animation = this.walkAnimationLeft;
			break;
		case DIR_RIGHT:
			this.player_animation = this.walkAnimationRight;
			break;
		default:
			break;
		}
		setCurrentFrame(LevelRenderer.delta);
	}
		

}



/*package com.rip.objects;



import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.rip.RipGame;


public class Player extends MovableEntity {

	Texture punch;
	float health;
	float punch_damage;
	float kick_damage;
	float time;
	boolean timeFreeze;
	public boolean hit = false;
	
	boolean ATTACK_ANIMATION = false;
	
	//The player class has several animations
	////At any given time, the renderer can only 
	////access the current frame from the current
	////animation
	protected Animation player_animation;
	protected TextureRegion currentFrame;
	protected float stateTime = 0f;
	
	//Walk Animation
	private static final int WALK_COLS = 5;
	private static final int WALK_ROWS = 1;
	
	protected Animation walkAnimationRight;
	protected Animation walkAnimationLeft;
	protected Texture walkSheet;
	protected TextureRegion[] walkFramesRight;
	protected TextureRegion[] walkFramesLeft;
	protected TextureRegion currentwalkFrame;
	
    float walkTime = 0f;
    
	//Kick Animation
    private static final int KICK_COLS = 7;
	private static final int KICK_ROWS = 1;
	
	protected Animation kickAnimationRight;
	protected Animation kickAnimationLeft;
	protected Texture kickSheet;
	protected TextureRegion[] kickFramesRight;
	protected TextureRegion[] kickFramesLeft;
	protected TextureRegion currentkickFrame;
	
    float kickTime = 0f;
	
	//Hit Animation
    private static final int HIT_COLS = 3;
	private static final int HIT_ROWS = 1;
	
	protected Animation hitAnimationRight;
	protected Animation hitAnimationLeft;
	protected Texture hitSheet;
	protected TextureRegion[] hitFramesRight;
	protected TextureRegion[] hitFramesLeft;
	
    float hitTime = 0f;
	
	//Punch Animation
	private static final int PUNCH_COLS = 5;
	private static final int PUNCH_ROWS = 1;
	
	protected Animation punchAnimationRight;
	protected Animation punchAnimationLeft;
	protected Texture punchSheet;
	protected TextureRegion[] punchFramesRight;
	protected TextureRegion[] punchFramesLeft;
	protected TextureRegion currentpunchFrame;
	
    float punchTime = 0f;
    
	Random rand = new Random();
	
	// Remove as soon as sprite sheets are available
	protected final Texture PUNCH_RIGHT = new Texture("data/RIP_PUNCH_RIGHT.png");
	protected final Texture PUNCH_LEFT = new Texture("data/RIP_PUNCH_LEFT.png");
	protected final static Texture RIGHT = new Texture("data/RIP_RIGHT.png");
	protected final Texture LEFT = new Texture("data/RIP_LEFT.png");
	
	Sound[] punch_sounds = {Gdx.audio.newSound(Gdx.files.internal("data/CartoonPunches_01.wav")),
	                        Gdx.audio.newSound(Gdx.files.internal("data/CartoonPunches_02.wav")),
	                        Gdx.audio.newSound(Gdx.files.internal("data/CartoonPunches_03.wav")),
	                        Gdx.audio.newSound(Gdx.files.internal("data/CartoonPunches_03.wav"))};
	
	Sound[] miss_sounds = {Gdx.audio.newSound(Gdx.files.internal("data/No Hit_01.wav")),
							Gdx.audio.newSound(Gdx.files.internal("data/No Hit_02.wav")),
							Gdx.audio.newSound(Gdx.files.internal("data/No Hit_03.wav")),
							Gdx.audio.newSound(Gdx.files.internal("data/No Hit_04.wav")),
							Gdx.audio.newSound(Gdx.files.internal("data/No Hit_05.wav"))};
	
	Sound[] kick_sounds = {Gdx.audio.newSound(Gdx.files.internal("data/Cartoon Kicks_01.wav")),
            Gdx.audio.newSound(Gdx.files.internal("data/Cartoon Kicks_02.wav"))};
	
	//collision objects.
//	public Intersector in = new Intersector();
//	public Rectangle hitableBox = new Rectangle(this.getX() + this.getHeight() / 2,this.getY() + this.getWidth() / 2,this.getWidth() / 2,this.getHeight()/2);
	public Rectangle punchBoxRight = new Rectangle(this.getX() + (this.width / 2), (this.getY() + (this.height / 2)), (this.width / 2), (this.height / 2) - 30);
	public Rectangle punchBoxLeft =  new Rectangle(this.getX(), (this.getY() + (this.height / 2)), (this.width / 2), (this.height / 2) - 30);
	
	//public Rectangle punchBoxRight = new Rectangle(this.getX() + 60, this.getY() + (this.height / 2), 65, (this.height / 2));
	//public Rectangle punchBoxLeft =  new Rectangle(this.getX() + 10, this.getY() + (this.height / 2), 50, (this.height / 2));
	
	
	public Player(int x, int y, float width, float height, int SPEED, Texture text) {
		super(x, y, width, height, SPEED, text);
		this.health = 100f;
		this.punch_damage = 10;
		this.kick_damage = 15;
		CreateAnimations();
	}
	
	public Player(int x, int y) {
		super(x, y, 128, 163, 3, RIGHT);
		this.health = 100f;
		this.punch_damage = 10;
		this.kick_damage = 15;
		CreateAnimations();
	}
	
	public void CreateAnimations() {
		int index;
		
		//Initiate Walk Animation
		TextureRegion temp;
		walkSheet = new Texture(Gdx.files.internal("data/rip_walk.png"));
		TextureRegion[][] tmpwRight = TextureRegion.split(walkSheet, walkSheet.getWidth() / WALK_COLS, walkSheet.getHeight() / WALK_ROWS);
		TextureRegion[][] tmpwLeft = TextureRegion.split(walkSheet, walkSheet.getWidth() / WALK_COLS, walkSheet.getHeight() / WALK_ROWS);
		walkFramesRight = new TextureRegion[WALK_COLS * WALK_ROWS];
		walkFramesLeft = new TextureRegion[WALK_COLS * WALK_ROWS];
		index = 0;
		for (int i = 0; i < WALK_ROWS; i++) {
			for (int j = 0; j < WALK_COLS; j++) {
				temp = tmpwRight[i][j];
				walkFramesRight[index] = temp;
				//walkFramesLeft[index] = temp;
				index++;
			}
		}
		
		
		index = 0;
		for (int i = 0; i < WALK_ROWS; i++) {
			for (int j = 0; j < WALK_COLS; j++) {
				temp = tmpwLeft[i][j];
				walkFramesLeft[index] = temp;
				walkFramesLeft[index].flip(true, false);
				index++;
			}
		}
		
		for (TextureRegion region : walkFramesLeft) {
			region.flip(true, false);
		}
		
		walkAnimationRight = new Animation(0.1f, walkFramesRight);
		walkAnimationLeft = new Animation(0.1f, walkFramesLeft);
		
		//Initiate Kick Animation
		kickSheet = new Texture(Gdx.files.internal("data/rip_kick.png"));
		TextureRegion[][] tmpkRight = TextureRegion.split(kickSheet, kickSheet.getWidth() / KICK_COLS, kickSheet.getHeight() / KICK_ROWS);
		TextureRegion[][] tmpkLeft = TextureRegion.split(kickSheet, kickSheet.getWidth() / KICK_COLS, kickSheet.getHeight() / KICK_ROWS);
		kickFramesRight = new TextureRegion[KICK_COLS * KICK_ROWS];
		kickFramesLeft = new TextureRegion[KICK_COLS * KICK_ROWS];
		index = 0;
		for (int i = 0; i < KICK_ROWS; i++) {
			for (int j = 0; j < KICK_COLS; j++) {
				kickFramesRight[index++] = tmpkRight[i][j];
			}
		}
		
		index = 0;
		for (int i = 0; i < KICK_ROWS; i++) {
			for (int j = 0; j < KICK_COLS; j++) {
				kickFramesLeft[index] = tmpkLeft[i][j];
				kickFramesLeft[index].flip(true, false);
				index++;
			}
		}
		kickAnimationRight = new Animation(0.05f, kickFramesRight);
		kickAnimationLeft = new Animation(0.05f, kickFramesLeft);
		
		
		//Initiate Hit Animation
		hitSheet = new Texture(Gdx.files.internal("data/riphit.png"));
		TextureRegion[][] tmphRight = TextureRegion.split(hitSheet, hitSheet.getWidth() / HIT_COLS, hitSheet.getHeight() / HIT_ROWS);
		TextureRegion[][] tmphLeft = TextureRegion.split(hitSheet, hitSheet.getWidth() / HIT_COLS, hitSheet.getHeight() / HIT_ROWS);
		hitFramesRight = new TextureRegion[HIT_COLS * HIT_ROWS];
		hitFramesLeft = new TextureRegion[HIT_COLS * HIT_ROWS];
		index = 0;
		for (int i = 0; i < HIT_ROWS; i++) {
			for (int j = 0; j < HIT_COLS; j++) {
				hitFramesRight[index++] = tmphRight[i][j];
			}
		}
		
		index = 0;
		for (int i = 0; i < HIT_ROWS; i++) {
			for (int j = 0; j < HIT_COLS; j++) {
				hitFramesLeft[index] = tmphLeft[i][j];
				hitFramesLeft[index].flip(true, false);
				index++;
			}
		}
		hitAnimationRight = new Animation(0.05f, hitFramesRight);
		hitAnimationLeft = new Animation(0.05f, hitFramesLeft);
		
		
		//Initiate Punch Animation
		punchSheet = new Texture(Gdx.files.internal("data/rip_punch.png"));
		TextureRegion[][] tmppRight = TextureRegion.split(punchSheet, punchSheet.getWidth() / PUNCH_COLS, punchSheet.getHeight() / PUNCH_ROWS);
		TextureRegion[][] tmppLeft = TextureRegion.split(punchSheet, punchSheet.getWidth() / PUNCH_COLS, punchSheet.getHeight() / PUNCH_ROWS);
		punchFramesRight = new TextureRegion[PUNCH_COLS * PUNCH_ROWS];
		punchFramesLeft = new TextureRegion[PUNCH_COLS * PUNCH_ROWS];
		index = 0;
		for (int i = 0; i < PUNCH_ROWS; i++) {
			for (int j = 0; j < PUNCH_COLS; j++) {
				punchFramesRight[index++] = tmppRight[i][j];
			}
		}
		
		index = 0;
		for (int i = 0; i < PUNCH_ROWS; i++) {
			for (int j = 0; j < PUNCH_COLS; j++) {
				punchFramesLeft[index] = tmppLeft[i][j];
				punchFramesLeft[index].flip(true, false);
				index++;
			}
		}
		punchAnimationRight = new Animation(0.05f, punchFramesRight);
		punchAnimationLeft = new Animation(0.05f, punchFramesLeft);
		
		
		//Set player_animation
		player_animation = walkAnimationRight;
		currentFrame = player_animation.getKeyFrame(stateTime, true);
		
	}
	
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public boolean getTimeFreeze() {
		return timeFreeze;
	}
	
	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public void flipTimeFreeze() {
		if (timeFreeze == false) {
			timeFreeze = true;
		} else {
			timeFreeze = false;
		}
	}


	public float getPunch_damage() {
		return punch_damage;
	}

	public void setPunch_damage(float punch_damage) {
		this.punch_damage = punch_damage;
	}

	public float getKick_damage() {
		return kick_damage;
	}

	public void setKick_damage(float kick_damage) {
		this.kick_damage = kick_damage;
	}

	public Texture getPunch() {
		return punch;
	}


	public void setPunch(Texture punch) {
		this.punch = punch;
	}
	
	
	public Directions getDir() {
		return dir;
	}


	public void setDir(Directions dir) {
		this.dir = dir;
	}


	public void update() {
		
		bounds.x = this.x;
		bounds.y = this.y;
		
	}
	
	public Rectangle getPunchBoxRight() {
		return punchBoxRight;
	}
	
	public Rectangle getPunchBoxLeft() {
		return punchBoxLeft;
	}

	public void setPunchBoxRight(Rectangle punchBox) {
		this.punchBoxRight = punchBox;
	}
	
	public void setPunchBoxLeft(Rectangle punchBox) {
		this.punchBoxLeft = punchBox;
	}
	
	public boolean isHit(Rectangle attacker) {
		return Intersector.intersectRectangles(hitableBox, attacker);
	}
	
	public boolean punches(Rectangle attacker) {
		switch (this.getDir()) {
			case DIR_LEFT:
				return this.punchBoxLeft.overlaps(attacker);
			case DIR_RIGHT:
				return this.punchBoxRight.overlaps(attacker);
			default:
				return false;
		}
	}
	
	
	public boolean[] collides(ArrayList<Enemy> me) {
		//Create array of booleans in which each boolean corresponds with a direction
		// i.e. [<UP>, <DOWN>, <LEFT>, <RIGHT>]
		boolean[] c = {false, false, false, false};
		//boolean[] c = {true, true, true, true};
		
		for (int i = 0; i < me.size(); i++) {
			Enemy m = me.get(i);
			
			if (Intersector.overlapRectangles(this.hitableBox, m.hitableBox)) {
			//if (this.hitableBox.overlaps(m.hitableBox)) {
				m.setCollides_player(true);
				//Is Left occupied?
				if ((this.hitableBox.x <= (m.hitableBox.x + m.hitableBox.width)) && (this.hitableBox.x >= m.hitableBox.x)) {
					c[2] = true;
				}
				//Is Down occupied
				if ((this.hitableBox.y <= (m.hitableBox.y + m.hitableBox.height)) && (this.hitableBox.y >= m.hitableBox.y)) {
					c[1] = true;
				}
				//Is Right occupied?
				if (((this.hitableBox.x + this.hitableBox.width) >= m.hitableBox.x) && (m.hitableBox.x >= this.hitableBox.x)){
					c[3] = true;
				}
				// Is Up occupied?
				if (((this.hitableBox.y + this.hitableBox.height) >= m.hitableBox.y) && (m.hitableBox.y >= this.hitableBox.y)){
					c[0] = true;
				}
			} else {
				m.setCollides_player(false);
			}
			
		}
		
		return c;
	}
	

	// Remove once spritesheet becomes available
	
	public Texture getPUNCH_RIGHT() {
		return PUNCH_RIGHT;
	}

	public Texture getPUNCH_LEFT() {
		return PUNCH_LEFT;
	}

	public Texture getRIGHT() {
		return RIGHT;
	}

	public Texture getLEFT() {
		return LEFT;
	}
	
	public void setX(int x) {
		this.x = x;
		this.bounds.x = x;
		this.hitableBox.x = x + this.getBoxset();
		this.punchBoxRight.x = x + (width / 2);
		this.punchBoxLeft.x = x; //+ 10; 
		
	}
	
	public void setY(int y) {
		this.y = y;
		this.bounds.y = y;
		this.hitableBox.y = y + (height / 2);
		this.punchBoxLeft.y = y + (height / 2);
		this.punchBoxRight.y = y + (height / 2);
	}

	public Sound getRandomPunch_sounds() {
		int index = rand.nextInt(punch_sounds.length);
		return punch_sounds[index];
	}
	
	public Sound getRandomKick_sounds() {
		int index = rand.nextInt(kick_sounds.length);
		return kick_sounds[index];
	}
	
	public Sound getRandomMiss_sounds() {
		int index = rand.nextInt(miss_sounds.length);
		return miss_sounds[index];
	}
	
	
	public static Texture getRight() {
		return RIGHT;
	}

	
	public Animation getPlayer_animation() {
		return player_animation;
	}

	public void setPlayer_animation(Animation player_animation) {
		this.player_animation = player_animation;
	}

	public void setCurrentFrame(float delta) {
		this.stateTime += delta;
		//this.currentFrame = player_animation.getKeyFrame(stateTime, true);
		
		if (player_animation == this.kickAnimationLeft || player_animation == this.kickAnimationRight
			|| player_animation == this.punchAnimationLeft || player_animation == this.punchAnimationRight) {
			this.currentFrame = player_animation.getKeyFrame(stateTime, false);
		} else {
			this.currentFrame = player_animation.getKeyFrame(stateTime, true);
		}
		
	}
	
	
	public void hitBack(int distance, ArrayList<Enemy> e) {
		if (distance == 0) {
			return;
		}
		// make sure you can't be moved off screen
		this.setX(this.getX() + distance);
		
		//distance += 10;
		
		for (Enemy m : e) {
			// reverse this
			if (Intersector.overlapRectangles(this.hitableBox, m.hitableBox)) {
				if (distance < 0) {
					if (((this.hitableBox.x + this.hitableBox.width) >= m.hitableBox.x) && (m.hitableBox.x >= this.hitableBox.x)){
						m.hitBack(distance / 2, e);
						break;
					}
						
				} else {
					if ((this.hitableBox.x <= (m.hitableBox.x + m.hitableBox.width)) && (this.hitableBox.x >= m.hitableBox.x)) {
						m.hitBack(distance / 2, e);
						break;
					}
				}
			}
		}
		
		
		this.stateTime = 0f;
		switch (this.getDir()) {
		case DIR_LEFT:
			this.player_animation = this.hitAnimationLeft;
			break;
		case DIR_RIGHT:
			this.player_animation = this.hitAnimationRight;
			break;
		default:
			break;
		}
		this.hit = true;
		return;
	}
	

	public void setCurrentFrame(TextureRegion currentFrame) {
		this.currentFrame = currentFrame;
	}
	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public Animation getWalkAnimationRight() {
		return walkAnimationRight;
	}

	public void setWalkAnimationRight(Animation walkAnimationRight) {
		this.walkAnimationRight = walkAnimationRight;
	}

	public Animation getWalkAnimationLeft() {
		return walkAnimationLeft;
	}

	public void setWalkAnimationLeft(Animation walkAnimationLeft) {
		this.walkAnimationLeft = walkAnimationLeft;
	}

	public Animation getKickAnimationRight() {
		return kickAnimationRight;
	}

	public void setKickAnimationRight(Animation kickAnimationRight) {
		this.kickAnimationRight = kickAnimationRight;
	}

	public Animation getKickAnimationLeft() {
		return kickAnimationLeft;
	}

	public void setKickAnimationLeft(Animation kickAnimationLeft) {
		this.kickAnimationLeft = kickAnimationLeft;
	}

	public Animation getPunchAnimationRight() {
		return punchAnimationRight;
	}

	public void setPunchAnimationRight(Animation punchAnimationRight) {
		this.punchAnimationRight = punchAnimationRight;
	}

	public Animation getPunchAnimationLeft() {
		return punchAnimationLeft;
	}

	public void setPunchAnimationLeft(Animation punchAnimationLeft) {
		this.punchAnimationLeft = punchAnimationLeft;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public boolean isATTACK_ANIMATION() {
		return ATTACK_ANIMATION;
	}

	public void setATTACK_ANIMATION(boolean aTTACK_ANIMATION) {
		ATTACK_ANIMATION = aTTACK_ANIMATION;
	}

	public Animation getHitAnimationRight() {
		return hitAnimationRight;
	}

	public void setHitAnimationRight(Animation hitAnimationRight) {
		this.hitAnimationRight = hitAnimationRight;
	}

	public Animation getHitAnimationLeft() {
		return hitAnimationLeft;
	}

	public void setHitAnimationLeft(Animation hitAnimationLeft) {
		this.hitAnimationLeft = hitAnimationLeft;
	}

	
	
}*/
