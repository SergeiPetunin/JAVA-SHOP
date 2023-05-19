
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
    <h1>New Customer</h1>

    <div style="width: 30em; margin: auto">
            
            <form action="createCustomer" method="POST">
                
                <div class="input-group mb-3">
                    <div class="input-group-prepend" style="width: 9.2em">
                      <span class="input-group-text">Логин:</span>
                    </div>
                    <input type="text" name="login" value="" class="form-control">
                </div>
                
                <div class="input-group mb-3">
                    <div class="input-group-prepend" style="width: 9.2em">
                      <span class="input-group-text">Пароль:</span>
                    </div>
                    <input type="password" name="password" value="" class="form-control">
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-prepend" style="width: 9.2em">
                      <span class="input-group-text">Имя:</span>
                    </div>
                    <input type="text" name="firstName" value="" class="form-control">
                </div>
                
                <div class="input-group mb-3">
                    <div class="input-group-prepend" style="width: 9.2em">
                      <span class="input-group-text">Фамилия:</span>
                    </div>
                    <input type="text" name="lastname" value="" class="form-control">
                </div>
                
                <div class="input-group mb-3">
                    <div class="input-group-prepend" style="width: 9.2em">
                      <span class="input-group-text">Телефон:</span>
                    </div>
                    <input type="text" name="phone" value="" class="form-control">
                </div>
                
                <div class="input-group mb-3">
                    <div class="input-group-prepend" style="width: 9.2em">
                      <span class="input-group-text">Деньги:</span>
                    </div>
                    <input type="text" name="cash" value="" class="form-control">
                </div>

                <input type="submit" class="btn btn-secondary btn-lg" value="Добавить">
            </form>
        </div>