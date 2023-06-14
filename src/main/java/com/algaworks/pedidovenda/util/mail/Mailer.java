package com.algaworks.pedidovenda.util.mail;

import com.outjected.email.api.MailMessage;
import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.MailMessageImpl;

import javax.inject.Inject;
import java.io.Serializable;

public class Mailer implements Serializable {

    @Inject
    private SessionConfig config;

    public MailMessage novaMensagem() {
        return new MailMessageImpl(this.config);
    }
}
