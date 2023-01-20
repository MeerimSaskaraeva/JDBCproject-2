package java8.dao;

import java8.Util;
import java8.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobDaoImpl implements JobDao {
    Connection connection;

    public JobDaoImpl() {
        this.connection = Util.getConnection();
    }

    @Override
    public void createJobTable() {
        String query = """
                create table jobs(
                id serial primary key ,
                position varchar(50),
                profession varchar,
                description varchar,
                experience int);
                """;
        try (Statement statement = connection.createStatement();) {
            statement.executeQuery(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addJob(Job job) {
        String query = """
                insert into jobs(position,profession,description,experience)
                values (?,?,?,?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4, job.getExperience());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public Job getJobById(Long jobId) {
        String query = """
                select * from jobs
                where id=?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, jobId);

            ResultSet resultSet = preparedStatement.executeQuery();
            Job job = new Job();

            while (resultSet.next()) {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString(2));
                job.setProfession(resultSet.getString(3));
                job.setDescription(resultSet.getString(4));
                job.setExperience(resultSet.getInt(5));

            }
            resultSet.close();
            return job;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> jobList = new ArrayList<>();
        String sortSql = null;
        if (Objects.equals(ascOrDesc, "asc")) {
            sortSql = """
                    select * from jobs
                    order by experience;
                    """;
        } else if (Objects.equals(ascOrDesc, "desc")) {
            sortSql = """
                    select * from jobs
                    order by experience desc;
                    """;
        }
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sortSql);
            while (resultSet.next()) {
                Job job = new Job();

                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString(2));
                job.setProfession(resultSet.getString(3));
                job.setDescription(resultSet.getString(4));
                job.setExperience(resultSet.getInt(5));
                jobList.add(job);
            }
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jobList;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        String query= """
                select * from jobs j 
                join employees e 
                on j.id = e.job_id
                where j.id=?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1,employeeId);

            ResultSet resultSet = preparedStatement.executeQuery();
            Job job=new Job();
            while (resultSet.next()){
               job.setId(resultSet.getLong(1));
               job.setPosition(resultSet.getString(2));
               job.setProfession(resultSet.getString(3));
               job.setDescription(resultSet.getString(4));
               job.setExperience(resultSet.getInt(5));
            }
            resultSet.close();
            return job;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteDescriptionColumn() {

        String query= """
                alter table jobs drop column description;
                """;
        try (Statement statement = connection.createStatement();){
            statement.executeQuery(query);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
