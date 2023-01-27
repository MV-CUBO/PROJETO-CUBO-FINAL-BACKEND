package br.com.mv.APIHealth.exception;

public class SenhaInvalidaException extends RuntimeException{

    public SenhaInvalidaException() {
        super("Senha invalida");
    }
}
