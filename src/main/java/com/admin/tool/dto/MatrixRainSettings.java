package com.admin.tool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatrixRainSettings {

    private CharacterType characterType;
    private Integer speed;  // 1-10 (느림-빠름)
    private Integer density; // 1-10 (적음-많음)
    private String color;   // 색상 (hex code)

    public enum CharacterType {
        ALPHABET("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"),
        JAPANESE("あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわをんアイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲン"),
        KOREAN("가나다라마바사아자차카타파하거너더러머버서어저처커터퍼허고노도로모보소오조초코토포호구누두루무부수우주추쿠투푸후");

        private final String characters;

        CharacterType(String characters) {
            this.characters = characters;
        }

        public String getCharacters() {
            return characters;
        }
    }

    // 기본 설정
    public static MatrixRainSettings getDefault() {
        return MatrixRainSettings.builder()
                .characterType(CharacterType.ALPHABET)
                .speed(5)
                .density(5)
                .color("#0F0")  // 녹색
                .build();
    }
}
