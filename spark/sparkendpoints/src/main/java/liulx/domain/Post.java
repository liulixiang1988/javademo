package liulx.domain;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Liu Lixiang on 2017/5/2.
 */
@Embedded
public class Post {
    private Long id;
    private String title;
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
