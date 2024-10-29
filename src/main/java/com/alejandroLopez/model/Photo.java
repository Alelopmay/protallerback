package com.alejandroLopez.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String url;

    @ManyToOne
    @JoinColumn(name = "form_id", referencedColumnName = "id")
    private Form form;

    // Constructor vacío
    public Photo() {}

    // Constructor completo
    public Photo(String url, Form form) {
        this.url = url;
        this.form = form;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", form=" + form +
                '}';
    }
}