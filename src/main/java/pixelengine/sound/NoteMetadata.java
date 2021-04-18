package pixelengine.sound;

public class NoteMetadata {
	
	public Type type;
	public double start;
	public double data;
	
	public enum Type {
		None,
		Blend,
		VibSpeed,
		VibDepth,
		DutyCycle,
		Volume
	}
	
	public NoteMetadata(Type type, double start, double data) {
		this.type = type;
		this.start = start;
		this.data = data;
	}
	
	public static NoteMetadata fromString(String str, int octave) {
		
		String t[] = str.split(":");
		
		Type type = Type.None;
		double data = Double.NaN;
		double time = 0.0;
		
		if(t.length == 2) {
			time = Double.parseDouble(t[0]);
			
			String event = t[1];
			char eChar = event.charAt(0);
			
			switch(eChar) {
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'E':
				case 'F':
				case 'G':
					type = Type.Blend;
					data = MusicMath.note(event, octave);
					break;
				case 'S': type = Type.VibSpeed; break;
				case 'T': type = Type.VibDepth; break;
				case 'U': type = Type.DutyCycle; break;
				case 'V': type = Type.Volume; break;
			}
			
			if(Double.isNaN(data) && type != Type.None) {
				String subStr = event.substring(1, event.length() - 1);
				data = Double.parseDouble(subStr);
			}
			
		}
		
		return new NoteMetadata(type, time, data);
	}
	
	public static NoteMetadata[] processNoteMetadata(String metaStr, int octave) {
		
		if(metaStr == null) {
			return null;
		}
		
		String s[] = metaStr.split(",");
		
		if(s.length > 0) {
			NoteMetadata[] nmd = new NoteMetadata[s.length];
			
			for(int i = 0; i < s.length; i++) {
				nmd[i] = fromString(s[i], octave);
			}
			
			return nmd;
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return type + " " + start + " " + data;
	}
}
