package com.mycompany.logistik.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Utilitas untuk menghasilkan nomor resi ekspedisi yang unik dan otomatis.
 */
public class ResiGenerator {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Random RANDOM = new Random();

    public static String generateResi() {
        String datePart = LocalDateTime.now().format(DATE_FORMAT);
        int randomPart = 1000 + RANDOM.nextInt(9000); // 4 digit angka acak (1000 - 9999)
        return "EXP-" + datePart + "-" + randomPart;
    }
}

