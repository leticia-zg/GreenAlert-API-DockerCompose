package br.com.fiap.monitor_tree_api.dto;

import br.com.fiap.monitor_tree_api.model.UserRole;
import br.com.fiap.monitor_tree_api.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Long idPessoa; // Incluído para retorno

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "O campo telefone é obrigatório")
    private String telefone;

    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "O campo role é obrigatório")
    private String role; 

    @NotBlank(message = "O campo senha é obrigatório")
    private String senha;


    public Usuario toEntity() {
        return Usuario.builder()
                .idPessoa(this.idPessoa) 
                .nome(this.nome)
                .cpf(this.cpf)
                .telefone(this.telefone)
                .email(this.email)
                .role(UserRole.valueOf(this.role.toUpperCase()))
                .senha(this.senha)
                .build();
    }

    public static UsuarioDTO fromEntity(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdPessoa(usuario.getIdPessoa()); 
        dto.setNome(usuario.getNome());
        dto.setCpf(usuario.getCpf());
        dto.setTelefone(usuario.getTelefone());
        dto.setEmail(usuario.getEmail());
        dto.setRole(usuario.getRole() != null ? usuario.getRole().name() : null);
        dto.setSenha(usuario.getSenha());
        return dto;
    }
}