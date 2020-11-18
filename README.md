# Redis Geo & Jedis Geo
## Redis-Geo
### What's new?
The are two new features:
- add support to polygon
- add a command to check whether a point is inside the polygon  

Three new command are: geoaddpolygon, geogetpolygon and geopointinpolygon
#### geoaddpolygon
> geoaddpolygon key member lng0 lat0 lng1 lat1 lng2 lat2 [... lngn latn] lng0 lat0

It is used to add polygon. **Remember that the first point must be same as the last point because it is a polygon**
#### geogetpolygon
> geogetpolygon ket memeber

It is used to get polygon
#### geopointinpolygon
> geopointinpolygon keyPoint memberPoint keyPolygon memberPolygon

It is used to check if the point is inside the polygon
### Source code
It is based on redis but not merged into master branch. Source code can be see [here](https://github.com/huachangYu/redis/tree/feature-geo). We just add nearly 200 lines (see [here](https://github.com/redis/redis/compare/unstable...huachangYu:feature-geo)) to the origin codes.
### Download
Download redis-geo at 
- [google drive](https://drive.google.com/file/d/124gWF38xna16SKnatOdO7jHGfJMrK1cj/view?usp=sharing)  
or 
- [baidu Pan](https://pan.baidu.com/s/1OBIsst7_XbC4MTV8UO8tyA). The password is **qse5**
## Jedis-Geo
### What's new?
We add three new functions to the class Jedis:
- Long geoaddpolygon(String key, String member, GeoPolygon polygon)
- GeoPolygon geogetpolygon(String key, String member)
- Boolean geopointinpolygon(String keyPoint, String memberPoint, String keyPolygon, String memberPolygon)  

They correspond to the three new command of redis-geo.
### Download
Download jedis-3.4.0-SNAPSHOT.jar at 
- [google drive](https://drive.google.com/file/d/1aih0j2oX2i9rECd53o5C490Rl84EmPT9/view?usp=sharing)  
or 
- [baidu Pan](https://pan.baidu.com/s/1SSjLOgpbWSvLeOAtRiQ2iQ). The password is **dkv5**
### Source code
It is based on redis but not merged into master branch. Source code can be see [here](https://github.com/huachangYu/jedis/tree/feature-geo). We just add nearly 170 lines (see [here](https://github.com/redis/jedis/compare/master...huachangYu:feature-geo)) to the origin codes.
## Run
1. download redis-geo and run

> bin/redis-server redis.conf  

2. download jedis-geo and add it to external library, then test it with below codes
```java
public static void main(String[] args) {
    Jedis j = new Jedis("127.0.0.1", 6379);
    j.auth("foobared");
    List<GeoCoordinate> points = new ArrayList<>();
    points.add(new GeoCoordinate(120, 30));
    points.add(new GeoCoordinate(121, 31));
    points.add(new GeoCoordinate(122, 30));
    points.add(new GeoCoordinate(120, 30));
    GeoPolygon polygon = new GeoPolygon(points);

    long polygonStatus = j.geoaddpolygon("gp", "p0", polygon); // add polygon
    long pointStatus = j.geoadd("pt", 121, 30.5, "pt0"); // add point
    long pointStatus1 = j.geoadd("pt", 121, 34, "pt1"); // add point
    Boolean res = j.geopointinpolygon("pt", "pt0", "gp", "p0"); // check if pto is in the polygon
    Boolean res1 = j.geopointinpolygon("pt", "pt1", "gp", "p0");
    System.out.println("pt0 in polygon: " + res);
    System.out.println("pt1 in polygon: " + res1);
}
```

The output is
```
pt0 in polygon: true
pt1 in polygon: false
```