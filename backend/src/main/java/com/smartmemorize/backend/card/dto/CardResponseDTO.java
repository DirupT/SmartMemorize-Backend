package com.smartmemorize.backend.card.dto;

public class CardResponseDTO {
    private String front;
    private String back;

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

    @Override
    public String toString() {
        return "CardResponseDTO{" +
                "front='" + front + '\'' +
                ", back='" + back + '\'' +
                '}';
    }
}
