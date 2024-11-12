package com.flint.ruggedshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Player {
    private Texture texture;
    public Rectangle bounds;
    private float speed;
    private Texture bulletTexture;
    private Array<Bullet> bullets;
    private float bulletSpawnTimer, bulletSpawnDelay;

    public Player(Texture playerTexture, Texture bulletTexture) {
        this.texture = playerTexture;
        this.bounds = new Rectangle(0, 0, playerTexture.getWidth() * 0.15f, playerTexture.getHeight() * 0.15f);
        this.speed = 300f;
        this.bulletTexture = bulletTexture;
        this.bullets = new Array<>();
        this.bulletSpawnTimer = 0f;
        this.bulletSpawnDelay = 0.2f;
    }


    private void handleMovement(float deltaTime) {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            bounds.x -= speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            bounds.x += speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            bounds.y += speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            bounds.y -= speed * deltaTime;
        }

        // Clamp player position within the screen bounds
        bounds.x = MathUtils.clamp(bounds.x, 0, Gdx.graphics.getWidth() - bounds.width);
        bounds.y = MathUtils.clamp(bounds.y, 0, Gdx.graphics.getHeight() - bounds.height);
    }

    private void handleShooting(float deltaTime) {
        bulletSpawnTimer += deltaTime;
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) && bulletSpawnTimer >= bulletSpawnDelay) {
            spawnBullet();
            bulletSpawnTimer = 0f;
        }
    }

    private void spawnBullet() {
        // Calculate the position to spawn the bullet centered relative to the player
        float bulletX = bounds.x + bounds.width / 2 - bulletTexture.getWidth() * 0.02f / 2; // Centering the bullet horizontally
        float bulletY = bounds.y + bounds.height; // Spawn above the player's bottom edge
        Bullet bullet = new Bullet(bulletTexture, bulletX, bulletY);
        bullets.add(bullet);
    }

    public void update(float deltaTime) {
        handleMovement(deltaTime);
        handleShooting(deltaTime);
        Array<monster> monsters;
        updateBullets(deltaTime, monsters);
    }

    private void updateBullets(float deltaTime, Array<monster> monsters) {
        // Update all bullets with deltaTime and check for collisions with monsters
        for (Bullet bullet : bullets) {
            for (monster m : monsters) {
                bullet.update(deltaTime, m); // Pass each monster to check for collisions
            }
        }
    }


    public void draw(SpriteBatch batch) {
        // Draw player character
        float scale = 1.0f; // No scaling applied here
        batch.draw(texture, bounds.x, bounds.y, bounds.width * scale, bounds.height * scale);

        // Draw each bullet
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }
    }

}
