package pkgMain;

public class Train {
	final int start_time;
	final int length;
	
	public Train(int s, int l) {
		start_time = s % 24;
		length = l;
	}
}
