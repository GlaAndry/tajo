package org.apache.tajo.storage.s3;


import net.minidev.json.JSONObject;
import org.apache.hadoop.fs.Path;
import org.apache.tajo.conf.TajoConf;
import org.apache.tajo.storage.TablespaceManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class S3TableSpaceTest {

    public static final String S3_SPACENAME = "s3_cluster";
    public static final String S3N_SPACENAME = "s3N_cluster";
    public static final String S3A_SPACENAME = "s3A_cluster";

    public static final String S3_URI = "s3://tajo-test/";
    public static final String S3N_URI = "s3n://tajo-test/";
    public static final String S3A_URI = "s3a://tajo-test/";

    //Devono essere necessariamente istanziate in questo modo altrimenti quando vado a generare le tabelle
    //viene restituito NullPointer.

    Object resultMethod;
    Object resultException;

    @Parameterized.Parameters
    public static Collection S3TableSpaceParameters() throws Exception {
        return Arrays.asList(new Object[][]{
                //resultMethod, resultException
                {30L, NullPointerException.class}
        });

    }

    public S3TableSpaceTest(Object resultMethod, Object resultException) {
        this.resultMethod = resultMethod;
        this.resultException = resultException;
    }

    @BeforeClass
    public static void generateTables() throws Exception {
        // Add tablespace for s3 prefix
        S3TableSpace s3TableSpace = new S3TableSpace(S3_SPACENAME, URI.create(S3_URI), new JSONObject());
        TajoConf tajoConf = new TajoConf();
        tajoConf.set("fs.s3.impl", MockS3FileSystem.class.getName());
        tajoConf.set("fs.s3.awsAccessKeyId", "test_access_key_id");
        tajoConf.set("fs.s3.awsSecretAccessKey", "test_secret_access_key");
        s3TableSpace.init(tajoConf);
        TablespaceManager.addTableSpaceForTest(s3TableSpace);

        // Add tablespace for s3n prefix
        S3TableSpace s3nTableSpace = new S3TableSpace(S3N_SPACENAME, URI.create(S3N_URI), new JSONObject());
        tajoConf = new TajoConf();
        tajoConf.set("fs.s3n.impl", MockS3FileSystem.class.getName());
        tajoConf.set("fs.s3n.awsAccessKeyId", "test_access_key_id");
        tajoConf.set("fs.s3n.awsSecretAccessKey", "test_secret_access_key");
        s3nTableSpace.init(tajoConf);
        TablespaceManager.addTableSpaceForTest(s3nTableSpace);

        // Add tablespace for s3a prefix
        S3TableSpace s3aTableSpace = new S3TableSpace(S3A_SPACENAME, URI.create(S3A_URI), new JSONObject());
        tajoConf = new TajoConf();
        tajoConf.set("fs.s3a.impl", MockS3FileSystem.class.getName());
        tajoConf.set("fs.s3a.access.key", "test_access_key_id");
        tajoConf.set("fs.s3a.secret.key", "test_secret_access_key");
        s3aTableSpace.init(tajoConf);
        TablespaceManager.addTableSpaceForTest(s3aTableSpace);
    }

    @AfterClass
    public static void removeAllTables() throws IOException {
        TablespaceManager.removeTablespaceForTest(S3_SPACENAME);
        TablespaceManager.removeTablespaceForTest(S3N_SPACENAME);
        TablespaceManager.removeTablespaceForTest(S3A_SPACENAME);
    }


    @Test
    public void testCalculateSizeWithS3Prefix() throws Exception {
        try{
            Path path = new Path(S3_URI, "test");
            assertTrue((TablespaceManager.getByName(S3_SPACENAME)) instanceof S3TableSpace);
            S3TableSpace tableSpace = TablespaceManager.get(path.toUri());
            tableSpace.setAmazonS3Client(new MockAmazonS3());
            long size = tableSpace.calculateSize(path);
            assertEquals(resultMethod, size);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testCalculateSizeWithS3NPrefix() throws Exception {
        try{
            Path path = new Path(S3N_URI, "test");
            assertTrue((TablespaceManager.getByName(S3N_SPACENAME)) instanceof S3TableSpace);
            S3TableSpace tableSpace = TablespaceManager.get(path.toUri());
            tableSpace.setAmazonS3Client(new MockAmazonS3());
            long size = tableSpace.calculateSize(path);
            assertEquals(resultMethod, size);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testCalculateSizeWithS3APrefix() throws Exception {
        try{
            Path path = new Path(S3A_URI, "test");
            assertTrue((TablespaceManager.getByName(S3A_SPACENAME)) instanceof S3TableSpace);
            S3TableSpace tableSpace = TablespaceManager.get(path.toUri());
            tableSpace.setAmazonS3Client(new MockAmazonS3());
            long size = tableSpace.calculateSize(path);
            assertEquals(resultMethod, size);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testCalculateSizeWithS3PrefixNullPath() throws Exception {

        try {
            Path path = null;
            assertTrue((TablespaceManager.getByName(S3_SPACENAME)) instanceof S3TableSpace);
            S3TableSpace tableSpace = TablespaceManager.get(path.toUri());
            tableSpace.setAmazonS3Client(new MockAmazonS3());
            tableSpace.calculateSize(path);
        } catch (Exception e) {
            assertEquals(resultException, e.getClass());
        }
    }

    @Test
    public void testCalculateSizeWithS3NPrefixNullPath() throws Exception {

        try {
            Path path = null;
            assertTrue((TablespaceManager.getByName(S3N_SPACENAME)) instanceof S3TableSpace);
            S3TableSpace tableSpace = TablespaceManager.get(path.toUri());
            tableSpace.setAmazonS3Client(new MockAmazonS3());
            tableSpace.calculateSize(path);
        } catch (Exception e) {
            assertEquals(resultException, e.getClass());
        }
    }

    @Test
    public void testCalculateSizeWithS3APrefixNullPath() throws Exception {

        try {
            Path path = null;
            assertTrue((TablespaceManager.getByName(S3A_SPACENAME)) instanceof S3TableSpace);
            S3TableSpace tableSpace = TablespaceManager.get(path.toUri());
            tableSpace.setAmazonS3Client(new MockAmazonS3());
            tableSpace.calculateSize(path);
        } catch (Exception e) {
            assertEquals(resultException, e.getClass());
        }
    }

    @Test
    public void testCalculateSizeWithS3PrefixStrangePath() throws Exception {
        try{
            Path path = new Path("incorrect-PATH", "test");
            assertTrue((TablespaceManager.getByName(S3_SPACENAME)) instanceof S3TableSpace);
            S3TableSpace tableSpace = TablespaceManager.get(path.toUri());
            tableSpace.setAmazonS3Client(new MockAmazonS3());
            tableSpace.calculateSize(path);
        } catch (Exception e) {
            assertEquals(StringIndexOutOfBoundsException.class, e.getClass());
        }
    }

    @Test
    public void testCalculateSizeWithS3NPrefixStrangePath() throws Exception {
        try{
            Path path = new Path("incorrect-PATH", "test");
            assertTrue((TablespaceManager.getByName(S3N_SPACENAME)) instanceof S3TableSpace);
            S3TableSpace tableSpace = TablespaceManager.get(path.toUri());
            tableSpace.setAmazonS3Client(new MockAmazonS3());
            tableSpace.calculateSize(path);
        } catch (Exception e) {
            assertEquals(StringIndexOutOfBoundsException.class, e.getClass());
        }
    }

    @Test
    public void testCalculateSizeWithS3APrefixStrangePath() throws Exception {
        try{
            Path path = new Path("incorrect-PATH", "test");
            assertTrue((TablespaceManager.getByName(S3A_SPACENAME)) instanceof S3TableSpace);
            S3TableSpace tableSpace = TablespaceManager.get(path.toUri());
            tableSpace.setAmazonS3Client(new MockAmazonS3());
            tableSpace.calculateSize(path);
        } catch (Exception e) {
            assertEquals(StringIndexOutOfBoundsException.class, e.getClass());
        }
    }

}
