package br.com.fiap.unidades.resource;

import br.com.fiap.unidades.dto.response.UnidadeResponse;
import br.com.fiap.unidades.dto.request.UnidadeRequest;
import br.com.fiap.unidades.entity.Unidade;
import br.com.fiap.unidades.service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
@RestController
@RequestMapping(value = "/unidade")
public class UnidadeResource implements ResourceDTO<UnidadeRequest, UnidadeResponse>{

    @Autowired
    private UnidadeService service;


    @GetMapping
    public ResponseEntity<List<UnidadeResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "sigla", required = false) String sigla,
            @RequestParam(name = "macroId", required = false) Long macroId
    ) {

        var unidade = Unidade.builder()
                .nome(nome)
                .sigla(sigla)
                .macro(Unidade.builder().id(macroId).build())
                .build();


        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();


        Example<Unidade> example = Example.of(unidade, matcher);


        List<Unidade> encontrados = service.findAll(example);


        List<UnidadeResponse> respostas = encontrados.stream()
                .map(service::toResponse)
                .toList();

        return ResponseEntity.ok(respostas);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<UnidadeResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById(id);
        var resposta = service.toResponse(encontrado);
        return ResponseEntity.ok(resposta);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<UnidadeResponse> save(@RequestBody @Valid UnidadeRequest r) {
        var entity = service.toEntity(r);
        var saved = service.save(entity);
        var resposta = service.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(resposta);
    }
}