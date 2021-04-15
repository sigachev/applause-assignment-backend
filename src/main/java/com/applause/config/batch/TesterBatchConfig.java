package com.applause.config.batch;

import javax.sql.DataSource;

import com.applause.config.logs.DBLogTesterProcessor;
import com.applause.model.Tester;
import com.applause.utils.BeanWrapperFieldSetMapperCustomLocalDateTime;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class TesterBatchConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("classpath:/input/testers.csv")
    private Resource inputResource;



    @Bean
    public Job readCSVFileJobTesters() {
        return jobBuilderFactory
                .get("readCSVFileJobTesters")
                .incrementer(new RunIdIncrementer())
                .start(stepTester())
                .build();
    }

    @Bean
    public Step stepTester() {
        return stepBuilderFactory
                .get("import testers step")
                .<Tester, Tester>chunk(1)
                .reader(readerTester())
                .processor(processorTester())
                .writer(writerTester())
                .build();
    }

    @Bean
    public ItemProcessor<Tester, Tester> processorTester() {
        return new DBLogTesterProcessor();
    }

    @Bean
    public FlatFileItemReader<Tester> readerTester() {
        FlatFileItemReader<Tester> itemReader = new FlatFileItemReader<Tester>();
        itemReader.setLineMapper(lineMapperTester());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public LineMapper<Tester> lineMapperTester() {
        DefaultLineMapper<Tester> lineMapper = new DefaultLineMapper<Tester>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "id", "firstName", "lastName", "country", "lastLogin" });
        lineTokenizer.setIncludedFields(new int[] { 0, 1, 2, 3, 4 });
        BeanWrapperFieldSetMapperCustomLocalDateTime<Tester> fieldSetMapper = new BeanWrapperFieldSetMapperCustomLocalDateTime<Tester>();
        fieldSetMapper.setTargetType(Tester.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public JdbcBatchItemWriter<Tester> writerTester() {
        JdbcBatchItemWriter<Tester> itemWriter = new JdbcBatchItemWriter<Tester>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO TESTER (id, first_name, last_name, country, last_login ) " +
                "VALUES (:id, :firstName, :lastName, :country, :lastLogin )");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Tester>());
        return itemWriter;
    }


}
