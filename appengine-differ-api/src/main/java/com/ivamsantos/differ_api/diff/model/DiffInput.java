package com.ivamsantos.differ_api.diff.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Strings;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;

/**
 * Created by iluz on 6/18/17.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiffInput {
    public enum Side {
        LEFT,
        RIGHT
    }

    @Id
    private String key;

    @Index
    private long id;

    private Side side;

    private String value;

    public DiffInput() {

    }

    private DiffInput(Builder builder) {
        id = builder.id;
        side = builder.side;
        key = buildKey(id, side);
        value = Strings.nullToEmpty(builder.value);
    }

    public long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public Side getSide() {
        return side;
    }

    public String getValue() {
        return value;
    }

    public static String buildKey(long id, Side side) {
        if (id < 0) {
            throw new IllegalArgumentException("id should be greather than or equal to 0.");
        }

        if (side == null) {
            throw new IllegalArgumentException("side can't be null.");
        }

        return String.format("%s-%s", id, side);
    }

    public static final class Builder {
        private Long id;
        private Side side;
        private String value;

        public Builder() {
        }

        public Builder withId(Long val) {
            id = val;
            return this;
        }

        public Builder withSide(Side val) {
            side = val;
            return this;
        }

        public Builder withValue(String val) {
            value = val;
            return this;
        }

        public DiffInput.Builder from(DiffInput diffInput) {
            if (diffInput == null) {
                throw new IllegalArgumentException("diffInput can't be null.");
            }

            id = diffInput.getId();
            side = diffInput.getSide();
            value = diffInput.getValue();

            return this;
        }

        public DiffInput build() {
            validate();

            return new DiffInput(this);
        }

        private void validate() {
            boolean isIdNull = (id == null);

            if (isIdNull) {
                throw new InvalidDiffInputException("id can't be null.");
            }

            if (id < 0) {
                throw new InvalidDiffInputException("id should be greather than or equal to 0.");
            }

            if (side == null) {
                throw new InvalidDiffInputException("A side should be specified.");
            }
        }
    }
}
