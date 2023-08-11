package es.udc.paproject.backend.model.exceptions;
@SuppressWarnings("serial")
public class SessionNotFoundException extends Exception {
    public SessionNotFoundException(String name) {
        super(name);
    }
}