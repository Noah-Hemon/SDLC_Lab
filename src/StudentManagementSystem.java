import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StudentManagementSystem {
    private JFrame frame;
    private JTextField idField, nameField, ageField, courseField;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private ArrayList<Student> students;

    public StudentManagementSystem() {
        students = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Student Management System");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel lblId = new JLabel("Student ID:");
        lblId.setBounds(20, 20, 100, 30);
        frame.add(lblId);

        idField = new JTextField();
        idField.setBounds(120, 20, 200, 30);
        frame.add(idField);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(20, 60, 100, 30);
        frame.add(lblName);

        nameField = new JTextField();
        nameField.setBounds(120, 60, 200, 30);
        frame.add(nameField);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(20, 100, 100, 30);
        frame.add(lblAge);

        ageField = new JTextField();
        ageField.setBounds(120, 100, 200, 30);
        frame.add(ageField);

        JLabel lblCourse = new JLabel("Course:");
        lblCourse.setBounds(20, 140, 100, 30);
        frame.add(lblCourse);

        courseField = new JTextField();
        courseField.setBounds(120, 140, 200, 30);
        frame.add(courseField);

        JButton addButton = new JButton("Add Student");
        addButton.setBounds(20, 180, 140, 30);
        frame.add(addButton);

        JButton deleteButton = new JButton("Delete Student");
        deleteButton.setBounds(180, 180, 140, 30);
        frame.add(deleteButton);

        JButton searchButton = new JButton("Search Student");
        searchButton.setBounds(340, 180, 140, 30);
        frame.add(searchButton);

        tableModel = new DefaultTableModel(new String[]{"Student ID", "Name", "Age", "Course"}, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(20, 230, 750, 200);
        frame.add(scrollPane);

        // Button Actions
        addButton.addActionListener(e -> addStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        searchButton.addActionListener(e -> openSearchWindow());

        frame.setVisible(true);
    }

    private void addStudent() {
        String id = idField.getText();
        String name = nameField.getText();
        String ageStr = ageField.getText();
        String course = courseField.getText();

        if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Age must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Student student : students) {
            if (student.getId().equals(id)) {
                JOptionPane.showMessageDialog(frame, "Student ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Student student = new Student(id, name, age, course);
        students.add(student);
        tableModel.addRow(new Object[]{id, name, age, course});
        JOptionPane.showMessageDialog(frame, "Student added successfully!");
        clearFields();
    }

    private void deleteStudent() {
        String id = idField.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Enter the Student ID to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.remove(i);
                tableModel.removeRow(i);
                JOptionPane.showMessageDialog(frame, "Student deleted successfully!");
                clearFields();
                return;
            }
        }

        JOptionPane.showMessageDialog(frame, "Student ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void openSearchWindow() {
        JFrame searchFrame = new JFrame("Search Student");
        searchFrame.setSize(400, 300);
        searchFrame.setLayout(null);

        JLabel lblSearchBy = new JLabel("Search by:");
        lblSearchBy.setBounds(20, 20, 100, 30);
        searchFrame.add(lblSearchBy);

        JRadioButton searchById = new JRadioButton("ID");
        JRadioButton searchByName = new JRadioButton("Name");
        searchById.setBounds(120, 20, 50, 30);
        searchByName.setBounds(180, 20, 70, 30);
        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(searchById);
        searchGroup.add(searchByName);
        searchFrame.add(searchById);
        searchFrame.add(searchByName);

        JLabel lblSearch = new JLabel("Enter Value:");
        lblSearch.setBounds(20, 70, 100, 30);
        searchFrame.add(lblSearch);

        JTextField searchField = new JTextField();
        searchField.setBounds(120, 70, 200, 30);
        searchFrame.add(searchField);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBounds(20, 120, 350, 100);
        searchFrame.add(resultArea);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(150, 230, 100, 30);
        searchFrame.add(searchButton);

        searchButton.addActionListener(e -> {
            String searchValue = searchField.getText();
            if (searchValue.isEmpty()) {
                JOptionPane.showMessageDialog(searchFrame, "Enter a value to search.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (searchById.isSelected()) {
                for (Student student : students) {
                    if (student.getId().equalsIgnoreCase(searchValue)) {
                        resultArea.setText("Student Found:\n" +
                                "ID: " + student.getId() + "\n" +
                                "Name: " + student.getName() + "\n" +
                                "Age: " + student.getAge() + "\n" +
                                "Course: " + student.getCourse());
                        return;
                    }
                }
                resultArea.setText("No student found with ID: " + searchValue);
            } else if (searchByName.isSelected()) {
                StringBuilder results = new StringBuilder();
                for (Student student : students) {
                    if (student.getName().equalsIgnoreCase(searchValue)) {
                        results.append("ID: ").append(student.getId()).append("\n")
                                .append("Name: ").append(student.getName()).append("\n")
                                .append("Age: ").append(student.getAge()).append("\n")
                                .append("Course: ").append(student.getCourse()).append("\n\n");
                    }
                }
                if (results.length() > 0) {
                    resultArea.setText(results.toString());
                } else {
                    resultArea.setText("No student found with Name: " + searchValue);
                }
            } else {
                JOptionPane.showMessageDialog(searchFrame, "Select a search option.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        searchFrame.setVisible(true);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        courseField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementSystem::new);
    }
}
