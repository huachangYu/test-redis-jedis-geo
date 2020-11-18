package com.yuhuachang;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoPolygon;
import redis.clients.jedis.Jedis;

public class App {
    public static void main(String[] args) {
        Jedis j = new Jedis("127.0.0.1", 6379);
        j.auth("foobared");
        List<GeoCoordinate> points = new ArrayList<>();
        points.add(new GeoCoordinate(120, 30));
        points.add(new GeoCoordinate(121, 31));
        points.add(new GeoCoordinate(122, 30));
        points.add(new GeoCoordinate(120, 30));
        GeoPolygon polygon = new GeoPolygon(points);

        long polygonStatus = j.geoaddpolygon("gp", "p0", polygon);
        long pointStatus = j.geoadd("pt", 121, 30.5, "pt0");
        long pointStatus1 = j.geoadd("pt", 121, 34, "pt1");
        Boolean res = j.geopointinpolygon("pt", "pt0", "gp", "p0");
        Boolean res1 = j.geopointinpolygon("pt", "pt1", "gp", "p0");
        System.out.println("pt0 in polygon: " + res);
        System.out.println("pt1 in polygon: " + res1);
    }
}
