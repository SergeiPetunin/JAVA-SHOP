<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="w-100 text-center my-5">Редактирование ролей пользователю</h1>
    <div class="row d-flex justify-content-center">
        <div class="card m-2 border-0" style="width: 45rem;">
            <form action="editUserRole" method="POST">
                <div class="mb-3 row">
                    <label for="userSelect" class="col-sm-4 col-form-label text-end">Список пользователей</label>
                    <div class="col-sm-8">
                        <select class="form-select" name="userId" id="userSelect">
                                <option value="#" selected disabled>Выберите пользователя</option>
                            <c:forEach var="user" items="${listUsers}">
                                <option value="${user.id}">
                                    ${user.customer.firstName} ${user.customer.lastName} роли {
                                        <c:forEach var="userRole" items="${user.roles}">
                                            ${userRole}
                                        </c:forEach>
                                    }
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="mb-3 row">
                    <label for="roleSelect" class="col-sm-4 col-form-label text-end">Список ролей</label>
                    <div class="col-sm-4">
                        <select class="form-select" name="roleName" id="roleSelect">
                            <option value="#" selected disabled>Выберите роль</option>
                            <c:forEach var="role" items="${listRoles}">
                                <option value="${role}">
                                    ${role} 
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="mb-3 row">
                    <div class="col-sm-12 d-flex justify-content-end">
                        <button class="btn btn-primary mx-2" type="submit" name="btnDelete" value="delete">Удалить роль</button>
                        <button class="btn btn-primary" type="submit"  name="btnAdd" value="add">Добавить роль</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
