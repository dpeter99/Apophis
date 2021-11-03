package engine.modules

object ModuleManager {

    var modules = mutableListOf<ApplicationModule>();

    fun Init() {
        for (module in modules) {
            module.Init();
        }
    }

    fun Update() {
        for (module in modules) {
            module.Update();
        }
    }

    fun LateRender() {
        for (module in modules) {
            module.LateRender();
        }
    }

    fun Render() {
        for (module in modules) {
            module.Render();
        }
    }

    fun AfterFrameEnd() {
        for (module in modules) {
            module.AfterFrameEnd();
        }
    }

    fun PreFrame() {
        for (module in modules) {
            module.PreFrame();
        }
    }

    fun Dispose() {
        for (module in modules) {
            module.dispose();
        }
    }
}