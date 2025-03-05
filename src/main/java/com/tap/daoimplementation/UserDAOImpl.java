package com.tap.daoimplementation;

import com.tap.dao.UserDAO;
import com.tap.model.User;
import com.tap.utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    private static final String INSERT_USER_QUERY = "INSERT INTO `user`(`name`, `username`, `password`, `email`, `phone`, `address`, `role`) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_QUERY = "SELECT * FROM `user` WHERE `userId`=?";
    private static final String UPDATE_USER_QUERY = "UPDATE `user` SET `name`=?, `password`=?, `phone`=?, `address`=?, `role`=? WHERE `userId`=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM `user` WHERE `userId`=?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM `user`";
    private static final String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM `user` WHERE `username`=?";
    private static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM `user` WHERE `email`=?";

    @Override
    public boolean addUser(User user) {
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getRole());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("✅ User Added Successfully: " + user.getName());
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUser(int userId) {
        User user = null;
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_QUERY)) {

            preparedStatement.setInt(1, userId);
            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) {
                user = new User(res.getInt("userId"),
                        res.getString("name"),
                        res.getString("username"),
                        res.getString("password"),
                        res.getString("email"),
                        res.getString("phone"),
                        res.getString("address"),
                        res.getString("role"), null, null);

                System.out.println("✅ User Found: " + user.getName());
                System.out.println("Details: " + user.getUserId() + ", " + user.getName() + ", " + user.getUsername() + ", " + user.getEmail() + ", " + user.getPhone() + ", " + user.getAddress() + ", " + user.getRole());
            } else {
                System.out.println("❌ No user found with userId: " + userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setInt(6, user.getUserId());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("✅ User Updated Successfully: " + user.getName());
                System.out.println("Updated Details: " + user.getUserId() + ", " + user.getName() + ", " + user.getUsername() + ", " + user.getEmail() + ", " + user.getPhone() + ", " + user.getAddress() + ", " + user.getRole());
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY)) {

            preparedStatement.setInt(1, userId);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("✅ User Deleted Successfully with userId: " + userId);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnect();
             Statement statement = connection.createStatement();
             ResultSet res = statement.executeQuery(GET_ALL_USERS_QUERY)) {

            while (res.next()) {
                User user = new User(res.getInt("userId"),
                        res.getString("name"),
                        res.getString("username"),
                        res.getString("password"),
                        res.getString("email"),
                        res.getString("phone"),
                        res.getString("address"),
                        res.getString("role"), null, null);

                userList.add(user);
                System.out.println("User Found: " + user.getUserId() + ", " + user.getName() + ", " + user.getUsername() + ", " + user.getEmail() + ", " + user.getPhone() + ", " + user.getAddress() + ", " + user.getRole());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = null;
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_USERNAME_QUERY)) {

            preparedStatement.setString(1, username);
            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) {
                user = new User(res.getInt("userId"),
                        res.getString("name"),
                        res.getString("username"),
                        res.getString("password"),
                        res.getString("email"),
                        res.getString("phone"),
                        res.getString("address"),
                        res.getString("role"), null, null);

                System.out.println("✅ User Found by Username: " + username);
                System.out.println("Details: " + user.getUserId() + ", " + user.getName() + ", " + user.getUsername() + ", " + user.getEmail() + ", " + user.getPhone() + ", " + user.getAddress() + ", " + user.getRole());
            } else {
                System.out.println("❌ No user found with username: " + username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        try (Connection connection = DBConnection.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL_QUERY)) {

            preparedStatement.setString(1, email);
            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) {
                user = new User(res.getInt("userId"),
                        res.getString("name"),
                        res.getString("username"),
                        res.getString("password"),
                        res.getString("email"),
                        res.getString("phone"),
                        res.getString("address"),
                        res.getString("role"), null, null);

                System.out.println("✅ User Found by Email: " + email);
                System.out.println("Details: " + user.getUserId() + ", " + user.getName() + ", " + user.getUsername() + ", " + user.getEmail() + ", " + user.getPhone() + ", " + user.getAddress() + ", " + user.getRole());
            } else {
                System.out.println("❌ No user found with email: " + email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
