package util

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import glm_.vec2.Vec2

val Vec2.Companion.Zero: Vec2
    get() = Vec2(0,0)

val Vec2.Companion.One: Vec2
    get() {
        return Vec2(1f,1f);
    }

fun Vec2.toVector2(): Vector2 {
    return Vector2(this.x,this.y);
}

fun Vec2.toVector3(): Vector3 {
    return Vector3(this.x,this.y,0F);
}

fun Vector2.toVec2(): Vec2 {
    return Vec2(this.x,this.y);
}