<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <h1>Список покупателей</h1>
    
    <table class="table table-striped table-light">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Имя</th>
            <th scope="col">Фамилия</th>
            <th scope="col">Телефон</th>
            <th scope="col">Деньги</th>
          </tr>
        </thead>

        <tbody>
            <c:forEach var="customer" items="${listCustomers}" varStatus="status">
                <tr>
                  <th scope="row">${status.index+1}</th>
                  <td>${customer.firstName}</td>
                  <td>${customer.lastName}</td>
                  <td>${customer.phone}</td>
                  <td>${customer.cash}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>