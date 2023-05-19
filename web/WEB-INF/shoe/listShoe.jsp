

<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <h1>Список обуви</h1>
        <table class="table table-striped table-light">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Название обуви</th>
                <th scope="col">Цена</th>
                <th scope="col">Кол. на складе</th>
              </tr>
            </thead>
            
            <tbody>
                <c:forEach var="shoe" items="${listShoes}" varStatus="status">
                    <tr>
                      <th scope="row">${status.index+1}</th>
                      <td>${shoe.name}</td>
                      <td>${shoe.price}</td>
                      <td>${shoe.countItem}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        
        <div class="row d-flex justify-content-center ">
            <c:forEach var="shoe" items="${listShoes}">
                <div class="card m-2" style="width: 15rem;">
                    <img src="insertFile/${shoe.cover.url}" class="card-img-top" alt="...">
                    <div class="card-body">
                      <h5 class="card-title">${shoe.name}</h5>
                      
                      <a href="shoe?shoeId=${shoe.id}" class="btn btn-primary">Посмотреть обувь</a>
                    </div>
                </div>
            </c:forEach>
        </div>