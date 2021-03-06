package lt.vcs.notes;

import java.time.LocalDateTime;

public class Note {

    private int id;
    private String name;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isDeleted = false;

    public boolean isDeleted() {
        return isDeleted;
    }

    public Note(String name, String content){
        this.name = name;
        this.content = content;
        createDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    public Note(int id, String name, String content, LocalDateTime createDate, LocalDateTime updateDate, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.isDeleted = isDeleted;
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
