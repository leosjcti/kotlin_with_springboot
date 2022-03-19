package br.com.leonardo.forum.repository

import br.com.leonardo.forum.model.Curso
import org.springframework.data.jpa.repository.JpaRepository

interface CursoRepository: JpaRepository<Curso, Long> {
}