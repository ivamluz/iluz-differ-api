package com.ivamsantos.differ_api.diff.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Strings;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;

/**
 * Entity class for storing the input parameters for the diff operation.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiffInput {
    /**
     * Datastore key for identifying the entity in the datastore. Should be built through {@link #buildKey(long, Side)}
     * method.
     */
    @Id
    private String key;

    /**
     * The id of the diff operation, as provided by the API caller.
     */
    @Index
    private long id;

    /**
     * The side of the input.
     */
    private Side side;

    /**
     * The value of the input to be diff-ed.
     */
    private String value;

    public DiffInput() {

    }

    private DiffInput(Builder builder) {
        id = builder.id;
        side = builder.side;
        key = buildKey(id, side);
        value = Strings.nullToEmpty(builder.value);
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

    public enum Side {
        LEFT,
        RIGHT
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
