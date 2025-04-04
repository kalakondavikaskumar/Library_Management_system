import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Library_management extends JFrame implements ActionListener {
    private JLabel label1, label2, label3, label4, label5, label6, label7;
    private JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    private JButton addButton, viewButton, editButton, deleteButton, clearButton, exitButton;
    private JPanel panel;
    
    public Library_management() {
        setTitle("Library Management System");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label1 = new JLabel("Book ID");
        label2 = new JLabel("Book Title");
        label3 = new JLabel("Author");
        label4 = new JLabel("Publisher");
        label5 = new JLabel("Year of Publication");
        label6 = new JLabel("ISBN");
        label7 = new JLabel("Number of Copies");

        textField1 = new JTextField(10);
        textField2 = new JTextField(20);
        textField3 = new JTextField(20);
        textField4 = new JTextField(20);
        textField5 = new JTextField(10);
        textField6 = new JTextField(20);
        textField7 = new JTextField(10);

        addButton = new JButton("Add");
        viewButton = new JButton("View");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        panel = new JPanel(new GridLayout(10, 2));
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(label4);
        panel.add(textField4);
        panel.add(label5);
        panel.add(textField5);
        panel.add(label6);
        panel.add(textField6);
        panel.add(label7);
        panel.add(textField7);
        panel.add(addButton);
        panel.add(viewButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(clearButton);
        panel.add(exitButton);

        add(panel);
        setVisible(true);
    }

    // Database connection method
    public Connection connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/library_dbs";  // Replace with your DB URL
            String user = "root";  // Replace with your database username
            String password = "root123";  // Replace with your database password
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ActionListener for buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String bookID = textField1.getText();
            String title = textField2.getText();
            String author = textField3.getText();
            String publisher = textField4.getText();
            String year = textField5.getText();
            String isbn = textField6.getText();
            String copies = textField7.getText();

            try (Connection conn = connectToDatabase()) {
                if (conn != null) {
                    String sql = "INSERT INTO books (id, title, author, publisher, year, isbn, copies) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, Integer.parseInt(bookID));
                        stmt.setString(2, title);
                        stmt.setString(3, author);
                        stmt.setString(4, publisher);
                        stmt.setInt(5, Integer.parseInt(year));
                        stmt.setString(6, isbn);
                        stmt.setInt(7, Integer.parseInt(copies));

                        int rowsInserted = stmt.executeUpdate();
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(this, "Book added to the database successfully.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to add the book.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to connect to the database.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while adding book: " + ex.getMessage());
            }

            clearFields();
        }
        else if (e.getSource() == viewButton) {
            try (Connection conn = connectToDatabase()) {
                if (conn != null) {
                    String sql = "SELECT * FROM books";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {

                        String[] columns = {"Book ID", "Book Title", "Author", "Publisher", "Year of Publication", "ISBN", "Number of Copies"};
                        ArrayList<String[]> dataList = new ArrayList<>();

                        while (rs.next()) {
                            String[] row = new String[7];
                            row[0] = String.valueOf(rs.getInt("id"));
                            row[1] = rs.getString("title");
                            row[2] = rs.getString("author");
                            row[3] = rs.getString("publisher");
                            row[4] = String.valueOf(rs.getInt("year"));
                            row[5] = rs.getString("isbn");
                            row[6] = String.valueOf(rs.getInt("copies"));
                            dataList.add(row);
                        }

                        String[][] data = new String[dataList.size()][7];
                        for (int i = 0; i < dataList.size(); i++) {
                            data[i] = dataList.get(i);
                        }

                        JTable table = new JTable(data, columns);
                        JScrollPane scrollPane = new JScrollPane(table);
                        JFrame frame = new JFrame("View Books");
                        frame.add(scrollPane);
                        frame.setSize(800, 400);
                        frame.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to connect to the database.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while viewing books: " + ex.getMessage());
            }
        }
        else if (e.getSource() == editButton) {
            String bookID = JOptionPane.showInputDialog(this, "Enter book ID to edit:");
            try (Connection conn = connectToDatabase()) {
                if (conn != null) {
                    String sql = "SELECT * FROM books WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, Integer.parseInt(bookID));
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            textField1.setText(String.valueOf(rs.getInt("id")));
                            textField2.setText(rs.getString("title"));
                            textField3.setText(rs.getString("author"));
                            textField4.setText(rs.getString("publisher"));
                            textField5.setText(String.valueOf(rs.getInt("year")));
                            textField6.setText(rs.getString("isbn"));
                            textField7.setText(String.valueOf(rs.getInt("copies")));
                        } else {
                            JOptionPane.showMessageDialog(this, "Book not found.");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while editing book: " + ex.getMessage());
            }

            // After entering details, update the book
            int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to update the book?");
            if (confirmation == JOptionPane.YES_OPTION) {
                String title = textField2.getText();
                String author = textField3.getText();
                String publisher = textField4.getText();
                String year = textField5.getText();
                String isbn = textField6.getText();
                String copies = textField7.getText();

                try (Connection conn = connectToDatabase()) {
                    if (conn != null) {
                        String updateSQL = "UPDATE books SET title = ?, author = ?, publisher = ?, year = ?, isbn = ?, copies = ? WHERE id = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
                            stmt.setString(1, title);
                            stmt.setString(2, author);
                            stmt.setString(3, publisher);
                            stmt.setInt(4, Integer.parseInt(year));
                            stmt.setString(5, isbn);
                            stmt.setInt(6, Integer.parseInt(copies));
                            stmt.setInt(7, Integer.parseInt(bookID));

                            int rowsUpdated = stmt.executeUpdate();
                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(this, "Book updated successfully.");
                            } else {
                                JOptionPane.showMessageDialog(this, "Failed to update book.");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error while updating book: " + ex.getMessage());
                }
            }
        }
        else if (e.getSource() == deleteButton) {
            String bookID = JOptionPane.showInputDialog(this, "Enter book ID to delete:");
            try (Connection conn = connectToDatabase()) {
                if (conn != null) {
                    String sql = "DELETE FROM books WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, Integer.parseInt(bookID));

                        int rowsDeleted = stmt.executeUpdate();
                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(this, "Book deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Book not found.");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while deleting book: " + ex.getMessage());
            }
        }
        else if (e.getSource() == clearButton) {
            clearFields();
        }
        else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
        textField7.setText("");
    }

    public static void main(String[] args) {
        try {
            // Register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        new Library_management();
    }
}
