package com.example.client.pocbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.client.pocbatch.city.CityItemReader;
import com.example.client.pocbatch.city.CityItemWriter;
import com.example.client.pocbatch.continent.ContinentItemProcessor;
import com.example.client.pocbatch.continent.ContinentItemReader;
import com.example.client.pocbatch.continent.ContinentItemWriter;
import com.example.client.pocbatch.country.CountryItemReader;
import com.example.client.pocbatch.country.CountryItemWriter;
import com.example.client.pocbatch.person.PersonItemReader;
import com.example.client.pocbatch.person.PersonItemWriter;
import com.exemple.poc.client.dto.response.CityDTO;
import com.exemple.poc.client.dto.response.ContinentDTO;
import com.exemple.poc.client.dto.response.CountryDTO;
import com.exemple.poc.client.dto.response.PersonDTO;



@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public Job processOrderJob() {
        return jobBuilderFactory.get("processOrderJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(continentStep())
                .next(countryStep())
                .next(cityStep())
                //.next(personStep())
                .end()
                .build();
    }
    
    @Bean
    public Step personStep() {
    	PersonItemReader reader = new PersonItemReader();
    	reader.init();
    	
        return stepBuilderFactory.get("personStep").<PersonDTO, PersonDTO> chunk(100)
        		.reader(reader)
                .writer(personWriter())
                .build();
    }
    
    
    
    @Bean
    public Step cityStep() {
    	CityItemReader reader = new CityItemReader();
    	reader.init();
    	
        return stepBuilderFactory.get("cityStep").<CityDTO, CityDTO> chunk(100)
        		.reader(reader)
                .writer(cityWriter())
                .build();
    }
    
    @Bean
    public Step countryStep() {
    	CountryItemReader reader = new CountryItemReader();
    	reader.init();
    	
        return stepBuilderFactory.get("countryStep").<CountryDTO, CountryDTO> chunk(10)
        		.reader(reader)
                //.processor(processor())
                .writer(countryWriter())
                .build();
    }
    
    @Bean
    public Step continentStep() {
    	ContinentItemReader reader = new ContinentItemReader();
    	reader.init();
    	
        return stepBuilderFactory.get("continentStep").<ContinentDTO, ContinentDTO> chunk(1)
        		.reader(reader)
                .processor(processor())
                .writer(continentWriter())
                .build();
    }
    

    /*@Bean
    public FlatFileItemReader<ContinentDTO> reader() {
        FlatFileItemReader<ContinentDTO> reader = new FlatFileItemReader<ContinentDTO>();
        reader.setResource(new ClassPathResource("ContinentData.csv"));
        reader.setLineMapper(new DefaultLineMapper<ContinentDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "code", "name"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<ContinentDTO>() {{
                setTargetType(ContinentDTO.class);
            }});
        }});
        return reader;
    }*/

    @Bean
    public ContinentItemProcessor processor() {
        return new ContinentItemProcessor();
    }
    
    @Bean
    public ItemWriter<ContinentDTO> continentWriter() {
        return new ContinentItemWriter();
    }
    
    @Bean
    public ItemWriter<CityDTO> cityWriter() {
        return new CityItemWriter();
    }
    
    @Bean
    public ItemWriter<PersonDTO> personWriter() {
        return new PersonItemWriter();
    }
    
    @Bean
    public ItemWriter<CountryDTO> countryWriter() {
        return new CountryItemWriter();
    }

   @Bean
    public JobExecutionListener listener() {
        return new JobCompletionNotificationListener();
    }
    
}