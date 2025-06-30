/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Alunos {
    private int idAluno;
    private String cpf;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private Cursos curso;
    private boolean ativo = true;

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf != null && CPF_PATTERN.matcher(cpf).matches()) {
            this.cpf = cpf;
        } else {
            throw new IllegalArgumentException("CPF inválido. Deve conter exatamente 11 dígitos numéricos.");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && nome.trim().length() >= 3) {
            this.nome = nome;
        } else {
            throw new IllegalArgumentException("Nome deve ter no mínimo 3 caracteres.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && EMAIL_PATTERN.matcher(email).matches()) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento != null && Period.between(dataNascimento, LocalDate.now()).getYears() >= 16) {
            this.dataNascimento = dataNascimento;
        } else {
            throw new IllegalArgumentException("Aluno deve ter no mínimo 16 anos.");
        }
    }

    public Cursos getCurso() {
        return curso;
    }

    void setCursoInterno(Cursos curso) {
    this.curso = curso;
    }    
public void setCurso(Cursos curso) {
    // se já houver curso definido e aluno estiver inativo, impede trocar
    if (this.curso != null && !ativo) {
        throw new IllegalStateException("Aluno inativo não pode ser transferido a outro curso.");
    }
    if (curso == null) {
        throw new IllegalArgumentException("O aluno deve estar associado a um curso.");
    }
    this.curso = curso;
    
}    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getDataNascimentoFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataNascimento != null ? dataNascimento.format(formatter) : "";
    }

    public void excluirAluno() {
        if (curso != null) {
            curso.removerAluno(this);
        }
        this.ativo = false;
    }

    public void reativarAluno() {
        this.ativo = true;
    }

    public boolean isDisponivelParaListagem() {
        return ativo;
    }
    @Override
    public String toString() {
    return getNome(); // mostra o nome do curso no JComboBox
}
}
