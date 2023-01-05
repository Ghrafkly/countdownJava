package org.countdownJava.Archive.Bytes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WriteToFile {
	public void write(Map<Integer, Long> solutions) {
		File file = new File("src/main/java/org/countdownJava/current/solutions.txt");
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (Map.Entry<Integer, Long> entry : solutions.entrySet()) {
				writer.write("%d = %d".formatted(entry.getKey(), entry.getValue()));
				writer.newLine();
			}
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
