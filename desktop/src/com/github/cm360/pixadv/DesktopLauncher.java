package com.github.cm360.pixadv;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.github.cm360.pixadv.graphics.picasso.Picasso;
import com.github.cm360.pixadv.network.Server;
import com.github.cm360.pixadv.util.Logger;

public class DesktopLauncher {

	public static void main(String[] arg) {
		if (Arrays.asList(arg).contains("server")) {
			// Server
			try {
				Server server = new Server();
				server.run(InetAddress.getByName("0.0.0.0"), 43234);
			} catch (UnknownHostException e) {
				Logger.logException("Unknown host!", e);
			}
		} else {
			// Client
			// On macOS the program must be started with the -XstartOnFirstThread JVM argument
			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setForegroundFPS(60);
			config.setWindowedMode(Picasso.defaultWindowWidth, Picasso.defaultWindowHeight);
			config.setTitle("PixelAdventure-libGDX");
			new Lwjgl3Application(new ClientApplication(), config);
		}
	}

}
