<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <h1>Добавить покупку</h1>

    <h2>Список покупателей</h2>
    
    <form action="createPurchase" method="POST">
        <div>
            <label for="selectCustomers">Покупатели</label>
            <div>
                <select name="customerId" id="selectCustomers">
                    <c:forEach var="customer" items="${listCustomers}">
                        <option value="${customer.id}">${customer.firstName}${customer.lastName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <h2>Список обуви</h2>
        <div>
            <label for="selectShoe">Обувь</label>
            <div>
                <select name="shoeId" id="selectShoe">
                    <c:forEach var="shoe" items="${listShoes}">
                        <option value="${shoe.id}">${shoe.name} | ${shoe.price}&#x20AC;</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        
        
        <h2>Количество</h2>
        <div>
            <input placeholder="Кол-во" name="qtty"/>
        </div>
        
        <div>
            <div>
                <input type="submit" value="Оформить покупку">
            </div>
        </div>
    </form>