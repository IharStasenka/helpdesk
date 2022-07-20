package com.training.istasenka.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.training.istasenka.assembler.TicketDtoAssembler;
import com.training.istasenka.converter.attachment.AttachmentConverter;
import com.training.istasenka.converter.ticket.TicketConverter;
import com.training.istasenka.dto.ticket.TicketDto;
import com.training.istasenka.dto.ticket.UpdateStatusTicketDto;
import com.training.istasenka.dto.ticket.View;
import com.training.istasenka.dto.validationgroups.CreateInfo;
import com.training.istasenka.dto.validationgroups.UpdateInfo;
import com.training.istasenka.model.ticket.Ticket;
import com.training.istasenka.provider.specification.pageable.pageable.PageableProvider;
import com.training.istasenka.service.ticket.TicketService;
import com.training.istasenka.validator.attachment.AttachmentSize;
import com.training.istasenka.validator.attachment.ExtensionMatch;
import com.training.istasenka.validator.fronttableparams.OrderByMatch;
import com.training.istasenka.validator.textfield.TextFieldMatch;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.training.istasenka.model.pagiablecomponent.PageableEntity.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping(path = "/tickets")
public class TicketController {

    private static final Logger logger = LogManager.getLogger("DebugLevelLog");

    private final TicketService ticketService;
    private final TicketConverter ticketConverter;
    private final TicketDtoAssembler ticketDtoAssembler;
    private final AttachmentConverter attachmentConverter;
    private final PageableProvider pageableProvider;
    private final PagedResourcesAssembler<Ticket> ticketPagedResourcesAssembler;

    @PutMapping(value = "/{id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestPart(name = "ticketDto") @Validated(UpdateInfo.class) TicketDto ticketDto,
            @RequestPart(name = "attachments", required = false) List<@AttachmentSize @ExtensionMatch MultipartFile> attachments) throws IOException {
        ticketService.updateTicket(
                ticketConverter.convertFromTicketDto(ticketDto),
                attachmentConverter.convertFromAttachmentDtoList(attachments),
                id);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.deleteTicket(id);

        return ResponseEntity.status(NO_CONTENT).build();
    }


    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Long> saveTicket(
            @RequestPart(name = "ticketDto") @Validated(CreateInfo.class) TicketDto ticketDto,
            @RequestPart(name = "attachments") List<@AttachmentSize @ExtensionMatch MultipartFile> attachments
    ) throws IOException {
        var createdTicketId = ticketService.saveTicket(
                ticketConverter.convertFromTicketDto(ticketDto),
                attachmentConverter.convertFromAttachmentDtoList(attachments));
        var ticketUri = linkTo(TicketController.class).slash(createdTicketId).toUri().toASCIIString();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, ticketUri)
                .body(createdTicketId);

    }

    @GetMapping(value = "/{id}")
    @JsonView(View.FullTicket.class)
    public ResponseEntity<TicketDto> getTicketById(@PathVariable Long id) {
        var ticket = ticketService.getTicketById(id);

        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(ticketDtoAssembler.toFullModel(ticket));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> patchStatusById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateStatusTicketDto action) {
        ticketService.updateTicketStatus(id, action.getAction());
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @JsonView(View.ShortTicket.class)
    public ResponseEntity<PagedModel<TicketDto>> getAllUserTickets(
            @NumberFormat()
            @RequestParam(name = "page")
                    Integer page,
            @NumberFormat()
            @RequestParam(name = "rowsPerPage")
                    Integer rowsPerPage,
            @OrderByMatch(value = TicketDto.class)
            @RequestParam(name = "orderBy")
                    String orderBy,
            @RequestParam(name = "order")
                    Sort.Direction order,
            @TextFieldMatch(message = "unresolved filterBy symbol")
            @RequestParam(name = "filterBy")
                    String filterBy,
            @RequestParam(name = "myTicketFilterStatus") Boolean myTicketFilterStatus) {

        logger.debug("try to get all tickets");
        var pagination = pageableProvider.getPagination(page, rowsPerPage, orderBy, order, TICKET);
        var ticketPage = ticketService.getAllTickets(pagination, filterBy, myTicketFilterStatus);
        var httpStatus = PARTIAL_CONTENT;
        if (ticketPage.getTotalPages() == 1) {
            httpStatus = OK;
        }
        if (ticketPage.isEmpty()) {
            httpStatus = REQUESTED_RANGE_NOT_SATISFIABLE;
        }
        return ResponseEntity
                .status(httpStatus)
                .contentType(APPLICATION_JSON)
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .header("X-Total-Count", String.valueOf(ticketPage.getTotalElements()))
                .body(ticketPagedResourcesAssembler.toModel(ticketPage, ticketDtoAssembler));
    }
}
