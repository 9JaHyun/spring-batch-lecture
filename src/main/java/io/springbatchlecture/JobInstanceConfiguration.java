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
class JobInstanceConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     * Main IDEA
     *  - 같은 Job 이어도 파라미터가 다르면 다른 JobInstance 취급
     *  - 같은 Job 이면서 파라미터도 같으면 같은 JobInstance 취급하며 실행이 안됨.
     */
    @Bean
    public Job jobInstance() {
        return jobBuilderFactory.get("jobInstance")
              .start(stepInstance1())
              .next(stepInstance2())
              .build();
    }

    @Bean
    public Step stepInstance1() {
        return stepBuilderFactory.get("stepInstance1")
              .tasklet(new Tasklet() {
                  @Override
                  public RepeatStatus execute(StepContribution stepContribution,
                        ChunkContext chunkContext) throws Exception {
                      System.out.println("stepInstance1 - executing!");
                      return RepeatStatus.FINISHED;
                  }
              })
              .build();
    }

    @Bean
    public Step stepInstance2() {
        return stepBuilderFactory.get("stepInstance2")
              .tasklet(new Tasklet() {
                  @Override
                  public RepeatStatus execute(StepContribution stepContribution,
                        ChunkContext chunkContext) throws Exception {
                      System.out.println("stepInstance2 - executing!");
                      return RepeatStatus.FINISHED;
                  }
              })
              .build();
    }
}
