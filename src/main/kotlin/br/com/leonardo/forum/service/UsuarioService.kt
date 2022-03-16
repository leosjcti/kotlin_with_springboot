package br.com.leonardo.forum.service

import br.com.leonardo.forum.model.Curso
import br.com.leonardo.forum.model.Usuario
import org.springframework.stereotype.Service

@Service
class UsuarioService (var usuarios: List<Usuario>) {

    init {
        val usuario = Usuario(
            id = 1,
            nome = "Leonardo Lima",
            email = "leo@email.com"
        )
        usuarios = listOf(usuario)
    }

    fun buscarPorId(id: Long): Usuario {
        return usuarios.stream().filter { u ->  u.id == id }.findFirst().get()
    }

}
