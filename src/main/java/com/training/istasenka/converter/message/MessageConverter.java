package com.training.istasenka.converter.message;

import com.training.istasenka.dto.messages.MessagesDto;
import com.training.istasenka.util.MessageType;
import org.springframework.validation.FieldError;

import java.util.List;

public interface MessageConverter {

    MessagesDto fromErrorList(List<FieldError> errors);

    MessagesDto fromMessage(String errorMessage, MessageType type);

    MessagesDto fromMessages(List<String> messages, MessageType type);

}
