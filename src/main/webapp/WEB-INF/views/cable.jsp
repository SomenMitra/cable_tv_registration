<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cable Form</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css" />

<script src="https://code.jquery.com/jquery-2.2.4.js"
	integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
	crossorigin="anonymous"></script>
<script
	src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.4.0/jspdf.umd.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.13/jspdf.plugin.autotable.min.js"></script>

</head>
<body>
	<c:if test="${msg ne null}">
		<div class="alert alert-success" id="alId">${msg}</div>
	</c:if>

	<c:if test="${dmsg ne null}">
		<div class="alert alert-danger" id="dlId">${dmsg}</div>
	</c:if>

	<c:if test="${umsg ne null}">
		<div class="alert alert-warning" id="ulId">${umsg}</div>
	</c:if>
	<div class="container">

		<div class="card">
			<div class="card-header text-center h3 text-dark bg-warning">Cable
				Page</div>
			<div class="card-body">
				<form action="./saveReg" method="post" enctype="multipart/form-data">
					<div class="row">
						<div class="col-2">
							<input type="hidden" name="registrationId" id="regidId"
								value="${reg.registrationId}">
						</div>
						<div class="col-3">
							<label for="batchName">Select Provider*:</label> <select
								class="form-control" id="providerId" name="provider.providerId"
								onchange="getSubByProvId(this.value)">
								<option value="0">--Select--</option>
								<c:forEach items="${providers}" var="p">
									<option value="${p.providerId}"
										<c:if test="${p.providerId eq reg.provider.providerId}">selected='selected'</c:if>>${p.providerName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-3">
							<label for="batchName">Select Subscription*:</label> <select
								class="form-control" id="subsId"
								name="subscription.subscriptionId">
								<c:if test="${ reg ne null }">
									<c:forEach items="${subsList}" var="s">
										<option value="${s.subscriptionId}"
											<c:if test="${s.subscriptionId eq reg.subscription.subscriptionId}">selected='selected'</c:if>>${s.subscriptionType}</option>
									</c:forEach>
								</c:if>

							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label for="name">Name</label> <input type="text"
									class="form-control" id="aplname" name="applicantName" required
									value=${reg.applicantName }>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="email">Email</label> <input type="email"
									class="form-control" id="emailIdId" name="emailId" required
									value=${reg.emailId }>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="mobile">Mobile Number</label> <input type="text"
									class="form-control" id="mobileId" name="mobileNo" required
									value=${reg.mobileNo }>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="dob">Date of Birth</label> <input type="date"
									class="form-control" id="dob" name="dob" required
									value=${reg.dob }>
							</div>
						</div>
						<div class="col-4">
							<label for="genId" class="font-weight-bold">Gender</label>
							<div>
								<input type="radio" name="gender" id="genIdm" value="male"
									${reg.gender eq 'male' ? 'checked' : ''}>&nbsp;&nbsp;Male
								<input type="radio" name="gender" id="genIdf" value="female"
									${reg.gender eq 'female' ? 'checked' : ''}>&nbsp;&nbsp;Female
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="image">Upload Image</label> <input type="file"
									class="form-control" id="image" name="imageFile" required>
							</div>
						</div>
					</div>
					<div class="row mt-3 justify-content-center">
						<div class="col-md-6 text-center">
							<input type="submit" class="btn btn-success"
								value="${reg eq null ?'save':'update'}">
							<button type="reset" class="btn btn-info">Reset</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="card mt-4">
			<div class="card-header">
				<div class="h3 text-danger bg-warning text-center">Registration
					Details</div>
			</div>
			<div class="card-body">
				<form action="./filterProvider" method="get">
					<div class="row mb-3">
						<div class="col-md-4">
							<h5>Filter</h5>
							<label>Provider Name</label> <select class="form-control"
								name="fPvId" id="filterProviderId">
								<option value="0">-select-</option>
								<c:forEach items="${providers}" var="p">
									<option value="${p.providerId}">${p.providerName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2 d-flex align-items-end">
							<input type="submit" class="btn btn-info" value="search"
								class="form-control">
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="card mt-4">
			<div class="card-header">
				<a class='btn btn-danger m-1' href="#" id="downloadPdfBtn"><i
					class="fa fa-file-pdf"></i>Download as Pdf</a> <a
					class='btn btn-success m-1' href="#"><i
					class="fa fa-file-excel"></i>Download as Excel</a>
			</div>
			<div class="card-body">
				<table class="table table-bordered" id="vTableId">
					<thead>
						<tr>
							<th>Sl No</th>
							<th>Name</th>
							<th>Email</th>
							<th>Mobile Number</th>
							<th>Gender</th>
							<th>Document</th>
							<th>DOB</th>
							<th>Provider Name</th>
							<th>Subscription</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody id="registrationDetails">
						<c:forEach items="${regList}" var="reg" varStatus="counter">
							<tr>
								<td>${counter.count}</td>
								<td>${reg.applicantName}</td>
								<td>${reg.emailId}</td>
								<td>${reg.mobileNo}</td>
								<td>${reg.gender}</td>
								<td><a href="./getImage?fileName=${reg.imagePath}">${reg.imagePath}</a></td>
								<td><fmt:formatDate pattern="dd-MM-yyyy" value="${reg.dob}" /></td>
								<td>${reg.provider.providerName}</td>
								<td>${reg.subscription.subscriptionType}</td>
								<td><a class='btn btn-danger'
									href="./delReg?rId=${reg.registrationId}">Del</a>&nbsp; <a
									class='btn btn-info' href="./upReg?rId=${reg.registrationId}">Update</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#vTableId").dataTable({
				"lengthMenu" : [ 5, 10, 25, 50, "All" ]
			});
		});

		$(document).ready(function() {
			setTimeout(function() {
				document.getElementById("alId").style.display = 'none';
			}, 2000);
		});

		$(document).ready(function() {
			setTimeout(function() {
				document.getElementById("dlId").style.display = 'none';
			}, 2000);
		});
		$(document).ready(function() {
			setTimeout(function() {
				document.getElementById("ulId").style.display = 'none';
			}, 2000);
		});

		function getSubByProvId(pId) {
			console.log("provider id: " + pId);
			$.ajax({
				type : 'GET',
				url : 'getSubsByProvid',
				data : {
					provId : pId
				},
				success : function(resp) {
					$('#subsId').html(resp);
				},
				error : function(xhr, status, error) {
					console.error("AJAX Error: " + status + " - " + error);
				}
			});

		}
		
		$('#downloadPdfBtn').click(function() {
			const { jsPDF } = window.jspdf;
			const doc = new jsPDF();
			doc.autoTable({ 
				html: '#vTableId',
				styles: { fontSize: 8 },
				margin: { top: 20 },
				headStyles: { fillColor: [23, 162, 184] }
			});
			doc.save('cable.pdf');
		});
	</script>
</body>
</html>
