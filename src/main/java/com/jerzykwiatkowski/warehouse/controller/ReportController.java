package com.jerzykwiatkowski.warehouse.controller;

import com.jerzykwiatkowski.warehouse.converter.ReportResourceAssembler;
import com.jerzykwiatkowski.warehouse.entity.Report;
import com.jerzykwiatkowski.warehouse.exception.ReportNotFoundException;
import com.jerzykwiatkowski.warehouse.repository.ReportRepository;
import com.jerzykwiatkowski.warehouse.service.ReportService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {

    private final ReportRepository repository;
    private final ReportResourceAssembler assembler;
    private final ReportService service;

    public ReportController(ReportRepository repository,
                            ReportResourceAssembler assembler,
                            ReportService service) {
        this.repository = repository;
        this.assembler = assembler;
        this.service = service;
    }

    @GetMapping
    public Resources<Resource<Report>> all() {

        List<Resource<Report>> reports = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(reports,
                linkTo(methodOn(ReportController.class).all()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<?> newItem(@RequestBody Report newReport) throws URISyntaxException {

        Resource<Report> resource = assembler.toResource(repository.save(newReport));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("/{id}")
    public Resource<Report> one(@PathVariable Long id) {

        Report report = repository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(id));

        return assembler.toResource(report);
    }
}
