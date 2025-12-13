package com.spring.jpa.mysql.domain;

import com.spring.jpa.core.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "board", catalog = "study")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 👈 Builder와 함께 사용하기 위해 추가 (private 접근)
@Getter
public class Board extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String writer;   // 작성자 이름 또는 작성자 ID

    @Column(nullable = false)
    private int viewCount = 0;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
