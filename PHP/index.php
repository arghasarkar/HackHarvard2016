<!DOCTYPE html>
<html lang="en">
<head>
  
  <title> WORLDESQUE</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
  body {
      font: 20px Montserrat, sans-serif;
      line-height: 1.8;
      color: #6666FF;
      align-content: center;

  }
  img {
  	align-content: center;

  }

  p {font-size: 16px;}
  .margin {margin-bottom: 45px;}
  .bg-1 {
      background-color: #6666FF;
      color: #fff;
  }
  .bg-2 {
      background-color: #474e5d; /* Dark Blue */
      color: #ffffff;
  }
  .bg-3 {
      background-color: #ffffff; /* White */
      color: #555555;
  }
  .bg-4 {
      background-color: #2f2f2f; /* Black Gray */
      color: #fff;
  }
  .container-fluid {
      padding-top: 70px;
      padding-bottom: 70px;
  }
  
  </style>
</head>
<body>



<!-- First Container -->
<div class="container-fluid bg-1 text-center">
  <h1 class="margin">WORLDESQUE</h1>
  <img src="words.png" class="img-responsive" style="display:inline" alt="words" width="800" height="800">
  <h3><i>Because communication matters!</i></h3>
</div>

<!-- Second Container -->
<div class="container-fluid bg-2 text-center">
  <h3 class="margin">Who are we?</h3>
  <p>We are a group of students aiming to do simple things in helping others.</p>
  <p> We love communication and we want to bring you closer to everyone, closer to the world. Because we makes things easier!</p>
  <p> We are the solution for making more friends! We help you talk to them in the same language!</p>
  <p> And as they say, true friends talk the same language </p>
  <p> Give it a try! </p>
  
</div>

<!-- Third Container (Grid) -->
<div class="container-fluid bg-3 text-center">
  <h1 class="margin"> Process </h1><br>
  <div class="row">
    <div class="col-sm-4">
      <p>To use our products you have to point your front camera to the subject of the photo and take a photo.</p>
      
    </div>

    <div class="col-sm-4">
      <p>Wait for it... </p>
      
    </div>
    <div class="col-sm-4">
      <p>We give you the text translation followed by a funny rendition off a set of images. </p>
      
    </div>
  </div>
</div>


<form action="upload.php" method="post" enctype="multipart/form-data">
    Select image to upload:
    <input type="file" name="fileToUpload" id="fileToUpload">
    <input type="submit" value="Upload Image" name="submit">

</form>


</div>


</body>
</html>