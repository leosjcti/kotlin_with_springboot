package br.com.leonardo.forum.service

import br.com.leonardo.forum.dto.AtualizacaoTopicoForm
import br.com.leonardo.forum.dto.NovoTopicoForm
import br.com.leonardo.forum.dto.TopicoView
import br.com.leonardo.forum.exception.NotFoundException
import br.com.leonardo.forum.mapper.TopicoFormMapper
import br.com.leonardo.forum.mapper.TopicoViewMapper
import br.com.leonardo.forum.model.Topico
import br.com.leonardo.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Topico nao encontrado"
    ) {


    fun listar(nomeCurso: String?, paginacao:Pageable): Page<TopicoView> {
        var topicos = if(nomeCurso == null) {
            repository.findAll(paginacao)
        } else {
            repository.findByCursoNome(nomeCurso, paginacao)
        }
        return topicos.map{
            t -> topicoViewMapper.map(t)
        }
    }


    fun buscarPorId(id: Long): TopicoView {
        val topico =  repository.findById(id)
                .orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topico)
    }

    fun cadastrar(dto: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(dto)
        repository.save(topico)
        return topicoViewMapper.map(topico)
    }

    fun atualizar(dto: AtualizacaoTopicoForm): TopicoView {
        val topico =  repository.findById(dto.id).orElseThrow{NotFoundException(notFoundMessage)}

        topico.titulo = dto.titulo
        topico.mensagem = dto.mensagem

        return topicoViewMapper.map(topico)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }
}