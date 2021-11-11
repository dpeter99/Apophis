package util

import com.badlogic.gdx.Gdx
import java.io.IOException
import java.net.URISyntaxException
import java.nio.file.Files
import java.nio.file.Paths


object ReadFiles {



    fun loadFromResources(name: String): ByteArray? {
        return try {
            return Gdx.files.internal(name).readBytes();
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
    }

}