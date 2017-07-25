package lukfugl.raytracer.display;

import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	private int width;
	private int height;
	private String title;

	private long id;
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	private Scene scene;

	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public void init() {
		errorCallback = GLFWErrorCallback.createPrint();
		GLFW.glfwSetErrorCallback(errorCallback);
		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW!");
		}

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        id = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
        if (id == NULL) {
            GLFW.glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window!");
        }

        GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(id,
                         (vidmode.width() - width) / 2,
                         (vidmode.height() - height) / 2
        );

        GLFW.glfwMakeContextCurrent(id);
        GL.createCapabilities();
        GLFW.glfwSwapInterval(1);

        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
                    GLFW.glfwSetWindowShouldClose(window, true);
                }
                if (key == GLFW.GLFW_KEY_S && action == GLFW.GLFW_PRESS) {
                	scene.screenshot();
                }
            }
        };
        GLFW.glfwSetKeyCallback(id, keyCallback);
	}

	public boolean isClosing() {
		return GLFW.glfwWindowShouldClose(id);
	}

	public void update() {
		GLFW.glfwSwapBuffers(id);
		GLFW.glfwPollEvents();
	}

	public void destroy() {
		GLFW.glfwDestroyWindow(id);
        GLFW.glfwTerminate();
        errorCallback.free();
		keyCallback.free();
	}
	
	public void loop(Scene scene) {
		init();
		// TODO revisit this; needed so the keyCB can see the scene object
		this.scene = scene;
		scene.init();
		while (!isClosing()) {
			scene.render();
			update();
		}
        scene.cleanup();
        destroy();
	}
}