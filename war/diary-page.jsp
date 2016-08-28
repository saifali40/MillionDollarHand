<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>

<head>
    <title>Personal Diary | index.html</title>
	<meta charset="utf-8" />
	<link href="assets/css/bootstrap.css" rel="stylesheet" />
    <link href="assets/css/pe-icon-7-stroke.css" rel="stylesheet" />
    <link href="assets/css/ct-navbar.css" rel="stylesheet" />
    <link rel="icon" type="image/png" href="assets/img/cloud.png">
    <link href="assets/css/bootstrap-datepicker.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" crossorigin="anonymous">
    <script src="assets/js/tinymce/tinymce.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
 	<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.12/datatables.min.js"></script>
    <script src="assets/js/bootstrap-datepicker.js" type="text/javascript"></script>
    <script src="assets/js/assets.js"></script>
 	
	
    <body>

<div id="navbar-full">
    <div id="navbar">
       <!--    
        navbar-default can be changed with navbar-ct-blue navbar-ct-azzure navbar-ct-red navbar-ct-green navbar-ct-orange  
        -->
        <nav class="navbar navbar-ct-blue navbar-fixed-top" role="navigation">
          
          <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand navbar-brand-logo" href="#">
                    <div class="logo">
                    <img src="assets/img/logo.png" width="60px">
                    </div>
                    <div class="brand"> Cloud Diaries </div>
              </a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
              <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="javascript:void(0);" data-toggle="search" class="hidden-xs">
                            <i class="pe-7s-search"></i>
                            <p>Search</p>
                        </a>
                    </li>
                    
                    <li>
                        <a href="#/new" data-toggle="Add new" class="hidden-xs">
                            <i class="pe-7s-plus"></i>
                            <p>AddNew</p>
                        </a>
                    </li>
                    
                    <li class="dropdown">
                          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="pe-7s-user"></i>
                                <p>Account</p>
                            </a>
                          <ul class="dropdown-menu">
                            <li><a href="#">Action</a></li>
                            <li><a href="#">Another action</a></li>
                            <li><a href="#">Something</a></li>
                            <li><a href="#">Another action</a></li>
                            <li><a href="#">Something</a></li>
                            <li class="divider"></li>
                            <li><a href="Diary/Logout">Logout</a></li>
                          </ul>
                    </li>
               </ul>
               <form class="navbar-form navbar-right navbar-search-form" role="search" id="live-search">                  
                 <div class="form-group">
                 <fieldset>
                      <input type="text" value="" class="form-control" placeholder="Search..." id="filter">
                      <span id="filter-count"></span>
      				</fieldset>
                 </div> 
              </form>
              
            </div><!-- /.navbar-collapse -->
          </div><!-- /.container-fluid -->
        </nav>
      
    </div><!--  end navbar -->

</div> <!-- end menu-dropdown -->





        <div class="container" id="container">

            <div class="row" style="">
                <div class="col-md-12" id="primary-content" style="margin-top:100px">
                </div>

            </div>
        </div>

        <!-- THE JAVASCRIPT TEMPLATES -->
        <!-- the form, used for create and edit -->




        <script type="text/jst" id="formTemplate">
            <div class="well" style="margin-top:10px;box-shadow:0 10px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19) !important;" div="scroll">
                <div class="alert alert-danger fade in" style="display:none;margin-top:10px;box-shadow:0 10px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19) !important;"></div>
                <form class="add-entry">
                    <div class="form-group">
                        <label>Title:</label>
                        <input type="text" class="form-control" name="title" value="" id="title" required placeholder="Title Here" />
                        <input type="hidden" name="Id" id="Id" />
                    </div>
                    <div class="form-group">
                        <label>Date:</label>
							<div class="controls">
                        	<input type="date" class="datepicker form-control" name="date" id="date" min="2000-01-01" max="today" required/>
							</div>
                    </div>
                    <div class="form-group">
                        <label>Description:</label>
                        <textarea class="form-control" name="description" rows="15" id="description" placeholder="Enter Your Description"></textarea>
                    </div>
                    <button class="save btn btn-large btn-info" type="submit" style="font-size:20px;"><i class="fa fa-floppy-o"></i></button>
                    <a href="#" class="btn btn-large btn-default" style="font-size:20px;"><i class="fa fa-times"></i></a>
                </form>
            </div>
        </script>

        <script type="text/jst" id="editTemplate">
            <div class="well" style="margin-top:10px;box-shadow:0 10px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19) !important;" div="scroll">
                <div class="alert alert-danger fade in" style="display:none;margin-top:10px;box-shadow:0 10px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19) !important;"></div>
                <form class="add-entry">
                    <div class="form-group">
                        <label>Title:</label>
                        <input type="text" class="form-control" name="title" value="<@= diary.get('title') @>" id="title" required placeholder="Title Here" />
                        <input type="hidden" name="id" value="<@= diary.get('id') @>" id="id" />
                        <input type="hidden" name="Id" id="Id" />
                    </div>
                    <div class="form-group">
                        <label>Date :</label>
                        <@= diary.get('date') @>
                            <input type="hidden" class="datepicker form-control" name="date" value="<@= diary.get('date') @>" id="date" min="2000-01-01" />

                    </div>
                    <div class="form-group">
                        <label>Description:</label>
                        <textarea class="form-control" name="description" rows="15" id="description" placeholder="Enter Your Description">
                            <@ var desobj =  diary.get('description') @>
						<@= desobj.value @>
                        </textarea>
                    </div>

                    <button class="save btn btn-large btn-info" type="submit" style="font-size:20px;"><i class="fa fa-floppy-o"></i></button>
                    <a href="#" class="btn btn-large btn-default" style="font-size:20px;"><i class="fa fa-times"></i></a>
                </form>
            </div>
        </script>


        <script type="text/template" id="viewTemplate">
            <div class="well well1" style="margin-top:20px;box-shadow:0 10px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19) !important;">
                <h2 style="padding-left: 2.7cm;"><@= diary.get('title') @></h2>
                <div class="form-group" style="padding-left: 2.7cm;">
                    <label>Date:&nbsp </label><font face=""><@= diary.get('date') @></font>
                </div>
                <div class="form-group" style="padding-left: 2.7cm;">
                    <label>Description:</label>
                    <p>
                        <@ var desobj =  diary.get('description') @>
						<@= desobj.value @>
                    </p>
                </div>
                <a href="#" class="btn btn-large btn-default" style="font-size:20px;"><i class="fa fa-times"></i></a>
            </div>
        </script>
        
        <!-- the index container -->
        <script type="text/template" id="indexTemplate">
			<div class="fresh-table" width="100%">
  				<table class="table table-striped" id="fresh-table" width="100%">
    				<thead class="fresh-table">
                        	<th data-field="name" data-sortable="true">Date</th>
                        	<th data-field="country" data-sortable="true">Title</th>
                        	<th data-field="actions"><center>Actions</center></th>
                        </thead>
					<tbody>
						<@ _.each(diaries,function(entry){ @>
						<tr class="Search">
							<td><@= entry.get('date') @></td>
							<td><@= entry.get('title') @></td>
								<@var desobj =  entry.get('description') @>
							<td style="display:none"><@= desobj.value @></td>

							<td><center>
							<a data-toggle="tooltip" data-placement="top" title="Tooltip on top" href="#/Details/<@= entry.id @>" style="font-size:20px;padding:0px;color:#229954;"><i class="fa fa-eye"></i></a>
            				<a href="#/edit/<@= entry.id @>" style="font-size:20px;padding:0px;color:skyblue"><i class="fa fa-pencil"></i></a>
            				<a href="#/remove/<@= entry.id @>" style="font-size:20px;padding:0px;color:#922B21"><i class="fa fa-trash "></i></a></center>
							</td>
						</tr>
						<@ }); @>
					</tbody>
	
  				</table>
			</div>
                       


        </script>




        <!-- load the libraries we need -->
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/json2/20150503/json2.min.js"></script>
        <script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min.js"></script>
        <script src="http://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.2.3/backbone-min.js"></script>
        <script type="text/javascript" src="assets/js/jquery-1.11.2.min.js"></script>
        <script src="assets/js/bootstrap.js"></script>
        <script src="assets/js/ct-navbar.js"></script>
        <script src="assets/js/index.js"></script>
			<script src="assets/nanobar/nanobar.js"></script>
			<script>
			
			</script>
		<script>
	 	_.templateSettings = {
	 		    interpolate: /\<\@\=(.+?)\@\>/gim,
	 		    evaluate: /\<\@([\s\S]+?)\@\>/gim,
	 		    escape: /\<\@\-(.+?)\@\>/gim
	 		};

			var simplebar = new Nanobar();
			simplebar.go(100);
		</script>
    </body>

</html>