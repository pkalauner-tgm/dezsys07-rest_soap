package at.kalaunermalik.dezsys07.rest;


import at.kalaunermalik.dezsys07.db.Entry;
import at.kalaunermalik.dezsys07.db.EntryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntryController {
    @Autowired
    private EntryDao entryDao;

    /**
     * Create a new entry
     *
     * @param entry
     * @return String
     */
    @RequestMapping(value = "/entries", method = RequestMethod.POST)
    public ResponseEntity<Entry> create(@RequestBody Entry entry) {
        return new ResponseEntity<>(entryDao.create(entry), HttpStatus.OK);
    }

    /**
     * Delete the entry with the passed id
     *
     * @param id
     * @return String
     */
    @RequestMapping(value = "/entries/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Entry> delete(@PathVariable("id") long id) {
        Entry e = entryDao.getById(id);
        if (e == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        entryDao.delete(e);
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    /**
     * Retrieve the Entry with the given id
     *
     * @param id
     * @return Entry
     */
    @RequestMapping(value = "/entries/{id}", method = RequestMethod.GET)
    public ResponseEntity<Entry> getById(@PathVariable("id") long id) {
        return new ResponseEntity<>(entryDao.getById(id), HttpStatus.OK);
    }

    /**
     * Retrieve the Entry with the given title
     *
     * @param title
     * @return Entry
     */
    @RequestMapping(value = "/entries/title={title}", method = RequestMethod.GET)
    public ResponseEntity<List<Entry>> getByTitle(@PathVariable("title") String title) {
        if (title == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(entryDao.getByTitle(title), HttpStatus.OK);
    }

    /**
     * Update the title and the content for the entry identified by the passed id
     *
     * @param id
     * @param newEntry
     * @return Entry
     */
    @RequestMapping(value = "/entries/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Entry> updateName(@PathVariable("id") long id, @RequestBody Entry newEntry) {
        Entry entry = entryDao.getById(id);
        if (entry == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        entry.setTitle(newEntry.getTitle());
        entry.setContent(newEntry.getContent());
        entryDao.update(entry);
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }
}