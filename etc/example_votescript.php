<?php
//This is an example vote script.

define("MY_REFERER", "myscript1");

function doVote($address, $site, $name, $key, $port = 10099, $timeout = 30) {
	$socket = @fsockopen($address, $port, $errno, $errstr, $timeout);
	if (!$socket) {
		echo "<h1>The server isn't online.</h1>";
		return false;
	}
	$end_time = microtime(true);

	$prefix = MY_REFERER . ";" . $key . ";";

	fwrite($socket, $prefix . $site . "," . $name . "\n");

	$response = "";

	while(!feof($socket)) {
		$response .= fgets($socket, 1024);
	}

	echo $response;
	return $response;
}

if (isset($_GET['site_id'])) {
	$site = $_GET['site_id'];
	$name = $_GET['name'];
	$key = "";

	$response = doVote("play.mymcserver.com", $site, $name, $key);
	if ($response != "GOOD") {
		die("You have already voted.");
	}
	header("Location:http://minecraftservers.net/vote.php?id=11652");
}
?>
<!doctype html>
<html>
<head>
<title>Vote!</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<style type="text/css">
body {
	font-family: "Myriad Pro", Helvetica, Arial;
	text-align: center;
	line-height: 70%;
	margin: 0px;
}

h1 {
	font-size: 50pt;
}

h2 {
	font-size: 25pt;
	font-family: Georgia, "Times New Roman";
	font-style: italic;
	font-weight: lighter;
}

#content {
	position: absolute;
	width: 800px;
	height: 300px;
	margin-left: -400px;
	margin-top: -150px;
	left: 50%;
	top: 50%;
	visibility: visible;
}

.instantly {
	font-weight: bolder;
}

#votebox {
	font-size: 50pt;
	font-weight: bold;
	font-family: "Myriad Pro", Helvetica, Arial;
	width: 100%;
}

#votebox .blur {
	color: #aaaaaa;
}

#votebox .typing {
	color: #000000;
}

</style>
</head>
<body>
<script type="text/javascript">
$(function() {
	var voteText = "Click here...";
	$("#votebox")
	.val(voteText)
	.focusin(function() {
		$(this).val("");
		$("#instruct").html("<h2>Type your username and press enter to vote!</h2>")
	})	    
	.keydown(function(event) {
		if (event.keyCode == 13) {
			$(this).submit();
		}
	})
	.blur(function() {
		$(this).val(voteText);
		$("#instruct").html("");
	});
});
</script>
<div id="content">
<h1>Vote for CrimsonCraft!</h1>
<h2>and get commands <span class="instantly">instantly</span>!</h2>
<form action="" method="GET">
<input type="hidden" name="site_id" value="1" />
<input type="text" name="name" id="votebox" class="blur" />
</form>
<div id="instruct"></div>
</div>
</body>
</html>