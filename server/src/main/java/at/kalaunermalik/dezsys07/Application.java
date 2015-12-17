package at.kalaunermalik.dezsys07;

import at.kalaunermalik.dezsys07.db.Entry;
import at.kalaunermalik.dezsys07.db.EntryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {
    private static final int TEST_DATA_NUMBER = 0;

    @Autowired
    private EntryDao entryDao;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @PostConstruct
    public void insertTestData() {
        for (int i = 1; i <= TEST_DATA_NUMBER; i++)
            entryDao.create(new Entry("Titel " + i, "Content " + i));
    }
}
