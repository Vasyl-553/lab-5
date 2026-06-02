package com.logistics.model;

import jakarta.persistence.*;

@Entity
public class Cargo {
    private Double length;
    private Double width;
    private Double height;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Cargo(Builder builder) {
        this.length = builder.length;
        this.width = builder.width;
        this.height = builder.height;
    }

    public Cargo() {

    }

    public static class Builder
    {
        private Double length;
        private Double width;
        private Double height;

        public Builder setLength(Double length)
        {
            this.length = length / 100.0d;
            return this;
        }

        public Builder setWidth(Double width)
        {
            this.width = width / 100.0d;
            return this;
        }

        public Builder setHeight(Double height)
        {
            this.height = height / 100.0d;
            return this;
        }

        public Cargo build()
        {
            return new Cargo(this);
        }
    }


    public Double getVolume()
    {
        return length * width * height;
    }
}
