package pixelengine.graphics;

import pixelengine.Resources;
import pixelengine.math.RectI;
import pixelengine.math.Vec2i;

public class Font {

	private PixelBuffer buffer;
	private Glyph[] glyphArray = new Glyph[128];

	private int[] advanceData = {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			3, 3, 5, 7, 6, 6, 7, 3, 4, 4, 5, 5, 4, 4, 3, 6,
			6, 5, 6, 5, 6, 6, 6, 5, 6, 6, 3, 4, 5, 4, 5, 5,
			6, 6, 6, 6, 6, 6, 6, 6, 6, 5, 6, 6, 6, 7, 6, 6,
			6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 4, 7, 4, 5, 5,
			4, 6, 6, 5, 6, 6, 5, 6, 6, 3, 6, 6, 4, 7, 6, 6,
			6, 6, 6, 5, 5, 6, 5, 7, 5, 6, 6, 5, 3, 5, 6, 0
	};

	private class Glyph {
		private RectI rect;
		private int advance;

		public Glyph (RectI rect, int advance) {
			this.rect = rect;
			this.advance = advance;
		}

		public RectI getRect() {
			return rect;
		}

		public int getAdvance() {
			return advance;
		}
	}

	public Font(String resource) {
		this.buffer = Resources.loadPixelBuffer(resource);

		int c = 0;

		for(int e = 0; e < 32; e++){
			glyphArray[c] = new Glyph(new RectI(0, 0, 8, 12), advanceData[c]);
			c++;
		}

		for(int y = 0; y < 6; y++){
			for(int x = 0; x < 16; x++){
				glyphArray[c] = new Glyph(new RectI(x * 8, y * 12, 8, 12), advanceData[c]);
				c++;
			}
		}
	}

	public int getTextWidth(String text){
		//get accumlated advance for each line, set MAX and revert to 0
		int advanceAcc = 0;
		int maxAccum = 0;

		for(int i = 0; i < text.length(); i++ ){
			char charToPrint = text.charAt(i);

			if (charToPrint == '\n'){
				maxAccum = Math.max(advanceAcc, maxAccum);
				advanceAcc = 0;
			} else {
				Glyph glyph = glyphArray[charToPrint];
				advanceAcc += glyph.getAdvance();
			}
		}
		return Math.max(advanceAcc, maxAccum);

	}

	public int getTextHeight(String text){
		//get accumlated advance for each line, set MAX and revert to 0
		int heightAccum = text.isEmpty() ? 0 : 10;

		for(int i = 0; i < text.length(); i++ ){
			char charToPrint = text.charAt(i);

			if (charToPrint == '\n'){
				heightAccum += 10;
			}
		}
		return heightAccum;
	}

	public Vec2i getTextSize(String text){
		return new Vec2i(getTextWidth(text), getTextHeight(text));
	}

	public void drawFont(PixelBuffer dstBuffer, Vec2i v, String text ){

		int currX = 0;
		int currY = -3;

		for (int i = 0; i < text.length(); i++) {
			char charToPrint = text.charAt(i);

			if (charToPrint == '\n'){
				currX = 0;
				currY += 10;
			} else {
				Glyph glyph = glyphArray[charToPrint];
				dstBuffer.blit(v.getX() + currX, v.getY() + currY, buffer, glyph.getRect(), false);
				currX += glyph.getAdvance();

			}
		}

	}
}
