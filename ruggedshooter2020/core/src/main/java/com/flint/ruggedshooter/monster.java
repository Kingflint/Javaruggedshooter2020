package com.flint.ruggedshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class monster extends Rectangle {
    private Texture texture;
    private float speed;
    public Rectangle bounds;
    private float scale; // Current scale of the monster

    public monster(Texture texture, float x, float y, float minScale, float maxScale) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.scale = MathUtils.random(minScale, maxScale); // Set scale randomly within range
        this.width = texture.getWidth() * scale;
        this.height = texture.getHeight() * scale;
        this.speed = MathUtils.random(50f, 150f);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void update(float deltaTime) {
        y -= speed * deltaTime;

        // Check if the monster is off-screen
        if (y + height < 0) {
            respawn();
        }

        bounds.setPosition(x, y); // Update bounds for collision detection
    }

    public void respawn() {
        // Respawn the monster at a random x position above the screen
        x = MathUtils.random(0, Gdx.graphics.getWidth() - width);
        y = Gdx.graphics.getHeight() + height;

        // Reassign random scale each time the monster respawns
        this.scale = MathUtils.random(0.08f, 0.1f); // Adjust min and max as needed
        this.width = texture.getWidth() * scale;
        this.height = texture.getHeight() * scale;
        bounds.setSize(width, height);
    }

    // Destroy the monster
    public void destroy() {
        // Trigger respawn after destruction
        respawn();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
}
