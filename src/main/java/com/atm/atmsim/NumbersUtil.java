package com.atm.atmsim;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public class NumbersUtil {

    public static BigDecimal newRandomBigDecimal(Random r, int precision) {

        BigInteger n = BigInteger.TEN.pow(precision);

        return new BigDecimal(newRandomBigInteger(n, r), precision);

    }

    private static BigInteger newRandomBigInteger(BigInteger n, Random rnd) {

        BigInteger r;

        do {

            r = new BigInteger(n.bitLength(), rnd);

        } while (r.compareTo(n) >= 0);

        return r;

    }

    public static boolean newRandomBoolean() {

        Random random = new Random();

        return random.nextBoolean();

    }

}
