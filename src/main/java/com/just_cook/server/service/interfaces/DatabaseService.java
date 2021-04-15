package com.just_cook.server.service.interfaces;

public interface DatabaseService {
    String LZ = "2020_zaranek_lukasz";
    String LZPass = "32057";
    String AG = "2020_grzywa_arkadiusz";
    String AGPass = "31994";
    String JDBC_DRIVER = "org.postgresql.Driver";
    String DB_URL = "jdbc:postgresql://195.150.230.210:5434/" + LZ;
    String USER = LZ;
    String PASS = LZPass;
    int POOL_SIZE = 5;
}
