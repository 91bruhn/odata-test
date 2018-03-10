////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service;

import org.mongodb.morphia.converters.LocalDateConverter;
import org.mongodb.morphia.converters.LocalDateTimeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 */
public class test {

public static void main (String [] args){
    String date = "01.10.2017 11:15:20.900000";

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSSSSS");
    final LocalDate d = LocalDate.parse(date, formatter);
    LocalDateTime lcd = LocalDateTime.parse(date, formatter);
    final LocalDateConverter ldc = new LocalDateConverter();
    LocalDateTimeConverter ldtc = new LocalDateTimeConverter();
    Date bb = (Date) ldtc.encode(lcd);

    System.out.println();
}

}
