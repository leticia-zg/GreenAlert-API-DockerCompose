package br.com.fiap.monitor_tree_api.controller;

import br.com.fiap.monitor_tree_api.model.Sensor;
import br.com.fiap.monitor_tree_api.repository.SensorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensores")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository sensorRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sensor cadastrar(@RequestBody @Valid Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @GetMapping
    public List<Sensor> listar() {
        return sensorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Sensor buscarPorId(@PathVariable Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
    }

    @PutMapping("/{id}")
    public Sensor atualizar(@PathVariable Long id, @RequestBody @Valid Sensor sensorAtualizado) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
        sensor.setNome(sensorAtualizado.getNome());
        sensor.setTipo(sensorAtualizado.getTipo());
        sensor.setLocalizacao(sensorAtualizado.getLocalizacao());
        sensor.setDataCriacao(sensorAtualizado.getDataCriacao());
        return sensorRepository.save(sensor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new RuntimeException("Sensor não encontrado");
        }
        sensorRepository.deleteById(id);
    }
}