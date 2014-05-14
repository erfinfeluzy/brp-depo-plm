<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<c:set var="url">${pageContext.request.contextPath}</c:set>

<section class="content-header">
	<h1>
		Invoice<small>Preview</small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
		<li><a href="#">Forms</a></li>
		<li class="active">General Elements</li>
	</ol>
</section>

<!-- Main content -->
<section class="content invoice">
	<!-- title row -->
	<div class="row">
		<div class="col-xs-12">
			<h2 class="page-header">
				<i class="fa fa-globe"></i> PT BAHARI RAHARJA PERMAI 
				<small class="pull-right">Date:12/31/2014</small>
			</h2>
		</div>
		<!-- /.col -->
	</div>
	<!-- info row -->
	<div class="row invoice-info" style="border-bottom: 1px solid #eee">
		<div class="col-sm-4 invoice-col">
			From
			<address>
				<strong>PT Bahari Raharja Permai</strong><br/> 
				Jl. Bambang Utoyo, samping Villa Bari Indah<br/>
				Palembang, Indonesia, 16514<br/> 
				Tlp:0711-720207 () <br/> 
				Email: office@depobrp.com / finance@depobrp.com
			</address>
		</div>
		<!-- /.col -->
		<div class="col-sm-4 invoice-col">
			To
			<address>
				<strong>PT. KIRANA WINDU</strong><br> 
				JL. LINTAS SUMATERA KM 98<br>
				PASAR SURULANGUN RAWAS<br>
				KAB. MUSI RAWAS - SUMATERA SELATAN<br>
			</address>
		</div>
		<!-- /.col -->
		<div class="col-sm-4 invoice-col">
			<b class="pull-right">No: 248/INV/BRP-KRW/III/2014</b><br /> <br /> 
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	<br/>
	<div class="row">
		<div class="col-sm-4">
		<table style="width: 100%;">
			<tr>
				<td width="100px">Contract No</td>
				<td width="10px">:</td>
				<td>YR 5116</td>
			</tr>
			<tr>
				<td>Weight</td>
				<td>:</td>
				<td>YR 5116</td>
			</tr>
			
			<tr>
				<td>In Date</td>
				<td>:</td>
				<td>YR 5116</td>
			</tr>
			
		</table>
		</div>
		<div class="col-sm-8">
			<table style="width: 100%;">
			<tr>
				<td>KGS</td>
				
			</tr>
			<tr>
				<td>= 24 x MB4 x 1.345 KGS = 32.280 KGS</td>
			</tr>
			<tr>
				<td>= 48 x MB4 x 1.345 KGS = 64.560 KGS</td>
			</tr>
			<tr>
				<td>= 8 x MB4 x 1.345 KGS = 10.760 KGS</td>
			</tr>
			
		</table>
		</div>
	</div>
	<br/>
	<!-- Table row -->
	<div class="row">
		<div class="col-xs-12 table-responsive">
			<b>STUFFING DI CYDEPO - TERM CFS/DEPO BRP</b>
			<table class="table table-striped">
				<thead>
					<tr>
						<th width="60%">Description</th>
						<th width="10%"><span class="pull-right">Weight</span></th>
						<th width="10%"><span class="pull-right">Price</span></th>
						<th width="20%"><span class="pull-right">Subtotal</span></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>OPP receiving/delivery</td>
						<td><b class="pull-right">100</b></td>
						<td><b class="pull-right">@ Rp. 22,946</b></td>
						<td><b class="pull-right">Rp. 2,488,990</b></td>
					</tr>
					
					<tr>
						<td>Forklift dari truck bak tertutup di gudang</td>
						<td><b class="pull-right">100</b></td>
						<td><b class="pull-right">@ Rp. 22,946</b></td>
						<td><b class="pull-right">Rp. 2,488,990</b></td>
					</tr>
					<tr>
						<td>Forklift dari truck bak tertutup di gudang</td>
						<td><b class="pull-right">100</b></td>
						<td><b class="pull-right">@ Rp. 22,946</b></td>
						<td><b class="pull-right">Rp. 2,488,990</b></td>
					</tr>
					
					
				</tbody>
			</table>
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->

	<div class="row">
		<!-- accepted payments column -->
		<div class="col-xs-6">
			<p class="lead">Payment Methods:</p>
			<img src="${url}/img/credit/visa.png" alt="Visa" /> <img
				src="${url}/img/credit/mastercard.png" alt="Mastercard" /> <img
				src="${url}/img/credit/american-express.png" alt="American Express" />
			<img src="${url}/img/credit/paypal2.png" alt="Paypal" />
			<p class="text-muted well well-sm no-shadow" style="margin-top: 10px;">
				Please remit to our account : <br/>
				Bank Mandiri Palembang <br/>
				Acc No.1120010570351 (IDR) <br/>
				PT. BAHARI RAHARJA PERMAI <br/>
				Ref# 248 </p>
		</div>
		<!-- /.col -->
		<div class="col-xs-6">
			<p class="lead">Amount Due 2/22/2014</p>
			<div class="table-responsive">
				<table class="table">
					<tr>
						<th style="width: 50%">Subtotal:</th>
						<td>Rp. 6,540,000.00</td>
					</tr>
					<tr>
						<th>PPN (10.0%)</th>
						<td>Rp. 654,000.00</td>
					</tr>
					
					<tr>
						<th>Total:</th>
						<td>Rp. 7,194,000.00</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->

	<!-- this row will not appear when printing -->
	<div class="row no-print">
		<div class="col-xs-12">
			<button class="btn btn-default" onclick="window.print();">
				<i class="fa fa-print"></i> Print
			</button>
			<button class="btn btn-success pull-right">
				<i class="fa fa-credit-card"></i> Submit Payment
			</button>
			<button class="btn btn-primary pull-right" style="margin-right: 5px;">
				<i class="fa fa-download"></i> Generate PDF
			</button>
		</div>
	</div>