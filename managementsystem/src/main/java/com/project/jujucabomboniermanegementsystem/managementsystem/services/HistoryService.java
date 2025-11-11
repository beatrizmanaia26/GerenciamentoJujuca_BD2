package com.project.jujucabomboniermanegementsystem.managementsystem.services;

import com.project.jujucabomboniermanegementsystem.managementsystem.models.HistoryModel;
import com.project.jujucabomboniermanegementsystem.managementsystem.repository.HistoryRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Service
public class HistoryService {

    private static final ZoneId ZONE_SAO_PAULO = ZoneId.of("America/Sao_Paulo");
    private static final DateTimeFormatter HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    LocalDate dataSP   = LocalDate.now(ZONE_SAO_PAULO);    
    LocalTime horaSP   = LocalTime.now(ZONE_SAO_PAULO);

    public UUID registrarTransacao(String produto,
                                   int quantidade,
                                   double preco,
                                   String cpfCliente,
                                   String nomeCliente) {

        HistoryModel h = new HistoryModel();
        h.setId_transacao(UUID.randomUUID());           // ID aleatório
        h.setProduto(produto);
        h.setQuantidade(quantidade);
        h.setPreco(preco);
        h.setCpf_cliente(cpfCliente);
        h.setNome_cliente(nomeCliente);
        h.setDate(dataSP);                           
        h.setHourMinSec(horaSP.toString());

        historyRepository.save(h);
        System.out.println("✅ Histórico salvo com sucesso no Cassandra!");
        return h.getId_transacao();
    }
}
