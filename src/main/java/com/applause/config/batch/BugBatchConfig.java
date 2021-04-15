package com.applause.config.batch;

import javax.sql.DataSource;

import com.applause.config.logs.DBLogBugDTOProcessor;
import com.applause.model.batch.BugDTO;
import com.applause.repositories.BugRepository;
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
public class BugBatchConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    BugRepository bugRepository;

    @Value("classpath:/input/bugs.csv")
    private Resource inputResource;



    @Bean
    public Job readCSVFileJobBugs() {
        return jobBuilderFactory
                .get("readCSVFileJobBugDTOs")
                .incrementer(new RunIdIncrementer())
                .start(stepBug())
                .build();
    }

    @Bean
    public Step stepBug() {
        return stepBuilderFactory
                .get("import BugDTOs step")
                .<BugDTO, BugDTO>chunk(1)
                .reader(readerBug())
                .processor(processorBug())
                .writer(writerBug())
                .build();
    }

    @Bean
    public ItemProcessor<BugDTO, BugDTO> processorBug() {
        return new DBLogBugDTOProcessor();
    }

    @Bean
    public FlatFileItemReader<BugDTO> readerBug() {
        FlatFileItemReader<BugDTO> itemReader = new FlatFileItemReader<BugDTO>();
        itemReader.setLineMapper(lineMapperBug());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public LineMapper<BugDTO> lineMapperBug() {
        DefaultLineMapper<BugDTO> lineMapper = new DefaultLineMapper<BugDTO>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "id", "deviceId", "testerId" });
        lineTokenizer.setIncludedFields(new int[] { 0, 1, 2 });
        BeanWrapperFieldSetMapperCustomLocalDateTime<BugDTO> fieldSetMapper = new BeanWrapperFieldSetMapperCustomLocalDateTime<BugDTO>();
        fieldSetMapper.setTargetType(BugDTO.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public JdbcBatchItemWriter writerBug() {
        JdbcBatchItemWriter itemWriter = new JdbcBatchItemWriter();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO BUG (id, device_id, tester_id) VALUES (:id, :deviceId, :testerId)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        return itemWriter;
    }



}
