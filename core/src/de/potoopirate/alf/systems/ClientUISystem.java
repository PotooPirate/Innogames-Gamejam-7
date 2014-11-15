package de.potoopirate.alf.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.potoopirate.alf.interfaces.ClientListener;

public class ClientUISystem extends EntitySystem {

	private static final int BLOCK_COUNTER_RELEASE = 10;
	private static final Texture TORTSEN_ICON = new Texture(Gdx.files.internal("icons/tortsenicon.png"));
	private static final Texture EMMA_ICON = new Texture(Gdx.files.internal("icons/emmaicon.png"));
	private static final Texture GUNTER_ICON = new Texture(Gdx.files.internal("icons/guntericon.png"));
	private static final Texture LEFT_ICON = new Texture(Gdx.files.internal("icons/left.png"));
	private static final Texture UP_ICON = new Texture(Gdx.files.internal("icons/up.png"));
	private static final Texture RIGHT_ICON = new Texture(Gdx.files.internal("icons/right.png"));

	private static final int SECTION = Gdx.graphics.getWidth() / 3;
	private static final int ICON_X = (SECTION - SECTION / 2) - TORTSEN_ICON.getWidth() / 2;
	private static final int ICON_Y = Gdx.graphics.getHeight() / 3;
	private static final int PATH_ICON_Y = 0;
	private static final int UP_PATH_ICON_X = (Gdx.graphics.getWidth() / 2) - UP_ICON.getWidth() / 2;
	private static final int LEFT_PATH_ICON_X = UP_PATH_ICON_X - LEFT_ICON.getWidth();
	private static final int RIGHT_PATH_ICON_X = UP_PATH_ICON_X + UP_ICON.getWidth();
	private static final int SCREENHEIGHT = Gdx.graphics.getHeight();

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

		slot1 = new Image(TORTSEN_ICON);
		slot1.setPosition(ICON_X, Gdx.graphics.getHeight());
		slot2 = new Image(EMMA_ICON);
		slot2.setPosition(ICON_X + SECTION, Gdx.graphics.getHeight());
		slot3 = new Image(GUNTER_ICON);
		slot3.setPosition(ICON_X + SECTION * 2, Gdx.graphics.getHeight());

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
			if (x < SECTION) {
				slot1.setY(Gdx.graphics.getHeight() - Gdx.input.getY());

			} else if (x < SECTION * 2) {
				slot2.setY(Gdx.graphics.getHeight() - Gdx.input.getY());

			} else {
				slot3.setY(Gdx.graphics.getHeight() - Gdx.input.getY());

			}
		} else if (!Gdx.input.isTouched() && touched) {
			touched = false;
			if (y <= -100) {
				x = Gdx.input.getX();
				if (x < SECTION) {

					slot1.addAction(Actions.sequence(Actions.moveBy(0, Gdx.graphics.getHeight(), 0.5f), Actions.moveTo(ICON_X, ICON_Y, 1, Interpolation.bounceOut)));

					clientSystem.throwSlot(1, activePath);
					blockCounter = 0;
				} else if (x < SECTION * 2) {
					slot2.addAction(Actions.sequence(Actions.moveBy(0, Gdx.graphics.getHeight(), 0.5f), Actions.moveTo(ICON_X + SECTION, ICON_Y, 1, Interpolation.bounceOut)));
					clientSystem.throwSlot(2, activePath);
					blockCounter = 0;
				} else {

					slot3.addAction(Actions.sequence(Actions.moveBy(0, Gdx.graphics.getHeight(), 0.5f), Actions.moveTo(ICON_X + SECTION * 2, ICON_Y, 1, Interpolation.bounceOut)));

					clientSystem.throwSlot(3, activePath);
					blockCounter = 0;
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
		// batch.begin();

		// if (blockCounter <= BLOCK_COUNTER_RELEASE && started) {
		if (blockCounter >= BLOCK_COUNTER_RELEASE) {
			throwSlot();
			changePath();
			slot1.setColor(300, 300, 300, 1);
			slot2.setColor(300, 300, 300, 1);
			slot3.setColor(300, 300, 300, 1);
		} else if (blockCounter < BLOCK_COUNTER_RELEASE) {
			slot1.setColor(100, 100, 100, 0.2f);
			slot2.setColor(100, 100, 100, 0.2f);
			slot3.setColor(100, 100, 100, 0.2f);
		} else if (!started) {
			started = clientSystem.isStarted();
		}

		batch.draw(LEFT_ICON, LEFT_PATH_ICON_X, PATH_ICON_Y);
		batch.draw(UP_ICON, UP_PATH_ICON_X, PATH_ICON_Y);
		batch.draw(RIGHT_ICON, RIGHT_PATH_ICON_X, PATH_ICON_Y);

		slot1.act(deltaTime);
		slot2.act(deltaTime);
		slot3.act(deltaTime);

		slot1.draw(batch, 1f);
		slot2.draw(batch, 1f);
		slot3.draw(batch, 1f);

		batch.end();
	}
}
