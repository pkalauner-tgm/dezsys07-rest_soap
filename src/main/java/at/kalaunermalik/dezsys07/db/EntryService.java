package at.kalaunermalik.dezsys07.db;

import java.util.List;

public interface EntryService {
    Entry create(Entry entry);
    void delete(Entry entry);
    Entry getById(long id);
    List<Entry> getAll();
    List<Entry> getByTitle(String title);
    Entry update(Entry entry);
}
