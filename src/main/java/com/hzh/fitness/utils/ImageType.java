package com.hzh.fitness.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hzh
 */

public enum ImageType {
    /**
     * 枚举类型为各种图片格式
     */
    ImageJpg("jpg", "image/jpeg", "FFD8FF"),
    ImageGif("gif", "image/gif", "47494638"),
    ImagePng("png", "image/png", "89504E47"),
    ImageWebp("webp", "image/webp", "52494646");

    private final String ext;

    private final String mime;

    private final String magic;

    ImageType(String ext, String mime, String magic) {
        this.ext = ext;
        this.mime = mime;
        this.magic = magic;
    }

    private static final Map<String, ImageType> map;

    static {
        map = new HashMap<>(4);
        for (ImageType e : values()) {
            map.put(e.getExt(), e);
        }
    }

    public static ImageType getEnum(String img) {
        if (img == null) {
            return null;
        }
        String[] s = img.split("\\.");
        return map.get(s[1].toLowerCase());
    }

    public String getExt() {
        return ext;
    }

    public String getMagic() {
        return magic;
    }

    public String getMime() {
        return mime;
    }

    public static String getExt(String img) {
        if (isImageName(img)) {
            String[] s = img.split("\\.");
            return map.get(s[1].toLowerCase()).ext;
        } else {
            return null;
        }
    }

    public static boolean isImageName(String img) {
        if (img == null) {
            return false;
        } else {
            String[] s = img.split("\\.");
            if (s.length != 2) {
                return false;
            } else {
                return map.get(s[1].toLowerCase()) != null;
            }
        }

    }
}
