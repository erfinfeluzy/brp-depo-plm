<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<c:set var="url">${pageContext.request.contextPath}</c:set>

<!-- Left side column. contains the logo and sidebar -->

            <aside class="left-side sidebar-offcanvas">
                <!-- sidebar: style can be found in sidebar.less -->
                <section class="sidebar">
                    <!-- Sidebar user panel -->
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="img/avatar3.png" class="img-circle" alt="User Image" />
                        </div>
                        <div class="pull-left info">
                            <p>Hello, Elin</p>

                            <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                        </div>
                    </div>
                    <!-- search form -->
                    <form action="#" method="get" class="sidebar-form">
                        <div class="input-group">
                            <input type="text" name="q" class="form-control" placeholder="Search..."/>
                            <span class="input-group-btn">
                                <button type='submit' name='seach' id='search-btn' class="btn btn-flat"><i class="fa fa-search"></i></button>
                            </span>
                        </div>
                    </form>
                    <!-- /.search form -->
                    <!-- sidebar menu: : style can be found in sidebar.less -->
                    
                    
                    
                    <!-- Menu -->
                    <ul class="sidebar-menu">
                        <li class="active">
                            <a href="#">
                                <i class="fa fa-dashboard"></i> <span>Dashboard</span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i class="fa fa-th"></i> <span>Task</span> <small class="badge pull-right bg-green">new</small>
                            </a>
                        </li>
                        <li class="treeview">
                            <a href="#">
                                <i class="fa fa-bar-chart-o"></i>
                                <span>Finance</span>
                                <i class="fa fa-angle-left pull-right"></i>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="${url}/invoice"><i class="fa fa-angle-double-right"></i> Invoice</a></li>
                            </ul>
                        </li>
                        
                        <li class="treeview">
                            <a href="#">
                                <i class="fa fa-bar-chart-o"></i>
                                <span>Order Management</span>
                                <i class="fa fa-angle-left pull-right"></i>
                            </a>
                            
                            	<ul class="treeview-menu">
	                                <li><a href="${url}/form"><i class="fa fa-angle-double-right"></i>Order Karet</a></li>
	                                <li><a href="#"><i class="fa fa-angle-double-right"></i>Karet Masuk</a></li>
	                                <li><a href="#"><i class="fa fa-angle-double-right"></i>Karet Keluar</a></li>
	                                <li><a href="#"><i class="fa fa-angle-double-right"></i>Detail Order</a></li>
                                </ul>
                        </li>
                        
                        <li class="treeview">
                            <a href="#">
                                <i class="fa fa-bar-chart-o"></i>
                                <span>My Client</span>
                                <i class="fa fa-angle-left pull-right"></i>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="#"><i class="fa fa-angle-double-right"></i>Shipper</a></li>
                                <li><a href="#"><i class="fa fa-angle-double-right"></i>MLO</a></li>
                            </ul>
                        </li>
                        
                    </ul>
                </section>
                <!-- /.sidebar -->
            </aside>