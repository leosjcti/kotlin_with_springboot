package br.com.leonardo.forum.service

import br.com.leonardo.forum.dto.AtualizacaoTopicoForm
import br.com.leonardo.forum.dto.NovoTopicoForm
import br.com.leonardo.forum.dto.TopicoView
import br.com.leonardo.forum.exception.NotFoundException
import br.com.leonardo.forum.mapper.TopicoFormMapper
import br.com.leonardo.forum.mapper.TopicoViewMapper
import br.com.leonardo.forum.model.Topico
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class TopicoService(
    private var topicos: List<Topico> = ArrayList(),
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Topico nao encontrado"
    ) {


    fun listar(): List<TopicoView> {
        return topicos.stream().map{
                t -> topicoViewMapper.map(t)
        }.collect(Collectors.toList())
    }


    fun buscarPorId(id: Long): TopicoView {
        val topico =  topicos.stream().filter { t ->
            t.id == id
        }.findFirst().orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topico)
    }

    fun cadastrar(dto: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(dto)
        topico.id = topicos.size.toLong() + 1
        topicos = topicos.plus(topico)  //plus, adiciona um elemento na lista
        return topicoViewMapper.map(topico)
    }

    fun atualizar(dto: AtualizacaoTopicoForm): TopicoView {
        val topico =  topicos.stream().filter { t ->
            t.id == dto.id
        }.findFirst().orElseThrow{NotFoundException(notFoundMessage)}

        val topicoAtualizado = Topico(
            id = dto.id,
            titulo = dto.titulo,
            mensagem = dto.mensagem,
            autor = topico.autor,
            curso = topico.curso,
            respostas = topico.respostas,
            status = topico.status,
            dataCriacao = topico.dataCriacao
        )
        topicos = topicos.minus(topico).plus(topicoAtualizado)
        return topicoViewMapper.map(topicoAtualizado)
    }

    fun deletar(id: Long) {
        val topico =  topicos.stream().filter { t ->
            t.id == id
        }.findFirst().orElseThrow{NotFoundException(notFoundMessage)}

        topicos = topicos.minus(topico) //minus, remove um elemento da lista
    }
}