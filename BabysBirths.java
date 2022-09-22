/**
 * MiniProject: Methods that answer questions  US baby names data.
 * 
 * @version september 22, 2022
 * 
 * @admin : Kareem_Nagah
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
public class BabysBirths {
    public void printNames() {
        FileResource fr = new FileResource();
        for (CSVRecord rec: fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                    " Gender " + rec.get(1) +
                    " Num Born " + rec.get(2));
            }
        }
    }
    
    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0, totalGirls = 0;

        for (CSVRecord rec: fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M"))
                totalBoys += numBorn;
            else {
                totalGirls += numBorn;
            }
        }
        System.out.println("Total Number = " + totalBirths);
        System.out.println("Total Boys = " + totalBoys);
        System.out.println("Total Girls = " + totalGirls);
    }

    

    public int getRank(int year, String name, String gender) {
        int counter = 0;
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + String.valueOf(year) + ".csv");
        for (CSVRecord rec: fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                counter++;
                if (rec.get(0).equals(name)) return (counter);
            }
        }
        return (-1);
    }

    

    public String getName(int year, int rank, String gender) {
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + String.valueOf(year) + ".csv");
        int counter = 0;
        String name = "";
        for (CSVRecord rec: fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                counter++;
                if (rank == counter) {
                    name = rec.get(0);
                    return (name);
                }
            }
        }
        return ("No Name ");
    }

    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int nameRank = getRank(year, name, gender);
        String eqName = getName(newYear, nameRank, gender);
        if (gender == "M") System.out.println(name + " born in " + year + " would be " + eqName + " if he was born in " + newYear);
        if (gender == "F") System.out.println(name + " born in " + year + " would be " + eqName + " if she was born in " + newYear);
    }

    public int yearOfHighestRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int highestYear = 0;
        int rank = 10000000;
        for (File f: dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            int counter = 0;
            int currRank = -1;
            String fileName = f.getName();
            int year = Integer.parseInt(fileName.replaceAll("[\\D]", ""));
            for (CSVRecord rec: fr.getCSVParser(false)) {
                if (rec.get(1).equals(gender)) {
                    counter++;
                    if (rec.get(0).equals(name)) {
                        currRank = counter;
                        break;
                    }
                }
            }
            if (currRank != -1 && currRank < rank) {
                rank = currRank;
                highestYear = year;
            }
            
        }
        return (highestYear);
    }
 

    public double getAverageRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int fileCounter = 0;
        int totalRank = 0;
        for (File f: dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            fileCounter++;
            int counter = 0;
            int currRank = -1;
            String fileName = f.getName();
            int year = Integer.parseInt(fileName.replaceAll("[\\D]", ""));
            for (CSVRecord rec: fr.getCSVParser(false)) {
                if (rec.get(1).equals(gender)) {
                    counter++;
                    if (rec.get(0).equals(name)) {
                        currRank = counter;
                        break;
                    }
                }
            }
            totalRank += currRank;
        }
        double avr = (double) totalRank / fileCounter;
        if (avr == 0) return -1;
        else return (avr);
    } 

    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + String.valueOf(year) + ".csv");
        int births;
        int total = 0;
        for (CSVRecord rec: fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {

                if (rec.get(0).equals(name)) {
                    break;
                }

                births = Integer.parseInt(rec.get(2));
                total += births;
            }
        }
        return (total);
    }
    //Testing Methods .
    public void testTotalBirths() {
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
    
    public void testGetRank()

    {
        int rank = getRank(1971, "Frank", "M");
        if (rank == -1) System.out.println("No Rank");
        else System.out.println("Name Rank is " + rank);
    }
 
    public void testgetName() {
        String name = getName(1982, 450, "M");
        System.out.println("Name  is " + name);
    }
    
    public void testWhatIsNameInYear() {
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    
    public void testYearOfHighestRank() {
        int year = yearOfHighestRank("Mich", "M");
        System.out.println("Year of highest rank is: " + year);
    }
    
    public void testGetAverageRank() {
        System.out.println(getAverageRank("Robert", "M"));
        //System.out.println(getAverageRank("Mason", "M"));
    }
    
    public void testGetTotalBirthsRankedHigher() {
        System.out.println(getTotalBirthsRankedHigher(1990, "Drew", "M"));
    }


}