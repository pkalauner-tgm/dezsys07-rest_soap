package at.kalaunermalik.dezsys07.soap;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import at.kalaunermalik.dezsys07.db.Entry;
import at.kalaunermalik.dezsys07.db.EntryDao;
import dezsys07.kalaunermalik.at.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.ParseState;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DataRepository {

    //@Autowired
    //private EntryDao entryDao;
    private static final List<Data> data = new ArrayList<Data>();

    @PostConstruct
    public void initData() {
        Data d1 = new Data();
        d1.setId(1);
        d1.setTitle("d1");
        d1.setContent("asdaduiasndnuinduiandunasuindansuidnuainuduasnidna");

        data.add(d1);

        Data d2 = new Data();
        d2.setId(2);
        d2.setTitle("d2");
        d2.setContent("asdaduiasndnuinduiandunasuindansuidnuainuduasnidna");

        data.add(d2);

        Data d3 = new Data();
        d3.setId(3);
        d3.setTitle("d3");
        d3.setContent("asdaduiasndnuinduiandunasuindansuidnuainuduasnidna");

        data.add(d3);

        Data d4 = new Data();
        d4.setId(4);
        d4.setTitle("d4");
        d4.setContent("asdaduiasndnuinduiandunasuindansuidnuainuduasnidna");

        data.add(d4);
    }





    //public List<Entry> findData(String name) {
      public Data findData(String name){
        Assert.notNull(name);

        Data result = null;

        for (Data d : data) {
            if (name.equals(d.getTitle())) {
                result = d;
            }
        }

        return result;
    }
}