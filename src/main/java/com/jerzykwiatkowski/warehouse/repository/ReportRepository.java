package com.jerzykwiatkowski.warehouse.repository;

import com.jerzykwiatkowski.warehouse.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
