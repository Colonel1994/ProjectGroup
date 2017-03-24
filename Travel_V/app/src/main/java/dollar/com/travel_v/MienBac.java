package dollar.com.travel_v;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by anhch_000 on 21/03/2017.
 */

public class MienBac {

    private int id;
    private Bitmap image;
    private String name;
    private String title;

    public MienBac(int id, Bitmap image, String name, String title) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.title = title;
    }

    public MienBac() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImageBitMap() {

        if (image == null){
            return BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.banana);
        } else {
            return image;
        }
    }

}
