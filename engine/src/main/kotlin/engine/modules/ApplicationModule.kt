package engine.modules

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.utils.Disposable

interface ApplicationModule : Disposable {

    fun Init() = Unit;
    fun Update() = Unit;
    fun LateRender() = Unit;
    fun Render() = Unit;
    fun AfterFrameEnd() = Unit;
    fun PreFrame() = Unit;

}