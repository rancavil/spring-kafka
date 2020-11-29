package cl.microservices.data;

public class Temperature {
    private int id;
    private String datetime;
    private float degrees;

    public String getDatetime() {
        return datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Temperature(int id, String datetime, float degrees) {
        this.id = id;
        this.datetime = datetime;
        this.degrees = degrees;
    }
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public float getDegrees() {
        return degrees;
    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
    }
}
