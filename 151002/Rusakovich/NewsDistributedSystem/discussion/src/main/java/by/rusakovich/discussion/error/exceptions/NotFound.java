package by.rusakovich.discussion.error.exceptions;


import by.rusakovich.discussion.error.BaseDSException;

public class NotFound extends BaseDSException {
    public NotFound(){super(0, BaseDSException.codeAndMessage.get(0));}
    public NotFound(String add){super(0, BaseDSException.codeAndMessage.get(0) + add);}
    public NotFound(Object id){super(0, BaseDSException.codeAndMessage.get(0) + "With id: " + id.toString());}
}
