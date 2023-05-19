<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <h1>New Shoe</h1>
        <a class="w-100 text-center my-2" href="uploadForm">Загрузить обложку</a>
        <div style="width: 30em; margin: auto">
            
            <form action="createShoe" method="POST">

                <div class="input-group mb-3">
                    <div class="input-group-prepend" style="width: 9.2em">
                      <span class="input-group-text">Название обуви:</span>
                    </div>
                    <input type="text" name="name" value="" class="form-control">
                </div>
                
                <div class="input-group mb-3">
                    <div class="input-group-prepend" style="width: 9.2em">
                      <span class="input-group-text">Цена:</span>
                    </div>
                    <input type="text" name="price" value="" class="form-control">
                </div>
                
                <div class="input-group mb-3">
                    <div class="input-group-prepend" style="width: 9.2em">
                      <span class="input-group-text">Количество:</span>
                    </div>
                    <input type="text" name="countItem" value="" class="form-control">
                </div>
                
                <div class="mb-3 row">
                    <label for="selectCover" class="col-sm-4 col-form-label">Обложки</label>
                    <div class="col-sm-8">
                        <select class="form-select" name="coverId" rows="5" cols="20" id="selectCover">
                            <c:forEach var="cover" items="${listCovers}">
                                <option value="${cover.id}">${cover.description}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <input type="submit" class="btn btn-secondary btn-lg" value="Добавить">
            </form>
        </div>
