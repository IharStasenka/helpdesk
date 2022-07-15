package com.training.istasenka.dto.ticket;

public class View {
    public interface ShortTicket{}
    public interface FullTicket extends ShortTicket{}
    public interface DisabledFields extends FullTicket{}

}
