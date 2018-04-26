package BnGaya;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class MTF {

	public static List<Integer> encode(List<String> msg, String symTable) {
		List<Integer> output = new LinkedList<Integer>();
		StringBuilder s = new StringBuilder(symTable);
		List<String> forEncode = new LinkedList<String>();

		for (String string : msg) {
			for (char c : string.toCharArray()) {
				int idx = s.indexOf("" + c);
				output.add(idx);
				s = s.deleteCharAt(idx).insert(0, c);
			}
		}
		return output;
	}

	public static String decode(List<Integer> idxs, String symTable) {

		StringBuilder output = new StringBuilder();
		StringBuilder s = new StringBuilder(symTable);
		for (int idx : idxs) {
			char c = s.charAt(idx);
			output = output.append(c);
			s = s.deleteCharAt(idx).insert(0, c);
		}
		return output.toString();
	}

	public void encode(String file) {
		String symTable = "";// abcdefghijklmnopqrstuvwxyz
		char characters[] = new char[9000];
		for (int i = 0; i < characters.length; i++) {
			characters[i] += i;
			symTable = symTable + characters[i];
		}
		try {
			BufferedReader buf = new BufferedReader(new FileReader(new File(file)));// Forty-Rules (1)
			BufferedWriter bu = new BufferedWriter(new FileWriter(new File("mtfEn.txt")));
			String reader = "";
			String g = buf.readLine();
			while (g != null) {
				reader += g + " ";
				g = buf.readLine();
			}
			List<String> Read = new LinkedList<String>();
			StringTokenizer st = new StringTokenizer(reader, " ", true);
			String[] bwtToken = new String[st.countTokens()];

			for (int i = 0; i < bwtToken.length; i++) {
				Read.add(st.nextToken());
			}
			List<Integer> encoded = encode(Read, symTable);
			st = new StringTokenizer(encoded.toString(), ",[]", false);
			StringBuilder sb = new StringBuilder();
			while (st.hasMoreTokens()) {
				sb.append(st.nextToken());
			}

			bu.write(sb.toString());

			System.out.println("Written");
			buf.close();
			bu.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void decode(String file) {
		String symTable = "";// abcdefghijklmnopqrstuvwxyz
		char characters[] = new char[9000];
		for (int i = 0; i < characters.length; i++) {
			characters[i] += i;
			symTable = symTable + characters[i];
		}

		try {
			BufferedReader buf = new BufferedReader(new FileReader(new File(file)));
			BufferedWriter bu = new BufferedWriter(new FileWriter(new File("mtfDn.txt")));

			String reader = "";
			String g = buf.readLine();
			while (g != null) {
				reader += g;
				g = buf.readLine();
			}

			StringTokenizer st = new StringTokenizer(reader, " ", false);
			List<Integer> write = new LinkedList<Integer>();
			String[] bwtToken = new String[st.countTokens()];
			for (int i = 0; i < bwtToken.length; i++) {
				write.add(new Integer(st.nextToken()));
			}

			String decoded = decode(write, symTable);
			bu.write(decoded);

			buf.close();
			bu.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int to(Byte b) {
		int ret = b;
		if (ret < 0) {
			ret = ~b;
			ret = ret + 1;
			ret = ret ^ 255;
			ret += 1;
		}
		return ret;
	}
}
