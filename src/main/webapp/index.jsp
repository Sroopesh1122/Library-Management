<html>
<body>
<h2>Hello World!</h2>
<form action="<%=request.getContextPath()+"/img/upload" %>" method="post" enctype="multipart/form-data" >
 <input type="file" name="img">
 <input type="text" name="name">
 <button type="submit">Submit</button>
</form>
</body>
</html>
