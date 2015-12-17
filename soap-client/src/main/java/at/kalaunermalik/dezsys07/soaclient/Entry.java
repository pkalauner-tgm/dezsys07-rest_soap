package at.kalaunermalik.dezsys07.soaclient;




import javax.xml.bind.annotation.*;

@XmlRootElement(name = "entry", namespace = "at.kalaunermalik.dezsys07")
@XmlType(name="entry",namespace = "at.kalaunermalik.dezsys07")
@XmlAccessorType(XmlAccessType.FIELD)
public class Entry {
    @XmlElement(required = true)
    private long id;

    @XmlElement(required = true)
    private String title;

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
