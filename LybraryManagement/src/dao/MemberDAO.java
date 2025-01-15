package dao;

import model.Member;
import utilitaire.DBconnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    public void addMember(Member member) throws SQLException {
        String sql = "INSERT INTO membres (nom, email, date_adhesion) VALUES (?, ?, ?)";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            statement.setDate(3, java.sql.Date.valueOf(member.getEnrollmentDate()));
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                member.setId(generatedKeys.getInt(1));
            }
        }
    }

    public Member findMemberById(int id) throws SQLException {
        String sql = "SELECT * FROM membres WHERE id = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Member member = new Member();
                member.setId(resultSet.getInt("id"));
                member.setName(resultSet.getString("nom"));
                member.setEmail(resultSet.getString("email"));
                member.setEnrollmentDate(resultSet.getDate("date_adhesion").toLocalDate());
                return member;
            }
        }
        return null;
    }

    public List<Member> findAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM membres";
        try (Connection connection = DBconnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Member member = new Member();
                member.setId(resultSet.getInt("id"));
                member.setName(resultSet.getString("nom"));
                member.setEmail(resultSet.getString("email"));
                member.setEnrollmentDate(resultSet.getDate("date_adhesion").toLocalDate());
                members.add(member);
            }
        }
        return members;
    }

    public void updateMember(Member member) throws SQLException {
        String sql = "UPDATE membres SET nom = ?, email = ? WHERE id = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            statement.setInt(3, member.getId());
            statement.executeUpdate();
        }
    }

    public void deleteMember(int memberId) throws SQLException {
        String sql = "DELETE FROM membres WHERE id = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            statement.executeUpdate();
        }
    }
}
