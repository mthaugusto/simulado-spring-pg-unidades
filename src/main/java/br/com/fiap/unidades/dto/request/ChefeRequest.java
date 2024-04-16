package br.com.fiap.unidades.dto.request;

import java.time.LocalDateTime;

public record ChefeRequest (

        LocalDateTime inicio,
        LocalDateTime fim,
        AbstractRequest usuario,
        Boolean substituto,
        AbstractRequest unidade
){
}
