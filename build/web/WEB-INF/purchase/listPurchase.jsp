<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.text.SimpleDateFormat"%>
<%
  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
  getServletContext().setAttribute("dateFormat", dateFormat);
%>


 <h1>Список всех покупок</h1>
 
 
<table class="table table-striped table-light">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Покупатель</th>
            <th scope="col">Товар</th>
            <th scope="col">Цена шт.</th>
            <th scope="col">Кол-во</th>
            <th scope="col">Сумма</th>
            <th scope="col">Дата покупки</th>
          </tr>
        </thead>

        <tbody>
            <c:forEach var="purchase" items="${listPurchases}" varStatus="status">
                <tr>
                  <th scope="row">${status.index+1}</th>
                  <td>${purchase.customer.firstName} ${purchase.customer.lastName}</td>
                  <td>${purchase.shoe.name}</td>
                  <td>${purchase.shoe.price} &#x20AC; </td>
                  <td>${purchase.qtty}</td>
                  <td>${purchase.qtty * purchase.shoe.price}</td>
                  <td>${dateFormat.format(purchase.purchaseDate)}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>