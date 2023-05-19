<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
    <h1>Welcome shoe shop!</h1>
    
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
    