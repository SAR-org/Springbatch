package com.sar.spring.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.sar.spring.dto.EmployeeDTO;
import com.sar.spring.mapper.EmployeeFileRowMapper;
import com.sar.spring.model.Employee;
import com.sar.spring.processor.EmployeeProcessor;
import com.sar.spring.writer.EmployeeDBWriter;

@Configuration
public class DemoJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private EmployeeDBWriter employeeDBWriter;

	@Bean
	public Job flatFileJob() {
		return this.jobBuilderFactory.get("flatFileJob").start(flatFileStep1()).build();
	}
	@Bean
	public Step flatFileStep1() {
		return this.stepBuilderFactory.get("step1").<EmployeeDTO, Employee>chunk(5)
				.reader(employeeReader())
				.processor(new EmployeeProcessor())
				//.writer(employeeDBWriterDefault()) this is using defaul writer
				.writer(employeeDBWriter) // using a custom database writer
				.build();
	}

	private JdbcBatchItemWriter<Employee>  employeeDBWriterDefault() {
		JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql(
				"INSERT INTO employee(employee_id,first_name,last_name,email_id,gender) VALUES(:employeeId, :firstName, :lastName, :emailId, :gender)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
		itemWriter.afterPropertiesSet ();
		return itemWriter;
	}

	@Bean
	public FlatFileItemReader<EmployeeDTO> employeeReader() {
		FlatFileItemReader<EmployeeDTO> reader = new FlatFileItemReader<EmployeeDTO>();
		
		reader.setResource(new ClassPathResource("employee.csv"));
		reader.setLinesToSkip(1);
		reader.setLineMapper(new DefaultLineMapper<EmployeeDTO>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames("employeeId", "firstName", "lastName", "email", "gender");
					}
				});
				setFieldSetMapper(new EmployeeFileRowMapper());
			}
		});
		
		return reader;
	}
}
