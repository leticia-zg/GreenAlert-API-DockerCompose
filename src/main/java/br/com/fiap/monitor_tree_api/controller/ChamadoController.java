package br.com.fiap.monitor_tree_api.controller;

import br.com.fiap.monitor_tree_api.dto.ChamadoDTO;
import br.com.fiap.monitor_tree_api.model.Alerta;
import br.com.fiap.monitor_tree_api.model.Chamado;
import br.com.fiap.monitor_tree_api.repository.AlertaRepository;
import br.com.fiap.monitor_tree_api.repository.ChamadoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoRepository chamadoRepository;
    private final AlertaRepository alertaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoDTO criar(@RequestBody @Valid ChamadoDTO dto) {
        Chamado chamado = dto.toEntity();

        Alerta alerta = alertaRepository.findById(dto.getAlertaId())
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));
        chamado.setAlerta(alerta);
        chamado = chamadoRepository.save(chamado);
        return ChamadoDTO.fromEntity(chamado);
    }

    @GetMapping
    public Page<ChamadoDTO> listar(Pageable pageable) {
        return chamadoRepository.findAll(pageable).map(ChamadoDTO::fromEntity);
    }

    @GetMapping("/{id}")
    public ChamadoDTO buscarPorId(@PathVariable Long id) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        return ChamadoDTO.fromEntity(chamado);
    }

    @PutMapping("/{id}")
    public ChamadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid ChamadoDTO dto) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        Alerta alerta = alertaRepository.findById(dto.getAlertaId())
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));
        chamado.setAlerta(alerta);
        chamado.setTitulo(dto.getTitulo());
        chamado.setDescricao(dto.getDescricao());
        if (dto.getStatus() != null)
            chamado.setStatus(br.com.fiap.monitor_tree_api.model.ChamadoStatus.valueOf(dto.getStatus().toUpperCase()));
        if (dto.getTipo() != null)
            chamado.setTipo(br.com.fiap.monitor_tree_api.model.ChamadoTipo.valueOf(dto.getTipo().toUpperCase()));
        chamado.setDataHoraAbertura(dto.getDataHoraAbertura());
        chamado.setDataHoraFechamento(dto.getDataHoraFechamento());
        chamado = chamadoRepository.save(chamado);
        return ChamadoDTO.fromEntity(chamado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        if (!chamadoRepository.existsById(id)) {
            throw new RuntimeException("Chamado não encontrado");
        }
        chamadoRepository.deleteById(id);
    }
}