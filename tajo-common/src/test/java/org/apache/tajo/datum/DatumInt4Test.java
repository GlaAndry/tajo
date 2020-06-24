package org.apache.tajo.datum;


import org.apache.tajo.common.TajoDataTypes;
import org.apache.tajo.exception.InvalidOperationException;
import org.apache.tajo.type.Int4;
import org.apache.tajo.type.Null;
import org.apache.tajo.type.Type;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DatumInt4Test {

    Datum int4_1;
    Datum int4_2;
    Datum resultDat;

    int intRes;

    Object resultException;
    Object resultEqual;
    Object resultCompare;
    Object resultPlus;
    Object resultMinus;
    Object resultMultiply;
    Object resultDivide;
    Object resultModular;


    public DatumInt4Test(Datum int4_1, Datum int4_2, Object resultException, Object resultEqual,
                         Object resultCompare, Object resultPlus, Object resultMinus, Object resultMultiply,
                         Object resultDivide, Object resultModular) {
        this.int4_1 = int4_1;
        this.int4_2 = int4_2;
        this.resultException = resultException;
        this.resultEqual = resultEqual;
        this.resultCompare = resultCompare;
        this.resultPlus = resultPlus;
        this.resultMinus = resultMinus;
        this.resultMultiply = resultMultiply;
        this.resultDivide = resultDivide;
        this.resultModular = resultModular;
    }

    @Parameterized.Parameters
    public static Collection Int4DatumParameters() throws Exception {
        return Arrays.asList(new Object[][]{
                //Datum1, Datum2, Exception, ResEqual, ResCompare, ResPlus, ResMinus, ResMultiply, ResDivide, ResModular

                //Normal con due istanze Datum della stessa tipologia.
                {DatumFactory.createInt4(2), DatumFactory.createInt4(2), null, true, 0, DatumFactory.createInt4(4), DatumFactory.createInt4(0), DatumFactory.createInt4(4), DatumFactory.createInt4(1), DatumFactory.createInt4(0)},
                {DatumFactory.createInt4(2), DatumFactory.createInt4(1), null, false, 1, DatumFactory.createInt4(3), DatumFactory.createInt4(1), DatumFactory.createInt4(2), DatumFactory.createInt4(2), DatumFactory.createInt4(0)},
                {DatumFactory.createInt4(1), DatumFactory.createInt4(2), null, false, -1, DatumFactory.createInt4(3), DatumFactory.createInt4(-1), DatumFactory.createInt4(2), DatumFactory.createInt4(0), DatumFactory.createInt4(1)},

                //Test con due Istanze Datum di tipologie diverse tra loro.
                {DatumFactory.createInt4(1), DatumFactory.createFloat4(2), null, false, -1, DatumFactory.createFloat4(3), DatumFactory.createFloat4(-1), DatumFactory.createFloat4(2), DatumFactory.createFloat4(0.5f), DatumFactory.createFloat4(1)},

                //null Test.
                {null, null, NullPointerException.class, null, null, null, null, null, null, null},

        });

    }

    @Test
    public void equalsTest() {
        Datum datum1;
        Datum datum2;
        try {
            resultDat = int4_1.equalsTo(int4_2);
            Assert.assertEquals(resultEqual, resultDat.asBool());
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt2((short) 1);
        Assert.assertEquals(true, datum1.equalsTo(datum2).asBool());

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt8((long) 1);
        Assert.assertEquals(true, datum1.equalsTo(datum2).asBool());

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat4(1);
        Assert.assertEquals(true, datum1.equalsTo(datum2).asBool());

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(true, datum1.equalsTo(datum2).asBool());

        try {
            datum1 = DatumFactory.createInt4(1);
            datum2 = DatumFactory.createNullDatum();
            datum1.equalsTo(datum2);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }

        //not in switch-case
        try{
            datum1 = DatumFactory.createInt4(1);
            datum2 = DatumFactory.createText("stringTest");
            datum1.minus(datum2);
        } catch (Exception e){
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void compareToTest() {
        Datum datum1;
        Datum datum2;

        try {
            intRes = int4_1.compareTo(int4_2);
            Assert.assertEquals(resultCompare, intRes);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage:
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt2((short) 1);
        Assert.assertEquals(0, datum1.compareTo(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt2((short) 2);
        Assert.assertEquals(-1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createInt4(2);
        datum2 = DatumFactory.createInt2((short) 1);
        Assert.assertEquals(1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt8((long) 1);
        Assert.assertEquals(0, datum1.compareTo(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt8((long) 2);
        Assert.assertEquals(-1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createInt4(2);
        datum2 = DatumFactory.createInt8((long) 1);
        Assert.assertEquals(1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createInt4(2);
        datum2 = DatumFactory.createFloat4(1);
        Assert.assertEquals(1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat4(1);
        Assert.assertEquals(0, datum1.compareTo(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat4(2);
        Assert.assertEquals(-1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createInt4(2);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(0, datum1.compareTo(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat8(2);
        Assert.assertEquals(-1, datum1.compareTo(datum2));

        //NullType
        datum1 = DatumFactory.createNullDatum();
        datum2 = DatumFactory.createNullDatum();
        Assert.assertEquals(0, datum1.compareTo(datum2));

        //not in switch-case
        try{
            datum1 = DatumFactory.createInt4(1);
            datum2 = DatumFactory.createText("stringTest");
            datum1.minus(datum2);
        } catch (Exception e){
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void plusTest() {
        Datum datum1;
        Datum datum2;
        DateDatum dateDatum;
        try {
            resultDat = int4_1.plus(int4_2);
            Assert.assertEquals(resultPlus, resultDat);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage && mutation
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt2((short) 1);
        Assert.assertEquals(DatumFactory.createInt4(2), datum1.plus(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt8((long) 1);
        Assert.assertEquals(DatumFactory.createInt8((long) 2), datum1.plus(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(DatumFactory.createFloat8(2), datum1.plus(datum2));

        datum1 = DatumFactory.createInt4(0);
        datum2 = DatumFactory.createDate(DatumFactory.createInt4(1));
        dateDatum = new DateDatum(1);
        Assert.assertEquals(dateDatum, datum1.plus(datum2));


        //not in switch-case
        try {
            datum1 = DatumFactory.createInt4(1);
            datum2 = DatumFactory.createText("stringTest");
            datum1.plus(datum2);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void minusTest() {
        Datum datum1;
        Datum datum2;
        DateDatum dateDatum;
        try {
            resultDat = int4_1.minus(int4_2);
            Assert.assertEquals(resultMinus, resultDat);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage && mutation
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt2((short) 1);
        Assert.assertEquals(DatumFactory.createInt4(0), datum1.minus(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt8((long) 1);
        Assert.assertEquals(DatumFactory.createInt8((long) 0), datum1.minus(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(DatumFactory.createFloat8(0), datum1.minus(datum2));

        datum1 = DatumFactory.createInt4(0);
        datum2 = DatumFactory.createDate(DatumFactory.createInt4(1));
        dateDatum = new DateDatum(1);
        Assert.assertEquals(dateDatum, datum1.minus(datum2));

        //not in switch-case
        try {
            datum1 = DatumFactory.createInt4(1);
            datum2 = DatumFactory.createText("stringTest");
            datum1.minus(datum2);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void multiplyTest() {
        Datum datum1;
        Datum datum2;
        try {
            resultDat = int4_1.multiply(int4_2);
            Assert.assertEquals(resultMultiply, resultDat);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage && mutation

        //int2
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt2((short) 2);
        Assert.assertEquals(DatumFactory.createInt4(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createInt4(2);
        datum2 = DatumFactory.createInt2((short) 1);
        Assert.assertEquals(DatumFactory.createInt4(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createInt4(2);
        datum2 = DatumFactory.createInt2((short) 0);
        Assert.assertEquals(DatumFactory.createInt4(0), datum1.multiply(datum2));

        //int4
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt4(2);
        Assert.assertEquals(DatumFactory.createInt4(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createInt4(2);
        datum2 = DatumFactory.createInt4(1);
        Assert.assertEquals(DatumFactory.createInt4(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createInt4(2);
        datum2 = DatumFactory.createInt4(0);
        Assert.assertEquals(DatumFactory.createInt4(0), datum1.multiply(datum2));

        //int8
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt8((long) 2);
        Assert.assertEquals(DatumFactory.createInt8((long) 2), datum1.multiply(datum2));

        datum1 = DatumFactory.createInt4(2);
        datum2 = DatumFactory.createInt8((long) 1);
        Assert.assertEquals(DatumFactory.createInt8((long) 2), datum1.multiply(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt8((long) 0);
        Assert.assertEquals(DatumFactory.createInt8((long) 0), datum1.multiply(datum2));

        //float8
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat8(2);
        Assert.assertEquals(DatumFactory.createFloat8(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createInt4(2);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(DatumFactory.createFloat8(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat8(0);
        Assert.assertEquals(DatumFactory.createFloat8(0), datum1.multiply(datum2));

        //interval

        datum1 = DatumFactory.createInt4(2);
        datum2 = DatumFactory.createInterval("01:00:00");
        Assert.assertEquals(DatumFactory.createInterval("02:00:00"), datum1.multiply(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInterval("01:00:00");
        Assert.assertEquals(DatumFactory.createInterval("01:00:00"), datum1.multiply(datum2));

        datum1 = DatumFactory.createInt4(0);
        datum2 = DatumFactory.createInterval("01:00:00");
        Assert.assertEquals(DatumFactory.createInterval("00:00:00"), datum1.multiply(datum2));

        //not in switch-case
        try {
            datum1 = DatumFactory.createInt4(0);
            datum2 = DatumFactory.createDate(DatumFactory.createInt4(1));
            datum1.multiply(datum2);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }

    }

    @Test
    public void divideTest() {
        Datum datum1;
        Datum datum2;
        try {
            resultDat = int4_1.divide(int4_2);
            Assert.assertEquals(resultDivide, resultDat);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage && mutation

        //int2
        datum1 = DatumFactory.createInt4(10);
        datum2 = DatumFactory.createInt2((short) 5);
        Assert.assertEquals(DatumFactory.createInt4(2), datum1.divide(datum2));

        datum1 = DatumFactory.createInt4(5);
        datum2 = DatumFactory.createInt2((short) 10);
        Assert.assertEquals(DatumFactory.createInt4(0), datum1.divide(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt2((short) 0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.divide(datum2));

        //int4 --> mutation on line 281
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt4(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.divide(datum2));

        //int8
        datum1 = DatumFactory.createInt4(10);
        datum2 = DatumFactory.createInt8((long) 5);
        Assert.assertEquals(DatumFactory.createInt8((long) 2), datum1.divide(datum2));

        datum1 = DatumFactory.createInt4(5);
        datum2 = DatumFactory.createInt8((long) 10);
        Assert.assertEquals(DatumFactory.createInt8((long) 0), datum1.divide(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt8((long) 0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.divide(datum2));

        //float4
        datum1 = DatumFactory.createInt4(10);
        datum2 = DatumFactory.createFloat4(5);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.divide(datum2));

        datum1 = DatumFactory.createInt4(5);
        datum2 = DatumFactory.createFloat4(10);
        Assert.assertEquals(DatumFactory.createFloat4(0.5f), datum1.divide(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat4(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.divide(datum2));


        //float8
        datum1 = DatumFactory.createInt4(10);
        datum2 = DatumFactory.createFloat8(5);
        Assert.assertEquals(DatumFactory.createFloat8(2), datum1.divide(datum2));

        datum1 = DatumFactory.createInt4(5);
        datum2 = DatumFactory.createFloat8(10);
        Assert.assertEquals(DatumFactory.createFloat8(0.5), datum1.divide(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat8(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.divide(datum2));

        //not in switch-case
        try {
            datum1 = DatumFactory.createInt4(0);
            datum2 = DatumFactory.createDate(DatumFactory.createInt4(1));
            datum1.divide(datum2);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void modularTest() {
        Datum datum1;
        Datum datum2;
        try {
            resultDat = int4_1.modular(int4_2);
            Assert.assertEquals(resultModular, resultDat);

        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage && mutation

        //int2
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt2((short) 1);
        Assert.assertEquals(DatumFactory.createInt4(0), datum1.modular(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt2((short) 0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.modular(datum2));

        //int4
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt4(1);
        Assert.assertEquals(DatumFactory.createInt4(0), datum1.modular(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt4(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.modular(datum2));

        //int8
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt8((long) 1);
        Assert.assertEquals(DatumFactory.createInt8((long) 0), datum1.modular(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createInt8((long) 0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.modular(datum2));

        //float4
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat4(1);
        Assert.assertEquals(DatumFactory.createFloat4(0), datum1.modular(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat4(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.modular(datum2));

        //float8
        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(DatumFactory.createFloat8(0), datum1.modular(datum2));

        datum1 = DatumFactory.createInt4(1);
        datum2 = DatumFactory.createFloat8(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.modular(datum2));

        //not in switch-case
        try {
            datum1 = DatumFactory.createInt4(0);
            datum2 = DatumFactory.createDate(DatumFactory.createInt4(1));
            datum1.modular(datum2);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }

    }

    /**
     * Coverage Test
     */

    @Test
    public void testInverse() {
        NumericDatum datum = DatumFactory.createInt4(1);
        Datum res = DatumFactory.createInt4(-1);
        Assert.assertEquals(res, datum.inverseSign());
    }

    @Test
    public final void testEqual() {
        Datum dat1;
        Datum dat2;
        Datum dat3;

        dat1 = DatumFactory.createInt4(121);
        dat2 = DatumFactory.createInt4(121);
        dat3 = dat1.equalsTo(dat2);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);
        dat3 = dat2.equalsTo(dat1);
        assertEquals(dat3.type(), Type.Bool);
        assertEquals(dat3.asBool(), true);
    }

    @Test
    public void asTest() {

        Datum datum = DatumFactory.createInt4(1);
        Assert.assertEquals(datum.size(), 4); //integer Size
        Assert.assertEquals(datum.asInt2(), (short) 1);
        Assert.assertEquals(datum.asInt4(), (int) 1);
        Assert.assertEquals(datum.asInt8(), (long) 1);
        Assert.assertEquals(datum.asByte(), (byte) 1);
        Assert.assertTrue(datum.asFloat4() == (float) 1);
        Assert.assertTrue(datum.asFloat8() == (double) 1);
        Assert.assertEquals(datum.asChar(), 49); //1 equivale a 49 nella tabella ASCII

    }

}
