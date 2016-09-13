# Drone-Transect-Generator
This library lets you create transects for a given polygon.
You need to provide few inputs : 
1. Polygon point list.
2. Distance betweent the transects.
3. Angle at which the transects should be drawn.

#Preview
![screenshot_20160913-155653](https://cloud.githubusercontent.com/assets/4836122/18471177/3d1c9bd0-79ce-11e6-961b-19f3a41e3d72.png)
![screenshot_20160913-155658](https://cloud.githubusercontent.com/assets/4836122/18471178/3d1d94c2-79ce-11e6-954c-25ad33063abe.png)
![screenshot_20160913-155705](https://cloud.githubusercontent.com/assets/4836122/18471179/3d368fcc-79ce-11e6-880c-daa9fc52a245.png)
![screenshot_20160913-155710](https://cloud.githubusercontent.com/assets/4836122/18471180/3d4ba9a2-79ce-11e6-99fd-4ef226dce07f.png)


### Instructions
There are two ways in which you can use this library.
- First : Just copy paste entire **dronetransects** package from **LibraryCode/dronetransects** in your project and start using it.
- Second : You can add given **jar file** in your application.

### How to use it
#### You have a list of latitude and longitude points that forms a polygon
        final List<LatLng> latLngPolygon = new ArrayList<>();
        {
          latLngPolygon.add(new LatLng(18.517458180674886, 73.77362251281738));
          latLngPolygon.add(new LatLng(18.51379572782087, 73.77422332763672));
          latLngPolygon.add(new LatLng(18.51656292166201, 73.77563953399658));
          latLngPolygon.add(new LatLng(18.513999199480704, 73.7783432006836));
          latLngPolygon.add(new LatLng(18.51855690124174, 73.77731323242188));
          latLngPolygon.add(new LatLng(18.517458180674886, 73.77362251281738));
        }

#### Convert list of lat lng to list of points.
        List<Point> polygonPoints = new ArrayList<>();
        for (int i = 0; i < latLngPolygon.size(); i++) {
            Point point = new Point(latLngPolygon.get(i).getLatitude(),
                    latLngPolygon.get(i).getLongitude());
            polygonPoints.add(point);
        }
        
#### Call generate function with a given angle (0-360) and distance betweenn transects (0.0001 ~ 10 meters on ground)
        int angle = 30; // angle can be anything between 0 to 360.
        double distance = 0.0001; //This is pretty much hit and trial, 0.0001 is approx 10 meter on ground
        
        List<Point> waypoints = Transects.generateTransects(polygonPoints, angle, distnace);
        
        //"genererateTransects" gives you a list of points (waypoints). Joining all the points will give you your desired transects.  
