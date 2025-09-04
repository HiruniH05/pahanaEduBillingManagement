<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="model.Customer" %>
<%
  List<Customer> customers = (List<Customer>) request.getAttribute("customers");
  Customer edit = (Customer) request.getAttribute("editCustomer");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="CSS/styles.css">
<title>Customer Management</title>
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
    <li><a class="nav-btn active" href="customers">Customer Management</a></li>
    <li><a class="nav-btn" href="ListItemsServlet">Item Management</a></li>
    <li><a class="nav-btn" href="billing">Billing</a></li>
    <li><a class="nav-btn" href="reports">Reports</a></li>
    <li><a class="nav-btn" href="help.html">Help</a></li>
  </ul>
</nav>

<main class="main-content">
<section class="content-section active">
  <div class="section-header">
    <h2>Customer Management</h2>
  </div>
 


  <!-- Add / Edit form -->
  <div class="card" style="padding:16px;margin-bottom:18px;">
    <h3><%= (edit==null) ? "Add New Customer" : ("Edit Customer #"+edit.getCustomerId()) %></h3>
    <form action="customers" method="post" class="grid">
      <input type="hidden" name="customer_id" value="<%= (edit!=null)?edit.getCustomerId():"" %>"/>
      <input type="text" name="name" placeholder="Full Name" value="<%= (edit!=null)?edit.getName():"" %>" required/>
      <input type="email" name="email" placeholder="Email" value="<%= (edit!=null)?edit.getEmail():"" %>"/>
      <input type="text" name="phone" placeholder="Phone" value="<%= (edit!=null)?edit.getPhone():"" %>"/>
      <input type="text" name="address" placeholder="Address" value="<%= (edit!=null)?edit.getAddress():"" %>"/>
      <div class="form-actions">
        <button type="submit" class="btn btn-primary"><%= (edit==null) ? "Add" : "Update" %></button>
        <a href="customers" class="btn btn-secondary">Clear</a>
      </div>
    </form>
  </div>
  
    <!-- Search Bar -->
<div class="search-container" style="margin:16px 0;">
  <form action="customers" method="get">
    <input type="text" name="search" placeholder="Search by name, email, or phone"
           value="<%= (request.getParameter("search") != null) ? request.getParameter("search") : "" %>"
           style="padding:8px;width:250px;"/>
    <button type="submit" class="btn btn-primary">Search</button>
    <a href="customers" class="btn btn-secondary">Clear</a>
  </form>
</div>


  <!-- List -->
  <div class="table-container">
    <table class="data-table">
      <thead>
        <tr>
          <th>Account #</th>
          <th>Name</th>
          <th>Email</th>
          <th>Telephone</th>
          <th>Address</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
      <% if (customers != null) { for (Customer c : customers) { %>
        <tr>
          <td><%= c.getCustomerId() %></td>
          <td><%= c.getName() %></td>
          <td><%= c.getEmail() %></td>
          <td><%= c.getPhone() %></td>
          <td><%= c.getAddress() %></td>
          <td>
            <a class="btn btn-secondary" href="customers?action=edit&id=<%= c.getCustomerId() %>">Edit</a>
            <a class="btn btn-danger" href="customers?action=delete&id=<%= c.getCustomerId() %>" onclick="return confirm('Delete this customer?')">Delete</a>
          </td>
        </tr>
      <% } } %>
      </tbody>
    </table>
  </div>
</section>
</main>
</body>
</html>
