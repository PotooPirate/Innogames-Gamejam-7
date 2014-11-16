package de.potoopirate.alf.systems;

import java.awt.Font;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.potoopirate.alf.components.SoundComponent;
import de.potoopirate.alf.interfaces.ClientListener;

public class ClientUISystem extends EntitySystem {

	private static final int BLOCK_COUNTER_RELEASE = 3;
	private static final Texture TORTSEN_ICON = new Texture(Gdx.files.internal("icons/tortsenicon.png"));
	private static final Texture EMMA_ICON = new Texture(Gdx.files.internal("icons/emmaicon.png"));
	private static final Texture GUNTER_ICON = new Texture(Gdx.files.internal("icons/guntericon.png"));
	private static final Texture LEFT_ICON = new Texture(Gdx.files.internal("icons/left.png"));
	private static final Texture UP_ICON = new Texture(Gdx.files.internal("icons/up.png"));
	private static final Texture RIGHT_ICON = new Texture(Gdx.files.internal("icons/right.png"));
	private static final Texture SELECTED_LEFT_ICON = new Texture(Gdx.files.internal("icons/selectedleft.png"));
	private static final Texture SELECTED_UP_ICON = new Texture(Gdx.files.internal("icons/selectedup.png"));
	private static final Texture SELECTED_RIGHT_ICON = new Texture(Gdx.files.internal("icons/selectedright.png"));
	private static final Texture CLIENTBACKGROUND = new Texture(Gdx.files.internal("clientbackground.png"));

	
	public static final Texture GRAY_BACKGROUND = new Texture(Gdx.files.internal("icons/gray_background.png"));
	
	static final BitmapFont FONT = new BitmapFont(Gdx.files.internal("CandyFont.fnt"));

	private static final int SECTION = Gdx.graphics.getWidth() / 3;
	private static final int ICON_X = (SECTION - SECTION / 2) - TORTSEN_ICON.getWidth() / 2;
	private static final int ICON_Y = Gdx.graphics.getHeight() / 3;
	private static final int PATH_ICON_Y = 0;
	private static final int UP_PATH_ICON_X = (Gdx.graphics.getWidth() / 2) - UP_ICON.getWidth() / 2;
	private static final int LEFT_PATH_ICON_X = UP_PATH_ICON_X - LEFT_ICON.getWidth();
	private static final int RIGHT_PATH_ICON_X = UP_PATH_ICON_X + UP_ICON.getWidth();
	private static final int SCREENHEIGHT = Gdx.graphics.getHeight();
	BitmapFont font;
	
	private Stage stage;
	private ClientListener clientSystem;
	private int activePath;
	private int x, y;
	private boolean touched;
	private boolean started; // Holding the client in a block state
	private float blockCounter;
	private SpriteBatch batch;

	private Image slot1;
	private Image slot2;
	private Image slot3;
	private Image blockImage;

	private ShapeRenderer debugRenderer;

	public ClientUISystem(ClientListener clientSystem) {
		this.clientSystem = clientSystem;
		debugRenderer = new ShapeRenderer();
		activePath = 2;
		batch = new SpriteBatch();

		blockImage = new Image(GRAY_BACKGROUND);
		blockImage.setPosition(0, 0);
		blockImage.setWidth(Gdx.graphics.getWidth());
		blockImage.setHeight(Gdx.graphics.getHeight());
		
		slot1 = new Image(TORTSEN_ICON);
		slot1.setPosition(ICON_X, Gdx.graphics.getHeight());
		slot2 = new Image(EMMA_ICON);
		slot2.setPosition(ICON_X + SECTION, Gdx.graphics.getHeight());
		slot3 = new Image(GUNTER_ICON);
		slot3.setPosition(ICON_X + SECTION * 2, Gdx.graphics.getHeight());
		font = new BitmapFont();
		font.setColor(1f, 0f, 0f, 1f);
		
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);

		slot1.addAction(Actions.moveTo(ICON_X, ICON_Y, 2, Interpolation.bounceOut));
		slot2.addAction(Actions.sequence(Actions.delay(0.5f), Actions.moveTo(ICON_X + SECTION, ICON_Y, 2, Interpolation.bounceOut)));
		slot3.addAction(Actions.sequence(Actions.delay(1f), Actions.moveTo(ICON_X + SECTION * 2, ICON_Y, 2, Interpolation.bounceOut)));

		blockCounter = 10;
		started = false;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

	}

	private void throwSlot() {
		if (Gdx.input.isTouched() && !touched) {
			y = 0;
			touched = true;
		} else if (Gdx.input.isTouched() && touched) {
			y += Gdx.input.getDeltaY();
			// check selected Section
			x = Gdx.input.getX();
			if (y <= -100) {
				if (x < SECTION) {
					slot1.addAction(Actions.sequence(Actions.moveBy(0, Gdx.graphics.getHeight(), 0.5f), Actions.moveTo(ICON_X, ICON_Y, 1, Interpolation.bounceOut)));
					//SoundComponent.HIPPO_SPAWN_SOUND.play();
				} else if (x < SECTION * 2) {
					slot2.addAction(Actions.sequence(Actions.moveBy(0, Gdx.graphics.getHeight(), 0.5f), Actions.moveTo(ICON_X + SECTION, ICON_Y, 1, Interpolation.bounceOut)));
					//SoundComponent.OCTO_SPAWN_SOUND.play();
				} else {
					slot3.addAction(Actions.sequence(Actions.moveBy(0, Gdx.graphics.getHeight(), 0.5f), Actions.moveTo(ICON_X + SECTION * 2, ICON_Y, 1, Interpolation.bounceOut)));
					//SoundComponent.SNAIL_SPAWN_SOUND.play();
				}
			}
		} else if (!Gdx.input.isTouched() && touched) {
			touched = false;
			if (y <= -100) {
				x = Gdx.input.getX();
				blockCounter = 0;
				if (x < SECTION) {
					clientSystem.throwSlot(1, activePath);
				} else if (x < SECTION * 2) {
					clientSystem.throwSlot(2, activePath);
				} else {
					clientSystem.throwSlot(3, activePath);
				}
			}
			System.out.println("delta X: " + x + "/ delta Y:" + y);
		}
	}

	private void changePath() {
		if (Gdx.input.isTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			if (x > LEFT_PATH_ICON_X && x < LEFT_PATH_ICON_X + LEFT_ICON.getWidth() && y > SCREENHEIGHT - LEFT_ICON.getHeight()) {
				activePath = 1;
				System.out.println("left path");
			} else if (x > UP_PATH_ICON_X && x < UP_PATH_ICON_X + UP_ICON.getWidth() && y > SCREENHEIGHT - UP_ICON.getHeight()) {
				activePath = 2;
				System.out.println("up path");
			} else if (x > RIGHT_PATH_ICON_X && x < (RIGHT_PATH_ICON_X + RIGHT_ICON.getWidth()) && y > SCREENHEIGHT - RIGHT_ICON.getHeight()) {
				activePath = 3;
				System.out.println("right path");
			}
		}
	}

	@Override
	public void update(float deltaTime) {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.update(deltaTime);
		blockCounter += deltaTime;
		stage.act(deltaTime);
		stage.draw();

		// debugRenderer.begin(ShapeType.Line);
		// debugRenderer.setColor(1f, 0, 0, 1);
		// debugRenderer.line(Gdx.graphics.getWidth() / 3, 0, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
		// debugRenderer.line((Gdx.graphics.getWidth() / 3) * 2, 0, (Gdx.graphics.getWidth() / 3) * 2, Gdx.graphics.getHeight());
		// debugRenderer.end();
		
		batch.begin();
		batch.draw(CLIENTBACKGROUND,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//font.draw(batch, "This is some text", 10, 10);
		// if (blockCounter <= BLOCK_COUNTER_RELEASE && started) {

		if (activePath == 1) 
			batch.draw(SELECTED_LEFT_ICON, LEFT_PATH_ICON_X, PATH_ICON_Y);

			else
			batch.draw(LEFT_ICON, LEFT_PATH_ICON_X, PATH_ICON_Y);
			
		if (activePath == 2) 
			batch.draw(SELECTED_UP_ICON, UP_PATH_ICON_X, PATH_ICON_Y);
		else
			batch.draw(UP_ICON, UP_PATH_ICON_X, PATH_ICON_Y);
			
			
		if (activePath == 3) 
			batch.draw(SELECTED_RIGHT_ICON, RIGHT_PATH_ICON_X, PATH_ICON_Y);
		else
			batch.draw(RIGHT_ICON, RIGHT_PATH_ICON_X, PATH_ICON_Y);
			

		slot1.act(deltaTime);
		slot2.act(deltaTime);
		slot3.act(deltaTime);

		slot1.draw(batch, 1f);
		slot2.draw(batch, 1f);
		slot3.draw(batch, 1f);
		
		
		blockImage.act(deltaTime);
		if (blockCounter >= BLOCK_COUNTER_RELEASE && started) {
			throwSlot();
			changePath();
		} else if (blockCounter < BLOCK_COUNTER_RELEASE) {
			blockImage.draw(batch, 1f);
			FONT.setScale(3f);
			TextBounds bounds = FONT.getBounds("0");
			FONT.draw(batch, ""+(int)(3-blockCounter), Gdx.graphics.getWidth()/2 - bounds.width/2 , Gdx.graphics.getHeight()/2 + bounds.height/2);
		} else if (!started) {
			started = clientSystem.isStarted();
		}
		
		batch.end();
	}
}
