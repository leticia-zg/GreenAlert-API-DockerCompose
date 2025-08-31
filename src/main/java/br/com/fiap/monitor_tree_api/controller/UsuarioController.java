package br.com.fiap.monitor_tree_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.monitor_tree_api.dto.UsuarioDTO;
import br.com.fiap.monitor_tree_api.model.Usuario;
import br.com.fiap.monitor_tree_api.model.UsuarioFilter;
import br.com.fiap.monitor_tree_api.repository.UsuarioRepository;
import br.com.fiap.monitor_tree_api.specification.UserSpecfication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@Slf4j
@Cacheable(value = "usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public Page<Usuario> index(UsuarioFilter filter, @PageableDefault(size = 10, sort = "idPessoa") Pageable page){
        return repository.findAll(UserSpecfication.withFilters(filter), page);
    }
    
    @PostMapping
    @Operation(responses = @ApiResponse(responseCode = "400"))
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO create(@RequestBody @Valid UsuarioDTO usuarioDTO){
        log.info("Cadastrando um usuário: {}", usuarioDTO);
        Usuario usuario = usuarioDTO.toEntity();
        Usuario saved = repository.save(usuario);
        return UsuarioDTO.fromEntity(saved);
    }

    @GetMapping("{id}")
    public Usuario get(@PathVariable Long id) {
        log.info("Buscando Usuário com id: {}", id);
        return getUsuario(id);
    }

    @DeleteMapping("{id}")
    public void destroy(@PathVariable Long id) {
        log.info("Deletando Usuário com id: {}", id);
        repository.delete(getUsuario(id));
    }
    
    @PutMapping("{id}")
    public Usuario update(@PathVariable Long id, @RequestBody Usuario usuario) {
        log.info("Atualizando Usuário com id: {}", id);

        getUsuario(id);
        usuario.setIdPessoa(id);
        return repository.save(usuario);
    }

    private Usuario getUsuario(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário " + id + " não encontrado"));
    }
}
