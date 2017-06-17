package com.ivamsantos.differ_api.diff.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by iluz on 6/16/17.
 */
public class Differences {
    private List<Delta> differences;

    public Differences() {
        differences = new LinkedList<>();
    }

    public void add(Delta delta) {
        if (delta == null) {
            throw new IllegalArgumentException("delta can't be null");
        }

        differences.add(delta);
    }

    public int getCount() {
        return differences.size();
    }

    public List<Delta> getDifferences() {
        return differences;
    }

    public static class Delta {
        private Type type;
        private Chunk original;
        private Chunk revised;

        public Delta() {

        }

        private Delta(Builder builder) {
            type = builder.type;
            original = builder.original;
            revised = builder.revised;
        }

        public Type getType() {
            return type;
        }

        public Chunk getOriginal() {
            return original;
        }

        public Chunk getRevised() {
            return revised;
        }

        public enum Type {
            INSERTED,
            DELETED,
            CHANGED
        }

        public static final class Builder {
            private Type type;
            private Chunk original;
            private Chunk revised;

            public Builder() {
            }

            public Builder type(Type val) {
                type = val;
                return this;
            }

            public Builder original(Chunk val) {
                original = val;
                return this;
            }

            public Builder revised(Chunk val) {
                revised = val;
                return this;
            }

            public Delta build() {
                return new Delta(this);
            }
        }
    }

    public static class Chunk {
        private int position;
        private int size;
        private List<String> lines;

        public Chunk() {
            
        }

        private Chunk(Builder builder) {
            position = builder.position;
            size = builder.size;
            lines = builder.lines;
        }

        public int getPosition() {
            return position;
        }

        public int getSize() {
            return size;
        }

        public List<String> getLines() {
            return lines;
        }

        public static final class Builder {
            private int position;
            private int size;
            private List<String> lines;

            public Builder() {
            }

            public Builder position(int val) {
                position = val;
                return this;
            }

            public Builder size(int val) {
                size = val;
                return this;
            }

            public Builder lines(List<String> val) {
                lines = new LinkedList<>(val);
                return this;
            }

            public Chunk build() {
                return new Chunk(this);
            }
        }
    }
}
