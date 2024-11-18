public class Student {
    private String id;     // Unique ID for the student
    private String name;   // Full name of the student
    private int age;       // Age of the student
    private String course; // Course the student is enrolled in

    // Constructor
    public Student(String id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    // Getter for ID
    public String getId() {
        return id;
    }

    // Setter for ID
    public void setId(String id) {
        this.id = id;
    }

    // Getter for Name
    public String getName() {
        return name;
    }

    // Setter for Name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for Age
    public int getAge() {
        return age;
    }

    // Setter for Age
    public void setAge(int age) {
        this.age = age;
    }

    // Getter for Course
    public String getCourse() {
        return course;
    }

    // Setter for Course
    public void setCourse(String course) {
        this.course = course;
    }

    // toString method to return student details
    @Override
    public String toString() {
        return "Student{" +
                "ID='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", Age=" + age +
                ", Course='" + course + '\'' +
                '}';
    }
}
