package com.applause;

import com.applause.repositories.BugRepository;
import com.applause.repositories.DeviceRepository;
import com.applause.repositories.TesterRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.transaction.Transactional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableJpaRepositories
public class ApplauseAssignementApplication {


    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("readCSVFileJobDevices")
    @Autowired
    Job processJobDevices;

    @Qualifier("readCSVFileJobTesters")
    @Autowired
    Job processJobTesters;

    @Qualifier("readCSVFileJobBugs")
    @Autowired
    Job processJobBugs;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    TesterRepository testerRepository;

    @Autowired
    BugRepository bugRepository;

/*    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;
    */

    public static void main(String[] args) {
        SpringApplication.run(ApplauseAssignementApplication.class, args);
    }

  /*
    public void perform() throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job, params);
    } */


    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws Exception {
        System.out.println("Spring Boot has started up!");
        System.out.println("Starting importing data from CSV files...");


        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(() -> jobLauncher.run(processJobDevices, new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters()));
        es.submit(() -> jobLauncher.run(processJobTesters, new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters()));

        Thread.sleep(2000);

        es.submit(() -> jobLauncher.run(processJobBugs, new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters()));
        es.shutdown();

/*        jobLauncher.run(processJobDevices, new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters());
        jobLauncher.run(processJobTesters, new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters());
        jobLauncher.run(processJobBugs, new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters());*/

}

}
