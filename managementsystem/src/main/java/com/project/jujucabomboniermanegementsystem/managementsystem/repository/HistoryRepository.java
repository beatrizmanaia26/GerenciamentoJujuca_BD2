package com.project.jujucabomboniermanegementsystem.managementsystem.repository;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.HistoryModel;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends CassandraRepository<HistoryModel, UUID> {
    List<HistoryModel> findByDate(LocalDate date);
}