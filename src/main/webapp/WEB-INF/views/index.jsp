<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Social</title>
  <jsp:include page="parts/common_resources.jsp"/>

</head>

<body ng-controller="AppController">


      <jsp:include page="parts/left_bar.jsp"/>
      <jsp:include page="parts/right_bar.jsp"/>
      <div ng-view class="col-md-8 col-md-offset-2 main-container" >

      </div>
</body>
</html>

