package org.apache.tajo.datum;


import org.apache.tajo.exception.InvalidOperationException;
import org.apache.tajo.exception.InvalidValueForCastException;
import org.apache.tajo.exception.TajoRuntimeException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TextDatumTest {

    Datum text1;
    Datum text2;

    Object resultException;
    Object resultEqual;
    Object resultCompare;
    Object resultEqualsTo;


    public TextDatumTest(Datum text1, Datum text2,
                         Object resultException, Object resultEqual, Object resultCompare, Object resultEqualsTo) {
        this.text1 = text1;
        this.text2 = text2;
        this.resultException = resultException;
        this.resultEqual = resultEqual;
        this.resultCompare = resultCompare;
        this.resultEqualsTo = resultEqualsTo;
    }


    @Parameterized.Parameters
    public static Collection DatumTextParameters() throws Exception {
        return Arrays.asList(new Object[][]{
                //Datum1, Datum2, Exception, ResEqual, ResCompare, ResEqualTo

                /**
                 *  vedere : http://www.asciitable.com/ per riferimento ai valori
                 *  {65,66,67} = "ABC" --> HTML value della tabella.
                */

                {DatumFactory.createText(new byte[]{0, 0, 0}), DatumFactory.createText(new byte[]{0, 0, 0}), null, true, 0, true},
                {DatumFactory.createText(new byte[]{0, 1, 0}), DatumFactory.createText(new byte[]{0, 0, 0}), null, false, 1, false},
                {null, DatumFactory.createText(new byte[]{0, 0, 0}), NullPointerException.class, null, null, null},
                {null, null, NullPointerException.class, null, null, null}

        });
    }

    @Test
    public void compareToTest() {
        Datum datum1;
        Datum datum2;

        try {
            int res = text1.compareTo(text2);
            Assert.assertEquals(resultCompare, res);

            //for coverage
            datum1 = DatumFactory.createText(new byte[]{0, 0, 0});
            datum2 = DatumFactory.createChar(new byte[]{0, 0, 0});
            Assert.assertEquals(0, datum1.compareTo(datum2));

            datum1 = DatumFactory.createText(new byte[]{0, 0, 0});
            datum2 = DatumFactory.createBlob(new byte[]{0, 0, 0});
            Assert.assertEquals(0, datum1.compareTo(datum2));

            datum1 = DatumFactory.createText(new byte[]{0, 0, 0});
            datum2 = DatumFactory.createNullDatum();
            Assert.assertEquals(-1, datum1.compareTo(datum2));

            try {
                datum1 = DatumFactory.createText(new byte[]{0, 0, 0});
                datum2 = DatumFactory.createTime(datum1);
                datum1.compareTo(datum2);
            } catch (Exception e) {
                Assert.assertEquals(IllegalArgumentException.class, e.getClass());
            }

        } catch (Exception e) {
            Assert.assertEquals(resultException, e.getClass());
        }
    }

    @Test
    public void equalsToTest() {
        Datum datum1;
        Datum datum2;
        try {
            Datum equalDat = text1.equalsTo(text2);
            Assert.assertEquals(resultEqualsTo, equalDat.asBool());

            //for coverage
            datum1 = DatumFactory.createText(new byte[]{0, 0, 0});
            datum2 = DatumFactory.createChar(new byte[]{0, 0, 0});
            Assert.assertTrue(datum1.equalsTo(datum2).asBool());

            datum1 = DatumFactory.createText(new byte[]{0, 0, 0});
            datum2 = DatumFactory.createBlob(new byte[]{0, 0, 0});
            Assert.assertTrue(datum1.equalsTo(datum2).asBool());

            try{
                datum1 = DatumFactory.createText(new byte[]{0, 0, 0});
                datum2 = DatumFactory.createNullDatum();
                Assert.assertFalse(datum1.equalsTo(datum2).asBool());
            } catch (Exception e){
                Assert.assertEquals(TajoRuntimeException.class, e.getClass());
            }

            try {
                datum1 = DatumFactory.createText(new byte[]{0, 0, 0});
                datum2 = DatumFactory.createTime(datum1);
                datum1.equalsTo(datum2);
            } catch (Exception e) {
                Assert.assertEquals(IllegalArgumentException.class, e.getClass());
            }

        } catch (Exception e) {
            Assert.assertEquals(resultException, e.getClass());
        }
    }

    @Test
    public void equalsTest() {
        Datum datum1;
        Datum datum2;
        try {
            boolean isEqualToDat = text1.equals(text2);
            Assert.assertEquals(resultEqual, isEqualToDat);

            //for coverage
            datum1 = DatumFactory.createText(new byte[]{0, 0, 0});
            datum2 = DatumFactory.createChar(new byte[]{0, 0, 0});
            Assert.assertFalse(datum1.equals(datum2));

        } catch (Exception e) {
            Assert.assertEquals(resultException, e.getClass());
        }
    }

    /**
     * Coverage
     */

    @Test
    public void asTest() {

        Datum datum = DatumFactory.createText("1"); //prende i valori HTML
        try {
            Assert.assertEquals(datum.size(), 1); //Array Size
            Assert.assertEquals(datum.asInt2(), (short) 1);
            Assert.assertEquals(datum.asInt4(), (int) 1);
            Assert.assertEquals(datum.asInt8(), (long) 1);
            Assert.assertTrue(datum.asFloat4() == (float) 1);
            Assert.assertTrue(datum.asFloat8() == (double) 1);
            Assert.assertEquals(datum.asChar(), 49); //1 equivale a 49 nella tabella ASCII
            Assert.assertEquals(datum.asUnicodeChars(), 49); //1 equivale a 49 nella tabella ASCII
        } catch (Exception e) {
            Assert.assertEquals(TajoRuntimeException.class, e.getClass());

        }

    }

    @Test (expected = TajoRuntimeException.class)
    public void asBoolTest(){
        Datum datum = DatumFactory.createText("1"); //prende i valori HTML
        Assert.assertEquals(datum.asBool(), false);
    }

    @Test (expected = TajoRuntimeException.class)
    public void asByteTest(){
        Datum datum = DatumFactory.createText("1"); //prende i valori HTML
        Assert.assertEquals(datum.asByte(), new byte[]{49});
    }

    @Test
    public void asBytesTest(){
        Datum datum = DatumFactory.createText("1"); //prende i valori HTML
        Assert.assertEquals(datum.asByteArray(), datum.asTextBytes());
    }

}
