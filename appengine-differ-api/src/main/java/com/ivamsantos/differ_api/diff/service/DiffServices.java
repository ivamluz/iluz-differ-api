package com.ivamsantos.differ_api.diff.service;

/**
 * Created by iluz on 6/15/17.
 */
public interface DiffServices {
    void saveLeft(long id, String left);

    void saveRight(long id, String right);
}
