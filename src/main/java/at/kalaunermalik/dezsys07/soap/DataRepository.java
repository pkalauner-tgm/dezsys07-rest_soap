package at.kalaunermalik.dezsys07.soap;

import javax.annotation.PostConstruct;

import at.kalaunermalik.dezsys07.db.Entry;
import at.kalaunermalik.dezsys07.db.EntryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class DataRepository {

    @Autowired
    private EntryDao entryDao;

    public List<Entry> findData(String name) {
        Assert.notNull(name);
        return entryDao.getByTitle(name);
    }
}