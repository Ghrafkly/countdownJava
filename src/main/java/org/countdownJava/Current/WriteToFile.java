package org.countdownJava.Current;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WriteToFile {
	public void write(Map<Integer, Long> solutions) {
		File file = new File("src/main/java/org/countdownJava/current/solutions.txt");
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(file));
			// write solutions in order 101 to 999
			for (int i = 101; i < 1000; i++) {
				if (solutions.containsKey(i)) {
					writer.write(i + " " + solutions.get(i));
					writer.newLine();
				}
			}

//			for (Map.Entry<Integer, Long> entry : solutions.entrySet()) {
//				writer.write("%d = %d".formatted(entry.getKey(), entry.getValue()));
//				writer.newLine();
//			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				assert writer != null;
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
