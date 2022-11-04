package io.springbatchlecture;

import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MAIN IDEA
 *   - Parameters를 가지고 올 수 있는 두가지 방법에 대해 알아보자.
 *
 *   stepContribution
 *      - StepContribution -> StepExecution -> JobExecution -> JobParameters 참조로 가져오기
 *      - JobParameters 타입으로 가져온다.
 *      - 더 많이 사용
 *
 *   chunkContext
 *      - ChunkContext -> StepContext -> StepExecution -> JobExecution -> JobParameters 참조로 가져오기
 *      - 타입이 JobParameters 이 아닌 Map<String, Object> 타입이기 떄문에 Casting 필요
 */
@RequiredArgsConstructor
@Configuration
class JobParameterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobParameters() {
        return jobBuilderFactory.get("jobParameters")
              .start(stepParameters1())
              .next(stepParameters2())
              .build();
    }

    @Bean
    public Step stepParameters1() {
        return stepBuilderFactory.get("stepParameters1")
              .tasklet(new Tasklet() {
                  @Override
                  public RepeatStatus execute(StepContribution stepContribution,
                        ChunkContext chunkContext) throws Exception {
                      // StepContribution 에서 가져오기
                      JobParameters jobParameters = stepContribution.getStepExecution()
                            .getJobExecution().getJobParameters();
                      String name = jobParameters.getString("name");
                      Long seq = jobParameters.getLong("seq");
                      Double age = jobParameters.getDouble("age");
                      Date date = jobParameters.getDate("date");
                      System.out.printf("%d - name: %s, age: %f, [date: %s]\n", seq, name, age, date);
                      return RepeatStatus.FINISHED;
                  }
              })
              .build();
    }

    @Bean
    public Step stepParameters2() {
        return stepBuilderFactory.get("stepParameters2")
              .tasklet(new Tasklet() {
                  @Override
                  public RepeatStatus execute(StepContribution stepContribution,
                        ChunkContext chunkContext) throws Exception {
                      // chunkContext 로 부터 가져오기
                      Map<String, Object> jobParameters = chunkContext.getStepContext()
                            .getJobParameters();
                      // Object 타입으로 가져오기 때문에 Casting 이 필요하다
                      String name = (String) jobParameters.get("name");
                      Long seq = (Long) jobParameters.get("seq");
                      Double age = (Double) jobParameters.get("age");
                      Date date = (Date) jobParameters.get("date");
                      System.out.printf("%d - name: %s, age: %f, [date: %s]\n", seq, name, age, date);

                      return RepeatStatus.FINISHED;
                  }
              })
              .build();
    }
}
