/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.bean;

import java.util.List;
/**
 *
 * @author Léo
 */
import java.util.ArrayList;
import java.util.List;

public class Cursos {
    private int idCurso;
    private String nome;
    private int cargaHoraria;
    private int limiteAlunos;
    private boolean ativo = true;
    private List<Alunos> alunos = new ArrayList<>();

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && nome.trim().length() >= 3) {
            this.nome = nome;
        } else {
            throw new IllegalArgumentException("Nome do curso deve ter no mínimo 3 caracteres.");
        }
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        if (cargaHoraria >= 20) {
            this.cargaHoraria = cargaHoraria;
        } else {
            throw new IllegalArgumentException("Carga horária mínima é de 20 horas.");
        }
    }

    public int getLimiteAlunos() {
        return limiteAlunos;
    }

    public void setLimiteAlunos(int limiteAlunos) {
        if (limiteAlunos >= 1) {
            this.limiteAlunos = limiteAlunos;
        } else {
            throw new IllegalArgumentException("O limite de alunos deve ser no mínimo 1.");
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Alunos> getAlunos() {
        return alunos;
    }

    public void adicionarAluno(Alunos aluno) {
        if (!ativo) {
            throw new IllegalStateException("Não é possível adicionar alunos a um curso inativo.");
        }
        if (alunos.size() >= limiteAlunos) {
            throw new IllegalStateException("Limite máximo de alunos atingido para este curso.");
        }
        if (aluno != null) {
            aluno.setCurso(this);
            alunos.add(aluno);
        }
    }

    public void removerAluno(Alunos aluno) {
        alunos.remove(aluno);
    }

    public void excluirCurso() {
        alunos.clear();
    }

    public boolean isDisponivelParaCadastro() {
        return ativo;
    }

    public void reativarCurso() {
        this.ativo = true;
    }
}
