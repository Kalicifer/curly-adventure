package BnGaya;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Compression {
	
	static PriorityQueue<TREE> pq = new PriorityQueue<TREE>();
	static int[] freq = new int[300];
	static String[] ss = new String[300];
	static int exbits;
	static byte bt;
	static int cnt;
	static int ext = 0;
	static String decodeArr[] = new String[300];
	static char store[];
	static int index = 0;
	static String con = "";
	static ArrayList<Integer> result = new ArrayList<>();
	private static FileReader file;
	private static BufferedReader read;
	static String reader;
	static ArrayList<String> arr = new ArrayList<String>();
	static File fi;
	static File fi2;

	static class TREE implements Comparable<TREE> {
		TREE Lchild;
		TREE Rchild;

		public String deb;
		public int Bite;
		public int Freqnc;

		public int compareTo(TREE T) {

			if (this.Freqnc < T.Freqnc)
				return -1;
			if (this.Freqnc > T.Freqnc)
				return 1;
			return 0;
		}
	}

	static TREE Root;

	public static void CalFreq(String fname) {
		File file = null;
		Byte bt;
		file = new File(fname);
		try {
			FileInputStream file_input = new FileInputStream(file);
			DataInputStream data_in = new DataInputStream(file_input);
			while (true) {
				try {
					bt = data_in.readByte();
					freq[to(bt)]++;
				} catch (EOFException eof) {
					System.out.println("End of File");
					break;
				}
			}
			file_input.close();
			data_in.close();
		} catch (IOException e) {
			System.out.println("IO Exception =: " + e);
		}
		file = null;
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

	public static void initHzipping() {
		int i;
		cnt = 0;
		if (Root != null)
			fredfs(Root);

		for (i = 0; i < 300; i++)
			freq[i] = 0;

		for (i = 0; i < 300; i++) {
			ss[i] = "";
		}
		pq.clear();
	}

	public static void fredfs(TREE now) {
		if (now.Lchild == null && now.Rchild == null) {
			now = null;
			return;
		}
		if (now.Lchild != null)
			fredfs(now.Lchild);
		if (now.Rchild != null)
			fredfs(now.Rchild);
	}

	public static void dfs(TREE now, String st) {

		now.deb = st;
		if ((now.Lchild == null) && (now.Rchild == null)) {
			ss[now.Bite] = st;
			decodeArr[now.Bite] = st;
			return;
		}
		if (now.Lchild != null) {
			dfs(now.Lchild, st + "0");
		}
		if (now.Rchild != null) {
			dfs(now.Rchild, st + "1");
		}

	}

	public static void MakeNode() {
		for (int i = 0; i < 300; i++) {
			decodeArr[i] = "";
		}
		int i;
		pq.clear();

		for (i = 0; i < 300; i++) {
			if (freq[i] != 0) {

				TREE Temp = new TREE();
				Temp.Bite = i;
				Temp.Freqnc = freq[i];
				Temp.Lchild = null;
				Temp.Rchild = null;
				pq.add(Temp);
				cnt++;
			}

		}
		TREE Temp1, Temp2;

		if (cnt == 0) {
			return;
		} else if (cnt == 1) {
			for (i = 0; i < 300; i++)
				if (freq[i] != 0) {
					ss[i] = "0";
					break;
				}
			return;
		}
		while (pq.size() != 1) {
			TREE Temp = new TREE();
			Temp1 = pq.poll();
			Temp2 = pq.poll();
			Temp.Lchild = Temp1;
			Temp.Rchild = Temp2;
			Temp.Freqnc = Temp1.Freqnc + Temp2.Freqnc;
			pq.add(Temp);
		}
		Root = pq.poll();
	}

	public static void encrypt(String fname) {
		File file = null;

		file = new File(fname);
		try {
			FileInputStream file_input = new FileInputStream(file);
			DataInputStream data_in = new DataInputStream(file_input);
			while (true) {
				try {

					bt = data_in.readByte();
					freq[bt]++;
				} catch (EOFException eof) {
					System.out.println("End of File");
					break;
				}
			}
			file_input.close();
			data_in.close();

		} catch (IOException e) {
			System.out.println("IO Exception =: " + e);
		}
		file = null;
	}

	public static void fakezip(String fname) {

		File filei, fileo;
		int i;

		filei = new File(fname);
		fileo = new File("fakezipped.txt");
		try {
			FileInputStream file_input = new FileInputStream(filei);
			DataInputStream data_in = new DataInputStream(file_input);
			PrintStream ps = new PrintStream(fileo);

			while (true) {
				try {
					bt = data_in.readByte();
					ps.print(ss[to(bt)]);
				} catch (EOFException eof) {
					System.out.println("End of File");
					break;
				}
			}

			file_input.close();
			data_in.close();
			ps.close();

		} catch (IOException e) {
			System.out.println("IO Exception =: " + e);
		}
		filei = null;
		fileo = null;

	}

	public static void realzip(String fname, String fname1) {
		File filei, fileo;

		filei = new File(fname);
		fileo = new File(fname1);

		try {
			FileInputStream file_input = new FileInputStream(filei);
			DataInputStream data_in = new DataInputStream(file_input);
			FileOutputStream file_output = new FileOutputStream(fileo);
			DataOutputStream data_out = new DataOutputStream(file_output);

			long texbits;
			texbits = filei.length() % 8;/// reads no. of characters
			System.out.println("size nhai " + filei.length());
			System.out.println("tetxbits " + texbits);
			texbits = (8 - texbits) % 8;
			exbits = (int) texbits;
			ext = exbits;
			System.out.println("This s extrabit " + exbits);
			data_out.write(exbits);
			int length = 0;
			while (true) {
				try {
					bt = 0;
					length = 0;
					byte ch;
					// String cv="";
					for (exbits = 0; exbits < 8; exbits++) {
						ch = data_in.readByte();
						length *= 2;
						bt *= 2;
						if (ch == '1') {
							length++;
							bt++;
						}

					}
					data_out.write(length);
				} catch (EOFException eof) {
					int x;
					if (exbits != 0) {
						for (x = exbits; x < 8; x++) {
							bt *= 2;
							length *= 2;

						}
						data_out.write(length);
					}

					exbits = (int) texbits;
					System.out.println("End of File");
					break;
				}
			}
			data_in.close();
			data_out.close();
			file_input.close();
			file_output.close();

		} catch (IOException e) {
			System.out.println("IO exception = " + e);
		}
		// filei.delete();
		filei = null;
		fileo = null;
	}

	public static void beginHzipping(String arg1) {

		initHzipping();
		CalFreq(arg1);
		MakeNode();
		if (cnt > 1)
			dfs(Root, "");

		fakezip(arg1);
		realzip("fakezipped.txt", "realZipped.txt");
		initHzipping();
		unZip2("realZipped.txt");
		FileHandleing();

	}

	public static void decode(String line) {
		store = line.toCharArray();
		System.out.println("length is " + store.length);
		decodidation(Root);
		File file = new File("NewDecoded.txt");
		BufferedWriter a;
		try {
			a = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < result.size(); i++) {
				int intt = new Integer(result.get(i));
				a.write((char) intt);
			}

			a.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

	}

	public static void decodeAgain(TREE Node) {

		try {
			if (Node == null) {
				con = "";
				return;
			} else if (Node.Lchild == null && Node.Rchild == null) {
				if (con.equals(Node.deb)) {
					result.add(Node.Bite);
					con = "";
				}
				decodeAgain(Root);
				return;

			}

			else if (Node != null) {
				if (store[index] == '0') {

					con += store[index];
					index++;

					decodeAgain(Node.Lchild);
					return;
				} else {
					con += store[index];
					index++;
					decodeAgain(Node.Rchild);
					return;

				}
			}
		} catch (Exception e) {
			return;
		}

	}

	public static void decodidation(TREE Node) {
		try {
			while (true) {
				if (index > store.length)
					break;
				if (Node == null) {
					con = "";
					return;
				}

				else if ((Node.Lchild != null || Node.Rchild != null)) {
					if (index >= store.length)
						break;
					if (store[index] == '0') {
						con += store[index];
						index++;
						Node = Node.Lchild;
					} else {
						con += store[index];
						index++;
						Node = Node.Rchild;
					}
				}

				else if (Node.Lchild == null && Node.Rchild == null) {
					if (con.equals(Node.deb)) {
						result.add(Node.Bite);
						con = "";
						Node = Root;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	static void iterative_preorder() {
		Stack<TREE> s = new Stack<TREE>();
		TREE P = Root;
		while (P != null) {
			System.out.println(P.deb);
			if (P.Rchild != null)
				s.push(P.Rchild);

			P = P.Lchild;
			if (P == null || s.isEmpty())
				if (!s.isEmpty())
					P = s.pop();
		}

	}

	public static void FileHandleing() {
		reader = "";
		try {
			file = new FileReader("UnZip2.txt");
			read = new BufferedReader(file);
			filereader();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void filereader() {
		try {

			String line = read.readLine();
			while (line != null) {
				decode(line);
				line = read.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void unZip2(String filename) {
		File filei = new File(filename);
		File fileo = new File("UnZip2.txt");
		FileInputStream file_input = null;
		ArrayList<Byte> ab = new ArrayList<Byte>();
		try {
			file_input = new FileInputStream(filei);
			DataInputStream data_in = new DataInputStream(file_input);
			while (true) {
				ab.add(data_in.readByte());
			}

		} catch (Exception e1) {
			System.out.println("File ended unzpi2");
		}

		BufferedWriter file_output = null;
		try {
			file_output = new BufferedWriter((new FileWriter(fileo)));
			String s = "";
			for (int i = 0; i < ab.size(); i++) {
				if (i == 0) {
					ext = ab.get(i);
				} else {

					s = toBinary(to(ab.get(i)));
					if (i == arr.size() - 1)
						s = s.substring(0, s.length() - ext);
					file_output.write(s);
					s = "";
				}

			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		try {
			System.out.println("This is closed");
			file_output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void unZip(String filename) {
		try {
			String line = new BufferedReader(new FileReader(new File(filename))).readLine();
			String reader = "";
			while (line != null) {
				reader += line;
				line = new BufferedReader(new FileReader(new File(filename))).readLine();

			}
			File f = new File("unZip.txt");

			BufferedWriter uff = new BufferedWriter(new FileWriter(f));
			char[] arr = reader.toCharArray();
			String s = "";
			for (int i = 0; i < arr.length; i++) {
				if (i == 0) {
					ext = arr[i];
				} else {
					if (i == arr.length - 1)
						s = s.substring(0, s.length() - ext);

					uff.write(s);
					s = "";
				}

			}

			uff.write(s);
			uff.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String manually(char a) {
		int c = a;
		int e = 0;
		String d = "";
		while (c != 1 && c != 0) {
			e = c % 2;
			c = c / 2;
			d = e + d;
		}
		d = c + d;
		if (d.length() < 8) {
			for (int i = 0; i < 8 - d.length(); i++)
				d = "0" + d;
		}
		return d;
	}

	public static String toBinary(int b) {
		int arr[] = new int[8];
		String s = "";
		for (int i = 0; i < 8; i++) {
			arr[i] = (b % 2);
			b = b / 2;
		}
		for (int i = 7; i >= 0; i--) {
			s += arr[i];
		}

		return s;

	}

}