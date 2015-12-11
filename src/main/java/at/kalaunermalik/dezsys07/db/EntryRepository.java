package at.kalaunermalik.dezsys07.db;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface EntryRepository extends CrudRepository<Entry, Long> {
    List<Entry> findAll();
    List<Entry> findByTitle(String title);
}