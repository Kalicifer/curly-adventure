package BnGaya;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BWTAd {

	private StringTokenizer isolator;
	private String Tokens[];
	static ArrayList<String> bwtToken = new ArrayList<String>();
	private static ArrayList<String> bwtToken2 = new ArrayList<String>();
	int i = 0;

	public BWTAd(String text) {
		isolator = new StringTokenizer(text, "\n ", true);
	}

	public void Encode() {
		int temp = 0;
		// bwtToken = new String[isolator.countTokens()];
		Tokens = new String[isolator.countTokens()];
		while (isolator.hasMoreTokens()) {
			Tokens[temp] = isolator.nextToken();
			Encode(Tokens[temp] + "$", i);
			temp++;
			i += temp;
		}
	}

	public void Encode(String s, int k) {
		if (s.contains(" ")) {
			bwtToken.add(s);
		} else {
			String t = s + s;
			String BWT = "";
			int size = s.length();
			String[] s1 = new String[size];
			for (int i = 0; i < size; i++) {
				s1[i] = t.substring(i, i + size);
			}
			Arrays.sort(s1);
			for (int i = 0; i < size; i++) {
				BWT += s1[i].charAt(size - 1);
			}
			bwtToken.add(BWT);
		}
	}

	public void Decode() {
		int temp = 0;
		// bwtToken = new String[isolator.countTokens()];
		Tokens = new String[isolator.countTokens()];
		while (isolator.hasMoreTokens()) {
			Tokens[temp] = isolator.nextToken();
			Decode(Tokens[temp], i);
			temp++;
			i += temp;
		}
	}

	public void Decode(String s, int k) {
		if (s.equals(" "))
			bwtToken2.add(s);
		int size = s.length();
		String BWT[] = new String[size];
		for (int j = 0; j < size; j++) {
			BWT[j] = "";
		}
		for (int j = 0; j < size; j++) {
			int count = 0;
			for (int i = 0; i < size; i++) {
				BWT[i] = s.charAt(count) + BWT[i];
				count++;
			}
			Arrays.sort(BWT);
		}
		for (int m = 0; m < size; m++) {

			if (BWT[m].charAt(size - 1) == ('$')) {
				bwtToken2.add(BWT[m].replace("$", ""));
			}
		}
	}

	public ArrayList<String> getBWT() {
		return bwtToken;
	}

	public ArrayList<String> get_De_BWT() {
		return bwtToken2;
	}
}