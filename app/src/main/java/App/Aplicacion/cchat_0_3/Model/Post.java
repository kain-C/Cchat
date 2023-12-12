package App.Aplicacion.cchat_0_3.Model;

public class Post {
    private String postid;
    private String Title;
    private String description;
    private String textPost;
    private String publisher;

    public Post() {
    }

    public Post(String postid, String title, String description, String text, String publisher) {
        this.postid = postid;
        this.Title = title;
        this.description = description;
        this.textPost = text;
        this.publisher = publisher;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTextPost() {
        return textPost;
    }

    public void setTextPost(String textPost) {
        this.textPost = textPost;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
