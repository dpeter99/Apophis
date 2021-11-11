package game.world

import engine.entitysystem.Entity
import engine.entitysystem.Scene
import game.entities.Asteroid
import glm_.vec2.Vec2
import kotlin.random.Random


class WorldGenerator {

    val MAP_SIZE = 1000
    val CLOSE_SUN_DISTANCE = 250

    var rnd: Random = Random(0)

    fun generate(scene: Scene){

        val root = scene.add(Entity("World Root"));

        for (i in 0..499) {
            //val res: Unit = GenerateNewResource()
            //val count: Unit = rnd.nextInt(5) + 1

            //Create pos
            val pos = Vec2(rnd.nextInt(MAP_SIZE), rnd.nextInt(MAP_SIZE))
            val a = Asteroid()
            a.position = pos;
            //a.setResource(res);
            //a.setCrustSize(rnd.nextInt(3) + 1)
            a.name = "A_$i"

            root.add(a);
            //asteroids.add(a)
        }

    }

}