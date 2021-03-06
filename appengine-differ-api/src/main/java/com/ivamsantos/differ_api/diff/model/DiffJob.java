package com.ivamsantos.differ_api.diff.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffObjectException;

/**
 * This class was used in the first version of the implementation. Please disconsider it.
 */
@Deprecated
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiffJob {
    @Id
    private Long id;

    private String left;

    private String right;

    private Differences diff;

    public DiffJob() {

    }

    private DiffJob(Builder builder) {
        id = builder.id;
        left = builder.left;
        right = builder.right;
        diff = builder.diff;
    }

    public Long getId() {
        return id;
    }

    public boolean hasId() {
        return id != null;
    }

    public String getLeft() {
        return left;
    }

    public boolean hasLeft() {
        return left != null;
    }

    public String getRight() {
        return right;
    }

    public boolean hasRight() {
        return right != null;
    }

    public boolean hasBothSides() {
        return hasLeft() && hasRight();
    }

    public Differences getDiff() {
        return diff;
    }

    public boolean hasDiff() {
        return diff != null;
    }

    public static final class Builder {
        private Long id;
        private String left;
        private String right;
        private Differences diff;

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

        public Builder withDiff(Differences val) {
            diff = val;
            return this;
        }

        public Builder from(DiffJob diffJob) {
            if (diffJob == null) {
                throw new IllegalArgumentException("diffJob can't be null.");
            }

            id = diffJob.getId();
            left = diffJob.getLeft();
            right = diffJob.getRight();
            this.diff = diffJob.getDiff();

            return this;
        }

        public DiffJob build() {
            validate();

            return new DiffJob(this);
        }

        private void validate() {
            boolean isIdNull = (id == null);

            if (isIdNull) {
                throw new InvalidDiffObjectException("id can't be null.");
            }

            if (id < 0) {
                throw new InvalidDiffObjectException("id should be greather than or equal to 0.");
            }

            boolean isDiffNull = (diff == null);
            boolean isLeftOrRightNull = (left == null) || (right == null);
            boolean isLeftAndRightNull = (left == null) && (right == null);

            if (!isDiffNull && isLeftOrRightNull) {
                throw new InvalidDiffObjectException("DiffJob may not be filled with either left or right as null.");
            }

            boolean isOnlyIdFilled = (!isIdNull && isDiffNull && isLeftAndRightNull);
            if (isOnlyIdFilled) {
                throw new InvalidDiffObjectException("Either left or right should be provided with id.");
            }
        }
    }
}
