package registroProjetos.registroProjetos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "responsaveis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Responsavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(unique = true, length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CargoResponsavel cargo;

    @Column(unique = true, length = 60)
    private String login;

    @Column(length = 100)
    private String senha; 
}