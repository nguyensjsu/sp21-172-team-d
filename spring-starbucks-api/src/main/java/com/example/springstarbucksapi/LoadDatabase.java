package com.example.springstarbucksapi;

import java.math.BigDecimal;
import java.util.Map;

import com.example.springstarbucksapi.model.Price;
import com.example.springstarbucksapi.repository.DrinkRepository;
import com.example.springstarbucksapi.repository.PriceRepository;
import com.example.springstarbucksapi.repository.SizeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(DrinkRepository drinkRepository, SizeRepository sizeRepository,
            PriceRepository priceRepository) {

        return args -> {
            // Drink latte = drinkRepository.save(new Drink("Caffe Latte"));
            // Drink americano = drinkRepository.save(new Drink("Caffe Americano"));
            // Drink mocha = drinkRepository.save(new Drink("Caffe Mocha"));
            // Drink cappuccino = drinkRepository.save(new Drink("Cappuccino"));
            // Drink espresso = drinkRepository.save(new Drink("Espresso"));
            // drinkRepository.findAll().forEach(drink -> log.info("Preloaded " + drink));

            // Size shortSize = sizeRepository.save(new Size("short"));
            // Size tall = sizeRepository.save(new Size("tall"));
            // Size grande = sizeRepository.save(new Size("grande"));
            // Size venti = sizeRepository.save(new Size("venti"));
            // Size trenta = sizeRepository.save(new Size("trenta"));
            // sizeRepository.findAll().forEach(size -> log.info("Preloaded " + size));
            
            // priceRepository.save(new Price(latte, tall, new BigDecimal("2.95")));
            // priceRepository.save(new Price(latte, grande, new BigDecimal("3.65")));
            // priceRepository.save(new Price(latte, venti, new BigDecimal("3.95")));
            // priceRepository.findAll().forEach(price -> log.info("Preloaded " + price));

            // Drink latte = new Drink("Caffe Latte");
            // Size tall = new Size("tall");
            // priceRepository.save(new Price(new Drink("Caffe Latte"), new Size("tall"), 2.95));
            // priceRepository.save(new Price(latte, tall, new BigDecimal("2.95")));
            // priceRepository.save(new Price(latte, grande, new BigDecimal("3.65")));
            // priceRepository.save(new Price(latte, venti, new BigDecimal("3.95")));
            // priceRepository.findAll().forEach(price -> log.info("Preloaded " + price));
            
            priceRepository.save(new Price("Caffe Latte", "tall", "2.95"));
            priceRepository.save(new Price("Caffe Latte", "grande", "3.65"));
            priceRepository.save(new Price("Caffe Latte", "venti", "3.95"));
            
            priceRepository.save(new Price("Caffe Americano", "tall", "2.25"));
            priceRepository.save(new Price("Caffe Americano", "grande", "2.65"));
            priceRepository.save(new Price("Caffe Americano", "venti", "2.95"));
            
            priceRepository.save(new Price("Caffe Mocha", "tall", "3.45"));
            priceRepository.save(new Price("Caffe Mocha", "grande", "4.15"));
            priceRepository.save(new Price("Caffe Mocha", "venti", "4.45"));
            
            priceRepository.save(new Price("Cappuccino", "tall", "2.95"));
            priceRepository.save(new Price("Cappuccino", "grande", "3.65"));
            priceRepository.save(new Price("Cappuccino", "venti", "3.95"));
            
            priceRepository.save(new Price("Espresso", "short", "1.75"));
            priceRepository.save(new Price("Espresso", "tall", "1.95"));
            priceRepository.findAll().forEach(item -> log.info("Preloaded " + item));
        };
    }
}
