package com.training.istasenka.dto.messages;

import com.training.istasenka.util.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageDto {
    private String message;
    private MessageType type;

}
