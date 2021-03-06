package br.com.leonardo.forum.controller

import br.com.leonardo.forum.dto.AtualizacaoTopicoForm
import br.com.leonardo.forum.dto.NovoTopicoForm
import br.com.leonardo.forum.dto.TopicoView
import br.com.leonardo.forum.service.TopicoService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/topicos")
class TopicoController(private val service: TopicoService) {

    @GetMapping()
    @Cacheable("topicos")
    fun listar(@RequestParam(required = false) nomeCurso: String?, paginacao: Pageable): Page<TopicoView> {
        return service.listar(nomeCurso, paginacao)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        return service.buscarPorId(id)
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun cadastrar(@RequestBody @Valid dto: NovoTopicoForm, uriBuilder: UriComponentsBuilder): ResponseEntity<TopicoView> {
        val topicoView = service.cadastrar(dto)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)  //?? recomend??vel sempre retornar um status 201(Criado) para o POST. Para isso, ?? necess??rio retornar um ResponseEntity passando a URI do recurso criado e tamb??m um objeto de retorno
    }

    @PutMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun atualizar(@RequestBody @Valid dto: AtualizacaoTopicoForm): ResponseEntity<TopicoView> {
        val topicoView = service.atualizar(dto)
        return ResponseEntity.ok(topicoView)
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)  //Retornar 204 caso d?? certo
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun deletar(@PathVariable id: Long) {
        return service.deletar(id)
    }

}