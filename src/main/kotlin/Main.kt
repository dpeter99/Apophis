import engine.Bootstrap
import java.lang.management.ManagementFactory

fun main() {

    var id = ManagementFactory.getRuntimeMXBean().getName()

    println(id)

    var game = Bootstrap(Bootstrap.EngineType.Editor);
    game.Run();
    //val config = Lwjgl3ApplicationConfiguration()
    //Lwjgl3Application(game, config)

}