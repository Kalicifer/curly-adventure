package BnGaya;

public class HuffMTF {
	public static void main(String[] args) {
		// Files Used: RealZipped FakeZipped Uzip2 NewDecoded
		// Files Main:
		// RealZipped
		// mtfDn

		MTF asa = new MTF();
		asa.encode("C:\\New folder\\Forty Rules.txt");
		Compression.beginHzipping("mtfEn.txt");
		MTF asa2 = new MTF();
		asa2.decode("NewDecoded.txt");
	}
}
