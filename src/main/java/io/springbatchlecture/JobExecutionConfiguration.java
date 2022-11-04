package io.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobExecutionConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job executionJob() {
        return jobBuilderFactory.get("executionJob")
              .start(step1())
//              .next(step2())
              .next(failedStep1())
              .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("executionStep1")
              .tasklet(new Tasklet() {
                  @Override
                  public RepeatStatus execute(StepContribution stepContribution,
                        ChunkContext chunkContext) throws Exception {
                      System.out.println("executionStep1 - executed!");
                      return RepeatStatus.FINISHED;
                  }
              })
              .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("executionStep2")
              .tasklet(new Tasklet() {
                  @Override
                  public RepeatStatus execute(StepContribution stepContribution,
                        ChunkContext chunkContext) throws Exception {
                      System.out.println("executionStep2 - executed!");
                      return RepeatStatus.FINISHED;
                  }
              })
              .build();
    }

    @Bean
    public Step failedStep1() {
        return stepBuilderFactory.get("executionFailedStep1")
              .tasklet(new Tasklet() {
                  @Override
                  public RepeatStatus execute(StepContribution stepContribution,
                        ChunkContext chunkContext) throws Exception {
                      System.out.println("executionFailedStep1 - executed!");
//                      throw new RuntimeException("executionFailedStep1 - failed!");
                      return RepeatStatus.FINISHED;
                  }
              })
              .build();
    }
}
