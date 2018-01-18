package service;

import java.sql.*;
import javax.swing.text.html.ListView;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class systemData { // Singeltion class
    private static systemData instance = new systemData();
    Map<String,String> loginData = new HashMap<>();
    ArrayList<User> users = new ArrayList<>();
    Statement statement;
    DBConnector connector = new DBConnector();
    ArrayList<LearningApplication> dataLA = new ArrayList<>();
    ArrayList<LearningCategory> dataLC = new ArrayList<>();

    private  Map<Integer, List<LearningUnit>> learningUnitMap;
    private Map<String, LearningUnit> mapStringLU;      //< map of all LUs (key=LU reference name; value=LU)
    private List<LearningUnit> learningUnitList;
    private Map<Integer, LuText> luTextMap;
    private Map<Integer, LuDiagram> luDiagramMap;
    private Map<Integer, LuTextPicture> luTextPictureMap;

    public Map<Integer, LuText> getLuTextMap() {
        return luTextMap;
    }

    public Map<Integer, LuDiagram> getLuDiagramMap() {
        return luDiagramMap;
    }

    public Map<Integer, LuTextPicture> getLuTextPictureMap() {
        return luTextPictureMap;
    }

    public List<LearningUnit> getLearningUnitList() {
        return learningUnitList;
    }

    private int lastCategoryId;
    private int lastLUid;

    public int getLastLUid() {
        return lastLUid;
    }

    public void setLastLUid(int lastLUid) {
        this.lastLUid = lastLUid;
    }

    public int getLastCategoryId() {
        return lastCategoryId;
    }

    public void setLastCategoryId(int lastCategoryId) {
        this.lastCategoryId = lastCategoryId;
    }

    public void setLearningUnitMap(Map<Integer, List<LearningUnit>> learningUnitMap) {
        this.learningUnitMap = learningUnitMap;
    }

        public Map<Integer, List<LearningUnit>> getLearningUnitMap() {
        return learningUnitMap;
    }

        public Map<String, LearningUnit> getMapStringLU() {
        return mapStringLU;
    }

    /** function to set a map of all learning units -> mapStringLU (key=LUName; value=LU)
     *
     */
    public void setMapStringLU() {
        mapStringLU = new HashMap<>();
        try {
            String answerQuestionCombi;
            Connection conn = getDBConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM learning_units");
            while (resultSet.next()){
                if (resultSet.getInt("approved") == 1){
                    answerQuestionCombi = resultSet.getString("question_type") +
                            resultSet.getString("answer_type");
                    switch (answerQuestionCombi){
                        case "tt":
                            Statement st = conn.createStatement();
                            ResultSet resultSetTT = st.executeQuery("SELECT * FROM lu_text_text WHERE id = " + Integer.toString(resultSet.getInt("id")));
                            //ResultSet resultSetTT = st.executeQuery("SELECT * FROM lu_text_text WHERE refName='test'");
                            resultSetTT.next();
                            String test = resultSetTT.getString("refName");
                            LuText luText = new LuText(resultSetTT);
                            mapStringLU.put(luText.getName(), luText);
                            break;
                        case "tp":
                            break;
                        case "pp":
                            break;
                        case "pt":
                            break;
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private int lastUserId;
    private int currentUserID;
    LearningInstance activeLI;

    private systemData() {
        setUsersData();
        setLoginData();
        setDataLA();
        setDataLC();
        setLearningUnit();
        // TODO maybe remove this call later
        setMapStringLU();
    }

    /** to reinitialize the systemData instance
     *
     */
    public void reInit(){
        instance = new systemData();
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

    private void setCurrentUser(String username) {
        for(User user : users) {
            if(user.user_id == currentUserID) {
                currentUser.setInstance(user);
            }
        }
    }

    // keys :
    // 1 --> Learning App
    // 2 --> Learning Category
    // 3 --> Learning Unit
    public ArrayList<LearningInstance> search(int key, String word) {
        word = word.toLowerCase();
        ArrayList<LearningInstance> output = new ArrayList<>();
        switch (key) {
            case 1 : {
                for(LearningApplication la : dataLA) {
                    if(la.name.toLowerCase().contains(word) || la.description.toLowerCase().contains(word)) {
                        output.add(la);
                    }
                }
                break;
            }
            case 2 : {
                for(LearningCategory lc : dataLC) {
                    if(lc.name.toLowerCase().contains(word) || lc.description.toLowerCase().contains(word)) {
                        output.add(lc);
                    }
                }
                break;
            }
            case 3 : {
                //implement LU
                break;
            }
        }
        return output;
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
                boolean isAdmin = resultSet.getBoolean("isAdmin");
                boolean isCreator = resultSet.getBoolean("isCreator");
                //String gender = resultSet.getString("gender");

                // User user = new User(id, user_name, user_surname, email, birthdate, gender);
                User user = new User(id, user_name, user_surname, email, birthdate, isAdmin, isCreator);
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

    public void addLA(String name, String description) {
        try {
            statement = connector.getConnection().createStatement();
            String query = "INSERT INTO learning_applications (la_name, la_description) VALUES (\"" + name + "\", \"" +
                    description + "\")";
            statement.executeUpdate(query);
            ResultSet resultSet = statement.executeQuery("SELECT la_id FROM learning_applications");
            resultSet.last();
            int laId = resultSet.getInt("la_id");
            getDataLA().add(new LearningApplication(laId, name, description));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user, String username, String password) {
        try {
            statement = connector.getConnection().createStatement();
            String query = "INSERT INTO users (user_name, user_surname, email, birthdate) VALUES (\"" +
                    user.getUser_name() + "\", \"" + user.getUser_surname() + "\", \"" + user.getEmail() + "\", \"" +
                    user.getBirthdate() + "\")";
            statement.executeUpdate(query);
            setLastUserId(getLastUserId()+1);
            // KRJO: changed this a bit, login_data user_id should not depend on users user_id
            // get UserID
            ResultSet resultSet = statement.executeQuery("SELECT user_id FROM users");
            resultSet.last();
            int UserId = resultSet.getInt("user_id");

            String query2 = "INSERT INTO login_data (username, password, user_id) VALUES (\"" + username +
                    "\", \"" + password + "\", \"" + UserId + "\")";
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

    public boolean isLoginSuccessful(String username, String password) {
        try {
            statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM login_data");
            for(Map.Entry<String, String> entry : loginData.entrySet()){
                if (entry.getKey().equals(username) && entry.getValue().equals(password)){
                    // KRJO: to set the user id of the current user to know who is logged in
                    while (resultSet.next())
                        if (resultSet.getString("username").equals(username)){
                            currentUserID = resultSet.getInt("user_id");
                            break;
                        }

                    statement.close();
                    setCurrentUser(username);
                    return true;
                }
            }
            statement.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    public int getCurrentUserID() {
        return currentUserID;
    }
    public void setCurrentUserID(int userId) {
        currentUserID = userId;
    }

    /** returns some data from the data base
     *
     * @param UserID user id of the user from which the data should be received
     * @param tableName table from which the data is of interest
     * @param columnNme column of that table
     */
    public String getDBData(int UserID, String tableName, String columnNme){
        String output = "";
        try {
            statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            while(resultSet.next()) {
                if (resultSet.getInt("user_id") == UserID){
                    output = resultSet.getString(columnNme);
                    break;
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    public int getCategoryID(String refName, String tableName, String returnColumn, String searchColumn){
        int output = 0;
        try {
            statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            while(resultSet.next()) {
                if (resultSet.getString(searchColumn).equals(refName)){
                    output = resultSet.getInt(returnColumn);
                    break;
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
    public String getStringData(String name, String tableName, String returnColumn, String searchColumn){
        String output = "";
        try {
            statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            while(resultSet.next()) {
                if (resultSet.getString(searchColumn).equals(name)){
                    output = resultSet.getString(returnColumn);
                    break;
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void setLastUserId(int lastUserId) {
        this.lastUserId = lastUserId;
    }

    public Connection getDBConnection(){
            return connector.getConnection();
    }


    public LearningInstance getActiveLI() {
        return activeLI;
    }

    public void setActiveLI(LearningInstance activeLI) {
        this.activeLI = activeLI;
    }


    private void setLearningUnit(){
        learningUnitList = new ArrayList<>();

        try {
            statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM learning_units");
            while(resultSet.next()) {
                LearningUnit learningUnit = new LearningUnit();
                learningUnit.setId(resultSet.getInt("id"));
                // TODO JO for now the reference name is shown frontend, maybe that's ok though!
                learningUnit.setName(resultSet.getString("refName"));
                learningUnit.setQuestion_type(resultSet.getString("question_type").charAt(0));
                learningUnit.setAnswer_type(resultSet.getString("answer_type").charAt(0));
                learningUnit.setCategory_id( resultSet.getInt("category_id"));
                learningUnit.setApprovedFlag(resultSet.getBoolean("approved"));
                learningUnitList.add(learningUnit);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        learningUnitMap = new HashMap<Integer, List<LearningUnit>>();

        for (int countCategorys = 0; countCategorys < dataLC.size(); countCategorys++){
            List<LearningUnit> learningUnitL = new ArrayList<>();
            for (int count = 0; count < learningUnitList.size(); count++){
                if (learningUnitList.get(count).getCategory_id() == dataLC.get(countCategorys).getId()){
                    learningUnitL.add(learningUnitList.get(count));
                }
            }
            learningUnitMap.put(dataLC.get(countCategorys).getId(), learningUnitL);
        }

        setOther();
    }

    private void setOther(){

        luDiagramMap = new HashMap<>();
        luTextMap = new HashMap<>();
        luTextPictureMap = new HashMap<>();

        try {
            statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM lu_diagram");
            while(resultSet.next()) {
                LuDiagram luDiagram = new LuDiagram();
                luDiagram.setId(resultSet.getInt("id"));
                luDiagram.setObject1_name(resultSet.getString("object1_name"));
                luDiagram.setObject1_parameters(resultSet.getString("object1_parameters"));
                luDiagram.setObject1_num( resultSet.getInt("object1_num"));
                luDiagram.setObject2_name(resultSet.getString("object2_name"));
                luDiagram.setObject2_num(resultSet.getInt("object2_num"));
                luDiagram.setObject2_parameters(resultSet.getString("object2_parameters"));
                luDiagram.setRelationship( resultSet.getString("relationship"));

                luDiagramMap.put(luDiagram.getId(), luDiagram);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        try {
//            statement = connector.getConnection().createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM lu_text_text");
//            while(resultSet.next()) {
//                LuText luText = new LuText();
//                luText.setId(resultSet.getInt("id"));
//                luText.setText(resultSet.getString("text"));
//
//
//                luTextMap.put(luText.getId(), luText);
//            }
//            statement.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        try {
            statement = connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM lu_text_picture");
            while(resultSet.next()) {
                LuTextPicture luTextPicture = new LuTextPicture();
                luTextPicture.setId(resultSet.getInt("id"));
                luTextPicture.setType(resultSet.getInt("type"));
                luTextPicture.setText1(resultSet.getString("text1"));
                luTextPicture.setText2( resultSet.getString("text2"));
                luTextPicture.setText3(resultSet.getString("text3"));
                luTextPicture.setText4(resultSet.getString("text4"));

                luTextPictureMap.put(luTextPicture.getId(), luTextPicture);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}


