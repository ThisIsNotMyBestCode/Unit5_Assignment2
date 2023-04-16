import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Otabek Aripdjanov
 * This is a WeatherReport class
 * that reads data from a file then sorts it by two different methods.
 * One is the built-in sort() method, and mergeSort algorithm.
 */
public class WeatherReport {

    private LinkedList<Temperature> temperatureList = new LinkedList<Temperature>();

    // Constructor
    public WeatherReport() {

        String example1 = "39,Birmingham,BHM,33,1/3/2016,46,32,1,0,4.33,Alabama,3,2016";
        String example2 = "39,Huntsville,HSV,32,1/3/2016,47,31,1,0,3.86,Alabama,3,2016";
        String example3 = "46,Mobile,MOB,35,1/3/2016,51,41,1,0.16,9.73,Alabama,3,2016";
        String example4 = "45,Montgomery,MGM,32,1/3/2016,52,38,1,0,6.86,Alabama,3,2016";
        String example5 = "34,Anchorage,ANC,19,1/3/2016,38,29,1,0.01,7.8,Alaska,3,2016";
        String example6 = "38,Annette,ANN,9,1/3/2016,44,31,1,0.09,8.7,Alaska,3,2016";
        String example7 = "30,Bethel,BET,9,1/3/2016,36,24,1,0.05,16.46,Alaska,3,2016";
        String example8 = "22,Bettles,BTT,2,1/3/2016,32,9,1,0.15,3.1,Alaska,3,2016";

        String[] reportline1 = example1.split(",");
        String[] reportline2 = example2.split(",");
        String[] reportline3 = example3.split(",");
        String[] reportline4 = example4.split(",");
        String[] reportline5 = example5.split(",");
        String[] reportline6 = example6.split(",");
        String[] reportline7 = example7.split(",");
        String[] reportline8 = example8.split(",");

        temperatureList.add(new Temperature(reportline1[1],reportline1[10], reportline1[5], reportline1[6]));
        temperatureList.add(new Temperature(reportline2[1],reportline2[10], reportline2[5], reportline2[6]));
        temperatureList.add(new Temperature(reportline3[1],reportline3[10], reportline3[5], reportline3[6]));
        temperatureList.add(new Temperature(reportline4[1],reportline4[10], reportline4[5], reportline4[6]));
        temperatureList.add(new Temperature(reportline5[1],reportline5[10], reportline5[5], reportline5[6]));
        temperatureList.add(new Temperature(reportline6[1],reportline6[10], reportline6[5], reportline6[6]));
        temperatureList.add(new Temperature(reportline7[1],reportline7[10], reportline7[5], reportline7[6]));
        temperatureList.add(new Temperature(reportline8[1],reportline8[10], reportline8[5], reportline8[6]));

    }

    public WeatherReport(String fname) {
        try {
            Scanner scanner = new Scanner(new File(fname));

            scanner.nextLine(); // consume the newline character

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] reportline = line.split(",");

                temperatureList.add(new Temperature(reportline[1],reportline[10], reportline[5], reportline[6]));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Checks if the linked list is in sorted order by city
    public boolean isSortedByCity() {
        // If there is 1 or zero items in the list, then of course it will be sorted
        if (this.temperatureList.size() <= 1) {
            return true;
        }

        SortByCity sortByCity = new SortByCity();

        Iterator<Temperature> iter = this.temperatureList.iterator();
        Temperature prev = iter.next();
        while (iter.hasNext()) {
            Temperature current = iter.next();
            if (sortByCity.compare(prev, current) > 0) {
                return false;
            }
            prev = current;
        }
        return true;
    }

    // Checks if the linked list is in sorted order by highest temperature
    public boolean isSortedByHigh() {
        // If there is 1 or zero items in the list, then of course it will be sorted
        if (this.temperatureList.size() <= 1) {
            return true;
        }

        SortByHigh sortByHigh = new SortByHigh();

        Iterator<Temperature> iter = this.temperatureList.iterator();
        Temperature prev = iter.next();
        while (iter.hasNext()) {
            Temperature current = iter.next();
            if (sortByHigh.compare(prev, current) > 0) {
                return false;
            }
            prev = current;
        }
        return true;
    }

    public void sortWithCollections(String by) {
        if( by == "City") {
            System.out.println("Sorting by city...");
            Collections.sort(this.temperatureList, new SortByCity());

            for (Temperature temp : this.temperatureList) {
                System.out.println(temp.getCity());
            }
        }
        if( by == "High") {
            System.out.println("Sorting by high...");
            Collections.sort(this.temperatureList, new SortByHigh());

            for (Temperature temp : this.temperatureList) {
                System.out.println(temp.getHigh());
            }
        }
    }

    public void sortWithMerge(String by) {
        if (this.temperatureList == null || this.temperatureList.size() <= 1) {
            return;
        }
        int n = this.temperatureList.size();

        // divide the list into sublists of size 1 and merge them iteratively
        for (int size = 1; size < n; size *= 2) {
            // merge sublists of size "size"
            for (int left = 0; left < n - size; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);
                mergeHelper(this.temperatureList, left, mid, right, by);
            }
        }
    }

    private void mergeHelper(LinkedList<Temperature> tempList, int left, int mid, int right, String by) {
        // create temporary arrays for left and right sublists
        Temperature[] leftArray = new Temperature[mid - left + 1];
        Temperature[] rightArray = new Temperature[right - mid];

        // copy data to temporary arrays
        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = tempList.get(left + i);
        }
        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = tempList.get(mid + 1 + i);
        }

        // merge the temporary arrays back into the original list
        int i = 0, j = 0, k = left;
        while (i < leftArray.length && j < rightArray.length) {
            int diff = leftArray[i].differential() - rightArray[j].differential();

            if (by.equals("City") && leftArray[i].getCity().compareTo(rightArray[j].getCity()) <= 0
                    || by.equals("Differential") && diff <= 0) {
                tempList.set(k++, leftArray[i++]);
            } else {
                tempList.set(k++, rightArray[j++]);
            }
        }

        // copy remaining elements of left array
        while (i < leftArray.length) {
            tempList.set(k++, leftArray[i++]);
        }

        // copy remaining elements of right array
        while (j < rightArray.length) {
            tempList.set(k++, rightArray[j++]);
        }
    }
}
/*
 * Comparator to sort by the city stored in a Temperature object
 */
class SortByCity implements Comparator<Temperature> {
    // Used for sorting in ascending order of City
    public int compare(Temperature t1, Temperature t2)
    {
        return t1.getCity().compareTo(t2.getCity());
    }
}

/*
 * Comparator to sort by the high temperature stored in a Temperature object
 */
class SortByHigh implements Comparator<Temperature> {
    // Used for sorting in ascending order of high temperature
    public int compare(Temperature t1, Temperature t2)
    {
        return t1.getHigh() - t2.getHigh();
    }
}