package com.flint.ruggedshooter;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;


public class ruggedshooter extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture playerTexture, monsterTexture, bulletTexture, explosionTexture;
    private Array<monster> monsters;
    private Player player;
    private Array<Bullet> bullets;
    private int score;
    private boolean gameOver;
    private float monsterSpawnTimer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        playerTexture = new Texture("player.png");
        monsterTexture = new Texture("monster1.png");
        bulletTexture = new Texture("bullet.png");
        explosionTexture = new Texture("explosion.png");
        monsters = new Array<>();
        player = new Player(playerTexture, bulletTexture);
        bullets = new Array<>();
        score = 0;
        gameOver = false;

        // Initial monster spawning
        spawnMonsters(0);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();

        // Update and draw player
        player.update(deltaTime);
        player.draw(batch);

        // Update and draw monsters
        updateAndDrawMonsters(deltaTime);

        // Update and draw bullets
        updateAndDrawBullets(deltaTime);

        // Update score and check for game over
        updateScore();
        checkGameOver();

        // Draw UI elements (score, game over, restart button)
        drawUI();

        batch.end();
    }

    private void updateAndDrawMonsters(float deltaTime) {
        // Update monster positions and check for collisions with player
        for (monster monster : monsters) {
            monster.update(deltaTime);
            monster.draw(batch);
            if (monster.bounds.overlaps(player.bounds)) {
                gameOver = true;
            }
        }

        // Spawn new monsters periodically
        spawnMonsters(deltaTime);
    }

    private void updateAndDrawBullets(float deltaTime) {
        // Update bullet positions and check for collisions with monsters
        for (Bullet bullet : bullets) {
            for (monster monster : monsters) {
                bullet.update(deltaTime, monster); // Pass both deltaTime and the current monster
            }

            bullet.draw(batch); // Draw the bullet

            for (monster monster : monsters) {
                if (bullet.bounds.overlaps(monster.bounds)) {
                    // Remove the monster and bullet from their respective lists
                    monsters.removeValue(monster, true);
                    bullets.removeValue(bullet, true);

                    // Increase the score
                    score++;

                    // Draw the explosion at the monster's position
                    batch.draw(explosionTexture, monster.bounds.x, monster.bounds.y);

                    break; // Stop checking further monsters for this bullet
                }
            }
        }
    }



    private void updateScore() {
        // Update score display logic
    }

    private void checkGameOver() {
        // Check if the game is over and display the game over screen
    }

    private void drawUI() {
        // Draw score, game over screen, and restart button
    }

    private void spawnMonsters(float deltaTime) {
        monsterSpawnTimer += deltaTime;

        // Spawn a new monster every 2 seconds
        if (monsterSpawnTimer >= 2.0f) {
            float x = MathUtils.random(0, Gdx.graphics.getWidth() - monsterTexture.getWidth());
            float y = Gdx.graphics.getHeight();
            float minScale = 0.08f; // Set minimum scale
            float maxScale = 0.1f; // Set maximum scale
            monsters.add(new monster(monsterTexture, x, y, minScale, maxScale));
            monsterSpawnTimer = 0; // Reset spawn timer
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        monsterTexture.dispose();
        bulletTexture.dispose();
        explosionTexture.dispose();
    }
}
