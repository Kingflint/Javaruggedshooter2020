package com.flint.ruggedshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Rectangle {
    final Rectangle bounds;
    private Texture texture;
    private float x, y, speed;
    private float scale = 0.02f;  // Scale factor for the bullet (2% of the original size)
    private Explosion explosion;  // Explosion instance

    public Bullet(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = texture.getWidth() * scale;
        this.height = texture.getHeight() * scale;
        this.speed = 500f;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void update(float deltaTime, monster monster) {
        // Update bullet position
        y += speed * deltaTime;

        // Check for off-screen condition
        if (y > Gdx.graphics.getHeight()) {
            setPosition(-1000, -1000); // Move bullet off-screen when it goes out of bounds
        }

        setPosition(x, y);

        // Check collision with monster
        if (this.overlaps(monster.bounds)) {
            triggerExplosion();  // Trigger explosion when hitting the monster
            monster.destroy();   // Destroy the monster
            setPosition(-1000, -1000);  // Move bullet off-screen (or reset it)
        }

        if (explosion != null) {
            explosion.update(deltaTime);  // Update explosion effect if it exists
            if (explosion.isFinished()) {
                explosion = null;  // Remove explosion after it finishes
            }
        }
    }

    public void draw(SpriteBatch batch) {
        // Draw the bullet
        batch.draw(texture, x, y, width, height);

        // Draw explosion if it occurred
        if (explosion != null) {
            explosion.draw(batch);  // Draw explosion if triggered
        }
    }

    // Trigger explosion
    private void triggerExplosion() {
        explosion = new Explosion(x, y);  // Initialize the explosion effect at the bullet's position
    }
}
