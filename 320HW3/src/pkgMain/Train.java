package pkgMain;

public class Train {
	int start_time;
	int length;
	
	public Train(int s, int l) {
		start_time = s % 24;
		length = l;
	}
}
