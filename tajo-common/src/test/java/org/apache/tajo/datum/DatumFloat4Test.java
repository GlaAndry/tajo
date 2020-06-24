package org.apache.tajo.datum;

import org.apache.tajo.exception.InvalidOperationException;
import org.apache.tajo.exception.InvalidValueForCastException;
import org.apache.tajo.exception.TajoRuntimeException;
import org.apache.tajo.type.Type;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DatumFloat4Test {


    Datum float4_1;
    Datum float4_2;
    Datum resultDat;

    float floatRes;

    Object resultException;
    Object resultEqual;
    Object resultCompare;
    Object resultPlus;
    Object resultMinus;
    Object resultMultiply;
    Object resultDivide;
    Object resultModular;


    public DatumFloat4Test(Datum float4_1, Datum float4_2,
                           Object resultException, Object resultEqual, Object resultCompare, Object resultPlus,
                           Object resultMinus, Object resultMultiply, Object resultDivide, Object resultModular) {
        this.float4_1 = float4_1;
        this.float4_2 = float4_2;
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
    public static Collection Float4DatumParameters() throws Exception {
        return Arrays.asList(new Object[][]{
                //Datum1, Datum2, Exception, ResEqual, ResCompare, ResPlus, ResMinus, ResMultiply, ResDivide, ResModular

                //Normal con due istanze Datum della stessa tipologia.
                {DatumFactory.createFloat4(2), DatumFactory.createFloat4(2), null, true, 0f, DatumFactory.createFloat4(4), DatumFactory.createFloat4(0), DatumFactory.createFloat4(4), DatumFactory.createFloat4(1), DatumFactory.createFloat4(0)},
                {DatumFactory.createFloat4(2), DatumFactory.createFloat4(1), null, false, 1f, DatumFactory.createFloat4(3), DatumFactory.createFloat4(1), DatumFactory.createFloat4(2), DatumFactory.createFloat4(2), DatumFactory.createFloat4(0)},
                {DatumFactory.createFloat4(1), DatumFactory.createFloat4(2), null, false, -1f, DatumFactory.createFloat4(3), DatumFactory.createFloat4(-1), DatumFactory.createFloat4(2), DatumFactory.createFloat4(0.5f), DatumFactory.createFloat4(1)},

                //Test con due Istanze Datum di tipologie diverse tra loro.
                {DatumFactory.createInt4(1), DatumFactory.createFloat4(2), null, false, -1f, DatumFactory.createFloat4(3), DatumFactory.createFloat4(-1), DatumFactory.createFloat4(2), DatumFactory.createFloat4(0.5f), DatumFactory.createFloat4(1)},

                //null Test.
                {null, null, NullPointerException.class, null, null, null, null, null, null, null},

        });

    }

    @Test
    public void equalsTest() {
        Datum datum1;
        Datum datum2;

        try {
            resultDat = float4_1.equalsTo(float4_2);
            Assert.assertEquals(resultEqual, resultDat.asBool());
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt2((short)1);
        Assert.assertEquals(true, datum1.equalsTo(datum2).asBool());

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt8((long) 1);
        Assert.assertEquals(true, datum1.equalsTo(datum2).asBool());

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt4(1);
        Assert.assertEquals(true, datum1.equalsTo(datum2).asBool());

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(true, datum1.equalsTo(datum2).asBool());

        try{
            datum1 = DatumFactory.createFloat4(1);
            datum2 = DatumFactory.createNullDatum();
            datum1.equalsTo(datum2);
        } catch (Exception e){
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }

        //not in switch-case
        try{
            datum1 = DatumFactory.createFloat4(1);
            datum2 = DatumFactory.createText("stringTest");
            datum1.equalsTo(datum2);
        } catch (Exception e){
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void compareToTest() {
        Datum datum1;
        Datum datum2;
        try {
            floatRes = float4_1.compareTo(float4_2);
            Assert.assertEquals(resultCompare, floatRes);

        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage && mutation
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt2((short)1);
        Assert.assertEquals(0, datum1.compareTo(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt2((short)2);
        Assert.assertEquals(-1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createInt2((short)1);
        Assert.assertEquals(1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt8((long)1);
        Assert.assertEquals(0, datum1.compareTo(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt8((long)2);
        Assert.assertEquals(-1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createInt8((long)1);
        Assert.assertEquals(1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createInt4(1);
        Assert.assertEquals(1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt4(1);
        Assert.assertEquals(0, datum1.compareTo(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt4(2);
        Assert.assertEquals(-1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(0, datum1.compareTo(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createFloat8(2);
        Assert.assertEquals(-1, datum1.compareTo(datum2));

        //NullType
        datum1 = DatumFactory.createNullDatum();
        datum2 = DatumFactory.createNullDatum();
        Assert.assertEquals(0, datum1.compareTo(datum2));

        //not in switch-case
        try{
            datum1 = DatumFactory.createFloat4(1);
            datum2 = DatumFactory.createText("stringTest");
            datum1.compareTo(datum2);
        } catch (Exception e){
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void plusTest(){
        Datum datum1;
        Datum datum2;
        DateDatum dateDatum;
        try {
            resultDat = float4_1.plus(float4_2);
            Assert.assertEquals(resultPlus, resultDat);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage && mutation on method plus():
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt2((short)1);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.plus(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt4( 1);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.plus(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt8((long) 1);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.plus(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(DatumFactory.createFloat8(2), datum1.plus(datum2));

        datum1 = DatumFactory.createFloat4(0);
        datum2 = DatumFactory.createDate(DatumFactory.createInt4(1));
        dateDatum = new DateDatum(1);
        Assert.assertEquals(dateDatum, datum1.plus(datum2));

        //not in switch-case
        try{
            datum1 = DatumFactory.createFloat4(1);
            datum2 = DatumFactory.createText("stringTest");
            datum1.plus(datum2);
        } catch (Exception e){
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void minusTest(){
        Datum datum1;
        Datum datum2;
        DateDatum dateDatum;
        try {
            resultDat = float4_1.minus(float4_2);
            Assert.assertEquals(resultMinus, resultDat);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage && mutation
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt2((short)1);
        Assert.assertEquals(DatumFactory.createFloat4(0), datum1.minus(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt4( 1);
        Assert.assertEquals(DatumFactory.createFloat4(0), datum1.minus(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt8((long) 1);
        Assert.assertEquals(DatumFactory.createFloat4(0), datum1.minus(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(DatumFactory.createFloat8(0), datum1.minus(datum2));

        datum1 = DatumFactory.createFloat4(0);
        datum2 = DatumFactory.createDate(DatumFactory.createInt4(1));
        dateDatum = new DateDatum(1);
        Assert.assertEquals(dateDatum, datum1.minus(datum2));

        //not in switch-case
        try{
            datum1 = DatumFactory.createFloat4(1);
            datum2 = DatumFactory.createText("stringTest");
            datum1.minus(datum2);
        } catch (Exception e){
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void multiplyTest(){
        Datum datum1;
        Datum datum2;
        try {
            resultDat = float4_1.multiply(float4_2);
            Assert.assertEquals(resultMultiply, resultDat);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage && mutation

        //mutation case int2
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt2((short)2);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createInt2((short)1);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createInt2((short)0);
        Assert.assertEquals(DatumFactory.createFloat4(0), datum1.multiply(datum2));
        //////

        //mutation case int4
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt4(2);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createInt4(1);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createInt4(0);
        Assert.assertEquals(DatumFactory.createFloat4(0), datum1.multiply(datum2));
        //////

        //mutation case int8
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt8(2);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createInt8(1);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createInt8(0);
        Assert.assertEquals(DatumFactory.createFloat4(0), datum1.multiply(datum2));
        /////

        //mutation case float8
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createFloat8(2);
        Assert.assertEquals(DatumFactory.createFloat8(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(DatumFactory.createFloat8(2), datum1.multiply(datum2));

        datum1 = DatumFactory.createFloat4(2);
        datum2 = DatumFactory.createFloat8(0);
        Assert.assertEquals(DatumFactory.createFloat8(0), datum1.multiply(datum2));
        ////

        try{
            datum1 = DatumFactory.createFloat4(2);
            datum2 = DatumFactory.createInterval("01:00:00");
            Assert.assertEquals(DatumFactory.createInterval("02:00:00"), datum1.multiply(datum2));

            datum1 = DatumFactory.createFloat4(1);
            datum2 = DatumFactory.createInterval("01:00:00");
            Assert.assertEquals(DatumFactory.createInterval("01:00:00"), datum1.multiply(datum2));

            datum1 = DatumFactory.createFloat4(0);
            datum2 = DatumFactory.createInterval("01:00:00");
            Assert.assertEquals(DatumFactory.createInterval("00:00:00"), datum1.multiply(datum2));

        } catch (Exception e){
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }

        //not in switch-case
        try{
            datum1 = DatumFactory.createFloat4(1);
            datum2 = DatumFactory.createDate(DatumFactory.createInt4(1));
            datum1.multiply(datum2);
        } catch (Exception e){
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }

    }

    @Test
    public void divideTest(){
        Datum datum1;
        Datum datum2;
        try {
            resultDat = float4_1.divide(float4_2);
            Assert.assertEquals(resultDivide, resultDat);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage && mutation

        //case int2
        datum1 = DatumFactory.createFloat4(10);
        datum2 = DatumFactory.createInt2((short)5);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.divide(datum2));

        datum1 = DatumFactory.createFloat4(5);
        datum2 = DatumFactory.createInt2((short)10);
        Assert.assertEquals(DatumFactory.createFloat4(0.5f), datum1.divide(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt2((short)0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.divide(datum2));
        ////////

        //case int4:
        datum1 = DatumFactory.createFloat4(10);
        datum2 = DatumFactory.createInt4(5);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.divide(datum2));

        datum1 = DatumFactory.createFloat4(5);
        datum2 = DatumFactory.createInt4(10);
        Assert.assertEquals(DatumFactory.createFloat4(0.5f), datum1.divide(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt4(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.divide(datum2));
        ////////

        //case int8
        datum1 = DatumFactory.createFloat4(10);
        datum2 = DatumFactory.createInt8(5);
        Assert.assertEquals(DatumFactory.createFloat4(2), datum1.divide(datum2));

        datum1 = DatumFactory.createFloat4(5);
        datum2 = DatumFactory.createInt8(10);
        Assert.assertEquals(DatumFactory.createFloat4(0.5f), datum1.divide(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt8(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.divide(datum2));
        ///

        //case float 4 --> mutation on line 300
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createFloat4(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.divide(datum2));
        /////

        //case float8
        datum1 = DatumFactory.createFloat4(10);
        datum2 = DatumFactory.createFloat8(5);
        Assert.assertEquals(DatumFactory.createFloat8(2), datum1.divide(datum2));

        datum1 = DatumFactory.createFloat4(5);
        datum2 = DatumFactory.createFloat8(10);
        Assert.assertEquals(DatumFactory.createFloat8(0.5), datum1.divide(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createFloat8(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.divide(datum2));
        //

        //not in switch-case
        try{
            datum1 = DatumFactory.createFloat4(1);
            datum2 = DatumFactory.createDate(DatumFactory.createInt4(1));
            datum1.divide(datum2);
        } catch (Exception e){
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void modularTest(){
        Datum datum1;
        Datum datum2;

        try {
            resultDat = float4_1.modular(float4_2);
            Assert.assertEquals(resultModular, resultDat);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), resultException);
        }

        //for coverage && mutation

        //int2
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt2((short)1);
        Assert.assertEquals(DatumFactory.createFloat4(0), datum1.modular(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt2((short)0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.modular(datum2));

        //int4
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt4(1);
        Assert.assertEquals(DatumFactory.createFloat4(0), datum1.modular(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt4(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.modular(datum2));

        //int8
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt8((long) 1);
        Assert.assertEquals(DatumFactory.createFloat4(0), datum1.modular(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createInt8((long) 0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.modular(datum2));

        //float4 --> mutation on line 340
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createFloat4(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.modular(datum2));

        //float8
        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createFloat8(1);
        Assert.assertEquals(DatumFactory.createFloat8(0), datum1.modular(datum2));

        datum1 = DatumFactory.createFloat4(1);
        datum2 = DatumFactory.createFloat8(0);
        Assert.assertEquals(DatumFactory.createNullDatum(), datum1.modular(datum2));

        //not in switch-case
        try{
            datum1 = DatumFactory.createFloat4(0);
            datum2 = DatumFactory.createDate(DatumFactory.createInt4(1));
            datum1.modular(datum2);
        } catch (Exception e){
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    /**
     *
     * Coverage Test
     */

    @Test
    public void testInverse(){
        NumericDatum datum = DatumFactory.createFloat4(1);
        Datum res = DatumFactory.createFloat4(-1);
        Assert.assertEquals(res, datum.inverseSign());

        //For mutation on line 358
        NumericDatum datum1 = DatumFactory.createFloat4(0);
        Datum res1 = DatumFactory.createFloat4(0);
        Assert.assertEquals(res1, datum1.inverseSign());

        NumericDatum datum2 = DatumFactory.createFloat4(-1);
        Datum res2 = DatumFactory.createFloat4(1);
        Assert.assertEquals(res2, datum2.inverseSign());
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
    }

    @Test
    public void asTest(){

        Datum datum = DatumFactory.createFloat4(1);
        Assert.assertEquals(datum.size(), 4); //float Size
        Assert.assertEquals(datum.asInt2() , (short)1);
        Assert.assertEquals(datum.asInt4() , (int)1);
        Assert.assertEquals(datum.asInt8() , (long)1);
        Assert.assertTrue(datum.asFloat4() == (float) 1);
        Assert.assertTrue(datum.asFloat8() == (double) 1);
        Assert.assertEquals(datum.asChar() , 49); //1 equivale a 49 nella tabella ASCII
        try {
            Datum datum1 = DatumFactory.createFloat4(1);
            datum1.asByte();
        } catch (Exception e){
            Assert.assertEquals(e.getClass() , TajoRuntimeException.class);
        }

    }

}
