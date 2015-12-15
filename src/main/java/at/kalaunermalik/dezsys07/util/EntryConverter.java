package at.kalaunermalik.dezsys07.util;

import at.kalaunermalik.dezsys07.db.Entry;

/**
 * Created by patrick on 15.12.2015.
 */
public class EntryConverter {

    public static dezsys07.kalaunermalik.at.Entry entryToEntry(Entry entry){
        dezsys07.kalaunermalik.at.Entry retEntry = new dezsys07.kalaunermalik.at.Entry();
        retEntry.setTitle(entry.getTitle());
        retEntry.setContent(entry.getContent());
        retEntry.setId(entry.getId());
        return retEntry;
    }
}
