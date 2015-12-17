package at.kalaunermalik.dezsys07.db;




import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "entry", namespace = "at.kalaunermalik.dezsys07")
@XmlType(name="entry",namespace = "at.kalaunermalik.dezsys07")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Entry {
    @Id
    @GeneratedValue
    @XmlElement(required = true)
    private long id;

    @NotEmpty
    @Size(max = 50)
    @XmlElement(required = true)
    private String title;

    @Lob
    @XmlElement
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

    public String toFormattedString(){
        return "id: "+this.getId()+"\ntitle: "+this.getTitle()+"\ncontent: "+this.getContent();
    }
}
