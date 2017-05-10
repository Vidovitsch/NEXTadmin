<%-- 
    Document   : mapcreation
    Created on : Apr 17, 2017, 10:21:02 PM
    Author     : Michael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-app.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-auth.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-database.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.2/firebase.js"></script>        
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/css/map.css" />
        <title>Mapcreation</title>
    </head>
    <body>
        <div class="wrapper">
            <div class="menu">
                <div class="menu-item">
                    <!-- show the locations and an option to add a new location -->
                    <span class="soflow-title">Locations</span>
                    <br>
                    <select class="soflow" id="option-location" onchange="console.log('selected new location')">
                        <option></option>
                        <option>Fontys Rachelsmolen</option>
                        <option>Klokgebouw</option>
                    </select>
                    <input type="button" class="soflow-btn" id="btn-add-location" value="+" onclick="console.log('function to add location')" />
                </div>
                <div class="menu-item">
                    <!-- show the floors and an option to add a new floor -->
                    <span class="soflow-title">Floors</span>
                    <br>
                    <select class="soflow" id="option-floor" onchange="console.log('selected new floor')">
                        <option></option>
                        <option>Begane grond</option>
                        <option>Verdieping 1</option>
                    </select>
                    <input type="button" class="soflow-btn" id="btn-add-floor" value="+" onclick="console.log('function to add floor')" />
                </div>
                    <div class="menu-item-title">
                        <span class="soflow-title">Floor items</span>
                    </div>
                <div id="menu-items" class="menu-item">
                    <!-- show selectable rooms, tables, walls, etc. -->
                    <!-- dynamic divs being added called "floor-item" -->
                    <div class="radio-wrapper-s">
                        <div class="floor-item">
                            <input id="none" type="radio" name="floor-items" value="none" checked>
                            <label class="label-default" for="none">none</label>
                        </div>
                        <div class="floor-item">
                            <input id="wall1" type="radio" name="floor-items" value="wall_north">
                            <label class="label-default" for="wall1">wall_north</label>
                        </div>
                        <div class="floor-item">
                            <input id="wall2" type="radio" name="floor-items" value="wall_south">
                            <label class="label-default" for="wall2">wall_south</label>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="map-options">
                <!-- show the available options: currently limited to Create and Edit -->
                <div class="radio-wrapper-s">
                    <input id="option-create" type="radio" name="drawing-options" value="Create" onchange="mapCreationOptions()" checked>
                    <label class="label-default" for="option-create">Create</label>
                
                    <input id="option-edit" type="radio" name="drawing-options" value="Edit" onchange="mapCreationOptions()">
                    <label class="label-default" for="option-edit">Edit</label>
                </div>
                <div id="dynamic-options" class="dynamic-options">
                    <div id="nothing-selected">
                    <span class="soflow-title">Nothing yet...<span>
                </div>
                </div>
                    <div class="coordinates-display">
                    <span id="coordinates" class="soflow-title">X: 0, Y: 0</span>
                </div>
                <div class="map-container">
                    <canvas id="map" style="background-color: #F0F0F0" width="1600px" height="800px"></canvas>
                </div>
            </div>
        </div>
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
        <script src="/javascript/map/rectangle.js"></script> <!-- rectangle class -->
        <script src="/javascript/map/table.js"></script> <!-- table class -->
        <script src="/javascript/map/circle.js"></script> <!-- circle class -->
        <script src="/javascript/map/line.js"></script> <!-- line class -->
        <script src="/javascript/map/mapcreation.js"></script> <!-- mapcreation tool -->
        <script src="/javascript/map/mapoptions.js"></script> <!-- mapoptions creation tool -->
        <script src="/javascript/map/initfirebase.js"></script> <!-- initialize firebase -->
        <script src="/javascript/map/savemap.js"></script> <!-- save & load the map - firebase -->
    </body>
</html>
