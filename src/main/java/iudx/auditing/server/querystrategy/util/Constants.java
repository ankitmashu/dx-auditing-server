package iudx.auditing.server.querystrategy.util;

import javax.swing.plaf.PanelUI;
import org.apache.zookeeper.server.admin.Commands;

public class Constants {
  public static final String SIZE = "response_size";
  public static final String ID = "id";
  public static final String USER_ID = "userid";
  public static final String PRIMARY_KEY = "primaryKey";
  public static final String PROVIDER_ID = "providerID";
  public static final String ISO_TIME = "isoTime";
  public static final String BODY = "body";
  public static final String IUDX_ID = "iudxID";
  public static final String IID = "iid";
  public static final String USER_ROLE = "userRole";
  public static final String HTTP_METHOD = "httpMethod";
  public static final String RESOURCE_GROUP = "resourceGroup";
  public static final String TYPE = "type";
  public static final String DELEGATOR_ID = "delegatorId";

  public static final String EPOCH_TIME = "epochTime";
  public static final String API = "api";
  public static final String RS_PG_TABLE_NAME = "postgresRsTableName";
  public static final String RS_SUBS_TABLE_NAME = "postgresSubscriptionAuditTableName";
  public static final String RS_IMMUDB_TABLE_NAME = "immudbRsTableName";
  public static final String AUTH_PG_TABLE_NAME = "postgresAuthTableName";
  public static final String AUTH_IMMUDB_TABLE_NAME = "immudbAuthTableName";
  public static final String CAT_PG_TABLE_NAME = "postgresCatTableName";
  public static final String CAT_IMMUDB_TABLE_NAME = "immudbCatTableName";
  public static final String APD_PG_TABLE_NAME = "postgersApdTableName";
  public static final String APD_IMMUDB_TABLE_NAME = "immudbApdTableName";
  public static final String RS_WRITE_QUERY_IMMUDB =
      "INSERT INTO $0 (id,api,userid,epochtime,resourceid,isotime,providerid,size) "
          + "VALUES ('$1','$2','$3',$4,'$5','$6','$7',$8)";
  public static final String RS_WRITE_QUERY_PG =
      "INSERT INTO $0 (id,api,userid,epochtime,resourceid,isotime,providerid,size,time,resource_group,item_type,delegator_id)"
          + " VALUES ('$1','$2','$3',$4,'$5','$6','$7',$8,'$9','$a','$b','$c')";
  public static final String CAT_WRITE_QUERY_PG =
      "INSERT INTO $0 (id, userRole, userID, iid, api, method, time, iudxID) "
          + "VALUES ('$1','$2','$3','$4','$5','$6',$7,'$8')";
  public static final String CAT_WRITE_QUERY_IMMUDB =
      "INSERT INTO $0 (id, userRole, userID, iid, api, method, time, iudxID) "
          + "VALUES ('$1','$2','$3','$4','$5','$6',$7,'$8')";
  public static final String AUTH_WRITE_QUERY_PG =
      "INSERT INTO $0 (id,body,endpoint,method,time,userid) VALUES ('$1','$2','$3','$4','$5','$6')";
  public static final String AUTH_WRITE_QUERY_IMMUDB =
      "INSERT INTO $0 (id,body,endpoint,method,time,userid) VALUES ('$1','$2','$3','$4','$5','$6')";
  public static final String DELETE_QUERY = "DELETE FROM $0 WHERE id = '$1';";
  public static final String DELETE_SUBSCRIPTION_QUERY =
      "DELETE FROM $0 WHERE subscription_id = '$1';";

  public static final String RS_SUBS_WRITE_QUERY_PG =
      "INSERT INTO $0 (subscription_id, user_id, event_type, subscription_type, resource_id)"
          + " VALUES ('$1','$2','$3','$4','$5');";
  public static final String RS_SUBS_UPDATE_QUERY_PG =
      "UPDATE $0 SET event_type = '$1' WHERE subscription_id = '$2'";
  public static final String APD_WRITE_QUERY_PG =
      "INSERT INTO $0 (id,userid,endpoint,method,body,size,time) VALUES ('$1','$2','$3','$4','$5',$6,'$7')";
  public static final String APD_WRITE_QUERY_IMMUDB =
      "INSERT INTO $0 (id,userid,endpoint,method,body,size,epochtime,isotime)"
          + " VALUES ('$1','$2','$3','$4','$5',$6,$7,'$8')";

}