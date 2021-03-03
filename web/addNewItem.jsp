<%-- 
    Document   : homePage
    Created on : 06-Jan-2021, 23:52:40
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="https://getbootstrap.com/docs/4.0/assets/img/favicons/favicon.ico">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <link type="text/css" rel="stylesheet" href="C:/Users/ADMIN/Desktop/SE5/LAB231/Cover Template for Bootstrap_files/cover.css">

        <title>Hana Shop</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/cover/">

        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link rel="stylesheet" type="text/css" href="css/cover.css" />
        <title>Hana Shop</title>
    </head>
    <body class="text-center" data-new-gr-c-s-check-loaded="14.990.0" data-gr-ext-installed="">
        <c:set var="user" value="${sessionScope.LAST_USER}"/>
        <c:set var="lastSearch" value="${requestScope.LAST_SEARCH_VALUE}"/>
        <c:set var="lastCategory" value="${requestScope.LAST_SEARCH_CATEGORY}"/>
        <c:set var="lastPriceStart" value="${requestScope.LAST_SEARCH_PRICE_START}"/>
        <c:set var="lastPriceEnd" value="${requestScope.LAST_SEARCH_PRICE_END}"/>


        <c:set var="listCategory" value="${sessionScope.LIST_CATEGORY}"/>
        <c:set var="cart" value="${sessionScope.CART}"/>

        <c:set var="numberOfPage" value="${sessionScope.NUMBER_OF_PAGE_USER}"/>

        <c:url var="toViewCartPage" value="DispatchServlet">
            <c:param name="btnAction" value="View Cart"></c:param>
            <c:param name="txtLastSearchValue" value="${lastSearch}"></c:param>
            <c:param name="txtLastSearchCategory" value="${lastCategory}"></c:param>
            <c:param name="txtLastSearchPriceStart" value="${lastPriceStart}"></c:param>
            <c:param name="txtLastSearchPriceEnd" value="${lastPriceEnd}"></c:param>
        </c:url>
        <div class="cover-container d-flex h-100 p-3 mx-auto flex-column">
            <header class="masthead mb-auto">
                <div class="inner">
                    <h3 class="masthead-brand">Hana Shop - Admin</h3>
                    <nav class="nav nav-masthead justify-content-center">
                        <a class="nav-link">Welcome, ${user.name}</a>

                        <a class="nav-link" href="DispatchServlet?btnAction=Logout">Logout</a>
                    </nav>
                </div>
            </header>
            <form action="DispatchServlet">
                <main role="main" class="form-group align-items-center">
                    <div class="form-group col-md-3" style="margin-left: 20px">
                        <label for="txtName">Item Name</label>
                        <input type="text" class="form-control"name="txtName" placeholder="Enter Item Name">
                    </div>
                    <br/>
                    
                    <div class="form-group col-md-3" style="margin-left: 20px">
                        <label for="txtImage">Image Link</label>
                        <input type="text" class="form-control"name="txtImage" placeholder="Enter Image Link">
                    </div>
                    <br/>
                    
                    <div class="form-group col-md-3" style="margin-left: 20px">
                        <label for="txtDescription">Description</label>
                        <input type="text" class="form-control"name="txtDescription" placeholder="Enter Description">
                    </div>
                    <br/>
                    
                    <div class="form-group col-md-3" style="margin-left: 20px">
                        <label for="txtPrice">Price</label>
                        <input type="number" class="form-control"name="txtPrice" placeholder="Enter Price">
                    </div>
                    <br/>
                    
                    <div class="form-group col-md-3" style="margin-left: 20px">
                        <label for="txtCategory">Category</label>
                        <input type="text" class="form-control"name="txtCategory" placeholder="Enter Category">
                    </div>
                    <br/>
                    
                    <div class="form-group col-md-3" style="margin-left: 20px">
                        <label for="txtQuantity">Quantity</label>
                        <input type="number" class="form-control"name="txtQuantity" placeholder="Enter Quantity">
                    </div>
                    <br/>

                    <div class="row" style="margin-left: 20px">
                        <button type="submit" class="btn btn-primary col-md-1" value="Add Item" name="btnAction">Add Item</button>
                        <div class="col-md-1"></div>
                        <button type="reset" class="btn btn-primary col-md-1">Reset</button><br/>
                    </div>
                </main>
            </form>


            <footer class="mastfoot mt-auto">
                <div class="inner">
                    <p>Cover template for <a href="https://getbootstrap.com/">Bootstrap</a>, by <a href="https://twitter.com/mdo">@mdo</a>.</p>
                </div>
            </footer>
        </div>


        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" integrity="sha512-bLT0Qm9VnAYZDflyKcBaQ2gg0hSYNQrJ8RilYldYQ1FxQYoCLtUjuuRuZo+fjqhx/qtq/1itJ0C2ejDxltZVFg==" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    </body>
</html>
