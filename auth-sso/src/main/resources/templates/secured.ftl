<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>freemarker test</title>
</head>
<body>

<!-- curUser -->
    <#assign user=security.getUser()>
    <#assign role=security.hasRole("ROLE_USER")>
    hello! ${user.principal}
    <#if role == true>
        has ROLE_USER
    </#if>
</body>
</html>