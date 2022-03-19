package br.com.leonardo.forum.service

import br.com.leonardo.forum.dto.AtualizacaoTopicoForm
import br.com.leonardo.forum.dto.NovoTopicoForm
import br.com.leonardo.forum.dto.TopicoView
import br.com.leonardo.forum.exception.NotFoundException
import br.com.leonardo.forum.mapper.TopicoFormMapper
import br.com.leonardo.forum.mapper.TopicoViewMapper
import br.com.leonardo.forum.model.Topico
import br.com.leonardo.forum.repository.TopicoRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Topico nao encontrado"
    ) {


    fun listar(): List<TopicoView> {
        return repository.findAll().stream().map{
                t -> topicoViewMapper.map(t)
        }.collect(Collectors.toList())
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