package com.spring.jpa.mysql.domain

import com.spring.jpa.core.domain.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "board", catalog = "study")
data class Board(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    @Column(nullable = false, length = 200)
    private var title: String? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    private var content: String? = null,

    @Column(nullable = false)
    private var writer: String? = null,

    // 작성자 이름 또는 작성자 ID
    @Column
    private var viewCount: Int? = 0
) : BaseEntity()
