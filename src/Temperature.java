/**
 * Otabek Aripdjanov
 *
 * Stores low and high temperature data for a city/state 
 */
public class Temperature {
    private String city;
    private String state;
    private int lowTemperature;
    private int highTemperature;

    public Temperature(String city, String state, String high, String low) {
        this.city = city;
        this.state = state;
        this.lowTemperature = Integer.parseInt(low);
        this.highTemperature = Integer.parseInt(high);;
    }

    public int differential() {
        return Math.abs(this.lowTemperature - this.highTemperature);
    }

    public String getCity() {
        return this.city;
    }

    public int getHigh() {
        return this.highTemperature;
    }
    /* Add appropriate instance attributes, constructors, and accessor/mutator methods */
}