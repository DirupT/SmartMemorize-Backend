package com.smartmemorize.backend.card.dto;

public class CreateCardDTO {
    private String front;
    private String back;
    private Long deckId;

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    @Override
    public String toString() {
        return "CreateCardDTO{" +
                "front='" + front + '\'' +
                ", back='" + back + '\'' +
                ", deckId=" + deckId +
                '}';
    }
}
