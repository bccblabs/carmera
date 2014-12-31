package veme.cario.com.CARmera.nav_drawer;

/**
 * Created by bski on 12/30/14.
 */
public class DrawerItem {
    String item_name;
    int img_id;
    String item_title;

    public DrawerItem(String itemName, int imgResID) {
        item_name = itemName;
        this.img_id = imgResID;
    }

    public String getItemName() {
        return item_name;
    }

    public void setItemName(String item_name) {
        this.item_name = item_name;
    }

    public int getImgId() {
        return img_id;
    }

    public void setImgId(int img_id) {
        this.img_id = img_id;
    }

    public String getItemTitle() {
        return item_title;
    }

    public void setItemTitle(String item_title) {
        this.item_title = item_title;
    }
}
