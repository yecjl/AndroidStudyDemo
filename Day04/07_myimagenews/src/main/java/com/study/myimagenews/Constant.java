package com.study.myimagenews;

import android.os.Environment;

import java.io.File;

/**
 * 功能：
 * Created by danke on 2017/3/9.
 */

public class Constant {
    public static final String SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Danke";
}
