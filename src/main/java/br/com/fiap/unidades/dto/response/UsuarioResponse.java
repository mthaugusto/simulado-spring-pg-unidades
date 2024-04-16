package br.com.fiap.unidades.dto.response;

import lombok.Builder;

@Builder
public record UsuarioResponse(
        PessoaResponse pessoa,
        String username,
        Long id
) {
}
