package dateparser.places;

import dateparser.CsvReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nikolayivanov on 1/23/17.
 */
public class PlaceDataSource {
    private static final String RESOURCES_FOLDER = "src/main/resources/places";
    private static final String AIRPORT_CODES = RESOURCES_FOLDER + File.separator + "airportcodes.csv";
    private static final String AIRPORT_NAMES = RESOURCES_FOLDER + File.separator + "airportnames.csv";
    private static final String CITY_CODES = RESOURCES_FOLDER + File.separator + "citycodes.csv";
    private static final String CITY_NAMES = RESOURCES_FOLDER + File.separator + "citynames.csv";
    private static final String COUNTRY_NAMES = RESOURCES_FOLDER + File.separator + "countrynames.csv";
    private static final String IGNORE_WORDS = RESOURCES_FOLDER + File.separator + "ignore.csv";

    private Map<String, String> airportNameToAirportCode;
    private Map<String, String> airportCodeToCityCode;
    private Map<String, String> cityNameToCityCode;
    private Map<String, String> cityCodeToCountryCode;
    private Map<String, String> countryNameToCountryCode;

    private Set<String> wordsToIgnore;

    public PlaceDataSource() {
        this.airportCodeToCityCode = new HashMap<>();
        this.airportNameToAirportCode = new HashMap<>();
        this.cityCodeToCountryCode = new HashMap<>();
        this.cityNameToCityCode = new HashMap<>();
        this.countryNameToCountryCode = new HashMap<>();
        this.wordsToIgnore = new HashSet<>();
    }

    public void initialize() throws IOException {
        this.airportCodeToCityCode = this.loadPlaceDataFromFile(AIRPORT_CODES);
        this.airportNameToAirportCode = this.loadPlaceDataFromFile(AIRPORT_NAMES);
        this.cityCodeToCountryCode = this.loadPlaceDataFromFile(CITY_CODES);
        this.cityNameToCityCode = this.loadPlaceDataFromFile(CITY_NAMES);
        this.countryNameToCountryCode = this.loadPlaceDataFromFile(COUNTRY_NAMES);
        this.wordsToIgnore = this.loadIgnoreWordsFromFile(IGNORE_WORDS);
    }

    public PlaceInfo getPlaceInfo(String text) {
        PlaceInfo placeInfo = new PlaceInfo();
        if(wordsToIgnore.contains(text)) {
            return placeInfo;
        }
        String key = text;
        if (key != null && airportNameToAirportCode.containsKey(key)) {
            placeInfo.setAirportName(key);
            key = airportNameToAirportCode.get(key);
        }

        if (key != null && airportCodeToCityCode.containsKey(key)) {
            placeInfo.setAirportCode(key);
            key = airportCodeToCityCode.get(key);
        }

        if (key != null && cityCodeToCountryCode.containsKey(key)) {
            placeInfo.setCityCode(key);
            key = cityCodeToCountryCode.get(key);
            placeInfo.setCountryCode(key);
        }

        if (placeInfo.isEmpty()) {
            if (key != null && cityNameToCityCode.containsKey(key)) {
                placeInfo.setCityName(key);
                key = cityNameToCityCode.get(key);
            }

            if (key != null && cityCodeToCountryCode.containsKey(key)) {
                placeInfo.setCityCode(key);
                key = cityCodeToCountryCode.get(key);
                placeInfo.setCountryCode(key);
            }
        }

        if (placeInfo.isEmpty()) {
            if (key != null && countryNameToCountryCode.containsKey(key)) {
                placeInfo.setCountryName(key);
                key = countryNameToCountryCode.get(key);
                placeInfo.setCountryCode(key);
            }
        }
        return placeInfo;
    }

    private Set<String> loadIgnoreWordsFromFile(String fileName) throws IOException {
        Map<String,String> res = new HashMap<>();
        FileReader in = new FileReader(fileName);
        CsvReader csvReader = new CsvReader(new BufferedReader(in));
        List<List<String>> csvTable = csvReader.readRecords();
        return csvTable.stream().map(x -> x.get(0))
                .collect(Collectors.toSet());
    }

    private Map<String,String> loadPlaceDataFromFile(String fileName) throws IOException {
        Map<String,String> res = new HashMap<>();
        FileReader in = new FileReader(fileName);
        CsvReader csvReader = new CsvReader(new BufferedReader(in));
        List<List<String>> csvTable = csvReader.readRecords();
        for(List<String> row: csvTable) {
            if(!res.containsKey(row.get(0).toLowerCase())) {
                res.put(row.get(0).toLowerCase(), row.get(1).toLowerCase());
            }
        }
        return res;
        //return csvTable.stream().map(x -> x.get(0) + ',' + x.get(1))
        //        .collect(Collectors.toMap(k -> k.split(",")[0].toLowerCase(), v -> v.split(",")[1].toLowerCase()));
    }

    public static void main(String[] args) throws IOException {
        PlaceDataSource pds = new PlaceDataSource();
        pds.initialize();
    }

}
