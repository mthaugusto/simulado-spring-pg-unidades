package br.com.fiap.unidades.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChefeResponse(
        UnidadeResponse unidade,
        LocalDateTime inicio,
        LocalDateTime fim,
        Long id,
        UsuarioResponse usuario,
        Boolean substituto
) {
}
