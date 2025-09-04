<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%
  List<Map<String,Object>> dailySales = (List<Map<String,Object>>) request.getAttribute("dailySales");
  List<Map<String,Object>> topItems = (List<Map<String,Object>>) request.getAttribute("topItems");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="CSS/styles.css">
<title>Reports</title>
</head>
<body>
<header class="header">
  <div class="header-content">
    <h1>Pahana Edu Bookshop</h1>
    
    <div class="user-info">
  <%
     String username = (String) session.getAttribute("username");
     String role = (String) session.getAttribute("role");
     if (username != null) {
  %>
       <span>Welcome, <b><%= username %></b> (<%= role %>)</span>
       <a href="LogoutServlet" class="btn btn-danger small">Logout</a>
  <% } else { %>
       <a href="login.html" class="btn btn-secondary small">Login</a>
       <a href="signup.html" class="btn btn-primary small">Sign Up</a>
  <% } %>
</div>

  </div>
</header>
<nav class="navigation">
  <ul class="nav-menu">
    <li><a class="nav-btn" href="DashboardServlet">Dashboard</a></li>
    <li><a class="nav-btn" href="customers">Customer Management</a></li>
    <li><a class="nav-btn" href="ListItemsServlet">Item Management</a></li>
    <li><a class="nav-btn" href="billing">Billing</a></li>
    <li><a class="nav-btn active" href="reports">Reports</a></li>
    <li><a class="nav-btn" href="help.html">Help</a></li>
  </ul>
</nav>

<main class="main-content">
<section class="content-section active">

  <div class="section-header">
  <div class="section-header"><h2>Sales Reports</h2></div>
  <a href="ExportReportServlet" class="btn btn-primary">Export PDF</a>
</div>

  <h3>Daily Sales</h3>
  <div class="table-container">
    <table class="data-table">
      <thead>
        <tr><th>Date</th><th>Total Sales (LKR)</th></tr>
      </thead>
      <tbody>
        <% if (dailySales != null) {
             for (Map<String,Object> r : dailySales) { %>
          <tr>
            <td><%= r.get("day") %></td>
            <td><%= r.get("total_sales") %></td>
          </tr>
        <% } } %>
      </tbody>
    </table>
  </div>

  <h3 style="margin-top:24px;">Top Items (by revenue)</h3>
  <div class="table-container">
    <table class="data-table">
      <thead>
        <tr><th>Item</th><th>Qty</th><th>Revenue (LKR)</th></tr>
      </thead>
      <tbody>
        <% if (topItems != null) {
             for (Map<String,Object> r : topItems) { %>
          <tr>
            <td><%= r.get("item_name") %></td>
            <td><%= r.get("qty") %></td>
            <td><%= r.get("revenue") %></td>
          </tr>
        <% } } %>
      </tbody>
    </table>
  </div>
</section>
</main>
</body>
</html>
