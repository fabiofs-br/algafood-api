package com.algaworks.algafood.core.email;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    private Sandbox sandbox = new Sandbox();
    private Implementacao impl =  Implementacao.FAKE;

    @NonNull
    private String remetente;

    public enum Implementacao {
        SMTP, FAKE, SANDBOX
    }

    @Setter
    @Getter
    public class Sandbox {
        private String destinatario;

    }

}
