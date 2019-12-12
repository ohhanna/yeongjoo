<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
   <!-- Bootstrap Core CSS -->
   <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
   <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
   
<head>
<meta charset="UTF-8">
   <!-- jQuery -->
    <script src="/resources/vendor/jquery/jquery.min.js"></script>
<title>CUSTOM LOGIN</title>
</head>
<body>
   <c:if test="${error != null }">
      <h4 style="color:red">ERROR : ${error }</h4>
   </c:if>
   <c:if test="${logout != null }">
      <h4 style="color:red">LOGOUT : ${logout }</h4>
   </c:if>
   
   <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" action="/login" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="User ID" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember-me" type="checkbox">Remember Me
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <a href="index.html" class="btn btn-lg btn-success btn-block">Login</a>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
       $(function(){
          $(".btn-success").on("click", function(e){
             e.preventDefault();
             $("form").submit();
          });
       });
    </script>

    
    <!-- Bootstrap Core JavaScript -->
    <script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/resources/dist/js/sb-admin-2.js"></script>

</body>
</html>