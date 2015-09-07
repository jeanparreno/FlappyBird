package net.felipebueno.flappybird.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

	protected OrthographicCamera camera;
	protected Vector3 mouse;
	protected GameStateManager manager;

	public State(GameStateManager manager) {
		this.manager = manager;
		camera = new OrthographicCamera();
		mouse = new Vector3();
	}

	protected abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch spriteBatch);

}