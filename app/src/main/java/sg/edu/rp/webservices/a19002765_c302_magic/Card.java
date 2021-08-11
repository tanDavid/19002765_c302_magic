package sg.edu.rp.webservices.a19002765_c302_magic;

import java.io.Serializable;

public class Card implements Serializable {

    private int cardId, colourId, typeId, qty;
    private String cardName;
    private Double price;

    public Card(int cardId, int colourId, int typeId, int qty, String cardName, Double price) {
        this.cardId = cardId;
        this.colourId = colourId;
        this.typeId = typeId;
        this.qty = qty;
        this.cardName = cardName;
        this.price = price;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getColourId() {
        return colourId;
    }

    public void setColourId(int colourId) {
        this.colourId = colourId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return cardName + "  " + String.format("%.02f" , price);
    }
}
