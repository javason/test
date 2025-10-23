package com.admin.tool.service;

import com.admin.tool.dto.LottoNumberResponse;
import com.admin.tool.entity.LottoHistory;
import com.admin.tool.repository.LottoHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LottoService {

    private final LottoHistoryRepository lottoHistoryRepository;

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;
    private static final int NUMBERS_COUNT = 6;

    /**
     * 로또 번호 1세트 생성 (6개의 번호)
     */
    @Transactional
    public LottoNumberResponse generateSingleLottoNumbers() {
        List<Integer> numbers = generateNumbers();

        // 히스토리 저장
        saveHistory(numbers, "SINGLE");

        log.info("로또 번호 생성: {}", numbers);
        return LottoNumberResponse.of(numbers);
    }

    /**
     * 로또 번호 여러 세트 생성
     */
    @Transactional
    public List<LottoNumberResponse> generateMultipleLottoNumbers(int count) {
        List<LottoNumberResponse> results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<Integer> numbers = generateNumbers();

            // 히스토리 저장
            saveHistory(numbers, "MULTIPLE");

            results.add(LottoNumberResponse.of(numbers));
        }
        log.info("로또 번호 {}세트 생성 완료", count);
        return results;
    }

    /**
     * 1-45 사이의 숫자 중 중복되지 않는 6개를 랜덤하게 선택
     * 오름차순으로 정렬하여 반환
     */
    private List<Integer> generateNumbers() {
        Random random = new Random();

        // 1부터 45까지의 숫자 리스트 생성
        List<Integer> numbers = IntStream.rangeClosed(MIN_NUMBER, MAX_NUMBER)
                .boxed()
                .collect(Collectors.toList());

        // 셔플하여 무작위로 섞기
        Collections.shuffle(numbers, random);

        // 앞에서 6개 선택하고 오름차순 정렬
        return numbers.stream()
                .limit(NUMBERS_COUNT)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * 로또 번호 히스토리 저장
     */
    private void saveHistory(List<Integer> numbers, String type) {
        String username = getCurrentUsername();
        String numbersStr = numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        LottoHistory history = LottoHistory.builder()
                .username(username)
                .numbers(numbersStr)
                .generationType(type)
                .build();

        lottoHistoryRepository.save(history);
        log.debug("로또 번호 히스토리 저장: {}", history);
    }

    /**
     * 현재 인증된 사용자 이름 가져오기
     */
    private String getCurrentUsername() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return "anonymous";
        }
    }

    /**
     * 사용자의 로또 번호 생성 히스토리 조회 (페이징)
     */
    public Page<LottoHistory> getUserHistory(Pageable pageable) {
        String username = getCurrentUsername();
        return lottoHistoryRepository.findByUsername(username, pageable);
    }

    /**
     * 사용자의 최근 로또 번호 생성 히스토리 10개 조회
     */
    public List<LottoHistory> getRecentHistory() {
        String username = getCurrentUsername();
        return lottoHistoryRepository.findTop10ByUsernameOrderByGeneratedAtDesc(username);
    }
}
