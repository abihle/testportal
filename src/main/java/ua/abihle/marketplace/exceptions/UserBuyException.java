package ua.abihle.marketplace.exceptions;

public class UserBuyException extends RuntimeException  {
    private String message;
    public UserBuyException(String message){
        super(message);
        this.message = message;
    }


}