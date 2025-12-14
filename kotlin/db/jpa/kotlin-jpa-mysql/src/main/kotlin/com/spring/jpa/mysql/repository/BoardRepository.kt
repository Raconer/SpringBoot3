package com.spring.jpa.mysql.repository

import com.spring.jpa.mysql.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {
}