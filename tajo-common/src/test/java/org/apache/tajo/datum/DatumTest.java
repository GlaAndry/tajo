package org.apache.tajo.datum;

import org.apache.tajo.type.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class DatumTest {

    Datum int4_1;
    Datum int4_2;
    Datum int4_3;

    Object returnInt4_sum;
    Object returnInt4_subtraction;
    Object returnInt4_multiply;
    Object returnInt4_divide;


    Datum int8_1;
    Datum int8_2;
    Datum int8_3;

    Object returnInt8_sum;
    Object returnInt8_subtraction;
    Object returnInt8_multiply;
    Object returnInt8_divide;

    Datum float4_1;
    Datum float4_2;
    Datum float4_3;

    Object returnFloat4_sum;
    Object returnFloat4_subtraction;
    Object returnFloat4_multiply;
    Object returnFloat4_divide;


    Datum float8_1;
    Datum float8_2;
    Datum float8_3;

    Object returnFloat8_sum;
    Object returnFloat8_subtraction;
    Object returnFloat8_multiply;
    Object returnFloat8_divide;


    @Parameterized.Parameters
    public static Collection Int2DatumParameters() throws Exception {
        return Arrays.asList(new Object[][]{
                {DatumFactory.createInt4(1), DatumFactory.createInt4(2), null, 3, -1, 2, 0,
                        DatumFactory.createInt8(1L), DatumFactory.createInt8(2L), null, 3L, -1L, 2L, 2L,
                        DatumFactory.createFloat4(1f), DatumFactory.createFloat4(2.5f), null, 3.5f, -1.5f, 2.5f, 0.4f,
                        DatumFactory.createFloat8(1d), DatumFactory.createFloat8(4.5d), null, 5.5d, -3.5d, 4.5d, 0.2222222222222222d},

                {DatumFactory.createInt4(2), DatumFactory.createInt4(2), null, 4, 0, 4, 1,
                        DatumFactory.createInt8(2L), DatumFactory.createInt8(2L), null, 4L, 0L, 4L, 4L,
                        DatumFactory.createFloat4(3f), DatumFactory.createFloat4(2.5f), null, 5.5f, 0.5f, 7.5f, 1.2f,
                        DatumFactory.createFloat8(1d), DatumFactory.createFloat8(4.5d), null, 5.5d, -3.5d, 4.5d, 0.2222222222222222d}
        });
    }

    public DatumTest(Datum int4_1, Datum int4_2, Datum int4_3, Object returnInt4_sum, Object returnInt4_subtraction, Object returnInt4_multiply, Object returnInt4_divide,
                     Datum int8_1, Datum int8_2, Datum int8_3, Object returnInt8_sum, Object returnInt8_subtraction, Object returnInt8_multiply, Object returnInt8_divide,
                     Datum float4_1, Datum float4_2, Datum float4_3, Object returnFloat4_sum, Object returnFloat4_subtraction, Object returnFloat4_multiply, Object returnFloat4_divide,
                     Datum float8_1, Datum float8_2, Datum float8_3, Object returnFloat8_sum, Object returnFloat8_subtraction, Object returnFloat8_multiply, Object returnFloat8_divide) {
        this.int4_1 = int4_1;
        this.int4_2 = int4_2;
        this.int4_3 = int4_3;
        this.returnInt4_sum = returnInt4_sum;
        this.returnInt4_subtraction = returnInt4_subtraction;
        this.returnInt4_multiply = returnInt4_multiply;
        this.returnInt4_divide = returnInt4_divide;
        this.int8_1 = int8_1;
        this.int8_2 = int8_2;
        this.int8_3 = int8_3;
        this.returnInt8_sum = returnInt8_sum;
        this.returnInt8_subtraction = returnInt8_subtraction;
        this.returnInt8_multiply = returnInt8_multiply;
        this.returnInt8_divide = returnInt8_divide;
        this.float4_1 = float4_1;
        this.float4_2 = float4_2;
        this.float4_3 = float4_3;
        this.returnFloat4_sum = returnFloat4_sum;
        this.returnFloat4_subtraction = returnFloat4_subtraction;
        this.returnFloat4_multiply = returnFloat4_multiply;
        this.returnFloat4_divide = returnFloat4_divide;
        this.float8_1 = float8_1;
        this.float8_2 = float8_2;
        this.float8_3 = float8_3;
        this.returnFloat8_sum = returnFloat8_sum;
        this.returnFloat8_subtraction = returnFloat8_subtraction;
        this.returnFloat8_multiply = returnFloat8_multiply;
        this.returnFloat8_divide = returnFloat8_divide;
    }

    @Test
    public final void testAddDatumInt4() {

        int4_3 = int4_1.plus(int4_2);
        assertEquals(int4_3.type(), Type.Int4);
        assertEquals(int4_3.asInt4(), returnInt4_sum);
        int4_3 = int4_2.plus(int4_1);
        assertEquals(int4_3.type(), Type.Int4);
        assertEquals(int4_3.asInt4(), returnInt4_sum);
    }

    @Test
    public final void testAddDatumInt8() {
        int8_3 = int8_1.plus(int8_2);
        assertEquals(int8_3.type(), Type.Int8);
        assertEquals(int8_3.asInt8(), returnInt8_sum);
        int8_3 = int8_2.plus(int8_1);
        assertEquals(int8_3.type(), Type.Int8);
        assertEquals(int8_3.asInt8(), returnInt8_sum);
    }

    @Test
    public final void testAddDatumFloat4() {

        float4_3 = float4_1.plus(float4_2);
        assertEquals(float4_3.type(), Type.Float4);
        assertTrue(float4_3.asFloat4() == (float) returnFloat4_sum);
        float4_3 = float4_2.plus(float4_1);
        assertEquals(float4_3.type(), Type.Float4);
        assertTrue(float4_3.asFloat4() == (float) returnFloat4_sum);

    }

    @Test
    public final void testAddDatumFloat8() {

        float8_3 = float8_1.plus(float8_2);
        assertEquals(float8_3.type(), Type.Float8);
        assertTrue(float8_3.asFloat8() == (Double) returnFloat8_sum);
        float8_3 = float8_2.plus(float8_1);
        assertEquals(float8_3.type(), Type.Float8);
        assertTrue(float8_3.asFloat8() == (Double) returnFloat8_sum);
    }

    @Test
    public final void testMinusDatumInt4() {
        int4_3 = int4_1.minus(int4_2);
        assertEquals(int4_3.type(), Type.Int4);
        assertEquals(int4_3.asInt4(), returnInt4_subtraction);
        int4_3 = int4_2.minus(int4_1);
        assertEquals(int4_3.type(), Type.Int4);
        assertEquals(int4_3.asInt4(),  (-1)*(Integer) returnInt4_subtraction);
    }

    @Test
    public final void testMinusDatumInt8() {
        int8_3 = int8_1.minus(int8_2);
        assertEquals(int8_3.type(), Type.Int8);
        assertEquals(int8_3.asInt8(),  returnInt8_subtraction);
        int8_3 = int8_2.minus(int8_1);
        assertEquals(int8_3.type(), Type.Int8);
        assertEquals(int8_3.asInt8(), (-1)*(Long) returnInt8_subtraction);
    }

    @Test
    public final void testMinusDatumFloat4() {

        float4_3 = float4_1.minus(float4_2);
        assertEquals(float4_3.type(), Type.Float4);
        assertTrue(float4_3.asFloat4() == (Float) returnFloat4_subtraction);
        float4_3 = float4_2.minus(float4_1);
        assertEquals(float4_3.type(), Type.Float4);
        assertTrue(float4_3.asFloat4() == (-1)*(Float) returnFloat4_subtraction);
    }

    @Test
    public final void testMinusDatumFloat8() {

        float8_3 = float8_1.minus(float8_2);
        assertEquals(float8_3.type(), Type.Float8);
        assertTrue(float8_3.asFloat8() == (Double) returnFloat8_subtraction);
        float8_3 = float8_2.minus(float8_1);
        assertEquals(float8_3.type(), Type.Float8);
        assertTrue(float8_3.asFloat8() == (-1)*(Double) returnFloat8_subtraction);
    }

    @Test
    public final void testMultiDatumInt4(){
        int4_3 = int4_1.multiply(int4_2);
        assertEquals(int4_3.type(), Type.Int4);
        assertEquals(int4_3.asInt4(), returnInt4_multiply);
        int4_3 = int4_2.multiply(int4_1);
        assertEquals(int4_3.type(), Type.Int4);
        assertEquals(int4_3.asInt4(), returnInt4_multiply);
    }

    @Test
    public final void testMultiDatumInt8(){
        int8_3 = int8_1.multiply(int8_2);
        assertEquals(int8_3.type(), Type.Int8);
        assertEquals(int8_3.asInt8(), returnInt8_multiply);
        int8_3 = int8_2.multiply(int8_1);
        assertEquals(int8_3.type(), Type.Int8);
        assertEquals(int8_3.asInt8(), returnInt8_multiply);
    }

    @Test
    public final void testMultiDatumFloat4(){
        float4_3 = float4_1.multiply(float4_2);
        assertEquals(float4_3.type(), Type.Float4);
        assertTrue(float4_3.asFloat4() == (Float) returnFloat4_multiply);
        float4_3 = float4_2.multiply(float4_1);
        assertEquals(float4_3.type(), Type.Float4);
        assertTrue(float4_3.asFloat4() == (Float) returnFloat4_multiply);
    }

    @Test
    public final void testMultiDatumFloat8(){
        float8_3 = float8_1.multiply(float8_2);
        assertEquals(float8_3.type(), Type.Float8);
        assertTrue(float8_3.asFloat8() == (Double) returnFloat8_multiply);
        float8_3 = float8_2.multiply(float8_1);
        assertEquals(float8_3.type(), Type.Float8);
        assertTrue(float8_3.asFloat8() == (Double) returnFloat8_multiply);
    }

    @Test
    public final void testDivideDatumInt4(){
        int4_3 = int4_1.divide(int4_2);
        assertEquals(int4_3.type(), Type.Int4);
        assertEquals(int4_3.asInt4(), returnInt4_divide);
    }

    @Test
    public final void testDivideDatumInt8(){
        int8_3 = int8_1.multiply(int8_2);
        assertEquals(int8_3.type(), Type.Int8);
        assertEquals(int8_3.asInt8(), returnInt8_divide);
    }

    @Test
    public final void testDivideDatumFloat4(){
        float4_3 = float4_1.divide(float4_2);
        assertEquals(float4_3.type(), Type.Float4);
        assertTrue(float4_3.asFloat4() == (Float) returnFloat4_divide);

    }

    @Test
    public final void testDivideDatumFloat8(){
        float8_3 = float8_1.divide(float8_2);
        assertEquals(float8_3.type(), Type.Float8);
        assertTrue(float8_3.asFloat8() == (Double) returnFloat8_divide);
    }

    @Test
    public final void testEqual() {
        Datum dat1;
        Datum dat2;
        Datum dat3;

        dat1 = DatumFactory.createFloat4(3.123456789f);
        dat2 = DatumFactory.createFloat4(3.123456789f);
        dat3 = dat1.equalsTo(dat2);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);
        dat3 = dat2.equalsTo(dat1);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);

        dat1 = DatumFactory.createInt4(123456789);
        dat2 = DatumFactory.createInt4(123456789);
        dat3 = dat1.equalsTo(dat2);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);
        dat3 = dat2.equalsTo(dat1);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);

    }

    @Test
    public final void testLessThan() {
        Datum dat1;
        Datum dat2;
        Datum dat3;

        dat1 = DatumFactory.createInt4(20);
        dat2 = DatumFactory.createInt4(10);
        dat3 = dat1.lessThan(dat2);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), false);
        dat3 = dat2.lessThan(dat1);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);

        dat1 = DatumFactory.createInt4(5);
        dat2 = DatumFactory.createInt4(2);
        dat3 = dat1.lessThan(dat2);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), false);
        dat3 = dat2.lessThan(dat1);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);
    }

    @Test
    public final void testLessThanEquals() {
        Datum dat1;
        Datum dat2;
        Datum dat3;

        dat1 = DatumFactory.createInt4(20);
        dat2 = DatumFactory.createInt4(10);
        dat3 = dat1.lessThan(dat2);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), false);
        dat3 = dat2.lessThan(dat1);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);

        dat1 = DatumFactory.createInt4(5);
        dat2 = DatumFactory.createInt4(5);
        dat3 = dat1.lessThanEqual(dat2);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);
        dat3 = dat2.lessThanEqual(dat1);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);
    }

    @Test
    public final void testGreaterThan() {
        Datum dat1;
        Datum dat2;
        Datum dat3;

        dat1 = DatumFactory.createInt4(6);
        dat2 = DatumFactory.createInt4(3);
        dat3 = dat1.greaterThan(dat2);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);
        dat3 = dat2.greaterThan(dat1);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), false);

        dat1 = DatumFactory.createInt4(15);
        dat2 = DatumFactory.createInt4(15);
        dat3 = dat1.greaterThan(dat2);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), false);
        dat3 = dat2.greaterThan(dat1);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), false);
    }

    @Test
    public final void testGreaterThanEquals() {
        Datum dat1;
        Datum dat2;
        Datum dat3;

        dat1 = DatumFactory.createInt4(6);
        dat2 = DatumFactory.createInt4(3);
        dat3 = dat1.greaterThanEqual(dat2);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);
        dat3 = dat2.greaterThanEqual(dat1);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), false);

        dat1 = DatumFactory.createInt4(6);
        dat2 = DatumFactory.createInt4(6);
        dat3 = dat1.greaterThanEqual(dat2);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);
        dat3 = dat2.greaterThanEqual(dat1);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);
    }
}
