package gui.util;

import java.net.URL;

public final class ResourceLoader {
	
	public static URL loadResource(String filename) {
		return ResourceLoader.class.getResource(filename);
	}
}
