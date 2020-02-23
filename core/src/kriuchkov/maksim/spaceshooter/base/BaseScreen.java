package kriuchkov.maksim.spaceshooter.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.MatrixUtils;
import ru.geekbrains.math.Rect;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    private Rect screenBounds;
    private Rect worldBounds;
    private Rect glBounds;

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;
    private Vector2 touch;

    @Override
    public void show() {
        System.out.println("show()");

        batch = new SpriteBatch();

        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0,0,1f,1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        touch = new Vector2();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown(keycode = " + keycode + ")");
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp(keycode = " + keycode + ")");
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped(character = " + character + ")");
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.printf("touchDown(x = %d, y = %d)\n", screenX, screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer, button);
        return false;
    }

    private boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.printf("touchDown(touch.x = %f, touch.y = %f)\n", touch.x, touch.y);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.printf("touchUp(x = %d, y = %d)\n", screenX, screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer, button);
        return false;
    }

    private boolean touchUp(Vector2 touch, int pointer, int button) {
        System.out.printf("touchUp(touch.x = %f, touch.y = %f)\n", touch.x, touch.y);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.printf("touchDragged(x = %d, y = %d)\n", screenX, screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    private boolean touchDragged(Vector2 touch, int pointer) {
        System.out.printf("touchDragged(touch.x = %f, touch.y = %f)\n", touch.x, touch.y);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        System.out.printf("mouseMoved(x = %d, y = %d)\n", screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.printf("scrolled(amount = %d)\n", amount);
        return false;
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        System.out.printf("resize(width = %d, height = %d)\n", width, height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        worldBounds.setHeight(1f);
        float aspect = width / (float) height;
        worldBounds.setWidth(aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {
        System.out.printf("worldBounds: width = %f, height = %f)\n", worldBounds.getWidth(), worldBounds.getHeight());
    }

    @Override
    public void pause() {
        System.out.println("pause()");
    }

    @Override
    public void resume() {
        System.out.println("resume()");
    }

    @Override
    public void hide() {
        System.out.println("hide()");
    }

    @Override
    public void dispose() {
        System.out.println("dispose()");
    }
}