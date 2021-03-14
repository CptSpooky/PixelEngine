package pixelengine;

import pixelengine.graphics.PixelBuffer;

import java.util.HashMap;
import java.util.Map;

public class Resources {

	private static Map<String, PixelBuffer> map = new HashMap<>();

	public static PixelBuffer loadPixelBuffer(int w, int h, String resource) {
		return map.computeIfAbsent(resource, res -> new PixelBuffer(w, h, res));
	}

	public static PixelBuffer loadPixelBuffer(String resource) {
		return map.computeIfAbsent(resource, res -> new PixelBuffer(res));
	}

}
