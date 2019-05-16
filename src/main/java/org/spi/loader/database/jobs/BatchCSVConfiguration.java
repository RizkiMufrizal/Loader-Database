package org.spi.loader.database.jobs;

import lombok.extern.slf4j.Slf4j;
import org.spi.loader.database.listener.JobCompletionNotificationListener;
import org.spi.loader.database.model.Person;
import org.spi.loader.database.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 16 May 2019
 * @Time 11:04
 * @Project Loader-Database
 * @Package org.spi.loader.database.jobs
 * @File BatchCSVConfiguration
 */
@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchCSVConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersonItemProcessor personItemProcessor() {
        return new PersonItemProcessor();
    }

    @Bean
    public FlatFileItemReader<Person> personFlatFileItemReader() {
        FlatFileItemReader<Person> personFlatFileItemReader = new FlatFileItemReader<>();
        personFlatFileItemReader.setResource(new ClassPathResource("person.csv"));
        personFlatFileItemReader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setDelimiter(";");
                setNames("name", "address", "birthday");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
        return personFlatFileItemReader;
    }

    @Bean
    public JdbcBatchItemWriter<Person> personJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Person> personJdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        personJdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        personJdbcBatchItemWriter.setSql("INSERT INTO tb_person (name_person, address_person, birthday_person) VALUES (:name, :address, :birthday)");
        personJdbcBatchItemWriter.setDataSource(dataSource);
        return personJdbcBatchItemWriter;
    }

    @Bean
    public Step stepWriteDB() {
        return stepBuilderFactory.get("stepWriteDB").<Person, Person>chunk(10).reader(personFlatFileItemReader())
                .processor(personItemProcessor()).writer(personJdbcBatchItemWriter()).build();
    }

    @Bean
    public Job importPersonJob(JobCompletionNotificationListener jobCompletionNotificationListener) {
        return jobBuilderFactory.get("importPersonJob").incrementer(new RunIdIncrementer())
                .listener(jobCompletionNotificationListener).flow(stepWriteDB()).end().build();
    }
}