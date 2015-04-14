<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/mycss.css" rel="stylesheet">
<script src="js/myjs.js"></script>
<script src="js/vis.min.js"></script>
<script src="js/jquery-1.11.1.min.js"></script>


<title>Twitter Analysis</title>
</head>
<body>
	<div class="container"
		style="background: #00CED1; height: 150px; padding-left: 50px; padding-top: 10px; border: 2px dotted; border-radius: 10px">
		<h1>Twitter Analysis(Oak Team)</h1>
		<p>
			<u>Andrew ID: jialic, ahsu1, ziyuans</u>
		</p>
	</div>
	<div class="container" style="margin-top: 10px; margin-bottom: 10px">
		<form action="twitter?q=4" method="POST">
			<span> Start Date: <input id="sDate-id" name="sDate"
				class="form-group" placeholder="yyyy-mm-dd"> End Date: <input
				id="eDate-id" name="eDate" class="form-group"
				placeholder="yyyy-mm-dd"> HashTag: <input id="hashTag-id"
				name="hashTag" class="form-group" placeholder="CMURocks"> <input
				type="submit" class="btn btn-default" style="float: right"
				value="SUBMIT" onclick="">
			</span>
		</form>
	</div>

	<div class="container"
		style="overflow: auto; background: #AFEEEE; padding-left: 50px; padding-top: 10px; border-radius: 5px">
		<div>
			<h2>TOP 10 Tweets</h2>
		</div>
		<table class="table table-bordered"
			style="background: #F0F8FF; width: 80%;">
			<tr>
				<th width="10px">#</th>
				<th>Twitter ID</th>
				<th>User ID</th>
				<th>Twitter Time</th>
			</tr>
			<c:forEach var="tweet" items="${tweetList}" varStatus="loop">
				<tr>
					<td>${loop.index}</td>
					<td><a href="javascript:void(0)"
						onclick="getContext(document.getElementById('top${loop.index}-twitterId').innerHTML,document.getElementById('top${loop.index}-userId').innerHTML,document.getElementById('top${loop.index}-time').innerHTML)"><span
							id="top${loop.index}-twitterId">${tweet.tweetid}</span></a></td>
					<td><a href="javascript:void(0)"
						onclick="relationDiagram(document.getElementById('top${loop.index}-userId').innerHTML)"><span
							id="top${loop.index}-userId">${tweet.userid}</span></a></td>
					<td><span id="top${loop.index}-time">${tweet.tweettime}</span></td>
				</tr>
			</c:forEach>
			<tr style="height: 80px">
				<td align="left"><h4>Context</h4></td>
				<td colspan="3" id="context"></td>
			</tr>
		</table>
		<div>
			<h2>
				Retweet Network for <span id="showID">UserID XXX</span>
			</h2>
		</div>
		<div id="relationDiagram" class="container"
			style="background: #F5FFFA; width: 80%; float: left; height: 500px; margin-bottom: 100px; border-radius: 10px; border-style: solid; border-color: green">
		</div>
	</div>
	<script type="text/javascript">
		// create an array with nodes
	</script>
</body>
</html>