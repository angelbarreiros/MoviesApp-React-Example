package es.udc.paproject.backend.model.exceptions;
@SuppressWarnings("serial")
public class PurchaseNotFoundException extends Exception {
    public PurchaseNotFoundException(String name) {
        super(name);
    }
}