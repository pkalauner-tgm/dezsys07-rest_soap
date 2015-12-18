package at.kalaunermalik.dezsys07;

import at.kalaunermalik.dezsys07.db.Entry;
import at.kalaunermalik.dezsys07.db.EntryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * Entry point of the Spring Application
 *
 * @author Paul Kalauner 5BHIT
 * @version 20151218.1
 */
@SpringBootApplication
public class Application {
    private static int TEST_DATA_NUMBER = 0;

    @Autowired
    private EntryDao entryDao;

    /**
     * Main-Method
     *
     * @param args The main method takes one argument with the number of test data entries which should be inserted.
     *             If no argument is present, no entries will be inserted.
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                TEST_DATA_NUMBER = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
            }
        }
        SpringApplication.run(Application.class, args);
    }


    /**
     * Methods which inserts TestData
     */
    @PostConstruct
    public void insertTestData() {
        for (int i = 1; i <= TEST_DATA_NUMBER; i++)
            entryDao.create(new Entry("Lorem ips " + i, "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " + i));
    }
}
