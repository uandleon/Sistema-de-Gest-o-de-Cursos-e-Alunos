package modelo;

import java.util.ArrayList;
import java.util.List;

public class Cursos {
    private int idCurso;
    private String nomeCurso;
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

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        if (nomeCurso != null && nomeCurso.trim().length() >= 3) {
            this.nomeCurso = nomeCurso;
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

    @Override
    public String toString() {
        return this.getNomeCurso(); // ou apenas "return nome;" se o campo for acessível
}

}
