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

        <!--<title>Hana Shop</title>-->

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

        <c:set var="listItems" value="${sessionScope.LIST_ITEMS}"/>

        <c:set var="listCategory" value="${sessionScope.LIST_CATEGORY}"/>
        <c:set var="cart" value="${sessionScope.CART}"/>

        <c:url var="toViewCartPage" value="DispatchServlet">
            <c:param name="btnAction" value="View Cart"></c:param>
            <c:param name="txtLastSearchValue" value="${lastSearch}"></c:param>
            <c:param name="txtLastSearchCategory" value="${lastCategory}"></c:param>
        </c:url>
        <div class="cover-container d-flex h-100 p-3 mx-auto flex-column">
            <header class="masthead mb-auto">
                <div class="inner">
                    <h3 class="masthead-brand">Hana Shop - Admin</h3>
                    <nav class="nav nav-masthead justify-content-center">
                        <a class="nav-link">Welcome, ${user.fullname}</a>
                    </nav>
                </div>
            </header>
            <br/><br/><br/><br/>

            <main role="main" class="form-group">
                <a class="btn btn-lg btn-primary" href="addNewItem.jsp">Add Item</a>
                <!--<input class="btn btn-lg btn-secondary" type="submit" value="Add Item" name="btnAction" />-->
            </main>

            <main role="main" class="form-group">
                <c:if test="${not empty listItems}">
                    <div class="itemsList row" style="width: 100%">

                        <c:forEach var="item" items="${listItems}" varStatus="counter">
                            <c:if test="${counter.count % 2 == 1}">
                                <div class="row" style="width: 100%">
                                </c:if>
                                <form id="form-${counter.count}"
                                      action="DispatchServlet" 
                                      class="col-md-5 offset-1 border border-white rounded " 
                                      style="height: 800px; width: 400px; margin-bottom: 20px; padding-top: 15px">
                                    <img src="${item.img}" height="50%" width="100%"><br/>
                                    <input type="hidden" name="txtItemImg" value="${item.img}" />
                                    <hr/>

                                    <div class="card-body row">
                                        <div class="card-text col-12">
                                            <label for="txtImgLink">Item Image Link: </label>
                                            <input type="text" name="txtImgLink" value="${item.img}" disabled="true" id="txtImgLink"/>
                                        </div>
                                        <!--<br/>-->

                                        <div class="card-text col-12">
                                            <label for="txtItemName">Item Name: </label>
                                            <input type="text" name="txtItemName" value="${item.name}" disabled="true" id="txtItemName"/>
                                            <input type="hidden" name="txtOldItemName" value="${item.name}" />
                                        </div>
                                        <!--<br/>-->

                                        <div class="card-text col-12">
                                            <label for="txtDescription">Item Description: </label>
                                            <input type="text" name="txtDescription" value="${item.description}" disabled="true" id="txtDescription"/>
                                        </div>
                                        <!--<br/>-->

                                        <div class="card-text col-12">
                                            <label  for="txtPrice">Item Price: $</label>
                                            <input type="number" name="txtPrice" value="${item.price}" disabled="true" id="txtPrice"/>
                                        </div>

                                        <div class="card-text col-12">
                                            <select class="form-control" id="txtCategory" name="txtCategory" disabled="true">
                                                <option value="${item.category}">${item.category}</option>
                                                <c:forEach items="${listCategory}" var="category">
                                                    <option value="${category}">${category}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <br/><br/>

                                        <div class="card-text col-12">
                                            <select class="form-control" id="txtStatus" name="txtStatus" disabled="true">
                                                <c:if test="${item.status}">
                                                    <option value="true">On Sale</option>
                                                    <option value="false">Out Sale</option>
                                                </c:if>
                                                <c:if test="${not item.status}">
                                                    <option value="false">Out Sale</option>
                                                    <option value="true">On Sale</option>
                                                </c:if>
                                            </select>
                                        </div>
                                        <br/><br/>

                                        <div class="row">
                                            <input class="btn btn-lg btn-primary col-3 btn-edit" type="button" value="Edit" name="btnAction" target='form-${counter.count}'/>
                                            <div class="col-1"></div>
                                            <input class="btn btn-lg btn-primary col-4 .btnSaveChanged" type="submit" id="saveChangedBtn" value="Save Changed" name="btnAction" disabled="true"/>
                                            <div class="col-1"></div>
                                            <input class="btn btn-lg btn-primary col-3" type="submit" value="Delete" name="btnAction" onclick="return confirm('Are you sure?')"/>
                                        </div>
                                    </div>
                                </form>
                                <c:if test="${counter.count % 2 == 0}"> 
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${not empty requestScope.NO_SEARCH_RESULT}">
                    <h1>
                        ${requestScope.NO_SEARCH_RESULT}
                    </h1>
                </c:if>
            </main>

            <main>
                <div style="margin-left: 40%; width: 5%">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination">
                            <c:forEach var="i" begin="1" end="${numberOfPage}">
                                <c:url value="DispatchServlet" var="swapPageNumber">
                                    <c:param name="pageNumberAdmin" value="${i}"></c:param>
                                </c:url>
                                <li class="page-item"><a class="page-link" href="${swapPageNumber}">${i}</a></li>
                                </c:forEach>
                        </ul>
                    </nav>
                </div>
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

        <script>
                                                $('.btn-edit').on('click', (event) => {
                                                    var formId = $(event.target).attr('target');
                                                    var inputSelector = 'form#' + formId + ' :input';
                                                    var selectSelector = 'form#' + formId + ' :select';
//                                                    var buttonSelector = 'form#' + formId + ' :button';

                                                    $(inputSelector).each(function () {
                                                        var input = $(this);
                                                        input.attr('disabled', false);
                                                    });
                                                    $(selectSelector).each(function () {
                                                        var select = $(this);
                                                        select.attr('disabled', false);
                                                    });
                                                    $('.btnSaveChanged').attr('disabled', false);
                                                }
                                                );
        </script> 
    </body>
</html>
