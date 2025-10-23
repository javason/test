package com.admin.tool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "lotto_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LottoHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String numbers;  // 쉼표로 구분된 번호들 (예: "3,12,23,34,41,45")

    @Column(length = 10)
    private String bonusNumber;  // 보너스 번호 (있는 경우)

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime generatedAt;

    @Column(length = 50)
    private String generationType;  // "SINGLE", "MULTIPLE", "BONUS"
}
