package io.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
              .start(step1())
              .next(step2())
              .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
              .tasklet((stepContribution, chunkContext) -> {
                  System.out.println("STEP1 - executing!");
                  return RepeatStatus.FINISHED;
              })
              .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step1")
              .tasklet((stepContribution, chunkContext) -> {
                  System.out.println("STEP2 - executing!");
                  return RepeatStatus.FINISHED;
              })
              .build();
    }
}
