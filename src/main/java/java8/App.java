package java8;

import java8.model.Employee;
import java8.model.Job;
import java8.service.EmployeeService;
import java8.service.EmployeeServiceImpl;
import java8.service.JobService;
import java8.service.JobServiceImpl;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        JobService jobService = new JobServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();

        while (true) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~Commands~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println( """
                    1==Create job table
                    2==Create employee
                    3==Add employee
                    4==Drop employee table
                    5==Get job by id
                    6==Clear employee table
                    7==Sort by job experience
                    8==Update employee
                    9==Get all employees
                    10==Get job by employee id
                    11==Find by employee email
                    12==Get employee by id
                    13==Get employee by position
                    14==delete description column
                    """);
            int choose = new Scanner(System.in).nextInt();
            switch (choose) {
                case 1 -> jobService.createJobTable();
                case 2 -> {
                    employeeService.createEmployee();
                    jobService.addJob(new Job("Mentor", "Java", "Backend Developer", 2));
                    jobService.addJob(new Job("Mentor", "JavaScript", "Fronted developer", 1));
                    jobService.addJob(new Job("Management", "JavaScript", "Fronted developer", 3));
                    jobService.addJob(new Job("Instructor", "Java", "Backend developer", 1));
                }
                case 3 -> {
                    employeeService.addEmployee(new Employee("Asel", "Urakunova", 30, "asel@gmail.com", 1));
                    employeeService.addEmployee(new Employee("Ulan", "Asanov", 25, "ulan@gmail.com", 2));
                    employeeService.addEmployee(new Employee("Esen", "Osmonov", 28, "esen@gmail.com", 3));
                    employeeService.addEmployee(new Employee("Aijan", "Asakeeva", 22, "aijan@gmail.com", 4));
                }
                case 4 -> employeeService.dropTable();
                case 5 -> System.out.println(jobService.getJobById(1L));
                case 6 -> employeeService.cleanTable();
                case 7 -> {
                    System.out.println("Write 'asc' or 'desc'");
                    String ascOrDesc = new Scanner(System.in).nextLine();
                    System.out.println(jobService.sortByExperience(ascOrDesc));
                }
                case 8 -> employeeService.updateEmployee(2L, new Employee("Maksat", "Dosov", 32, "maksat@gmail.com", 1));

                case 9 -> System.out.println(employeeService.getAllEmployees());
                case 10 -> System.out.println(jobService.getJobByEmployeeId(3L));

                case 11 -> System.out.println(employeeService.findByEmail("maksat@gmail.com"));

                case 12 -> System.out.println(employeeService.getEmployeeById(3L));

                case 13 -> System.out.println(employeeService.getEmployeeByPosition("Mentor"));
                case 14 -> jobService.deleteDescriptionColumn();
            }
        }
    }
}
