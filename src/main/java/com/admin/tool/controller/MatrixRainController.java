package com.admin.tool.controller;

import com.admin.tool.dto.ApiResponse;
import com.admin.tool.dto.MatrixRainSettings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Matrix Rain", description = "문자비 (매트릭스 효과) API")
@Controller
@RequestMapping("/matrix-rain")
@RequiredArgsConstructor
public class MatrixRainController {

    @Operation(summary = "문자비 페이지 표시", description = "매트릭스 효과 문자비 페이지를 반환합니다")
    @GetMapping
    public String showMatrixRainPage() {
        return "matrix-rain.html";
    }

    @Operation(summary = "기본 설정 조회", description = "문자비 기본 설정을 반환합니다")
    @GetMapping("/settings/default")
    @ResponseBody
    @SecurityRequirement(name = "bearer-auth")
    public ResponseEntity<ApiResponse<MatrixRainSettings>> getDefaultSettings() {
        MatrixRainSettings settings = MatrixRainSettings.getDefault();
        return ResponseEntity.ok(ApiResponse.success("기본 설정 조회 성공", settings));
    }

    @Operation(summary = "문자 세트 조회", description = "선택한 타입의 문자 세트를 반환합니다")
    @GetMapping("/characters/{type}")
    @ResponseBody
    @SecurityRequirement(name = "bearer-auth")
    public ResponseEntity<ApiResponse<String>> getCharacterSet(
            @PathVariable MatrixRainSettings.CharacterType type) {
        String characters = type.getCharacters();
        return ResponseEntity.ok(
                ApiResponse.success(
                        String.format("%s 문자 세트 조회 성공", type.name()),
                        characters
                )
        );
    }
}
