package serdjuk.com.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import serdjuk.com.Start;

/** Launches the desktop (LWJGL) application. */
public class DesktopLauncher {
	public static void main(String[] args) {
		createApplication();
	}

	private static LwjglApplication createApplication() {
		return new LwjglApplication(new Start(), getDefaultConfiguration());
	}

	private static LwjglApplicationConfiguration getDefaultConfiguration() {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "xpeccydump";
		configuration.width = 640 - 26;
		configuration.height = 480;
		configuration.pauseWhenMinimized = true;
		//// This prevents a confusing error that would appear after exiting normally.
		configuration.forceExit = false;

		configuration.addIcon("hex.png", FileType.Internal);
//		for (int size : new int[] { 128, 64, 32, 16 }) {
//			configuration.addIcon("libgdx" + size + ".png", FileType.Internal);
//		}
		return configuration;
	}
}