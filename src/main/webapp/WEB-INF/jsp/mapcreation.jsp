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
            <div class="radio-wrapper-s">
                <input id="option-draw" type="radio" name="drawing-options" value="Draw" onchange="mapCreationOptions()" checked>
                <label class="label-default" for="option-draw">Draw</label>
                
		<input id="option-resize" type="radio" name="drawing-options" value="Resize" onchange="mapCreationOptions()">
		<label class="label-default" for="option-resize">Resize</label>
			
		<input id="option-modify" type="radio" name="drawing-options" value="Modify" onchange="mapCreationOptions()">
		<label class="label-default" for="option-modify">Modify</label>
                 		
		<input id="option-file" type="radio" name="drawing-options" value="File" onchange="mapCreationOptions()">
		<label class="label-default" for="option-file">File</label>
            </div>

            <div id="dynamic_options" style="height:100px">
                Nothing yet...
            </div>
            <div style="radio-wrapper-s">
                <p id="coordinates" style="display: table-cell">
                    <b>X:</b> 0, <b>Y: </b> 0
                </p>
            </div>
            <canvas id="map" style="background-color: #F0F0F0"></canvas>
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
