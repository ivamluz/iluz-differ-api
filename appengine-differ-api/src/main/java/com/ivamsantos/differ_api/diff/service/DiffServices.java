package com.ivamsantos.differ_api.diff.service;

import com.ivamsantos.differ_api.diff.model.Differences;

/**
 * Created by iluz on 6/15/17.
 */
public interface DiffServices {
    void saveLeft(long id, String left);

    void saveRight(long id, String right);

    Differences diff(long id);
}
