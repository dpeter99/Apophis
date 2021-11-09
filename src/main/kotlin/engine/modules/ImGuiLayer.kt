package engine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics
import engine.eventbus.SyncEventBus
import engine.modules.ApplicationModule
import imgui.*
import imgui.extension.imguizmo.ImGuizmo
import imgui.flag.ImGuiConfigFlags
import imgui.flag.ImGuiDockNodeFlags
import imgui.flag.ImGuiStyleVar
import imgui.flag.ImGuiWindowFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import imgui.type.ImBoolean
import org.lwjgl.glfw.GLFW
import util.FontAwesomeIcons
import util.ReadFiles


class ImGuiLayer : ApplicationModule {

    var imGuiGlfw = ImGuiImplGlfw()
    var imGuiGl3 = ImGuiImplGl3()

    private var windowHandle: Long? = null

    var dockspace_flags = ImGuiDockNodeFlags.None

    override fun Init() {
        ImGui.createContext()

        val io: ImGuiIO = ImGui.getIO()
        //io.iniFilename = null
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable)
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);    // Enable Multi-Viewport / Platform Windows

        initFonts(io);

        windowHandle = (Gdx.graphics as Lwjgl3Graphics).window.windowHandle

        imGuiGlfw.init(windowHandle!!, true)
        imGuiGl3.init();
    }

    private fun initFonts(io: ImGuiIO) {
        io.fonts.addFontDefault() // Add default font for latin glyphs

        // You can use the ImFontGlyphRangesBuilder helper to create glyph ranges based on text input.
        // For example: for a game where your script is known, if you can feed your entire script to it (using addText) and only build the characters the game needs.
        // Here we are using it just to combine all required glyphs in one place
        val rangesBuilder = ImFontGlyphRangesBuilder() // Glyphs ranges provide
        rangesBuilder.addRanges(io.fonts.glyphRangesDefault)
        rangesBuilder.addRanges(io.fonts.glyphRangesCyrillic)
        rangesBuilder.addRanges(io.fonts.glyphRangesJapanese)
        rangesBuilder.addRanges(FontAwesomeIcons._IconRange)

        // Font config for additional fonts
        // This is a natively allocated struct so don't forget to call destroy after atlas is built
        val fontConfig = ImFontConfig()
        fontConfig.mergeMode = true // Enable merge mode to merge cyrillic, japanese and icons with default font
        fontConfig.setGlyphOffset(0f,1.5f);
        fontConfig.glyphMinAdvanceX = 12.0f


        val glyphRanges = rangesBuilder.buildRanges()
        io.fonts.addFontFromMemoryTTF(
            ReadFiles.loadFromResources("fa-regular-400.ttf"),
            12.0f,
            fontConfig,
            glyphRanges
        ) // font awesome
        io.fonts.addFontFromMemoryTTF(
            ReadFiles.loadFromResources("fa-solid-900.ttf"),
            12.0f,
            fontConfig,
            glyphRanges
        ) // font awesome
        //io.fonts.texGlyphPadding = 14;


        io.fonts.build()
        fontConfig.destroy()
    }

    override fun PreFrame() {



    }

    override fun LateRender() {

    }

    override fun AfterFrameEnd() {

        imGuiGlfw.newFrame();

        ImGui.newFrame();
        ImGuizmo.beginFrame();

        //ImGui.dockSpace(1);
        var window_flags = ImGuiWindowFlags.MenuBar.or(ImGuiWindowFlags.NoDocking)

        val viewport: ImGuiViewport = ImGui.getMainViewport();
        ImGui.setNextWindowPos(viewport.workPos.x,viewport.workPos.y);
        ImGui.setNextWindowSize(viewport.workSize.x,viewport.workSize.y);
        ImGui.setNextWindowViewport(viewport.id);

        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);

        window_flags = window_flags.or(ImGuiWindowFlags.NoTitleBar).or(ImGuiWindowFlags.NoCollapse).or(ImGuiWindowFlags.NoResize).or(ImGuiWindowFlags.NoMove);
        window_flags = window_flags.or(ImGuiWindowFlags.NoBringToFrontOnFocus).or(ImGuiWindowFlags.NoNavFocus);

        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding,0.0f, 0.0f);

        ImGui.begin("DockSpace Demo", ImBoolean(true), window_flags);

        ImGui.popStyleVar(3);

        val dockspace_id = ImGui.getID("MyDockSpace");
        ImGui.dockSpace(dockspace_id, 0.0f, 0.0f, dockspace_flags);

        ImGui.showDemoWindow();

        //###############################
        SyncEventBus.MAIN.post(OnGUIEvent());
        //###############################


        ImGui.end();

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            val backupWindowPtr:Long = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }
    }

    override fun dispose() {
        imGuiGl3.dispose();
        imGuiGlfw.dispose();
        ImGui.destroyContext();
    }

    class OnGUIEvent{

    }

}

