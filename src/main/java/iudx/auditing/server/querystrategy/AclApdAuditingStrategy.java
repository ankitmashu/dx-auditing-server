package iudx.auditing.server.querystrategy;

import static iudx.auditing.server.querystrategy.util.Constants.APD_IMMUDB_TABLE_NAME;
import static iudx.auditing.server.querystrategy.util.Constants.APD_PG_TABLE_NAME;
import static iudx.auditing.server.querystrategy.util.Constants.APD_WRITE_QUERY_IMMUDB;
import static iudx.auditing.server.querystrategy.util.Constants.APD_WRITE_QUERY_PG;
import static iudx.auditing.server.querystrategy.util.Constants.API;
import static iudx.auditing.server.querystrategy.util.Constants.BODY;
import static iudx.auditing.server.querystrategy.util.Constants.DELETE_QUERY;
import static iudx.auditing.server.querystrategy.util.Constants.EPOCH_TIME;
import static iudx.auditing.server.querystrategy.util.Constants.HTTP_METHOD;
import static iudx.auditing.server.querystrategy.util.Constants.ISO_TIME;
import static iudx.auditing.server.querystrategy.util.Constants.PRIMARY_KEY;
import static iudx.auditing.server.querystrategy.util.Constants.SIZE;
import static iudx.auditing.server.querystrategy.util.Constants.USER_ID;

import io.vertx.core.json.JsonObject;
import iudx.auditing.server.rabbitmq.consumers.SubscriptionMonitoringConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AclApdAuditingStrategy implements AuditingServerStrategy {
  private JsonObject config;
  private static final Logger LOGGER = LogManager.getLogger(AclApdAuditingStrategy.class);

  public AclApdAuditingStrategy(JsonObject config) {
    this.config = config;
  }

  @Override
  public String buildPostgresWriteQuery(JsonObject request) {
    String primaryKey = request.getString(PRIMARY_KEY);
    String userId = request.getString(USER_ID);
    String api = request.getString(API);
    String method = request.getString(HTTP_METHOD);
    JsonObject body = request.getJsonObject(BODY);
    long responseSize = request.getLong(SIZE);
    String isoTime = request.getString(ISO_TIME);
    String databaseTableName = config.getString(APD_PG_TABLE_NAME);


    ZonedDateTime zonedDateTime = ZonedDateTime.parse(isoTime);
    zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));

    LocalDateTime utcTime = zonedDateTime.toLocalDateTime();

    return APD_WRITE_QUERY_PG
        .replace("$0", databaseTableName)
        .replace("$1", primaryKey)
        .replace("$2", userId)
        .replace("$3", api)
        .replace("$4", method)
        .replace("$5", body.toString())
        .replace("$6", Long.toString(responseSize))
        .replace("$7", utcTime.toString());
  }

  @Override
  public String buildPostgresDeleteQuery(JsonObject request) {
    String databaseTableName = config.getString(APD_PG_TABLE_NAME);
    String primaryKey = request.getString(PRIMARY_KEY);
    return DELETE_QUERY.replace("$0", databaseTableName).replace("$1", primaryKey);
  }

  @Override
  public String buildImmudbWriteQuery(JsonObject request) {
    LOGGER.debug("requeust == " + request);
    String primaryKey = request.getString(PRIMARY_KEY);
    String userId = request.getString(USER_ID);
    String api = request.getString(API);
    String method = request.getString(HTTP_METHOD);
    String body = request.getString(BODY);
    JsonObject body1 = request.getJsonObject(BODY);
    LOGGER.debug("body --> " + body);
    LOGGER.debug("body1 --> " + body1);

    long responseSize = request.getLong(SIZE);
    long epochTime = request.getLong(EPOCH_TIME);
    String isoTime = request.getString(ISO_TIME);
    String databaseTableName = config.getString(APD_IMMUDB_TABLE_NAME);
    LOGGER.debug("query --> " + APD_WRITE_QUERY_IMMUDB
            .replace("$0", databaseTableName)
            .replace("$1", primaryKey)
            .replace("$2", userId)
            .replace("$3", api)
            .replace("$4", method)
            .replace("$5", body1.encode())
            .replace("$6", Long.toString(responseSize))
            .replace("$7", Long.toString(epochTime))
            .replace("$8", isoTime));
    return APD_WRITE_QUERY_IMMUDB
        .replace("$0", databaseTableName)
        .replace("$1", primaryKey)
        .replace("$2", userId)
        .replace("$3", api)
        .replace("$4", method)
        .replace("$5", body)
        .replace("$6", Long.toString(responseSize))
        .replace("$7", Long.toString(epochTime))
        .replace("$8", isoTime);
  }

}

