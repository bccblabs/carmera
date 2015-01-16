package veme.cario.com.CARmera.model.Json;

/**
 * Created by bski on 1/15/15.
 */
public class RecognitionMsg {
    private int classifier_id;
    private String img_url;

    public int getClassifierId() {
        return classifier_id;
    }

    public void setClassifierId(int classifier_id) {
        this.classifier_id = classifier_id;
    }

    public String getImgUrl() {
        return img_url;
    }

    public void setImgUrl(String img_url) {
        this.img_url = img_url;
    }
}
