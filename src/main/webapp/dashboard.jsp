<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="CSS/styles.css">
  <title>Dashboard | Pahana Edu Bookshop</title>
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
	      <li><a class="nav-btn active" href="DashboardServlet">Dashboard</a></li>
	      <li><a class="nav-btn" href="customers">Customer Management</a></li>
	      <li><a class="nav-btn" href="ListItemsServlet">Item Management</a></li>
	      <li><a class="nav-btn" href="billing">Billing</a></li>
	      <li><a class="nav-btn" href="reports">Reports</a></li>
	      <li><a class="nav-btn" href="help.html">Help</a></li>
	    </ul>
  </nav>
  <main class="main-content">
    <section class="content-section active">
      <div class="dashboard-header">
        <h2>Dashboard</h2>
        <p>Welcome to Pahana Edu Billing Management System</p>
      </div>
      <div class="dashboard-stats">
  <div class="stat-card">
    <h3>Total Customers</h3>
    <span class="stat-number"><%= request.getAttribute("customerCount") %></span>
  </div>
  <div class="stat-card">
    <h3>Total Items</h3>
    <span class="stat-number"><%= request.getAttribute("itemCount") %></span>
  </div>
  <div class="stat-card">
    <h3>Recent Bills</h3>
    <span class="stat-number"><%= request.getAttribute("billCount") %></span>
  </div>
</div>
    </section>
  </main>
  
  <script>
  const params = new URLSearchParams(window.location.search);
  if (params.get("registered") === "true") {
      alert("✅ Account created successfully! Welcome to the main page.");
  }
</script>

</body>
</html>
