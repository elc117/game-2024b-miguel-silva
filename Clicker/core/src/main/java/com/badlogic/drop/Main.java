package com.badlogic.drop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    Texture backgroundTexture;
    Texture buttonTexture;
    Sound clickSound;
    Music music;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Sprite buttonSprite; // Declare a new Sprite variable
    Vector2 touchPos;
    Rectangle buttonRectangle;
    BitmapFont font;
    int clicksDone;
    @Override
    public void create() {
        backgroundTexture = new Texture("background2.jpg");
        buttonTexture = new Texture("pepe-the-frog-party-time.gif");
        clickSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("game-176807.mp3"));
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(9, 7);
        buttonSprite = new Sprite(buttonTexture); // Initialize the sprite based on the texture
        buttonSprite.setSize(1, 1); // Define the size of the sprite
        touchPos = new Vector2();
        buttonRectangle = new Rectangle();
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() * 2 / Gdx.graphics.getHeight() * 2);
        clicksDone = 0;
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
        // Prepare your application here.
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
        // Draw your application here.
    }

    private void input(){
        float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta



        /*
        if (Gdx.input.isTouched()) { // If the user has clicked or tapped the screen
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
            viewport.unproject(touchPos); // Convert the units to the world units of the viewport
            buttonSprite.setCenterX(touchPos.x); // Change the horizontally centered position of the bucket
            buttonSprite.setCenterY(touchPos.y);
        }
        */

        if (Gdx.input.isTouched()) { // If the user has clicked or tapped the screen
            // Store the worldWidth and worldHeight as local variables for brevity
            float worldWidth = viewport.getWorldWidth();
            float worldHeight = viewport.getWorldHeight();

            // Store the bucket size for brevity
            float buttonWidth = buttonSprite.getWidth();
            float buttonHeight = buttonSprite.getHeight();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
            viewport.unproject(touchPos); // Convert the units to the world units of the viewport
            if ((touchPos.x >= buttonSprite.getX()) && (touchPos.y >= buttonSprite.getY()) && (touchPos.x <= buttonSprite.getX() + buttonWidth) && (touchPos.y <= buttonSprite.getY() + buttonHeight)){
                buttonSprite.setCenterX(MathUtils.random(0f, worldWidth - buttonWidth));
                buttonSprite.setCenterY(MathUtils.random(0f, worldHeight - buttonHeight));
                clicksDone++;
                clickSound.play();
            }
        }
    }

    private void logic(){
        float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        // Store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Store the bucket size for brevity
        float buttonWidth = buttonSprite.getWidth();
        float buttonHeight = buttonSprite.getHeight();


        //buttonSprite.setX(MathUtils.random(0f, worldWidth - buttonWidth));
        //buttonSprite.setY(MathUtils.random(0f, worldHeight - buttonHeight));
        //buttonSprite.setX(4);
        //buttonSprite.setY(3);

        buttonRectangle.set(buttonSprite.getX(), buttonSprite.getY(), buttonWidth, buttonHeight);

    }

    private void draw(){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        // store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight); // draw the background
        buttonSprite.draw(spriteBatch); // Sprites have their own draw method

        font.draw(spriteBatch, "Clicks: " + clicksDone, 0, worldHeight);

        spriteBatch.end();

    }

    private void drawSprite(){

    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
    }
}
