package pl.sda.matchbetapp.exception;



public class DateInPastException extends RuntimeException{
    public DateInPastException(String message) {
        super(message);
    }
}
