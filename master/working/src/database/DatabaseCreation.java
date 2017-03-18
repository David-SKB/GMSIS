/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;

public class DatabaseCreation
{
   public static void createDB()
  {
    Connection c;
    Statement stmt;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:src/database/gmsisdb.db");
      System.out.println("Opened database successfully");

      stmt = c.createStatement();
      String sql = "/*******************************************\n" +
"* INITIAL DATABASE FILE CREATED FOR GM-SIS *\n" +
"********************************************/\n" +
"\n" +
"/* AUTHENTICATION */\n" +
"CREATE TABLE USERS\n" +
"(\n" +
"  ID              INTEGER(5)     PRIMARY KEY  NOT NULL,\n" +
"  PASSWORD        TEXT                        NOT NULL,\n" + 
"  SURNAME         TEXT                        NOT NULL,\n" +
"  FIRSTNAME       TEXT                        NOT NULL,\n" + 
"  HRATE           DECIMAL(3,2),\n" +               
"  SYSADM          BOOL                        NOT NULL\n" +
");\n" +
"\n" +
"\n" +
"/* CUSTOMER TABLE */\n" +
"CREATE TABLE CUSTOMER\n" +
"(\n" +
"  ID              INTEGER     PRIMARY KEY,\n" +
"  SURNAME         TEXT                        NOT NULL,\n" +
"  FIRSTNAME       TEXT                        NOT NULL,\n" +              
"  ADDRESS         CHAR(50)                    NOT NULL,\n" +
"  POSTCODE        TEXT                        NOT NULL,\n" +
"  PHONE           TEXT        UNIQUE          NOT NULL,\n" +             
"  EMAIL           TEXT        UNIQUE          NOT NULL,\n" +
"  CUSTOMERTYPE    TEXT                        NOT NULL\n" +
");\n" +
"/* VEHICLE TABLE */\n" +
"CREATE TABLE VEHICLE\n" +
"(\n" +
" REGISTRATION INT PRIMARY KEY NOT NULL,\n" +
" CUSTOMERID   INT NOT NULL,\n" +
" MAKE         TEXT  NOT NULL,\n" +
" MODEL        TEXT  NOT NULL,\n" +
" ENGINESIZE   INT   NOT NULL,\n" +
" FUELTYPE     TEXT  NOT NULL,\n" +
" COLOUR       TEXT  NOT NULL,\n" +
" MOTDATE      DATE  NOT NULL, \n" +
" LASTSERVICE  DATE  NOT NULL,\n" +
" MILEAGE      INT   NOT NULL,\n" +
" FOREIGN KEY(CUSTOMERID) REFERENCES CUSTOMER(ID) ON DELETE CASCADE\n" +
");\n" +
"\n" +
"CREATE TABLE WARRANTY\n" +
"(\n" +
" REGISTRATION      INT     NOT NULL,\n" +
" NAME              TEXT    NOT NULL,\n" +
" ADDRESS           TEXT    NOT NULL,\n" +
" EXPIRYDATE        DATE    NOT NULL,\n" +
" FOREIGN KEY(REGISTRATION) REFERENCES VEHICLE(REGISTRATION) ON DELETE CASCADE\n" +
");\n" +
"/* PARTS TABLE */\n" +
"CREATE TABLE STOCKPARTS (\n" +
"  ID                INTEGER     PRIMARY KEY  AUTOINCREMENT,\n" +
"  NAME              TEXT                 NOT NULL,\n" +
"  DESCRIPTION       TEXT                 NOT NULL,\n" +
"  COST              INT                  NOT NULL,\n" +
"  STOCK             INT                  NOT NULL\n" +
");\n" +
"CREATE TABLE USEDPARTS (\n" +
"  ID                INTEGER   PRIMARY KEY  AUTOINCREMENT,\n" +
"  BOOKINGID         INT                     NOT NULL,\n" +
"  VEHICLEID         INT                     NOT NULL,\n" +
"  CUSTOMERID        INT                     NOT NULL,\n" +
"  PARTNAME          INT                     NOT NULL,\n" +
"  WARRANTYEND       DATE                    NOT NULL,\n" +
"  WARRANTYSTART     DATE                    NOT NULL,\n" +
"  COST		     INT		     NOT NULL,\n" +
"  FOREIGN KEY (ID)  REFERENCES STOCKPARTS(ID),\n" +
"  FOREIGN KEY(BOOKINGID) REFERENCES BOOKINGS(ID),\n" +
"  FOREIGN KEY(VEHICLEID) REFERENCES VEHICLE(REGISTRATION) ON DELETE NO ACTION,\n" +
"  FOREIGN KEY(CUSTOMERID) REFERENCES CUSTOMER(ID) ON DELETE NO ACTION\n" +
");\n" +
"/* SPECIALIST REPAIRS TABLES */\n" +
"CREATE TABLE REPAIRPARTS\n" +
"(\n" +
"ID                     INT       PRIMARY KEY   NOT NULL,\n" +              
"REGNO                  TEXT                    NOT NULL,\n" +
"PARTID                 INT                     NOT NULL,\n" +
"SPCID                  INT                     NOT NULL,\n" +              
"DELIVERYDATE           TEXT                    NOT NULL,\n" +
"RETURNDATE             TEXT                    NOT NULL,\n" +
"COST                   REAL                     NOT NULL,\n" +
"FOREIGN KEY(REGNO) REFERENCES VEHICLE(REGISTRATION) ON DELETE NO ACTION,\n" +
"FOREIGN KEY(SPCID) REFERENCES CENTRES(SPCID) ON DELETE NO ACTION\n" +    
              
");\n" +
"CREATE TABLE REPAIRVEHICLE\n" +
"(\n" +
"ID                     INTEGER    PRIMARY KEY          ,\n" +
"REGNO                  TEXT                    NOT NULL,\n" +
"SPCID                  INT                     NOT NULL,\n" +
"DELIVERYDATE           TEXT                    NOT NULL,\n" +
"RETURNDATE             TEXT                    NOT NULL,\n" +
"COST                   REAL                    NOT NULL,\n" +
"FOREIGN KEY(REGNO) REFERENCES VEHICLE(REGISTRATION) ON DELETE NO ACTION,\n" +
"FOREIGN KEY(SPCID) REFERENCES CENTRES(SPCID) ON DELETE NO ACTION\n" +
");\n" +
"CREATE TABLE CENTRES\n" +
"(\n" +
"SPCID                  INTEGER            PRIMARY KEY                ,\n" +
"NAME                   VARCHAR(255)                          NOT NULL,\n" +
"ADDRESS                VARCHAR(255)                          NOT NULL,\n" +
"TELEPHONE              VARCHAR(11)                           NOT NULL,\n" +
"EMAIL                  VARCHAR(255)                          NOT NULL\n" +
");\n" +
"/* BOOKINGS TABLE */\n" +
"CREATE TABLE BOOKINGS (\n" +
"	ID                   INTEGER    PRIMARY KEY      ,\n" +
"	BOOKDATE             VARCHAR(255)            NOT NULL,\n" +
"	TIME                 INT                     NOT NULL,\n" +
"	TYPE                 TEXT                    NOT NULL,\n" +
"	CUSTOMERID           INT                     NOT NULL,\n" +
"	VEHICLEREGISTRATION  INT                     NOT NULL,\n" +
"	MILEAGE              INT                     NOT NULL,\n" +              
"	EMPLOYEEID           INT                     NOT NULL,\n" +
"	FOREIGN KEY(CUSTOMERID) references CUSTOMER(ID) ON DELETE NO ACTION,\n" +
"	FOREIGN KEY(VEHICLEREGISTRATION) references VEHICLE(REGISTRATION) ON DELETE NO ACTION,\n" +
"	FOREIGN KEY(EMPLOYEEID) references USERS(ID)\n" +
");\n" + 
"/* ADMIN USER */\n" + 
"INSERT INTO USERS (ID, PASSWORD, SURNAME, FIRSTNAME, HRATE, SYSADM)\n" +
" VALUES(10000,'ADMIN','GMSIS','ADMIN',NULL,'TRUE');";

      stmt.executeUpdate(sql);
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Table created successfully");  
  }
}
