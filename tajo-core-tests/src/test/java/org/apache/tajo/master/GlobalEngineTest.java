package org.apache.tajo.master;


import org.apache.tajo.conf.TajoConf;
import org.apache.tajo.datum.DatumFactory;
import org.apache.tajo.engine.query.QueryContext;
import org.apache.tajo.session.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GlobalEngineTest {

    TajoMaster.MasterContext context;

    String sessionId;
    String userName;
    String databaseName;

    String query;

    Boolean isJson;

    Object resultException;
    Object result;

    Session sessione;


    public GlobalEngineTest(String sessionId, String userName, String databaseName,
                            String query, Boolean isJson, Object resultException, Object result) {
        this.sessionId = sessionId;
        this.userName = userName;
        this.databaseName = databaseName;
        this.query = query;
        this.isJson = isJson;
        this.resultException = resultException;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection Int2DatumParameters() throws Exception {
        return Arrays.asList(new Object[][]{
                //Datum1, Datum2, Exception, ResEqual, ResCompare, ResPlus, ResMinus, ResMultiply, ResDivide, ResModular
                {"prova", "prova", "prova", "prova", false, null, null}

        });

    }

    private Session generateSession(String sesID, String usrN, String dbName) {
        Session session = new Session(sesID, usrN, dbName);
        return session;
    }

    @Before
    public void prepareSession() {
        sessione = generateSession(sessionId, userName, databaseName);
    }

//    @Before
//    public void prepareContext(){
//        context = new TajoMaster.MasterContext()
//    }

    @After
    public void closeSession(){
        sessione.close();
    }


    @Test
    public void executeTestQuery() {

        try{

            //GlobalEngine globalEngine = new GlobalEngine(new TajoMaster.MasterContext());
            //globalEngine.executeQuery(sessione, query, isJson);
        } catch (Exception e){
            e.printStackTrace();
        }

    }


}
