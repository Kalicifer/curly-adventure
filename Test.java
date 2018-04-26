package BnGaya;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Test {
	public static void main(String args[]) {
		// Main Files:
		// RealZipped
		// theDEBWHT

		FileHandlee fi = new FileHandlee("C:\\New folder\\Forty Rules.txt");
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
		MTF asa = new MTF();
		asa.encode("thenewBWT.txt");
		Compression.beginHzipping("MTFen.txt");
		asa.decode("NewDecoded.txt");

		FileHandlee fi2 = new FileHandlee("mtfDn.txt");
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

	}

}
