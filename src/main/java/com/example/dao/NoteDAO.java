package com.example.dao;

import com.example.jdbc.ConfigConnection;
import com.example.model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    private Connection connection;

    public NoteDAO() throws Exception {
        this.connection = ConfigConnection.getConnection();
        this.connection.setAutoCommit(false);
    }

    public NoteDAO(Connection connection) {
        this.connection = connection;
    }

    public Note findById(int id) throws SQLException {
        String sql = "SELECT * FROM Note WHERE Note_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractNoteFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Note> findAll() throws SQLException {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM Note";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                notes.add(extractNoteFromResultSet(rs));
            }
        }
        return notes;
    }

    public void save(Note note) throws SQLException {
        String sql = "INSERT INTO Note (Inscription_ID, Note) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, note.getInscriptionId());
            ps.setFloat(2, note.getNote());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        note.setNoteId(generatedKeys.getInt(1));
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void update(Note note) throws SQLException {
        String sql = "UPDATE Note SET Inscription_ID = ?, Note = ? WHERE Note_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, note.getInscriptionId());
            ps.setFloat(2, note.getNote());
            ps.setInt(3, note.getNoteId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Note WHERE Note_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private Note extractNoteFromResultSet(ResultSet rs) throws SQLException {
        Note note = new Note();
        note.setNoteId(rs.getInt("Note_ID"));
        note.setInscriptionId(rs.getInt("Inscription_ID"));
        note.setNote(rs.getFloat("Note"));
        return note;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
