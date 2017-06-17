package com.ivamsantos.differ_api.diff.business;

import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;
import com.ivamsantos.differ_api.diff.model.Differences;
import difflib.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by iluz on 6/16/17.
 */
public class DiffUtilStringDiffer implements Differ<String> {
    @Override
    public Differences diff(String original, String revised) {
        if (original == null) {
            throw new InvalidDiffInputException("original can't be null");
        }

        if (revised == null) {
            throw new InvalidDiffInputException("revised can't be null");
        }

        final List<Delta> deltas = getDeltas(original, revised);

        return convert(deltas);
    }

    private List<Delta> getDeltas(String original, String revised) {
        final List<String> originalFileLines = Arrays.asList(original.split("\\n"));
        final List<String> revisedFileLines = Arrays.asList(revised.split("\\n"));

        final Patch patch = DiffUtils.diff(originalFileLines, revisedFileLines);

        return patch.getDeltas();
    }

    private Differences convert(List<Delta> diffUtilDeltas) {
        if (diffUtilDeltas == null || diffUtilDeltas.size() == 0) {
            return new Differences();
        }

        Differences differences = new Differences();

        for (int i = diffUtilDeltas.size() - 1; i >= 0; i--) {
            Delta delta = diffUtilDeltas.get(i);

            Chunk original = delta.getOriginal();
            Differences.Chunk originalChunk = new Differences.Chunk.Builder()
                    .position(original.getPosition())
                    .size(original.getSize())
                    .lines((List<String>) original.getLines())
                    .build();

            Chunk revised = delta.getRevised();
            Differences.Chunk revisedChunk = new Differences.Chunk.Builder()
                    .position(revised.getPosition())
                    .size(revised.getSize())
                    .lines((List<String>) revised.getLines())
                    .build();

            Differences.Delta.Type type;
            if (delta instanceof ChangeDelta) {
                type = Differences.Delta.Type.CHANGED;
            } else if (delta instanceof DeleteDelta) {
                type = Differences.Delta.Type.DELETED;
            } else if (delta instanceof InsertDelta) {
                type = Differences.Delta.Type.INSERTED;
            } else {
                throw new IllegalArgumentException("Unknown delta type");
            }

            Differences.Delta diffDelta = new Differences.Delta.Builder()
                    .original(originalChunk)
                    .revised(revisedChunk)
                    .type(type)
                    .build();

            differences.add(diffDelta);
        }

        return differences;
    }
}