package br.com.leonardo.forum.dto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class NovoTopicoForm (
    @field:NotEmpty @field:Size(min = 5, max = 50) val titulo: String,
    @field:NotEmpty val mensagem: String,
    @field:NotNull val idCurso: Long,
    @field:NotNull val idAutor: Long

)