package sg.edu.rp.webservices.a19002765_c302_magic;

import java.io.Serializable;

public class Colour implements Serializable {
    private String colourId;
    private String colourName;


    public Colour(String colourId, String colourName) {
        this.colourId = colourId;
        this.colourName = colourName;
    }

    public String getColourId() {
        return colourId;
    }

    public void setColourId(String colourId) {
        this.colourId = colourId;
    }

    public String getColourName() {
        return colourName;
    }

    public void setColourName(String colourName) {
        this.colourName = colourName;
    }


    @Override
    public String toString() {
        return colourName;
    }
}
