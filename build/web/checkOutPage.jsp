<%-- 
    Document   : checkOutPage
    Created on : 15-Jan-2021, 11:21:38
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="https://getbootstrap.com/docs/4.0/assets/img/favicons/favicon.ico">

        <title>Checkout example for Bootstrap</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/checkout/">

        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

        <!-- Custom styles for this template -->
        <link href="css/form-validation.css" rel="stylesheet">
    </head>
    <body class="bg-light" data-new-gr-c-s-check-loaded="14.991.0" data-gr-ext-installed="">

        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:set var="totalMoneyCart" value="${requestScope.TOTAL_MONEY_CART}"/>

        <div class="container">
            <div class="py-5 text-center">
                <h2>Last Step</h2>
                <p class="lead">
                    Your bill is ready for shipping to you. Fill in those fields below to complete your shopping.
                </p>
            </div>

            <div class="row">
                <div class="col-md-4 order-md-2 mb-4">
                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                        <span class="text-muted">Your cart</span>
                        <span class="badge badge-secondary badge-pill">${cart.size}</span>
                    </h4>
                    <ul class="list-group mb-3">
                        <c:forEach items="${cart.cartItem}" var="itemCart">
                            <li class="list-group-item d-flex justify-content-between lh-condensed">
                                <div>
                                    <h6 class="my-0">${itemCart.key.name} ( ${itemCart.value} )</h6>
                                    <small class="text-muted">${itemCart.key.description}</small>
                                </div>
                                <span class="text-muted">$${itemCart.key.price * itemCart.value}</span>
                            </li>
                        </c:forEach>

                        <li class="list-group-item d-flex justify-content-between">
                            <span>Total (USD)</span>
                            <strong>$${cart.totalMoney}</strong>
                        </li>
                    </ul>

                </div>
                <div class="col-md-8 order-md-1">
                    <h4 class="mb-3">Billing address</h4>
                    <form action="DispatchServlet" class="needs-validation" novalidate="">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="fullname">Full name</label>
                                <input type="text" class="form-control" id="fullname" placeholder="" value="" required="" name="txtFullname">
                                <div class="invalid-feedback">
                                    Valid first name is required.
                                </div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="phonenumber">Phone Number</label>
                                <input type="number" class="form-control" id="phonenumber" placeholder="" value="" required="" name="txtPhoneNumber">
                                <div class="invalid-feedback">
                                    Valid last name is required.
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="address">Address</label>
                            <input type="text" class="form-control" id="address" placeholder="1234 Main St" required="" name="txtAddress">
                            <div class="invalid-feedback">
                                Please enter your shipping address.
                            </div>
                        </div>

                        <hr class="mb-4">

                        <h4 class="mb-3">Payment</h4>

                        <input type="hidden" name="txtTotalMoney" value="${totalMoneyCart}" />
                        
                        
                        <div class="d-block my-3">
                            <div class="custom-control custom-radio">
                                <input id="cash" name="paymentMethod" type="radio" class="custom-control-input" checked="" required="" value="cash">
                                <label class="custom-control-label" for="cash">COD</label>
                            </div>
                            <div class="custom-control custom-radio">
                                <input id="paypal" name="paymentMethod" type="radio" class="custom-control-input" required="" value="paypal">
                                <label class="custom-control-label" for="paypal">Paypal</label>
                            </div>
                            
                            <%--<c:url value=""--%>
                            
                        </div>
                        <hr class="mb-4">
                        <button class="btn btn-primary btn-lg btn-block" id="checkOutBtn" type="submit" name="btnAction" value="checkOut">Continue to checkout</button>
                    </form>
                </div>
            </div>

            <footer class="my-5 pt-5 text-muted text-center text-small">
                <p class="mb-1">© 2021 Hana Shop</p>
                <ul class="list-inline">
                    <li class="list-inline-item"><a href="https://getbootstrap.com/docs/4.0/examples/checkout/#">Privacy</a></li>
                    <li class="list-inline-item"><a href="https://getbootstrap.com/docs/4.0/examples/checkout/#">Terms</a></li>
                    <li class="list-inline-item"><a href="https://getbootstrap.com/docs/4.0/examples/checkout/#">Support</a></li>
                </ul>
            </footer>
        </div>

        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <!--<script src="./Checkout example for Bootstrap_files/jquery-3.2.1.slim.min.js.tải xuống" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>-->
        <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" integrity="sha512-bLT0Qm9VnAYZDflyKcBaQ2gg0hSYNQrJ8RilYldYQ1FxQYoCLtUjuuRuZo+fjqhx/qtq/1itJ0C2ejDxltZVFg==" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/holder/2.9.7/holder.min.js"></script>
        <script>
            // Example starter JavaScript for disabling form submissions if there are invalid fields
            (function () {
                'use strict';

                window.addEventListener('load', function () {
                    // Fetch all the forms we want to apply custom Bootstrap validation styles to
                    var forms = document.getElementsByClassName('needs-validation');

                    // Loop over them and prevent submission
                    var validation = Array.prototype.filter.call(forms, function (form) {
                        form.addEventListener('submit', function (event) {
                            if (form.checkValidity() === false) {
                                event.preventDefault();
                                event.stopPropagation();
                            }
                            form.classList.add('was-validated');
                        }, false);
                    });
                }, false);
            })();

//            $('#checkOutBtn').click(function () {
//                if ($('#paypal').is(':checked')) {
//                    
//                }
//            });
        </script>


    </body>
</html>
