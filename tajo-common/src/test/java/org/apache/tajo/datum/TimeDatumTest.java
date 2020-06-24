package org.apache.tajo.datum;

import org.apache.tajo.exception.InvalidOperationException;
import org.apache.tajo.exception.TajoRuntimeException;
import org.apache.tajo.json.CommonGsonHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TimeDatumTest {


    Datum timeDatum1;
    Datum timeDatum2;
    Datum resultDat;

    int intRes;

    Object resultException;
    Object resultPlus;
    Object resultMinus;
    Object resultEqualsTo;
    Object resultCompare;
    Object resultEquals;

    public TimeDatumTest(Datum timeDatum1, Datum timeDatum2, int intRes,
                         Object resultException, Object resultPlus, Object resultMinus,
                         Object resultEqualsTo, Object resultCompare, Object resultEquals) {
        this.timeDatum1 = timeDatum1;
        this.timeDatum2 = timeDatum2;
        this.intRes = intRes;
        this.resultException = resultException;
        this.resultPlus = resultPlus;
        this.resultMinus = resultMinus;
        this.resultEqualsTo = resultEqualsTo;
        this.resultCompare = resultCompare;
        this.resultEquals = resultEquals;
    }

    @Parameterized.Parameters
    public static Collection TimeDatumParameters() throws Exception {
        return Arrays.asList(new Object[][]{

                //TimeDatum1, TimeDatum2, IntRes, ResEXC, ResPlus, ResMinus, ResEqTo, ResComp, ResEq.
                {DatumFactory.createTime("10:00:00"), DatumFactory.createInterval("01:00:00"), 0, InvalidOperationException.class, DatumFactory.createTime("11:00:00"), DatumFactory.createTime("09:00:00"), false, 1, false},
                {DatumFactory.createTime("01:00:00"), DatumFactory.createInterval("10:00:00"), 0, InvalidOperationException.class, DatumFactory.createTime("11:00:00"), DatumFactory.createTime("15:00:00"), false, -1, false},
                {DatumFactory.createTime("10:00:00"), DatumFactory.createInterval("10:00:00"), 0, InvalidOperationException.class, DatumFactory.createTime("20:00:00"), DatumFactory.createTime("00:00:00"), true, 0, false}, //in questo caso si ha false su equal perché il secondo datum è di tipo Interval
                {null, null, 0, NullPointerException.class, null, null, null, null, false}

        });
    }

    @Test
    public void plusTest() {
        Datum datum1;
        Datum datum2;

        try {
            resultDat = timeDatum1.plus(timeDatum2);
            Assert.assertEquals(resultPlus, resultDat);
        } catch (Exception e) {
            Assert.assertEquals(resultException, e.getClass());
        }

        //coverage
        datum1 = DatumFactory.createTime("10:00:00");
        datum2 = DatumFactory.createInterval("01:00:00");
        Assert.assertEquals(DatumFactory.createTime("11:00:00"), datum1.plus(datum2));

        //coverage
        datum1 = DatumFactory.createTime("10:00:00");
        datum2 = DatumFactory.createDate("2020-01-01");
        Assert.assertEquals(DatumFactory.createTimestamp("2020-01-01 11:00:00+1"), datum1.plus(datum2));

        //coverage
        datum1 = DatumFactory.createTime("10:00:00");
        datum2 = DatumFactory.createTimestamp("01:00:00");
        Assert.assertEquals(DatumFactory.createTimestamp("11:00:00"), datum1.plus(datum2));


        //not in switch-case
        try {
            datum1 = DatumFactory.createTime("10:00:00");
            datum2 = DatumFactory.createInt4(1);
            datum1.plus(datum2);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void minusTest() {
        Datum datum1;
        Datum datum2;

        try {
            resultDat = timeDatum1.minus(timeDatum2);
            Assert.assertEquals(resultMinus, resultDat);
        } catch (Exception e) {
            Assert.assertEquals(resultException, e.getClass());
        }

        //coverage

        //interval
        datum1 = DatumFactory.createTime("10:00:00");
        datum2 = DatumFactory.createInterval("01:00:00");
        Assert.assertEquals(DatumFactory.createTime("09:00:00"), datum1.minus(datum2));

        datum1 = DatumFactory.createTime("01:00:00");
        datum2 = DatumFactory.createInterval("10:00:00");
        Assert.assertEquals(DatumFactory.createTime("15:00:00"), datum1.minus(datum2));

        //mutation with complete date&time
        datum1 = DatumFactory.createTime("2020-01-01 01:00:00");
        datum2 = DatumFactory.createInterval("10:00:00");
        Assert.assertEquals(DatumFactory.createTime("2019-12-31 15:00:00"), datum1.minus(datum2));

        //mutation on line 143
        datum1 = DatumFactory.createTime("2020-01-01 01:00:00");
        datum2 = DatumFactory.createInterval("00:00:00");
        Assert.assertEquals(DatumFactory.createTime("2020-01-01 01:00:00"), datum1.minus(datum2));

        datum1 = DatumFactory.createTime("2020-01-01 01:00:00");
        datum2 = DatumFactory.createInterval("25:00:00");
        Assert.assertEquals(DatumFactory.createTime("2019-12-31 00:00:00"), datum1.minus(datum2));

        datum1 = DatumFactory.createTime("2020-01-02 10:00:00");
        datum2 = DatumFactory.createTime("2020-01-02 01:00:00");
        Assert.assertEquals(DatumFactory.createInterval("09:00:00"), datum1.minus(datum2));

        datum1 = DatumFactory.createTime("2020-01-02");
        datum2 = DatumFactory.createTime("2020-01-02");
        Assert.assertEquals(DatumFactory.createInterval("00:00:00"), datum1.minus(datum2));

        //not in switch-case
        try {
            datum1 = DatumFactory.createTime("10:00:00");
            datum2 = DatumFactory.createInt4(1);
            datum1.minus(datum2);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void equalsToTest() {
        Datum datum1;
        Datum datum2;
        try {
            //DatumFactory.createTime("10:00:00")
            resultDat = timeDatum1.equalsTo(timeDatum2);
            Assert.assertEquals(resultEqualsTo, resultDat.asBool());
        } catch (Exception e) {
            Assert.assertEquals(resultException, e.getClass());
        }

        //coverage
        datum1 = DatumFactory.createTime("10:00:00");
        datum2 = DatumFactory.createTime("10:00:00");
        Assert.assertEquals(true, datum1.equalsTo(datum2).asBool());

        datum1 = DatumFactory.createTime("11:00:00");
        datum2 = DatumFactory.createTime("10:00:00");
        Assert.assertEquals(false, datum1.equalsTo(datum2).asBool());

        datum1 = DatumFactory.createTime("09:00:00");
        datum2 = DatumFactory.createTime("10:00:00");
        Assert.assertEquals(false, datum1.equalsTo(datum2).asBool());


        //coverage && mutation on line 169
        datum1 = DatumFactory.createTime("10:00:00");
        datum2 = DatumFactory.createNullDatum();
        Assert.assertEquals(datum2, datum1.equalsTo(datum2));


        //not in switch-case
        try {
            datum1 = DatumFactory.createTime("10:00:00");
            datum2 = DatumFactory.createInt4(1);
            datum1.equalsTo(datum2);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }

    }

    @Test
    public void compareToTest() {

        Datum datum1;
        Datum datum2;
        try {
            //DatumFactory.createTime("10:00:00")
            intRes = timeDatum1.compareTo(timeDatum2);
            Assert.assertEquals(resultCompare, intRes);

        } catch (Exception e) {
            Assert.assertEquals(resultException, e.getClass());
        }

        //coverage
        datum1 = DatumFactory.createTime("10:00:00");
        datum2 = DatumFactory.createTime("10:00:00");
        Assert.assertEquals(0, datum1.compareTo(datum2));

        datum1 = DatumFactory.createTime("11:00:00");
        datum2 = DatumFactory.createTime("10:00:00");
        Assert.assertEquals(1, datum1.compareTo(datum2));

        datum1 = DatumFactory.createTime("09:00:00");
        datum2 = DatumFactory.createTime("10:00:00");
        Assert.assertEquals(-1, datum1.compareTo(datum2));

        //coverage
        datum1 = DatumFactory.createTime("10:00:00");
        datum2 = DatumFactory.createNullDatum();
        Assert.assertEquals(-1, datum1.compareTo(datum2));

        //not in switch-case
        try {
            datum1 = DatumFactory.createTime("10:00:00");
            datum2 = DatumFactory.createInt4(1);
            datum1.compareTo(datum2);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), InvalidOperationException.class);
        }
    }

    @Test
    public void equalsTest() {

        Datum datum1;
        Datum datum2;
        try {
            boolean isEqual = timeDatum1.equals(timeDatum2);
            Assert.assertEquals(resultEquals, isEqual);

        } catch (Exception e) {
            Assert.assertEquals(resultException, e.getClass());
        }

        //coverage
        datum1 = DatumFactory.createTime("10:00:00");
        datum2 = DatumFactory.createTime("10:00:00");
        Assert.assertEquals(true, datum1.equals(datum2));

        //coverage
        datum1 = DatumFactory.createTime("10:00:00");
        datum2 = DatumFactory.createTime("11:00:00");
        Assert.assertEquals(false, datum1.equals(datum2));
    }

    /**
     * Coverage
     */

    @Test
    public void asTest() {

        String time = "10:00:00";
        Datum datum1 = DatumFactory.createTime(time);
        Datum datumN;
        try {
            Assert.assertEquals("10:00:00", datum1.asChars());
            datumN = DatumFactory.createTime(datum1.asInt8());
            Assert.assertEquals(datumN, datum1);

            Assert.assertEquals("10:00:00", datum1.asChars());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertEquals(e.getClass(), TajoRuntimeException.class);
        }


    }

    @Test
    public void sizeTest() {
        String time = "10:00:00";
        Datum datum1 = DatumFactory.createTime(time);
        Assert.assertEquals(8, datum1.size());
    }

    @Test(expected = TajoRuntimeException.class)
    public final void testAsInt4() {
        String time = "10:00:00";
        Datum d = DatumFactory.createTime(time);
        Datum copy = DatumFactory.createTime(d.asInt4());
        Assert.assertEquals(d, copy);
    }

    @Test(expected = TajoRuntimeException.class)
    public final void testAsFloat4() {
        String time = "10:00:00";
        Datum d = DatumFactory.createTime(time);
        d.asFloat4();
    }

    @Test(expected = TajoRuntimeException.class)
    public final void testAsFloat8() {
        String time = "10:00:00";
        Datum d = DatumFactory.createTime(time);
        d.asFloat8();
    }

    @Test
    public void getTest() {
        //timeformat with hour:minute:seconds.milliseconds
        String time = "10:22:57.128";
        TimeDatum d = DatumFactory.createTime(time);
        Assert.assertEquals(10, d.getHourOfDay());
        Assert.assertEquals(22, d.getMinuteOfHour());
        Assert.assertEquals(57, d.getSecondOfMinute());
        Assert.assertEquals(128, d.getMillisOfSecond());
    }

    @Test
    public void toStringTest() {
        String time = "10:00:00";
        Datum d = DatumFactory.createTime(time);
        Assert.assertEquals("10:00:00", d.toString());
    }

    @Test
    public final void testToJson() {
        String time = "10:00:00";
        Datum d = DatumFactory.createTime(time);
        Datum d1 = CommonGsonHelper.fromJson(d.toJson(), Datum.class);
        Assert.assertEquals(d, d1);
    }


}
