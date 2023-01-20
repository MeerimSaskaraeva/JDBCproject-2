package java8.service;

import java8.dao.EmployeeDao;
import java8.dao.EmployeeDaoImpl;
import java8.model.Employee;
import java8.model.Job;

import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public void createEmployee() {
        System.out.println("Employee successfully created!!!");
        employeeDao.createEmployee();

    }

    @Override
    public void addEmployee(Employee employee) {
        employeeDao.addEmployee(employee);
        System.out.println("Employee successfully added....");

    }

    @Override
    public void dropTable() {
        employeeDao.dropTable();
        System.out.println("Employee dropped...");

    }

    @Override
    public void cleanTable() {
        employeeDao.cleanTable();
        System.out.println("Successfully cleared...");

    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        employeeDao.updateEmployee(id, employee);

    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();

    }

    @Override
    public Employee findByEmail(String email) {
        return employeeDao.findByEmail(email);

    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        return employeeDao.getEmployeeById(employeeId);

    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        return employeeDao.getEmployeeByPosition(position);

    }
}
