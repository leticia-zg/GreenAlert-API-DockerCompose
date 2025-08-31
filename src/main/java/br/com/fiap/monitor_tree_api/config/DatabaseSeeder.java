package br.com.fiap.monitor_tree_api.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.monitor_tree_api.model.*;
import br.com.fiap.monitor_tree_api.repository.*;

import jakarta.annotation.PostConstruct;

@Configuration
public class DatabaseSeeder {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private LeituraRepository leituraRepository;

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @PostConstruct
    public void init() {
        // Usuários
        var celina = Usuario.builder()
                .nome("Celina Alcantara")
                .cpf("443.600.840-13")
                .telefone("11999999999")
                .email("celina@fiap.com.br")
                .senha(passwordEncoder.encode("12345"))
                .role(UserRole.ADMIN)
                .build();

        var ana = Usuario.builder()
                .nome("Ana Carolina")
                .cpf("858.049.070-77")
                .telefone("11988888888")
                .email("ana@fiap.com.br")
                .senha(passwordEncoder.encode("1234"))
                .role(UserRole.USER)
                .build();

                var let = Usuario.builder()
                .nome("Leticia Zago")
                .cpf("858.049.070-77")
                .telefone("11988888888")
                .email("leticia@fiap.com.br")
                .senha(passwordEncoder.encode("12345"))
                .role(UserRole.USER)
                .build();

        usuarioRepository.saveAll(List.of(celina, ana, let));

    
        var sensor1 = Sensor.builder()
                .nome("Sensor Carina")
                .tipo(TipoSensor.TEMPERATURA)
                .localizacao("-3.8083123242704087, -63.12059061063028")
                .dataCriacao(LocalDateTime.now().minusDays(10))
                .build();

        var sensor2 = Sensor.builder()
                .nome("Sensor Maia")
                .tipo(TipoSensor.UMIDADE)
                .localizacao("-2.4999693351509515, -64.81846556958257")
                .dataCriacao(LocalDateTime.now().minusDays(5))
                .build();

        var sensor3 = Sensor.builder()
                .nome("Sensor Julio")
                .tipo(TipoSensor.UMIDADE)
                .localizacao("-3.7823864098932605, -63.160431056079375")
                .dataCriacao(LocalDateTime.now().minusDays(5))
                .build();

        var sensor4 = Sensor.builder()
                .nome("Sensor Ainsten")
                .tipo(TipoSensor.TEMPERATURA)
                .localizacao("-3.8270361104313406, -63.067758715578215")
                .dataCriacao(LocalDateTime.now().minusDays(5))
                .build();

        sensorRepository.saveAll(List.of(sensor1, sensor2, sensor3, sensor4));


        var leitura1 = Leitura.builder()
                .sensor(sensor1)
                .valor(55.0)
                .unidade("CELSIUS")
                .dataHora(LocalDateTime.now().minusHours(2))
                .build();

        var leitura2 = Leitura.builder()
                .sensor(sensor2)
                .valor(80.0)
                .unidade("PORCENTAGEM")
                .dataHora(LocalDateTime.now().minusHours(1))
                .build();

        var leitura3 = Leitura.builder()
                .sensor(sensor3)
                .valor(60.0)
                .unidade("PORCENTAGEM")
                .dataHora(LocalDateTime.now().minusHours(280))
                .build();

        var leitura4 = Leitura.builder()
                .sensor(sensor4)
                .valor(45.0)
                .unidade("CELSIUS")
                .dataHora(LocalDateTime.now().minusHours(24))
                .build();

        leituraRepository.saveAll(List.of(leitura1, leitura2, leitura3, leitura4));


        var alerta1 = Alerta.builder()
                .sensor(sensor1)
                .descricao("Temperatura acima do limite")
                .valor(55.0)
                .limite(50.0)
                .tipoAlerta("TEMPERATURA_ALTA")
                .status(StatusAlerta.ATIVO)
                .dataHora(LocalDateTime.now().minusHours(2))
                .build();

        var alerta2 = Alerta.builder()
                .sensor(sensor2)
                .descricao("Umidade acima do limite")
                .valor(80.0)
                .limite(75.0)
                .tipoAlerta("UMIDADE_ALTA")
                .status(StatusAlerta.RESOLVIDO)
                .dataHora(LocalDateTime.now().minusHours(1))
                .build();
        
        var alerta3 = Alerta.builder()
                .sensor(sensor2)
                .descricao("Umidade acima do limite")
                .valor(80.0)
                .limite(75.0)
                .tipoAlerta("UMIDADE_ALTA")
                .status(StatusAlerta.RESOLVIDO)
                .dataHora(LocalDateTime.now().minusHours(48))
                .build();
                
        var alerta4 = Alerta.builder()
                .sensor(sensor1)
                .descricao("Temperatura acima do limite")
                .valor(55.0)
                .limite(50.0)
                .tipoAlerta("TEMPERATURA_ALTA")
                .status(StatusAlerta.ATIVO)
                .dataHora(LocalDateTime.now().minusHours(72))
                .build();   
        
        alertaRepository.saveAll(List.of(alerta1, alerta2, alerta3, alerta4));

        var chamado1 = Chamado.builder()
                .alerta(alerta1)
                .titulo("Verificar Sensor Carina")
                .descricao("Temperatura muito alta detectada, verificar funcionamento do sensor.")
                .status(ChamadoStatus.ABERTO)
                .tipo(ChamadoTipo.DRONES)
                .dataHoraAbertura(LocalDateTime.now().minusHours(1))
                .build();

        var chamado2 = Chamado.builder()
                .alerta(alerta2)
                .titulo("Checar Sensor Maia")
                .descricao("Umidade acima do limite, possível vazamento.")
                .status(ChamadoStatus.ABERTO)
                .tipo(ChamadoTipo.ESPECIALISTA)
                .dataHoraAbertura(LocalDateTime.now().minusHours(2))
                .build();

        var chamado3 = Chamado.builder()
                .alerta(alerta3)
                .titulo("Revisar Sensor Julio")
                .descricao("Umidade alta recorrente, revisar local.")
                .status(ChamadoStatus.RESOLVIDO)
                .tipo(ChamadoTipo.BOMBEIROS)
                .dataHoraAbertura(LocalDateTime.now().minusHours(50))
                .dataHoraFechamento(LocalDateTime.now().minusHours(45))
                .build();

        var chamado4 = Chamado.builder()
                .alerta(alerta4)
                .titulo("Alerta Sensor Carina")
                .descricao("Temperatura acima do esperado, investigar causa.")
                .status(ChamadoStatus.ABERTO)
                .tipo(ChamadoTipo.POLICIA)
                .dataHoraAbertura(LocalDateTime.now().minusHours(70))
                .build();

        chamadoRepository.saveAll(List.of(chamado1, chamado2, chamado3, chamado4));

    }
}