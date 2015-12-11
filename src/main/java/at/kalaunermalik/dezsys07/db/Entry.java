package at.kalaunermalik.dezsys07.db;



import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Entry {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String title;

    @NotNull
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
