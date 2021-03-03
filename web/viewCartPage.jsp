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
        <!--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">-->
        <!--<link type="text/css" rel="stylesheet" href="C:/Users/ADMIN/Desktop/SE5/LAB231/Cover Template for Bootstrap_files/cover.css">-->

        <!--        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
                <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>-->

        <title>Hana Shop</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/cover/">

        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

        <!-- Custom styles for this template -->
        <link rel="stylesheet" type="text/css" href="css/cover.css" />
        <title>Hana Shop</title>
    </head>
    <body class="text-center" data-new-gr-c-s-check-loaded="14.990.0" data-gr-ext-installed="">
        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:set var="totalMoneyCart" value="${requestScope.TOTAL_MONEY_CART}"/>

        <c:set var="user" value="${sessionScope.LAST_USER}"/>
        <c:set var="lastSearch" value="${requestScope.LAST_SEARCH_VALUE}"/>
        <c:set var="lastCategory" value="${requestScope.LAST_SEARCH_CATEGORY}"/>
        <c:set var="lastPriceStart" value="${requestScope.LAST_SEARCH_PRICE_START}"/>
        <c:set var="lastPriceEnd" value="${requestScope.LAST_SEARCH_PRICE_END}"/>

        <div class="cover-container d-flex h-100 p-3 mx-auto flex-column">
            <header class="masthead mb-auto">
                <div class="inner">
                    ${requestScope.LAST_SEARCH_VALUE}

                    <c:url var="shoppingPage" value="DispatchServlet">
                        <c:param name="btnAction" value="Search"></c:param>
                        <c:param name="txtSearch" value="${lastSearch}"></c:param>
                        <c:param name="txtCategory" value="${lastCategory}"></c:param>
                        <c:param name="txtPriceStart" value="${lastPriceStart}"></c:param>
                        <c:param name="txtPriceEnd" value="${lastPriceEnd}"></c:param>
                    </c:url>
                    <h3 class="masthead-brand">Hana Shop</h3>
                    <nav class="nav nav-masthead justify-content-center">
                        <a class="nav-link">Welcome, ${user.name}</a>
                        <a class="nav-link" href=${shoppingPage}>
                            Back to Shopping Page
                        </a>
                        <a class="nav-link" href="DispatchServlet?btnAction=Logout">Logout</a>
                    </nav>
                </div>
            </header>

            <c:if test="${not empty cart}">
                <c:if test="${not empty cart.cartItem}">
                    <div class="container text-muted">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="panel panel-info">
                                    <div class="panel-heading">
                                        <div class="panel-title">
                                            <div class="row">
                                                <div class="col-xs-6">
                                                    <h5><span class="glyphicon glyphicon-shopping-cart"></span>Shopping Cart</h5>
                                                </div>
                                                <div class="col-xs-6">
                                                    <c:url value="DispatchServlet" var="shoppingHistoryLink">
                                                        <c:param name="btnAction" value="viewShoppingHistory"></c:param>
                                                    </c:url>
                                                    <a class="btn btn-primary btn-sm btn-block" href="${shoppingHistoryLink}" role="button">View Your Shopping History</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-body">
                                        <c:forEach items="${cart.cartItem}" var="itemCart">
                                            <form action="DispatchServlet">
                                                <div class="row">
                                                    <div class="col-xs-2"><img class="img-responsive" src="${itemCart.key.img}">
                                                    </div>
                                                    <div class="col-xs-4">
                                                        <h3 class="product-name"><strong>${itemCart.key.name}</strong></h3><h3><small>${itemCart.key.description}</small></h3>
                                                        <input type="hidden" name="txtItemName" value="${itemCart.key.name}" />
                                                    </div>
                                                    <div class="col-xs-6">
                                                        <div class="col-xs-5 text-right">
                                                            <h4><strong>${itemCart.key.price}$ <span class="text-muted">x</span></strong></h4>
                                                        </div>
                                                        <div class="col-xs-4">
                                                            <input name="txtItemValue" type="text" class="form-control input-sm" value="${itemCart.value}">
                                                        </div>
                                                        <div class="col-xs-1">
                                                            <button type="submit" name="btnAction" value="deleteItem" class="btn btn-link btn-xs" onclick="return confirm('Are you sure?')">
                                                                <span class="glyphicon glyphicon-trash"> </span>
                                                            </button>
                                                        </div>
                                                        <div class="col-xs-2">
                                                            <button type="submit" class="btn btn-outline-primary" value="updateCart" name="btnAction">Apply</button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <hr>
                                            </form>
                                        </c:forEach>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="row text-center">
                                            <div class="col-xs-9">
                                                <h4 class="text-right">Total <strong>
                                                        <c:if test="${not empty totalMoneyCart}">
                                                            ${totalMoneyCart}$
                                                        </c:if>
                                                    </strong></h4>
                                            </div>
                                            <div class="col-xs-3">
                                                <c:url var="confirmCartLink" value="DispatchServlet">
                                                    <c:param name="btnAction" value="confirmCart"></c:param>
                                                </c:url>
                                                <a class="btn btn-primary" href="${confirmCartLink}" role="button">Confirm</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${empty cart.cartItem}">
                    <h1>No Cart Is Available</h1>
                </c:if>
            </c:if>

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
