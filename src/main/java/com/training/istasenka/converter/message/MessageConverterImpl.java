package com.training.istasenka.converter.message;

import com.training.istasenka.dto.messages.MessageDto;
import com.training.istasenka.dto.messages.MessagesDto;
import com.training.istasenka.util.MessageType;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageConverterImpl implements MessageConverter {

    @Override
    public MessagesDto fromErrorList(List<FieldError> errors) {
        var errorsStrings= errors.stream()
                .map(error -> new MessageDto(error.getDefaultMessage(), MessageType.ERROR))
                .collect(Collectors.toList());

        return new MessagesDto(errorsStrings);
    }

    @Override
    public MessagesDto fromMessage(String errorMessage, MessageType type) {

        return new MessagesDto(List.of(new MessageDto(errorMessage, type)));
    }

    @Override
    public MessagesDto fromMessages(List<String> messages, MessageType type) {
        var messageDtos = messages.stream().map(m -> new MessageDto(m, type)).collect(Collectors.toList());
        return new MessagesDto(messageDtos);
    }
}
