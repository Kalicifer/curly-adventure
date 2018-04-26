package BnGaya;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileHandlee {
	private BufferedReader read;
	private StringBuilder reader;

	public FileHandlee(String name) {
		reader = new StringBuilder();
		try {
			read = new BufferedReader(new FileReader(new File(name)));
			filereader();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void filereader() {
		try {
			String line = read.readLine();
			while (line != null) {
				reader.append(line + " ");
				line = read.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getText() {
		return reader.substring(0, reader.length());
	}
}
