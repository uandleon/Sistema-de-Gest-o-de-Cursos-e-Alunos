package dao;

import Factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Cursos;

public class CursosDAO {

    public void create(Cursos curso) {
        String sql = "INSERT INTO Curso (nome, cargaHoraria, limiteAlunos, ativo) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNomeCurso());        // método getNomeCurso() pode continuar se o modelo usar esse nome
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getLimiteAlunos());
            stmt.setBoolean(4, curso.isAtivo());

            stmt.executeUpdate();
            System.out.println("Curso salvo com sucesso.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir curso: " + e.getMessage(), e);
        }
    }
    
    public List<Cursos> search(String termo) {
    List<Cursos> lista = new ArrayList<>();
    String sql = "SELECT idCurso, nomeCurso, cargaHoraria, limiteAlunos, ativo " +
                 "FROM curso " +
                 "WHERE nomeCurso LIKE ? AND ativo = true";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, "%" + termo + "%");

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cursos c = new Cursos();
                c.setIdCurso(rs.getInt("idCurso"));
                c.setNomeCurso(rs.getString("nomeCurso"));
                c.setCargaHoraria(rs.getInt("cargaHoraria"));
                c.setLimiteAlunos(rs.getInt("limiteAlunos"));
                c.setAtivo(rs.getBoolean("ativo"));
                lista.add(c);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return lista;
}

        public List<Cursos> readAll() {
           List<Cursos> lista = new ArrayList<>();
           String sql = "SELECT * FROM curso";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setIdCurso(rs.getInt("idCurso"));
                curso.setNomeCurso(rs.getString("nome"));         // corrigido de nomeCurso para nome
                curso.setCargaHoraria(rs.getInt("cargaHoraria"));
                curso.setLimiteAlunos(rs.getInt("limiteAlunos"));
                curso.setAtivo(rs.getBoolean("ativo"));

                lista.add(curso);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar cursos: " + ex);
        }

        return lista;
    }

    public void update(Cursos curso) {
        String sql = "UPDATE Curso SET nome = ?, cargaHoraria = ?, limiteAlunos = ?, ativo = ? WHERE idCurso = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNomeCurso());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getLimiteAlunos());
            stmt.setBoolean(4, curso.isAtivo());
            stmt.setInt(5, curso.getIdCurso());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar curso: " + ex);
        }
    }

public void delete(int idCurso) throws SQLException {
    String sqlDeleteAlunos = "DELETE FROM aluno WHERE idCurso = ?";
    String sqlDeleteCurso = "DELETE FROM curso WHERE idCurso = ?";

    try (Connection conn = ConnectionFactory.getConnection()) {
        conn.setAutoCommit(false);

        try (PreparedStatement ps1 = conn.prepareStatement(sqlDeleteAlunos);
             PreparedStatement ps2 = conn.prepareStatement(sqlDeleteCurso)) {

            //apagar alunos relacionados
            ps1.setInt(1, idCurso);
            ps1.executeUpdate();

            // apagar curso
            ps2.setInt(1, idCurso);
            ps2.executeUpdate();

            conn.commit();

        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        }
    }
}

    public Cursos findById(int idCurso) {
        String sql = "SELECT * FROM Curso WHERE idCurso = ?";
        Cursos curso = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                curso = new Cursos();
                curso.setIdCurso(rs.getInt("idCurso"));
                curso.setNomeCurso(rs.getString("nome"));      // corrigido
                curso.setCargaHoraria(rs.getInt("cargaHoraria"));
                curso.setLimiteAlunos(rs.getInt("limiteAlunos"));
                curso.setAtivo(rs.getBoolean("ativo"));
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao buscar curso por ID: " + ex);
        }

        return curso;
    }

    public List<Cursos> read() {
        List<Cursos> lista = new ArrayList<>();
        String sql = "SELECT idCurso, nome FROM Curso";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setIdCurso(rs.getInt("idCurso"));
                curso.setNomeCurso(rs.getString("nome"));     // corrigido
                lista.add(curso);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao buscar cursos: " + ex);
        }

        return lista;
    }

public Cursos findByNome(String nomeCurso) {
    String sql = "SELECT * FROM curso WHERE nome = ?";
    Cursos curso = null;

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setString(1, nomeCurso);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            curso = new Cursos();
            curso.setIdCurso(rs.getInt("idCurso"));
            curso.setNomeCurso(rs.getString("nome")); // coluna correta
            curso.setCargaHoraria(rs.getInt("cargaHoraria"));
            curso.setLimiteAlunos(rs.getInt("limiteAlunos"));
            curso.setAtivo(rs.getBoolean("ativo"));
        }
        rs.close();

    } catch (SQLException ex) {
        System.err.println("Erro ao buscar curso pelo nome: " + ex.getMessage());
    }
    return curso;
}
}