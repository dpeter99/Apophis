package game

import engine.entitysystem.Scene
import game.entities.Asteroid
import game.entities.Player
import game.world.WorldGenerator

class TestScene : Scene() {

    init {
        this.add(Player())

        val gen = WorldGenerator();

        gen.generate(this);
    }

}