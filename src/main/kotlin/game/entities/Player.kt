package game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import engine.entitysystem.CameraEntity
import engine.entitysystem.SceneEntity
import engine.entitysystem.entities.SpriteRenderer
import engine.Time
import glm_.vec2.Vec2
import util.Zero
import kotlin.math.atan2

class Player() : SceneEntity("Player") {

    lateinit var renderer: SpriteRenderer;


    var heading: Vec2 = Vec2(0f,1f);
    var speed: Float = 0f;

    override fun Setup(){
        addInternal(CameraEntity()){
            this.worldSize = 500f;
        }
        renderer = addInternal(SpriteRenderer());
    }

    override fun Update() {
        //rotation += 1f/1f;

        var direction: Vec2= Vec2.Zero;

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            direction.y += 1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            direction.y -= 1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            direction.x -= 1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            direction.x += 1f;
        }

        //direction = direction.normalizeAssign();

        heading = heading + (direction*0.05f);
        heading = heading.normalizeAssign();

        this.rotation =-(atan2(heading.x,heading.y)*180/ kotlin.math.PI.toFloat());

        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            speed += 10f*Time.DeltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
            speed -= 10f*Time.DeltaTime;
            if(speed < 0)
                speed = 0f;
        }

        this.position plusAssign (heading * speed * Time.DeltaTime);

    }

    override fun Render(batch: Batch, parentAlpha: Float) {

        scene.shape.setColor(Color.GOLD)
        scene.shape.filledCircle(worldPosition.x, worldPosition.y, 1f);

        val end = worldPosition + (Vec2(0,15f).rotate(this.worldRotation/ (180f/kotlin.math.PI.toFloat())));

        scene.shape.line(worldPosition.x, worldPosition.y,end.x, end.y, 1f);

        scene.shape.setColor(Color.GREEN)
        scene.shape.line(worldPosition.x, worldPosition.y,worldPosition.x + (heading.x*10f), worldPosition.y + (heading.y*10f), 1f);

    }
}