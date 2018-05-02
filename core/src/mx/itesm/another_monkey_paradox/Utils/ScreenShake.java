package mx.itesm.another_monkey_paradox.Utils;

/**
 * @Author  Netprogs Games
 */

import com.badlogic.gdx.graphics.OrthographicCamera;
import static com.badlogic.gdx.math.MathUtils.random;

public class ScreenShake {

    private float elapsed;
    private float duration;
    private float intensity;

    public ScreenShake(float intensity, float duration) {
        this.elapsed = 0;
        this.duration = duration / 1000f;
        this.intensity = intensity;
    }

    public void update(float delta, OrthographicCamera camera) {

        // Only shake when required.
        if(elapsed < duration) {

            // Calculate the amount of shake based on how long it has been shaking already
            float currentPower = intensity * camera.zoom * ((duration - elapsed) / duration);
            float x = (random.nextFloat() - 0.5f) * currentPower;
            float y = (random.nextFloat() - 0.5f) * currentPower;
            camera.translate(-x, -y);

            // Increase the elapsed time by the delta provided.
            elapsed += delta;
        }
    }

}
