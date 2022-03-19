package br.com.leonardo.forum.service

import br.com.leonardo.forum.model.Curso
import br.com.leonardo.forum.repository.CursoRepository
import org.springframework.stereotype.Service

@Service
class CursoService(private val repository: CursoRepository) {

    fun buscarPorId(id: Long): Curso {
        return repository.getById(id)
    }
}
