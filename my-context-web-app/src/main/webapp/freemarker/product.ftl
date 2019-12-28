<html>
<head>
    <meta charset="UTF-8">
    <title>Products</title>
</head>
<body>
<h1>Product page</h1>

<p>Your login = ${login}</p>
<p>Your role = ${role}</p>

<table>
    <tr>
        <td>id</td>
        <td>name</td>
    </tr>
    <#list products as product>
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
        </tr>
    </#list>
</table>

<#if role = "admin">
    <p>Add a new product</p>
    <form method="POST">
        <input type="hidden" name="form_type" value="add_product">
        <p>Name of new product</p>
        <input type="text" name="name">
        <input type="submit">
    </form>
</#if>

<p>Buy product</p>
<form method="POST">
    <input type="hidden" name="form_type" value="buy_product">
    <p>Id of buying product</p>
    <input type="text" name="id">
    <input type="submit">
</form>

<p>My products</p>
<table>
    <tr>
        <td>id</td>
        <td>name</td>
    </tr>
    <#list products as product>
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
        </tr>
    </#list>
</table>

</body>
</html>