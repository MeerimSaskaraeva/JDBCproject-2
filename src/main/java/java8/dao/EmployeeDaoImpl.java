package java8.dao;

import java8.Util;
import java8.model.Employee;
import java8.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    Connection connection;

    public EmployeeDaoImpl() {
        this.connection = Util.getConnection();
    }

    @Override
    public void createEmployee() {
        String quary = """
                create table employees(
                id serial primary key ,
                first_name varchar(50) not null ,
                last_name varchar,
                age smallint,
                email varchar unique ,
                job_id int references jobs(id));
                """;
        try (Statement statement = connection.createStatement();) {
            statement.executeQuery(quary);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void addEmployee(Employee employee) {
        String quary = """
                insert into employees(first_name,last_name,age,email,job_id)
                values (?,?,?,?,?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(quary);) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void dropTable() {
        String query = """
                drop table employees;
                """;
        try (Statement statement = connection.createStatement();) {
            statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void cleanTable() {
        String query = """
                truncate table employees;
                """;
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String query = """
                update employees
                set first_name=?,
                last_name=?,
                age=?,
                email=?,
                job_id=?
                where id=?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.setLong(6, id);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("Successfully updated...");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        String query = """
                select * from employees;
                """;
        try (Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setAge(resultSet.getInt(4));
                employee.setEmail(resultSet.getString(5));
                employee.setJobId(resultSet.getInt(6));
                employeeList.add(employee);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return employeeList;
    }

    @Override
    public Employee findByEmail(String email) {
        String query = """
                select * from employees
                where email=?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            Employee employee = new Employee();
            while (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setAge(resultSet.getInt(4));
                employee.setEmail(resultSet.getString(5));
                employee.setJobId(resultSet.getInt(6));

            }
            resultSet.close();
            return employee;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee, Job> map = new HashMap<>();
        Employee employee = new Employee();
        Job job = new Job();
        String empQuery = """
                select * from employees where id=?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(empQuery)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setAge(resultSet.getInt(4));
                employee.setEmail(resultSet.getString(5));
                employee.setJobId(resultSet.getInt(6));

            }
            resultSet.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String jobQuery = """
                select * from jobs j 
                join employees e 
                on j.id = e.job_id
                where j.id=?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(jobQuery)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString(2));
                job.setProfession(resultSet.getString(3));
                job.setDescription(resultSet.getString(4));
                job.setExperience(resultSet.getInt(5));
            }
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        map.put(employee, job);
        return map;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employeeList = new ArrayList<>();
        String query = """
                select * from employees e 
                join jobs j on j.id = e.job_id
                where position=?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, position);

            ResultSet resultSet = preparedStatement.executeQuery();
            Employee employee = new Employee();

            while (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setAge(resultSet.getInt(4));
                employee.setEmail(resultSet.getString(5));
                employee.setJobId(resultSet.getInt(6));
                employeeList.add(employee);
            }
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeeList;
    }
}
