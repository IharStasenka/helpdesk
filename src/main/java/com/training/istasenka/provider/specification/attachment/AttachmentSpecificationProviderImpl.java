package com.training.istasenka.provider.specification.attachment;

import com.training.istasenka.repository.specification.attachment.AttachmentSpecification;
import org.springframework.stereotype.Component;

@Component
public class AttachmentSpecificationProviderImpl implements AttachmentSpecificationProvider {
    @Override
    public AttachmentSpecification getAttachmentSpecification(Long ticketId) {
        return new AttachmentSpecification(ticketId);
    }
}
