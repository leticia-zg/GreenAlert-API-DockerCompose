package br.com.fiap.monitor_tree_api.controller;

import br.com.fiap.monitor_tree_api.dto.AlertaDTO;
import br.com.fiap.monitor_tree_api.model.Alerta;
import br.com.fiap.monitor_tree_api.model.Sensor;
import br.com.fiap.monitor_tree_api.repository.AlertaRepository;
import br.com.fiap.monitor_tree_api.repository.SensorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaRepository alertaRepository;
    private final SensorRepository sensorRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlertaDTO salvar(@RequestBody @Valid AlertaDTO dto) {
        Alerta alerta = dto.toEntity();
        alerta = alertaRepository.save(alerta);
        return AlertaDTO.fromEntity(alerta);
    }

    @GetMapping
    public Page<AlertaDTO> listar(Pageable pageable) {
        return alertaRepository.findAll(pageable).map(AlertaDTO::fromEntity);
    }

    @GetMapping("/{id}")
    public AlertaDTO buscarPorId(@PathVariable Long id) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));
        return AlertaDTO.fromEntity(alerta);
    }

    @PutMapping("/{id}")
    public AlertaDTO atualizar(@PathVariable Long id, @RequestBody @Valid AlertaDTO dto) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));
        Sensor sensor = sensorRepository.findById(dto.getSensorId())
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

        alerta.setDescricao(dto.getDescricao());
        alerta.setTipoAlerta(dto.getTipoAlerta());
        alerta.setDataHora(dto.getDataHora());
        alerta.setSensor(sensor);
        alerta.setStatus(dto.getStatus());
        alerta = alertaRepository.save(alerta);
        return AlertaDTO.fromEntity(alerta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        if (!alertaRepository.existsById(id)) {
            throw new RuntimeException("Alerta não encontrado");
        }
        alertaRepository.deleteById(id);
    }
}