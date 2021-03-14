package pixelengine.graphics;

public interface IPixelCompositor {

	Pixel composite(Pixel src, Pixel dst);

	public static final IPixelCompositor NORMAL = (s, d) -> s; //source and dest
	public static final IPixelCompositor BLEND = (s, d) -> d.blend(s);
	public static final IPixelCompositor ADD = (s, d) -> d.add(s);
	public static final IPixelCompositor MUL = (s, d) -> d.mul(s);
	public static final IPixelCompositor SUB = (s, d) -> d.sub(s);
	public static final IPixelCompositor GRAY = (s, d) -> s.gray();
	public static final IPixelCompositor INV = (s, d) -> s.inv();


}
