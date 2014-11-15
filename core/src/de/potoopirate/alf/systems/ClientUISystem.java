package de.potoopirate.alf.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.potoopirate.alf.interfaces.ClientListener;

public class ClientUISystem extends EntitySystem {

	private static final int BLOCK_COUNTER_RELEASE = 10;
	private static final Texture TORTSEN_ICON = new Texture(Gdx.files.internal("icons/tortsenicon.png"));
	private static final Texture LEFT_ICON = new Texture(Gdx.files.internal("icons/left.png"));
	private static final Texture UP_ICON = new Texture(Gdx.files.internal("icons/up.png"));
	private static final Texture RIGHT_ICON = new Texture(Gdx.files.internal("icons/right.png"));

	private static final int BUTTON_WIDTH = Gdx.graphics.getWidth() / 5;
	private static final int BUTTON_HEIGHT = Gdx.graphics.getHeight() / 4;
	private static final int SECTION = Gdx.graphics.getWidth() / 3;
	private static final int ICON_X = (SECTION - SECTION / 2) - TORTSEN_ICON.getWidth() / 2;
	private static final int ICON_Y = Gdx.graphics.getHeight() /3 ;
	private static final int PATH_ICON_Y = 0;
	private static final int UP_PATH_ICON_X = (Gdx.graphics.getWidth() / 2) - UP_ICON.getWidth() / 2;
	private static final int LEFT_PATH_ICON_X = UP_PATH_ICON_X - LEFT_ICON.getWidth();
	private static final int RIGHT_PATH_ICON_X = UP_PATH_ICON_X + UP_ICON.getWidth();
	private static final int SCREENHEIGHT = Gdx.graphics.getHeight();
	
	private int start_y = 0;
	private Stage stage;
	private ClientListener clientSystem;
	private int activePath;
	private int x, y;
	private boolean touched;
	private boolean started; // Holding the client in a block state
	private float blockCounter;
	private SpriteBatch batch;

	private ShapeRenderer debugRenderer;

	public ClientUISystem(ClientListener clientSystem) {
		this.clientSystem = clientSystem;
		debugRenderer = new ShapeRenderer();
		activePath = 2;
		batch = new SpriteBatch();
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);

		blockCounter = 0;
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
		} else if (!Gdx.input.isTouched() && touched) {
			touched = false;
			if (y <= -200) {
				x = Gdx.input.getX();
				if (x < Gdx.graphics.getWidth() / 3) {
					clientSystem.throwSlot(1, activePath);
				} else if (x < (Gdx.graphics.getWidth() / 3) * 2) {
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
			if (x > LEFT_PATH_ICON_X && x < LEFT_PATH_ICON_X + LEFT_ICON.getWidth() && y > SCREENHEIGHT -LEFT_ICON.getHeight() ) {
				activePath = 1;
				System.out.println("left path");
			} else if (x > UP_PATH_ICON_X && x < UP_PATH_ICON_X + UP_ICON.getWidth() && y > SCREENHEIGHT - UP_ICON.getHeight()) {
				activePath = 2;
				System.out.println("up path");
			} else if (x > RIGHT_PATH_ICON_X && x < (RIGHT_PATH_ICON_X + RIGHT_ICON.getWidth()) &&  y > SCREENHEIGHT -RIGHT_ICON.getHeight()) {
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

		debugRenderer.begin(ShapeType.Line);
		debugRenderer.setColor(1f, 0, 0, 1);
		debugRenderer.line(Gdx.graphics.getWidth() / 3, 0, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
		debugRenderer.line((Gdx.graphics.getWidth() / 3) * 2, 0, (Gdx.graphics.getWidth() / 3) * 2, Gdx.graphics.getHeight());
		debugRenderer.end();

		batch.begin(); 
		// Draw Path Icons
		batch.draw(LEFT_ICON, LEFT_PATH_ICON_X, PATH_ICON_Y);
		batch.draw(UP_ICON, UP_PATH_ICON_X, PATH_ICON_Y);
		batch.draw(RIGHT_ICON, RIGHT_PATH_ICON_X, PATH_ICON_Y);

		// Draw Animal Icons
		
		if (start_y < ICON_Y ){
			batch.draw(TORTSEN_ICON, ICON_X, start_y);
			start_y++;
		}else if (start_y == ICON_Y){
			batch.draw(TORTSEN_ICON, ICON_X, ICON_Y);
		}
		batch.draw(TORTSEN_ICON, ICON_X + SECTION, ICON_Y);
		batch.draw(TORTSEN_ICON, ICON_X + (SECTION * 2), ICON_Y);
		batch.end();

		// if(blockCounter >= BLOCK_COUNTER_RELEASE && started) {
		throwSlot();
		changePath();
		// } else if (!started) {
		// started = clientSystem.isStarted();
		// }
	}
}
