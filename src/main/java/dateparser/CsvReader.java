package dateparser;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nikolayivanov on 1/20/17.
 */
public class CsvReader {
    private static final String SEPARATOR = ",";

    private final Reader source;

    public CsvReader(Reader source) {
        this.source = source;
    }

    public List<String> readHeader() {
        try (BufferedReader reader = new BufferedReader(source)) {
            return reader.lines()
                    .findFirst()
                    .map(line -> Arrays.asList(line.split(SEPARATOR)))
                    .get();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<List<String>> readRecords() {
        try (BufferedReader reader = new BufferedReader(source)) {
            return reader.lines()
                    .map(line -> Arrays.asList(line.split(SEPARATOR)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void writeCsvFile(String fileName, List<List<String>> csvTable, int[] columnFilterIdx) throws IOException {
        int max=columnFilterIdx[0];

        for (int i = 0; i < columnFilterIdx.length; i++) {
            if (columnFilterIdx[i] > max) {
                max = columnFilterIdx[i];
            }
        }

        PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        for (List<String> row: csvTable) {
            if (row.size() > max) {
                StringBuffer rowStr = new StringBuffer();
                for (int colIdx : columnFilterIdx) {
                    if (rowStr.length() != 0) {
                        rowStr.append(",");
                    }
                    rowStr.append(row.get(colIdx).toLowerCase());
                }
                pw.write(rowStr.toString() + "\n");
            }
        }
        pw.close();
    }

    public static void main(String[] args) throws IOException {
        FileReader in = new FileReader("src/main/resources/places/places.csv");
        CsvReader csvReader = new CsvReader(new BufferedReader(in));
        List<List<String>> csvTable = csvReader.readRecords();
        int[] airportcodes = {0, 1};
        int[] citycodes = {1, 3};
        int[] citynames = {8, 1};
        writeCsvFile("src/main/resources/places/airportcodes.csv", csvTable, airportcodes);
        writeCsvFile("src/main/resources/places/citycodes.csv", csvTable, citycodes);
        writeCsvFile("src/main/resources/places/citynames.csv", csvTable, citynames);
    }
}
