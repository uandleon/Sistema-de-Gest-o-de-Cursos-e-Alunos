/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane; // Import para exibir mensagens de erro
import modelo.Alunos;
import modelo.Cursos;

public class AlunosDAO {

    public void create(Alunos aluno) {
        String sql = "INSERT INTO aluno (cpf, nome, email, dataNascimento, idCurso, ativo) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getEmail());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setInt(5, aluno.getCurso().getIdCurso());
            stmt.setBoolean(6, aluno.isAtivo());

            stmt.executeUpdate(); // executa o SQL no banco, inserindo o aluno.

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                aluno.setIdAluno(rs.getInt(1));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir aluno: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            System.err.println("Erro ao inserir aluno: " + ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }
    
    public List<Alunos> readAll() {
        List<Alunos> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno WHERE ativo = true";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Alunos aluno = new Alunos();
                aluno.setIdAluno(rs.getInt("idAluno"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                aluno.setAtivo(rs.getBoolean("ativo"));

                Cursos curso = new Cursos();
                curso.setIdCurso(rs.getInt("idCurso")); // apenas ID
                aluno.setCurso(curso);

                lista.add(aluno);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar alunos: " + ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        }

        return lista;
    }

    

    public void update(Alunos aluno) {
        String sql = "UPDATE aluno SET cpf = ?, nome = ?, email = ?, dataNascimento = ?, idCurso = ?, ativo = ? WHERE idAluno = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getEmail());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setInt(5, aluno.getCurso().getIdCurso());
            stmt.setBoolean(6, aluno.isAtivo());
            stmt.setInt(7, aluno.getIdAluno());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar aluno: " + ex);
        } finally {
            ConnectionFactory.closeConnection(conn, stmt);
        }
    }

    
    public void delete(int idAluno) throws SQLException {
        String sql = "DELETE FROM aluno WHERE idAluno = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAluno); // define o ID no SQL

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Nenhum aluno encontrado com o id: " + idAluno);
            }
        }
    }


    public Alunos findById(int idAluno) {           // busca um aluno no banco pelo ID e retorna um objeto Alunos preenchido com os dados.
        String sql = "SELECT * FROM aluno WHERE idAluno = ?";
        Alunos aluno = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idAluno);
            rs = stmt.executeQuery();

            if (rs.next()) {                 // Se encontrou o aluno
                aluno = new Alunos();
                aluno.setIdAluno(rs.getInt("idAluno"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                aluno.setAtivo(rs.getBoolean("ativo"));

                Cursos curso = new Cursos();
                curso.setIdCurso(rs.getInt("idCurso"));
                aluno.setCurso(curso);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao buscar aluno por ID: " + ex);
        } finally {
           ConnectionFactory.closeConnection(conn, stmt, rs);
        }

        return aluno;
    }


public List<Alunos> readAllWithCurso() {
    List<Alunos> lista = new ArrayList<>();
    String sql = "SELECT a.*, c.nome AS nomeCurso, c.cargaHoraria, c.limiteAlunos, c.ativo AS ativoCurso " +
                 "FROM aluno a " +
                 "INNER JOIN curso c ON a.idCurso = c.idCurso " +
                 "WHERE 1 =1";

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = ConnectionFactory.getConnection();
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();

        while (rs.next()) {
            Alunos aluno = new Alunos();
            aluno.setIdAluno(rs.getInt("idAluno"));
            aluno.setCpf(rs.getString("cpf"));
            aluno.setNome(rs.getString("nome"));
            aluno.setEmail(rs.getString("email"));
            aluno.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
            aluno.setAtivo(rs.getBoolean("ativo"));

            Cursos curso = new Cursos();
            curso.setIdCurso(rs.getInt("idCurso"));
            curso.setNomeCurso(rs.getString("nomeCurso"));
            curso.setCargaHoraria(rs.getInt("cargaHoraria"));
            curso.setLimiteAlunos(rs.getInt("limiteAlunos"));
            curso.setAtivo(rs.getBoolean("ativoCurso"));

            aluno.setCurso(curso); // associa o curso ao aluno

            lista.add(aluno);
        }

    } catch (SQLException ex) {
        System.err.println("Erro ao listar alunos com curso: " + ex);
    } finally {
        ConnectionFactory.closeConnection(conn, stmt, rs);
    }

    return lista;
}

public Cursos findByNome(String nomeCurso) {
    String sql = "SELECT * FROM curso WHERE nomeCurso = ?";
    Cursos curso = null;
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, nomeCurso);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            curso = new Cursos();
            curso.setIdCurso(rs.getInt("idCurso"));
            curso.setNomeCurso(rs.getString("nomeCurso"));
            curso.setCargaHoraria(rs.getInt("cargaHoraria"));
            curso.setLimiteAlunos(rs.getInt("limiteAlunos"));
            curso.setAtivo(rs.getBoolean("ativo"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return curso;
}

public List<Alunos> search(String termo) {
    List<Alunos> lista = new ArrayList<>();
    String sql = "SELECT a.idAluno, a.nome, a.cpf, a.email, a.dataNascimento, a.ativo, " +
                 "c.idCurso, c.nome AS nomeCurso " +
                 "FROM aluno a " +
                 "LEFT JOIN curso c ON a.idCurso = c.idCurso " +
                 "WHERE a.ativo = true " +
                 "  AND (a.nome LIKE ? OR a.cpf LIKE ?)";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        String q = "%" + termo + "%";
        stmt.setString(1, q);
        stmt.setString(2, q);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Alunos a = new Alunos();
                a.setIdAluno(rs.getInt("idAluno"));
                a.setNome(rs.getString("nome"));
                a.setCpf(rs.getString("cpf"));
                a.setEmail(rs.getString("email"));
                a.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                a.setAtivo(rs.getBoolean("ativo"));

                Cursos c = null;
                int idC = rs.getInt("idCurso");
                if (!rs.wasNull()) {
                    c = new Cursos();
                    c.setIdCurso(idC);
                    c.setNomeCurso(rs.getString("nomeCurso"));
                }
                // só setCurso se aluno ativo (já filtrado)
                a.setCurso(c);

                lista.add(a);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return lista;
}
}