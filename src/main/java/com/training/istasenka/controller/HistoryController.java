package com.training.istasenka.controller;

import com.training.istasenka.assembler.HistoriesDtoAssembler;
import com.training.istasenka.dto.HistoryDto;
import com.training.istasenka.model.history.History;
import com.training.istasenka.repository.pagiablecomponent.PageableEntity;
import com.training.istasenka.provider.specification.pageable.pageable.PageableProvider;
import com.training.istasenka.service.history.HistoryService;
import com.training.istasenka.validator.fronttableparams.OrderByMatch;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@AllArgsConstructor
@RequestMapping("/tickets")
@EnableHypermediaSupport(type = HAL)
@Validated
public class HistoryController {

    private final HistoryService historyService;
    private final HistoriesDtoAssembler historiesDtoAssembler;
    private final PageableProvider pageableProvider;
    private final PagedResourcesAssembler<History> historyPagedResourcesAssembler;

    @GetMapping(path = "/{ticket_id}/histories/{history_id}")
    public ResponseEntity<HistoryDto> getById(
            @PathVariable(value = "history_id") Long commentId,
            @PathVariable(value = "ticket_id") Long ticketId) {
        var history = historyService.getHistoryById(commentId, ticketId);

        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(historiesDtoAssembler.toModel(history));
    }

    @GetMapping(path = "/{ticket_id}/histories")
    public ResponseEntity<PagedModel<HistoryDto>> getAllByTicketId(
            @PathVariable(value = "ticket_id") Long ticketId,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "rowsPerPage") Integer rowsPerPage,
            @OrderByMatch(value = HistoryDto.class) @RequestParam(name = "orderBy") String orderBy,
            @RequestParam(name = "order") Sort.Direction order) {
        var pagination = pageableProvider.getPagination(page, rowsPerPage, orderBy, order, PageableEntity.COMMENT);
        var historyPage = historyService.getHistoryPage(ticketId, pagination);
        var httpStatus = PARTIAL_CONTENT;
        if (historyPage.getTotalPages() == 1) {
            httpStatus = OK;
        }
        if (historyPage.isEmpty()) {
            httpStatus = REQUESTED_RANGE_NOT_SATISFIABLE;
        }

        return ResponseEntity
                .status(httpStatus)
                .contentType(APPLICATION_JSON)
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .header("X-Total-Count", String.valueOf(historyPage.getTotalElements()))
                .body(historyPagedResourcesAssembler.toModel(historyPage, historiesDtoAssembler));
    }
}
