<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<%
    List<Item> items = (List<Item>) request.getAttribute("items");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="CSS/styles.css">
<title>Item Management</title>
</head>
<body>
<header class="header">
    <div class="header-content">
      <h1>Pahana Edu Bookshop</h1>
      <div class="user-info">
        <a href="login.html" class="btn btn-secondary small">Login</a>
        <a href="signup.html" class="btn btn-primary small">Sign Up</a>
      </div>
    </div>
</header>
<nav class="navigation">
    <ul class="nav-menu">
      <li><a class="nav-btn" href="index.html">Dashboard</a></li>
      <li><a class="nav-btn" href="customers">Customer Management</a></li>
      <li><a class="nav-btn active" href="items.jsp">Item Management</a></li>
      <li><a class="nav-btn" href="billing">Billing</a></li>
      <li><a class="nav-btn" href="reports">Reports</a></li>
      <li><a class="nav-btn" href="help.html">Help</a></li>
    </ul>
</nav>
<main class="main-content">
    <section class="content-section active">
      <div class="section-header">
        <h2>Item Management</h2>
        
        <form action="AddItemServlet" method="post">
            <input type="text" name="itemName" placeholder="Item Name" required />
            <input type="text" name="category" placeholder="Category" />
            <input type="number" name="price" placeholder="Price" step="0.01" required />
            <input type="number" name="stockQuantity" placeholder="Stock" required />
            <button type="submit" class="btn btn-primary">Add Item</button>
        </form>
        
      
  
      </div>
      <div class="table-container">
        <table class="data-table">
          <thead>
            <tr>
              <th>Item ID</th>
              <th>Name</th>
              <th>Category</th>
              <th>Price</th>
              <th>Stock</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <%
              if (items != null) {
                for (Item item : items) {
            %>
              <tr>
                <td><%= item.getItemId() %></td>
                <td><%= item.getItemName() %></td>
                <td><%= item.getCategory() %></td>
                <td><%= item.getPrice() %></td>
                <td><%= item.getStockQuantity() %></td>
                <td><a href="DeleteItemServlet?id=<%= item.getItemId() %>">Delete</a></td>
              </tr>
            <% 
                }
              }
            %>
          </tbody>
        </table>
      </div>
    </section>
</main>
</body>
</html>
