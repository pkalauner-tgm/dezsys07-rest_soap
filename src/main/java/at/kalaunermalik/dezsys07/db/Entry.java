package at.kalaunermalik.dezsys07.db;




import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Entry {
    @Id
    @GeneratedValue
    private long id;

    @NotEmpty
    @Size(max = 50)
    private String title;

    @Lob
    private String content;

    public Entry() {}

    public Entry(long id) {
        this.id = id;
    }

    public Entry(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
