package org.spi.loader.database.processor;

import lombok.extern.slf4j.Slf4j;
import org.spi.loader.database.model.Person;
import org.springframework.batch.item.ItemProcessor;

/**
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 16 May 2019
 * @Time 11:07
 * @Project Loader-Database
 * @Package org.spi.loader.database.processor
 * @File PersonItemProcessor
 */
@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) throws Exception {
        Person personTransform = new Person(person.getName(), person.getAddress(), person.getBirthday());
        log.info("person processor from {} to {}", person, personTransform);
        return person;
    }
}