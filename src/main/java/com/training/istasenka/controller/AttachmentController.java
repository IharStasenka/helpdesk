package com.training.istasenka.controller;

import com.training.istasenka.assembler.AttachmentDtoAssembler;
import com.training.istasenka.converter.attachment.AttachmentConverter;
import com.training.istasenka.dto.AttachmentDto;
import com.training.istasenka.dto.messages.MessagesDto;
import com.training.istasenka.service.attachment.AttachmentService;
import com.training.istasenka.validator.attachment.AttachmentSize;
import com.training.istasenka.validator.attachment.ExtensionMatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

@RestController
@Validated
@RequestMapping("/tickets/{ticket_id}/attachments")
@AllArgsConstructor
@EnableHypermediaSupport(type = HAL)
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final AttachmentConverter attachmentConverter;
    private final AttachmentDtoAssembler attachmentDtoAssembler;


    @Operation(summary = "Save list of attachments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Save attachments",
                    content = {@Content(schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect attachments data",
                    content = {@Content(schema = @Schema(implementation = MessagesDto.class))}),
            @ApiResponse(responseCode = "403", description = "Current user cant add attachments to the ticket",
                    content = {@Content(schema = @Schema(implementation = MessagesDto.class))}),
            @ApiResponse(responseCode = "404", description = "Ticket not found",
                    content = {@Content(schema = @Schema(implementation = MessagesDto.class))})
    })
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Long>> saveAll(
            @PathVariable(name = "ticket_id") Long ticketId,
            @RequestParam(name = "attachments") List<@AttachmentSize @ExtensionMatch MultipartFile> attachmentDtos) throws IOException {
        var attachments = attachmentConverter.convertFromAttachmentDtoList(attachmentDtos);
        return ResponseEntity
                .status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(attachmentService.createAttachments(ticketId, attachments));
    }

    @Operation(summary = "Get attachment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get attachment",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AttachmentDto.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect path variables",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessagesDto.class))}),
            @ApiResponse(responseCode = "403", description = "Current user cant get attachment to the ticket",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessagesDto.class))}),
            @ApiResponse(responseCode = "404", description = "Attachment not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessagesDto.class))})
    })
    @GetMapping(value = "/{attachment_id}")
    public ResponseEntity<AttachmentDto> getById(
            @PathVariable(value = "attachment_id") Long attachmentId,
            @PathVariable(value = "ticket_id") Long ticketId) {
        var attachment = attachmentService.getAttachment(ticketId, attachmentId);

        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(attachmentDtoAssembler.toModel(attachment));
    }

    @Operation(summary = "Get all attachments for ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get attachments",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AttachmentDto.class))}),
            @ApiResponse(responseCode = "400", description = "Incorrect path variables"),
            @ApiResponse(responseCode = "403", description = "Current user cant get attachments to the ticket"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @GetMapping
    public ResponseEntity<List<AttachmentDto>> getAllByTicketId(@PathVariable(value = "ticket_id") Long ticketId) {
        var attachments = attachmentService.getAllAttachmentsByTicketId(ticketId);

        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(attachmentDtoAssembler.toModelList(attachments));

    }

    @Operation(summary = "Delete attachment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete attachment"),
            @ApiResponse(responseCode = "400", description = "Incorrect path variables"),
            @ApiResponse(responseCode = "403", description = "Current user cant delete attachment to the ticket"),
            @ApiResponse(responseCode = "404", description = "Attachment not found")
    })
    @DeleteMapping(value = "/{attachment_id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable(value = "attachment_id") Long attachmentId,
            @PathVariable(value = "ticket_id") Long ticketId) {
        attachmentService.deleteAttachment(ticketId, attachmentId);

        return ResponseEntity
                .status(NO_CONTENT)
                .build();
    }

    @Operation(summary = "Download attachment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get attachment"),
            @ApiResponse(responseCode = "400", description = "Incorrect path variables"),
            @ApiResponse(responseCode = "403", description = "Current user cant download attachment to the ticket"),
            @ApiResponse(responseCode = "404", description = "Attachment not found")
    })
    @GetMapping(value = "/{attachment_id}/downloads")
    public ResponseEntity<Resource> download(
            @PathVariable(value = "ticket_id") Long ticketId,
            @PathVariable(value = "attachment_id") Long attachmentId) {
        var resource = attachmentService.downloadAttachment(ticketId, attachmentId);

        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
