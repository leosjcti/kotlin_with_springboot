package br.com.leonardo.forum.service

import br.com.leonardo.forum.model.Curso
import br.com.leonardo.forum.model.Usuario
import br.com.leonardo.forum.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioService (private val repository: UsuarioRepository) {

    fun buscarPorId(id: Long): Usuario {
        return repository.getById(id)
    }
}
