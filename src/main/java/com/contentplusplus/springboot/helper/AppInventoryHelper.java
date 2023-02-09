package com.contentplusplus.springboot.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import com.contentplusplus.springboot.model.AppInventory;

public class AppInventoryHelper {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "Title", "Description", "Active" };

	public static boolean hasCSVFormat(MultipartFile file) {
		System.out.println(file.getContentType());
		if (TYPE.equals(file.getContentType()) || file.getContentType().equals("application/vnd.ms-excel")) {
			return true;
		}

		return false;
	}

	public static List<AppInventory> csvToInventory(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<AppInventory> appInventoryList = new ArrayList<>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				AppInventory appInventory = new AppInventory(csvRecord.get("Title"), csvRecord.get("Description"),
						Boolean.parseBoolean(csvRecord.get("Active")));

				appInventoryList.add(appInventory);
			}

			return appInventoryList;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream inventoriesToCSV(List<AppInventory> appInventoryList) {
		final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format.withHeader(HEADERs));) {
			for (AppInventory appInventory : appInventoryList) {
				List<String> data = Arrays.asList(String.valueOf(appInventory.getId()), appInventory.getTitle(),
						appInventory.getDescription(), String.valueOf(appInventory.isActive()));

				csvPrinter.printRecord(data);
			}

			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		}
	}
}
