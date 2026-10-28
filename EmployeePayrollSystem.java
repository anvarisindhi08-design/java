
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

class Employee
{
    int id;
    String name;
    double salary;
    String department;

    // Default constructor
    Employee()
    {
        id = 0;
        name = "";
        salary = 0;
        department = "";
    }

    // Parameterized constructor
    Employee(int id, String name, double salary, String department)
    {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    // Calculate allowance
    double getAllowance()
    {
        return salary * 0.20;
    }

    // Calculate deduction
    double getDeduction()
    {
        return salary * 0.05;
    }

    // Calculate net salary
    double getNetSalary()
    {
        return salary + getAllowance() - getDeduction();
    }

    // Display employee details
    public String toString()
    {
        return "Employee ID: " + id +
               "\nName: " + name +
               "\nDepartment: " + department +
               "\nBasic Salary: Rs. " + salary +
               "\nAllowance: Rs. " + getAllowance() +
               "\nDeduction: Rs. " + getDeduction() +
               "\nNet Salary: Rs. " + getNetSalary() +
               "\n-----------------------------\n";
    }
}

public class EmployeePayrollSystem extends JFrame
{
    JTextField txtId, txtName, txtSalary, txtSearch;
    JComboBox<String> cbDepartment;
    JTextArea area;

    ArrayList<Employee> employees = new ArrayList<Employee>();

    Color blue = new Color(30, 58, 138);
    Color lightBlue = new Color(239, 246, 255);

    EmployeePayrollSystem()
    {
        setTitle("Employee Payroll System");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(lightBlue);
        setLayout(new BorderLayout(10, 10));

        // Heading
        JLabel heading = new JLabel(
            "EMPLOYEE PAYROLL SYSTEM", JLabel.CENTER);

        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);
        heading.setBackground(blue);
        heading.setOpaque(true);

        add(heading, BorderLayout.NORTH);

        // Main panel
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(lightBlue);
        main.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Employee form
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBackground(Color.WHITE);

        form.setBorder(BorderFactory.createTitledBorder(
            "Employee Details"));

        txtId = new JTextField();
        txtName = new JTextField();
        txtSalary = new JTextField();

        cbDepartment = new JComboBox<String>(
            new String[]{"IT", "HR", "Sales", "Finance", "Accounts"});

        form.add(new JLabel("Employee ID:"));
        form.add(txtId);

        form.add(new JLabel("Employee Name:"));
        form.add(txtName);

        form.add(new JLabel("Basic Salary:"));
        form.add(txtSalary);

        form.add(new JLabel("Department:"));
        form.add(cbDepartment);

        // Search field
        JPanel searchPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        searchPanel.setBackground(lightBlue);

        txtSearch = new JTextField();

        searchPanel.add(new JLabel("Search Employee ID:"));
        searchPanel.add(txtSearch);

        // Buttons
        JPanel buttons = new JPanel(new GridLayout(2, 3, 10, 10));
        buttons.setBackground(lightBlue);

        JButton btnAdd = createButton("Add Employee", new Color(22, 163, 74));
        JButton btnSave = createButton("Save", new Color(37, 99, 235));
        JButton btnLoad = createButton("Load", new Color(124, 58, 237));
        JButton btnView = createButton("View All", new Color(8, 145, 178));
        JButton btnSearch = createButton("Search ID", new Color(217, 119, 6));
        JButton btnSort = createButton("Sort Salary", new Color(219, 39, 119));

        buttons.add(btnAdd);
        buttons.add(btnSave);
        buttons.add(btnLoad);
        buttons.add(btnView);
        buttons.add(btnSearch);
        buttons.add(btnSort);

        // Top panel
        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBackground(lightBlue);

        top.add(searchPanel, BorderLayout.NORTH);
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        // Employee records area
        area = new JTextArea(12, 40);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(area);

        scroll.setBorder(BorderFactory.createTitledBorder(
            "Employee Records"));

        // Clear button
        JButton btnClear = createButton(
            "Clear Fields", new Color(71, 85, 105));

        JPanel bottom = new JPanel();
        bottom.setBackground(lightBlue);
        bottom.add(btnClear);

        main.add(top, BorderLayout.NORTH);
        main.add(scroll, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        add(main, BorderLayout.CENTER);

        // Button actions
        btnAdd.addActionListener(e -> addEmployee());
        btnSave.addActionListener(e -> saveEmployees());
        btnLoad.addActionListener(e -> loadEmployees());
        btnView.addActionListener(e -> viewEmployees());
        btnSearch.addActionListener(e -> searchEmployee());
        btnSort.addActionListener(e -> sortEmployees());
        btnClear.addActionListener(e -> clearFields());

        setVisible(true);
    }

    // Create button
    JButton createButton(String text, Color color)
    {
        JButton button = new JButton(text);

        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);

        return button;
    }

    // Add employee
    void addEmployee()
    {
        try
        {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText().trim();
            double salary = Double.parseDouble(txtSalary.getText());

            String department = cbDepartment.getSelectedItem().toString();

            // Check valid details
            if (id <= 0 || name.isEmpty() || salary < 0)
            {
                JOptionPane.showMessageDialog(this,
                    "Please enter valid details!");
                return;
            }

            // Check duplicate ID
            for (Employee e : employees)
            {
                if (e.id == id)
                {
                    JOptionPane.showMessageDialog(this,
                        "Employee ID already exists!");
                    return;
                }
            }

            // Create employee object
            Employee emp = new Employee(
                id, name, salary, department);

            employees.add(emp);

            JOptionPane.showMessageDialog(this,
                "Employee Added Successfully!\n" +
                "Net Salary: Rs. " + emp.getNetSalary());

            clearFields();
            viewEmployees();
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this,
                "Enter valid Employee ID and Salary!");
        }
    }

    // Save employees in file
    void saveEmployees()
    {
        try
        {
            FileWriter fw = new FileWriter("employees.txt");

            for (Employee e : employees)
            {
                fw.write(e.id + "|" + e.name + "|" +
                    e.salary + "|" + e.department + "\n");
            }

            fw.close();

            JOptionPane.showMessageDialog(this,
                "Records Saved Successfully!");
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(this,
                "Error while saving records!");
        }
    }

    // Load employees from file
    void loadEmployees()
    {
        try
        {
            FileReader fr = new FileReader("employees.txt");
            BufferedReader br = new BufferedReader(fr);

            employees.clear();

            String line;

            while ((line = br.readLine()) != null)
            {
                String[] data = line.split("\\|");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                double salary = Double.parseDouble(data[2]);
                String department = data[3];

                Employee e = new Employee(
                    id, name, salary, department);

                employees.add(e);
            }

            br.close();

            viewEmployees();

            JOptionPane.showMessageDialog(this,
                "Records Loaded Successfully!");
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(this,
                "File not found! Save records first.");
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this,
                "Invalid data in file!");
        }
    }

    // View all employees
    void viewEmployees()
    {
        area.setText("");

        if (employees.isEmpty())
        {
            area.setText("No Employee Records Available.");
            return;
        }

        for (Employee e : employees)
        {
            area.append(e.toString() + "\n");
        }
    }

    // Search employee by ID
    void searchEmployee()
    {
        try
        {
            int id = Integer.parseInt(txtSearch.getText());

            for (Employee e : employees)
            {
                if (e.id == id)
                {
                    area.setText("EMPLOYEE FOUND\n\n");
                    area.append(e.toString());
                    return;
                }
            }

            JOptionPane.showMessageDialog(this,
                "Employee Not Found!");
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this,
                "Enter a valid Employee ID!");
        }
    }

    // Sort employees by salary
    void sortEmployees()
    {
        // Simple bubble sort
        for (int i = 0; i < employees.size(); i++)
        {
            for (int j = 0; j < employees.size() - 1; j++)
            {
                if (employees.get(j).salary >
                    employees.get(j + 1).salary)
                {
                    Employee temp = employees.get(j);

                    employees.set(j, employees.get(j + 1));

                    employees.set(j + 1, temp);
                }
            }
        }

        viewEmployees();

        JOptionPane.showMessageDialog(this,
            "Employees Sorted By Salary!");
    }

    // Clear fields
    void clearFields()
    {
        txtId.setText("");
        txtName.setText("");
        txtSalary.setText("");
        txtSearch.setText("");

        cbDepartment.setSelectedIndex(0);
    }

    public static void main(String[] args)
    {
        new EmployeePayrollSystem();
    }
}
