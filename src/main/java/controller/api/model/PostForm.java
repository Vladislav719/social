package controller.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by ElessarST on 15.02.2015.
 */
public class PostForm {
    @NotNull(message = "Something is wrong")
    private long profileId;

    @NotNull(message = "Text can't be empty")
    private String text;

    private Long authorId;

    public PostForm(long profileId, String text) {
        this.profileId = profileId;
        this.text = text;
    }

    public PostForm() {
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
