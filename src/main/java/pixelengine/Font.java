package pixelengine;

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
		private RectangleI rect;
		private int advance;

		public Glyph (RectangleI rect, int advance) {
			this.rect = rect;
			this.advance = advance;
		}

		public RectangleI getRect() {
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
			glyphArray[c] = new Glyph(new RectangleI(0, 0, 8, 12), advanceData[c]);
			c++;
		}

		for(int y = 0; y < 6; y++){
			for(int x = 0; x < 16; x++){
				glyphArray[c] = new Glyph(new RectangleI(x * 8, y * 12, 8, 12), advanceData[c]);
				c++;
			}
		}
	}

	public int getTextWidth(String text){
		//get accumlated advance for each line, set MAX and revert to 0
		int advanceCum = 0;
		int maxAccum = 0;

		System.out.println(text);

		for(int i = 0; i < text.length(); i++ ){
			char charToPrint = text.charAt(i);

			if (charToPrint == '\n'){
				maxAccum = Math.max(advanceCum, maxAccum);
				advanceCum = 0;
				System.out.println("break was reached");
			} else {
				Glyph glyph = glyphArray[charToPrint];
				advanceCum += glyph.getAdvance();
				System.out.println("clear");
			}
			System.out.println(advanceCum);
			System.out.println(maxAccum);

		}

		System.out.println(Math.max(advanceCum, maxAccum));
		return Math.max(advanceCum, maxAccum);

	}

	public void drawFont(PixelBuffer dstBuffer, VectorI v, String text ){

		int currX = 0;
		int currY = 0;

		for (int i = 0; i < text.length(); i++) {
			char charToPrint = text.charAt(i);

			if (charToPrint == '\n'){
				currX = 0;
				currY += 10;
			} else {
				Glyph glyph = glyphArray[charToPrint];
				dstBuffer.drawSprite(v.getX() + currX, v.getY() + currY, buffer, glyph.getRect(), false);
				currX += glyph.getAdvance();

			}
		}

	}
}
