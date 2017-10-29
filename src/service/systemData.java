package service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class systemData { // Singeltion class
    private static systemData instance = new systemData();
    Map<String,String> loginData = new HashMap<>();
    ArrayList<User> users = new ArrayList<>();
    Statement statement;
    DBConnector connector = new DBConnector();
    ArrayList<LearningApplication> dataLA = new ArrayList<>();
    ArrayList<LearningCategory> dataLC = new ArrayList<>();

    private systemData() {
        setUsersData();
        setLoginData();
        setDataLA();
        setDataLC();
    }

    private void setLoginData() {
        try {
            statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM login_data");
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                loginData.put(username, password);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUsersData() {
        try {
            statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String user_name = resultSet.getString("user_name");
                String user_surname = resultSet.getString("user_surname");
                String email = resultSet.getString("email");
                Date birthdate = resultSet.getDate("birthdate");
                User user = new User(id, user_name, user_surname, email, birthdate);
                users.add(user);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDataLA() {
        try {
            statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM learning_applications");
            while(resultSet.next()) {
                int id = resultSet.getInt("la_id");
                String la_name = resultSet.getString("la_name");
                String la_description = resultSet.getString("la_description");
                LearningApplication la = new LearningApplication(id, la_name, la_description);
                dataLA.add(la);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            statement = connector.getConnection().createStatement();
            String query = "INSERT INTO users (user_name, user_surname, email, birthdate) VALUES (" +
                    user.getUser_name() + ", " + user.getUser_surname() + ", " + user.getEmail() + ", " +
                    user.getBirthdate() + ")";
            statement.execute(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setDataLC() {
        try {
            statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM learning_caterogies");
            while(resultSet.next()) {
                int lc_id = resultSet.getInt("lc_id");
                int la_id = resultSet.getInt("la_id");
                String lc_name = resultSet.getString("lc_name");
                String lc_description = resultSet.getString("lc_description");
                LearningCategory lc = new LearningCategory(lc_id, lc_name, lc_description, la_id);
                dataLC.add(lc);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static systemData getInstance() {
        return instance;
    }

    public  ArrayList<User> getUsers() {
        return users;
    }

    public boolean isLoginSuccsessful(String username, String password) {
        for(Map.Entry<String, String> entry : loginData.entrySet())
            if (entry.getKey().equals(username) && entry.getValue().equals(password))
                return true;

        return false;
    }

    public ArrayList<LearningApplication> getDataLA() {
        return dataLA;
    }

    public ArrayList<LearningCategory> getDataLC() {
        return dataLC;
    }
}
