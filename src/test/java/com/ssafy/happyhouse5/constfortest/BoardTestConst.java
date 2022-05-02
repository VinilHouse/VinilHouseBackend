package com.ssafy.happyhouse5.constfortest;

import java.util.UUID;

public abstract class BoardTestConst {

    public static final String GARBAGE = getRandomString(20);

    public static final String MEMBER_COMMON_ID = getRandomString(20);
    public static final String MEMBER_COMMON_EMAIL = MEMBER_COMMON_ID + "@ssafy.com";

    public static final String BOARD_COMMON_TITLE = getRandomString(20);
    public static final String BOARD_COMMON_CONTENT = getRandomString(20);

    public static final String MEMBER_ID_1 = MEMBER_COMMON_ID + "1";
    public static final String MEMBER_PASSWORD_1 = "password1";
    public static final String MEMBER_EMAIL_1 = MEMBER_COMMON_EMAIL + "1";

    public static final String MEMBER_ID_2 = MEMBER_COMMON_ID + "2";
    public static final String MEMBER_PASSWORD_2 = "password2";
    public static final String MEMBER_EMAIL_2 = MEMBER_COMMON_EMAIL + "2";

    public static final String BOARD_TITLE_1 = BOARD_COMMON_TITLE + "1";
    public static final String BOARD_CONTENT_1 = BOARD_COMMON_CONTENT + "1";
    public static final String BOARD_TITLE_2 = BOARD_COMMON_TITLE + "2";
    public static final String BOARD_CONTENT_2 = BOARD_COMMON_CONTENT + "2";

    private static String getRandomString(int size) {
        return UUID.randomUUID().toString().substring(0, size);
    }
}