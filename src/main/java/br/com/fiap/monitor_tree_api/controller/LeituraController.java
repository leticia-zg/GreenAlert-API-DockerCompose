package br.com.fiap.monitor_tree_api.controller;

import br.com.fiap.monitor_tree_api.dto.LeituraDTO;
import br.com.fiap.monitor_tree_api.model.Leitura;
import br.com.fiap.monitor_tree_api.model.Sensor;
import br.com.fiap.monitor_tree_api.repository.LeituraRepository;
import br.com.fiap.monitor_tree_api.repository.SensorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leituras")
@RequiredArgsConstructor
public class LeituraController {

    private final LeituraRepository leituraRepository;
    private final SensorRepository sensorRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeituraDTO salvar(@RequestBody @Valid LeituraDTO dto) {
        Leitura leitura = dto.toEntity();
        Sensor sensor = sensorRepository.findById(dto.getSensorId())
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
        leitura.setSensor(sensor);
        leitura = leituraRepository.save(leitura);
        return LeituraDTO.fromEntity(leitura);
    }

    @GetMapping
    public Page<LeituraDTO> listar(Pageable pageable) {
        return leituraRepository.findAll(pageable).map(LeituraDTO::fromEntity);
    }

    @GetMapping("/{id}")
    public LeituraDTO buscarPorId(@PathVariable Long id) {
        Leitura leitura = leituraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leitura não encontrada"));
        return LeituraDTO.fromEntity(leitura);
    }

    @PutMapping("/{id}")
    public LeituraDTO atualizar(@PathVariable Long id, @RequestBody @Valid LeituraDTO dto) {
        Leitura leitura = leituraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leitura não encontrada"));
        Sensor sensor = sensorRepository.findById(dto.getSensorId())
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
        leitura.setSensor(sensor);
        leitura.setValor(dto.getValor());
        leitura.setUnidade(dto.getUnidade());
        leitura.setDataHora(dto.getDataHora());
        leitura = leituraRepository.save(leitura);
        return LeituraDTO.fromEntity(leitura);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        if (!leituraRepository.existsById(id)) {
            throw new RuntimeException("Leitura não encontrada");
        }
        leituraRepository.deleteById(id);
    }
}