package nta.catalog;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import nta.catalog.exception.CatalogException;
import nta.catalog.proto.CatalogProtos.DataType;

/**
 * 
 * @author Hyunsik Choi
 *
 */
public interface CatalogService {
  
  /**
   * Get a table description by name
   * @param name table name
   * @return a table description
   * @see TableDescImpl
   * @throws Throwable
   */
  TableDesc getTableDesc(String name) throws CatalogException;
  
  /**
   * 
   * @return
   * @throws CatalogException
   */
  Collection<TableDesc> getAllTableDescs() throws CatalogException;
  
  /**
   * 
   * @return
   * @throws CatalogException
   */
  Collection<FunctionDesc> getFunctions() throws CatalogException;
  
  /**
   * Add a table via table description
   * @param meta table meta
   * @see TableDescImpl
   * @throws Throwable
   */
  void addTable(TableDesc desc) throws CatalogException;
  
  /**
   * Drop a table by name
   * @param name table name
   * @throws Throwable
   */
  void deleteTable(String name) throws CatalogException;
  
  boolean existsTable(String tableId);
  
  void registerFunction(FunctionDesc funcDesc) throws CatalogException;
 
  void unregisterFunction(String signature, DataType...paramTypes) throws CatalogException;
  
  /**
   * 
   * @param signature
   * @return
   */
  FunctionDesc getFunctionMeta(String signature, DataType...paramTypes);
  
  /**
   * 
   * @param signature
   * @return
   */
  boolean containFunction(String signature, DataType...paramTypes);
  
  List<TabletServInfo> getHostByTable(String tableId);
  
  void updateAllTabletServingInfo(List<String> onlineServers) throws IOException;
}