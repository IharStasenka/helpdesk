package com.training.istasenka.provider.specification.attachment;

import com.training.istasenka.repository.specification.attachment.AttachmentSpecification;

public interface AttachmentSpecificationProvider {

    AttachmentSpecification getAttachmentSpecification(Long ticketId);
}
