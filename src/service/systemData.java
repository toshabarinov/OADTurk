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
    private int lastUserId;
    LearningInstance activeLI;

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
                String gender = resultSet.getString("gender");

                User user = new User(id, user_name, user_surname, email, birthdate, gender);
                users.add(user);
                lastUserId = id;

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

    public void addUser(User user, String username, String password) {
        try {
            statement = connector.getConnection().createStatement();
            String query = "INSERT INTO users (user_name, user_surname, email, birthdate, gender) VALUES (\"" +
                    user.getUser_name() + "\", \"" + user.getUser_surname() + "\", \"" + user.getEmail() + "\", \"" +
                    user.getBirthdate() + "\", \"" + user.getGender() + "\")";
            statement.executeUpdate(query);
            setLastUserId(getLastUserId()+1);
            String query2 = "INSERT INTO login_data (username, password, user_id) VALUES (\"" + username +
                    "\", \"" + password + "\", \"" + getLastUserId() + "\")";
            statement.executeUpdate(query2);
            statement.close();
            users.add(user);
            loginData.put(username,password);
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

    public boolean isUsernameExist(String username) {
        for(Map.Entry<String, String> entry : loginData.entrySet())
            if (entry.getKey().equals(username))
                return true;
        return false;
    }

    public boolean isEmailExist(String email) {
        for(User user : users)
            if(user.getEmail().equals(email))
                return true;
        return false;
    }

    public ArrayList<LearningCategory> getDataLC() {
        return dataLC;
    }

    public int getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(int lastUserId) {
        this.lastUserId = lastUserId;
    }

    public LearningInstance getActiveLI() {
        return activeLI;
    }

    public void setActiveLI(LearningInstance activeLI) {
        this.activeLI = activeLI;
    }

}
