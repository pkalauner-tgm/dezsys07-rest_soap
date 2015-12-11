package at.kalaunermalik.dezsys07.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryDao implements EntryService{
    @Autowired
    private EntryRepository repository;

    @Override
    public Entry create(Entry entry) {
        return repository.save(entry);
    }

    @Override
    public void delete(Entry e) {
        repository.delete(e);
    }

    @Override
    public Entry getById(long id) {
        return repository.findOne(id);
    }

    @Override
    public List<Entry> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Entry> getByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Override
    public Entry update(Entry entry) {
        return repository.save(entry);
    }
}
