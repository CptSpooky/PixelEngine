package pixelengine.graphics;

//montage -tile 12x8 -geometry +0+0 -background none *.png out.png

import pixelengine.math.RectI;
import pixelengine.math.Vec2i;

import java.util.ArrayList;

public class Sprite {
	private final PixelBuffer buffer;
	private final ArrayList<Frame> frames = new ArrayList<>();

	public Sprite(String filename){
		buffer = new PixelBuffer(filename);
	}

	private class Frame {
		private RectI rect;
		private Vec2i origin;

		public Frame (RectI rect, Vec2i origin) {
			this.rect = rect;
			this.origin = origin;
		}

		public RectI getRect() {
			return rect;
		}

		public Vec2i getOrigin() {
			return origin;
		}
	}

	public void addFrame(RectI rect, Vec2i origin){
		Frame frame = new Frame(rect, origin);
		frames.add(frame);
	}

	public void render(PixelBuffer destBuffer, int frameNum, Vec2i pos ){
		Frame frame = frames.get(frameNum);
		pos = pos.sub(frame.getOrigin());
		destBuffer.blit(pos, buffer, frame.getRect(), false );
	}
}
