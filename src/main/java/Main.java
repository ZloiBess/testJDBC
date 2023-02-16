import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManager entityManager = new EntityManager();
        entityManager.init();

//        entityManager.createTable();

//        entityManager.printTableMetadata();

//        User user1 = new User("Jerry", 10, Date.valueOf(LocalDate.now()));
//        User user2 = new User("Bobby", 2, Date.valueOf(LocalDate.now()));
//        System.out.println(entityManager.addUser(user1));
//        System.out.println(entityManager.addUser(user2));

//        System.out.println(entityManager.deleteUserById(2));

//        System.out.println(entityManager.getUserById(1));
//        entityManager.changeNameById(1, "Barbara");
//        System.out.println(entityManager.getUserById(1));

//        System.out.println(entityManager.getAll());

    }
}

class EntityManager {
    private final String URL = "jdbc:mysql://localhost:3306/test";
    private final String USERNAME = "root";
    private final String PASSWORD = "sql12345";
    private Connection connection;

    public void init() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createTable() {
        String sql = "create table user ("
                + "id int not null auto_increment,"
                + "name varchar(20) not null,"
                + "level int not null,"
                + "date date not null,"
                + " primary key(id)"
                + ");";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void printTableMetadata() {
        String sql = "select * from user";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSetMetaData metaData = statement.executeQuery().getMetaData();

            int countColumn = metaData.getColumnCount();

            for (int i = 1; i <= countColumn; i++) {
                String name = metaData.getColumnName(i);
                String className = metaData.getColumnClassName(i);
                String typeName = metaData.getColumnTypeName(i);
                System.out.println(name + "\t" + className +"\t"+ typeName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public boolean addUser (User user){
            String sql = "insert into user (name, level, date) values(?, ?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, user.getName());
                statement.setInt(2, user.getLevel());
                statement.setDate(3, user.getDate());
                connection.setAutoCommit(false);
                statement.executeUpdate();
                connection.commit();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean deleteUserById ( int id){
            String sql = "delete from user where id = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                connection.setAutoCommit(false);
                statement.executeUpdate();
                connection.commit();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public List getAll () {
            String sql = "select * from user";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<User> list = new ArrayList<>();
                while (resultSet.next()) {
                    User user = getUserById(resultSet.getInt("id"));
                    list.add(user);
                }
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public User getUserById ( int id){
            String sql = "select * from user where id = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("level"),
                        resultSet.getDate("date")
                );
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public boolean changeNameById ( int idUser, String name){
            String sql = "update user set name = ? where id = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setInt(2, idUser);
                connection.setAutoCommit(false);
                statement.executeUpdate();
                connection.commit();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }


    }
