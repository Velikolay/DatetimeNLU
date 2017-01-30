package dateparser.places;

/**
 * Created by nikolayivanov on 1/23/17.
 */
public class PlaceInfo {
    private String airportName;
    private String airportCode;
    private String cityName;
    private String cityCode;
    private String countryName;
    private String countryCode;

    public static boolean isGeneralization(PlaceInfo infoA, PlaceInfo infoB) {
        if (infoA.getCountryCode() == null || infoB.getCountryCode() == null ||
                !infoA.getCountryCode().equals(infoB.getCountryCode())) return false;

        if (infoA.getCityCode() != null && !infoA.getCityCode().equals(infoB.getCityCode())) return false;
        if (infoA.getAirportCode() != null && !infoA.getAirportCode().equals(infoB.getAirportCode())) return false;
        return true;
    }

    public PlaceInfo() {
    }

    public PlaceInfo(String airportName, String airportCode, String cityName, String cityCode, String countryName, String countryCode) {
        this.airportName = airportName;
        this.airportCode = airportCode;
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.countryName = countryName;
        this.countryCode = countryCode;
    }

    public boolean isEmpty() {
        return airportCode == null && cityCode == null && countryCode == null;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceInfo placeInfo = (PlaceInfo) o;

        if (airportCode != null ? !airportCode.equals(placeInfo.airportCode) : placeInfo.airportCode != null)
            return false;
        if (cityCode != null ? !cityCode.equals(placeInfo.cityCode) : placeInfo.cityCode != null) return false;
        return countryCode != null ? countryCode.equals(placeInfo.countryCode) : placeInfo.countryCode == null;
    }

    @Override
    public int hashCode() {
        int result = airportCode != null ? airportCode.hashCode() : 0;
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "airportName='" + airportName + '\'' +
                ", airportCode='" + airportCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

    public static void main(String[] args) {
        PlaceInfo plA = new PlaceInfo(null, "xyz", null, "lon", null, "uk");
        PlaceInfo plB = new PlaceInfo(null, null, null, "lon", null, "uk");
        System.out.println(PlaceInfo.isGeneralization(plA, plB));
    }
}
