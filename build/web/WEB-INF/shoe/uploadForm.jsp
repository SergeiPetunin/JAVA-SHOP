<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <h1 class="w-100 text-center my-5">Новый файл</h1>
    <div class="row d-flex justify-content-center ">
        <div class="card m-2 border-0" style="width: 35rem;">
            <form action="uploadCover" method="POST"  enctype="multipart/form-data">
                <div class="mb-3 row">
                    <label for="description" class="col-sm-3 col-form-label">Описание</label>
                    <div class="col-sm-9">
                      <input type="text" class="w-100" id="description" name="description" value="">
                    </div>
                </div>
                <div class="mb-3 row">
                    <label for="inputFile" class="col-sm-3 col-form-label">Загрузить файл</label>
                    <div class="col-sm-9">
                      <input type="file" class="w-100" id="inputFile" name="file" value="">

                    </div>
                </div>
                <div class="mb-3 row">
                    <div class="col-sm-12 d-flex justify-content-end">
                        <input class="btn btn-primary" type="submit" value="Загрузить">
                    </div>
                </div>
            </form>
        </div>

    </div>
