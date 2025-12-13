package com.spring.mysql.repository

import com.spring.mysql.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Int> {
}