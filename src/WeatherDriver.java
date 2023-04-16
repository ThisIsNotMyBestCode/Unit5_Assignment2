/**
 * Otabek Aripdjanov
 * This is a driver class for WeatherReport class
 */
public class WeatherDriver {

    public static void main(String[] args) {
        System.out.println("Starting simulation");

        String fileName = "./src/weather.txt";
        try {
            // Create a new WeatherReport object with default constructor
            WeatherReport wr1 = new WeatherReport();
            System.out.println("Before sorting by city: " +  wr1.isSortedByCity());
            long start1 = System.currentTimeMillis();
            wr1.sortWithCollections("City");
            long end1 = System.currentTimeMillis();
            System.out.println("After sorting by city: " + wr1.isSortedByCity());
            System.out.println("Time taken to sort by city: " + (end1 - start1) + " ms");

            // Create a new WeatherReport object using the file name constructor
            WeatherReport wr2 = new WeatherReport(fileName);
            System.out.println("Before sorting by high: " +  wr2.isSortedByHigh());
            long start2 = System.currentTimeMillis();
            wr2.sortWithMerge("High");
            long end2 = System.currentTimeMillis();
            System.out.println("After sorting by high: " + wr2.isSortedByHigh());
            System.out.println("Time taken to sort by high: " + (end2 - start2) + " ms");

        }
        catch (Exception e) {
            System.out.println("Other error: " + e.getMessage());
        }

        System.out.println("Finished simulation");
    }
}



