package io.springbatchlecture;

import java.util.Date;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

// JobInstance의 중복을 확인하기 위해 실행 JobLauncher 을 따로 생성
@Component
public class JobRunner implements ApplicationRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Job Parameters 역시 빌더 팩토리를 제공해준다.
        JobParameters jobParameters = new JobParametersBuilder()
              .addString("name", "user2")
              .addLong("seq", 2L)
              .addDate("date", new Date())
              .addDouble("age", 16.5)
              .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}