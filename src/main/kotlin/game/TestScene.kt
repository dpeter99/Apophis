package game

import engine.entitysystem.Scene
import game.entities.Asteroid
import game.entities.Player

class TestScene : Scene() {

    init {
        this.add(Asteroid("Test"));
        this.add(Player())
    }

}