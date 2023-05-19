
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="w-100 text-center my-5">Авторизация</h1>
<div class="row d-flex justify-content-center ">
    <div class="card m-2 border-0" style="width: 25rem;">
        <form action="login" method="POST">
            <div class="mb-3 row">
                <label for="inputLogin" class="col-sm-3 col-form-label">Логин</label>
                <div class="col-sm-9">
                  <input type="text" class="w-100" id="inputLogin" name="login" value="">
                </div>
            </div>
            <div class="mb-3 row">
                <label for="inputPassword" class="col-sm-3 col-form-label">Пароль</label>
                <div class="col-sm-9">
                  <input type="password" class="w-100" id="inputPassword" name="password" value="">
                </div>
            </div>

            <div class="mb-3 row">
                <div class="col-sm-12 d-flex justify-content-end">
                    <input class="btn btn-primary" type="submit" value="Войти">
                </div>
            </div>
        </form>
    </div>
    
</div>