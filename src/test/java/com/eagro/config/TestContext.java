package com.eagro.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class TestContext {

  

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * As json string.
     *
     * @param obj the obj
     * @return the string
     */
    public static String convertToJson(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadJsonFile(final String jsonFilePath) {
        StringBuilder response = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath))) {
            response = new StringBuilder();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

        } catch (final IOException e) {
            e.printStackTrace();
        }

        if (response != null) {
            return response.toString();
        } else {
            return "";
        }
    }
}
