package game

import engine.entities.Scene
import game.entities.Asteroid

class TestScene : Scene() {

    init {
        this.hierarchy.add(Asteroid());
    }

}