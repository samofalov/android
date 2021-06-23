package lt.vcs.notes;

import java.time.LocalDateTime;

public class Note {

    private int id;
    private String name;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Note(int id, String name, String content){
        this.id = id;
        this.name = name;
        this.content = content;
        createDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    public Note(int id, String name, String content, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        if(!"".equals(name)){
            this.name = name;
            updateDate = LocalDateTime.now();
        }
     }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {

        if(!"".equals(content)){
            this.content = content;
            updateDate = LocalDateTime.now();
        }
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @Override
    public String toString(){

        String text = id + ". " + name;

        return text;
    }
}
