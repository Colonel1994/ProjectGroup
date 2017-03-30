package dollar.com.travel_v.mien_trung;

/**
 * Created by anhch_000 on 30/03/2017.
 */

public class MienTrung {

    private int id;
    private String image;
    private String name;
    private String title;

    public MienTrung(int id, String image, String name, String title) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.title = title;
    }

    public MienTrung() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageString() {
        return image;
    }

    public void setImage(String image) {
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




}
