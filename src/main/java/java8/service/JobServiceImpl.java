package java8.service;

import java8.dao.JobDao;
import java8.dao.JobDaoImpl;
import java8.model.Job;

import java.util.List;

public class JobServiceImpl implements JobService {
    JobDao jobDao = new JobDaoImpl();

    @Override
    public void createJobTable() {

        jobDao.createJobTable();
        System.out.println("Job successfully created!!!");

    }

    @Override
    public void addJob(Job job) {
        jobDao.addJob(job);
        System.out.println("Job successfully added....");


    }

    @Override
    public Job getJobById(Long jobId) {
        return jobDao.getJobById(jobId);
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
       return jobDao.sortByExperience(ascOrDesc);

    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
      return   jobDao.getJobByEmployeeId(employeeId);

    }

    @Override
    public void deleteDescriptionColumn() {
        jobDao.deleteDescriptionColumn();

    }
}
