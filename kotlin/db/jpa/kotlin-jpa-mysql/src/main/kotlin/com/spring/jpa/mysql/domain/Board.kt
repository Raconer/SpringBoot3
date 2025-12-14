package com.spring.jpa.mysql.domain

import com.spring.jpa.core.domain.entity.BaseEntity
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "board", catalog = "study")
data class Board(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 200)
    var title: String? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String? = null,

    @Column(nullable = false)
    var writer: String? = null,

    @Column
    var viewCount: Int? = 0
) : BaseEntity(), Serializable {
    fun update(title: String?, content: String?) {
        this.title = title
        this.content = content
    }
}
