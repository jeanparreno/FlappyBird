package net.felipebueno.flappybird.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import net.felipebueno.flappybird.sprites.Bird;
import net.felipebueno.flappybird.sprites.Tube;

import static net.felipebueno.flappybird.FlappyBird.HEIGHT;
import static net.felipebueno.flappybird.FlappyBird.WIDTH;
import static net.felipebueno.flappybird.sprites.Tube.TUBES_COUNT;
import static net.felipebueno.flappybird.sprites.Tube.TUBE_SPACING;
import static net.felipebueno.flappybird.sprites.Tube.TUBE_WIDTH;

public class PlayState extends State {

	private final String TAG = getClass().getSimpleName();
	private final Bird bird;
	private final Texture bg;
	private final Texture ground;
	private final Array<Tube> tubes;

	public PlayState(GameStateManager manager) {
		super(manager);
		camera.setToOrtho(false, WIDTH / 2, HEIGHT / 2);

		bird = new Bird(50, 100);
		bg = new Texture("bg.png");

		tubes = new Array<Tube>();
		for (int i = 1; i <= TUBES_COUNT; i++) {
			tubes.add(new Tube(i * (TUBE_SPACING + TUBE_WIDTH)));
		}

		ground = new Texture("ground.png");

	}

	@Override
	protected void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			manager.set(new MenuState(manager));

		if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.J))
			bird.jump();
	}

	@Override
	public void update(float dt) {
		handleInput();
		bird.update(dt);
		camera.position.x  = bird.getPosition().x + 80;

		for (int i = 0; i < tubes.size; i++) {
			Tube tube = tubes.get(i);
			if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
				tube.reposition(tube.getPosTopTube().x + ((TUBE_WIDTH + TUBE_SPACING) * TUBES_COUNT));
			}
			if (tube.collides(bird.getBounds()))
				manager.set(new PlayState(manager));
		}


		camera.update();
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
		batch.draw(ground, camera.position.x - (camera.viewportWidth / 2), 0);
		batch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

		for (Tube tube : tubes) {
			batch.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
			batch.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
		}
		batch.end();
	}

	@Override
	public void dispose() {
		bg.dispose();
		ground.dispose();
		bird.dispose();
		for (Tube tube : tubes)
			tube.dispose();
		Gdx.app.log(TAG, "disposed");
	}
}

