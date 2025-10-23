package com.admin.tool.common;

/**
 * 애플리케이션 전역 상수 정의
 */
public final class Constants {

    private Constants() {
        throw new AssertionError("Cannot instantiate constants class");
    }

    /**
     * 로또 관련 상수
     */
    public static class Lotto {
        public static final int MIN_NUMBER = 1;
        public static final int MAX_NUMBER = 45;
        public static final int NUMBERS_COUNT = 6;
        public static final int MAX_GENERATION_COUNT = 10;
        public static final int MIN_GENERATION_COUNT = 1;

        private Lotto() {
            throw new AssertionError("Cannot instantiate constants class");
        }
    }

    /**
     * 페이징 관련 상수
     */
    public static class Pagination {
        public static final int DEFAULT_PAGE_SIZE = 20;
        public static final int DEFAULT_PAGE_NUMBER = 0;
        public static final String DEFAULT_SORT_DIRECTION = "DESC";

        private Pagination() {
            throw new AssertionError("Cannot instantiate constants class");
        }
    }

    /**
     * JWT 관련 상수
     */
    public static class Jwt {
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String HEADER_NAME = "Authorization";
        public static final int TOKEN_BEGIN_INDEX = 7;

        private Jwt() {
            throw new AssertionError("Cannot instantiate constants class");
        }
    }

    /**
     * 사용자 관련 상수
     */
    public static class User {
        public static final int USERNAME_MIN_LENGTH = 3;
        public static final int USERNAME_MAX_LENGTH = 50;
        public static final int PASSWORD_MIN_LENGTH = 6;

        private User() {
            throw new AssertionError("Cannot instantiate constants class");
        }
    }
}
