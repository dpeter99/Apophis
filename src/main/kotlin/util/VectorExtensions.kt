package util

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import glm_.vec2.Vec2

val Vec2.Companion.Zero: Vec2
    get() = Vec2(0,0)

fun Vec2.toVector2(): Vector2 {
    return Vector2(this.x,this.y);
}

fun Vec2.toVector3(): Vector3 {
    return Vector3(this.x,this.y,0F);
}