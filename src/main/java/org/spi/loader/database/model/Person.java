package org.spi.loader.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 16 May 2019
 * @Time 11:05
 * @Project Loader-Database
 * @Package org.spi.loader.database.model
 * @File Person
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Person {

    private String name;
    private String address;
    private String birthday;
}