package br.ufma.portal_egresso.entidade;


import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "egresso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Egresso {
    @Id
    @Column(name="id_egresso")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="email")
    private String email;

    @Column(name="cpf")
    private String cpf;

    @Column(name="resumo")
    private String resumo;

    @Column(name="url_foto")
    private String url_foto;

//    @ManyToMany
//    @JoinTable(
//            name = "contato_egresso",
//            joinColumns = @JoinColumn(name="contato_id"),
//            inverseJoinColumns = @JoinColumn(name="egresso_id"))
//    Set<Contato> contatos;
//
//    @OneToMany(mappedBy = "egresso")
//    private Set<CursoEgresso> cursoEgresso;
//
//    @OneToMany(mappedBy = "egresso")
//    private List<Depoimento> depoimentos;
//
//    @OneToMany(mappedBy = "egresso")
//    private List<ProfEgresso> profEgressos;
}
