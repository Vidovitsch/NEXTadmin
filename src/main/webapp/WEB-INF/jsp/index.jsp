<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%! int fontSize;%> 
<%! int maxfontSize = 3;%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <spring:url value="/css/index.css" var="mainCSS" />
        <link href="${mainCSS}" rel="stylesheet" />
    </head>
    <body>
        <div class="wrapper">
            <div class="leftpart">
                <div id='createassigmentpart' class='middlepartcontainer'>
                    <h3>Create Assignment</h3>
                    <input type='text' id='createassignmentname' class='createassignmentname middlepartitem' name='createassignmentname' placeholder='Specify assignment name...'>
                    <textarea rows="4" cols="50" name="createassignmentdescription" class="createassignmentdescription middlepartitem" id="createassignmentdescription" placeholder="Description"></textarea>
                    <button class="button_base b01_simple_rollover middlepartitem middleparthalfbutton" id="buttoncreateassignment" onclick="updateassignment()">Submit</button>
                    <button class="button_base b01_simple_rollover middlepartitem middleparthalfbutton" id="buttonremoveassignment" onclick="removeselectedassignment()">Remove</button>
                    <span class="assignments">Assignments</span>
                    <ol id="assignmentlist" class="borderedlist" type="1">
                    </ol>
                    <span class="assignmentname">Submissions</span>
                    <ol id="submissionlist" class="borderedlist" type="1">
                    </ol>
                </div>
            </div>
            <div class="middlepart">
                <div id='updatestudentdatapart' class='middlepartcontainer'>
                    <h3>Update student data</h3>
                    <label class="button_base b01_simple_rollover middlepartitem buttonbrowseexcel">Browse<input type="file" name="xlfile" id="xlf" style="display: none;"></input>
                    </label>
                    <input class="button_base b01_simple_rollover buttonupdatedatabase middlepartitem" disabled type="button" id="buttonupdatedatabase" name="buttonparse" value="Update database" onclick="parsejson();"/><br />
                </div>
                <div id='updateallgroups' class='middlepartcontainer'>
                    <h3>Update the user groups</h3>
                    Mix courses:<br>
                    <input type="radio" id="rbMixGroups" name="mixGroups" value="Yes"> Yes<br>
                    <input type="radio" id="rbDoNotMixGroups" name="mixGroups" checked="checked" value="No"> No<br>
                    Amount of students per group: <input type='number' id='studentsPerGroup' class='studentsPerGroup middlepartitem' name='studentsPerGroup' value=10>
                    <input class="button_base b01_simple_rollover middlepartitem" type="button" id="buttonAllocatieStudents" value="Allocate all users" onclick="allocateAllStudents()"/>
                    <input class="button_base b01_simple_rollover middlepartitem" type="button" id="buttonResetGroups" value="Reset all groups" onclick="resetAllGroups()"/>
                </div>
            </div>
            <div class="rightpart">
                <div id='createaccountpart' class='middlepartcontainer'>
                    <h3>Create PiE-Account</h3>
                    <input type='text' id='createaccountname' class='createaccountname middlepartitem' name='createaccountname' placeholder='Specify account name...'>
                    <button class="button_base b01_simple_rollover middlepartitem" id="buttoncreateaccount" onclick="createaccount()">Create account</button></br>
                </div>
                <button class="button_base b01_simple_rollover buttonremovelog" type="button" id="buttonhidelog" name="buttonhidelog" value="Hide log" onclick="hidelog()"/>Hide log</button>
            </div>
        </div>
        <textarea readonly id="adminlog" class="adminlog" rows="10" cols="70">Log:&#13;&#10;</textarea>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-app.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-auth.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.1/firebase-database.js"></script>
        <script src="https://www.gstatic.com/firebasejs/3.7.2/firebase.js"></script>
        <script src="initfirebase.js"></script> <!-- initialize firebase -->
        <script src="dist/cpexcel.js"></script>
        <script src="shim.js"></script>
        <script src="jszip.js"></script>
        <script src="xlsx.js"></script>
        <script>
                    /*jshint browser:true */
                    /*global XLSX */
                    var clipboardstudents = "";
                    var selectedstudent = "";
                    var selectedassignmentname = "";
                    var studentlist = [];
                    var assignmentlist = [];
                    function updatelog(logtext)
                    {
                        document.getElementById('adminlog').innerHTML += logtext + "&#13;&#10;";
                    }

                    function hidelog() {
                        if (document.getElementById("adminlog").style.visibility == "hidden")
                        {
                            showlog();
                        } else
                        {
                            document.getElementById("adminlog").style.visibility = "hidden";
                        }
                    }

                    function showlog() {
                        document.getElementById("adminlog").style.visibility = "visible";
                    }

                    function getEventTarget(e) {
                        e = e || window.event;
                        return e.target || e.srcElement;
                    }

                    var olassignments = document.getElementById('assignmentlist');
                    olassignments.onclick = function (event) {
                        var target = getEventTarget(event);
                        updateselectedassignment(target.innerHTML);
                        showsubmissionsassignment();
                    };
                    function updateselectedassignment(assignmentname) {
                        selectedassignmentname = assignmentname;
                        var arrayLength = assignmentlist.length;
                        for (var i = 0; i < arrayLength; i++) {
                            if (assignmentlist[i].name === assignmentname)
                            {
                                document.getElementsByName("createassignmentname")[0].value = assignmentlist[i].name;
                                document.getElementsByName("createassignmentdescription")[0].value = assignmentlist[i].description;
                            }
                        }
                    }

                    function createaccount() {
                        var emailvalue = document.getElementsByName("createaccountname")[0].value;
                        updatelog('creating account with email: ' + emailvalue);
                        firebase.auth().createUserWithEmailAndPassword(emailvalue, "temp123").then(function (user) {
                            updatelog('Succesfully created user: ' + user.email);
                            sendresetmail(user.email);
                        }).catch(function (error) {
                            var errorMessage = error.message;
                            if (errorMessage === "A network error (such as timeout, interrupted connection or unreachable host) has occurred.") {
                            } else {
                                updatelog(errorMessage);
                            }
                        });
                    }

                    function sendresetmail(email) {
                        firebase.auth().sendPasswordResetEmail(email).then(function () {
                            updatelog("Mail voor passwordreset verstuurd");
                        }, function (error) {
                            var errorMessage = error.message;
                            if (errorMessage === "A network error (such as timeout, interrupted connection or unreachable host) has occurred.") {
                            } else {
                                alert(errorMessage);
                            }
                        });
                    }

                    function setassignmentlist() {
                        var updatedrecords = 0;
                        var assigmenthtml;
                        var assignmentname;
                        assignmentlist = [];
                        document.getElementById('assignmentlist').innerHTML = "";
                        firebase.database().ref('/Assignment').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                assignmentname = childSnapshot.key;
                                assignmentdsc = childSnapshot.val().Description;
                                assignment = {
                                    name: assignmentname,
                                    description: assignmentdsc}
                                assignmentlist.push(assignment);
                                updatedrecords++;
                                assigmenthtml = "<li><a href='#'>" + assignmentname + "</li>";
                                document.getElementById('assignmentlist').innerHTML += assigmenthtml;
                            });
                            var updatetext = updatedrecords + " assignments loaded";
                            updatelog(updatetext);
                        });
                    }
                    setassignmentlist();
                    function updateassignment() {
                        var assignment;
                        var assignmentname = document.getElementsByName("createassignmentname")[0].value;
                        var assignmentdescription = document.getElementsByName("createassignmentdescription")[0].value;
                        assignment = {
                            name: assignmentname,
                            description: assignmentdescription};
                        firebase.database().ref('Assignment/' + assignment.name).set({
                            Description: assignment.description
                        }).then(finishassignmentupdate(assignmentname));
                    }

                    function removeselectedassignment() {
                        var assignmentname = document.getElementsByName("createassignmentname")[0].value;
                        if (assignmentname.length > 0)
                        {
                            firebase.database().ref('Assignment/' + assignmentname).remove().then(finishassigmentremoval(assignmentname));
                        } else
                        {
                            alert("No assignment selected");
                        }
                    }

                    function showsubmissionsassignment() {
                        var assignmentname = document.getElementsByName("createassignmentname")[0].value;
                        if (assignmentname.length > 0)
                        {
                            showPopup(assignmentname);
                        } else
                        {
                            alert("No assignment selected");
                        }
                    }


                    function showPopup(assignmentname)
                    {
                        document.getElementById('submissionlist').innerHTML = ""
                        document.getElementById('submissionlist').innerHTML +=
                                '<div id="event" class="overlay"> \
                                <div class="popup"> \
                                        <div id="popup-wrapper-assignment"> \
                                            <div class="content">';

                        var dataref = '/Assignment/' + assignmentname + '/Submissions';
                        firebase.database().ref(dataref).once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                var submissionLink = childSnapshot.val().Link;
                                var submissionName = childSnapshot.val().Name;
                                assigmenthtml = "<li><a href='#'>" + submissionName + " : " + submissionLink + "</li>";
                                document.getElementById('submissionlist').innerHTML += assigmenthtml;
                            })

                        });
                        document.getElementById('submissionlist').innerHTML +=
                                '</div> \
                                        </div> \
                                </div> \
                            </div>';
                    }



                    function finishassigmentremoval(assignmentname) {
                        updatelog(assignmentname + " has been removed");
                        document.getElementsByName("createassignmentname")[0].value = "";
                        document.getElementsByName("createassignmentdescription")[0].value = "";
                        setassignmentlist();
                    }

                    function finishassignmentupdate(assignmentname)
                    {
                        updatelog(assignmentname + " has been updated/created");
                        setassignmentlist();
                    }

                    function allocateAllStudents()
                    {
                        if (confirm('Are you sure you want to create groups for all unnasigned students?')) {
                            var studentsPerGroup = document.getElementById('studentsPerGroup').value;
                            if (studentsPerGroup > 0) {
                                if (document.getElementById('rbMixGroups').checked) {
                                    post('createGroups', {studentsPerGroup: studentsPerGroup, mixGroups: true});
                                }
                                if (document.getElementById('rbDoNotMixGroups').checked) {
                                    post('createGroups', {studentsPerGroup: studentsPerGroup, mixGroups: false});
                                }
                            } else {
                                alert('Please fill in a number greater then 0 for students per group.');
                            }
                        }
                    }

                    function resetAllGroups()
                    {
                        if (confirm('Are you sure that you want to reset ALL user groups?')) {
                            window.location.href = 'resetGroups';
                        }
                    }

                    function post(path, params, method) {
                        method = method || "post"; // Set method to post by default if not specified.
                        // The rest of this code assumes you are not using a library.
                        // It can be made less wordy if you use one.
                        var form = document.createElement("form");
                        form.setAttribute("method", method);
                        form.setAttribute("action", path);
                        for (var key in params) {
                            if (params.hasOwnProperty(key)) {
                                var hiddenField = document.createElement("input");
                                hiddenField.setAttribute("type", "hidden");
                                hiddenField.setAttribute("name", key);
                                hiddenField.setAttribute("value", params[key]);
                                form.appendChild(hiddenField);
                            }
                        }
                        document.body.appendChild(form);
                        form.submit();
                    }
                    ;

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
                    var groupid = -1;
                    function setdatabasedata() {
                        var updatedrecords = 0;
                        var obj;

                        firebase.database().ref('/User').once("value", function (snapshot) {
                            snapshot.forEach(function (childSnapshot) {
                                var mail = childSnapshot.val().Mail;
                                for (i in jsondata.Blad1) {
                                    obj = jsondata.Blad1[i];
                                    if (mail == obj.mail) {
                                        try {
                                            groupid = childSnapshot.val().GroupID;
                                        } catch (err) {
                                        }
                                        uid = childSnapshot.key;
                                        firebase.database().ref('User/' + uid).set({
                                            Mail: obj.mail,
                                            Course: obj.Lesgroep.charAt(1),
                                            Name: obj.roepnaam,
                                            Lastname: obj.achternaam,
                                            GroupID: groupid,
                                            Role: "Student",
                                            Semester: obj.Lesgroep.charAt(2),
                                            Status: "Inactive",
                                            Submitted: 0
                                        });
                                        updatedrecords++;
                                    }
                                }
                            });
                            var updatetext = "number of records updated: " + updatedrecords;
                            updatelog(updatetext);
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
                        jsondata = to_json(wb);
                        updatelog("Succesfully loaded the excel file");
                        document.getElementById('buttonupdatedatabase').style.backgroundColor = "green";
                        document.getElementById('buttonupdatedatabase').disabled = false;
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
