package com.jerzykwiatkowski.warehouse.converter;

import com.jerzykwiatkowski.warehouse.controller.ReportController;
import com.jerzykwiatkowski.warehouse.entity.Report;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class ReportResourceAssembler implements ResourceAssembler<Report, Resource<Report>> {

    @Override
    public Resource<Report> toResource(Report report) {
        return new Resource<>(report,
                linkTo(methodOn(ReportController.class).one(report.getId())).withSelfRel(),
                linkTo(methodOn(ReportController.class).all()).withRel("reports"));
    }
}
