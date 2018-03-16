////////////////////////////////////////////////////////////////////////////////
//
// Created by BBruhns on 10.03.2018.
//
// Copyright (c) 2006 - 2018 FORCAM GmbH. All rights reserved.
////////////////////////////////////////////////////////////////////////////////

package myservice.mynamespace.database.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import myservice.mynamespace.database.data.enums.UnitOfCurrency;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class test {

    public static void main(String[] args) {
        //    String date = "01.10.2017 11:15:20.900000";
        //
        //    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSSSSS");
        //    final LocalDate d = LocalDate.parse(date, formatter);
        //    LocalDateTime lcd = LocalDateTime.parse(date, formatter);
        //    final LocalDateConverter ldc = new LocalDateConverter();
        //    LocalDateTimeConverter ldtc = new LocalDateTimeConverter();
        //    Date bb = (Date) ldtc.encode(lcd);

        String v = "USD";
        String v2 = null;


        UnitOfCurrency s = UnitOfCurrency.valueOf(v);
        UnitOfCurrency s2 = v2 == null ? null : UnitOfCurrency.valueOf(v2);
        System.out.println(s2);
        Boolean sd = null;

    }

    public static boolean idTaken(String idToCheckIfTaken) {
        return StringUtils.isEmpty(idToCheckIfTaken) || false;
    }

}
