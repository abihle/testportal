package ua.abihle.marketplace.exceptions;

public class ItemNotFoundException extends RuntimeException  {
    private String message;
    public ItemNotFoundException(String message){
        super(message);
        this.message = message;
    }


}
