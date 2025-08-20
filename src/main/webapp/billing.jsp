<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Item" %>
<%
  List<Customer> customers = (List<Customer>) request.getAttribute("customers");
  List<Item> items = (List<Item>) request.getAttribute("items");
  String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="CSS/styles.css">
<title>Billing</title>
<script>

function addRow() {
    const container = document.getElementById('itemsContainer');
    const row = document.createElement('div');
    row.className = 'bill-item';

    // Use the template
    const template = document.getElementById('itemTemplate').content.cloneNode(true);

    // Create the select element and append cloned options
    const select = document.createElement('select');
    select.name = "item_id";
    select.required = true;

    // Add default option
    const defaultOption = document.createElement('option');
    defaultOption.value = "";
    defaultOption.textContent = "Select item...";
    select.appendChild(defaultOption);

    select.appendChild(template); // append all options from template

    row.innerHTML = `
        <input type="number" name="quantity" min="1" value="1" required/>
        <button type="button" class="btn btn-danger" onclick="this.parentElement.remove(); calcTotal();">Remove</button>
    `;
    row.prepend(select); // put select at the start
    container.appendChild(row);
}



function calcTotal(){
  const rows = document.querySelectorAll('.bill-item');
  let total = 0;
  rows.forEach(r=>{
    const sel = r.querySelector('select[name="item_id"]');
    const qty = parseInt(r.querySelector('input[name="quantity"]').value||0,10);
    const price = parseFloat(sel.selectedOptions[0]?.dataset.price || 0);
    total += price * qty;
  });
  document.getElementById('totalSpan').textContent = total.toFixed(2);
}
document.addEventListener('change', (e)=>{
  if(e.target.matches('select[name="item_id"], input[name="quantity"]')) calcTotal();
});
</script>
<style>
.bill-item { display:flex; gap:10px; margin-bottom:8px; align-items:center; }
</style>
</head>
<body>
<header class="header">
  <div class="header-content">
    <h1>Pahana Edu Bookshop</h1>
    <div class="user-info">
      <a href="login.jsp" class="btn btn-secondary small">Login</a>
      <a href="signup.jsp" class="btn btn-primary small">Sign Up</a>
    </div>
  </div>
</header>
<nav class="navigation">
  <ul class="nav-menu">
    <li><a class="nav-btn" href="index.html">Dashboard</a></li>
    <li><a class="nav-btn" href="customers">Customer Management</a></li>
    <li><a class="nav-btn" href="ListItemsServlet">Item Management</a></li>
    <li><a class="nav-btn active" href="billing">Billing</a></li>
    <li><a class="nav-btn" href="reports">Reports</a></li>
    <li><a class="nav-btn" href="help.html">Help</a></li>
  </ul>
</nav>

<main class="main-content">
<section class="content-section active">
  <div class="section-header"><h2>Create New Bill</h2></div>

  <% if (error != null) { %>
    <div class="error"><%= error %></div>
  <% } %>

  <form action="billing" method="post" onsubmit="return (document.querySelectorAll('.bill-item').length>0)">
    <div class="form-group">
      <label for="customerSelect">Select Customer</label>
      <select id="customerSelect" name="customer_id" required>
        <option value="">Select a customer...</option>
        <% for (Customer c : customers) { %>
          <option value="<%= c.getCustomerId() %>"><%= c.getName() %> (#<%= c.getCustomerId() %>)</option>
        <% } %>
      </select>
    </div>

    <div class="items-section">
      <h4>Items</h4>
      <div id="itemsContainer"></div>
      <button type="button" class="btn btn-secondary" onclick="addRow()">Add Item</button>
    </div>

    <div class="form-group">
      <label>Payment Method</label>
      <select name="payment_method" required>
        <option value="CASH">Cash</option>
        <option value="CARD">Card</option>
        <option value="ONLINE">Online</option>
      </select>
    </div>

    <div class="bill-total"><h4>Total: LKR <span id="totalSpan">0.00</span></h4></div>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">Generate Bill</button>
      <button type="reset" class="btn btn-secondary" onclick="document.getElementById('itemsContainer').innerHTML='';calcTotal();">Clear</button>
    </div>

<%= "Items loaded: " + items.size() %>
<div class="form-group">
    <template id="itemTemplate">
	  <% for (Item it : items) { %>
	    <option value="<%= it.getItemId() %>" data-price="<%= it.getPrice() %>">
	      <%= it.getItemName() %> (LKR <%= it.getPrice() %>)
	    </option>
	  <% } %>
	</template>
</div>

  </form>
</section>
</main>

<script>addRow();</script>
</body>
</html>
