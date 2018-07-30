package com.nina.goal.keeper.tracking.data.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import org.slf4j.LoggerFactory;

public class FileReaderWriter {
	private String filePath;
	private String fileName;
	private String data;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FileReaderWriter.class);

	public FileReaderWriter(String filePath, String fileName, String data) {
		this.fileName = fileName;
		this.filePath = filePath;
		this.data = data;
	}

	public String readFile() {
		String line = null;
		StringBuilder dataBuilder = new StringBuilder();
		FileReader fileReader;
		try {
			fileReader = new FileReader(filePath + fileName);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				dataBuilder.append(line);
			}
			// Always close files.
			bufferedReader.close();
		} catch (Exception e) {
			logger.error("Error happend when reading the file", e);
		}
		data = dataBuilder.toString();
		return data;
	}

	public void writeFile() {
		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(filePath + fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter.write(data);

			// Always close files.
			bufferedWriter.close();
		} catch (Exception e) {
			logger.error("Error happend when writing the file", e);
		}
	}

}
