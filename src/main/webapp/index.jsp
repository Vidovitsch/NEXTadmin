<!DOCTYPE html>
<!-- xlsx.js (C) 2013-present  SheetJS http://sheetjs.com -->
<!-- vim: set ts=2: -->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>JS-XLSX Live Demo</title>
    </head>
    <body>
        <input type="file" name="xlfile" id="xlf" /><br />
        <input type="button" id="test1" name="buttonparse" value="Update database" onclick="parsejson();"/><br />
        <pre id="out" name="jsontext"></pre>
        <pre id="updated" name="jsontext"></pre>
        <br />
        <!-- uncomment the next line here and in xlsxworker.js for encoding support -->
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-app.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-auth.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-database.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.2/firebase.js"></script>
        <script>
            // Initialize Firebase
            var config = {
                apiKey: "AIzaSyCRi0Ma5ekQxhwg-BfQCa6684hMzvR3Z1o",
                authDomain: "nextweek-b9a58.firebaseapp.com",
                databaseURL: "https://nextweek-b9a58.firebaseio.com",
                storageBucket: "nextweek-b9a58.appspot.com",
                messagingSenderId: "488624254338"
            };
            firebase.initializeApp(config);
        </script>
        <script src="dist/cpexcel.js"></script>
        <script src="shim.js"></script>
        <script src="jszip.js"></script>
        <script src="xlsx.js"></script>
        <script>
            /*jshint browser:true */
            /*global XLSX */
            var jsondata;
            var X = XLSX;
            var XW = {
                /* worker message */
                msg: 'xlsx',
                /* worker scripts */
                rABS: './xlsxworker2.js',
                norABS: './xlsxworker1.js',
                noxfer: './xlsxworker.js'
            };
            var rABS = typeof FileReader !== "undefined" && typeof FileReader.prototype !== "undefined" && typeof FileReader.prototype.readAsBinaryString !== "undefined";
            var use_worker = typeof Worker !== 'undefined';
            var transferable = use_worker;
            var wtf_mode = false;

            function parsejson() {
                setdatabasedata();
            }

            function setdatabasedata() {
                var updatedrecords = 0;
                var obj;
                firebase.database().ref('/User').once("value", function (snapshot) {
                    snapshot.forEach(function (childSnapshot) {
                        var mail = childSnapshot.val().Mail;
                        for (i in jsondata.Blad1) {
                            obj = jsondata.Blad1[i];
                            var groupid = -1;
                            try {
                                if (obj.GroupID == null) {
                                } else {
                                    groupid = obj.GroupID;
                                }
                            } catch (err) {
                            }
                            if (mail === obj.mail) {
                                uid = childSnapshot.key;
                                firebase.database().ref('User/' + uid).set({
                                    Mail: obj.mail,
                                    Course: obj.Lesgroep.charAt(1),
                                    Name: obj.roepnaam,
                                    Lastname: obj.achternaam,
                                    GroupID: groupid,
                                    Role: "Student",
                                    Semester: obj.Lesgroep.charAt(2),
                                    Status: "Attending"
                                });
                                updatedrecords++;
                            }
                        }
                    });
                    var updatetext = "number of records updated: " + updatedrecords;
                    if (updated.innerText === undefined)
                        updated.textContent = updatetext;
                    else
                        updated.innerText = updatetext;
                });

            }
            function fixdata(data) {
                var o = "", l = 0, w = 10240;
                for (; l < data.byteLength / w; ++l)
                    o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
                o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
                return o;
            }

            function ab2str(data) {
                var o = "", l = 0, w = 10240;
                for (; l < data.byteLength / w; ++l)
                    o += String.fromCharCode.apply(null, new Uint16Array(data.slice(l * w, l * w + w)));
                o += String.fromCharCode.apply(null, new Uint16Array(data.slice(l * w)));
                return o;
            }

            function s2ab(s) {
                var b = new ArrayBuffer(s.length * 2), v = new Uint16Array(b);
                for (var i = 0; i != s.length; ++i)
                    v[i] = s.charCodeAt(i);
                return [v, b];
            }

            function xw_noxfer(data, cb) {
                var worker = new Worker(XW.noxfer);
                worker.onmessage = function (e) {
                    switch (e.data.t) {
                        case 'ready':
                            break;
                        case 'e':
                            console.error(e.data.d);
                            break;
                        case XW.msg:
                            cb(JSON.parse(e.data.d));
                            break;
                    }
                };
                var arr = rABS ? data : btoa(fixdata(data));
                worker.postMessage({d: arr, b: rABS});
            }

            function xw_xfer(data, cb) {
                var worker = new Worker(rABS ? XW.rABS : XW.norABS);
                worker.onmessage = function (e) {
                    switch (e.data.t) {
                        case 'ready':
                            break;
                        case 'e':
                            console.error(e.data.d);
                            break;
                        default:
                            xx = ab2str(e.data).replace(/\n/g, "\\n").replace(/\r/g, "\\r");
                            console.log("done");
                            cb(JSON.parse(xx));
                            break;
                    }
                };
                if (rABS) {
                    var val = s2ab(data);
                    worker.postMessage(val[1], [val[1]]);
                } else {
                    worker.postMessage(data, [data]);
                }
            }

            function xw(data, cb) {
                if (transferable)
                    xw_xfer(data, cb);
                else
                    xw_noxfer(data, cb);
            }

            function to_json(workbook) {
                var result = {};
                workbook.SheetNames.forEach(function (sheetName) {
                    var roa = X.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
                    if (roa.length > 0) {
                        result[sheetName] = roa;
                    }
                });
                return result;
            }

            var global_wb;
            function process_wb(wb) {
                global_wb = wb;
                var output = "";
                jsondata = to_json(wb);
                output = JSON.stringify(jsondata, 2, 2);
                output = "Succesfully loaded the excel file";
                if (out.innerText === undefined)
                    out.textContent = output;
                else
                    out.innerText = output;
                if (typeof console !== 'undefined')
                    console.log("output", new Date());
            }
            function setfmt() {
                if (global_wb)
                    process_wb(global_wb);
            }

            var xlf = document.getElementById('xlf');
            function handleFile(e) {
                rABS = true;
                use_worker = true;
                var files = e.target.files;
                var f = files[0];
                {
                    var reader = new FileReader();
                    var name = f.name;
                    reader.onload = function (e) {
                        if (typeof console !== 'undefined')
                            console.log("onload", new Date(), rABS, use_worker);
                        var data = e.target.result;
                        if (use_worker) {
                            xw(data, process_wb);
                        } else {
                            var wb;
                            if (rABS) {
                                wb = X.read(data, {type: 'binary'});
                            } else {
                                var arr = fixdata(data);
                                wb = X.read(btoa(arr), {type: 'base64'});
                            }
                            process_wb(wb);
                        }
                    };
                    if (rABS)
                        reader.readAsBinaryString(f);
                    else
                        reader.readAsArrayBuffer(f);
                }
            }
            if (xlf.addEventListener)
                xlf.addEventListener('change', handleFile, false);
        </script>
    </body>
</html>
