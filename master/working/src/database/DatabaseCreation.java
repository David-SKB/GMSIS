/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;

public class DatabaseCreation
{
   public static void main( String args[] )
  {
    Connection c;
    Statement stmt;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:test.db");
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
"  HRATE           DECIMAL(3,2)                NOT NULL,\n" +               
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
" FOREIGN KEY(CUSTOMERID) REFERENCES CUSTOMER(ID)\n" +
");\n" +
"\n" +
"CREATE TABLE WARRANTY\n" +
"(\n" +
" REGISTRATION      INT     NOT NULL,\n" +
" NAME              TEXT    NOT NULL,\n" +
" ADDRESS           TEXT    NOT NULL,\n" +
" EXPIRYDATE        DATE    NOT NULL,\n" +
" FOREIGN KEY(REGISTRATION) REFERENCES VEHICLE(REGISTRATION)\n" +
");\n" +
"/* PARTS TABLE */\n" +
"CREATE TABLE STOCKPARTS (\n" +
"  ID                INT     PRIMARY KEY  NOT NULL,\n" +
"  NAME              TEXT                 NOT NULL,\n" +
"  DESCRIPTION       TEXT                 NOT NULL,\n" +
"  COST              INT                  NOT NULL\n" +
");\n" +
"CREATE TABLE REPAIRS (\n" +
"  ID                INT     PRIMARY KEY  NOT NULL,\n" +
"  VEHICLEID         INT                  NOT NULL,\n" +
"  CUSTOMERID        INT                  NOT NULL,\n" +
"  TOTALCOST         INT                  NOT NULL,\n" +
"  FOREIGN KEY(VEHICLEID) REFERENCES VEHICLE(ID),\n" +
"  FOREIGN KEY(CUSTOMERID) REFERENCES CUSTOMER(ID)\n" +
");\n" +
"CREATE TABLE REPAIRPARTS (\n" +
"  ID                INT     PRIMARY KEY     NOT NULL,\n" +
"  REPAIRID          INT                     NOT NULL,\n" +
"  PARTNAME          INT                     NOT NULL,\n" +
"  WARRANTYEND       INT                     NOT NULL,\n" +
"  WARRANTYSTART     INT                     NOT NULL,\n" +
"  FOREIGN KEY(REPAIRID) REFERENCES REPAIRS(ID)\n" +
");\n" +
"/* SPECIALIST REPAIRS TABLE */\n" +
"CREATE TABLE SPECIALISTREPAIRS\n" +
"(\n" +
"CUSTOMERID             INT                    NOT NULL,\n" +
"CONTRACT               VARCHAR(255)           NOT NULL,\n" +
"ITEMID                 VARCHAR(255)           NOT NULL,\n" +
"SPCID                  INT                    NOT NULL,\n" +
"DELIVERYDATE           DATE                   NOT NULL,\n" +
"RETURNDATE             DATE                   NOT NULL,\n" +
"COST                   INT                    NOT NULL,\n" +
"FOREIGN KEY(CUSTOMERID) REFERENCES CUSTOMER(ID)\n" +
");\n" +
"\n" +
"CREATE TABLE CENTRES\n" +
"(\n" +
"SPCID                  INT            PRIMARY KEY            NOT NULL,\n" +
"NAME                   VARCHAR(255)                          NOT NULL,\n" +
"ADDRESS                VARCHAR(255)                          NOT NULL,\n" +
"TELEPHONE              VARCHAR(11)                           NOT NULL,\n" +
"EMAIL                  VARCHAR(255)                          NOT NULL\n" +
");\n" +
"/* BOOKINGS TABLE */\n" +
"CREATE TABLE BOOKINGS (\n" +
"	ID                   INT    PRIMARY KEY      NOT NULL,\n" +
"	BOOKDATE             DATE                    NOT NULL,\n" +
"	TIME                 INT                     NOT NULL,\n" +
"	TYPE                 TEXT                    NOT NULL,\n" +
"	MILAGE               INT                     NOT NULL,\n" +
"	CUSTOMERID           INT                     NOT NULL,\n" +
"	VEHICLEREGISTRATION  INT                     NOT NULL,\n" +
"	EMPLOYEEID           INT                     NOT NULL,\n" +
"	FOREIGN KEY(CUSTOMERID) references CUSTOMER(ID),\n" +
"	FOREIGN KEY(VEHICLEREGISTRATION) references VEHICLE(REGISTRATION),\n" +
"	FOREIGN KEY(EMPLOYEEID) references EMPLOYEE(ID)\n" +
");";

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
