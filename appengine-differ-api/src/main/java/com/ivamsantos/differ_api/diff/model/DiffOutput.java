package com.ivamsantos.differ_api.diff.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by iluz on 6/18/17.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiffOutput {
    @Id
    private long id;

    private Differences diff;

    public DiffOutput() {

    }

    private DiffOutput(Builder builder) {
        id = builder.id;
        diff = builder.diff;
    }

    public long getId() {
        return id;
    }

    public Differences getDifferences() {
        return diff;
    }

    public static final class Builder {
        private long id = -1;
        private Differences diff;

        public Builder() {
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }

        public Builder withDifferences(Differences val) {
            diff = val;
            return this;
        }

        public DiffOutput build() {
            validate();

            return new DiffOutput(this);
        }

        private void validate() {
            if (id < 0) {
                throw new IllegalArgumentException("id should be greather than or equal to 0.");
            }

            if (diff == null) {
                throw new IllegalArgumentException("A valid Differences instance is required.");
            }
        }
    }
}
