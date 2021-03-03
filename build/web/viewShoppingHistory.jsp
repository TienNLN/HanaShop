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

        <c:set var="mapOrdersOrderDetails" value="${requestScope.MAP_ORDERS_BY_ORDER_DETAILS}"/>
        <c:set var="listItems" value="${requestScope.LIST_ITEMS}"/>
        <c:set var="listOrdersHistory" value="${requestScope.LIST_ORDERS_HISTORY}"/>

        <c:set var="listCategory" value="${sessionScope.LIST_CATEGORY}"/>
        <c:set var="cart" value="${sessionScope.CART}"/>

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
                    <h3 class="masthead-brand">Hana Shop</h3>
                    <nav class="nav nav-masthead justify-content-center">
                        <a class="nav-link">Welcome, ${user.name}</a>
                        <a class="nav-link" href=${toViewCartPage}>
                            Your Cart <span class="badge badge-light">
                                <c:if test="${not empty cart}">
                                    ${cart.size}
                                </c:if>
                                <c:if test="${empty cart}">
                                    0
                                </c:if>
                            </span>
                            <span class="sr-only">unread messages</span>
                        </a>
                        <a class="nav-link" href="DispatchServlet?btnAction=Logout">Logout</a>
                    </nav>
                </div>
            </header>

            <form action="DispatchServlet">
                <main role="main" class="form-group">
                    <div class="row">
                        <div class="col-7">
                            <input class="form-control" name="txtSearchItemName" placeholder="Search Shopping History by Item Name">
                        </div>
                        <div class="col-5">
                            <input class="btn btn-lg btn-secondary" type="submit" value="Search History" name="btnAction" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-1">
                            <button class="btn btn-primary" type="button" data-toggle="collapse" data-target=".multi-collapse" aria-expanded="false" aria-controls="multiCollapseExample">Advance Search</button>
                        </div>
                    </div>


                    <div class="row" style="width: 100%">
                        <div class="col">
                            <div class="collapse multi-collapse" id="multiCollapseExample">
                                <div class="card card-body">
                                    <div class="form-group">
                                        <label for="txtSearchHistoryDate" class="text-secondary">
                                            Search by Shopping Date
                                        </label><br/>

                                        <input type="date" class="form-control" name="txtSearchHistoryDate" placeholder="Search by Shopping Date"">
                                    </div>
                                </div>
                            </div>
                        </div>

                </main>
            </form>
            <br/><br/><br/><br/>

            
            <main role="main" class="form-group">

                <c:forEach var="itemOrdersOrderDetails" items="${mapOrdersOrderDetails}">
                    <c:forEach items="${listOrdersHistory}" var="itemMap">
                        <c:if test="${itemOrdersOrderDetails.key == itemMap.orderID}">
                            <h2>
                                Order ID:&nbsp;&nbsp;&nbsp;&nbsp;${itemMap.orderID}
                                <br/>
                                Shopping Time:&nbsp;&nbsp;&nbsp;&nbsp;${itemMap.buyDateTime}
                                <br/>
                                Total Amount:&nbsp;&nbsp;&nbsp;&nbsp;${itemMap.totalAmount}$
                                <br/>
                                <c:if test="${itemMap.isPaid}">
                                    Status:&nbsp;&nbsp;&nbsp;&nbsp; Paid
                                </c:if>
                                <c:if test="${not itemMap.isPaid}">
                                    Status:&nbsp;&nbsp;&nbsp;&nbsp; Not Paid
                                </c:if>
                            </h2>
                            <br/>
                            <div class="test">
                                <div class="itemsList row" style="width: 100%">
                                    <c:forEach items="${itemOrdersOrderDetails.value}" var="item">
                                        <c:if test="${itemOrdersOrderDetails.key == itemMap.orderID}">
                                            <c:set var="count" value="${0}"/>
                                            <c:forEach items="${listItems}" var="itemListItems"  varStatus="counter">
                                                <c:set var="count" value="${count + 1}"/>

                                                <!--${coun}-->

                                                <c:if test="${count % 2 == 1}">
                                                    <div class="row" style="width: 100%">
                                                    </c:if>
                                                    <c:if test="${item.itemName == itemListItems.name}">

                                                        <form action="DispatchServlet" class="col-md-5 offset-1 border border-white rounded " style="height: 800px; width: 400px; margin-bottom: 20px; padding-top: 15px">
                                                            <img src="${itemListItems.img}" height="60%" width="100%"><br/>
                                                            <input type="hidden" name="txtItemImg" value="${itemListItems.img}" />

                                                            <div class="card-body">
                                                                <h3 class="card-title">${itemListItems.name}</h3>
                                                                <br/>

                                                                <p class="card-text">${itemListItems.description}</p>
                                                                <br/>

                                                                <p class="card-text">Price : ${itemListItems.price}$</p>
                                                                <br/>

                                                                <p class="card-text">Quantity : ${item.quantity}</p>
                                                                <br/>
                                                            </div>
                                                        </form>
                                                    </c:if>
                                                    <c:if test="${counter.count % 2 == 0}">
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <%--</c:forEach>--%>
                            </div>
                            <hr/>
                        </c:if>
                    </c:forEach> 
                </c:forEach>
                <!--                </div>-->
                <c:if test="${not empty requestScope.NO_SEARCH_RESULT}">
                    <h1>
                        ${requestScope.NO_SEARCH_RESULT}
                    </h1>
                </c:if>
            </main>


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
