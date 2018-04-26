package BnGaya;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class MTFBWTHuff {
	public static void main(String[] args) {
		// Files Used: MTFen thenewBWT fakezipped realzipped unzip newDecoded theDEBWHT
		// mtfDn
		// Main Files:
		// realzipped
		// mtfDn

		MTF asa = new MTF();
		asa.encode("C:\\New folder\\Forty Rules.txt");

		FileHandlee fi = new FileHandlee("MTFen.txt");
		String by = fi.getText();

		BWTAd aw = new BWTAd(by);
		aw.Encode();
		ArrayList<String> aqr = aw.getBWT();

		try {
			BufferedWriter f = new BufferedWriter(new FileWriter(new File("thenewBWT.txt")));
			for (int i = 0; i < aqr.size(); i++)
				f.write(aqr.get(i));

			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Compression.beginHzipping("thenewBWT.txt");

		FileHandlee fi2 = new FileHandlee("NewDecoded.txt");
		String byy = fi2.getText();

		BWTAd aww = new BWTAd(byy);
		aww.Decode();
		ArrayList<String> aqrr = aww.get_De_BWT();

		try {
			BufferedWriter f = new BufferedWriter(new FileWriter(new File("theDEBWHT.txt")));
			for (int i = 0; i < aqrr.size(); i++)
				f.write(aqrr.get(i));

			f.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MTF p = new MTF();
		p.decode("theDEBWHT.txt");

	}
}
