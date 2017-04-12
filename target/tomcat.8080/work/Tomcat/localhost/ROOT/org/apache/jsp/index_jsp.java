/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.11
 * Generated at: 2017-04-12 13:08:52 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\r\n");
      out.write("<!-- xlsx.js (C) 2013-present  SheetJS http://sheetjs.com -->\r\n");
      out.write("<!-- vim: set ts=2: -->\r\n");
      out.write("<html>\r\n");
      out.write("    <head>\r\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("        <title>JS-XLSX Live Demo</title>\r\n");
      out.write("        <style>\r\n");
      out.write("            #drop{\r\n");
      out.write("                border:2px dashed #bbb;\r\n");
      out.write("                -moz-border-radius:5px;\r\n");
      out.write("                -webkit-border-radius:5px;\r\n");
      out.write("                border-radius:5px;\r\n");
      out.write("                padding:25px;\r\n");
      out.write("                text-align:center;\r\n");
      out.write("                font:20pt bold,\"Vollkorn\";color:#bbb\r\n");
      out.write("            }\r\n");
      out.write("            #b64data{\r\n");
      out.write("                width:100%;\r\n");
      out.write("            }\r\n");
      out.write("        </style>\r\n");
      out.write("    </head>\r\n");
      out.write("    <body>\r\n");
      out.write("        <b>JS-XLSX Live Demo</b><br />\r\n");
      out.write("        Output Format:\r\n");
      out.write("        <select name=\"format\" onchange=\"setfmt()\">\r\n");
      out.write("            <option value=\"csv\" selected> CSV</option>\r\n");
      out.write("            <option value=\"json\"> JSON</option>\r\n");
      out.write("            <option value=\"form\"> FORMULAE</option>\r\n");
      out.write("        </select><br />\r\n");
      out.write("\r\n");
      out.write("        <div id=\"drop\">Drop a spreadsheet file here to see sheet data</div>\r\n");
      out.write("        <p><input type=\"file\" name=\"xlfile\" id=\"xlf\" /> ... or click here to select a file</p>\r\n");
      out.write("        <textarea id=\"b64data\">... or paste a base64-encoding here</textarea>\r\n");
      out.write("        <input type=\"button\" id=\"dotext\" value=\"Click here to process the base64 text\" onclick=\"b64it();\"/><br />\r\n");
      out.write("        Advanced Demo Options: <br />\r\n");
      out.write("        Use Web Workers: (when available) <input type=\"checkbox\" name=\"useworker\" checked><br />\r\n");
      out.write("        Use Transferrables: (when available) <input type=\"checkbox\" name=\"xferable\" checked><br />\r\n");
      out.write("        Use readAsBinaryString: (when available) <input type=\"checkbox\" name=\"userabs\" checked><br />\r\n");
      out.write("        <input type=\"button\" id=\"test1\" name=\"buttonparse\" value=\"test jsonparse\" onclick=\"parsejson();\"/><br />\r\n");
      out.write("        <pre id=\"out\" name=\"jsontext\"></pre>\r\n");
      out.write("        <br />\r\n");
      out.write("        <!-- uncomment the next line here and in xlsxworker.js for encoding support -->\r\n");
      out.write("        <script src=\"https://www.gstatic.com/firebasejs/3.7.1/firebase-app.js\"></script>\r\n");
      out.write("        <script src=\"https://www.gstatic.com/firebasejs/3.7.1/firebase-auth.js\"></script>\r\n");
      out.write("        <script src=\"https://www.gstatic.com/firebasejs/3.7.1/firebase-database.js\"></script>\r\n");
      out.write("        <script src=\"https://www.gstatic.com/firebasejs/3.7.2/firebase.js\"></script>\r\n");
      out.write("        <script>\r\n");
      out.write("            // Initialize Firebase\r\n");
      out.write("            var config = {\r\n");
      out.write("                apiKey: \"AIzaSyCRi0Ma5ekQxhwg-BfQCa6684hMzvR3Z1o\",\r\n");
      out.write("                authDomain: \"nextweek-b9a58.firebaseapp.com\",\r\n");
      out.write("                databaseURL: \"https://nextweek-b9a58.firebaseio.com\",\r\n");
      out.write("                storageBucket: \"nextweek-b9a58.appspot.com\",\r\n");
      out.write("                messagingSenderId: \"488624254338\"\r\n");
      out.write("            };\r\n");
      out.write("            firebase.initializeApp(config);\r\n");
      out.write("        </script>\r\n");
      out.write("        <script>\r\n");
      out.write("            < script src = \"dist/cpexcel.js\" ></script>\r\n");
      out.write("        <script src=\"shim.js\"></script>\r\n");
      out.write("        <script src=\"jszip.js\"></script>\r\n");
      out.write("        <script src=\"xlsx.js\"></script>\r\n");
      out.write("        <script>\r\n");
      out.write("            /*jshint browser:true */\r\n");
      out.write("            /*global XLSX */\r\n");
      out.write("            var jsondata;\r\n");
      out.write("            var X = XLSX;\r\n");
      out.write("            var XW = {\r\n");
      out.write("                /* worker message */\r\n");
      out.write("                msg: 'xlsx',\r\n");
      out.write("                /* worker scripts */\r\n");
      out.write("                rABS: './xlsxworker2.js',\r\n");
      out.write("                norABS: './xlsxworker1.js',\r\n");
      out.write("                noxfer: './xlsxworker.js'\r\n");
      out.write("            };\r\n");
      out.write("\r\n");
      out.write("            var rABS = typeof FileReader !== \"undefined\" && typeof FileReader.prototype !== \"undefined\" && typeof FileReader.prototype.readAsBinaryString !== \"undefined\";\r\n");
      out.write("            if (!rABS) {\r\n");
      out.write("                document.getElementsByName(\"userabs\")[0].disabled = true;\r\n");
      out.write("                document.getElementsByName(\"userabs\")[0].checked = false;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            var use_worker = typeof Worker !== 'undefined';\r\n");
      out.write("            if (!use_worker) {\r\n");
      out.write("                document.getElementsByName(\"useworker\")[0].disabled = true;\r\n");
      out.write("                document.getElementsByName(\"useworker\")[0].checked = false;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            var transferable = use_worker;\r\n");
      out.write("            if (!transferable) {\r\n");
      out.write("                document.getElementsByName(\"xferable\")[0].disabled = true;\r\n");
      out.write("                document.getElementsByName(\"xferable\")[0].checked = false;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            var wtf_mode = false;\r\n");
      out.write("\r\n");
      out.write("            function parsejson() {\r\n");
      out.write("                setdatabasedata();\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function setdatabasedata(){\r\n");
      out.write("                var obj;\r\n");
      out.write("            firebase.database().ref('/User').once(\"value\", function (snapshot) {\r\n");
      out.write("                snapshot.forEach(function (childSnapshot) {\r\n");
      out.write("                    var mail = childSnapshot.val().Mail;\r\n");
      out.write("                    for (i in jsondata.Blad1) {\r\n");
      out.write("                        obj = jsondata.Blad1[i];\r\n");
      out.write("                        if (mail === obj.mail) {\r\n");
      out.write("                            uid = childSnapshot.key;\r\n");
      out.write("                            firebase.database().ref('User/' + uid).set({\r\n");
      out.write("                                Mail: obj.mail,\r\n");
      out.write("                                Course: obj.Lesgroep.charAt(1),\r\n");
      out.write("                                Name: obj.roepnaam,\r\n");
      out.write("                                Role: \"Student\",\r\n");
      out.write("                                Semester: obj.Lesgroep.charAt(2),\r\n");
      out.write("                                Status: \"Attending\"\r\n");
      out.write("                            });\r\n");
      out.write("                        }\r\n");
      out.write("                    }\r\n");
      out.write("                });\r\n");
      out.write("            });\r\n");
      out.write("            }\r\n");
      out.write("            function fixdata(data) {\r\n");
      out.write("                var o = \"\", l = 0, w = 10240;\r\n");
      out.write("                for (; l < data.byteLength / w; ++l)\r\n");
      out.write("                    o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));\r\n");
      out.write("                o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));\r\n");
      out.write("                return o;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function ab2str(data) {\r\n");
      out.write("                var o = \"\", l = 0, w = 10240;\r\n");
      out.write("                for (; l < data.byteLength / w; ++l)\r\n");
      out.write("                    o += String.fromCharCode.apply(null, new Uint16Array(data.slice(l * w, l * w + w)));\r\n");
      out.write("                o += String.fromCharCode.apply(null, new Uint16Array(data.slice(l * w)));\r\n");
      out.write("                return o;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function s2ab(s) {\r\n");
      out.write("                var b = new ArrayBuffer(s.length * 2), v = new Uint16Array(b);\r\n");
      out.write("                for (var i = 0; i != s.length; ++i)\r\n");
      out.write("                    v[i] = s.charCodeAt(i);\r\n");
      out.write("                return [v, b];\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function xw_noxfer(data, cb) {\r\n");
      out.write("                var worker = new Worker(XW.noxfer);\r\n");
      out.write("                worker.onmessage = function (e) {\r\n");
      out.write("                    switch (e.data.t) {\r\n");
      out.write("                        case 'ready':\r\n");
      out.write("                            break;\r\n");
      out.write("                        case 'e':\r\n");
      out.write("                            console.error(e.data.d);\r\n");
      out.write("                            break;\r\n");
      out.write("                        case XW.msg:\r\n");
      out.write("                            cb(JSON.parse(e.data.d));\r\n");
      out.write("                            break;\r\n");
      out.write("                    }\r\n");
      out.write("                };\r\n");
      out.write("                var arr = rABS ? data : btoa(fixdata(data));\r\n");
      out.write("                worker.postMessage({d: arr, b: rABS});\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function xw_xfer(data, cb) {\r\n");
      out.write("                var worker = new Worker(rABS ? XW.rABS : XW.norABS);\r\n");
      out.write("                worker.onmessage = function (e) {\r\n");
      out.write("                    switch (e.data.t) {\r\n");
      out.write("                        case 'ready':\r\n");
      out.write("                            break;\r\n");
      out.write("                        case 'e':\r\n");
      out.write("                            console.error(e.data.d);\r\n");
      out.write("                            break;\r\n");
      out.write("                        default:\r\n");
      out.write("                            xx = ab2str(e.data).replace(/\\n/g, \"\\\\n\").replace(/\\r/g, \"\\\\r\");\r\n");
      out.write("                            console.log(\"done\");\r\n");
      out.write("                            cb(JSON.parse(xx));\r\n");
      out.write("                            break;\r\n");
      out.write("                    }\r\n");
      out.write("                };\r\n");
      out.write("                if (rABS) {\r\n");
      out.write("                    var val = s2ab(data);\r\n");
      out.write("                    worker.postMessage(val[1], [val[1]]);\r\n");
      out.write("                } else {\r\n");
      out.write("                    worker.postMessage(data, [data]);\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function xw(data, cb) {\r\n");
      out.write("                transferable = document.getElementsByName(\"xferable\")[0].checked;\r\n");
      out.write("                if (transferable)\r\n");
      out.write("                    xw_xfer(data, cb);\r\n");
      out.write("                else\r\n");
      out.write("                    xw_noxfer(data, cb);\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function get_radio_value(radioName) {\r\n");
      out.write("                var radios = document.getElementsByName(radioName);\r\n");
      out.write("                for (var i = 0; i < radios.length; i++) {\r\n");
      out.write("                    if (radios[i].checked || radios.length === 1) {\r\n");
      out.write("                        return radios[i].value;\r\n");
      out.write("                    }\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function to_json(workbook) {\r\n");
      out.write("                var result = {};\r\n");
      out.write("                workbook.SheetNames.forEach(function (sheetName) {\r\n");
      out.write("                    var roa = X.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);\r\n");
      out.write("                    if (roa.length > 0) {\r\n");
      out.write("                        result[sheetName] = roa;\r\n");
      out.write("                    }\r\n");
      out.write("                });\r\n");
      out.write("                return result;\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function to_csv(workbook) {\r\n");
      out.write("                var result = [];\r\n");
      out.write("                workbook.SheetNames.forEach(function (sheetName) {\r\n");
      out.write("                    var csv = X.utils.sheet_to_csv(workbook.Sheets[sheetName]);\r\n");
      out.write("                    if (csv.length > 0) {\r\n");
      out.write("                        result.push(\"SHEET: \" + sheetName);\r\n");
      out.write("                        result.push(\"\");\r\n");
      out.write("                        result.push(csv);\r\n");
      out.write("                    }\r\n");
      out.write("                });\r\n");
      out.write("                return result.join(\"\\n\");\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function to_formulae(workbook) {\r\n");
      out.write("                var result = [];\r\n");
      out.write("                workbook.SheetNames.forEach(function (sheetName) {\r\n");
      out.write("                    var formulae = X.utils.get_formulae(workbook.Sheets[sheetName]);\r\n");
      out.write("                    if (formulae.length > 0) {\r\n");
      out.write("                        result.push(\"SHEET: \" + sheetName);\r\n");
      out.write("                        result.push(\"\");\r\n");
      out.write("                        result.push(formulae.join(\"\\n\"));\r\n");
      out.write("                    }\r\n");
      out.write("                });\r\n");
      out.write("                return result.join(\"\\n\");\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            var tarea = document.getElementById('b64data');\r\n");
      out.write("            function b64it() {\r\n");
      out.write("                if (typeof console !== 'undefined')\r\n");
      out.write("                    console.log(\"onload\", new Date());\r\n");
      out.write("                var wb = X.read(tarea.value, {type: 'base64', WTF: wtf_mode});\r\n");
      out.write("                process_wb(wb);\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            var global_wb;\r\n");
      out.write("            function process_wb(wb) {\r\n");
      out.write("                global_wb = wb;\r\n");
      out.write("                var output = \"\";\r\n");
      out.write("                switch (get_radio_value(\"format\")) {\r\n");
      out.write("                    case \"json\":\r\n");
      out.write("                        jsondata = to_json(wb);\r\n");
      out.write("                        output = JSON.stringify(jsondata, 2, 2);\r\n");
      out.write("                        break;\r\n");
      out.write("                    case \"form\":\r\n");
      out.write("                        output = to_formulae(wb);\r\n");
      out.write("                        break;\r\n");
      out.write("                    default:\r\n");
      out.write("                        output = to_csv(wb);\r\n");
      out.write("                }\r\n");
      out.write("                if (out.innerText === undefined)\r\n");
      out.write("                    out.textContent = output;\r\n");
      out.write("                else\r\n");
      out.write("                    out.innerText = output;\r\n");
      out.write("                if (typeof console !== 'undefined')\r\n");
      out.write("                    console.log(\"output\", new Date());\r\n");
      out.write("            }\r\n");
      out.write("            function setfmt() {\r\n");
      out.write("                if (global_wb)\r\n");
      out.write("                    process_wb(global_wb);\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            var drop = document.getElementById('drop');\r\n");
      out.write("            function handleDrop(e) {\r\n");
      out.write("                e.stopPropagation();\r\n");
      out.write("                e.preventDefault();\r\n");
      out.write("                rABS = document.getElementsByName(\"userabs\")[0].checked;\r\n");
      out.write("                use_worker = document.getElementsByName(\"useworker\")[0].checked;\r\n");
      out.write("                var files = e.dataTransfer.files;\r\n");
      out.write("                var f = files[0];\r\n");
      out.write("                {\r\n");
      out.write("                    var reader = new FileReader();\r\n");
      out.write("                    var name = f.name;\r\n");
      out.write("                    reader.onload = function (e) {\r\n");
      out.write("                        if (typeof console !== 'undefined')\r\n");
      out.write("                            console.log(\"onload\", new Date(), rABS, use_worker);\r\n");
      out.write("                        var data = e.target.result;\r\n");
      out.write("                        if (use_worker) {\r\n");
      out.write("                            xw(data, process_wb);\r\n");
      out.write("                        } else {\r\n");
      out.write("                            var wb;\r\n");
      out.write("                            if (rABS) {\r\n");
      out.write("                                wb = X.read(data, {type: 'binary'});\r\n");
      out.write("                            } else {\r\n");
      out.write("                                var arr = fixdata(data);\r\n");
      out.write("                                wb = X.read(btoa(arr), {type: 'base64'});\r\n");
      out.write("                            }\r\n");
      out.write("                            process_wb(wb);\r\n");
      out.write("                        }\r\n");
      out.write("                    };\r\n");
      out.write("                    if (rABS)\r\n");
      out.write("                        reader.readAsBinaryString(f);\r\n");
      out.write("                    else\r\n");
      out.write("                        reader.readAsArrayBuffer(f);\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            function handleDragover(e) {\r\n");
      out.write("                e.stopPropagation();\r\n");
      out.write("                e.preventDefault();\r\n");
      out.write("                e.dataTransfer.dropEffect = 'copy';\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            if (drop.addEventListener) {\r\n");
      out.write("                drop.addEventListener('dragenter', handleDragover, false);\r\n");
      out.write("                drop.addEventListener('dragover', handleDragover, false);\r\n");
      out.write("                drop.addEventListener('drop', handleDrop, false);\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("            var xlf = document.getElementById('xlf');\r\n");
      out.write("            function handleFile(e) {\r\n");
      out.write("                rABS = document.getElementsByName(\"userabs\")[0].checked;\r\n");
      out.write("                use_worker = document.getElementsByName(\"useworker\")[0].checked;\r\n");
      out.write("                var files = e.target.files;\r\n");
      out.write("                var f = files[0];\r\n");
      out.write("                {\r\n");
      out.write("                    var reader = new FileReader();\r\n");
      out.write("                    var name = f.name;\r\n");
      out.write("                    reader.onload = function (e) {\r\n");
      out.write("                        if (typeof console !== 'undefined')\r\n");
      out.write("                            console.log(\"onload\", new Date(), rABS, use_worker);\r\n");
      out.write("                        var data = e.target.result;\r\n");
      out.write("                        if (use_worker) {\r\n");
      out.write("                            xw(data, process_wb);\r\n");
      out.write("                        } else {\r\n");
      out.write("                            var wb;\r\n");
      out.write("                            if (rABS) {\r\n");
      out.write("                                wb = X.read(data, {type: 'binary'});\r\n");
      out.write("                            } else {\r\n");
      out.write("                                var arr = fixdata(data);\r\n");
      out.write("                                wb = X.read(btoa(arr), {type: 'base64'});\r\n");
      out.write("                            }\r\n");
      out.write("                            process_wb(wb);\r\n");
      out.write("                        }\r\n");
      out.write("                    };\r\n");
      out.write("                    if (rABS)\r\n");
      out.write("                        reader.readAsBinaryString(f);\r\n");
      out.write("                    else\r\n");
      out.write("                        reader.readAsArrayBuffer(f);\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("            if (xlf.addEventListener)\r\n");
      out.write("                xlf.addEventListener('change', handleFile, false);\r\n");
      out.write("        </script>\r\n");
      out.write("    </body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
