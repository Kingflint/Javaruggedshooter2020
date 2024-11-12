package com.flint.ruggedshooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Explosion {
    private Texture explosionTexture;
    private Vector2 position;
    private float duration;       // Duration of the explosion in seconds
    private float timeElapsed;    // Time elapsed since the explosion started

    public Explosion(float x, float y) {
        // Initialize explosion texture
        explosionTexture = new Texture("explosion.png");  // Ensure you have an "explosion.png" asset

        position = new Vector2(x, y);
        duration = 0.5f;  // Explosion lasts 0.5 seconds
        timeElapsed = 0;
    }

    public void update(float deltaTime) {
        timeElapsed += deltaTime;
    }

    public void draw(SpriteBatch batch) {
        if (!isFinished()) {
            batch.draw(explosionTexture, position.x, position.y);
        }
    }

    // Check if explosion duration has finished
    public boolean isFinished() {
        return timeElapsed >= duration;
    }

    public void dispose() {
        explosionTexture.dispose();
    }
}
