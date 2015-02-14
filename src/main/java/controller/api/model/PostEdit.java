package controller.api.model;

import javax.validation.constraints.NotNull;

/**
 * Created by ElessarST on 15.02.2015.
 */
public class PostEdit {
    @NotNull(message = "Text can't be empty")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
