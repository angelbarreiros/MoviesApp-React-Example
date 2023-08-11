package es.udc.paproject.backend.model.exceptions;
@SuppressWarnings("serial")
public class AlreadyUsedException extends Exception{
    public AlreadyUsedException(String name){
        super(name);
    }
}
