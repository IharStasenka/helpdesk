package com.training.istasenka.model.mailing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class MailData {

    private String subject;

    private String body;

    private String email;
}
