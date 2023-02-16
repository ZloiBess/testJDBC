import java.sql.Date;

public class User {
    private int id;
    private String name;
    private int level;

    private Date date;

    public User(String name, int level, Date date) {
        this.name = name;
        this.level = level;
        this.date = date;
    }

    public User(int id, String name, int level, Date date) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.date = date;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                '}' + "\n";
    }
}
