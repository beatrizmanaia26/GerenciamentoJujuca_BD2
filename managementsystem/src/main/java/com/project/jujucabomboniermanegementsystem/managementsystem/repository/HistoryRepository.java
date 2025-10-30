package com.project.jujucabomboniermanegementsystem.managementsystem.repository;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.HistoryModel;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface HistoryRepository extends CassandraRepository<HistoryModel, UUID> {

    @AllowFiltering
    List<HistoryModel> findByDate(Date date);
}