package io.springbatchlecture.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
class HelloJobConfiguration {

    // 빌더 팩토리 주입
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     * Job 생성
     * get() - Job 이름 부여
     * start() - step 등록
     */
    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
              .start(helloStep1())
              .next(helloStep2())
              .build();
    }

    /**
     * Step 생성
     * get() - Step 이름 부여
     * tasklet() - Step 안에서 단일 태스크로 수행되는 로직 구현
     */
    @Bean
    public Step helloStep1() {
        return stepBuilderFactory.get("helloStep1")
              .tasklet(((stepContribution, chunkContext) -> {
                  System.out.println("Step1 - Hello Spring Batch");
                  return RepeatStatus.FINISHED;
              }))
              .build();
    }

    @Bean
    public Step helloStep2() {
        return stepBuilderFactory.get("helloStep2")
              .tasklet(((stepContribution, chunkContext) -> {
                  System.out.println("Step2 - Hello Spring Batch");
                  return RepeatStatus.FINISHED;
              }))
              .build();
    }
}