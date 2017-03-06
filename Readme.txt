Краткая справка по demoOAuth.

Состоит из 3 модулей:
1. auth-server - сервер авторизации
    запускается на 9999 порту uaa/
    в памяти хранит 2 пользователей (user1, password1, roles("USER"))
                                    (admin, password, roles("ADMIN","USER"))
    для получения токена надо выполнить:
    curl -v myclient:myclientsecret@localhost:9999/uaa/oauth/token -d grant_type=password -d username=user1 -d password=password1
    или в Postman: http://localhost:9999/uaa/oauth/token?grant_type=password&username=user1&password=password1 (with myclient myclientsecret)
    для получения информации о пользователе:
    curl -H "Authorization: Bearer {TOKEN}" localhost:9999/uaa/user
2. resource-server - ресурс сервер
    запускается на 9000 порту
    разграничены права доступа для получения доступа к ресурсам
                                   /resource/** - все зарегестрированные
                                   /resource/samplestring/** - "hasRole('USER')"
                                   /resource/samplestringAdmin/** - "hasRole('ADMIN')"
    для получения доступа к ресурсам надо выполнить:
    curl -H "Authorization: Bearer {TOKEN}" localhost:9000/resource/samplestring
3. UI - доступ к ресурсам через браузер (токен получается автоматом)
    запускается на 8080 порту
    разграничены права доступа для получения доступа к ресурсам
                                    /index.html - все
                                    /resource - все
                                    /resource/samplestring/** - "hasRole('USER')"
                                    /resource/samplestringAdmin/** - "hasRole('ADMIN')"
    для получения доступа просто выполнять запросы в браузере
