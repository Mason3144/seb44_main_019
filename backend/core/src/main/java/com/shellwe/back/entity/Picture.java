package com.shellwe.back.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "PICTURE")
@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PICTURE_ID")
    private Long id;

    @Column(name = "PICTURE_ORDER")
    private int order;

    private String url;

    public Picture(int order, String url) {
        this.order = order;
        this.url = url;
    }

    public void minusOrder() {
        this.order--;
    }

    public void updatePictureUrl(int order, String url) {
        if (order == this.order) {
            this.url = url;
        } else {
            throw new IllegalStateException("해당 순서의 사진이 아닙니다.");
        }
    }
}
