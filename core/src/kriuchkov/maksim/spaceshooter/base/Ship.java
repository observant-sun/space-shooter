package kriuchkov.maksim.spaceshooter.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import kriuchkov.maksim.spaceshooter.pool.BulletPool;
import kriuchkov.maksim.spaceshooter.pool.ExplosionPool;
import kriuchkov.maksim.spaceshooter.sprite.Bullet;
import kriuchkov.maksim.spaceshooter.sprite.Explosion;
import ru.geekbrains.math.Rect;

public abstract class Ship extends Sprite {

    protected static final float DAMAGE_ANIMATION_INTERVAL = 0.1f;

    protected final Vector2 v0 = new Vector2();

    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletTextureRegion;
    protected Sound bulletFireSound;
    protected Vector2 bulletV;
    protected Vector2 bulletEmitterPos;
    protected boolean isShooting;
    protected float delayBetweenShots;
    protected float sinceLastShot;
    protected float bulletHeight;
    protected int bulletDamage;
    protected float bulletFireSoundVolume = 1f;
    protected float damageAnimationTimer;
    protected int hp;

    protected Ship() {
        isShooting = true;
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        isShooting = true;
    }

    @Override
    public void update(float delta) {
        if (sinceLastShot < delayBetweenShots)
            sinceLastShot += delta;
        if (isShooting && sinceLastShot >= delayBetweenShots) {
            shoot();
            sinceLastShot -= delayBetweenShots;
            if (sinceLastShot >= delayBetweenShots)
                sinceLastShot = 0;
        }

        damageAnimationTimer += delta;
        if (damageAnimationTimer >= DAMAGE_ANIMATION_INTERVAL) {
            frame = 0;
        }
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        updateBulletEmitterPos();
        bullet.set(this, bulletTextureRegion, bulletEmitterPos, bulletV, bulletHeight, worldBounds, bulletDamage);
        bulletFireSound.play(bulletFireSoundVolume);
    }

    protected abstract void updateBulletEmitterPos();

    protected void explode() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    @Override
    public void destroy() {
        destroy(true);
    }

    public void destroy(boolean explosion) {
        super.destroy();
        if (explosion)
            explode();
    }

    public void dispose() {
        bulletFireSound.dispose();
    }

    public void damage(int damageAmount) {
        this.hp -= damageAmount;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
        damageAnimationTimer = 0f;
        frame = 1;
    }

    /**
     * @return current 'hp' of this ship - minimal damage that it must take to be destroyed
     * */
    public int getHp() {
        return hp;
    }
}
