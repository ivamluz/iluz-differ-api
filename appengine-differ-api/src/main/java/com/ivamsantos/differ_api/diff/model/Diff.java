package com.ivamsantos.differ_api.diff.model;

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
    private Long id;

    private String left;

    private String right;

    private String diff;

    private Diff(Builder builder) {
        id = builder.id;
        left = builder.left;
        right = builder.right;
        diff = builder.diff;
    }

    public Long getId() {
        return id;
    }

    public String getLeft() {
        return left;
    }

    public boolean hasId() {
        return (id != null);
    }

    public String getRight() {
        return right;
    }

    public String getDiff() {
        return diff;
    }

    public static final class Builder {
        private Long id;
        private String left;
        private String right;
        private String diff;

        public Builder() {
        }

        public Builder withId(Long val) {
            id = val;
            return this;
        }

        public Builder withLeft(String val) {
            left = val;
            return this;
        }

        public Builder withRight(String val) {
            right = val;
            return this;
        }

        public Builder withDiff(String val) {
            diff = val;
            return this;
        }

        public Diff build() {
            return new Diff(this);
        }
    }
}
