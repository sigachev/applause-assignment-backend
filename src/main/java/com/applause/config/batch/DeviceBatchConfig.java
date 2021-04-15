package com.applause.config.batch;

import javax.sql.DataSource;

import com.applause.config.logs.DBLogDeviceProcessor;
import com.applause.model.Device;
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
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class DeviceBatchConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("classpath:/input/devices.csv")
    private Resource inputResource;



    @Bean
    public Job readCSVFileJobDevices() {
        return jobBuilderFactory
                .get("readCSVFileJobDevices")
                .incrementer(new RunIdIncrementer())
                .start(stepDevice())
                .build();
    }

    @Bean
    public Step stepDevice() {
        return stepBuilderFactory
                .get("import devices step")
                .<Device, Device>chunk(1)
                .reader(readerDevice())
                .processor(processorDevice())
                .writer(writerDevice())
                .build();
    }

    @Bean
    public ItemProcessor<Device, Device> processorDevice() {
        return new DBLogDeviceProcessor();
    }

    @Bean
    public FlatFileItemReader<Device> readerDevice() {
        FlatFileItemReader<Device> itemReader = new FlatFileItemReader<Device>();
        itemReader.setLineMapper(lineMapperDevice());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public LineMapper<Device> lineMapperDevice() {
        DefaultLineMapper<Device> lineMapper = new DefaultLineMapper<Device>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "id", "description" });
        lineTokenizer.setIncludedFields(new int[] { 0, 1 });
        BeanWrapperFieldSetMapper<Device> fieldSetMapper = new BeanWrapperFieldSetMapper<Device>();
        fieldSetMapper.setTargetType(Device.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public JdbcBatchItemWriter<Device> writerDevice() {
        JdbcBatchItemWriter<Device> itemWriter = new JdbcBatchItemWriter<Device>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO DEVICE (id, description) VALUES (:id, :description)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Device>());
        return itemWriter;
    }


}
