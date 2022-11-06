package serdjuk.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Start extends Game {


	View view;

	@Override
	public void create() {
		view = new View();
		setScreen(view);
	}

	@Override
	public void render() {
		super.render();


	}


//	@Override
//	public void resize(int width, int height) {
////		super.resize(width, height);
//	}

	@Override
	public void dispose() {
		view.dispose();
	}
}