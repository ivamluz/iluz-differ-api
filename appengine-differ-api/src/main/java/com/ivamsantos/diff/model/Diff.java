package com.ivamsantos.diff.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by iluz on 6/15/17.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Diff {
    @Id
    private String id;

    private Diff(Builder builder) {
        id = builder.id;
    }

    public String getId() {
        return id;
    }

    public String getTest() {
        return "test";
    }

    public static final class Builder {
        private String id;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Diff build() {
            return new Diff(this);
        }
    }
}
