package net.ausiasmarch.academia.exception;

public class ResourceNotModifiedException extends RuntimeException {
    public ResourceNotModifiedException(String mensaje) {
        super(mensaje);
    }
}
