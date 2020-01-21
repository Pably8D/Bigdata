package it.bigdata.ejb.service.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoClientProvider {
   private static final String NAME_DB_MONGO = "stakoverflow";
   private MongoClient mongoClient = null;

   @Lock(LockType.READ)
   public MongoClient getMongoClient() {
      return this.mongoClient;
   }

   @PostConstruct
   public void init() {
      String mongoIpAddress = "127.0.0.1";
      Integer mongoPort = 27017;

      try {
         this.mongoClient = new MongoClient(mongoIpAddress, mongoPort);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public DB getDB() {
      DB db = null;

      try {
         db = this.mongoClient.getDB("stackoverflow");
         Set var2 = db.getCollectionNames();
         return db;
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public static String getNameDbMongo() {
      return "stakoverflow";
   }
}
